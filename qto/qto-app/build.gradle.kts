plugins {
    java
    id("org.springframework.boot") version "3.2.2"
}

dependencies {
    implementation(project(":qto-core"))
    implementation(project(":qto-database"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework:spring-jms")

    runtimeOnly("org.postgresql:postgresql")
}
