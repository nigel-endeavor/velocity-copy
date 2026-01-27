package com.endeavorms.qto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for application configuration properties.
 *
 * Verifies that configuration values are loaded correctly from application-test.yml.
 */
@SpringBootTest
@ActiveProfiles("test")
class ConfigurationPropertiesTest {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${qto.version}")
    private String version;

    @Value("${qto.migration.phase}")
    private String migrationPhase;

    @Value("${qto.migration.description}")
    private String migrationDescription;

    @Value("${server.port}")
    private int serverPort;

    @Value("${management.endpoints.web.base-path}")
    private String actuatorBasePath;

    @Test
    void applicationName_ShouldBeConfigured() {
        assertThat(applicationName).isEqualTo("qto-application-test");
    }

    @Test
    void version_ShouldBeConfigured() {
        assertThat(version).isEqualTo("1.18.1-SNAPSHOT-TEST");
    }

    @Test
    void migrationPhase_ShouldBeConfigured() {
        assertThat(migrationPhase).isEqualTo("1-foundation-test");
    }

    @Test
    void migrationDescription_ShouldBeConfigured() {
        assertThat(migrationDescription).isEqualTo("Test profile configuration");
    }

    @Test
    void serverPort_ShouldBeZeroForRandomPort() {
        assertThat(serverPort).isEqualTo(0);
    }

    @Test
    void actuatorBasePath_ShouldBeConfigured() {
        assertThat(actuatorBasePath).isEqualTo("/actuator");
    }

    @Test
    void allConfigurationValues_ShouldNotBeNull() {
        assertThat(applicationName).isNotNull();
        assertThat(version).isNotNull();
        assertThat(migrationPhase).isNotNull();
        assertThat(migrationDescription).isNotNull();
        assertThat(actuatorBasePath).isNotNull();
    }

    @Test
    void allConfigurationValues_ShouldNotBeEmpty() {
        assertThat(applicationName).isNotEmpty();
        assertThat(version).isNotEmpty();
        assertThat(migrationPhase).isNotEmpty();
        assertThat(migrationDescription).isNotEmpty();
        assertThat(actuatorBasePath).isNotEmpty();
    }
}
