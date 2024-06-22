// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Root build.gradle file

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:7.4.2") // Make sure to use the appropriate version for your project
        classpath ("com.google.gms:google-services:4.3.15") // Ensure you have the correct version
    }
}






plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}
