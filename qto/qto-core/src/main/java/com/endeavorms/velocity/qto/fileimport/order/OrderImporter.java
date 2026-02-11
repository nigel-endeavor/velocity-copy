package com.endeavorms.velocity.qto.fileimport.order;

import com.google.common.base.Strings;
import com.endeavorms.velocity.qto.address.AddressView;
import com.endeavorms.velocity.qto.address.AddressViewManager;
import com.endeavorms.velocity.qto.common.adapter.ExcelAdapter;
import com.endeavorms.velocity.qto.common.mapping.StringSourceMapper;
import com.endeavorms.velocity.qto.common.mapping.ValidatingSourceMapper;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.config.CompanyConfigKey;
import com.endeavorms.velocity.qto.config.CompanyConfigPropertyManager;
import com.endeavorms.velocity.qto.customfield.field.CustomField;
import com.endeavorms.velocity.qto.customfield.field.CustomFieldManager;
import com.endeavorms.velocity.qto.fileimport.AbstractImporter;
import com.endeavorms.velocity.qto.fileimport.AbstractInventoryServiceImportColumns;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivity;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.order.Order;
import com.endeavorms.velocity.qto.order.OrderManager;
import com.endeavorms.velocity.qto.order.dto.OrderCreateAddress;
import com.endeavorms.velocity.qto.order.dto.OrderCreateCompany;
import com.endeavorms.velocity.qto.order.dto.OrderCreateContact;
import com.endeavorms.velocity.qto.order.dto.OrderCreateDto;
import com.endeavorms.velocity.qto.order.dto.OrderCreateLocation;
import com.endeavorms.velocity.qto.order.dto.OrderCreateService;
import com.endeavorms.velocity.qto.order.dto.OrderCreateServiceCustomFields;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.ServiceType;

import org.springframework.stereotype.Component;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rcasey
 * @since 2/19/2024
 */
@Component
@TransactionManagement(TransactionManagementType.BEAN)
public class OrderImporter extends AbstractImporter {

    @Inject
    private CompanyManager companyManager;

    @Inject
    private ServiceManager serviceManager;

    @Inject
    private LocationManager locationManager;

    @Inject
    private OrderManager orderManager;

    @Inject
    private AddressViewManager addressViewManager;

    @Inject
    private CompanyConfigPropertyManager<CompanyConfigKey> companyConfigPropertyManager;

    @Inject
    private CustomFieldManager customFieldManager;

    //the list of dtos generated from the spreadsheet, gets attached to the ImportActivity object
    private List<OrderCreateDto> dtoList;

    private Boolean lockOneLocationPerOrder = null;

    @Override
    public ImportActivity importFile(final Long id) {
        dtoList = new ArrayList<>();
        ImportActivity importActivity = super.importFile(id);
        importActivity.setDtoList(dtoList);
        lockOneLocationPerOrder = null;
        return importActivity;
    }

