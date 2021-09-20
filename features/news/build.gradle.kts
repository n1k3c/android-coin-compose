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
    implementation(project(":core"))
    implementation(project(":core-ui"))

    androidxDependencies()
    composeDependencies()
    implementation(Dependencies.composeNavigation)
    implementation(Dependencies.composePaging)

    implementation(Dependencies.hiltCompose)
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)

    coroutinesDependencies()
    retrofitDependencies()

    implementation(Dependencies.roomKtx)

    implementation(Dependencies.timberkt)
    implementation(Dependencies.coil)
    implementation(Dependencies.accompanistSwipeToRefresh)
    coreLibraryDesugaring(Dependencies.desugarJdkLibs)
    implementation(Dependencies.jsoup)

    testImplementationDependencies()
    androidTestImplementationDependencies()
}
