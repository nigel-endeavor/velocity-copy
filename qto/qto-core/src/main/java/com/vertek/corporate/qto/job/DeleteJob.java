package com.vertek.corporate.qto.job;

import com.vertek.corporate.qto.common.quartz.AbstractQuartzJob;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.service.ServiceManager;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
public class DeleteJob extends AbstractQuartzJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteJob.class);

    @Override
    public void runJob(final JobExecutionContext jobExecutionContext) {
        try {
            long startTime = System.currentTimeMillis();
            LOGGER.debug("DeleteJob started");
            LocationManager locationManager = findEjb(LocationManager.class);
            ServiceManager serviceManager = findEjb(ServiceManager.class);
            createSchedulerSubjectThreadState("scheduler@vertek.com", "aad");
            LOGGER.debug("Delete Service started");
            serviceManager.deleteServiceJob();
            LOGGER.debug("Delete Location started");
            locationManager.deleteLocationJob();
            LOGGER.debug("DeleteJob finished in {} ms", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            LOGGER.error("Error during DeleteJob", e);
        }
    }

}
