plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(Dependencies.kotlinGradlePlugin)
    implementation(Dependencies.androidGradlePlugin)
    implementation(Dependencies.hiltGradlePlugin)
    implementation(kotlin("script-runtime"))
}

kotlin {
    // Add PluginDependency to compilation, so it will become available in main project
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}
