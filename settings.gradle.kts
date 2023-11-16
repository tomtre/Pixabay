pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Pixabay"
include(":app")
include(":feature:home")
include(":core:ui")
include(":feature:details")
include(":core:designsystem")
include(":core:network")
include(":core:common")
