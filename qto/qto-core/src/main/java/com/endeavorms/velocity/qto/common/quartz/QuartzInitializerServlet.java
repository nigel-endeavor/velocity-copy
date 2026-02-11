package com.endeavorms.velocity.qto.common.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * Servlet for initializing Quartz Scheduler. Adapted from quartz-ee for jakarta.servlet.
 * Note: Quartz EE uses javax.servlet; this is a Spring Boot/Jakarta-friendly version.
 */
@WebServlet(name = "QuartzInitializerServlet", loadOnStartup = 2, initParams = {
        @WebInitParam(name = "shutdown-on-unload", value = "true"),
        @WebInitParam(name = "config-file", value = "quartz.properties"),
        @WebInitParam(name = "start-delay-seconds", value = "30")
})
public class QuartzInitializerServlet extends GenericServlet {

    public static final String QUARTZ_FACTORY_KEY = "org.quartz.impl.StdSchedulerFactory.KEY";

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzInitializerServlet.class);
    private static final long serialVersionUID = -2867639539737690384L;

    @Override
    public void init(final ServletConfig cfg) throws ServletException {
        super.init(cfg);
        ServletContext servletContext = cfg.getServletContext();

        try {
            StdSchedulerFactory factory = new StdSchedulerFactory(cfg.getInitParameter("config-file"));
            factory.initialize();
            Scheduler scheduler = factory.getScheduler();
            servletContext.setAttribute(QUARTZ_FACTORY_KEY, factory);

            String schedulerName = scheduler.getSchedulerName();
            ScheduledJobUtil.setSchedulerName(schedulerName);
            LOGGER.debug("Initializing Quartz scheduler with name {}", schedulerName);

            String contextPath = servletContext.getContextPath();
            ScheduledJobUtil.setJndiPrefix("java:/global" + contextPath);
            ScheduledJobUtil.setContextPath(contextPath);

            String delayParam = cfg.getInitParameter("start-delay-seconds");
            if (delayParam != null) {
                int delay = Integer.parseInt(delayParam);
                if (delay > 0) {
                    scheduler.startDelayed(delay);
                } else {
                    scheduler.start();
                }
            } else {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            LOGGER.error("Quartz initialization failed", e);
            throw new ServletException(e);
        } catch (Exception e) {
            LOGGER.error("Quartz initialization failed", e);
            throw new ServletException(e);
        }
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) {
        // No-op - this servlet only does initialization
    }
}
