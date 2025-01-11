plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.shoppinglist"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.shoppinglist"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.example.shoppinglist.HiltTestRunner"
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
    buildFeatures{
        viewBinding = true
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
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //room database
    implementation(libs.androidx.room.runtime)
    implementation(libs.room.coroutines)
    ksp(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.compiler)


    //coroutines
    implementation(libs.kotlinx.coroutines.android)
    androidTestImplementation(libs.kotlinx.coroutines.test)

    //truth library
    androidTestImplementation (libs.truth)
    testImplementation (libs.truth)

    //android testing
    androidTestImplementation (libs.androidx.core.testing)
    testImplementation (libs.androidx.core.testing)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // For hilt Implementation
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // For instrumentation tests
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)
    debugImplementation(libs.androidx.fragment.testing)

    // For local unit tests
    testImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.compiler)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.extensions)
    // LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)





}