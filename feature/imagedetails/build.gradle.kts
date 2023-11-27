plugins {
    id("android.feature")
    id("android.library")
    id("android.library.compose")
    alias(libs.plugins.junit5)
}

android {
    namespace = "com.tomtre.pixabay.feature.imagedetails"
}

dependencies {

    testImplementation(libs.accompanist.testharness)
    testImplementation(libs.androidx.activity.compose)
    testImplementation(libs.androidx.compose.ui.test)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.espresso.core)
    testImplementation(libs.androidx.test.rules)
    testImplementation(libs.androidx.test.runner)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.junit4)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)

    testImplementation(project(":core:testing"))
    testImplementation(libs.kluent)
    testImplementation(libs.mockk)
}
