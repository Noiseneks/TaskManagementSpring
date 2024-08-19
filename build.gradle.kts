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
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-data-jpa")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-security")

    // JWT
    implementation(group = "io.jsonwebtoken", name = "jjwt-api", version = "0.11.5")
    runtimeOnly(group = "io.jsonwebtoken", name = "jjwt-impl", version = "0.11.5")
    runtimeOnly(group = "io.jsonwebtoken", name = "jjwt-jackson", version = "0.11.5")

    implementation("org.mariadb.jdbc:mariadb-java-client:2.5.2")

    // openapi
    implementation(group = "org.springdoc", name = "springdoc-openapi-starter-webmvc-ui", version = "2.6.0")
    implementation("org.jetbrains:annotations:24.1.0")

    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}