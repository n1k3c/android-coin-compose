buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(GradlePluginDependency.androidPlugin)
        classpath(GradlePluginDependency.kotlinPlugin)
        classpath(GradlePluginDependency.hiltPlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.register("getVersion") {
    println("v${BuildConfig.versionName}(${BuildConfig.versionCode})")
}