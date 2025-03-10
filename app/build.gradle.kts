plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.test2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.test2"
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
        compose= true  // Enable Compose feature
        mlModelBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14" // Specify Compose compiler version (use latest)
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
//    val lottieVersion = "6.6.2"
//
//    implementation("com.airbnb.android:lottie:$lottieVersion")

}
dependencies {
    implementation("com.airbnb.android:lottie:6.6.2")
}
dependencies {
    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")  // For API requests
    implementation ("org.json:json:20210307") // For handling JSON responses
}
dependencies {
    // Compose UI and Material3 dependencies
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.material3:material3:1.2.0")  // Ensure consistency
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0")
    implementation("androidx.navigation:navigation-compose:2.7.5") // Update to match material3 version

    // Activity Compose
    implementation("androidx.activity:activity-compose:1.8.2")

    // ViewModel for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // Lottie animations (optional for typing indicator)
    implementation("com.airbnb.android:lottie-compose:6.1.0")

    // Coroutine dependency
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("androidx.compose.foundation:foundation:1.5.4")
    implementation ("androidx.compose.animation:animation:1.5.4")

}
dependencies {
    implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.4.0-alpha09")
}
dependencies {
    implementation ("androidx.compose.material:material-icons-extended:1.4.3")
}






//dependencies {dependencies {
//    implementation 'org.tensorflow:tensorflow-lite:2.11.0'
//    implementation 'org.tensorflow:tensorflow-lite-support:2.11.0'
//}
//    implementation("com.google.cloud:google-cloud-dialogflow:0.121.0")
//    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
//}
//
//dependencies {
//    implementation ("com.google.auth:google-auth-library-oauth2-http:1.17.0")
//    implementation ("com.squareup.okhttp3:okhttp:4.9.3'  // For making HTTP requests")
//}
