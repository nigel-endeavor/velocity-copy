package com.endeavorms.velocity.qto.fileimport.mpls;

import com.google.common.base.Strings;
import com.endeavorms.velocity.qto.RecordSource;
import com.endeavorms.velocity.qto.common.adapter.ExcelAdapter;
import com.endeavorms.velocity.qto.common.mapping.StringSourceMapper;
import com.endeavorms.velocity.qto.common.mapping.ValidatingSourceMapper;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.fileimport.AbstractInventoryServiceImporter;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivity;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstanceManager;
import com.endeavorms.velocity.qto.note.ServiceNoteManager;
import com.endeavorms.velocity.qto.service.OrderType;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.mpls.MplsService;
import com.endeavorms.velocity.qto.service.mpls.MplsServiceManager;

import org.springframework.stereotype.Component;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rcasey
 * @since 5/17/2024
 */
@Component
@TransactionManagement(TransactionManagementType.BEAN)
public class MplsImporter extends AbstractInventoryServiceImporter {

    @Inject
    private ServiceManager serviceManager;

    @Inject
    private MplsServiceManager mplsServiceManager;

    @Inject
    private ServiceNoteManager serviceNoteManager;

    @Inject
    private ServiceMilestoneInstanceManager sMilestoneInstanceManager;

