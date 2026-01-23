package com.vertek.corporate.qto.common.quartz;

import com.google.common.collect.Maps;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.env.BasicIniEnvironment;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.ThreadState;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import java.util.List;
import java.util.Map;

/**
 * Base class for Quartz-based scheduled Jobs.
 * Implementations can supply the body of the scheduled job in AbstractQuartzJob#runJob and the base class will take
 * care of binding the "scheduler@vertek.com" subject to the job execution thread. Implementations may also override
 * AbstractQuartzJob#onError to provide any custom error handling.
 *
 * @author <a href="rconnolly@vertek.com">rconnolly@vertek.com</a>.
 * @since 3.2.0 - 5/5/15
 */
public abstract class AbstractQuartzJob implements Job {

    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQuartzJob.class);

    /**
     * Implementations supply the body of the scheduled job in this method. The base class will invoke this method when
     * executing the job.
     *
     * @param jobExecutionContext runJob method.
     * @throws Exception should the Job throw an Exception.
     */
    public abstract void runJob(final JobExecutionContext jobExecutionContext) throws Exception;


    /**
     * Runs the Job as the scheduler user.
     *
     * @param jobExecutionContext the JobExecutionContext contains job dependencies.
     * @throws org.quartz.JobExecutionException should the Job throw an Exception.
     */
    @Override
    public void execute(final JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ThreadState threadState = createSchedulerSubjectThreadState();
        try {
            runJob(jobExecutionContext);
        } catch (Exception e) {
            onError(e);
        } finally {
            threadState.clear();
        }
    }


    /**
     * Handles any Exception thrown in the execution of the scheduled job.
     *
     * @param e the thrown Exception.
     */
    protected void onError(final Exception e) {
        LOGGER.error("Caught an Exception", e);
    }


    /**
     * Delegate for ScheduledJobUtil#findEjb provided for convenience.
     *
     * @param clazz the class type of the EJB which is being requested for lookup.
     * @param <T>   the generic type of the EJB.
     * @return the requested EJB Class.
     * @throws javax.naming.NamingException should the requested EJB not be bound to JNDI.
     */
    protected <T> T findEjb(final Class<T> clazz) throws NamingException {
        return ScheduledJobUtil.findEjb(clazz);
    }


    /**
     * Delete the current job from the scheduler.
     *
     * @param jobExecutionContext the JobExecutionContext which provides the current job and other objects.
     */
    protected void deleteJob(final JobExecutionContext jobExecutionContext) {
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        Scheduler scheduler = jobExecutionContext.getScheduler();
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException schedulerException) {
            LOGGER.error("Cannot delete job with jobKey = {}", jobKey.toString());
            onError(schedulerException);
            // swallow the exception - don't want to kill the processing of responses just because of a quartz issue.
        }
    }


    /**
     * Binds the "scheduler" Subject to the current Thread.
     *
     * @return the created ThreadState.
     */
    protected ThreadState createSchedulerSubjectThreadState() {
        return createSchedulerSubjectThreadState("scheduler@vertek.com", "aad");
    }


    /**
     * Binds the "scheduler" Subject to the current Thread.
     *
     * @param subjectName the name of the subject.
     * @return the created ThreadState.
     */
    protected ThreadState createSchedulerSubjectThreadState(final String subjectName) {
        return createSchedulerSubjectThreadState(subjectName, "aad");

    }


    /**
     * Binds the "scheduler" Subject to the current Thread.
     *
     * @param subjectName the name of the subject.
     * @param realmName   the name of the realm.
     * @return the created ThreadState.
     */
    protected ThreadState createSchedulerSubjectThreadState(final String subjectName, final String realmName) {

        Subject subject;

        try {
            org.apache.shiro.SecurityUtils.getSecurityManager();

            Map<String, Object> attributes = Maps.newHashMap();

            // create simple authentication info
            List<Object> principals = CollectionUtils.asList(subjectName, attributes);
            PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, realmName);

            subject = new Subject.Builder()
                    .principals(principalCollection)
                    .buildSubject();
        } catch (UnavailableSecurityManagerException ex) {

            LOGGER.trace("Creating IniSecurityManagerFactory...");

//            IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-ejbstartup.ini");
            BasicIniEnvironment factory = new BasicIniEnvironment("classpath:shiro-ejbstartup.ini");


            LOGGER.trace("Getting SecurityManager instance...");

            org.apache.shiro.mgt.SecurityManager securityManager = factory.getSecurityManager();

            LOGGER.trace("Setting SecurityManager...");

            org.apache.shiro.SecurityUtils.setSecurityManager(securityManager);

            subject = org.apache.shiro.SecurityUtils.getSubject();

            subject.login(new UsernamePasswordToken(subjectName, "schedpassword"));

        }
        ThreadState state = new SubjectThreadState(subject);
        state.bind();

        return state;
    }

}
