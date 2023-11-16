@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("android.library")
    id("android.library.jacoco")
    id("android.hilt")
}

android {
    namespace = "com.tomtre.core.common"
}

dependencies {
    api(libs.timber)
    api(libs.arrow.core)
}
