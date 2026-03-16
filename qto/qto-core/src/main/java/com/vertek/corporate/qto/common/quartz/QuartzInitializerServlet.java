package com.vertek.corporate.qto.common.quartz;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebServlet for initilizing the Quartz Scheduler without the need for configuration via web.xml.
 * @author <a href="rconnolly@vertek.com">rconnolly@vertek.com</a>.
 * @since 1.14.0 - 6/21/14
 */
@WebServlet(name = "QuartzInitializerServlet", loadOnStartup = 2, initParams = {
        @WebInitParam(name = "shutdown-on-unload", value = "true"),
        @WebInitParam(name = "config-file", value = "quartz.properties"),
        @WebInitParam(name = "start-delay-seconds", value = "30")
})
public class QuartzInitializerServlet extends org.quartz.ee.servlet.QuartzInitializerServlet {

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzInitializerServlet.class);

    /** Serial UID. */
    private static final long serialVersionUID = -2867639539737690384L;

    @Override
    public void init(final ServletConfig cfg) throws ServletException {

        // Local dev: skip Quartz initialization if disabled via system property
        if ("true".equalsIgnoreCase(System.getProperty("quartz.disabled"))) {
            LOGGER.info("Quartz scheduler disabled via quartz.disabled system property - skipping initialization");
            return;
        }

        super.init(cfg);

        ServletContext servletContext = cfg.getServletContext();

        StdSchedulerFactory factory = (StdSchedulerFactory) servletContext.getAttribute(QUARTZ_FACTORY_KEY);

        try {

            // Get the scheduler instance name that was specified in the props file and give it to
            // ScheduledJobUtil for reference.

            String schedulerName = factory.getScheduler().getSchedulerName();
            ScheduledJobUtil.setSchedulerName(schedulerName);

            LOGGER.debug("Initializing Quartz scheduler with name " + schedulerName);

            // Quartz jobs that lookup EJB's must use JNDI names that include the application name.  The application
            // name depends on the deployment, so pull the name from the servlet context.
            // For Quartz, JNDI names for EJB must be in the form:  java:/global/<app-name>/<bean-name>
            // e.g., java:/global/quoting/QuoteRouteManager
            // Because Quartz job objects are not created by the container, the java:module and java:app namespaces
            // will not work.

            String contextPath = servletContext.getContextPath();
            ScheduledJobUtil.setJndiPrefix("java:/global" + contextPath);
            ScheduledJobUtil.setContextPath(contextPath);

        } catch (SchedulerException e) {
            // todo: throw exception here?
            LOGGER.error(e.toString());
        }
    }

}
