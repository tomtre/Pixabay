plugins {
    id("android.feature")
    id("android.library")
    id("android.library.compose")
}

android {
    namespace = "com.tomtre.pixabay.feature.imagelist"
}

dependencies {
    implementation(libs.androidx.paging.common.ktx)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)
}
