plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.room)
    kotlin("plugin.serialization") version "2.1.0"
}

android {
    val versionMajor = 1
    val versionMinor = 14
    val versionPatch = 4
    val appName = "CNote"
    val appVersionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
    val appVersionName = "$versionMajor.$versionMinor.$versionPatch"

    defaultConfig {
        applicationId = "com.baratali.cnote"
        minSdk = 26
        targetSdk = 35
        compileSdk = 35
        namespace = "com.baratali.cnote"
        versionCode = appVersionCode
        versionName = appVersionName

        testInstrumentationRunner = "com.baratali.cnote.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file(rootProject.file(properties["CN_NOTE_STORE_FILE"] ?: ""))
            storePassword = properties["CN_NOTE_STORE_PASSWORD"] as String
            keyAlias = properties["CN_NOTE_KEY_ALIAS"] as String
            keyPassword = properties["CN_NOTE_KEY_PASSWORD"] as String
            enableV3Signing = true
            enableV4Signing = true
        }
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = ".d"
            resValue("string", "app_name", "Cnote debug")
        }
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
    applicationVariants.all {
        outputs.all {
            val outputImpl = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            val buildType = name.split("-")[0]
            outputImpl.outputFileName =
                "${appName}-${buildType}-v${appVersionName}-c${appVersionCode}.apk"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    room {
        schemaDirectory("$projectDir/schemas/")
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)

    // Compose dependencies
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.navigation.compose)
    implementation(libs.androidx.material.icons.extended)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core.v171)
    implementation(libs.coroutines.android)

    //Dagger - Hilt
    implementation(libs.hilt.android)
    ksp(libs.dagger.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.kotlinx.serialization)

    //Data Store
    implementation(libs.datastore.preferences)

    //gson
    implementation(libs.retrofit.converter.gson)
    implementation(libs.gson.extras)

    //Work Manager with Coroutines
    implementation(libs.work)
    implementation(libs.androidx.hilt.work)
    ksp(libs.hilt.compiler)

    // Local unit tests
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.junit4)
    testImplementation(libs.arch.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.mockwebserver)
    debugImplementation(libs.androidx.ui.test.manifest)

    //biometric
    implementation(libs.biometric)

    // Instrumentation tests
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.dagger.hilt.compiler)
    androidTestImplementation(libs.junit4)
    androidTestImplementation(libs.arch.core.testing)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.core.ktx)
    androidTestImplementation(libs.mockwebserver)
    androidTestImplementation(libs.androidx.test.runner)
}