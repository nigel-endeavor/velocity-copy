plugins {
    java
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.2.2"))
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.liquibase:liquibase-core")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
}
