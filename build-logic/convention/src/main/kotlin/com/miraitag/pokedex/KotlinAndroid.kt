package com.miraitag.pokedex

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension
) {
    commonExtension.apply {
        compileSdk {
            version = release(36)
        }

        defaultConfig.minSdk = 24

        compileOptions.sourceCompatibility = JavaVersion.VERSION_17
        compileOptions.targetCompatibility = JavaVersion.VERSION_17

        buildFeatures.compose  = true
        buildFeatures.buildConfig = true
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    dependencies {
        add("implementation", libs.findLibrary("androidx.core.ktx").get())
        add("implementation", libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
    }
}