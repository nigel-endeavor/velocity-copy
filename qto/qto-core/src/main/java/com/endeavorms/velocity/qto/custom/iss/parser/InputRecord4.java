package com.endeavorms.velocity.qto.custom.iss.parser;

import com.endeavorms.velocity.qto.common.StandardBaseEntity;
import com.endeavorms.velocity.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "input_record_4")
public class InputRecord4  extends StandardBaseEntity {

        /**
     * ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "input_record_4_id")
    private Long id;

    @Column(name = "input_record_1_id")
    private Long inputRecord1Id;

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "item_no")
    private String itemNo;

    @Column(name = "need_quantity")
    private Long needQuantity;

    @Column(name = "system_asset_number")
    private String systemAssetNumber;

    @Override
    public Long getId() {
        return id;
    }

    public Long getInputRecord1Id() {
        return inputRecord1Id;
    }

    public void setInputRecord1Id(final Long inputRecord1Id) {
        this.inputRecord1Id = inputRecord1Id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(final String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(final String itemNo) {
        this.itemNo = itemNo;
    }

    public Long getNeedQuantity() {
        return needQuantity;
    }

    public void setNeedQuantity(final Long needQuantity) {
        this.needQuantity = needQuantity;
    }

    public String getSystemAssetNumber() {
        return systemAssetNumber;
    }

    public void setSystemAssetNumber(final String systemAssetNumber) {
        this.systemAssetNumber = systemAssetNumber;
    }
}
