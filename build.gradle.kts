plugins {
    id("java")
    id("org.springframework.boot") version ("3.1.4")
    id("io.spring.dependency-management") version ("1.1.3")
}

group = "com.github.Noiseneks"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-test")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-actuator")

    // openapi
    implementation(group = "org.springdoc", name = "springdoc-openapi-starter-webmvc-ui", version = "2.6.0")
    implementation(group = "org.projectlombok", name = "lombok", version = "1.18.30")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}