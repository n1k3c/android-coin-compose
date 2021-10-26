/*
 * Developed by n1k3c (Nikola Curilović)  2021
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import Dependencies.androidTestImplementationDependencies
import Dependencies.androidxDependencies
import Dependencies.composeDependencies
import Dependencies.coroutinesDependencies
import Dependencies.retrofitDependencies
import Dependencies.testImplementationDependencies
import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

val secretsPropertiesFile = rootProject.file("secrets.properties")
val secretProperties = Properties()

if (secretsPropertiesFile.exists()) {
    secretProperties.load(FileInputStream(secretsPropertiesFile))
} else {
    secretProperties.setProperty(
        "signing_keystore_password",
        System.getenv("signing_keystore_password") ?: ""
    )
    secretProperties.setProperty(
        "signing_key_password",
        System.getenv("signing_key_password") ?: ""
    )
    secretProperties.setProperty(
        "signing_key_alias",
        System.getenv("signing_key_alias") ?: ""
    )
}

android {
    compileSdk = BuildConfig.compileSdk

    defaultConfig {
        applicationId = App.packageName
        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk
        versionCode = BuildConfig.versionCode
        versionName = BuildConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    signingConfigs {
        create("release") {
            storeFile = file("../coin-compose.jks")
            storePassword = "${secretProperties["signing_keystore_password"]}"
            keyAlias = "${secretProperties["signing_key_alias"]}"
            keyPassword = "${secretProperties["signing_key_password"]}"
        }
    }
    flavorDimensions.add("version")
    productFlavors {
        create("development") {
            versionNameSuffix = "-development"
            dimension = "version"
        }
        create("staging") {
            versionNameSuffix = "-staging"
            dimension = "version"
        }
        create("production") {
            dimension = "version"
        }
    }
    compileOptions {
        sourceCompatibility = BuildConfig.javaVersion
        targetCompatibility = BuildConfig.javaVersion
    }
    kotlinOptions {
        jvmTarget = BuildConfig.jvmTarget
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
    kapt {
        correctErrorTypes = true
        useBuildCache = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    packagingOptions {
        resources.excludes.add("META-INF/*.version")
        resources.excludes.add("META-INF/proguard/*")
        resources.excludes.add("/*.properties")
        resources.excludes.add("META-INF/*.properties")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
    hilt {
        enableExperimentalClasspathAggregation = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-ui"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":feature-ui:coins"))
    implementation(project(":feature-ui:news"))
    implementation(project(":feature-ui:settings"))

    androidxDependencies()
    composeDependencies()
    implementation(Dependencies.composeNavigation)

    implementation(Dependencies.hiltCompose)
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)

    coroutinesDependencies()
    retrofitDependencies()

    implementation(Dependencies.timberkt)
    implementation(Dependencies.splashscreen)

    implementation(Dependencies.accompanistSystemUiController)

    testImplementationDependencies()
    androidTestImplementationDependencies()
}
