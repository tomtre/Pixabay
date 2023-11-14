package buildlogic

import org.gradle.api.JavaVersion

object AndroidConfig {
    const val compileSdkVersion = 34
    const val targetSdkVersion = 34
    const val minSdkVersion = 21
    val javaSourceCompatibility = JavaVersion.VERSION_17
    val javaTargetCompatibility = JavaVersion.VERSION_17
    const val kotlinJvmTarget = "17"
}
