plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "miraitag.pokedex.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("diLibrary") {
            id = "miraitag.pokedex.di.library"
            implementationClass = "DiLibraryConventionPlugin"
        }
        register("diLibraryCompose") {
            id = "miraitag.pokedex.di.library.compose"
            implementationClass = "DiLibraryComposeConventionPlugin"
        }
    }
}