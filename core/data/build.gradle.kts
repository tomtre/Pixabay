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
}
