package com.endeavorms.qto.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for StatusController.
 *
 * Uses @WebMvcTest to test only the web layer without loading the full application context.
 * Tests verify endpoint behavior, response structure, and status codes.
 */
@WebMvcTest(StatusController.class)
@ActiveProfiles("test")
class StatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getStatus_ShouldReturnApplicationStatus() throws Exception {
        mockMvc.perform(get("/api/status"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.application").value("qto-application-test"))
                .andExpect(jsonPath("$.version").value("1.18.1-SNAPSHOT-TEST"))
                .andExpect(jsonPath("$.status").value("OPERATIONAL"))
                .andExpect(jsonPath("$.phase").value("1-foundation-test"))
                .andExpect(jsonPath("$.message").value("QTO Spring Boot application is running"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void getStatus_ShouldReturnValidJsonStructure() throws Exception {
        mockMvc.perform(get("/api/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.application").isString())
                .andExpect(jsonPath("$.version").isString())
                .andExpect(jsonPath("$.status").isString())
                .andExpect(jsonPath("$.phase").isString())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.timestamp").isString());
    }

    @Test
    void getStatus_ShouldContainAllRequiredFields() throws Exception {
        mockMvc.perform(get("/api/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.application").exists())
                .andExpect(jsonPath("$.version").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.phase").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void ping_ShouldReturnPongMessage() throws Exception {
        mockMvc.perform(get("/api/status/ping"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("pong"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void ping_ShouldReturnValidJsonStructure() throws Exception {
        mockMvc.perform(get("/api/status/ping"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.timestamp").isString());
    }

    @Test
    void ping_ShouldHaveExactlyTwoFields() throws Exception {
        mockMvc.perform(get("/api/status/ping"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));
    }

    @Test
    void ping_TimestampShouldBeIso8601Format() throws Exception {
        mockMvc.perform(get("/api/status/ping"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timestamp").value(matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\..*")));
    }

    @Test
    void getStatus_TimestampShouldBeIso8601Format() throws Exception {
        mockMvc.perform(get("/api/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timestamp").value(matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\..*")));
    }

    @Test
    void getStatus_ShouldAcceptMultipleRequests() throws Exception {
        // Verify endpoint is idempotent and can handle multiple requests
        for (int i = 0; i < 3; i++) {
            mockMvc.perform(get("/api/status"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("OPERATIONAL"));
        }
    }

    @Test
    void ping_ShouldAcceptMultipleRequests() throws Exception {
        // Verify ping endpoint can handle rapid requests
        for (int i = 0; i < 5; i++) {
            mockMvc.perform(get("/api/status/ping"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("pong"));
        }
    }

    @Test
    void invalidEndpoint_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/status/invalid"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getStatus_WithAcceptHeader_ShouldReturnJson() throws Exception {
        mockMvc.perform(get("/api/status")
                        .header("Accept", "application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }
}
