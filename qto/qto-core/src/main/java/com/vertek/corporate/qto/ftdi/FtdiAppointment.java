package com.vertek.corporate.qto.ftdi;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ftdi_appointment")
public class FtdiAppointment extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ftdi_appointment_id")
    private Long id;

    @Column(name="legacy_id")
    private Long legacyId;

    /**
     * dispatch id.
     */
    @Column(name = "ftdi_dispatch_id")
    private Long ftdiDispatchId;

    /**
     * sr.
     */
    @Column(name = "sr")
    private Long sr;

    /**
     * status.
     */
    @Column(name = "status")
    private String status;

    /**
     * appointment_date.
     */
    @Column(name = "appointment_date")
    private Date appointmentDate;

    /**
     * time_on_site.
     */
    @Column(name = "time_on_site")
    private Date timeOnSite;

    /**
     * time_off_site.
     */
    @Column(name = "time_off_site")
    private Date timeOffSite;

    /**
     * eta.
     */
    @Column(name = "eta")
    private Date eta;

    /**
     * window_start.
     */
    @Column(name = "window_start")
    private Date windowStart;

    /**
     * window_end.
     */
    @Column(name = "window_end")
    private Date windowEnd;

    /**
     * mod_time_off_site.
     */
    @Column(name = "mod_time_off_site")
    private Date modTimeOffSite;

    /**
     * tech_name.
     */
    @Column(name = "tech_name")
    private String techName;

    /**
     * tech_cell.
     */
    @Column(name = "tech_cell")
    private String techCell;

    /**
     * vendor_name.
     */
    @Column(name = "vendor_name")
    private String vendor_name;


    @Override
    public Long getId() {
        return id;
    }

    public Long getFtdiDispatchId() {
        return ftdiDispatchId;
    }

    public void setFtdiDispatchId(final Long ftdiDispatchId) {
        this.ftdiDispatchId = ftdiDispatchId;
    }

    public Long getSr() {
        return sr;
    }

    public void setSr(final Long sr) {
        this.sr = sr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(final Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Date getTimeOnSite() {
        return timeOnSite;
    }

    public void setTimeOnSite(final Date timeOnSite) {
        this.timeOnSite = timeOnSite;
    }

    public Date getTimeOffSite() {
        return timeOffSite;
    }

    public void setTimeOffSite(final Date timeOffSite) {
        this.timeOffSite = timeOffSite;
    }

    public Date getEta() {
        return eta;
    }

    public void setEta(final Date eta) {
        this.eta = eta;
    }

    public Date getWindowStart() {
        return windowStart;
    }

    public void setWindowStart(final Date windowStart) {
        this.windowStart = windowStart;
    }

    public Date getWindowEnd() {
        return windowEnd;
    }

    public void setWindowEnd(final Date windowEnd) {
        this.windowEnd = windowEnd;
    }

    public Date getModTimeOffSite() {
        return modTimeOffSite;
    }

    public void setModTimeOffSite(final Date modTimeOffSite) {
        this.modTimeOffSite = modTimeOffSite;
    }

    public String getTechName() {
        return techName;
    }

    public void setTechName(final String techName) {
        this.techName = techName;
    }

    public String getTechCell() {
        return techCell;
    }

    public void setTechCell(final String techCell) {
        this.techCell = techCell;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(final String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public Long getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(final Long legacyId) {
        this.legacyId = legacyId;
    }
}
