package com.vertek.corporate.qto.common.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

/**
 * WebServlet for initializing the Quartz Scheduler without the need for configuration via web.xml.
 * Replaces the Quartz base class (javax.servlet) to be Jakarta EE 10 compatible.
 * NOTE: @WebServlet annotation disabled for local dev - Quartz 2.3.2 uses javax.transaction (incompatible with WildFly 27 Jakarta EE 10)
 * To re-enable, add @WebServlet annotation below and upgrade Quartz to a Jakarta EE 10 compatible version.
 * @author <a href="rconnolly@vertek.com">rconnolly@vertek.com</a>.
 * @since 1.14.0 - 6/21/14
 */
// @WebServlet(name = "QuartzInitializerServlet", loadOnStartup = 2, initParams = {
//         @WebInitParam(name = "shutdown-on-unload", value = "true"),
//         @WebInitParam(name = "config-file", value = "quartz.properties"),
//         @WebInitParam(name = "start-delay-seconds", value = "30")
// })
public class QuartzInitializerServlet extends HttpServlet {

    /** Key used to store the scheduler factory in the servlet context. */
    public static final String QUARTZ_FACTORY_KEY = "org.quartz.impl.StdSchedulerFactory.KEY";

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzInitializerServlet.class);

    /** Serial UID. */
    private static final long serialVersionUID = -2867639539737690384L;

    private boolean shutdownOnUnload = true;
    private Scheduler scheduler;

    @Override
    public void init(final ServletConfig cfg) throws ServletException {
        super.init(cfg);

        String shutdownPref = cfg.getInitParameter("shutdown-on-unload");
        if (shutdownPref != null) {
            shutdownOnUnload = Boolean.parseBoolean(shutdownPref);
        }

        String configFile = cfg.getInitParameter("config-file");
        String startDelayStr = cfg.getInitParameter("start-delay-seconds");
        int startDelay = 0;
        if (startDelayStr != null) {
            try { startDelay = Integer.parseInt(startDelayStr); } catch (NumberFormatException ignored) {}
        }

        ServletContext servletContext = cfg.getServletContext();

        try {
            StdSchedulerFactory factory;
            if (configFile != null) {
                factory = new StdSchedulerFactory(configFile);
            } else {
                factory = new StdSchedulerFactory();
            }

            scheduler = factory.getScheduler();
            servletContext.setAttribute(QUARTZ_FACTORY_KEY, factory);

            if (startDelay <= 0) {
                scheduler.start();
            } else {
                scheduler.startDelayed(startDelay);
            }

            String schedulerName = scheduler.getSchedulerName();
            ScheduledJobUtil.setSchedulerName(schedulerName);

            LOGGER.debug("Initializing Quartz scheduler with name " + schedulerName);

            String contextPath = servletContext.getContextPath();
            ScheduledJobUtil.setJndiPrefix("java:/global" + contextPath);
            ScheduledJobUtil.setContextPath(contextPath);

        } catch (SchedulerException e) {
            LOGGER.error("Error initializing Quartz scheduler: " + e.toString(), e);
        }
    }

    @Override
    public void destroy() {
        if (shutdownOnUnload && scheduler != null) {
            try {
                scheduler.shutdown();
                LOGGER.debug("Quartz scheduler shut down.");
            } catch (SchedulerException e) {
                LOGGER.error("Error shutting down Quartz scheduler: " + e.toString(), e);
            }
        }
    }

}
