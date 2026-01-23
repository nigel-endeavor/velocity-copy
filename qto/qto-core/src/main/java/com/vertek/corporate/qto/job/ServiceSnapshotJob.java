package com.vertek.corporate.qto.job;

import com.vertek.corporate.qto.common.quartz.AbstractQuartzJob;
import com.vertek.corporate.qto.service.snapshot.ServiceSnapshotManager;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
public class ServiceSnapshotJob extends AbstractQuartzJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceSnapshotJob.class);

    @Override
    public void runJob(final JobExecutionContext jobExecutionContext) {
        try {
            long startTime = System.currentTimeMillis();
            LOGGER.debug("ServiceSnapshotJob started");
            ServiceSnapshotManager serviceSnapshotManager= findEjb(ServiceSnapshotManager.class);
            createSchedulerSubjectThreadState("scheduler@vertek.com", "aad");
            serviceSnapshotManager.createServiceSnapshots();
            LOGGER.debug("ServiceSnapshotJob finished in {} ms", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            LOGGER.error("Error during ServiceSnapshotJob", e);
        }
    }

}
