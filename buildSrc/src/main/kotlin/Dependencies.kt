import org.gradle.kotlin.dsl.DependencyHandlerScope

object Dependencies {
    const val androidxCore = "androidx.core:core-ktx:${Versions.androidxCore}"
    const val androidxAppcompat = "androidx.appcompat:appcompat:${Versions.androidxAppcompat}"
    const val androidxLifecycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidxLifecycleRuntime}"
    const val material = "com.google.android.material:material:${Versions.material}"

    const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeActivity =
        "androidx.activity:activity-compose:${Versions.androidxActivityCompose}"
    const val composeViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.androidxLifecycleViewmodelCompose}"

    const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.navigation}"
    const val composePaging = "androidx.paging:paging-compose:${Versions.paging}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    const val hiltCompose = "androidx.hilt:hilt-navigation-compose:${Versions.androidxHiltCompose}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"

    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

    const val okhttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"

    const val timberkt = "com.github.ajalt:timberkt:${Versions.timberkt}"

    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val junit = "junit:junit:${Versions.junit}"
    const val roomTest = "androidx.room:room-testing:${Versions.room}"
    const val junitExt = "androidx.test.ext:junit:${Versions.androidxTestJUnit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.androidxTestEspresso}"
    const val composeUiTest = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"

    fun DependencyHandlerScope.androidxDependencies() {
        "implementation"(androidxCore)
        "implementation"(androidxAppcompat)
        "implementation"(androidxLifecycleRuntime)
        "implementation"(material)
    }

    fun DependencyHandlerScope.composeDependencies() {
        "implementation"(composeUi)
        "implementation"(composeMaterial)
        "implementation"(composeUiTooling)
        "implementation"(composeActivity)
        "implementation"(composeViewModel)
    }

    fun DependencyHandlerScope.coroutinesDependencies() {
        "implementation"(coroutinesAndroid)
        "implementation"(coroutinesCore)
        "implementation"(coroutinesTest)
    }

    fun DependencyHandlerScope.retrofitDependencies() {
        "implementation"(retrofit)
        "implementation"(retrofitMoshi)
        "implementation"(okhttp)
        "implementation"(okhttpLoggingInterceptor)
    }

    fun DependencyHandlerScope.testImplementationDependencies() {
        "testImplementation"(mockk)
        "testImplementation"(junit)
        "testImplementation"(roomTest)
    }

    fun DependencyHandlerScope.androidTestImplementationDependencies() {
        "androidTestImplementation"(junitExt)
        "androidTestImplementation"(espresso)
        "androidTestImplementation"(composeUiTest)
    }
}
