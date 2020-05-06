plugins {
    java
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

repositories {
    jcenter()
}

val monetaVersion by extra { System.getProperty("monetaVersion") ?: "1.4" }

dependencies {
    implementation("org.javamoney:moneta:${monetaVersion}")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
