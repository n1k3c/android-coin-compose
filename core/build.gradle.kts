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

android {
    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {
    androidxDependencies()
    composeDependencies()
    implementation(Dependencies.composeNavigation)
    implementation(Dependencies.composePaging)

    implementation(Dependencies.hiltCompose)
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)

    coroutinesDependencies()
    retrofitDependencies()

    api(Dependencies.roomRuntime)
    implementation(Dependencies.roomKtx)
    annotationProcessor(Dependencies.roomCompiler)
    kapt(Dependencies.roomCompiler)

    implementation(Dependencies.timberkt)

    implementation(Dependencies.kotlinReflect)

    testImplementationDependencies()
    androidTestImplementationDependencies()
}
