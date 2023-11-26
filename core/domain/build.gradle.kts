plugins {
    id("android.library")
    id("android.hilt")
}

android {
    namespace = "com.tomtre.core.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    implementation(libs.androidx.paging.common.ktx)
    implementation(libs.androidx.paging.runtime.ktx)
}
