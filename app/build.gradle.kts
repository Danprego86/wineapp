plugins {
    id(libs.plugins.androidApplication.get().pluginId)
    id(libs.plugins.jetbrainsKotlinAndroid.get().pluginId)
    id(libs.plugins.kapt.get().pluginId)
}

android {
    namespace = "com.example.wineapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.wineapp"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    viewBinding {
        enable = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    //swipe
    implementation(libs.androidx.swiperefreshlayout)

    //Glide
    implementation(libs.glide)
    kapt ("android.arch.lifecycle:compiler:1.0.0")
    kapt ("com.github.bumptech.glide:compiler:4.14.2")
    //implementation(libs.ksp)

    //retrofit
    implementation(libs.retrofit)

    //Gson
    implementation(libs.converter.gson)
    implementation(libs.gson)

    //Room
    implementation(libs.room.runtime)
    kapt ("androidx.room:room-compiler:2.5.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}