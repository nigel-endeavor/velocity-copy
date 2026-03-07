package com.vertek.corporate.qto.ftdi;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ftdi_shipments")
public class FtdiShipment extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ftdi_shipment_id")
    private Long id;

    @Column(name="legacy_id")
    private Long legacyId;

    /**
     * dispatch id.
     */
    @Column(name = "ftdi dispatch id")
    private Long ftdiDispatchId;

    /**
     * tracking_number.
     */
    @Column(name = "tracking_number")
    private String trackingNumber;

    /**
     * shipment_status.
     */
    @Column(name = "shipment_status")
    private String shipmentStatus;

    /**
     * shipment_date.
     */
    @Column(name = "shipment_date")
    private Date shipmentDate;

    /**
     * delivered.
     */
    @Column(name = "delivered")
    private Boolean delivered;

    /**
     * delivery_date.
     */
    @Column(name = "delivery_date")
    private Date deliveryDate;

    /**
     * signed_by.
     */
    @Column(name = "signed_by")
    private String signedBy;

    /**
     * return_shipment.
     */
    @Column(name = "return_shipment")
    private Boolean returnShipment;

    /**
     * courier.
     */
    @Column(name = "courier")
    private String courier;

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

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(final String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(final String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(final Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(final Boolean delivered) {
        this.delivered = delivered;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(final Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getSignedBy() {
        return signedBy;
    }

    public void setSignedBy(final String signedBy) {
        this.signedBy = signedBy;
    }

    public Boolean getReturnShipment() {
        return returnShipment;
    }

    public void setReturnShipment(final Boolean returnShipment) {
        this.returnShipment = returnShipment;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(final String courier) {
        this.courier = courier;
    }

    public Long getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(final Long legacyId) {
        this.legacyId = legacyId;
    }
}
