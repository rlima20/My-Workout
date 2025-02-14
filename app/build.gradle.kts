plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.myworkout"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.myworkout"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
//    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
//    implementation("androidx.compose.ui:ui-graphics")
//    androidTestImplementation(platform("androidx.compose:compose-bom:2024.04.01"))
    val composeUiVersion = "1.4.3"
//    debugImplementation("androidx.compose.ui:ui-test-manifest")
    val koin = "2.2.0"
    val navVersion = "2.5.3"
    val roomVersion = "2.5.1"

    // Main libraries
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.ui:ui:$composeUiVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")
    implementation("androidx.compose.material:material:1.2.0")
    implementation("androidx.compose.material3:material3:1.2.0-alpha02")

    // Testing libraries
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.3")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUiVersion")
    implementation("androidx.compose.ui:ui-test-manifest:1.4.1")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")
    testImplementation("io.kotlintest:kotlintest-assertions:3.4.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    testImplementation("org.robolectric:robolectric:3.7.1")
    implementation("org.robolectric:robolectric:4.10.1")
    implementation("androidx.test:core:1.5.0")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.1.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation("com.google.android.gms:play-services-measurement-api:21.2.2")

    // Material design
    implementation("com.google.android.material:material:1.4.+")

    // Coil
    implementation("io.coil-kt:coil-compose:2.3.0")

    // Koin for Kotlin apps
    implementation("io.insert-koin:koin-core:$koin")
    implementation("io.insert-koin:koin-androidx-viewmodel:2.2.2")

    // Mockk
    testImplementation("io.mockk:mockk-android:1.12.3")
    testImplementation("io.mockk:mockk-agent-jvm:1.12.3")

    // Navigation
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // Room
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // Google fonts
    implementation("androidx.compose.ui:ui-text-google-fonts:1.4.3")
}