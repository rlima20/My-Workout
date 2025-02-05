// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        // Make sure that you have the Google services Gradle plugin dependency
        classpath("com.google.gms:google-services:4.3.15")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id ("com.android.application") version ("7.4.0") apply false
    id ("com.android.library") version ("7.4.0") apply false
    id ("org.jetbrains.kotlin.android") version ("1.8.10") apply false
}