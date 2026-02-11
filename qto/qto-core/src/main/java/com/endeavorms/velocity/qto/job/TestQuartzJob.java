package com.endeavorms.velocity.qto.job;


import com.endeavorms.velocity.qto.common.quartz.AbstractQuartzJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Job to invoke shipment tracking updates.
 *
 * @author jboomhower
 * @since 6/27/14 12:43 PM
 */
@DisallowConcurrentExecution
public class TestQuartzJob extends AbstractQuartzJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestQuartzJob.class);

    @Override
    public void runJob(final JobExecutionContext jobExecutionContext) throws Exception  {
        LOGGER.debug("Tesing Quartz Job");

    }

}
