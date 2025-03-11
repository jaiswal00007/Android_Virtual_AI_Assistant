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
    implementation(libs.lottie)
}
dependencies {
    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
    implementation ("com.squareup.okhttp3:okhttp:4.11.0")  // For API requests
    implementation ("org.json:json:20210307") // For handling JSON responses
}

dependencies {

    val composeBom = platform("androidx.compose:compose-bom:2025.02.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Choose one of the following:
    // Material Design 3
    implementation("androidx.compose.material3:material3")
    // or Material Design 2
    implementation("androidx.compose.material:material")
    // or skip Material Design and build directly on top of foundational components
    implementation("androidx.compose.foundation:foundation")
    // or only import the main APIs for the underlying toolkit systems,
    // such as input and measurement/layout
    implementation("androidx.compose.ui:ui")

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Optional - Included automatically by material, only add when you need
    // the icons but not the material library (e.g. when using Material3 or a
    // custom design system based on Foundation)
    implementation("androidx.compose.material:material-icons-core")
    // Optional - Add full set of material icons
    implementation("androidx.compose.material:material-icons-extended")
    // Optional - Add window size utils
    implementation("androidx.compose.material3.adaptive:adaptive")

    // Optional - Integration with activities
    implementation("androidx.activity:activity-compose:1.10.0")
    // Optional - Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5")
    // Optional - Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")
    // Optional - Integration with RxJava
    implementation("androidx.compose.runtime:runtime-rxjava2")

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
