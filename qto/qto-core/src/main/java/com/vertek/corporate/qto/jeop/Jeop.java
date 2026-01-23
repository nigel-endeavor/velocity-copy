package com.vertek.corporate.qto.jeop;

import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author llevit
 */
@Entity
@Table(name = "jeop_instance")
@Inheritance(strategy = InheritanceType.JOINED)
public class Jeop extends AbstractMasterCustomerOwnedEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jeop_instance_id")
    private Long id;



    @Column(name="legacy_id")
    private Long legacyId;

    @Column(name = "jeop_description")
    private String description;

    @Column(name = "jeop_level")
    private String level;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

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

    @Column(name = "note")
    private String note;

    @Transient
    private Integer viewVersion;

    @Override
    public Long getId() {
        return id;
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

    protected void setLevel(final String level) {
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

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public Long getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(final Long legacyId) {
        this.legacyId = legacyId;
    }

    public Integer getViewVersion() {
        return viewVersion;
    }

    public void setViewVersion(final Integer viewVersion) {
        this.viewVersion = viewVersion;
    }
}
