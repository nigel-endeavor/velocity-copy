package com.endeavorms.qto.config;

import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.PlatformDatabase;
import com.endeavorms.velocity.qto.common.TenantSubjectJpaDao;
import com.endeavorms.velocity.qto.common.StubTenantSubjectJpaDao;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Proxy;

/**
 * Provides stub EntityManager and DAO beans for tests when database is disabled.
 * Satisfies @QtoDatabase, @PlatformDatabase, and TenantSubjectJpaDao dependencies.
 */
@Configuration
@ActiveProfiles("test")
public class TestDatabaseConfig {

    @Bean
    @QtoDatabase
    EntityManager qtoEntityManager() {
        return createStubEntityManager();
    }

    @Bean
    @PlatformDatabase
    EntityManager platformEntityManager() {
        return createStubEntityManager();
    }

    @Bean
    @Primary
    TenantSubjectJpaDao tenantSubjectJpaDao(
            @PlatformDatabase EntityManager platformEntityManager) {
        return new StubTenantSubjectJpaDao(platformEntityManager);
    }

    private static EntityManager createStubEntityManager() {
        return (EntityManager) Proxy.newProxyInstance(
            EntityManager.class.getClassLoader(),
            new Class<?>[]{EntityManager.class},
            (proxy, method, args) -> {
                Class<?> rt = method.getReturnType();
                if (rt == boolean.class) return false;
                if (rt == int.class) return 0;
                if (rt == long.class) return 0L;
                return null;
            }
        );
    }
}