    @Override
    protected void importRow(final Map<String, ValidatingSourceMapper> sourceMappers, final ImportActivity importActivity) throws Exception {
        Long tenantId = importActivity.getTenantId();
        Company masterCustomer = companyManager.findMasterCustomerByNameAndTenantId(sourceMappers.get(OrderImportColumns.MASTER_CUSTOMER).getValue(), tenantId);
        Company endCustomer = companyManager.findEndCustomerByNameAndTenantId(sourceMappers.get(OrderImportColumns.END_CUSTOMER).getValue(), tenantId);
        String clientOrderId = sourceMappers.get(OrderImportColumns.CLIENT_ORDER_ID).getValue();
        String clientLocationId = sourceMappers.get(OrderImportColumns.CLIENT_LOCATION_ID).getValue();
        AddressView address = addressViewManager.findByClientLocationId(clientLocationId);

        //check if we need to lock one location per order
        if (lockOneLocationPerOrder == null) {
            Company tenantCompany = companyManager.findTenantByTenantId(tenantId);
            lockOneLocationPerOrder = companyConfigPropertyManager.getBoolean(tenantCompany.getId(), CompanyConfigKey.LOCK_ONE_LOCATION_PER_ORDER);
        }

        //check if order dto already exists
        //if we are locking one location per order, check if location dto already exists
        //if it does, we need to create a new order dto
        OrderCreateDto dto = dtoList.stream()
                .filter(ocdto ->
                    ocdto.getMasterCustomer().getId().equals(masterCustomer.getId()) &&
                    ocdto.getEndCustomer().getId().equals(endCustomer.getId()) &&
                    ocdto.getClientOrderId().equals(clientOrderId) &&
                    (!lockOneLocationPerOrder || ocdto.getLocations().stream().anyMatch(ldto -> ldto.getClientLocationId().equals(clientLocationId)))
                )
                .findFirst()
                .orElse(new OrderCreateDto());

        //populate order fields
        if (dto.getClientOrderId() == null) {
            dto.setMasterCustomer(new OrderCreateCompany(masterCustomer.getId(), masterCustomer.getName()));
            dto.setEndCustomer(new OrderCreateCompany(endCustomer.getId(), endCustomer.getName()));
            dto.setClientOrderId(clientOrderId);
            dtoList.add(dto);
        }

        //check if location dto already exists
        OrderCreateLocation locationDto = dto.getLocations().stream()
                .filter(ldto -> ldto.getClientLocationId().equals(clientLocationId))
                .findFirst()
                .orElse(new OrderCreateLocation());
        //populate location fields
        if (locationDto.getClientLocationId() == null) {
            locationDto.setClientLocationId(clientLocationId);
            locationDto.setClientOrderId(clientOrderId);
            OrderCreateAddress locationAddress = new OrderCreateAddress();
            String address1 = sourceMappers.get(OrderImportColumns.ADDRESS_1).getValue();
            if (!Strings.isNullOrEmpty(address1)) {
                locationAddress.setAddress1(sourceMappers.get(OrderImportColumns.ADDRESS_1).getValue());
                locationAddress.setAddress2(sourceMappers.get(OrderImportColumns.ADDRESS_2).getValue());
                locationAddress.setCity(sourceMappers.get(OrderImportColumns.CITY).getValue());
                locationAddress.setState(lookupValueMap.get(sourceMappers.get(OrderImportColumns.STATE_PROVINCE_REGION).getValue()));
                locationAddress.setPostalCode(sourceMappers.get(OrderImportColumns.ZIP_POSTAL_CODE).getValue());
                locationAddress.setCountry(sourceMappers.get(OrderImportColumns.COUNTRY).getValue());
            } else if (address != null) {
                locationAddress.setAddress1(address.getAddress1());
                locationAddress.setAddress2(address.getAddress2());
                locationAddress.setCity(address.getCity());
                locationAddress.setState(address.getState());
                locationAddress.setPostalCode(address.getPostalCode());
                locationAddress.setCountry(address.getCountry());
            }
            locationDto.setAddress(locationAddress);
            locationDto.setLconName(sourceMappers.get(OrderImportColumns.LCON_NAME).getValue());
            locationDto.setLconEmail(sourceMappers.get(OrderImportColumns.LCON_EMAIL).getValue());
            locationDto.setLconPhone(sourceMappers.get(OrderImportColumns.LCON_PHONE).getValue());
            locationDto.setLocationInfo(sourceMappers.get(OrderImportColumns.LOCATION_INFO).getValue());
            locationDto.setLocationType(sourceMappers.get(OrderImportColumns.LOCATION_TYPE).getValue());
//            locationDto.setLevelOfEffort(sourceMappers.get(OrderImportColumns.LEVEL_OF_EFFORT).getValue());
            dto.getLocations().add(locationDto);
        }

        //populate service fields
        OrderCreateService serviceDto = new OrderCreateService();
        serviceDto.setClientLocationId(clientLocationId);
        serviceDto.setClientServiceId(sourceMappers.get(OrderImportColumns.CLIENT_SERVICE_ID).getValue());
        serviceDto.setServiceType(sourceMappers.get(OrderImportColumns.SERVICE).getValue());
        serviceDto.setDescription(sourceMappers.get(OrderImportColumns.DESCRIPTION).getValue());
        serviceDto.setQuoteId(sourceMappers.get(OrderImportColumns.QUOTE_ID).getValue());
        serviceDto.setProjectName(lookupValueMap.get(sourceMappers.get(OrderImportColumns.PROJECT_NAME).getValue()));
        serviceDto.setProvider(lookupValueMap.get(sourceMappers.get(OrderImportColumns.PROVIDER).getValue()));
        serviceDto.setServiceBilledTo(lookupValueMap.get(sourceMappers.get(OrderImportColumns.SERVICE_BILLED_TO).getValue()));
        serviceDto.setSubProductType(sourceMappers.get(OrderImportColumns.SUB_PRODUCT_TYPE).getValue());
        serviceDto.setServiceInfo(sourceMappers.get(OrderImportColumns.SERVICE_INFO).getValue());
        serviceDto.setClientServiceType(sourceMappers.get(OrderImportColumns.SERVICE_TYPE).getValue());
        serviceDto.setContractTerm(lookupValueMap.get(sourceMappers.get(OrderImportColumns.CONTRACT_TERM).getValue()));
        serviceDto.setPoNumber(sourceMappers.get(OrderImportColumns.PO_NUMBER).getValue());
        if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.MRC).getValue())) {
            serviceDto.setMrc(new BigDecimal(sourceMappers.get(OrderImportColumns.MRC).getValue()));
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.NRC).getValue())) {
            serviceDto.setNrc(new BigDecimal(sourceMappers.get(OrderImportColumns.NRC).getValue()));
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.ANNUAL_NRC).getValue())) {
            serviceDto.setAnnualNrc(new BigDecimal(sourceMappers.get(OrderImportColumns.ANNUAL_NRC).getValue()));
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.CUSTOMER_REQUESTED_INSTALL_DATE).getNonValidatedValue())) {
            serviceDto.setCustomerRequestedInstallDate(parseDate(sourceMappers.get(OrderImportColumns.CUSTOMER_REQUESTED_INSTALL_DATE).getValue()));
        }
        serviceDto.setUploadSpeed(lookupValueMap.get(sourceMappers.get(OrderImportColumns.UPLOAD_SPEED).getValue()));
        serviceDto.setDownloadSpeed(lookupValueMap.get(sourceMappers.get(OrderImportColumns.DOWNLOAD_SPEED).getValue()));
        serviceDto.setMediaType(lookupValueMap.get(sourceMappers.get(OrderImportColumns.MEDIA_TYPE).getValue()));
        if (Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.LINKED_BUNDLED).getValue())) {
            serviceDto.setLinkedOrBundled("none");
        } else {
            serviceDto.setLinkedOrBundled(sourceMappers.get(OrderImportColumns.LINKED_BUNDLED).getValue().toLowerCase());
        }
        serviceDto.setLinkedBundledClientServiceId(sourceMappers.get(OrderImportColumns.PARENT_CLIENT_SERVICE_ID).getValue());
        serviceDto.setAutoRenewal("Yes".equals(parseYesNo(sourceMappers.get(OrderImportColumns.AUTO_RENEWAL).getValue())));
        serviceDto.setCoTerminus("Yes".equals(parseYesNo(sourceMappers.get(OrderImportColumns.CO_TERMINUS).getValue())));
        serviceDto.setNoticePeriodForRenewal(sourceMappers.get(OrderImportColumns.NOTICE_PERIOD_FOR_RENEWAL).getValue());
        serviceDto.setContractInfo(sourceMappers.get(OrderImportColumns.CONTRACT_INFO).getValue());
        serviceDto.setDmarc(sourceMappers.get(OrderImportColumns.DMARC).getValue());
        serviceDto.setAdditionalIpBlock(lookupValueMap.get(sourceMappers.get(OrderImportColumns.ADDITIONAL_IP_BLOCK).getValue()));
        dto.setBillingAddress(new OrderCreateAddress());
        dto.getBillingAddress().setAddress1(sourceMappers.get(OrderImportColumns.BILLING_ADDRESS_1).getValue());
        dto.getBillingAddress().setAddress2(sourceMappers.get(OrderImportColumns.BILLING_ADDRESS_2).getValue());
        dto.getBillingAddress().setCity(sourceMappers.get(OrderImportColumns.BILLING_CITY).getValue());
        dto.getBillingAddress().setState(lookupValueMap.get(sourceMappers.get(OrderImportColumns.BILLING_STATE).getValue()));
        dto.getBillingAddress().setPostalCode(sourceMappers.get(OrderImportColumns.BILLING_ZIP).getValue());
        dto.getBillingAddress().setCountry(sourceMappers.get(OrderImportColumns.BILLING_COUNTRY).getValue());
        dto.getBillingAddress().setEmail(sourceMappers.get(OrderImportColumns.BILLING_EMAIL).getValue());
        dto.setTechContact(new OrderCreateContact());
        String techName = sourceMappers.get(OrderImportColumns.TECHNICAL_NAME).getValue();
        String[] techNameParts = techName.split(" ");
        if (techNameParts.length > 1) {
            dto.getTechContact().setFirstName(techNameParts[0]);
            dto.getTechContact().setLastName(techNameParts[1]);
        } else {
            dto.getTechContact().setFirstName(techName);
        }
        dto.getTechContact().setEmail(sourceMappers.get(OrderImportColumns.TECHNICAL_EMAIL).getValue());
        dto.getTechContact().setPhone(sourceMappers.get(OrderImportColumns.TECHNICAL_PHONE).getValue());
        dto.setSalesContact(new OrderCreateContact());
        String salesName = sourceMappers.get(OrderImportColumns.SALES_NAME).getValue();
        String[] salesNameParts = salesName.split(" ");
        if (salesNameParts.length > 1) {
            dto.getSalesContact().setFirstName(salesNameParts[0]);
            dto.getSalesContact().setLastName(salesNameParts[1]);
        } else {
            dto.getSalesContact().setFirstName(salesName);
        }
        dto.getSalesContact().setEmail(sourceMappers.get(OrderImportColumns.SALES_EMAIL).getValue());
        dto.getSalesContact().setPhone(sourceMappers.get(OrderImportColumns.SALES_PHONE).getValue());
        dto.setAuthContact(new OrderCreateContact());
        String authName = sourceMappers.get(OrderImportColumns.AUTHORIZATION_NAME).getValue();
        String[] authNameParts = authName.split(" ");
        if (authNameParts.length > 1) {
            dto.getAuthContact().setFirstName(authNameParts[0]);
            dto.getAuthContact().setLastName(authNameParts[1]);
        } else {
            dto.getAuthContact().setFirstName(authName);
        }
        dto.getAuthContact().setEmail(sourceMappers.get(OrderImportColumns.AUTHORIZATION_EMAIL).getValue());
        dto.getAuthContact().setPhone(sourceMappers.get(OrderImportColumns.AUTHORIZATION_PHONE).getValue());
        if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.COMMISSIONABLE_MRC).getValue())) {
            serviceDto.setCommissionableMrc(new BigDecimal(sourceMappers.get(OrderImportColumns.COMMISSIONABLE_MRC).getValue()));
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.COMMISSIONABLE_NRC).getValue())) {
            serviceDto.setCommissionableNrc(new BigDecimal(sourceMappers.get(OrderImportColumns.COMMISSIONABLE_NRC).getValue()));
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.COMMISSIONABLE_ARC).getValue())) {
            serviceDto.setCommissionableArc(new BigDecimal(sourceMappers.get(OrderImportColumns.COMMISSIONABLE_ARC).getValue()));
        }
        serviceDto.setSubmittedInAdvToProvider("Yes".equals(parseYesNo(sourceMappers.get(OrderImportColumns.SUBMITTED_IN_ADV_TO_PROVIDER).getValue())));
        serviceDto.setParentTsd(lookupValueMap.get(sourceMappers.get(OrderImportColumns.PARENT_TSD).getValue()));
        serviceDto.setSubmittedInAdvToTsd("Yes".equals(parseYesNo(sourceMappers.get(OrderImportColumns.SUBMITTED_IN_ADV_TO_TSD).getValue())));
        serviceDto.setCieTeamedDealInfo(sourceMappers.get(OrderImportColumns.CIE_TEAMED_DEAL_INFO).getValue());
        if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.COMMISSION_REDUCTION_PERCENT).getValue())) {
            double commissionReductionPercent = Double.parseDouble(sourceMappers.get(OrderImportColumns.COMMISSION_REDUCTION_PERCENT).getValue());
            if (commissionReductionPercent < 1) {
                commissionReductionPercent = commissionReductionPercent * 100;
                serviceDto.setCommissionReductionPercent(commissionReductionPercent);
            }
            serviceDto.setCommissionReductionPercent(commissionReductionPercent);
        }
        serviceDto.setOpportunityNum(sourceMappers.get(OrderImportColumns.SFA_OPPORTUNITY_NUM).getValue());
        serviceDto.setNetProviderPoints(sourceMappers.get(OrderImportColumns.NET_PROVIDER_POINTS).getValue());
        serviceDto.setPromotions(sourceMappers.get(OrderImportColumns.PROMOTIONS).getValue());
        if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.SPIFF_AMOUNT).getValue())) {
            serviceDto.setSpiffAmount(new BigDecimal(sourceMappers.get(OrderImportColumns.SPIFF_AMOUNT).getValue()));
        }
        serviceDto.setContractSignedDate(parseDate(sourceMappers.get(OrderImportColumns.CONTRACT_SIGNED_DATE).getValue()));
        serviceDto.setFieldServicesProvider(lookupValueMap.get(sourceMappers.get(OrderImportColumns.FIELD_SERVICES_PROVIDER).getValue()));
        serviceDto.setSubAgent(lookupValueMap.get(sourceMappers.get(OrderImportColumns.SUB_AGENT).getValue()));
        if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.SUB_AGENT_PERCENT).getValue())) {
            double subAgentPercent = Double.parseDouble(sourceMappers.get(OrderImportColumns.SUB_AGENT_PERCENT).getValue());
            if (subAgentPercent < 1) {
                subAgentPercent = subAgentPercent * 100;
                serviceDto.setSubAgentPercent(subAgentPercent);
            }
            serviceDto.setSubAgentPercent(subAgentPercent);
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.REFERRAL_NAME_COMPANY).getValue())) {
            serviceDto.setReferral(true);
            serviceDto.setReferralName(sourceMappers.get(OrderImportColumns.REFERRAL_NAME_COMPANY).getValue());
            if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.COMPANY_REFERRAL_PERCENT).getValue())) {
                double companyReferralPercent = Double.parseDouble(sourceMappers.get(OrderImportColumns.COMPANY_REFERRAL_PERCENT).getValue());
                if (companyReferralPercent < 1) {
                    companyReferralPercent = companyReferralPercent * 100;
                    serviceDto.setCompanyReferralPercent(companyReferralPercent);
                }
                serviceDto.setCompanyReferralPercent(companyReferralPercent);
            }
        }
        serviceDto.setCommissionPaymentType(lookupValueMap.get(sourceMappers.get(OrderImportColumns.COMMISSION_PAYMENT_TYPE).getValue()));
        if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.EXPECTED_COMMISSION).getValue())) {
            serviceDto.setExpectedCommission(new BigDecimal(sourceMappers.get(OrderImportColumns.EXPECTED_COMMISSION).getValue()));
        }
        serviceDto.setCommissionIcb("Yes".equals(parseYesNo(sourceMappers.get(OrderImportColumns.COMMISSIONS_ICB).getValue())));
        serviceDto.setInternalCommissionsComments(sourceMappers.get(OrderImportColumns.INTERNAL_COMMISSIONS_COMMENTS).getValue());
       if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.ENGINEER_RESOURCE_ALLOCATION).getValue())) {
           serviceDto.setEngineerResource(true);
           double engineerResourceAllocation = Double.parseDouble(sourceMappers.get(OrderImportColumns.ENGINEER_RESOURCE_ALLOCATION).getValue());
              if (engineerResourceAllocation < 1) {
                engineerResourceAllocation = engineerResourceAllocation * 100;
                serviceDto.setEngineerResourceAllocation(engineerResourceAllocation);
              }
           serviceDto.setEngineerResourceAllocation(engineerResourceAllocation);
       }
       List<OrderCreateServiceCustomFields> customFieldList = new ArrayList<>();
       if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.LUMEN_CIE_REP).getValue())) {
           CustomField cf = customFieldManager.findByNameAndTenant("LUMEN_CIE_REP", tenantId);
           OrderCreateServiceCustomFields customFieldDto = new OrderCreateServiceCustomFields();
           customFieldDto.setCustomFieldId(cf.getId());
           customFieldDto.setValue(sourceMappers.get(OrderImportColumns.LUMEN_CIE_REP).getValue());
           customFieldList.add(customFieldDto);
       }
       if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.LUMEN_REGION).getValue())) {
           CustomField cf = customFieldManager.findByNameAndTenant("LUMEN_REGION", tenantId);
           OrderCreateServiceCustomFields customFieldDto = new OrderCreateServiceCustomFields();
           customFieldDto.setCustomFieldId(cf.getId());
           customFieldDto.setValue(sourceMappers.get(OrderImportColumns.LUMEN_REGION).getValue());
           customFieldList.add(customFieldDto);
       }
       if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.IN_REGION_NEED_TAX_ID).getValue())) {
           CustomField cf = customFieldManager.findByNameAndTenant("IN_REGION_NEED_TAX_ID", tenantId);
           OrderCreateServiceCustomFields customFieldDto = new OrderCreateServiceCustomFields();
           customFieldDto.setCustomFieldId(cf.getId());
           customFieldDto.setValue(sourceMappers.get(OrderImportColumns.IN_REGION_NEED_TAX_ID).getValue());
           customFieldList.add(customFieldDto);
       }
       if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.IN_OUT_REGION_NEED_SALES_ID).getValue())) {
           CustomField cf = customFieldManager.findByNameAndTenant("IN_OUT_REGION_NEED_SALES_ID", tenantId);
           OrderCreateServiceCustomFields customFieldDto = new OrderCreateServiceCustomFields();
           customFieldDto.setCustomFieldId(cf.getId());
           customFieldDto.setValue(sourceMappers.get(OrderImportColumns.IN_OUT_REGION_NEED_SALES_ID).getValue());
           customFieldList.add(customFieldDto);
       }
       if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.LUMEN_AG).getValue())) {
           CustomField cf = customFieldManager.findByNameAndTenant("LUMEN_AG", tenantId);
           OrderCreateServiceCustomFields customFieldDto = new OrderCreateServiceCustomFields();
           customFieldDto.setCustomFieldId(cf.getId());
           customFieldDto.setValue(sourceMappers.get(OrderImportColumns.LUMEN_AG).getValue());
           customFieldList.add(customFieldDto);
       }
       if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.ORDER_NUMBER).getValue())) {
           CustomField cf = customFieldManager.findByNameAndTenant("ORDER_NUMBER", tenantId);
           OrderCreateServiceCustomFields customFieldDto = new OrderCreateServiceCustomFields();
           customFieldDto.setCustomFieldId(cf.getId());
           customFieldDto.setValue(sourceMappers.get(OrderImportColumns.ORDER_NUMBER).getValue());
           customFieldList.add(customFieldDto);
       }
       if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.BTN).getValue())) {
           CustomField cf = customFieldManager.findByNameAndTenant("BTN", tenantId);
           OrderCreateServiceCustomFields customFieldDto = new OrderCreateServiceCustomFields();
           customFieldDto.setCustomFieldId(cf.getId());
           customFieldDto.setValue(sourceMappers.get(OrderImportColumns.BTN).getValue());
           customFieldList.add(customFieldDto);
       }
       serviceDto.setCustomFields(customFieldList);

        locationDto.getServices().add(serviceDto);
    }

    @Override
    protected List<String> validateRow(Map<String, ValidatingSourceMapper> sourceMappers, Long tenantId) {
         List<String> errors = new ArrayList<>();
        //verify MC exists
         Company masterCustomer = null;
        try {
            if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.MASTER_CUSTOMER).getNonValidatedValue())) {
                masterCustomer = companyManager.findMasterCustomerByNameAndTenantId(sourceMappers.get(OrderImportColumns.MASTER_CUSTOMER).getValue(), tenantId);
                if (masterCustomer == null) {
                    errors.add("Master Customer does not exist");
                }
            }
        } catch (Exception e) {
            errors.add("Could not parse Master Customer");
        }
        //verify EC exists
        Company endCustomer = null;
        try {
            if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.END_CUSTOMER).getNonValidatedValue())) {
                endCustomer = companyManager.findEndCustomerByNameAndTenantId(sourceMappers.get(OrderImportColumns.END_CUSTOMER).getValue(), tenantId);
                if (endCustomer == null) {
                    errors.add("End Customer does not exist");
                }
            }
        } catch (Exception e) {
            errors.add("Could not parse End Customer");
        }
                //verify if client location id already exists that it belongs to the same master customer/end customer
        try {
            String clientLocationId = sourceMappers.get(AbstractInventoryServiceImportColumns.CLIENT_LOCATION_ID).getNonValidatedValue();
            List<Location> locationList = locationManager.findByClientLocIdAndTenant(clientLocationId, tenantId);
            if (!locationList.isEmpty()) {
                Company invMc = companyManager.retrieve(locationList.get(0).getMasterCustomerId());
                if (masterCustomer != null && invMc != null && !invMc.getId().equals(masterCustomer.getId())) {
                    errors.add("Client Location ID already exists under Master Customer " + invMc.getName());
                } else {
                    Order order = orderManager.retrieve(locationList.get(0).getOrderId());
                    if (endCustomer != null && order != null && !order.getCompany().getId().equals(endCustomer.getId())) {
                        errors.add("Client Location ID already exists under End Customer " + order.getCompany().getName());
                    }

                }
            }
        } catch (Exception e) {
            errors.add("Could not parse Client Location ID");
        }
        //verify client service is unique
        try {
            Company tenantCompany = companyManager.findTenantByTenantId(tenantId);
            boolean autoCreateClientServiceId = companyConfigPropertyManager.getBoolean(tenantCompany.getId(), CompanyConfigKey.AUTO_CREATE_CLIENT_SERVICE_ID);
            if (!Strings.isNullOrEmpty(sourceMappers.get(OrderImportColumns.CLIENT_SERVICE_ID).getNonValidatedValue())) {
                List<Service> serviceList = serviceManager.findByClientServiceIdAndTenant(sourceMappers.get(OrderImportColumns.CLIENT_SERVICE_ID).getValue(), tenantId);
                if (!serviceList.isEmpty()) {
                    errors.add("Client Service ID already exists");
                }
            } else if (!autoCreateClientServiceId && Strings.isNullOrEmpty(sourceMappers.get(AbstractInventoryServiceImportColumns.CLIENT_SERVICE_ID).getNonValidatedValue())) {
                errors.add("Client Service ID is required");
            }
        } catch (Exception e) {
            errors.add("Could not parse Client Service ID");
        }

        //verify address is populated, or client location id maps to an existing address
        try {
            String address1 = sourceMappers.get(OrderImportColumns.ADDRESS_1).getNonValidatedValue();
            String city = sourceMappers.get(OrderImportColumns.CITY).getNonValidatedValue();
            String state = sourceMappers.get(OrderImportColumns.STATE_PROVINCE_REGION).getNonValidatedValue();
            String postalCode = sourceMappers.get(OrderImportColumns.ZIP_POSTAL_CODE).getNonValidatedValue();
            String country = sourceMappers.get(OrderImportColumns.COUNTRY).getNonValidatedValue();
            if (!Strings.isNullOrEmpty(postalCode)) {
                //zip code
                errors = validateZipcodeCol(errors, sourceMappers.get(OrderImportColumns.ZIP_POSTAL_CODE));
            }
            if (!Strings.isNullOrEmpty(state)) {
                //state
                errors = validateStateLookupValueCol(errors, sourceMappers.get(OrderImportColumns.STATE_PROVINCE_REGION), tenantId);
            }
            if (Strings.isNullOrEmpty(address1) || Strings.isNullOrEmpty(city) || Strings.isNullOrEmpty(state) || Strings.isNullOrEmpty(postalCode) || Strings.isNullOrEmpty(country)) {
                AddressView address = addressViewManager.findByClientLocationId(sourceMappers.get(OrderImportColumns.CLIENT_LOCATION_ID).getValue());
                if (address == null) {
                    errors.add("Address is required");
                }
            }
        } catch (Exception e) {
            errors.add("Could not parse Address");
        }

        //validate dropdown values
        //service type
        List<String> serviceTypes = Arrays.stream(ServiceType.values()).map(ServiceType::getServiceName).collect(Collectors.toList());
        try {
            String serviceType = sourceMappers.get(OrderImportColumns.SERVICE).getNonValidatedValue();
            if (!Strings.isNullOrEmpty(serviceType) && !serviceTypes.contains(serviceType)) {
                errors.add("Service Type is not valid");
            }
        } catch (Exception e) {
            errors.add("Could not parse Service Type");
        }
        //project name
        errors = validateLookupValueCol(errors, sourceMappers.get(OrderImportColumns.PROJECT_NAME), "PROJECT_NAME", tenantId);
        //provider
        errors = validateLookupValueCol(errors, sourceMappers.get(OrderImportColumns.PROVIDER), "PROVIDER", tenantId);
        //service billed to
        errors = validateLookupValueCol(errors, sourceMappers.get(OrderImportColumns.SERVICE_BILLED_TO), "SERVICE_BILLED_TO", tenantId);
        //contract term
        errors = validateLookupValueCol(errors, sourceMappers.get(OrderImportColumns.CONTRACT_TERM), "CONTRACT_TERM", tenantId);
        //upload speed
        errors = validateLookupValueCol(errors, sourceMappers.get(OrderImportColumns.UPLOAD_SPEED), "SPEED", tenantId);
        //download speed
        errors = validateLookupValueCol(errors, sourceMappers.get(OrderImportColumns.DOWNLOAD_SPEED), "SPEED", tenantId);
        //media type
        errors = validateLookupValueCol(errors, sourceMappers.get(OrderImportColumns.MEDIA_TYPE), "MEDIA_TYPE", tenantId);
        //parent tsd
        errors = validateLookupValueCol(errors, sourceMappers.get(OrderImportColumns.PARENT_TSD), "PARENT_TSD", tenantId);
        //additional ip block
        errors = validateLookupValueCol(errors, sourceMappers.get(OrderImportColumns.ADDITIONAL_IP_BLOCK), "ADDITIONAL_IP_BLOCK", tenantId);
        //sub-agent
        errors = validateLookupValueCol(errors, sourceMappers.get(OrderImportColumns.SUB_AGENT), "AGENCY_NAME", tenantId);
        //commission payment type
        errors = validateLookupValueCol(errors, sourceMappers.get(OrderImportColumns.COMMISSION_PAYMENT_TYPE), "COMMISSIONS_PAYMENT_TYPE", tenantId);
        //lumen region
        errors = validateLookupValueCol(errors, sourceMappers.get(OrderImportColumns.LUMEN_REGION), "LUMEN_REGION", tenantId);
        //field services provider
        errors = validateLookupValueCol(errors, sourceMappers.get(OrderImportColumns.FIELD_SERVICES_PROVIDER), "FIELD_SERVICES_PROVIDER", tenantId);

        //verify date columns can be parsed
        //customer requested install date
        errors = validateDateCol(errors, sourceMappers.get(OrderImportColumns.CUSTOMER_REQUESTED_INSTALL_DATE));
        //contract signed date
        errors = validateDateCol(errors, sourceMappers.get(OrderImportColumns.CONTRACT_SIGNED_DATE));


        //verify decimal columns can be parsed
        //mrc
        errors = validateBigDecimalCol(errors, sourceMappers.get(OrderImportColumns.MRC));
        //nrc
        errors = validateBigDecimalCol(errors, sourceMappers.get(OrderImportColumns.NRC));
        //arc
        errors = validateBigDecimalCol(errors, sourceMappers.get(OrderImportColumns.ANNUAL_NRC));
        //commissionable mrc
        errors = validateBigDecimalCol(errors, sourceMappers.get(OrderImportColumns.COMMISSIONABLE_MRC));
        //commissionable nrc
        errors = validateBigDecimalCol(errors, sourceMappers.get(OrderImportColumns.COMMISSIONABLE_NRC));
        //commissionable arc
        errors = validateBigDecimalCol(errors, sourceMappers.get(OrderImportColumns.COMMISSIONABLE_ARC));
        //spiff amount
        errors = validateBigDecimalCol(errors, sourceMappers.get(OrderImportColumns.SPIFF_AMOUNT));
        //commission reduction percent
        errors = validateBigDecimalCol(errors, sourceMappers.get(OrderImportColumns.COMMISSION_REDUCTION_PERCENT));
        //sub agent percent
        errors = validateBigDecimalCol(errors, sourceMappers.get(OrderImportColumns.SUB_AGENT_PERCENT));
        //company referral percent
        errors = validateBigDecimalCol(errors, sourceMappers.get(OrderImportColumns.COMPANY_REFERRAL_PERCENT));
        //expected Commission
        errors = validateBigDecimalCol(errors, sourceMappers.get(OrderImportColumns.EXPECTED_COMMISSION));
        //engineer resource allocation
        errors = validateBigDecimalCol(errors, sourceMappers.get(OrderImportColumns.ENGINEER_RESOURCE_ALLOCATION));

        try {
            String linkedBundle = sourceMappers.get(OrderImportColumns.LINKED_BUNDLED).getNonValidatedValue();
            String linkedBundleParent = sourceMappers.get(OrderImportColumns.PARENT_CLIENT_SERVICE_ID).getNonValidatedValue();
            if (!Strings.isNullOrEmpty(linkedBundle) && Strings.isNullOrEmpty(linkedBundleParent)) {
                    errors.add("Parent Client Service ID is required when the Service is Linked or Bundled");
                    if (!"linked".equalsIgnoreCase(linkedBundle) && !"Bundled".equalsIgnoreCase(linkedBundle)) {
                        errors.add("Invalid value for Linked or Bundled");
                    }
            }
            if (!Strings.isNullOrEmpty(linkedBundleParent) && Strings.isNullOrEmpty(linkedBundle)) {
                    errors.add("Linked or Bundled is required if the Parent Client Service ID is filled in");
            }
        } catch (Exception e) {
            errors.add("Could not parse Linked or Bundled and/or Parent Client Service ID");
        }
        return errors;
    }

    @Override
    protected List<String> getExpectedColumns() {
        return OrderImportColumns.getExpectedColumns();
    }

    @Override
    protected String getTemplateName() {
        return "Order";
    }

    @Override
    protected String getImportTypeName() {
        return "New Services";
    }

    @Override
    protected Map<String, ValidatingSourceMapper> buildSourceMappers(final ExcelAdapter adapter) {
        HashMap<String, ValidatingSourceMapper> sourceMappers = new HashMap<>();
        sourceMappers.put(OrderImportColumns.MASTER_CUSTOMER, new StringSourceMapper(adapter, OrderImportColumns.MASTER_CUSTOMER, 100, true));
        sourceMappers.put(OrderImportColumns.END_CUSTOMER, new StringSourceMapper(adapter, OrderImportColumns.END_CUSTOMER, 100, true));
        sourceMappers.put(OrderImportColumns.CLIENT_ORDER_ID, new StringSourceMapper(adapter, OrderImportColumns.CLIENT_ORDER_ID, 100, true));
        sourceMappers.put(OrderImportColumns.CLIENT_LOCATION_ID, new StringSourceMapper(adapter, OrderImportColumns.CLIENT_LOCATION_ID, 100, true));
        sourceMappers.put(OrderImportColumns.ADDRESS_1, new StringSourceMapper(adapter, OrderImportColumns.ADDRESS_1, 100, false));
        sourceMappers.put(OrderImportColumns.ADDRESS_2, new StringSourceMapper(adapter, OrderImportColumns.ADDRESS_2, 100, false));
        sourceMappers.put(OrderImportColumns.CITY, new StringSourceMapper(adapter, OrderImportColumns.CITY, 100, false));
        sourceMappers.put(OrderImportColumns.STATE_PROVINCE_REGION, new StringSourceMapper(adapter, OrderImportColumns.STATE_PROVINCE_REGION, 100, false));
        sourceMappers.put(OrderImportColumns.ZIP_POSTAL_CODE, new StringSourceMapper(adapter, OrderImportColumns.ZIP_POSTAL_CODE, 100, false));
        sourceMappers.put(OrderImportColumns.COUNTRY, new StringSourceMapper(adapter, OrderImportColumns.COUNTRY, 100, false));
        sourceMappers.put(OrderImportColumns.LCON_NAME, new StringSourceMapper(adapter, OrderImportColumns.LCON_NAME, 100, false));
        sourceMappers.put(OrderImportColumns.LCON_EMAIL, new StringSourceMapper(adapter, OrderImportColumns.LCON_EMAIL, 100, false));
        sourceMappers.put(OrderImportColumns.LCON_PHONE, new StringSourceMapper(adapter, OrderImportColumns.LCON_PHONE, 100, false));
        sourceMappers.put(OrderImportColumns.LOCATION_INFO, new StringSourceMapper(adapter, OrderImportColumns.LOCATION_INFO, 100, false));
        sourceMappers.put(OrderImportColumns.LOCATION_TYPE, new StringSourceMapper(adapter, OrderImportColumns.LOCATION_TYPE, 100, false));
//        sourceMappers.put(OrderImportColumns.LEVEL_OF_EFFORT, new StringSourceMapper(adapter, OrderImportColumns.LEVEL_OF_EFFORT, 100, false));
        sourceMappers.put(OrderImportColumns.SERVICE, new StringSourceMapper(adapter, OrderImportColumns.SERVICE, 100, true));
        sourceMappers.put(OrderImportColumns.CLIENT_SERVICE_ID, new StringSourceMapper(adapter, OrderImportColumns.CLIENT_SERVICE_ID, 100, false));
        sourceMappers.put(OrderImportColumns.DESCRIPTION, new StringSourceMapper(adapter, OrderImportColumns.DESCRIPTION, 500, false));
        sourceMappers.put(OrderImportColumns.QUOTE_ID, new StringSourceMapper(adapter, OrderImportColumns.QUOTE_ID, 500, false));
        sourceMappers.put(OrderImportColumns.PROJECT_NAME, new StringSourceMapper(adapter, OrderImportColumns.PROJECT_NAME, 100, false));
        sourceMappers.put(OrderImportColumns.PROVIDER, new StringSourceMapper(adapter, OrderImportColumns.PROVIDER, 100, false));
        sourceMappers.put(OrderImportColumns.SERVICE_BILLED_TO, new StringSourceMapper(adapter, OrderImportColumns.SERVICE_BILLED_TO, 100, false));
        sourceMappers.put(OrderImportColumns.SUB_PRODUCT_TYPE, new StringSourceMapper(adapter, OrderImportColumns.SUB_PRODUCT_TYPE, 100, false));
        sourceMappers.put(OrderImportColumns.SERVICE_INFO, new StringSourceMapper(adapter, OrderImportColumns.SERVICE_INFO, 100, false));
        sourceMappers.put(OrderImportColumns.SERVICE_TYPE, new StringSourceMapper(adapter, OrderImportColumns.SERVICE_TYPE, 100, false));
        sourceMappers.put(OrderImportColumns.CONTRACT_TERM, new StringSourceMapper(adapter, OrderImportColumns.CONTRACT_TERM, 100, false));
        sourceMappers.put(OrderImportColumns.CONTRACT_SIGNED_DATE, new StringSourceMapper(adapter, OrderImportColumns.CONTRACT_SIGNED_DATE, 100, false));
        sourceMappers.put(OrderImportColumns.PO_NUMBER, new StringSourceMapper(adapter, OrderImportColumns.PO_NUMBER, 100, false));
        sourceMappers.put(OrderImportColumns.MRC, new StringSourceMapper(adapter, OrderImportColumns.MRC, 100, false));
        sourceMappers.put(OrderImportColumns.NRC, new StringSourceMapper(adapter, OrderImportColumns.NRC, 100, false));
        sourceMappers.put(OrderImportColumns.ANNUAL_NRC, new StringSourceMapper(adapter, OrderImportColumns.ANNUAL_NRC, 100, false));
        sourceMappers.put(OrderImportColumns.AUTO_RENEWAL, new StringSourceMapper(adapter, OrderImportColumns.AUTO_RENEWAL, 100, false));
        sourceMappers.put(OrderImportColumns.CO_TERMINUS, new StringSourceMapper(adapter, OrderImportColumns.CO_TERMINUS, 100, false));
        sourceMappers.put(OrderImportColumns.NOTICE_PERIOD_FOR_RENEWAL, new StringSourceMapper(adapter, OrderImportColumns.NOTICE_PERIOD_FOR_RENEWAL, 100, false));
        sourceMappers.put(OrderImportColumns.CONTRACT_INFO, new StringSourceMapper(adapter, OrderImportColumns.CONTRACT_INFO, 100, false));
        sourceMappers.put(OrderImportColumns.CUSTOMER_REQUESTED_INSTALL_DATE, new StringSourceMapper(adapter, OrderImportColumns.CUSTOMER_REQUESTED_INSTALL_DATE, 100, false));
        sourceMappers.put(OrderImportColumns.UPLOAD_SPEED, new StringSourceMapper(adapter, OrderImportColumns.UPLOAD_SPEED, 100, false));
        sourceMappers.put(OrderImportColumns.DOWNLOAD_SPEED, new StringSourceMapper(adapter, OrderImportColumns.DOWNLOAD_SPEED, 100, false));
        sourceMappers.put(OrderImportColumns.MEDIA_TYPE, new StringSourceMapper(adapter, OrderImportColumns.MEDIA_TYPE, 100, false));
        sourceMappers.put(OrderImportColumns.DMARC, new StringSourceMapper(adapter, OrderImportColumns.DMARC, 100, false));
        sourceMappers.put(OrderImportColumns.FIELD_SERVICES_PROVIDER, new StringSourceMapper(adapter, OrderImportColumns.FIELD_SERVICES_PROVIDER, 100, false));
        sourceMappers.put(OrderImportColumns.ADDITIONAL_IP_BLOCK, new StringSourceMapper(adapter, OrderImportColumns.ADDITIONAL_IP_BLOCK, 100, false));
        sourceMappers.put(OrderImportColumns.LINKED_BUNDLED, new StringSourceMapper(adapter, OrderImportColumns.LINKED_BUNDLED, 100, false));
        sourceMappers.put(OrderImportColumns.PARENT_CLIENT_SERVICE_ID, new StringSourceMapper(adapter, OrderImportColumns.PARENT_CLIENT_SERVICE_ID, 100, false));
        sourceMappers.put(OrderImportColumns.BILLING_ADDRESS_1, new StringSourceMapper(adapter, OrderImportColumns.BILLING_ADDRESS_1, 100, false));
        sourceMappers.put(OrderImportColumns.BILLING_ADDRESS_2, new StringSourceMapper(adapter, OrderImportColumns.BILLING_ADDRESS_2, 100, false));
        sourceMappers.put(OrderImportColumns.BILLING_CITY, new StringSourceMapper(adapter, OrderImportColumns.BILLING_CITY, 100, false));
        sourceMappers.put(OrderImportColumns.BILLING_STATE, new StringSourceMapper(adapter, OrderImportColumns.BILLING_STATE, 100, false));
        sourceMappers.put(OrderImportColumns.BILLING_ZIP, new StringSourceMapper(adapter, OrderImportColumns.BILLING_ZIP, 100, false));
        sourceMappers.put(OrderImportColumns.BILLING_COUNTRY, new StringSourceMapper(adapter, OrderImportColumns.BILLING_COUNTRY, 100, false));
        sourceMappers.put(OrderImportColumns.BILLING_EMAIL, new StringSourceMapper(adapter, OrderImportColumns.BILLING_EMAIL, 100, false));
        sourceMappers.put(OrderImportColumns.TECHNICAL_NAME, new StringSourceMapper(adapter, OrderImportColumns.TECHNICAL_NAME, 100, false));
        sourceMappers.put(OrderImportColumns.TECHNICAL_EMAIL, new StringSourceMapper(adapter, OrderImportColumns.TECHNICAL_EMAIL, 100, false));
        sourceMappers.put(OrderImportColumns.TECHNICAL_PHONE, new StringSourceMapper(adapter, OrderImportColumns.TECHNICAL_PHONE, 100, false));
        sourceMappers.put(OrderImportColumns.SALES_NAME, new StringSourceMapper(adapter, OrderImportColumns.SALES_NAME, 100, false));
        sourceMappers.put(OrderImportColumns.SALES_EMAIL, new StringSourceMapper(adapter, OrderImportColumns.SALES_EMAIL, 100, false));
        sourceMappers.put(OrderImportColumns.SALES_PHONE, new StringSourceMapper(adapter, OrderImportColumns.SALES_PHONE, 100, false));
        sourceMappers.put(OrderImportColumns.AUTHORIZATION_NAME, new StringSourceMapper(adapter, OrderImportColumns.AUTHORIZATION_NAME, 100, false));
        sourceMappers.put(OrderImportColumns.AUTHORIZATION_EMAIL, new StringSourceMapper(adapter, OrderImportColumns.AUTHORIZATION_EMAIL, 100, false));
        sourceMappers.put(OrderImportColumns.AUTHORIZATION_PHONE, new StringSourceMapper(adapter, OrderImportColumns.AUTHORIZATION_PHONE, 100, false));
        sourceMappers.put(OrderImportColumns.COMMISSIONABLE_MRC, new StringSourceMapper(adapter, OrderImportColumns.COMMISSIONABLE_MRC, 100, false));
        sourceMappers.put(OrderImportColumns.COMMISSIONABLE_NRC, new StringSourceMapper(adapter, OrderImportColumns.COMMISSIONABLE_NRC, 100, false));
        sourceMappers.put(OrderImportColumns.COMMISSIONABLE_ARC, new StringSourceMapper(adapter, OrderImportColumns.COMMISSIONABLE_ARC, 100, false));
        sourceMappers.put(OrderImportColumns.SUB_AGENT, new StringSourceMapper(adapter, OrderImportColumns.SUB_AGENT, 100, false));
        sourceMappers.put(OrderImportColumns.SUB_AGENT_PERCENT, new StringSourceMapper(adapter, OrderImportColumns.SUB_AGENT_PERCENT, 100, false));
        sourceMappers.put(OrderImportColumns.SUBMITTED_IN_ADV_TO_PROVIDER, new StringSourceMapper(adapter, OrderImportColumns.SUBMITTED_IN_ADV_TO_PROVIDER, 100, false));
        sourceMappers.put(OrderImportColumns.PARENT_TSD, new StringSourceMapper(adapter, OrderImportColumns.PARENT_TSD, 100, false));
        sourceMappers.put(OrderImportColumns.SUBMITTED_IN_ADV_TO_TSD, new StringSourceMapper(adapter, OrderImportColumns.SUBMITTED_IN_ADV_TO_TSD, 100, false));
        sourceMappers.put(OrderImportColumns.REFERRAL_NAME_COMPANY, new StringSourceMapper(adapter, OrderImportColumns.REFERRAL_NAME_COMPANY, 100, false));
        sourceMappers.put(OrderImportColumns.COMPANY_REFERRAL_PERCENT, new StringSourceMapper(adapter, OrderImportColumns.COMPANY_REFERRAL_PERCENT, 100, false));
        sourceMappers.put(OrderImportColumns.COMMISSION_PAYMENT_TYPE, new StringSourceMapper(adapter, OrderImportColumns.COMMISSION_PAYMENT_TYPE, 100, false));
        sourceMappers.put(OrderImportColumns.EXPECTED_COMMISSION, new StringSourceMapper(adapter, OrderImportColumns.EXPECTED_COMMISSION, 100, false));
        sourceMappers.put(OrderImportColumns.CIE_TEAMED_DEAL_INFO, new StringSourceMapper(adapter, OrderImportColumns.CIE_TEAMED_DEAL_INFO, 100, false));
        sourceMappers.put(OrderImportColumns.COMMISSION_REDUCTION_PERCENT, new StringSourceMapper(adapter, OrderImportColumns.COMMISSION_REDUCTION_PERCENT, 100, false));
        sourceMappers.put(OrderImportColumns.COMMISSIONS_ICB, new StringSourceMapper(adapter, OrderImportColumns.COMMISSIONS_ICB, 100, false));
        sourceMappers.put(OrderImportColumns.INTERNAL_COMMISSIONS_COMMENTS, new StringSourceMapper(adapter, OrderImportColumns.INTERNAL_COMMISSIONS_COMMENTS, 100, false));
        sourceMappers.put(OrderImportColumns.SFA_OPPORTUNITY_NUM, new StringSourceMapper(adapter, OrderImportColumns.SFA_OPPORTUNITY_NUM, 100, false));
        sourceMappers.put(OrderImportColumns.NET_PROVIDER_POINTS, new StringSourceMapper(adapter, OrderImportColumns.NET_PROVIDER_POINTS, 100, false));
        sourceMappers.put(OrderImportColumns.PROMOTIONS, new StringSourceMapper(adapter, OrderImportColumns.PROMOTIONS, 100, false));
        sourceMappers.put(OrderImportColumns.SPIFF_AMOUNT, new StringSourceMapper(adapter, OrderImportColumns.SPIFF_AMOUNT, 100, false));
        sourceMappers.put(OrderImportColumns.ENGINEER_RESOURCE, new StringSourceMapper(adapter, OrderImportColumns.ENGINEER_RESOURCE, 100, false));
        sourceMappers.put(OrderImportColumns.ENGINEER_RESOURCE_ALLOCATION, new StringSourceMapper(adapter, OrderImportColumns.ENGINEER_RESOURCE_ALLOCATION, 100, false));
        sourceMappers.put(OrderImportColumns.LUMEN_CIE_REP, new StringSourceMapper(adapter, OrderImportColumns.LUMEN_CIE_REP, 100, false));
        sourceMappers.put(OrderImportColumns.LUMEN_REGION, new StringSourceMapper(adapter, OrderImportColumns.LUMEN_REGION, 100, false));
        sourceMappers.put(OrderImportColumns.IN_REGION_NEED_TAX_ID, new StringSourceMapper(adapter, OrderImportColumns.IN_REGION_NEED_TAX_ID, 100, false));
        sourceMappers.put(OrderImportColumns.IN_OUT_REGION_NEED_SALES_ID, new StringSourceMapper(adapter, OrderImportColumns.IN_OUT_REGION_NEED_SALES_ID, 100, false));
        sourceMappers.put(OrderImportColumns.LUMEN_AG, new StringSourceMapper(adapter, OrderImportColumns.LUMEN_AG, 100, false));
        sourceMappers.put(OrderImportColumns.ORDER_NUMBER, new StringSourceMapper(adapter, OrderImportColumns.ORDER_NUMBER, 100, false));
        sourceMappers.put(OrderImportColumns.BTN, new StringSourceMapper(adapter, OrderImportColumns.BTN, 100, false));
        return sourceMappers;
    }

}
