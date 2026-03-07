
package com.vertek.corporate.qto.solution;

import com.vertek.corporate.qto.common.StandardBaseEntity;
import com.vertek.corporate.qto.quote.Quote;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The solution, which is part of a vendor's quote.
 * @author fcurran
 * @since 1.0.0
 */
@Entity
@Table(name = "solution")
public class Solution extends StandardBaseEntity {
    /** ID. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solution_id")
    private Long id;
    /** The ID of the persisted quote the solution came in on. */
    @Column(name = "vendor_quote_id")
    private String vendorQuoteId;
    /** id. */
    @Column(name = "vendor_solution_id")
    private String vendorSolutionId;
    /** quote. */
    @ManyToOne
    @JoinColumn(
            name = "quote_id",
            referencedColumnName = "quote_id"
    )
    private Quote quote;
    /** priceState. */
    @Column(name = "price_state")
    private String priceState;
    /** term. */
    @Column(name = "term")
    private String term;
    /** locationId. */
    @Column(name = "location_id")
    private String locationId;
    /** locationId. */
    @Column(name = "customer_location_id")
    private String customerLocationId;
    /** note. */
    @Column(name = "note")
    private String note;
    /** provider. */
    @Column(name = "provider")
    private String provider;
    /** mrc. */
    @Column(name = "mrc")
    private BigDecimal mrc;
    /** mrcCost. */
    @Column(name = "mrc_cost")
    private BigDecimal mrcCost;
    /** mrcTotalPrice. */
    @Column(name = "mrc_total_price")
    private BigDecimal mrcTotalPrice;
    /** nrc. */
    @Column(name = "nrc")
    private BigDecimal nrc;
    /** nrcCost. */
    @Column(name = "nrc_cost")
    private BigDecimal nrcCost;
    /** nrcTotalPrice. */
    @Column(name = "nrc_total_price")
    private BigDecimal nrcTotalPrice;
    /** baseCurrencyId. */
    @Column(name = "base_currency_id")
    private Long baseCurrencyId;
    /** address. */
    @Column(name = "address")
    private String address;
    /** city. */
    @Column(name = "city")
    private String city;
    /** state. */
    @Column(name = "state")
    private String state;
    /** zip. */
    @Column(name = "zip")
    private String zip;
    /** addressValid. */
    @Column(name = "address_valid")
    private Long addressValid;
    /** secondaryDesignator. */
    @Column(name = "secondary_designator")
    private String secondaryDesignator;
    /** secondaryNumber. */
    @Column(name = "secondary_number")
    private String secondaryNumber;
    /** latitude. */
    @Column(name = "latitude")
    private String latitude;
    /** longitude. */
    @Column(name = "longitude")
    private String longitude;
    /** uniqueKey. */
    @Column(name = "unique_key")
    private String uniqueKey;
    /** globalLocationId. */
    @Column(name = "global_location_id")
    private String globalLocationId;
    /** countryCode. */
    @Column(name = "country_code")
    private String countryCode;
    /** preferredSupplier. */
    @Column(name = "preferred_supplier")
    private Long preferredSupplier;
    /** customProductName. */
    @Column(name = "custom_product_name")
    private String customProductName;
    /** product. */
    @Column(name = "product")
    private String product;
    /** apiProductName. */
    @Column(name = "api_product_name")
    private String apiProductName;
    /** speed. */
    @Column(name = "speed")
    private String speed;
    /** uploadSpeed. */
    @Column(name = "upload_speed")
    private Long uploadSpeed;
    /** downloadSpeed. */
    @Column(name = "download_speed")
    private Long downloadSpeed;
    /** mediaType. */
    @Column(name = "media_type")
    private String mediaType;
    /** netStatus. */
    @Column(name = "net_status")
    private String netStatus;
    /** bldgStatus. */
    @Column(name = "bldg_status")
    private String bldgStatus;
    /** status. */
    @Column(name = "status")
    private String status;
    /** dispositionCode. */
    @Column(name = "disposition_code")
    private String dispositionCode;
    /** installInterval. */
    @Column(name = "install_interval")
    private Long installInterval;
    /** buildingCompetitiveRating. */
    @Column(name = "building_competitive_rating")
    private String buildingCompetitiveRating;
    /** pricingType. */
    @Column(name = "pricing_type")
    private String pricingType;
    /** createdDate. */
    @Column(name = "created_date")
    private Date createdDate;
    /** createdBy. */
    @Column(name = "created_by")
    private String createdBy;
    /** addressStatusId. */
    @Column(name = "address_status_id")
    private Long addressStatusId;
    /** lastmileSupplier. */
    @Column(name = "lastmile_supplier")
    private String lastmileSupplier;
    /** catalog. */
    @Column(name = "catalog")
    private String catalog;
    /** baseCurrency. */
    @Column(name = "base_currency")
    private String baseCurrency;
    /** quoteCurrency. */
    @Column(name = "quote_currency")
    private String quoteCurrency;

    /** siteName. */
    @Column(name = "site_name")
    private String siteName;

    /** siteId. */
    @Column(name = "site_id")
    private String siteId;

    /** flexField1. */
    @Column(name = "flex_field_1")
    private String flexField1;

    /** flexField2. */
    @Column(name = "flex_field_2")
    private String flexField2;

    /** Any noted additional properties. */
    @Column(name = "additional_properties")
    private String additionalProperties;

    /** When the solution had finished being process. */
    @Column(name = "solution_handled_time")
    private Date handledTime;
    /** The tenant the quote order solution came from. */
    @Column(name = "tenant_id")
    private Long tenantId;

