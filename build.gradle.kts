plugins {
    id("com.android.application") version "8.13.0" apply false
    id("com.android.library") version "8.13.0" apply false

    // Kotlin 2.0.21 para todo o projeto
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false

    // Compose Compiler obrigatório no Kotlin 2.0+
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false

    id("jacoco")
}

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }

    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
    }
}
