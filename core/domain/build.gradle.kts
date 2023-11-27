plugins {
    id("android.library")
    id("android.hilt")
}

android {
    namespace = "com.tomtre.pixabay.core.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    implementation(libs.androidx.paging.common.ktx)
    implementation(libs.androidx.paging.runtime.ktx)

    testImplementation(project(":core:testing"))
    testImplementation(libs.kluent)
    testImplementation(libs.mockk)
}
