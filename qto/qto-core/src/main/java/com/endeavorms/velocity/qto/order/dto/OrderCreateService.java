package com.endeavorms.velocity.qto.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderCreateService implements Serializable {
    @JsonProperty("clientLocationId")
    private String clientLocationId;
    @JsonProperty("clientServiceId")
    private String clientServiceId;
    @JsonProperty("serviceType")
    private String serviceType;
    @JsonProperty("quoteId")
    private String quoteId;
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("provider")
    private String provider;
    @JsonProperty("serviceBilledTo")
    private String serviceBilledTo;
    @JsonProperty("subProductType")
    private String subProductType;
    @JsonProperty("serviceInfo")
    private String serviceInfo;
    @JsonProperty("clientServiceType")
    private String clientServiceType;
    @JsonProperty("contractTerm")
    private String contractTerm;
    @JsonProperty("poNumber")
    private String poNumber;
    @JsonProperty("mrc")
    private BigDecimal mrc;
    @JsonProperty("nrc")
    private BigDecimal nrc;
    @JsonProperty("annualNrc")
    private BigDecimal annualNrc;
    @JsonProperty("customerRequestedInstallDate")
    private Date customerRequestedInstallDate;
    @JsonProperty("downloadSpeed")
    private String downloadSpeed;
    @JsonProperty("uploadSpeed")
    private String uploadSpeed;
    @JsonProperty("mediaType")
    private String mediaType;
    @JsonProperty("zAddress")
    private OrderCreateAddress zAddress;
    @JsonProperty("description")
    private String description;
    @JsonProperty("recordSource")
    private String recordSource;
    @JsonProperty("link")
    private String link;
    @JsonProperty("serviceId")
    private Long serviceId;
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("linkedOrBundled")
    private String linkedOrBundled;
    @JsonProperty("linkedBundledClientServiceId")
    private String linkedBundledClientServiceId;
    @JsonProperty("autoRenewal")
    private boolean autoRenewal;
    @JsonProperty("coTerminus")
    private boolean coTerminus;
    @JsonProperty("noticePeriodForRenewal")
    private String noticePeriodForRenewal;
    @JsonProperty("contractInfo")
    private String contractInfo;
    @JsonProperty("additionalIpBlock")
    private String additionalIpBlock;
    @JsonProperty("dmarc")
    private String dmarc;
    @JsonProperty("commissionableMrc")
    private BigDecimal commissionableMrc;
    @JsonProperty("commissionableNrc")
    private BigDecimal commissionableNrc;
    @JsonProperty("commissionableArc")
    private BigDecimal commissionableArc;
    @JsonProperty("subAgent")
    private String subAgent;
    @JsonProperty("subAgentPercent")
    private Double subAgentPercent;
    @JsonProperty("submittedInAdvToProvider")
    private boolean submittedInAdvToProvider;
    @JsonProperty("parentTsd")
    private String parentTsd;
    @JsonProperty("submittedInAdvToTsd")
    private boolean submittedInAdvToTsd;
    @JsonProperty("referral")
    private boolean referral;
    @JsonProperty("referralName")
    private String referralName;
    @JsonProperty("companyReferralPercent")
    private Double companyReferralPercent;
    @JsonProperty("commissionPaymentType")
    private String commissionPaymentType;
    @JsonProperty("expectedCommission")
    private BigDecimal expectedCommission;
    @JsonProperty("commissionReductionPercent")
    private Double commissionReductionPercent;
    @JsonProperty("cieTeamedDealInfo")
    private String cieTeamedDealInfo;
    @JsonProperty("commissionIcb")
    private boolean commissionIcb;
    @JsonProperty("internalCommissionsComments")
    private String internalCommissionsComments;
    @JsonProperty("opportunityNum")
    private String opportunityNum;
    @JsonProperty("netProviderPoints")
    private String netProviderPoints;
    @JsonProperty("promotions")
    private String promotions;
    @JsonProperty("spiffAmount")
    private BigDecimal spiffAmount;
    @JsonProperty("engineerResource")
    private boolean engineerResource;
    @JsonProperty("engineerResourceAllocation")
    private Double engineerResourceAllocation;

    @JsonProperty("customFields")
    private List<OrderCreateServiceCustomFields> customFields;

    @JsonProperty("contractSignedDate")
    private Date contractSignedDate;
    @JsonProperty("fieldServicesProvider")
    private String fieldServicesProvider;


    @JsonProperty("mrr")
    private BigDecimal mrr;
    @JsonProperty("nrr")
    private BigDecimal nrr;
    @JsonProperty("managedService")
    private boolean managedService;
    @JsonProperty("agent")
    private String agent;
    @JsonProperty("agentRep")
    private String agentRep;
    @JsonProperty("agentPercent")
    private Double agentPercent;
    @JsonProperty("subAgentRep")
    private String subAgentRep;

    public String getClientLocationId() {
        return clientLocationId;
    }

    public void setClientLocationId(final String clientLocationId) {
        this.clientLocationId = clientLocationId;
    }

    public String getClientServiceId() {
        return clientServiceId;
    }

    public void setClientServiceId(final String clientServiceId) {
        this.clientServiceId = clientServiceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(final String serviceType) {
        this.serviceType = serviceType;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(final String quoteId) {
        this.quoteId = quoteId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(final String provider) {
        this.provider = provider;
    }

    public String getServiceBilledTo() {
        return serviceBilledTo;
    }

    public void setServiceBilledTo(final String serviceBilledTo) {
        this.serviceBilledTo = serviceBilledTo;
    }

    public String getSubProductType() {
        return subProductType;
    }

    public void setSubProductType(final String subProductType) {
        this.subProductType = subProductType;
    }

    public String getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(final String serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public String getClientServiceType() {
        return clientServiceType;
    }

    public void setClientServiceType(final String clientServiceType) {
        this.clientServiceType = clientServiceType;
    }

    public String getContractTerm() {
        return contractTerm;
    }

    public void setContractTerm(final String contractTerm) {
        this.contractTerm = contractTerm;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(final String poNumber) {
        this.poNumber = poNumber;
    }

    public BigDecimal getMrc() {
        return mrc;
    }

    public void setMrc(final BigDecimal mrc) {
        this.mrc = mrc;
    }

    public BigDecimal getNrc() {
        return nrc;
    }

    public void setNrc(final BigDecimal nrc) {
        this.nrc = nrc;
    }

    public BigDecimal getAnnualNrc() {
        return annualNrc;
    }

    public void setAnnualNrc(final BigDecimal annualNrc) {
        this.annualNrc = annualNrc;
    }

    public Date getCustomerRequestedInstallDate() {
        return customerRequestedInstallDate;
    }

    public void setCustomerRequestedInstallDate(final Date customerRequestedInstallDate) {
        this.customerRequestedInstallDate = customerRequestedInstallDate;
    }

    public String getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(final String downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public String getUploadSpeed() {
        return uploadSpeed;
    }

    public void setUploadSpeed(final String uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(final String mediaType) {
        this.mediaType = mediaType;
    }

    public OrderCreateAddress getzAddress() {
        return zAddress;
    }

    public void setzAddress(final OrderCreateAddress zAddress) {
        this.zAddress = zAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getRecordSource() {
        return recordSource;
    }

    public void setRecordSource(final String recordSource) {
        this.recordSource = recordSource;
    }

    public String getLink() {
        return link;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getLinkedOrBundled() {
        return linkedOrBundled;
    }

    public void setLinkedOrBundled(final String linkedOrBundled) {
        this.linkedOrBundled = linkedOrBundled;
    }

    public String getLinkedBundledClientServiceId() {
        return linkedBundledClientServiceId;
    }

    public void setLinkedBundledClientServiceId(final String linkedBundledClientServiceId) {
        this.linkedBundledClientServiceId = linkedBundledClientServiceId;
    }

    public boolean isAutoRenewal() {
        return autoRenewal;
    }

    public void setAutoRenewal(final boolean autoRenewal) {
        this.autoRenewal = autoRenewal;
    }

    public boolean isCoTerminus() {
        return coTerminus;
    }

    public void setCoTerminus(final boolean coTerminus) {
        this.coTerminus = coTerminus;
    }

    public String getNoticePeriodForRenewal() {
        return noticePeriodForRenewal;
    }

    public void setNoticePeriodForRenewal(final String noticePeriodForRenewal) {
        this.noticePeriodForRenewal = noticePeriodForRenewal;
    }

    public String getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(final String contractInfo) {
        this.contractInfo = contractInfo;
    }

    public String getAdditionalIpBlock() {
        return additionalIpBlock;
    }

    public void setAdditionalIpBlock(final String additionalIpBlock) {
        this.additionalIpBlock = additionalIpBlock;
    }

    public String getDmarc() {
        return dmarc;
    }

    public void setDmarc(final String dmarc) {
        this.dmarc = dmarc;
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

    public Double getCommissionReductionPercent() {
        return commissionReductionPercent;
    }

    public void setCommissionReductionPercent(final Double commissionReductionPercent) {
        this.commissionReductionPercent = commissionReductionPercent;
    }

    public String getOpportunityNum() {
        return opportunityNum;
    }

    public void setOpportunityNum(final String opportunityNum) {
        this.opportunityNum = opportunityNum;
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

    public String getSubAgent() {
        return subAgent;
    }

    public void setSubAgent(final String subAgent) {
        this.subAgent = subAgent;
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

    public Double getCompanyReferralPercent() {
        return companyReferralPercent;
    }

    public void setCompanyReferralPercent(final Double companyReferralPercent) {
        this.companyReferralPercent = companyReferralPercent;
    }

    public BigDecimal getSpiffAmount() {
        return spiffAmount;
    }

    public void setSpiffAmount(final BigDecimal spiffAmount) {
        this.spiffAmount = spiffAmount;
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

    public String getInternalCommissionsComments() {
        return internalCommissionsComments;
    }

    public void setInternalCommissionsComments(final String internalCommissionsComments) {
        this.internalCommissionsComments = internalCommissionsComments;
    }

    public Double getSubAgentPercent() {
        return subAgentPercent;
    }

    public void setSubAgentPercent(Double subAgentPercent) {
        this.subAgentPercent = subAgentPercent;
    }

    public boolean isCommissionIcb() {
        return commissionIcb;
    }

    public void setCommissionIcb(boolean commissionIcb) {
        this.commissionIcb = commissionIcb;
    }

    public Date getContractSignedDate() {
        return contractSignedDate;
    }

    public void setContractSignedDate(final Date contractSignedDate) {
        this.contractSignedDate = contractSignedDate;
    }

    public String getFieldServicesProvider() {
        return fieldServicesProvider;
    }

    public void setFieldServicesProvider(final String fieldServicesProvider) {
        this.fieldServicesProvider = fieldServicesProvider;
    }

    public List<OrderCreateServiceCustomFields> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<OrderCreateServiceCustomFields> customFields) {
        this.customFields = customFields;
    }

    public BigDecimal getMrr() {
        return mrr;
    }

    public void setMrr(final BigDecimal mrr) {
        this.mrr = mrr;
    }

    public BigDecimal getNrr() {
        return nrr;
    }

    public void setNrr(final BigDecimal nrr) {
        this.nrr = nrr;
    }

    public boolean isManagedService() {
        return managedService;
    }

    public void setManagedService(final boolean managedService) {
        this.managedService = managedService;
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

    public Double getAgentPercent() {
        return agentPercent;
    }

    public void setAgentPercent(final Double agentPercent) {
        this.agentPercent = agentPercent;
    }

    public String getSubAgentRep() {
        return subAgentRep;
    }

    public void setSubAgentRep(final String subAgentRep) {
        this.subAgentRep = subAgentRep;
    }
}
