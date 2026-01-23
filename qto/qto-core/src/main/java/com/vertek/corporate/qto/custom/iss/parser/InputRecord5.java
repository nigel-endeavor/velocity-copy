package com.vertek.corporate.qto.custom.iss.parser;

import com.vertek.corporate.qto.common.StandardBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "input_record_5")
public class InputRecord5 extends StandardBaseEntity {

    /**
     * ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "input_record_5_id")
    private Long id;

    @Column(name = "input_record_1_id")
    private Long inputRecord1Id;

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "router_configured_ac")
    private String routerConfiguredAc;

    @Column(name = "router_brand_model_ac")
    private String routerBrandModelAc;

    @Column(name = "router_ownership_ac")
    private String routerOwnershipAc;

    @Column(name = "product_type_ac")
    private String productTypeAc;

    @Column(name = "ppoa_username_ac")
    private String ppoaUsernameAc;

    @Column(name = "ppoa_password_ac")
    private String ppoaPasswordAc;

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

    public String getRouterConfiguredAc() {
        return routerConfiguredAc;
    }

    public void setRouterConfiguredAc(final String routerConfiguredAc) {
        this.routerConfiguredAc = routerConfiguredAc;
    }

    public String getRouterBrandModelAc() {
        return routerBrandModelAc;
    }

    public void setRouterBrandModelAc(final String routerBrandModelAc) {
        this.routerBrandModelAc = routerBrandModelAc;
    }

    public String getRouterOwnershipAc() {
        return routerOwnershipAc;
    }

    public void setRouterOwnershipAc(final String routerOwnershipAc) {
        this.routerOwnershipAc = routerOwnershipAc;
    }

    public String getProductTypeAc() {
        return productTypeAc;
    }

    public void setProductTypeAc(final String productTypeAc) {
        this.productTypeAc = productTypeAc;
    }

    public String getPpoaUsernameAc() {
        return ppoaUsernameAc;
    }

    public void setPpoaUsernameAc(final String ppoaUsernameAc) {
        this.ppoaUsernameAc = ppoaUsernameAc;
    }

    public String getPpoaPasswordAc() {
        return ppoaPasswordAc;
    }

    public void setPpoaPasswordAc(final String ppoaPasswordAc) {
        this.ppoaPasswordAc = ppoaPasswordAc;
    }

    public String getSystemAssetNumber() {
        return systemAssetNumber;
    }

    public void setSystemAssetNumber(final String systemAssetNumber) {
        this.systemAssetNumber = systemAssetNumber;
    }
}
