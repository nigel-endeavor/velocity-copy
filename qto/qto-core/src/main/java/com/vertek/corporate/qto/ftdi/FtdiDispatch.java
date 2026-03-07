package com.vertek.corporate.qto.ftdi;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ftdi_dispatch")
public class FtdiDispatch extends StandardVersionedBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ftdi_dispatch_id")
    private Long id;

    @Column(name="legacy_id")
    private Long legacyId;

    /**
     * schedule_id.
     */
    @Column(name = "schedule_id")
    private Long scheduleId;

    /**
     * dispatch_id.
     */
    @Column(name = "vendor_dispatch_id")
    private String vendorDispatchId;

    /**
     * parent_dispatch_id.
     */
    @Column(name = "parent_vendor_dispatch_id")
    private String parentVendorDispatchId;

    /**
     * order_type_id.
     */
    @Column(name = "order_type_id")
    private Long orderTypeId;


    @Formula("(select ot.order_type from ftdi_order_type ot "
            + "where ot.ftdi_order_type_id = order_type_id)")
    private String orderType;

    /**
     * vendor.
     */
    @Column(name = "vendor")
    private String vendor;

    /**
     * status.
     */
    @Column(name = "status")
    private String status;

    /**
     * sr.
     */
    @Column(name = "sr")
    private String sr;

    /**
     * po.
     */
    @Column(name = "po")
    private String po;

    /**
     * subject.
     */
    @Column(name = "subject")
    private String subject;

    /**
     * on_hold.
     */
    @Column(name = "on_hold")
    private boolean onHold;

    /**
     * error_string.
     */
    @Column(name = "error_string")
    private String errorString;

    /**
     * result_id.
     */
    @Column(name = "result_id")
    private Integer resultId;

    /**
     * order_create_date.
     */
    @Column(name = "order_create_date")
    private Date orderCreateDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "ftdi_dispatch_id", referencedColumnName = "ftdi_dispatch_id")
    private List<FtdiAppointment> appointments = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "ftdi_dispatch_id", referencedColumnName = "ftdi_dispatch_id")
    private List<FtdiNote> notes = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(final Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getVendorDispatchId() {
        return vendorDispatchId;
    }

    public void setVendorDispatchId(final String vendorDispatchId) {
        this.vendorDispatchId = vendorDispatchId;
    }

    public String getParentVendorDispatchId() {
        return parentVendorDispatchId;
    }

    public void setParentVendorDispatchId(final String parentVendorDispatchId) {
        this.parentVendorDispatchId = parentVendorDispatchId;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(final Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(final String orderType) {
        this.orderType = orderType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(final String vendor) {
        this.vendor = vendor;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(final String sr) {
        this.sr = sr;
    }

    public String getPo() {
        return po;
    }

    public void setPo(final String po) {
        this.po = po;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public boolean isOnHold() {
        return onHold;
    }

    public void setOnHold(final boolean onHold) {
        this.onHold = onHold;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(final String errorString) {
        this.errorString = errorString;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(final Integer resultId) {
        this.resultId = resultId;
    }

    public Date getOrderCreateDate() {
        return orderCreateDate;
    }

    public void setOrderCreateDate(final Date orderCreateDate) {
        this.orderCreateDate = orderCreateDate;

    }

    public List<FtdiAppointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(final List<FtdiAppointment> appointments) {
        this.appointments = appointments;
    }

    public List<FtdiNote> getNotes() {
        return notes;
    }

    public void setNotes(final List<FtdiNote> notes) {
        this.notes = notes;
    }

    public Long getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(final Long legacyId) {
        this.legacyId = legacyId;
    }
}
