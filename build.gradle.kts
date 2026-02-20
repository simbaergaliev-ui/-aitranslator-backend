plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.aitranslator"
version = "1.0.0"

application {
    mainClass.set("com.aitranslator.ApplicationKt")
}

repositories {
    mavenCentral()
}

val ktorVersion = "2.3.8"

dependencies {

    // KTOR SERVER
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-client-core:2.3.7")
    implementation("io.ktor:ktor-client-cio:2.3.7")

    // KTOR CLIENT (для Luma)
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")

    // JSON
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.4.14")
}

kotlin {
    jvmToolchain(17)
}
