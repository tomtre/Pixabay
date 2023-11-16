@file:Suppress("UnstableApiUsage", "UNUSED_VARIABLE")

plugins {
    id("android.application")
    id("android.application.compose")
    id("android.application.flavors")
    id("android.hilt")
    alias(libs.plugins.junit5)
}

android {
    defaultConfig {
        applicationId = "com.tomtre.pixabay"
        versionCode = getVersionCode()
        versionName = getVersionName()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        val debug by getting {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = PixabayBuildType.DEBUG.applicationIdSuffix
        }
        val release by getting {
            isDebuggable = false
            isMinifyEnabled = true
            applicationIdSuffix = PixabayBuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            signingConfig = signingConfigs.getByName("debug")
        }
        val staging by creating {
            initWith(release)
            matchingFallbacks.add("release")
            applicationIdSuffix = PixabayBuildType.STAGING.applicationIdSuffix
            isDebuggable = true
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    namespace = "com.tomtre.pixabay"
}

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:details"))

    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    debugImplementation(libs.androidx.compose.ui.testManifest)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit5.api)
    testRuntimeOnly(libs.junit5.engine)

    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test)
}

// androidx.test is forcing JUnit, 4.12. This forces it to use 4.13
configurations.configureEach {
    resolutionStrategy {
        force(libs.junit4)
        // Temporary workaround for https://issuetracker.google.com/174733673
        force("org.objenesis:objenesis:2.6")
    }
}

val checkReleaseVersion by tasks.registering {
    doLast {
        val versionName = android.defaultConfig.versionName
        if (versionName?.matches("\\d+(\\.\\d+)+".toRegex()) == false) {
            throw GradleException(
                "Version name for release builds can only be numeric (like 1.0), but was $versionName\n" +
                    "Please use git tag to set version name on the current commit and try again\n" +
                    "For example: git tag -a 1.0 -m 'v1.0'"
            )
        }
    }
}

tasks.whenTaskAdded {
    if (name.contains("assemble") &&
        name.contains("Release")
    ) {
        dependsOn(checkReleaseVersion)
    }
}
