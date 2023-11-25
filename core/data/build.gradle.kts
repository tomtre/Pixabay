plugins {
    id("android.library")
    id("android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.tomtre.pixabay.core.data"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))

    implementation(libs.androidx.paging.common.ktx)
    implementation(libs.androidx.paging.runtime.ktx)
}
