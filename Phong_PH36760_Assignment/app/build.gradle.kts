plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.phong_assignment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.phong_assignment"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        dataBinding = true
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //binding
    implementation ("androidx.databinding:databinding-runtime:7.0.0")
    //animation
    implementation ("com.airbnb.android:lottie:6.3.0")
    //thư viện ảnh
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    //import thư viện  annotations @SerializedName và thu vien retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.google.code.gson:gson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

}