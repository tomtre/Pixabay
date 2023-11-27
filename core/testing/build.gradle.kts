plugins {
    id("android.library")
    id("android.library.compose")
    id("android.hilt")
    alias(libs.plugins.junit5)
}

android {
    namespace = "com.tom.pixabay.core.testing"
}
dependencies {
    api(libs.accompanist.testharness)
    api(libs.androidx.activity.compose)
    api(libs.androidx.compose.ui.test)
    api(libs.androidx.test.core)
    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.runner)
    api(libs.hilt.android.testing)
    api(libs.junit4)
    api(libs.kotlinx.coroutines.test)
    api(libs.turbine)

//     Problem with R8, we have to import them in each module separately
//    api(libs.kluent)
//    api(libs.mockk)

    api(libs.junit5.api)
    runtimeOnly(libs.junit5.engine)
}
