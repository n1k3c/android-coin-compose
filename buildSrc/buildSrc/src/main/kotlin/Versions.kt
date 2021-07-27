import org.gradle.api.JavaVersion

object App {
    const val packageName = "com.nikec.coincompose"
    const val versionMajor = 1
    const val versionMinor = 0
    const val versionPatch = 0
}

object BuildConfig {
    const val minSdk = 23
    const val compileSdk = 30
    const val targetSdk = 30
    val javaVersion = JavaVersion.VERSION_1_8
    const val jvmTarget = "1.8"

    const val versionCode =
        App.versionMajor * 1000 + App.versionMinor * 100 + App.versionPatch * 10
    const val versionName =
        "${App.versionMajor}.${App.versionMinor}.${App.versionPatch}"
}

object Versions {
    const val kotlin = "1.5.10"
    const val androidBuildTools = "7.1.0-alpha02"
    const val compose = "1.0.0-rc02"
    const val composeActivity = "1.3.0-rc02"
    const val composeViewModel = "1.0.0-alpha07"
    const val androidxCore = "1.3.2"
    const val androidxAppcompat = "1.2.0"
    const val androidxLifecycleRuntime = "2.4.0-alpha01"
    const val androidxTestJUnit = "1.1.2"
    const val material = "1.3.0"
    const val hilt = "2.37"
    const val hiltCompose = "1.0.0-alpha02"
    const val coroutines = "1.4.3"
    const val navigation = "2.4.0-alpha03"
    const val paging = "1.0.0-alpha10"
    const val room = "2.3.0"
    const val okhttp = "4.9.1"
    const val retrofit = "2.9.0"
    const val timberkt = "1.5.1"
    const val mockk = "1.10.6"
    const val junit = "4.13"
    const val robolectric = "4.4"
}
