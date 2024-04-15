plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    //Room Annotation 적용
    id("com.google.devtools.ksp")
    // firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.eunho.crossfitranker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.eunho.crossfitranker"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    implementation("androidx.camera:camera-view:1.3.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // recycler view
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //gson
    implementation("com.google.code.gson:gson:2.10.1")

    //MVVM
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.activity:activity-ktx:1.8.2")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //room
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
//    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")

    // flow binding
    val flowbinding_version = "1.2.0"
    implementation ("io.github.reactivecircus.flowbinding:flowbinding-android:${flowbinding_version}")
    implementation ("io.github.reactivecircus.flowbinding:flowbinding-activity:${flowbinding_version}")
    implementation ("io.github.reactivecircus.flowbinding:flowbinding-appcompat:${flowbinding_version}")
    implementation ("io.github.reactivecircus.flowbinding:flowbinding-core:${flowbinding_version}")
    implementation ("io.github.reactivecircus.flowbinding:flowbinding-drawerlayout:${flowbinding_version}")
    implementation ("io.github.reactivecircus.flowbinding:flowbinding-lifecycle:${flowbinding_version}")
    implementation ("io.github.reactivecircus.flowbinding:flowbinding-navigation:${flowbinding_version}")
    implementation ("io.github.reactivecircus.flowbinding:flowbinding-preference:${flowbinding_version}")
    implementation ("io.github.reactivecircus.flowbinding:flowbinding-recyclerview:${flowbinding_version}")
    implementation ("io.github.reactivecircus.flowbinding:flowbinding-swiperefreshlayout:${flowbinding_version}")
    implementation ("io.github.reactivecircus.flowbinding:flowbinding-viewpager:${flowbinding_version}")
    implementation ("io.github.reactivecircus.flowbinding:flowbinding-viewpager2:${flowbinding_version}")
    implementation ("io.github.reactivecircus.flowbinding:flowbinding-material:${flowbinding_version}")

    // navigation
    val nav_version = "2.7.7"
    //noinspection GradleDependency
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // lang3 - 공백 string 표기용
    implementation("org.apache.commons:commons-lang3:3.12.0")

    // viewModel implement
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    // CameraX
    implementation ("androidx.camera:camera-camera2:1.3.2")
    implementation ("androidx.camera:camera-lifecycle:1.3.2")
    implementation ("androidx.camera:camera-view:1.3.2")

    // ml kit
    implementation ("com.google.mlkit:pose-detection-accurate:18.0.0-beta4")
    implementation ("com.google.mlkit:pose-detection:18.0.0-beta4")


}