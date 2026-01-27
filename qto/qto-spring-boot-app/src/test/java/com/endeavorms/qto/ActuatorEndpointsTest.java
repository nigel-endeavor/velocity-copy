package com.endeavorms.qto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for Spring Boot Actuator endpoints.
 *
 * Verifies health, info, and metrics endpoints are properly configured and accessible.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ActuatorEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void actuatorBase_ShouldReturnLinks() throws Exception {
        mockMvc.perform(get("/actuator"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/vnd.spring-boot.actuator.v3+json"))
                .andExpect(jsonPath("$._links").exists())
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.health").exists())
                .andExpect(jsonPath("$._links.info").exists())
                .andExpect(jsonPath("$._links.metrics").exists());
    }

    @Test
    void healthEndpoint_ShouldReturnUp() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/vnd.spring-boot.actuator.v3+json"))
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void healthEndpoint_ShouldHaveStatus() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void infoEndpoint_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/actuator/info"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/vnd.spring-boot.actuator.v3+json"));
    }

    @Test
    void metricsEndpoint_ShouldReturnAvailableMetrics() throws Exception {
        mockMvc.perform(get("/actuator/metrics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/vnd.spring-boot.actuator.v3+json"))
                .andExpect(jsonPath("$.names").isArray())
                .andExpect(jsonPath("$.names", not(empty())));
    }

    @Test
    void metricsEndpoint_ShouldIncludeJvmMetrics() throws Exception {
        mockMvc.perform(get("/actuator/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.names", hasItem("jvm.memory.used")))
                .andExpect(jsonPath("$.names", hasItem("jvm.memory.max")))
                .andExpect(jsonPath("$.names", hasItem("jvm.threads.live")));
    }

    @Test
    void metricsEndpoint_JvmMemoryUsed_ShouldReturnDetails() throws Exception {
        mockMvc.perform(get("/actuator/metrics/jvm.memory.used"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("jvm.memory.used"))
                .andExpect(jsonPath("$.measurements").isArray())
                .andExpect(jsonPath("$.measurements[0].value").isNumber())
                .andExpect(jsonPath("$.availableTags").isArray());
    }

    @Test
    void metricsEndpoint_HttpServerRequests_ShouldExist() throws Exception {
        // Make a request first to generate metrics
        mockMvc.perform(get("/api/status"));

        // Now check if the metric exists
        mockMvc.perform(get("/actuator/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.names", hasItem("http.server.requests")));
    }

    @Test
    void metricsEndpoint_InvalidMetric_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/actuator/metrics/invalid.metric.name"))
                .andExpect(status().isNotFound());
    }

    @Test
    void actuatorEndpoints_ShouldUseCorrectBasePath() throws Exception {
        mockMvc.perform(get("/actuator"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").value(endsWith("/actuator")));
    }

    @Test
    void healthEndpoint_MultipleRequests_ShouldSucceed() throws Exception {
        for (int i = 0; i < 3; i++) {
            mockMvc.perform(get("/actuator/health"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("UP"));
        }
    }
}