    @Override
    protected void importRow(final Map<String, ValidatingSourceMapper> sourceMappers, final ImportActivity importActivity) throws Exception {
        Date inventoryAddedDate;
        Long tenantId = importActivity.getTenantId();
        if (Strings.isNullOrEmpty(sourceMappers.get(MplsImportColumns.DATE_ENTERED_IN_INVENTORY).getValue())) {
            inventoryAddedDate = new Date();
        } else {
            inventoryAddedDate = parseDate(sourceMappers.get(MplsImportColumns.DATE_ENTERED_IN_INVENTORY).getValue());
        }
        Company endCustomer = handleEndCustomer(sourceMappers, tenantId);
        //Location Info
        Location location = handleOrderAndLocation(sourceMappers, tenantId, endCustomer,
                MplsImportColumns.LOCATION_INFO, MplsImportColumns.LOCATION_TYPE,
                MplsImportColumns.ADDRESS_1, MplsImportColumns.ADDRESS_2, MplsImportColumns.CITY,
                MplsImportColumns.STATE_PROVINCE_REGION, MplsImportColumns.ZIP_POSTAL_CODE,
                MplsImportColumns.COUNTRY, MplsImportColumns.TIME_ZONE);

        //Service
        MplsService service = new MplsService();
        service.setCurrentInventory(true);
        service.setBillable(true);
        service.setActive(true);
        service.setOrderType(OrderType.NEW.name());
        service.setOrderId(location.getOrderId());
        service.setLocationId(location.getId());
        service.setRecordSource(RecordSource.INVENTORY_IMPORT.getName());
        service.setClientServiceId(sourceMappers.get(MplsImportColumns.CLIENT_SERVICE_ID).getValue());
        service.setDescription(sourceMappers.get(MplsImportColumns.SERVICE_DESCRIPTION).getValue());
        service.setQuoteSolutionId(sourceMappers.get(MplsImportColumns.QUOTE_SOLUTION_ID).getValue());
        service.setProjectName(sourceMappers.get(MplsImportColumns.PROJECT_NAME).getValue());
        service.setServiceBilledTo(lookupValueMap.get(sourceMappers.get(MplsImportColumns.SERVICE_BILLED_TO).getValue()));
        service.setProvider(lookupValueMap.get(sourceMappers.get(MplsImportColumns.PROVIDER).getValue()));
        service.setUnderlyingProvider(sourceMappers.get(MplsImportColumns.UNDERLYING_PROVIDER).getValue());
        service.setSubProductType(sourceMappers.get(MplsImportColumns.SUB_PRODUCT_TYPE).getValue());
        service.setLastMileProvider(lookupValueMap.get(sourceMappers.get(MplsImportColumns.LAST_MILE_PROVIDER).getValue()));
        service.setContractTerm(lookupValueMap.get(sourceMappers.get(MplsImportColumns.CONTRACT_TERM).getValue()));
        service.setContractSignedDate(parseDate(sourceMappers.get(MplsImportColumns.CONTRACT_SIGNED_DATE).getValue()));
        service.setCircuitTermEndDate(parseDate(sourceMappers.get(MplsImportColumns.CONTRACT_END_DATE).getValue()));
        service.setPoNumber(sourceMappers.get(MplsImportColumns.PO_NUMBER).getValue());
        service.setContractInfo(sourceMappers.get(MplsImportColumns.CONTRACT_INFO).getValue());
        service.setMrc(new BigDecimal(sourceMappers.get(MplsImportColumns.MRC).getValue()));
        service.setNrc(new BigDecimal(sourceMappers.get(MplsImportColumns.NRC).getValue()));
        service.setAnnualRecurringCost(new BigDecimal(sourceMappers.get(MplsImportColumns.ANNUAL_RECURRING_COST).getValue()));
        service.setUploadSpeed(lookupValueMap.get(sourceMappers.get(MplsImportColumns.MPLS_ACCESS_SPEED).getValue()));
        service.setDownloadSpeed(lookupValueMap.get(sourceMappers.get(MplsImportColumns.MPLS_ACCESS_SPEED).getValue()));
        service.setPortSpeed(lookupValueMap.get(sourceMappers.get(MplsImportColumns.MPLS_PORT_SPEED).getValue()));
        service.setProviderOrderNum(sourceMappers.get(MplsImportColumns.PROVIDER_ORDER_NUMBER).getValue());
        service.setBillCycle(parseLong(sourceMappers.get(MplsImportColumns.BILL_CYCLE).getValue()));
        service.setSummaryBill(sourceMappers.get(MplsImportColumns.SUMMARY_BILL).getValue());
        service.setAccountNumber(sourceMappers.get(MplsImportColumns.ACCOUNT_NUMBER_BAN).getValue());
        service.setProviderCircuitId(sourceMappers.get(MplsImportColumns.PROVIDER_CIRCUIT_ID).getValue());
        service.setAccessCircuitId(sourceMappers.get(MplsImportColumns.ACCESS_CIRCUIT_ID).getValue());
        service.setPortCircuitId(sourceMappers.get(MplsImportColumns.PORT_CIRCUIT_ID).getValue());
        service.setMplsType(lookupValueMap.get(sourceMappers.get(MplsImportColumns.MPLS_TYPE).getValue()));
        service.setMediaType(lookupValueMap.get(sourceMappers.get(MplsImportColumns.MEDIA_TYPE).getValue()));
        service.setInterfaceConnector(lookupValueMap.get(sourceMappers.get(MplsImportColumns.INTERFACE_CONNECTOR).getValue()));
        service.setProviderActivationMethod(lookupValueMap.get(sourceMappers.get(MplsImportColumns.PROVIDER_ORDER_ACTIVATION_METHOD).getValue()));
        service.setActivationLink(sourceMappers.get(MplsImportColumns.ACTIVATION_LINK).getValue());
        service.setActivationPhone(sourceMappers.get(MplsImportColumns.ACTIVATION_PHONE).getValue());
        service.setDmarc(sourceMappers.get(MplsImportColumns.DMARC).getValue());
        service.setNpaNxx(sourceMappers.get(MplsImportColumns.NPA_NXX).getValue());
        service.setLocationHours(sourceMappers.get(MplsImportColumns.LOCATION_HOURS).getValue());
        service.setCerIps(sourceMappers.get(MplsImportColumns.CER_IPS).getValue());
        service.setPerIps(sourceMappers.get(MplsImportColumns.PER_IPS).getValue());
        service.setVlanTag1(sourceMappers.get(MplsImportColumns.VLAN_TAG_1).getValue());
        service.setVlanTag2(sourceMappers.get(MplsImportColumns.VLAN_TAG_2).getValue());
        service.setVlanTag3(sourceMappers.get(MplsImportColumns.VLAN_TAG_3).getValue());
        service.setVlanTag4(sourceMappers.get(MplsImportColumns.VLAN_TAG_4).getValue());
        service.setOtherTechnicalNotes(sourceMappers.get(MplsImportColumns.OTHER_TECHNICAL_NOTES).getValue());
        service.setIpFormat(parseIpFormat(sourceMappers.get(MplsImportColumns.IP_FORMAT).getValue()));
        service.setAdditionalIpBlock(sourceMappers.get(MplsImportColumns.WAN_BLOCK).getValue());
        service.setWanIps(sourceMappers.get(MplsImportColumns.WAN_IPS).getValue());
        service.setWanGateway(sourceMappers.get(MplsImportColumns.WAN_GATEWAY).getValue());
        service.setWanSubnet(sourceMappers.get(MplsImportColumns.WAN_SUBNET).getValue());
        service.setLanBlock(sourceMappers.get(MplsImportColumns.LAN_BLOCK).getValue());
        service.setLanIps(sourceMappers.get(MplsImportColumns.LAN_IPS).getValue());
        service.setLanGateway(sourceMappers.get(MplsImportColumns.LAN_GATEWAY).getValue());
        service.setLanSubnet(sourceMappers.get(MplsImportColumns.LAN_SUBNET).getValue());
        service.setDns1(sourceMappers.get(MplsImportColumns.DNS_1).getValue());
        service.setDns2(sourceMappers.get(MplsImportColumns.DNS_2).getValue());
        service.setTspCode(sourceMappers.get(MplsImportColumns.TSP_CODE).getValue());
        service.setTspCodeExpirationDate(parseDate(sourceMappers.get(MplsImportColumns.TSP_CODE_EXPIRATION_DATE).getValue()));
        service.setManagedService("Yes".equals(parseYesNo(sourceMappers.get(MplsImportColumns.MANAGED_SERVICE).getValue())));
        service.setProductionImpacting("Yes".equals(parseYesNo(sourceMappers.get(MplsImportColumns.PRODUCTION_IMPACTING).getValue())));
        if ("MTM".equals(service.getContractTerm()) || service.getCircuitTermEndDate() == null) {
            service.setIgnoreForRenewals(true);
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(MplsImportColumns.ORIGINAL_MRC).getNonValidatedValue())) {
            service.setOriginalMrc(new BigDecimal(sourceMappers.get(MplsImportColumns.ORIGINAL_MRC).getValue()));
        }
        handleCommonService(service, sourceMappers);
        service = mplsServiceManager.create(service);

