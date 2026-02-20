plugins {
    java
}

sourceSets["main"].java.srcDir("${layout.buildDirectory.get()}/generated/sources/annotationProcessor/java/main")

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.2.2")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-artemis")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.slf4j:slf4j-api")

    // Jakarta (replaces javax)
    implementation("jakarta.persistence:jakarta.persistence-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")
    implementation("jakarta.json:jakarta.json-api:2.1.2")
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")

    implementation("com.fasterxml.jackson.core:jackson-annotations:2.15.4")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.15.4")
    implementation("com.google.guava:guava:32.1.3-jre")

    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    implementation("com.querydsl:querydsl-core:5.0.0")

    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    implementation("commons-beanutils:commons-beanutils:1.9.4")
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")

    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.3")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.3")

    implementation("org.freemarker:freemarker:2.3.32")
    implementation("org.apache.poi:poi:5.2.5")
    implementation("org.apache.poi:poi-ooxml:5.2.5")
    implementation("commons-fileupload:commons-fileupload:1.5")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.1")
    implementation("joda-time:joda-time:2.12.5")
    implementation("org.apache.commons:commons-lang3:3.14.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-guava")
    implementation("org.apache.tika:tika-core:2.9.1")
    implementation("commons-io:commons-io:2.15.1")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.2.1")
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("com.nimbusds:nimbus-jose-jwt:9.37.3")
    implementation("jakarta.mail:jakarta.mail-api:2.1.2")
    implementation("com.sun.mail:jakarta.mail:2.0.1")
    implementation("net.minidev:json-smart:2.5.0")
    implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:4.0.1")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
    implementation("jakarta.interceptor:jakarta.interceptor-api:2.1.0")
    implementation("jakarta.websocket:jakarta.websocket-api:2.1.1")
    implementation("org.apache.tomcat.embed:tomcat-embed-websocket")
    implementation("org.apache.commons:commons-vfs2:2.9.0")
    // implementation("jakarta.jms:jakarta.jms-api:3.0.0")

    implementation("org.springframework:spring-jms")
    compileOnly("jakarta.jms:jakarta.jms-api")

    implementation("org.liquibase:liquibase-core:4.29.2")

    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
    options.annotationProcessorGeneratedSourcesDirectory = file("${layout.buildDirectory.get()}/generated/sources/annotationProcessor/java/main")
}

tasks.matching { it.name == "bootJar" }.configureEach {
    enabled = false
}
tasks.matching { it.name == "jar" }.configureEach {
    enabled = true
}