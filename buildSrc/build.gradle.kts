plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(GradlePluginDependency.kotlinPlugin)
    implementation(GradlePluginDependency.androidPlugin)
    implementation(GradlePluginDependency.hiltPlugin)
    implementation(kotlin("script-runtime"))
}

kotlin {
    // Add PluginDependency to compilation, so it will become available in main project
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}