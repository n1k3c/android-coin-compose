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

import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

val secretsPropertiesFile = rootProject.file("secrets.properties")
val secretProperties = Properties()

if (secretsPropertiesFile.exists()) {
    secretProperties.load(FileInputStream(secretsPropertiesFile))
} else {
    secretProperties.setProperty(
        "news_api_key",
        System.getenv("news_api_key") ?: ""
    )
}

android {
    compileSdk = BuildConfig.compileSdk

    defaultConfig {
        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    compileOptions {
        sourceCompatibility = BuildConfig.javaVersion
        targetCompatibility = BuildConfig.javaVersion
        isCoreLibraryDesugaringEnabled = true
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
    flavorDimensions.add("version")
    productFlavors {
        create("development") {
            buildConfigField(
                "String",
                "COINS_BASE_URL",
                "\"https://api.coingecko.com/\""
            )
            buildConfigField("String", "NEWS_BASE_URL", "\"https://cryptopanic.com/\"")
            buildConfigField("String", "NEWS_API_KEY", "\"${secretProperties["news_api_key"]}\"")
            dimension = "version"
        }
        create("staging") {
            buildConfigField(
                "String",
                "COINS_BASE_URL",
                "\"https://api.coingecko.com/\""
            )
            buildConfigField("String", "NEWS_BASE_URL", "\"https://cryptopanic.com/\"")
            buildConfigField("String", "NEWS_API_KEY", "\"${secretProperties["news_api_key"]}\"")
            dimension = "version"
        }
        create("production") {
            buildConfigField(
                "String",
                "COINS_BASE_URL",
                "\"https://api.coingecko.com/\""
            )
            buildConfigField("String", "NEWS_BASE_URL", "\"https://cryptopanic.com/\"")
            buildConfigField("String", "NEWS_API_KEY", "\"${secretProperties["news_api_key"]}\"")
            dimension = "version"
        }
    }
}
