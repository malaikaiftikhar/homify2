import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.homify"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.homify"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    implementation ("androidx.activity:activity-compose:1.10.1")
    implementation ("androidx.compose.material3:material3:1.3.1")
    implementation ("androidx.compose.foundation:foundation:1.6.0")
        implementation ("androidx.navigation:navigation-compose:2.7.5")
        implementation ("androidx.compose.ui:ui-tooling-preview:1.6.0")
        debugImplementation ("androidx.compose.ui:ui-tooling:1.6.0")
    implementation ("androidx.compose.runtime:runtime-livedata:1.4.0")
        implementation ("androidx.navigation:navigation-compose:2.5.0")

    implementation ("androidx.appcompat:appcompat:1.6.1")
        implementation ("androidx.core:core-ktx:1.12.0")
        // Add these if using Compose
        implementation ("androidx.activity:activity-compose:1.8.0")
    // Recommended to add Compose BOM
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}