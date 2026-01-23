package com.vertek.corporate.qto.activation;

import com.vertek.corporate.qto.common.BaseSearchCriteria;
import com.vertek.corporate.qto.common.DateRangeType;

import javax.ws.rs.QueryParam;
import java.util.Date;
import java.util.List;

/**
 * @author rcasey
 * @since 3/1/2023
 */
public class ActivationViewSearchCriteria extends BaseSearchCriteria<ActivationView> {
    @QueryParam("search")
    private String search;

    @QueryParam("clientServiceId")
    private String clientServiceId;

    @QueryParam("scheduledAttemptStatus")
    private List<String> scheduledAttemptStatus;

    @QueryParam("internalTechAssigned")
    private String internalTechAssigned;

    @QueryParam("scheduledCheckInTime")
    private List<Date> scheduledCheckInTime;

    @QueryParam("scheduledCheckInTime-comparison")
    private List<DateRangeType> scheduledCheckInTimeRange;

    @QueryParam("lastUpdateBy")
    private String lastUpdateBy;

    @QueryParam("clientLocationType")
    private String clientLocationType;

    @QueryParam("clientLocationInfo")
    private String clientLocationInfo;

    @QueryParam("parentCompanyName")
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
