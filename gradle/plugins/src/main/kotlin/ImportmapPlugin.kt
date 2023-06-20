import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.logging.Logger
import org.gradle.api.provider.MapProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectories
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.create
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.nio.file.Files
import java.nio.file.Path
import java.time.Duration
import java.time.temporal.ChronoUnit.SECONDS

interface ImportmapPluginExtension {
    val modulesToDownload: MapProperty<String, String>
    val outputDirectory: DirectoryProperty
    val sourcesOutputDirectory: DirectoryProperty
    val importmapFile: RegularFileProperty

    fun download(moduleProvider: String, version: String) {
        modulesToDownload.put(moduleProvider, version)
    }
}

class ImportmapPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create<ImportmapPluginExtension>("importmap")

        val vendorResources = project.layout.buildDirectory.dir("resources/vendor")
        project.sourceSets.named("main").configure {
            resources.srcDirs(vendorResources)
        }

        extension.importmapFile.convention(vendorResources.map { it.file("importmap.json") })
        extension.outputDirectory.convention(vendorResources.map { it.dir("static") })
        extension.sourcesOutputDirectory.convention(project.layout.projectDirectory.dir("node_modules/"))

        val downloadTask =
            project.tasks.register("importmapDownload", DownloadImportmapTask::class.java) {
                outputDirectory.convention(extension.outputDirectory)
                sourcesOutputDirectory.convention(extension.sourcesOutputDirectory)
                modulesToDownload.convention(extension.modulesToDownload)
            }

        val buildJsonTask =
            project.tasks.register("importmapBuild", BuildImportmapJsonTask::class.java) {
                importmapFile.convention(extension.importmapFile)
                modules.convention(extension.modulesToDownload)
            }

        project.tasks.named("processResources").configure {
            dependsOn(downloadTask, buildJsonTask)
        }
    }
    private val Project.sourceSets: SourceSetContainer
        get() = project.extensions.getByName("sourceSets") as SourceSetContainer
}

@CacheableTask
abstract class DownloadImportmapTask : DefaultTask() {
    @get:OutputDirectories
    abstract val outputDirectory: DirectoryProperty

    @get:OutputDirectories
    abstract val sourcesOutputDirectory: DirectoryProperty

    @get:Input
    abstract val modulesToDownload: MapProperty<String, String>

    private val esModuleProvider = EsmDevEsModuleProvider(this.logger)

    @TaskAction
    fun run() {
        modulesToDownload.get().forEach(this::download)
    }

    private fun download(moduleSpecifier: String, version: String) {
        val filename = filename(moduleSpecifier)
        val output = outputDirectory.file(filename)
        esModuleProvider.download(moduleSpecifier, version, output.get().asFile.toPath())
        esModuleProvider.downloadSource(
            moduleSpecifier,
            version,
            sourcesOutputDirectory.get().asFile.toPath(),
        )
    }
}

@CacheableTask
abstract class BuildImportmapJsonTask : DefaultTask() {
    @get:OutputFile
    abstract val importmapFile: RegularFileProperty

    @get:Input
    abstract val modules: MapProperty<String, String>

    @TaskAction
    fun run() {
        val imports =
            modules.get().toList().joinToString(separator = ",\n") { (moduleSpecifier, _) ->
                "    \"$moduleSpecifier\": \"/${filename(moduleSpecifier)}\""
            }
        val json =
            """
            {
              "imports": {
            $imports
              }
            }
            """.trimIndent()
        importmapFile.get().asFile.writeText(json)
    }
}

class EsmDevEsModuleProvider(private val logger: Logger) {
    fun download(moduleSpecifier: String, version: String, destination: Path) {
        val filename = filename(moduleSpecifier)
        val url = "https://esm.sh/v113/$moduleSpecifier@$version/es2022/$filename"

        logger.info("Downloading module $moduleSpecifier@$version to $destination")
        download(URI(url), destination)

        val sourceMapDestination = destination.parent.resolve("${destination.fileName}.map")
        logger.info("Downloading source maps of module $moduleSpecifier@$version to $sourceMapDestination")
        download(URI("$url.map"), sourceMapDestination)
    }

    fun downloadSource(moduleSpecifier: String, version: String, sourceDirectory: Path) {
        val filename = filename(moduleSpecifier, ".development")
        val directory = sourceDirectory.resolve(moduleSpecifier)
        Files.createDirectories(directory)

        logger.info("Downloading sources of $moduleSpecifier@$version to $directory")

        val url = "https://esm.sh/v113/$moduleSpecifier@$version/es2022/$filename"
        download(URI(url), directory.resolve("index.js"))
    }
}

private fun filename(moduleSpecifier: String, suffix: String = "") =
    "${moduleSpecifier.split("/").last()}$suffix.mjs"

fun download(uri: URI, destination: Path) {
    val request = HttpRequest.newBuilder()
        .uri(uri)
        .timeout(Duration.of(60, SECONDS))
        .GET()
        .build()
    Files.createDirectories(destination.parent)
    val response = HttpClient
        .newBuilder()
        .build()
        .send(request, BodyHandlers.ofFile(destination))
    if (response.statusCode() != 200) {
        throw IllegalStateException("Download from $uri failed: $response")
    }
}
