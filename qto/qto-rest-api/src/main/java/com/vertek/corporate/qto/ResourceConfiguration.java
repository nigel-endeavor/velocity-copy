package com.vertek.corporate.qto;

import javax.ejb.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * REST API Configuration.
 * @author rcasey
 * @since 1.0.0
 */
@Singleton
@ApplicationPath("/api")
public class ResourceConfiguration extends Application {
}
