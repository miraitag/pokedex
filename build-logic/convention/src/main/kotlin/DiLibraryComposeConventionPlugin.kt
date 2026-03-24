import com.miraitag.pokedex.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class DiLibraryComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("miraitag.pokedex.di.library")
            dependencies.add("implementation", libs.findLibrary("koin.compose").get())
        }
    }
}