    public Long getId() {
        return id;
    }

    public String getVendorQuoteId() {
        return vendorQuoteId;
    }

    public void setVendorQuoteId(final String vendorQuoteId) {
        this.vendorQuoteId = vendorQuoteId;
    }

    public String getVendorSolutionId() {
        return vendorSolutionId;
    }

    public void setVendorSolutionId(final String vendorSolutionId) {
        this.vendorSolutionId = vendorSolutionId;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(final Quote quote) {
        this.quote = quote;
    }

    public String getPriceState() {
        return priceState;
    }

    public void setPriceState(String priceState) {
        this.priceState = priceState;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getCustomerLocationId() {
        return customerLocationId;
    }

    public void setCustomerLocationId(final String customerLocationId) {
        this.customerLocationId = customerLocationId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public BigDecimal getMrc() {
        return mrc;
    }

    public void setMrc(BigDecimal mrc) {
        this.mrc = mrc;
    }

    public BigDecimal getMrcCost() {
        return mrcCost;
    }

    public void setMrcCost(BigDecimal mrcCost) {
        this.mrcCost = mrcCost;
    }

    public BigDecimal getMrcTotalPrice() {
        return mrcTotalPrice;
    }

    public void setMrcTotalPrice(BigDecimal mrcTotalPrice) {
        this.mrcTotalPrice = mrcTotalPrice;
    }

    public BigDecimal getNrc() {
        return nrc;
    }

    public void setNrc(BigDecimal nrc) {
        this.nrc = nrc;
    }

    public BigDecimal getNrcCost() {
        return nrcCost;
    }

    public void setNrcCost(BigDecimal nrcCost) {
        this.nrcCost = nrcCost;
    }

    public BigDecimal getNrcTotalPrice() {
        return nrcTotalPrice;
    }

    public void setNrcTotalPrice(BigDecimal nrcTotalPrice) {
        this.nrcTotalPrice = nrcTotalPrice;
    }

    public Long getBaseCurrencyId() {
        return baseCurrencyId;
    }

    public void setBaseCurrencyId(Long baseCurrencyId) {
        this.baseCurrencyId = baseCurrencyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Long getAddressValid() {
        return addressValid;
    }

    public void setAddressValid(Long addressValid) {
        this.addressValid = addressValid;
    }

    public String getSecondaryDesignator() {
        return secondaryDesignator;
    }

    public void setSecondaryDesignator(String secondaryDesignator) {
        this.secondaryDesignator = secondaryDesignator;
    }

    public String getSecondaryNumber() {
        return secondaryNumber;
    }

    public void setSecondaryNumber(String secondaryNumber) {
        this.secondaryNumber = secondaryNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getGlobalLocationId() {
        return globalLocationId;
    }

    public void setGlobalLocationId(String globalLocationId) {
        this.globalLocationId = globalLocationId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Long getPreferredSupplier() {
        return preferredSupplier;
    }

    public void setPreferredSupplier(Long preferredSupplier) {
        this.preferredSupplier = preferredSupplier;
    }

    public String getCustomProductName() {
        return customProductName;
    }

    public void setCustomProductName(String customProductName) {
        this.customProductName = customProductName;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getApiProductName() {
        return apiProductName;
    }

    public void setApiProductName(String apiProductName) {
        this.apiProductName = apiProductName;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public Long getUploadSpeed() {
        return uploadSpeed;
    }

    public void setUploadSpeed(Long uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }

    public Long getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(Long downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(String netStatus) {
        this.netStatus = netStatus;
    }

    public String getBldgStatus() {
        return bldgStatus;
    }

    public void setBldgStatus(String bldgStatus) {
        this.bldgStatus = bldgStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDispositionCode() {
        return dispositionCode;
    }

    public void setDispositionCode(String dispositionCode) {
        this.dispositionCode = dispositionCode;
    }

    public Long getInstallInterval() {
        return installInterval;
    }

    public void setInstallInterval(Long installInterval) {
        this.installInterval = installInterval;
    }

    public String getBuildingCompetitiveRating() {
        return buildingCompetitiveRating;
    }

    public void setBuildingCompetitiveRating(String buildingCompetitiveRating) {
        this.buildingCompetitiveRating = buildingCompetitiveRating;
    }

    public String getPricingType() {
        return pricingType;
    }

    public void setPricingType(String pricingType) {
        this.pricingType = pricingType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getAddressStatusId() {
        return addressStatusId;
    }

    public void setAddressStatusId(Long addressStatusId) {
        this.addressStatusId = addressStatusId;
    }

    public String getLastmileSupplier() {
        return lastmileSupplier;
    }

    public void setLastmileSupplier(String lastmileSupplier) {
        this.lastmileSupplier = lastmileSupplier;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(final String siteName) {
        this.siteName = siteName;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(final String siteId) {
        this.siteId = siteId;
    }

    public String getFlexField1() {
        return flexField1;
    }

    public void setFlexField1(final String flexField1) {
        this.flexField1 = flexField1;
    }

    public String getFlexField2() {
        return flexField2;
    }

    public void setFlexField2(final String flexField2) {
        this.flexField2 = flexField2;
    }

    public String getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(final String additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public Date getHandledTime() {
        return handledTime;
    }

    public void setHandledTime(final Date handledTime) {
        this.handledTime = handledTime;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(final Long tenantId) {
        this.tenantId = tenantId;
    }

}
