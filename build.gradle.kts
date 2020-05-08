
plugins {
    java
    id("org.springframework.boot") version "2.2.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

repositories {
    jcenter()
    mavenLocal()
}

val monetaVersion by extra { System.getProperty("monetaVersion") ?: "1.5-SNAPSHOT" }
val javaVersion by extra { System.getProperty("javaVersion") ?: "VERSION_11" }


dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.javamoney:moneta:${monetaVersion}")
}

java {
    sourceCompatibility = JavaVersion.valueOf(javaVersion)
    targetCompatibility = JavaVersion.valueOf(javaVersion)
}

tasks.withType<JavaCompile>().configureEach {
//    options.apply {
//        options.isIncremental = true
//        isFork = true
//        --add-exports java.base/jdk.internal.reflect=ALL-UNNAMED --add-exports java.base/jdk.internal.misc=ALL-UNNAMED
//        forkOptions.jvmArgs = listOf("--add-exports", "java.base/jdk.internal.misc=ALL-UNNAMED", "--add-exports", "java.base/jdk.internal.reflect=ALL-UNNAMED")
//    }
}
