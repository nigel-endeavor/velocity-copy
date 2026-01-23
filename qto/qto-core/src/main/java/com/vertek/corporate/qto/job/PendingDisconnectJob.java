package com.vertek.corporate.qto.job;

import com.vertek.corporate.qto.common.quartz.AbstractQuartzJob;
import com.vertek.corporate.qto.inventory.PendingDisconnectManager;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
public class PendingDisconnectJob  extends AbstractQuartzJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(PendingDisconnectJob.class);

    @Override
    public void runJob(final JobExecutionContext jobExecutionContext) throws Exception  {
        PendingDisconnectManager disconnectManager = findEjb(PendingDisconnectManager.class);
        disconnectManager.processPendingDisconnects();
    }
}
