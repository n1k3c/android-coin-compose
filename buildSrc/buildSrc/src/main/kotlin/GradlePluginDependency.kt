object GradlePluginVersion {
    const val kotlin = "1.5.10"
    const val androidBuildTools = "7.1.0-alpha02"
    const val hilt = "2.37"
}

object GradlePluginDependency {
    const val kotlinPlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${GradlePluginVersion.kotlin}"
    const val androidPlugin =
        "com.android.tools.build:gradle:${GradlePluginVersion.androidBuildTools}"
    const val hiltPlugin =
        "com.google.dagger:hilt-android-gradle-plugin:${GradlePluginVersion.hilt}"
}
