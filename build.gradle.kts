buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        val libs = project.extensions.getByType<VersionCatalogsExtension>()
            .named("libs") as org.gradle.accessors.dm.LibrariesForLibs

        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.hilt.gradle.plugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}