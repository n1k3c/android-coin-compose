import Dependencies.androidxDependencies
import Dependencies.composeDependencies

plugins {
    id("common-android")
}

dependencies {
    androidxDependencies()
    composeDependencies()
    implementation(Dependencies.composeNavigation)
    implementation(Dependencies.composePaging)

    implementation(Dependencies.hiltCompose)
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)

    implementation(Dependencies.timberkt)
}
