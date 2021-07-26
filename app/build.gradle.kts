import Dependencies.androidTestImplementationDependencies
import Dependencies.androidxDependencies
import Dependencies.composeDependencies
import Dependencies.coroutinesDependencies
import Dependencies.retrofitDependencies
import Dependencies.testImplementationDependencies
import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

val secretsPropertiesFile = rootProject.file("secrets.properties")
val secretProperties = Properties()

if (secretsPropertiesFile.exists()) {
    secretProperties.load(FileInputStream(secretsPropertiesFile))
} else {
    secretProperties.setProperty(
        "signing_keystore_password",
        System.getenv("signing_keystore_password") ?: ""
    )
    secretProperties.setProperty(
        "signing_key_password",
        System.getenv("signing_key_password") ?: ""
    )
    secretProperties.setProperty(
        "signing_key_alias",
        System.getenv("signing_key_alias") ?: ""
    )
}

android {
    compileSdk = BuildConfig.compileSdk

    defaultConfig {
        applicationId = App.packageName
        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk
        versionCode = BuildConfig.versionCode
        versionName = BuildConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    signingConfigs {
        create("release") {
            storeFile = file("../coin-compose.jks")
            storePassword = "${secretProperties["signing_keystore_password"]}"
            keyAlias = "${secretProperties["signing_key_alias"]}"
            keyPassword = "${secretProperties["signing_key_password"]}"
        }
    }
    flavorDimensions.add("version")
    productFlavors {
        create("development") {
            versionNameSuffix = "-development"
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://api.coingecko.com/\""
            )
            dimension = "version"
        }
        create("staging") {
            versionNameSuffix = "-staging"
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://api.coingecko.com/\""
            )
            dimension = "version"
        }
        create("production") {
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://api.coingecko.com/\""
            )
            dimension = "version"
        }
    }
    compileOptions {
        sourceCompatibility = BuildConfig.javaVersion
        targetCompatibility = BuildConfig.javaVersion
    }
    kotlinOptions {
        jvmTarget = BuildConfig.jvmTarget
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
    packagingOptions {
        resources.excludes.add("META-INF/*.version")
        resources.excludes.add("META-INF/proguard/*")
        resources.excludes.add("/*.properties")
        resources.excludes.add("META-INF/*.properties")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
    hilt {
        enableExperimentalClasspathAggregation = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":features:coins"))

    androidxDependencies()
    composeDependencies()
    implementation(Dependencies.composeNavigation)

    implementation(Dependencies.hiltCompose)
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)

    coroutinesDependencies()
    retrofitDependencies()

    implementation(Dependencies.roomRuntime)
    implementation(Dependencies.roomKtx)
    annotationProcessor(Dependencies.roomCompiler)
    kapt(Dependencies.roomCompiler)

    implementation(Dependencies.timberkt)

    testImplementationDependencies()
    androidTestImplementationDependencies()
}
