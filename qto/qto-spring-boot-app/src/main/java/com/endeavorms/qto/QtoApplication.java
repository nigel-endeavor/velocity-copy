package com.endeavorms.qto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

/**
 * QTO Spring Boot Application Entry Point
 *
 * <p>This is the main Spring Boot application class for the Quantum Task Orchestrator (QTO) platform.
 * Built with Spring Boot and Gradle for Endeavor Managed Services.</p>
 *
 * @see <a href="https://github.com/Endeavor-Managed-Services/velocity">Project Repository</a>
 * @version 1.18.1-SNAPSHOT
 * @since Spring Boot 3.2.2
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.endeavorms.qto", "com.endeavorms.velocity.qto"})
@EnableJms
public class QtoApplication {

    /**
     * Main entry point for the QTO Spring Boot application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(QtoApplication.class, args);
    }

}
