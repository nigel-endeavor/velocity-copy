package com.endeavorms.velocity.qto.activation;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;
import com.endeavorms.velocity.qto.common.DateRangeType;

import java.util.Date;
import java.util.List;

/**
 * @author rcasey
 * @since 3/1/2023
 */
public class ActivationViewSearchCriteria extends BaseSearchCriteria<ActivationView> {
    private String search;

    private String clientServiceId;

    private List<String> scheduledAttemptStatus;

    private String internalTechAssigned;

    private List<Date> scheduledCheckInTime;

    private List<DateRangeType> scheduledCheckInTimeRange;

    private String lastUpdateBy;

    private String clientLocationType;

    private String clientLocationInfo;

    private List<String> parentCompanyName;

    public String getSearch() {
        return search;
    }

    public void setSearch(final String search) {
        this.search = search;
    }

    public String getClientServiceId() {
        return clientServiceId;
    }

    public void setClientServiceId(final String clientServiceId) {
        this.clientServiceId = clientServiceId;
    }

    public List<String> getScheduledAttemptStatus() {
        return scheduledAttemptStatus;
    }

    public void setScheduledAttemptStatus(final List<String> scheduledAttemptStatus) {
        this.scheduledAttemptStatus = scheduledAttemptStatus;
    }

    public String getInternalTechAssigned() {
        return internalTechAssigned;
    }

    public void setInternalTechAssigned(final String internalTechAssigned) {
        this.internalTechAssigned = internalTechAssigned;
    }

    public List<Date> getScheduledCheckInTime() {
        return scheduledCheckInTime;
    }

    public void setScheduledCheckInTime(final List<Date> scheduledCheckInTime) {
        this.scheduledCheckInTime = scheduledCheckInTime;
    }

    public List<DateRangeType> getScheduledCheckInTimeRange() {
        return scheduledCheckInTimeRange;
    }

    public void setScheduledCheckInTimeRange(final List<DateRangeType> scheduledCheckInTimeRange) {
        this.scheduledCheckInTimeRange = scheduledCheckInTimeRange;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(final String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getClientLocationType() {
        return clientLocationType;
    }

    public void setClientLocationType(final String clientLocationType) {
        this.clientLocationType = clientLocationType;
    }

    public String getClientLocationInfo() {
        return clientLocationInfo;
    }

    public void setClientLocationInfo(final String clientLocationInfo) {
        this.clientLocationInfo = clientLocationInfo;
    }

    public List<String> getParentCompanyName() {
        return parentCompanyName;
    }

    public void setParentCompanyName(final List<String> parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
    }
}
