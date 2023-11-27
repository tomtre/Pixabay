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
include(":feature:imagelist")
include(":feature:imagedetails")
include(":core:ui")
include(":core:designsystem")
include(":core:network")
include(":core:common")
include(":core:domain")
include(":core:model")
include(":core:data")
include(":core:database")
include(":core:testing")
