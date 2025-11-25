plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("kotlin-parcelize")    // <-- obrigatório aqui
}

android {
    namespace = "com.example.onboarding"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
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

    buildFeatures {
        compose = true
    }
}

dependencies {
    val koin = "3.5.6"
    val navVersion = "2.7.2"

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.activity:activity-compose:1.9.2")

    // Compose BOM moderno
    implementation(platform("androidx.compose:compose-bom:2024.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")

    implementation("androidx.compose.material:material:1.2.0")
    implementation("androidx.compose.material3:material3:1.2.0-alpha02")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Koin main
    implementation("io.insert-koin:koin-core:${koin}")
    // Koin Android
    implementation("io.insert-koin:koin-android:${koin}")
    // Koin + Jetpack Compose
    implementation("io.insert-koin:koin-androidx-compose:${koin}")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:$navVersion")

    implementation("com.google.accompanist:accompanist-pager:0.31.3-beta")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.36.0")
}
