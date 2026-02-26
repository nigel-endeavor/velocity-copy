package com.endeavorms.velocity.qto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

/**
 * QTO Spring Boot Application.
 */
@SpringBootApplication
@EnableJms
@ComponentScan(basePackages = "com.endeavorms.velocity.qto")
public class QtoApplication {

    public static void main(String[] args) {
        SpringApplication.run(QtoApplication.class, args);
    }
}
