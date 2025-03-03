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
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")  // For API requests
    implementation ("org.json:json:20210307") // For handling JSON responses
}

//dependencies {
//    implementation("com.google.cloud:google-cloud-dialogflow:0.121.0")
//    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
//}
//
//dependencies {
//    implementation ("com.google.auth:google-auth-library-oauth2-http:1.17.0")
//    implementation ("com.squareup.okhttp3:okhttp:4.9.3'  // For making HTTP requests")
//}

