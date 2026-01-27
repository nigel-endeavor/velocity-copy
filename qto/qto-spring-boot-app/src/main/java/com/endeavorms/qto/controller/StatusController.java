package com.endeavorms.qto.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Status endpoint for verifying QTO Spring Boot application is operational.
 *
 * <p>Provides basic application status information including version,
 * environment details, and timestamp.</p>
 *
 * @version 1.18.1-SNAPSHOT
 * @since Phase 1
 */
@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Value("${qto.version:unknown}")
    private String version;

    @Value("${qto.migration.phase:unknown}")
    private String migrationPhase;

    @Value("${spring.application.name:qto-application}")
    private String applicationName;

    /**
     * Get application status.
     *
     * @return ResponseEntity containing application status information
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("application", applicationName);
        status.put("version", version);
        status.put("status", "OPERATIONAL");
        status.put("phase", migrationPhase);
        status.put("timestamp", LocalDateTime.now().toString());
        status.put("message", "QTO Spring Boot application is running");

        return ResponseEntity.ok(status);
    }

    /**
     * Simple ping endpoint for connectivity verification.
     *
     * @return ResponseEntity with pong message
     */
    @GetMapping("/ping")
    public ResponseEntity<Map<String, String>> ping() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "pong");
        response.put("timestamp", LocalDateTime.now().toString());

        return ResponseEntity.ok(response);
    }
}
