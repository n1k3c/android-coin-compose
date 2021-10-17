import Dependencies.androidTestImplementationDependencies
import Dependencies.androidxDependencies
import Dependencies.composeDependencies
import Dependencies.coroutinesDependencies
import Dependencies.retrofitDependencies
import Dependencies.testImplementationDependencies
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("common-android")
    id("dagger.hilt.android.plugin")
    id("com.google.protobuf") version "0.8.12"
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.10.0"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}

android {
    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {
    api(project(":core"))

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
    implementation(Dependencies.protoDataStore)
    implementation(Dependencies.protoDataStoreJavaLite)

    coreLibraryDesugaring(Dependencies.desugarJdkLibs)
    implementation(Dependencies.jsoup)

    testImplementationDependencies()
    androidTestImplementationDependencies()
}
