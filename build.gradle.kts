plugins {
    application
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
}

application {
    mainClass.set("ServerKt")
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(files("libs/gplayapi.jar"))

    implementation(kotlin("cor"))

    val ktorVersion = "1.6.7"
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")

    implementation("io.lettuce:lettuce-core:6.1.6.RELEASE")

    implementation("ch.qos.logback:logback-classic:1.2.10")

    //Google Play Api
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("com.google.protobuf:protobuf-java:3.19.4")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.6.10")
}