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
    compileSdk = 30

    defaultConfig {
        applicationId = "com.nikec.coincompose"
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

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
    flavorDimensions("version")
    productFlavors {
        create("development") {
            versionNameSuffix = "-development"
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://service.com/api/\""
            )
            dimension = "version"
        }
        create("staging") {
            versionNameSuffix = "-staging"
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://service.com/api/\""
            )
            dimension = "version"
        }
        create("production") {
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://service.com/api/\""
            )
            dimension = "version"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
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
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.compose)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.kotlin.coroutines.test)

    implementation(libs.okhttp.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit.retrofit)
    implementation(libs.retrofit.moshi)


    implementation(libs.timberkt)

    testImplementation(libs.mockk)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
    androidTestImplementation(libs.androidx.compose.ui.test)
}
