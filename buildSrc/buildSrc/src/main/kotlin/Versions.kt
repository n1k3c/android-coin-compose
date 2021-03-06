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

import org.gradle.api.JavaVersion

object App {
    const val packageName = "com.nikec.coincompose"
    const val versionMajor = 1
    const val versionMinor = 0
    const val versionPatch = 0
}

object BuildConfig {
    const val minSdk = 26
    const val compileSdk = 31
    const val targetSdk = 31
    val javaVersion = JavaVersion.VERSION_1_8
    const val jvmTarget = "1.8"

    const val versionCode =
        App.versionMajor * 1000 + App.versionMinor * 100 + App.versionPatch * 10
    const val versionName =
        "${App.versionMajor}.${App.versionMinor}.${App.versionPatch}"
}

object Versions {
    const val kotlin = "1.5.21"
    const val androidBuildTools = "7.1.2"
    const val compose = "1.0.1"
    const val composeActivity = "1.3.0"
    const val composeViewModel = "1.0.0-alpha07"
    const val androidxCore = "1.3.2"
    const val androidxAppcompat = "1.2.0"
    const val androidxLifecycleRuntime = "2.4.0-alpha01"
    const val androidxTestJUnit = "1.1.2"
    const val material = "1.3.0"
    const val hilt = "2.41"
    const val hiltCompose = "1.0.0"
    const val coroutines = "1.4.3"
    const val navigation = "2.4.1"
    const val paging = "1.0.0-alpha12"
    const val room = "2.3.0"
    const val okhttp = "4.9.1"
    const val retrofit = "2.9.0"
    const val moshi = "1.12.0"
    const val timberkt = "1.5.1"
    const val coil = "1.3.2"
    const val accompanist = "0.18.0"
    const val splashscreen = "1.0.0-alpha01"
    const val desugarJdkLibs = "1.0.9"
    const val jsoup = "1.14.2"
    const val browser = "1.3.0"
    const val protoDataStore = "1.0.0"
    const val protoDataStoreJavaLite = "3.10.0"

    const val mockk = "1.10.6"
    const val junit = "4.13"
    const val robolectric = "4.4"
}
