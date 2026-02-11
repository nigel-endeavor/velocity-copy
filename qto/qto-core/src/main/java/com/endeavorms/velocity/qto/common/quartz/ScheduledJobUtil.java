package com.endeavorms.velocity.qto.common.quartz;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Utility for handling scheduled jobs.
 * @author mmeehan
 * @since 1.0
 */
public class ScheduledJobUtil {

    /**
     * logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledJobUtil.class);

    /**
     * High priority Quartz trigger.
     */
    public static final int HIGH_PRIORITY = 10;

    /**
     * Medium priority Quartz trigger.
     */
    public static final int MEDIUM_PRIORITY = Trigger.DEFAULT_PRIORITY;

    /**
     * Low priority Quartz trigger.
     */
    public static final int LOW_PRIORITY = 1;

    /**
     * Prefix to user for jndi names.
     */
    protected static String jndiPrefix = null;

    /**
     * Web application context path.
     */
    protected static String contextPath = null;

    /**
     * Scheduler name. (configured externally)
     */
    protected static String schedulerName = null;

    public static String getJndiPrefix() {
        return jndiPrefix;
    }

    public static void setJndiPrefix(final String jndiPrefix) {
        ScheduledJobUtil.jndiPrefix = jndiPrefix;
    }

    public static String getContextPath() {
        return contextPath;
    }

    public static void setContextPath(final String contextPath) {
        ScheduledJobUtil.contextPath = contextPath;
    }

    public static void setSchedulerName(final String schedulerName) {
        ScheduledJobUtil.schedulerName = schedulerName;
    }

    public static String getSchedulerName() {
        return ScheduledJobUtil.schedulerName;
    }

    /**
     * Default constructor.
     */
    protected ScheduledJobUtil() {
        throw new UnsupportedOperationException("Utility classes cannot be instantiated.");
    }

    /**
     * Check that the scheduler is running, and if not, start it.
     * @param sched the scheduler to be checked.
     * @throws org.quartz.SchedulerException if there is a problem of some sort.
     */
    protected static void verifySchedulerRunning(final Scheduler sched) throws SchedulerException {
        if (!sched.isStarted()) {
            LOGGER.warn("Scheduler "
                    + sched.getSchedulerName()
                    + " was not started.  Starting now.");
            sched.start();
        }
    }

    /**
     * Properties which should be passed to the JNDI context.
     */
    protected static Hashtable<String, String> jndiProperties = new Hashtable<String, String>() {
        {
            put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.as.naming.InitialContextFactory");
        }
    };

    /**
     * Build the JNDI name that will work within the context of a quartz job.
     * @param cls the class type of the EJB for which to build a name.
     * @return a String JNDI name.
     */
    protected static String buildJndiName(final Class cls) {
        final String className = cls.getSimpleName();
        return ScheduledJobUtil.getJndiPrefix()
                + "/"
                + className;
    }

    /**
     * This method finds an EJB with a JNDI lookup.
     * @param clazz that class type of the EJB which is being requested
     * @param <T> the generic type of the EJB
     * @return the requested EJB
     * @throws javax.naming.NamingException thrown when something bad happens
     */
    @SuppressWarnings("unchecked")
    public static <T> T findEjb(final Class<T> clazz) throws NamingException {

        final String jndiName = buildJndiName(clazz);

        // Create the initial context
        final Context context = new InitialContext(jndiProperties);

        return (T) context.lookup(jndiName);
    }


    /**
     * Schedules an AbstractJob.
     * @param job the AbstractJob to schedule.
     * @param jobData the map of Job data.
     * @param groupName the Job group name.
     * @return the Job key.
     */
    public static String scheduleJob(final AbstractQuartzJob job, final Map<String, Object> jobData, final String groupName) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Scheduling job {}", job);
        }

        String jobKey = null;

        try {

            String identifier = String.valueOf(job.hashCode() + jobData.hashCode());

            JobDetail jobDetail = newJob(job.getClass())
                    .withIdentity(identifier, groupName)
                    .usingJobData(new JobDataMap(jobData))
                    .build();

            Trigger trigger = newTrigger()
                    .withIdentity(identifier, groupName)
                    .withPriority(HIGH_PRIORITY)
                    .startNow()
                    .withSchedule(simpleSchedule().withRepeatCount(0))
                    .build();

            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler = sf.getScheduler(schedulerName);
            scheduler.scheduleJob(jobDetail, trigger);

            LOGGER.info("JobKey = {}", trigger.getJobKey().getName());

            verifySchedulerRunning(scheduler);

            jobKey = trigger.getJobKey().getName();

        } catch (final SchedulerException e) {
            LOGGER.error(e.getMessage());
        }

        return jobKey;
    }

}