import Dependencies.androidxDependencies
import Dependencies.composeDependencies

plugins {
    id("common-android")
}

dependencies {
    implementation(project(":core"))

    androidxDependencies()
    composeDependencies()
    implementation(Dependencies.composeNavigation)
    implementation(Dependencies.composePaging)
    
    implementation(Dependencies.accompanistSwipeToRefresh)

    implementation(Dependencies.timberkt)
}
