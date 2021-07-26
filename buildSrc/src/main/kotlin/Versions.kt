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
