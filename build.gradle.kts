plugins {
    kotlin("jvm") version "1.4.32"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.23.1")
}

tasks {
    test {
        useJUnitPlatform()
    }
}