        if (!Strings.isNullOrEmpty(sourceMappers.get(MplsImportColumns.CIRCUIT_INSTALLED_DATE).getNonValidatedValue())) {
            sMilestoneInstanceManager.create(service.getId(), "DATA_PROVISIONING_COMPLETE", parseDate(sourceMappers.get(MplsImportColumns.CIRCUIT_INSTALLED_DATE).getValue()));
        }
        if (!sMilestoneInstanceManager.doesMilestoneExist(service.getId(), "COMPLETE")) {
            sMilestoneInstanceManager.create(service.getId(), "COMPLETE", inventoryAddedDate);
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(MplsImportColumns.NOTES_TO_IMPORT).getNonValidatedValue())) {
            serviceNoteManager.create(service.getId(), sourceMappers.get(MplsImportColumns.NOTES_TO_IMPORT).getValue(), "MPLS", importActivity.getFileAttachment().getUploadedByUserName());
        }
    }

    @Override
    protected List<String> validateRow(Map<String, ValidatingSourceMapper> sourceMappers, Long tenantId) {
        List<String> errors = super.validateRow(sourceMappers, tenantId);

        //validate dropdown values
        //timezone
        errors = validateLookupValueCol(errors, sourceMappers.get(MplsImportColumns.TIME_ZONE), "NA_TIME_ZONE", tenantId);
        //Circuit Owner
        errors = validateLookupValueCol(errors, sourceMappers.get(MplsImportColumns.SERVICE_BILLED_TO), "SERVICE_BILLED_TO", tenantId);
        //PROVIDER
        errors = validateLookupValueCol(errors, sourceMappers.get(MplsImportColumns.PROVIDER), "PROVIDER", tenantId);
        //underlying provider
        errors = validateLookupValueCol(errors, sourceMappers.get(MplsImportColumns.UNDERLYING_PROVIDER), "UNDERLYING_PROVIDER", tenantId);
        //last mile provider
        errors = validateLookupValueCol(errors, sourceMappers.get(MplsImportColumns.LAST_MILE_PROVIDER), "PROVIDER", tenantId);
        //Contract Term
        errors = validateLookupValueCol(errors, sourceMappers.get(MplsImportColumns.CONTRACT_TERM), "CONTRACT_TERM", tenantId);
        //Port Speed
        errors = validateLookupValueCol(errors, sourceMappers.get(MplsImportColumns.MPLS_PORT_SPEED), "SPEED", tenantId);
        //Access Speed
        errors = validateLookupValueCol(errors, sourceMappers.get(MplsImportColumns.MPLS_ACCESS_SPEED), "SPEED", tenantId);
        //MPLS Type
        errors = validateLookupValueCol(errors, sourceMappers.get(MplsImportColumns.MPLS_TYPE), "MPLS_TYPE", tenantId);
        //Media Type
        errors = validateLookupValueCol(errors, sourceMappers.get(MplsImportColumns.MEDIA_TYPE), "MEDIA_TYPE", tenantId);
        //Interface Connector
        errors = validateLookupValueCol(errors, sourceMappers.get(MplsImportColumns.INTERFACE_CONNECTOR), "INTERFACE_CONNECTOR", tenantId);
        //Provider Order Activation Method
        errors = validateLookupValueCol(errors, sourceMappers.get(MplsImportColumns.PROVIDER_ORDER_ACTIVATION_METHOD), "PROVIDER_ACTIVATION_METHOD", tenantId);
        //IP Format
        errors = validateIpFormatCol(errors, sourceMappers.get(MplsImportColumns.IP_FORMAT));
        //WAN BLOCK
        errors = validateLookupValueCol(errors, sourceMappers.get(MplsImportColumns.WAN_BLOCK), "ADDITIONAL_IP_BLOCK", tenantId);
        //LAN Block
        errors = validateLookupValueCol(errors, sourceMappers.get(MplsImportColumns.LAN_BLOCK), "ADDITIONAL_IP_BLOCK", tenantId);

        //verify the date columns can be parsed
        //Contract Signed Date
        errors = validateDateCol(errors, sourceMappers.get(MplsImportColumns.CONTRACT_SIGNED_DATE));
        //Contract End Date
        errors = validateDateCol(errors, sourceMappers.get(MplsImportColumns.CONTRACT_END_DATE));
        //Date Entered in Inventory
        errors = validateDateCol(errors, sourceMappers.get(MplsImportColumns.DATE_ENTERED_IN_INVENTORY));
        //Circuit Installed Date
        errors = validateDateCol(errors, sourceMappers.get(MplsImportColumns.CIRCUIT_INSTALLED_DATE));

        //verify the decimal columns can be parsed
        //MRC
        errors = validateBigDecimalCol(errors, sourceMappers.get(MplsImportColumns.MRC));
        //NRC
        errors = validateBigDecimalCol(errors, sourceMappers.get(MplsImportColumns.NRC));
        //Annual Recurring Cost
        errors = validateBigDecimalCol(errors, sourceMappers.get(MplsImportColumns.ANNUAL_RECURRING_COST));
        //original mrc
        errors = validateBigDecimalCol(errors, sourceMappers.get(MplsImportColumns.ORIGINAL_MRC));
        
        return errors;
    }

    @Override
    protected List<String> getExpectedColumns() {
        return MplsImportColumns.getExpectedColumns();
    }

    @Override
    protected String getTemplateName() {
        return "MPLS";
    }

    @Override
    protected String getImportTypeName() {
        return "MPLS Service";
    }

    @Override
    protected Map<String, ValidatingSourceMapper> buildSourceMappers(final ExcelAdapter adapter) {
        HashMap<String, ValidatingSourceMapper> sourceMappers = new HashMap<>();
        handleCommonSourceMappers(sourceMappers, adapter);
        sourceMappers.put(MplsImportColumns.MASTER_CUSTOMER, new StringSourceMapper(adapter, MplsImportColumns.MASTER_CUSTOMER, 100, true));
        sourceMappers.put(MplsImportColumns.END_CUSTOMER, new StringSourceMapper(adapter, MplsImportColumns.END_CUSTOMER, 100, true));
        sourceMappers.put(MplsImportColumns.I90_PROJECT_MANAGER, new StringSourceMapper(adapter, MplsImportColumns.I90_PROJECT_MANAGER, 100, false));
        sourceMappers.put(MplsImportColumns.CLIENT_LOCATION_ID, new StringSourceMapper(adapter, MplsImportColumns.CLIENT_LOCATION_ID, 100, true));
        sourceMappers.put(MplsImportColumns.CLIENT_SERVICE_ID, new StringSourceMapper(adapter, MplsImportColumns.CLIENT_SERVICE_ID, 100, false));
        sourceMappers.put(MplsImportColumns.ADDRESS_1, new StringSourceMapper(adapter, MplsImportColumns.ADDRESS_1, 100, false));
        sourceMappers.put(MplsImportColumns.ADDRESS_2, new StringSourceMapper(adapter, MplsImportColumns.ADDRESS_2, 100, false));
        sourceMappers.put(MplsImportColumns.CITY, new StringSourceMapper(adapter, MplsImportColumns.CITY, 100, false));
        sourceMappers.put(MplsImportColumns.STATE_PROVINCE_REGION, new StringSourceMapper(adapter, MplsImportColumns.STATE_PROVINCE_REGION, 100, false));
        sourceMappers.put(MplsImportColumns.ZIP_POSTAL_CODE, new StringSourceMapper(adapter, MplsImportColumns.ZIP_POSTAL_CODE, 100, false));
        sourceMappers.put(MplsImportColumns.COUNTRY, new StringSourceMapper(adapter, MplsImportColumns.COUNTRY, 100, false));
        sourceMappers.put(MplsImportColumns.TIME_ZONE, new StringSourceMapper(adapter, MplsImportColumns.TIME_ZONE, 100, false));
        sourceMappers.put(MplsImportColumns.LOCATION_DESCRIPTION, new StringSourceMapper(adapter, MplsImportColumns.LOCATION_DESCRIPTION, 100, false));
        sourceMappers.put(MplsImportColumns.SERVICE_DESCRIPTION, new StringSourceMapper(adapter, MplsImportColumns.SERVICE_DESCRIPTION, 500, false));
        sourceMappers.put(MplsImportColumns.LCON_NAME, new StringSourceMapper(adapter, MplsImportColumns.LCON_NAME, 100, false));
        sourceMappers.put(MplsImportColumns.LCON_EMAIL, new StringSourceMapper(adapter, MplsImportColumns.LCON_EMAIL, 100, false));
        sourceMappers.put(MplsImportColumns.LCON_PHONE, new StringSourceMapper(adapter, MplsImportColumns.LCON_PHONE, 100, false));
        sourceMappers.put(MplsImportColumns.LOCATION_INFO, new StringSourceMapper(adapter, MplsImportColumns.LOCATION_INFO, 100, false));
        sourceMappers.put(MplsImportColumns.LOCATION_TYPE, new StringSourceMapper(adapter, MplsImportColumns.LOCATION_TYPE, 100, false));
        sourceMappers.put(MplsImportColumns.QUOTE_SOLUTION_ID, new StringSourceMapper(adapter, MplsImportColumns.QUOTE_SOLUTION_ID, 100, false));
        sourceMappers.put(MplsImportColumns.PROJECT_NAME, new StringSourceMapper(adapter, MplsImportColumns.PROJECT_NAME, 100, false));
        sourceMappers.put(MplsImportColumns.SERVICE_BILLED_TO, new StringSourceMapper(adapter, MplsImportColumns.SERVICE_BILLED_TO, 100, true));
        sourceMappers.put(MplsImportColumns.PROVIDER, new StringSourceMapper(adapter, MplsImportColumns.PROVIDER, 100, true));
        sourceMappers.put(MplsImportColumns.UNDERLYING_PROVIDER, new StringSourceMapper(adapter, MplsImportColumns.UNDERLYING_PROVIDER, 100, false));
        sourceMappers.put(MplsImportColumns.SUB_PRODUCT_TYPE, new StringSourceMapper(adapter, MplsImportColumns.SUB_PRODUCT_TYPE, 100, false));
        sourceMappers.put(MplsImportColumns.PO_NUMBER, new StringSourceMapper(adapter, MplsImportColumns.PO_NUMBER, 100, false));
        sourceMappers.put(MplsImportColumns.MANAGED_SERVICE, new StringSourceMapper(adapter, MplsImportColumns.MANAGED_SERVICE, 100, false));
        sourceMappers.put(MplsImportColumns.PRODUCTION_IMPACTING, new StringSourceMapper(adapter, MplsImportColumns.PRODUCTION_IMPACTING, 100, false));
        sourceMappers.put(MplsImportColumns.LAST_MILE_PROVIDER, new StringSourceMapper(adapter, MplsImportColumns.LAST_MILE_PROVIDER, 100, false));
        sourceMappers.put(MplsImportColumns.CONTRACT_TERM, new StringSourceMapper(adapter, MplsImportColumns.CONTRACT_TERM, 100, false));
        sourceMappers.put(MplsImportColumns.CONTRACT_SIGNED_DATE, new StringSourceMapper(adapter, MplsImportColumns.CONTRACT_SIGNED_DATE, 100, false));
        sourceMappers.put(MplsImportColumns.CONTRACT_END_DATE, new StringSourceMapper(adapter, MplsImportColumns.CONTRACT_END_DATE, 100, false));
        sourceMappers.put(MplsImportColumns.CONTRACT_INFO, new StringSourceMapper(adapter, MplsImportColumns.CONTRACT_INFO, 500, false));
        sourceMappers.put(MplsImportColumns.MRC, new StringSourceMapper(adapter, MplsImportColumns.MRC, 100, true));
        sourceMappers.put(MplsImportColumns.NRC, new StringSourceMapper(adapter, MplsImportColumns.NRC, 100, true));
        sourceMappers.put(MplsImportColumns.ANNUAL_RECURRING_COST, new StringSourceMapper(adapter, MplsImportColumns.ANNUAL_RECURRING_COST, 100, true));
        sourceMappers.put(MplsImportColumns.ORIGINAL_MRC, new StringSourceMapper(adapter, MplsImportColumns.ORIGINAL_MRC, 100, false));
        sourceMappers.put(MplsImportColumns.MPLS_ACCESS_SPEED, new StringSourceMapper(adapter, MplsImportColumns.MPLS_ACCESS_SPEED, 100, false));
        sourceMappers.put(MplsImportColumns.MPLS_PORT_SPEED, new StringSourceMapper(adapter, MplsImportColumns.MPLS_PORT_SPEED, 100, false));
        sourceMappers.put(MplsImportColumns.DATE_ENTERED_IN_INVENTORY, new StringSourceMapper(adapter, MplsImportColumns.DATE_ENTERED_IN_INVENTORY, 100, false));
        sourceMappers.put(MplsImportColumns.PROVIDER_ORDER_NUMBER, new StringSourceMapper(adapter, MplsImportColumns.PROVIDER_ORDER_NUMBER, 100, false));
        sourceMappers.put(MplsImportColumns.BILL_CYCLE, new StringSourceMapper(adapter, MplsImportColumns.BILL_CYCLE, 100, false));
        sourceMappers.put(MplsImportColumns.ACCOUNT_NUMBER_BAN, new StringSourceMapper(adapter, MplsImportColumns.ACCOUNT_NUMBER_BAN, 100, false));
        sourceMappers.put(MplsImportColumns.SUMMARY_BILL, new StringSourceMapper(adapter, MplsImportColumns.SUMMARY_BILL, 100, false));
        sourceMappers.put(MplsImportColumns.PROVIDER_CIRCUIT_ID, new StringSourceMapper(adapter, MplsImportColumns.PROVIDER_CIRCUIT_ID, 100, false));
        sourceMappers.put(MplsImportColumns.ACCESS_CIRCUIT_ID, new StringSourceMapper(adapter, MplsImportColumns.ACCESS_CIRCUIT_ID, 100, false));
        sourceMappers.put(MplsImportColumns.PORT_CIRCUIT_ID, new StringSourceMapper(adapter, MplsImportColumns.PORT_CIRCUIT_ID, 100, false));
        sourceMappers.put(MplsImportColumns.MPLS_TYPE, new StringSourceMapper(adapter, MplsImportColumns.MPLS_TYPE, 100, false));
        sourceMappers.put(MplsImportColumns.CIRCUIT_INSTALLED_DATE, new StringSourceMapper(adapter, MplsImportColumns.CIRCUIT_INSTALLED_DATE, 100, false));
        sourceMappers.put(MplsImportColumns.MEDIA_TYPE, new StringSourceMapper(adapter, MplsImportColumns.MEDIA_TYPE, 100, false));
        sourceMappers.put(MplsImportColumns.INTERFACE_CONNECTOR, new StringSourceMapper(adapter, MplsImportColumns.INTERFACE_CONNECTOR, 100, false));
        sourceMappers.put(MplsImportColumns.PROVIDER_ORDER_ACTIVATION_METHOD, new StringSourceMapper(adapter, MplsImportColumns.PROVIDER_ORDER_ACTIVATION_METHOD, 100, false));
        sourceMappers.put(MplsImportColumns.ACTIVATION_LINK, new StringSourceMapper(adapter, MplsImportColumns.ACTIVATION_LINK, 100, false));
        sourceMappers.put(MplsImportColumns.ACTIVATION_PHONE, new StringSourceMapper(adapter, MplsImportColumns.ACTIVATION_PHONE, 100, false));
        sourceMappers.put(MplsImportColumns.DMARC, new StringSourceMapper(adapter, MplsImportColumns.DMARC, 100, false));
        sourceMappers.put(MplsImportColumns.NPA_NXX, new StringSourceMapper(adapter, MplsImportColumns.NPA_NXX, 100, false));
        sourceMappers.put(MplsImportColumns.LOCATION_HOURS, new StringSourceMapper(adapter, MplsImportColumns.LOCATION_HOURS, 100, false));
        sourceMappers.put(MplsImportColumns.ROUTING_PROTOCOL, new StringSourceMapper(adapter, MplsImportColumns.ROUTING_PROTOCOL, 100, false));
        sourceMappers.put(MplsImportColumns.CER_IPS, new StringSourceMapper(adapter, MplsImportColumns.CER_IPS, 100, false));
        sourceMappers.put(MplsImportColumns.PER_IPS, new StringSourceMapper(adapter, MplsImportColumns.PER_IPS, 100, false));
        sourceMappers.put(MplsImportColumns.VLAN_TAG_1, new StringSourceMapper(adapter, MplsImportColumns.VLAN_TAG_1, 100, false));
        sourceMappers.put(MplsImportColumns.VLAN_TAG_2, new StringSourceMapper(adapter, MplsImportColumns.VLAN_TAG_2, 100, false));
        sourceMappers.put(MplsImportColumns.VLAN_TAG_3, new StringSourceMapper(adapter, MplsImportColumns.VLAN_TAG_3, 100, false));
        sourceMappers.put(MplsImportColumns.VLAN_TAG_4, new StringSourceMapper(adapter, MplsImportColumns.VLAN_TAG_4, 100, false));
        sourceMappers.put(MplsImportColumns.OTHER_TECHNICAL_NOTES, new StringSourceMapper(adapter, MplsImportColumns.OTHER_TECHNICAL_NOTES, 1000, false));
        sourceMappers.put(MplsImportColumns.IP_FORMAT, new StringSourceMapper(adapter, MplsImportColumns.IP_FORMAT, 100, false));
        sourceMappers.put(MplsImportColumns.WAN_BLOCK, new StringSourceMapper(adapter, MplsImportColumns.WAN_BLOCK, 100, false));
        sourceMappers.put(MplsImportColumns.WAN_IPS, new StringSourceMapper(adapter, MplsImportColumns.WAN_IPS, 100, false));
        sourceMappers.put(MplsImportColumns.WAN_GATEWAY, new StringSourceMapper(adapter, MplsImportColumns.WAN_GATEWAY, 100, false));
        sourceMappers.put(MplsImportColumns.WAN_SUBNET, new StringSourceMapper(adapter, MplsImportColumns.WAN_SUBNET, 100, false));
        sourceMappers.put(MplsImportColumns.LAN_BLOCK, new StringSourceMapper(adapter, MplsImportColumns.LAN_BLOCK, 100, false));
        sourceMappers.put(MplsImportColumns.LAN_IPS, new StringSourceMapper(adapter, MplsImportColumns.LAN_IPS, 100, false));
        sourceMappers.put(MplsImportColumns.LAN_GATEWAY, new StringSourceMapper(adapter, MplsImportColumns.LAN_GATEWAY, 100, false));
        sourceMappers.put(MplsImportColumns.LAN_SUBNET, new StringSourceMapper(adapter, MplsImportColumns.LAN_SUBNET, 100, false));
        sourceMappers.put(MplsImportColumns.DNS_1, new StringSourceMapper(adapter, MplsImportColumns.DNS_1, 100, false));
        sourceMappers.put(MplsImportColumns.DNS_2, new StringSourceMapper(adapter, MplsImportColumns.DNS_2, 100, false));
        sourceMappers.put(MplsImportColumns.TSP_CODE, new StringSourceMapper(adapter, MplsImportColumns.TSP_CODE, 100, false));
        sourceMappers.put(MplsImportColumns.TSP_CODE_EXPIRATION_DATE, new StringSourceMapper(adapter, MplsImportColumns.TSP_CODE_EXPIRATION_DATE, 100, false));
        sourceMappers.put(MplsImportColumns.NOTES_TO_IMPORT, new StringSourceMapper(adapter, MplsImportColumns.NOTES_TO_IMPORT, 1000, false));
        return sourceMappers;
    }

}
