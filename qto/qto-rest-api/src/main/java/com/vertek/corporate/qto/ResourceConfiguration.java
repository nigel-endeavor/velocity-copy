package com.vertek.corporate.qto;

import jakarta.ejb.Singleton;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * REST API Configuration.
 * @author rcasey
 * @since 1.0.0
 */
@Singleton
@ApplicationPath("/api")
public class ResourceConfiguration extends Application {
}
