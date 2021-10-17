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
include(":feature-ui:coins")
include(":feature-ui:news")
include(":feature-ui:settings")
include(":domain")
include(":data")
