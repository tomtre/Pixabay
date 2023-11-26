plugins {
    id("android.library")
    id("android.library.jacoco")
    id("android.hilt")
}

android {
    namespace = "com.tomtre.pixabay.core.common"
}

dependencies {
    api(libs.timber)
    api(libs.arrow.core)
}
