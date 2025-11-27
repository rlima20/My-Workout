plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // OBRIGATÓRIO NO KOTLIN 2.0+
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp") version "2.0.21-1.0.25"
}

android {
    namespace = "com.example.myworkout"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myworkout"
        minSdk = 25
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
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

    packagingOptions {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

tasks.register<JacocoReport>("jacocoTestReport") {
    group = "verification"
    description = "Task to generate JaCoCo code coverage report."

    dependsOn("testDebugUnitTest", "createDebugCoverageReport")

    val debugTree = fileTree("${buildDir}/intermediates/classes/debug")
    val mainSrc = "${project.projectDir}/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))

    executionData.setFrom(fileTree(buildDir).apply {
        include("jacoco/testDebugUnitTest.exec")
        include("outputs/code-coverage/connected/*coverage.ec")
    })
}

dependencies {
    val koin = "3.5.6"
    val navVersion = "2.7.2"
    val roomVersion = "2.6.1"


    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.activity:activity-compose:1.9.2")

    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material:1.2.0")
    implementation("androidx.compose.material3:material3:1.2.0-alpha02")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Koin
    // Koin main
    implementation("io.insert-koin:koin-core:${koin}")
    // Koin Android
    implementation("io.insert-koin:koin-android:${koin}")
    // Koin + Jetpack Compose
    implementation("io.insert-koin:koin-androidx-compose:${koin}")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")

    // Google Fonts
    implementation("androidx.compose.ui:ui-text-google-fonts")

    // Tests (mantidos)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")



    implementation(project(":onboarding"))
}