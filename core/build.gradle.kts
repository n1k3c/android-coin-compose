import Dependencies.androidTestImplementationDependencies
import Dependencies.androidxDependencies
import Dependencies.composeDependencies
import Dependencies.coroutinesDependencies
import Dependencies.retrofitDependencies

import Dependencies.testImplementationDependencies

plugins {
    id("common-android")
    id("dagger.hilt.android.plugin")
}

dependencies {
    androidxDependencies()
    composeDependencies()
    implementation(Dependencies.composeNavigation)

    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)

    coroutinesDependencies()
    retrofitDependencies()

    implementation(Dependencies.timberkt)

    testImplementationDependencies()
    androidTestImplementationDependencies()
}
