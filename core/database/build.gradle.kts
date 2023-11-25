plugins {
    id("android.library")
    id("android.hilt")
    id("android.room")
}

android {

    namespace = "com.tomtre.pixabay.core.database"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.androidx.room.paging)
}
