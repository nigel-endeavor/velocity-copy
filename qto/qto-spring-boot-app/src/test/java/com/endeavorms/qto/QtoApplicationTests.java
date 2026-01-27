package com.endeavorms.qto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for QTO Spring Boot Application.
 *
 * Uses @SpringBootTest to load the full application context and test end-to-end behavior.
 * Tests verify the application starts correctly and all components work together.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class QtoApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
        // Verify Spring context loads successfully
        assertThat(restTemplate).isNotNull();
    }

    @Test
    void applicationStarts() {
        // Verify application starts on a random port
        assertThat(port).isGreaterThan(0);
    }

    @Test
    void statusEndpoint_ReturnsOk() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/qto/api/status",
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void statusEndpoint_ContainsExpectedData() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/qto/api/status",
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String body = response.getBody();
        assertThat(body).isNotNull();

        @SuppressWarnings("unchecked")
        Map<String, Object> jsonBody = objectMapper.readValue(body, Map.class);
        assertThat(jsonBody.get("application")).isEqualTo("qto-application-test");
        assertThat(jsonBody.get("version")).isEqualTo("1.18.1-SNAPSHOT-TEST");
        assertThat(jsonBody.get("status")).isEqualTo("OPERATIONAL");
        assertThat(jsonBody.get("phase")).isEqualTo("1-foundation-test");
        assertThat(jsonBody.get("message")).isEqualTo("QTO Spring Boot application is running");
        assertThat(jsonBody.get("timestamp")).isNotNull();
    }

    @Test
    void pingEndpoint_ReturnsOk() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/qto/api/status/ping",
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void pingEndpoint_ReturnsPongMessage() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/qto/api/status/ping",
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String body = response.getBody();
        assertThat(body).isNotNull();

        @SuppressWarnings("unchecked")
        Map<String, Object> jsonBody = objectMapper.readValue(body, Map.class);
        assertThat(jsonBody.get("message")).isEqualTo("pong");
        assertThat(jsonBody.get("timestamp")).isNotNull();
    }

    @Test
    void healthEndpoint_ReturnsUp() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/qto/actuator/health",
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String body = response.getBody();
        assertThat(body).isNotNull();

        @SuppressWarnings("unchecked")
        Map<String, Object> jsonBody = objectMapper.readValue(body, Map.class);
        assertThat(jsonBody.get("status")).isEqualTo("UP");
    }

    @Test
    void invalidEndpoint_Returns404() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/qto/api/invalid",
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void multipleRequests_ShouldAllSucceed() {
        for (int i = 0; i < 5; i++) {
            ResponseEntity<String> response = restTemplate.getForEntity(
                    "http://localhost:" + port + "/qto/api/status/ping",
                    String.class
            );
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }
}
