dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Coin Compose"

include(":app")
include(":core")
include(":core-ui")
include(":features:coins")
include(":features:news")
