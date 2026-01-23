package com.vertek.corporate.qto.jeop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * A view containing a union of order, location, and service jeops.
 * @author llevit
 */
@Entity
@Table(name = "v_jeops_union")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JeopUnionView extends AbstractMasterCustomerOwnedEntity {

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "service_id")
    private Long serviceId;

    @Id
    @Column(name = "jeop_instance_id")
    private Long id;

    @Column(name = "jeop_description")
    private String description;

    @Column(name = "jeop_level")
    private String level;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "note")
    private String note;

    @Column(name = "originator")
    private String originator;

    @Column(name = "responsibility")
    private String responsibility;

    @Column(name = "assigned_to")
    private String assignedTo;

    @Column(name = "business_days_open")
    private Integer businessDaysOpen;

    @Column(name = "calendar_days_open")
    private Integer calendarDaysOpen;

    @Column(name = "level_jeop")
    private String levelJeop;

    @Transient
    private Integer viewVersion;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return this.getId() == null;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(final String level) {
        this.level = level;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(final String originator) {
        this.originator = originator;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(final String responsibility) {
        this.responsibility = responsibility;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(final String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Integer getBusinessDaysOpen() {
        return businessDaysOpen;
    }

    public void setBusinessDaysOpen(final Integer businessDaysOpen) {
        this.businessDaysOpen = businessDaysOpen;
    }

    public Integer getCalendarDaysOpen() {
        return calendarDaysOpen;
    }

    public void setCalendarDaysOpen(final Integer calendarDaysOpen) {
        this.calendarDaysOpen = calendarDaysOpen;
    }

    public String getLevelJeop() {
        return levelJeop;
    }

    public void setLevelJeop(final String levelJeop) {
        this.levelJeop = levelJeop;
    }

    public Integer getViewVersion() {
        return viewVersion;
    }

    public void setViewVersion(final Integer viewVersion) {
        this.viewVersion = viewVersion;
    }

}
