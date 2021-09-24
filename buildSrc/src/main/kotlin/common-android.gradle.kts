import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

val secretsPropertiesFile = rootProject.file("secrets.properties")
val secretProperties = Properties()

if (secretsPropertiesFile.exists()) {
    secretProperties.load(FileInputStream(secretsPropertiesFile))
} else {
    secretProperties.setProperty(
        "news_api_key",
        System.getenv("news_api_key") ?: ""
    )
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
            buildConfigField("String", "NEWS_BASE_URL", "\"https://cryptopanic.com/\"")
            buildConfigField("String", "NEWS_API_KEY", "\"${secretProperties["news_api_key"]}\"")
            dimension = "version"
        }
        create("staging") {
            buildConfigField(
                "String",
                "COINS_BASE_URL",
                "\"https://api.coingecko.com/\""
            )
            buildConfigField("String", "NEWS_BASE_URL", "\"https://cryptopanic.com/\"")
            buildConfigField("String", "NEWS_API_KEY", "\"${secretProperties["news_api_key"]}\"")
            dimension = "version"
        }
        create("production") {
            buildConfigField(
                "String",
                "COINS_BASE_URL",
                "\"https://api.coingecko.com/\""
            )
            buildConfigField("String", "NEWS_BASE_URL", "\"https://cryptopanic.com/\"")
            buildConfigField("String", "NEWS_API_KEY", "\"${secretProperties["news_api_key"]}\"")
            dimension = "version"
        }
    }
}
