/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    `kotlin-dsl`
}

group = "buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "android.application.compose"
            implementationClass = "plugin.AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "android.application"
            implementationClass = "plugin.AndroidApplicationConventionPlugin"
        }
        register("androidApplicationJacoco") {
            id = "android.application.jacoco"
            implementationClass = "plugin.AndroidApplicationJacocoConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "android.library.compose"
            implementationClass = "plugin.AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "android.library"
            implementationClass = "plugin.AndroidLibraryConventionPlugin"
        }
        register("androidLibraryJacoco") {
            id = "android.library.jacoco"
            implementationClass = "plugin.AndroidLibraryJacocoConventionPlugin"
        }
        register("androidTest") {
            id = "android.test"
            implementationClass = "plugin.AndroidTestConventionPlugin"
        }
        register("androidHilt") {
            id = "android.hilt"
            implementationClass = "plugin.AndroidHiltConventionPlugin"
        }
        register("androidFlavors") {
            id = "android.application.flavors"
            implementationClass = "plugin.AndroidApplicationFlavorsConventionPlugin"
        }
        register("androidFeature") {
            id = "android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidRoom") {
            id = "android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmLibrary") {
            id = "jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}
