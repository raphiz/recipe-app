package li.raphael.kotlinhtml

import li.raphael.kotlinhtml.stimulus.StimulusController
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StimulusControllerTest {
    @Test
    fun `the url is the same as the given relative path`() {
        val path = "/controllers/foo_controller.js"
        val actual = StimulusController.of(path)
        assertThat(actual.url).isEqualTo(path)
    }

    @Test
    fun `the module path is the same as the given relative path without the file extension and leading slash`() {
        val path = "/controllers/foo_controller.mjs"
        val actual = StimulusController.of(path)
        assertThat(actual.modulePath).isEqualTo("controllers/foo_controller")
    }

    @Test
    fun `the module identifier is derived from the path and filename`() {
        val path = "/controllers/users/stuff/list_item_controller.js"
        val actual = StimulusController.of(path)
        assertThat(actual.identifier).isEqualTo("users--stuff--list-item")
    }

    @Test
    fun `the controller class name is derived from the path and filename`() {
        val path = "/controllers/users/stuff/list_item_controller.js"
        val actual = StimulusController.of(path)
        assertThat(actual.controllerClassName).isEqualTo("Users_Stuff_ListItemController")
    }
}
