import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectories
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.create
import java.net.URI

interface AssetDownloadPluginExtension {
    val outputDirectory: DirectoryProperty
    val assetsToDownload: MapProperty<String, String>
    fun download(url: String, filename: String) {
        assetsToDownload.put(url, filename)
    }
}

class AssetDownloadPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create<AssetDownloadPluginExtension>("assets")

        val assetsDirectory = project.layout.buildDirectory.dir("resources/assets")
        project.sourceSets.named("main").configure {
            resources.srcDirs(assetsDirectory)
        }

        extension.outputDirectory.convention(assetsDirectory.map { it.dir("static") })

        val downloadTask =
            project.tasks.register("assetsDownload", AssetDownloadTask::class.java) {
                outputDirectory.convention(extension.outputDirectory)
                assetsToDownload.convention(extension.assetsToDownload)
            }

        project.tasks.named("processResources").configure {
            dependsOn(downloadTask, downloadTask)
        }
    }

    private val Project.sourceSets: SourceSetContainer
        get() = project.extensions.getByName("sourceSets") as SourceSetContainer
}

@CacheableTask
abstract class AssetDownloadTask : DefaultTask() {
    @get:OutputDirectories
    abstract val outputDirectory: DirectoryProperty

    @get:Input
    abstract val assetsToDownload: MapProperty<String, String>

    @TaskAction
    fun run() {
        assetsToDownload.get().forEach(this::download)
    }

    private fun download(url: String, filename: String) {
        println("$url -> ${outputDirectory.get().file(filename).asFile.toPath()}")
        download(URI(url), outputDirectory.get().file(filename).asFile.toPath())
    }
}
