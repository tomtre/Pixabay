@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("android.library")
    id("android.hilt")
    id("kotlinx-serialization")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.tomtre.pixabay.core.network"
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.bundles.ktor)

//    implementation(project(":core:common"))
//    implementation(project(":core:model"))
//    implementation(libs.coil.kt)
//    implementation(libs.coil.kt.svg)
//    implementation(libs.kotlinx.coroutines.android)
//    implementation(libs.kotlinx.datetime)
//    implementation(libs.kotlinx.serialization.json)
//    implementation(libs.okhttp.logging)
//    implementation(libs.retrofit.core)
//    implementation(libs.retrofit.kotlin.serialization)

//    testImplementation(project(":core:testing"))
}
