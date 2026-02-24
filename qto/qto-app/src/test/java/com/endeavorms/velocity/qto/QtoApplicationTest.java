package com.endeavorms.velocity.qto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Smoke test: verifies the application compiles and the main class is loadable.
 */
class QtoApplicationTest {

    @Test
    void applicationClassExists() {
        assertNotNull(QtoApplication.class);
    }
}
