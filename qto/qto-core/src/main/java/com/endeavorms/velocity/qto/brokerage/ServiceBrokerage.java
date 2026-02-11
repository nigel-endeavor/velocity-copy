package com.endeavorms.velocity.qto.brokerage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerOwnedEntity;
import com.endeavorms.velocity.qto.service.Service;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "service_brokerage")
public class ServiceBrokerage extends AbstractMasterCustomerOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_brokerage_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "service_id", referencedColumnName = "service_id")
    @JsonIgnore
    private Service service;

    @Transient
    private Long serviceId;

    @Column(name = "submitted_in_adv_to_provider")
    private boolean submittedInAdvToProvider;

    @Column(name = "parent_tsd")
    private String parentTsd;

    @Column(name = "submitted_in_adv_to_tsd")
    private boolean submittedInAdvToTsd;

    @Column(name = "cie_teamed_deal_info")
    private String cieTeamedDealInfo;

    @Column(name = "net_provider_points")
    private String netProviderPoints;

    @Column(name = "promotions")
    private String promotions;

    @Column(name = "commissionable_mrc")
    private BigDecimal commissionableMrc = BigDecimal.ZERO;

    @Column(name = "commissionable_nrc")
    private BigDecimal commissionableNrc = BigDecimal.ZERO;

    @Column(name = "commissionable_arc")
    private BigDecimal commissionableArc = BigDecimal.ZERO;

    @Column(name = "commissions_supplier")
    private String commissionsSupplier;

    @Column(name = "commissions_account_number")
    private String commissionsAccountNumber;

    @Column(name = "sub_agent_percent")
    private Double subAgentPercent;

    @Column(name = "sub_agent")
    private String subAgent;

    @Column(name = "sub_agent_rep")
    private String subAgentRep;

    @Column(name = "secondary_agency")
    private String secondaryAgency;

    @Column(name = "secondary_agency_rep")
    private String secondaryAgencyRep;

    @Column(name = "agent")
    private String agent;

    @Column(name = "agent_rep")
    private String agentRep;

    @Column(name = "agent_percent")
    private Double agentPercent;

    @Column(name = "referral")
    private boolean referral;

    @Column(name = "referral_name")
    private String referralName;

    @Column(name = "referral_percent")
    private Double referralPercent;

    @Column(name = "spiff_amount")
    private BigDecimal spiffAmount = BigDecimal.ZERO;

    @Column(name = "customer_order_alias")
    private String customerOrderAlias;

    @Column(name = "engineer_resource")
    private boolean engineerResource;

    @Column(name = "engineer_resource_allocation")
    private Double engineerResourceAllocation;

    @Column(name = "commission_payment_type")
    private String commissionPaymentType;

    @Column(name = "expected_commission")
    private BigDecimal expectedCommission = BigDecimal.ZERO;

    @Column(name = "commission_reduction_percent")
    private Double commissionReductionPercent;

    @Column(name = "commission_icb")
    private boolean commissionIcb;

    @Column(name = "internal_commissions_comments")
    private String internalCommissionsComments;

    @Column(name = "gross_profit_mrc_multiplier")
    private Double grossProfitMrcMultiplier;

    @Column(name = "percent_resides_from_carrier")
    private String percentResidesFromCarrier;

    @Column(name = "gross_profit_mrc")
    private BigDecimal grossProfitMrc = BigDecimal.ZERO;

    @Column(name = "gross_profit")
    private BigDecimal grossProfit = BigDecimal.ZERO;

    @Column(name = "gross_profit_mrc_needs_to_be_edited")
    private boolean grossProfitMrcNeedsToBeEdited;

    @Column(name = "gross_profit_needs_to_be_edited")
    private boolean grossProfitNeedsToBeEdited;

    @Column(name = "rep_gross_profit_needs_to_be_edited")
    private boolean repGrossProfitNeedsToBeEdited;

    @Column(name = "gross_profit_mrc_override")
    private BigDecimal grossProfitMrcOverride = BigDecimal.ZERO;

    @Column(name = "gross_profit_override")
    private BigDecimal grossProfitOverride = BigDecimal.ZERO;

    @Column(name = "rep_gross_profit_production")
    private BigDecimal repGrossProfitProduction = BigDecimal.ZERO;

    @Column(name = "total_contract_value")
    private BigDecimal totalContractValue = BigDecimal.ZERO;

    @Column(name = "uplift_mrc")
    private BigDecimal upliftMrc = BigDecimal.ZERO;

    @Column(name = "subaccount_mrc")
    private BigDecimal subaccountMrc = BigDecimal.ZERO;

    @PostLoad
    public void postLoad() {
        if (service != null) {
            serviceId = service.getId();
        }
    }

    public Long getId() {
        return id;
    }

    public Service getService() {
        return service;
    }

    public void setService(final Service service) {
        this.service = service;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public boolean isSubmittedInAdvToProvider() {
        return submittedInAdvToProvider;
    }

    public void setSubmittedInAdvToProvider(final boolean submittedInAdvToProvider) {
        this.submittedInAdvToProvider = submittedInAdvToProvider;
    }

    public String getParentTsd() {
        return parentTsd;
    }

    public void setParentTsd(final String parentTsd) {
        this.parentTsd = parentTsd;
    }

    public boolean isSubmittedInAdvToTsd() {
        return submittedInAdvToTsd;
    }

    public void setSubmittedInAdvToTsd(final boolean submittedInAdvToTsd) {
        this.submittedInAdvToTsd = submittedInAdvToTsd;
    }

    public String getCieTeamedDealInfo() {
        return cieTeamedDealInfo;
    }

    public void setCieTeamedDealInfo(final String cieTeamedDealInfo) {
        this.cieTeamedDealInfo = cieTeamedDealInfo;
    }

    public String getNetProviderPoints() {
        return netProviderPoints;
    }

    public void setNetProviderPoints(final String netProviderPoints) {
        this.netProviderPoints = netProviderPoints;
    }

    public String getPromotions() {
        return promotions;
    }

    public void setPromotions(final String promotions) {
        this.promotions = promotions;
    }

    public BigDecimal getCommissionableMrc() {
        return commissionableMrc;
    }

    public void setCommissionableMrc(final BigDecimal commissionableMrc) {
        this.commissionableMrc = commissionableMrc;
    }

    public BigDecimal getCommissionableNrc() {
        return commissionableNrc;
    }

    public void setCommissionableNrc(final BigDecimal commissionableNrc) {
        this.commissionableNrc = commissionableNrc;
    }

    public BigDecimal getCommissionableArc() {
        return commissionableArc;
    }

    public void setCommissionableArc(final BigDecimal commissionableArc) {
        this.commissionableArc = commissionableArc;
    }

    public String getCommissionsSupplier() {
        return commissionsSupplier;
    }

    public void setCommissionsSupplier(final String commissionsSupplier) {
        this.commissionsSupplier = commissionsSupplier;
    }

    public String getCommissionsAccountNumber() {
        return commissionsAccountNumber;
    }

    public void setCommissionsAccountNumber(final String commissionsAccountNumber) {
        this.commissionsAccountNumber = commissionsAccountNumber;
    }

    public Double getSubAgentPercent() {
        return subAgentPercent;
    }

    public void setSubAgentPercent(final Double subAgentPercent) {
        this.subAgentPercent = subAgentPercent;
    }

    public String getSubAgent() {
        return subAgent;
    }

    public void setSubAgent(final String subAgent) {
        this.subAgent = subAgent;
    }

    public String getSubAgentRep() {
        return subAgentRep;
    }

    public void setSubAgentRep(final String subAgentRep) {
        this.subAgentRep = subAgentRep;
    }

    public Double getAgentPercent() {
        return agentPercent;
    }

    public void setAgentPercent(final Double agentPercent) {
        this.agentPercent = agentPercent;
    }

    public String getSecondaryAgency() {
        return secondaryAgency;
    }

    public void setSecondaryAgency(final String secondaryAgency) {
        this.secondaryAgency = secondaryAgency;
    }

    public String getSecondaryAgencyRep() {
        return secondaryAgencyRep;
    }

    public void setSecondaryAgencyRep(final String secondaryAgencyRep) {
        this.secondaryAgencyRep = secondaryAgencyRep;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(final String agent) {
        this.agent = agent;
    }

    public String getAgentRep() {
        return agentRep;
    }

    public void setAgentRep(final String agentRep) {
        this.agentRep = agentRep;
    }

    public boolean isReferral() {
        return referral;
    }

    public void setReferral(final boolean referral) {
        this.referral = referral;
    }

    public String getReferralName() {
        return referralName;
    }

    public void setReferralName(final String referralName) {
        this.referralName = referralName;
    }

    public Double getReferralPercent() {
        return referralPercent;
    }

    public void setReferralPercent(final Double referralPercent) {
        this.referralPercent = referralPercent;
    }

    public BigDecimal getSpiffAmount() {
        return spiffAmount;
    }

    public void setSpiffAmount(final BigDecimal spiffAmount) {
        this.spiffAmount = spiffAmount;
    }

    public String getCustomerOrderAlias() {
        return customerOrderAlias;
    }

    public void setCustomerOrderAlias(final String customerOrderAlias) {
        this.customerOrderAlias = customerOrderAlias;
    }

    public boolean isEngineerResource() {
        return engineerResource;
    }

    public void setEngineerResource(final boolean engineerResource) {
        this.engineerResource = engineerResource;
    }

    public Double getEngineerResourceAllocation() {
        return engineerResourceAllocation;
    }

    public void setEngineerResourceAllocation(final Double engineerResourceAllocation) {
        this.engineerResourceAllocation = engineerResourceAllocation;
    }

    public String getCommissionPaymentType() {
        return commissionPaymentType;
    }

    public void setCommissionPaymentType(final String commissionPaymentType) {
        this.commissionPaymentType = commissionPaymentType;
    }

    public BigDecimal getExpectedCommission() {
        return expectedCommission;
    }

    public void setExpectedCommission(final BigDecimal expectedCommission) {
        this.expectedCommission = expectedCommission;
    }

    public Double getCommissionReductionPercent() {
        return commissionReductionPercent;
    }

    public void setCommissionReductionPercent(final Double commissionReductionPercent) {
        this.commissionReductionPercent = commissionReductionPercent;
    }

    public boolean isCommissionIcb() {
        return commissionIcb;
    }

    public void setCommissionIcb(final boolean commissionIcb) {
        this.commissionIcb = commissionIcb;
    }

    public String getInternalCommissionsComments() {
        return internalCommissionsComments;
    }

    public void setInternalCommissionsComments(final String internalCommissionsComments) {
        this.internalCommissionsComments = internalCommissionsComments;
    }

    public Double getGrossProfitMrcMultiplier() {
        return grossProfitMrcMultiplier;
    }

    public void setGrossProfitMrcMultiplier(final Double grossProfitMrcMultiplier) {
        this.grossProfitMrcMultiplier = grossProfitMrcMultiplier;
    }

    public String getPercentResidesFromCarrier() {
        return percentResidesFromCarrier;
    }

    public void setPercentResidesFromCarrier(final String percentResidesFromCarrier) {
        this.percentResidesFromCarrier = percentResidesFromCarrier;
    }

    public BigDecimal getGrossProfitMrc() {
        return grossProfitMrc;
    }

    public void setGrossProfitMrc(final BigDecimal grossProfitMrc) {
        this.grossProfitMrc = grossProfitMrc;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(final BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
    }

    public boolean isGrossProfitMrcNeedsToBeEdited() {
        return grossProfitMrcNeedsToBeEdited;
    }

    public void setGrossProfitMrcNeedsToBeEdited(final boolean grossProfitMrcNeedsToBeEdited) {
        this.grossProfitMrcNeedsToBeEdited = grossProfitMrcNeedsToBeEdited;
    }

    public boolean isGrossProfitNeedsToBeEdited() {
        return grossProfitNeedsToBeEdited;
    }

    public void setGrossProfitNeedsToBeEdited(final boolean grossProfitNeedsToBeEdited) {
        this.grossProfitNeedsToBeEdited = grossProfitNeedsToBeEdited;
    }

    public boolean isRepGrossProfitNeedsToBeEdited() {
        return repGrossProfitNeedsToBeEdited;
    }

    public void setRepGrossProfitNeedsToBeEdited(final boolean repGrossProfitNeedsToBeEdited) {
        this.repGrossProfitNeedsToBeEdited = repGrossProfitNeedsToBeEdited;
    }

    public BigDecimal getGrossProfitMrcOverride() {
        return grossProfitMrcOverride;
    }

    public void setGrossProfitMrcOverride(final BigDecimal grossProfitMrcOverride) {
        this.grossProfitMrcOverride = grossProfitMrcOverride;
    }

    public BigDecimal getGrossProfitOverride() {
        return grossProfitOverride;
    }

    public void setGrossProfitOverride(final BigDecimal grossProfitOverride) {
        this.grossProfitOverride = grossProfitOverride;
    }

    public BigDecimal getRepGrossProfitProduction() {
        return repGrossProfitProduction;
    }

    public void setRepGrossProfitProduction(final BigDecimal repGrossProfitProduction) {
        this.repGrossProfitProduction = repGrossProfitProduction;
    }

    public BigDecimal getTotalContractValue() {
        return totalContractValue;
    }

    public void setTotalContractValue(final BigDecimal totalContractValue) {
        this.totalContractValue = totalContractValue;
    }

    public BigDecimal getUpliftMrc() {
        return upliftMrc;
    }

    public void setUpliftMrc(final BigDecimal upliftMrc) {
        this.upliftMrc = upliftMrc;
    }

    public BigDecimal getSubaccountMrc() {
        return subaccountMrc;
    }

    public void setSubaccountMrc(final BigDecimal subaccountMrc) {
        this.subaccountMrc = subaccountMrc;
    }
}
