package com.vertek.corporate.qto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * QTO Spring Boot Application Entry Point
 *
 * <p>This is the main Spring Boot application class for the Quantum Task Orchestrator (QTO) platform.
 * It replaces the WildFly/Java EE deployment model with a standalone Spring Boot application.</p>
 *
 * <p><strong>Migration Status:</strong> Phase 1 - Foundation</p>
 * <ul>
 *   <li>✓ Spring Boot application shell created</li>
 *   <li>⏳ Database configuration (Phase 2)</li>
 *   <li>⏳ JPA repositories (Phase 3)</li>
 *   <li>⏳ Service layer (Phase 4)</li>
 *   <li>⏳ REST API (Phase 5)</li>
 *   <li>⏳ Security (Phase 6)</li>
 * </ul>
 *
 * @see <a href="https://github.com/Endeavor-Managed-Services/velocity">Project Repository</a>
 * @version 1.18.1-SNAPSHOT
 * @since Spring Boot 3.2.2
 */
@SpringBootApplication
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
