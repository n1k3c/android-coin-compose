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
