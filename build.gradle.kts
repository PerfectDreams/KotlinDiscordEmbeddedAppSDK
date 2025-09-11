plugins {
    kotlin("multiplatform") version "2.2.0"
    id("org.jetbrains.kotlin.plugin.js-plain-objects") version "2.2.0"
    id("maven-publish")
}

group = "net.perfectdreams.discordembeddedappsdk"
version = "0.0.1"

repositories {
    mavenCentral()
}

kotlin {
    js {
        browser()
        binaries.executable()

        compilerOptions {
            // Enable ES6
            target = "es2015"
            useEsClasses = true
        }
    }

    sourceSets {
        jsMain {
            dependencies {
                implementation(npm("@discord/embedded-app-sdk", "2.1.0"))
                implementation("org.jetbrains.kotlin-wrappers:kotlin-js:2025.8.1")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-web:2025.8.1")
            }
        }
    }
}

publishing {
    repositories {
        maven {
            name = "PerfectDreams"
            url = uri("https://repo.perfectdreams.net/")
            credentials(PasswordCredentials::class)
        }
    }
}