plugins {
    java
    id("org.springframework.boot") version "3.2.2"
}


repositories {
    mavenCentral()
}


dependencies {
    implementation(project(":qto-core"))
    implementation(project(":qto-database"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework:spring-jms")
    implementation("org.springframework.boot:spring-boot-starter-artemis")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}
