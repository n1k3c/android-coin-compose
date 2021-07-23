dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Coin Compose"
include(":app")

enableFeaturePreview("VERSION_CATALOGS")
include(":features:coins")
include(":core")
