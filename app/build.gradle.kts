import org.gradle.kotlin.dsl.implementation


plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.android.application")
    id("com.google.gms.google-services") // Must be last plugin
    id("org.jetbrains.kotlin.kapt") version "1.9.23"
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

    buildFeatures {
        viewBinding = true
        compose = true
    }



    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
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
    implementation(libs.firebase.firestore)

    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)

    implementation("androidx.navigation:navigation-compose:2.9.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation ("com.google.android.material:material:1.9.0")
    implementation("androidx.recyclerview:recyclerview:1.4.0")

    implementation ("com.github.bumptech.glide:glide:4.16.0")
    kapt ("com.github.bumptech.glide:compiler:4.16.0")

        // Compose
        implementation ("androidx.activity:activity-compose:1.7.2")
        implementation ("androidx.compose.material3:material3:1.1.2")
        implementation ("androidx.compose.material:material:1.5.4")





        // Coil
        implementation ("io.coil-kt:coil-compose:2.4.0")

        // Coroutines
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

    implementation ("androidx.compose.material:material:1.5.4")
// Firebase BoM - use this to avoid specifying individual versions
    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    implementation("io.coil-kt:coil-compose:2.5.0")


// Firebase products (no version needed when using BoM)
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-analytics")

    // ARCore & Sceneform
    implementation("com.google.ar:core:1.49.0")
   // implementation("com.google.ar.sceneform:filament-android:1.17.1")
    implementation("com.gorisse.thomas.sceneform:sceneform:1.21.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.10.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0")


    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

}
