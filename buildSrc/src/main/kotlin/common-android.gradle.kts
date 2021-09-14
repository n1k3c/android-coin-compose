plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = BuildConfig.compileSdk

    defaultConfig {
        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    compileOptions {
        sourceCompatibility = BuildConfig.javaVersion
        targetCompatibility = BuildConfig.javaVersion
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = BuildConfig.jvmTarget
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
    kapt {
        correctErrorTypes = true
        useBuildCache = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    flavorDimensions.add("version")
    productFlavors {
        create("development") {
            buildConfigField(
                "String",
                "COINS_BASE_URL",
                "\"https://api.coingecko.com/\""
            )
            dimension = "version"
        }
        create("staging") {
            buildConfigField(
                "String",
                "COINS_BASE_URL",
                "\"https://api.coingecko.com/\""
            )
            dimension = "version"
        }
        create("production") {
            buildConfigField(
                "String",
                "COINS_BASE_URL",
                "\"https://api.coingecko.com/\""
            )
            dimension = "version"
        }
    }
}
