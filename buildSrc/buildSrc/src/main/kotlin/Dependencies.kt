import org.gradle.kotlin.dsl.DependencyHandlerScope

object Dependencies {
    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val androidGradlePlugin =
        "com.android.tools.build:gradle:${Versions.androidBuildTools}"
    const val hiltGradlePlugin =
        "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"

    private const val androidxCore = "androidx.core:core-ktx:${Versions.androidxCore}"
    private const val androidxAppcompat =
        "androidx.appcompat:appcompat:${Versions.androidxAppcompat}"
    private const val androidxLifecycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidxLifecycleRuntime}"
    private const val material = "com.google.android.material:material:${Versions.material}"

    private const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    private const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    private const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    private const val composeActivity =
        "androidx.activity:activity-compose:${Versions.composeActivity}"
    private const val composeViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"

    const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.navigation}"
    const val composePaging = "androidx.paging:paging-compose:${Versions.paging}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    const val hiltCompose = "androidx.hilt:hilt-navigation-compose:${Versions.hiltCompose}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"

    private const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    private const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    private const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

    private const val okhttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    private const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private const val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    private const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    private const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"

    const val timberkt = "com.github.ajalt:timberkt:${Versions.timberkt}"
    const val coil = "io.coil-kt:coil-compose:${Versions.coil}"

    const val accompanistSwipeToRefresh =
        "com.google.accompanist:accompanist-swiperefresh:${Versions.accompanist}"
    const val accompanistSystemUiController =
        "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}"

    const val splashscreen = "androidx.core:core-splashscreen:${Versions.splashscreen}"
    const val desugarJdkLibs = "com.android.tools:desugar_jdk_libs:${Versions.desugarJdkLibs}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    const val jsoup = "org.jsoup:jsoup:${Versions.jsoup}"
    const val browser = "androidx.browser:browser:${Versions.browser}"
    const val protoDataStore = "androidx.datastore:datastore:${Versions.protoDataStore}"
    const val protoDataStoreJavaLite = "com.google.protobuf:protobuf-javalite:${Versions.protoDataStoreJavaLite}"

    private const val mockk = "io.mockk:mockk:${Versions.mockk}"
    private const val junit = "junit:junit:${Versions.junit}"
    private const val roomTest = "androidx.room:room-testing:${Versions.room}"
    private const val junitExt = "androidx.test.ext:junit:${Versions.androidxTestJUnit}"
    private const val composeUiTest = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    private const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    private const val hiltAndroidTest = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    private const val hiltCompilerTest = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

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
        "implementation"(moshi)
        "kapt"(moshiCodegen)
    }

    fun DependencyHandlerScope.testImplementationDependencies() {
        "testImplementation"(mockk)
        "testImplementation"(junit)
        "testImplementation"(roomTest)
        "testImplementation"(robolectric)
        "testImplementation"(composeUiTest)
        "testImplementation"(hiltAndroidTest)
        "kaptTest"(hiltCompilerTest)
    }

    fun DependencyHandlerScope.androidTestImplementationDependencies() {
        "androidTestImplementation"(junitExt)
        "androidTestImplementation"(composeUiTest)
        "androidTestImplementation"(hiltAndroidTest)
        "kaptAndroidTest"(hiltCompilerTest)
    }
}
