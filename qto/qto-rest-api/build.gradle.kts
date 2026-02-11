plugins {
    java
}

dependencies {
    implementation(project(":qto-core"))
    implementation(project(":qto-database"))

    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.2.2"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework:spring-jms")

    implementation("jakarta.ws.rs:jakarta.ws.rs-api:3.1.0")
    implementation("jakarta.ejb:jakarta.ejb-api:4.0.1")
    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")
    implementation("jakarta.jms:jakarta.jms-api:3.0.0")
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
    implementation("jakarta.interceptor:jakarta.interceptor-api:2.1.0")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")
    implementation("org.apache.poi:poi-ooxml:5.2.5")
    implementation("commons-fileupload:commons-fileupload:1.5")
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.jboss.resteasy:resteasy-core:6.2.10.Final")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-guava")
}
