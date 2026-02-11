package com.endeavorms.velocity.qto.fileimport.dia;

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
import com.endeavorms.velocity.qto.service.dia.DiaService;
import com.endeavorms.velocity.qto.service.dia.DiaServiceManager;

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
 * @since 9/18/2023
 */
@Component
@TransactionManagement(TransactionManagementType.BEAN)
public class DiaImporter extends AbstractInventoryServiceImporter {
    @Inject
    private ServiceManager serviceManager;

    @Inject
    private DiaServiceManager diaServiceManager;

    @Inject
    private ServiceNoteManager serviceNoteManager;

    @Inject
    private ServiceMilestoneInstanceManager sMilestoneInstanceManager;

    @Override
    protected void importRow(final Map<String, ValidatingSourceMapper> sourceMappers, final ImportActivity importActivity) throws Exception {
        Long tenantId = importActivity.getTenantId();
        Date inventoryAddedDate;
        if (Strings.isNullOrEmpty(sourceMappers.get(DiaImportColumns.DATE_ENTERED_IN_INVENTORY).getValue())) {
            inventoryAddedDate = new Date();
        } else {
            inventoryAddedDate = parseDate(sourceMappers.get(DiaImportColumns.DATE_ENTERED_IN_INVENTORY).getValue());
        }
        Company endCustomer = handleEndCustomer(sourceMappers, tenantId);
        //Location Info
        Location location = handleOrderAndLocation(sourceMappers, tenantId, endCustomer,
                DiaImportColumns.CLIENT_LOCATION_INFO, DiaImportColumns.CLIENT_LOCATION_TYPE,
                DiaImportColumns.ADDRESS_1, DiaImportColumns.ADDRESS_2, DiaImportColumns.CITY,
                DiaImportColumns.STATE_PROVINCE_REGION, DiaImportColumns.ZIP_POSTAL_CODE,
                DiaImportColumns.COUNTRY, DiaImportColumns.TIME_ZONE);

        //Service
        DiaService service = new DiaService();
        service.setCurrentInventory(true);
        service.setBillable(true);
        service.setActive(true);
        service.setOrderType(OrderType.NEW.name());
        service.setOrderId(location.getOrderId());
        service.setLocationId(location.getId());
        service.setRecordSource(RecordSource.INVENTORY_IMPORT.getName());
        service.setClientServiceId(sourceMappers.get(DiaImportColumns.CLIENT_SERVICE_ID).getValue());
        service.setDescription(sourceMappers.get(DiaImportColumns.SERVICE_DESCRIPTION).getValue());
        service.setQuoteSolutionId(sourceMappers.get(DiaImportColumns.QUOTE_SOLUTION_ID).getValue());
        service.setProjectName(sourceMappers.get(DiaImportColumns.PROJECT_NAME).getValue());
        service.setServiceBilledTo(lookupValueMap.get(sourceMappers.get(DiaImportColumns.SERVICE_BILLED_TO).getValue()));
        service.setProvider(lookupValueMap.get(sourceMappers.get(DiaImportColumns.PROVIDER).getValue()));
        service.setUnderlyingProvider(lookupValueMap.get(sourceMappers.get(DiaImportColumns.UNDERLYING_PROVIDER).getValue()));
        service.setSubProductType(sourceMappers.get(DiaImportColumns.SUB_PRODUCT_TYPE).getValue());
        service.setPoNumber(sourceMappers.get(DiaImportColumns.PO_NUMBER).getValue());
        service.setManagedService(parseBoolean(sourceMappers.get(DiaImportColumns.MANAGED_SERVICE).getValue()));
        service.setProductionImpacting(parseBoolean(sourceMappers.get(DiaImportColumns.PRODUCTION_IMPACTING).getValue()));
        service.setLastMileProvider(lookupValueMap.get(sourceMappers.get(DiaImportColumns.LAST_MILE_PROVIDER).getValue()));
        service.setContractTerm(lookupValueMap.get(sourceMappers.get(DiaImportColumns.CONTRACT_TERM).getValue()));
        if (!Strings.isNullOrEmpty(sourceMappers.get(DiaImportColumns.CONTRACT_SIGNED_DATE).getNonValidatedValue())) {
            service.setContractSignedDate(parseDate(sourceMappers.get(DiaImportColumns.CONTRACT_SIGNED_DATE).getValue()));
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(DiaImportColumns.CONTRACT_END_DATE).getNonValidatedValue())) {
            service.setCircuitTermEndDate(parseDate(sourceMappers.get(DiaImportColumns.CONTRACT_END_DATE).getValue()));
        }
        service.setContractInfo(sourceMappers.get(DiaImportColumns.CONTRACT_INFO).getValue());
        service.setMrc(new BigDecimal(sourceMappers.get(DiaImportColumns.MRC).getValue()));
        service.setNrc(new BigDecimal(sourceMappers.get(DiaImportColumns.NRC).getValue()));
        service.setAnnualRecurringCost(new BigDecimal(sourceMappers.get(DiaImportColumns.ANNUAL_RECURRING_COST).getValue()));
        if (!Strings.isNullOrEmpty(sourceMappers.get(DiaImportColumns.BURSTABLE_SPEED_COST).getNonValidatedValue())) {
            service.setBurstableSpeedCost(new BigDecimal(sourceMappers.get(DiaImportColumns.BURSTABLE_SPEED_COST).getValue()));
        }
        service.setDownloadSpeed(lookupValueMap.get(sourceMappers.get(DiaImportColumns.CIRCUIT_DOWNLOAD_SPEED).getValue()));
        service.setUploadSpeed(lookupValueMap.get(sourceMappers.get(DiaImportColumns.CIRCUIT_UPLOAD_SPEED).getValue()));
        service.setSpeed(service.getDownloadSpeed() + "/" + service.getUploadSpeed());
        service.setBurstableSpeed(lookupValueMap.get(sourceMappers.get(DiaImportColumns.BURSTABLE_SPEED).getValue()));
        service.setProviderOrderNum(sourceMappers.get(DiaImportColumns.PROVIDER_ORDER).getValue());
        service.setBillCycle(parseLong(sourceMappers.get(DiaImportColumns.BILL_CYCLE).getValue()));
        service.setAccountNumber(sourceMappers.get(DiaImportColumns.ACCOUNT_NUMBER_BAN).getValue());
        service.setSummaryBill(sourceMappers.get(DiaImportColumns.SUMMARY_BILL).getValue());
        //DIA Technical?
        service.setNewAccessCircuitId(sourceMappers.get(DiaImportColumns.ACCESS_CIRCUIT_ID).getValue());
        service.setProviderCircuitId(sourceMappers.get(DiaImportColumns.PROVIDER_CIRCUIT_ID).getValue());
        service.setMediaType(lookupValueMap.get(sourceMappers.get(DiaImportColumns.MEDIA_TYPE).getValue()));
        service.setInterfaceConnector(lookupValueMap.get(sourceMappers.get(DiaImportColumns.INTERFACE_CONNECTOR).getValue()));
        service.setProviderActivationMethod(lookupValueMap.get(sourceMappers.get(DiaImportColumns.PROVIDER_ORDER_ACTIVATION_METHOD).getValue()));
        service.setActivationLink(sourceMappers.get(DiaImportColumns.ACTIVATION_LINK).getValue());
        service.setActivationPhone(sourceMappers.get(DiaImportColumns.ACTIVATION_PHONE).getValue());
        service.setDmarc(sourceMappers.get(DiaImportColumns.DMARC).getValue());
        service.setNpaNxx(sourceMappers.get(DiaImportColumns.NPA_NXX).getValue());
        service.setLocationHours(sourceMappers.get(DiaImportColumns.LOCATION_HOURS).getValue());
        service.setRouterSerialNumber(sourceMappers.get(DiaImportColumns.PROVIDER_ROUTER).getValue());
        service.setRouterMacAddress(sourceMappers.get(DiaImportColumns.PROVIDER_ROUTER_MAC).getValue());
        service.setIpFormat(parseIpFormat(sourceMappers.get(DiaImportColumns.IP_FORMAT).getValue()));
        service.setAdditionalIpBlock(lookupValueMap.get(sourceMappers.get(DiaImportColumns.WAN_BLOCK).getValue()));
        service.setWanIps(sourceMappers.get(DiaImportColumns.WAN_IPS).getValue());
        service.setWanGateway(sourceMappers.get(DiaImportColumns.WAN_GATEWAY).getValue());
        service.setWanSubnet(sourceMappers.get(DiaImportColumns.WAN_SUBNET).getValue());
        service.setLanBlock(lookupValueMap.get(sourceMappers.get(DiaImportColumns.LAN_BLOCK).getValue()));
        service.setLanIps(sourceMappers.get(DiaImportColumns.LAN_IPS).getValue());
        service.setLanGateway(sourceMappers.get(DiaImportColumns.LAN_GATEWAY).getValue());
        service.setLanSubnet(sourceMappers.get(DiaImportColumns.LAN_SUBNET).getValue());
        service.setDns1(sourceMappers.get(DiaImportColumns.DNS_1).getValue());
        service.setDns2(sourceMappers.get(DiaImportColumns.DNS_2).getValue());
        service.setTspCode(sourceMappers.get(DiaImportColumns.TSP_CODE).getValue());
        if (!Strings.isNullOrEmpty(sourceMappers.get(DiaImportColumns.TSP_CODE_EXPIRATION_DATE).getNonValidatedValue())) {
            service.setTspCodeExpirationDate(parseDate(sourceMappers.get(DiaImportColumns.TSP_CODE_EXPIRATION_DATE).getValue()));
        }
        if ("MTM".equals(service.getContractTerm()) || service.getCircuitTermEndDate() == null) {
            service.setIgnoreForRenewals(true);
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(DiaImportColumns.ORIGINAL_MRC).getNonValidatedValue())) {
            service.setOriginalMrc(new BigDecimal(sourceMappers.get(DiaImportColumns.ORIGINAL_MRC).getValue()));
        }
        handleCommonService(service, sourceMappers);
        service = diaServiceManager.create(service);

        if (!Strings.isNullOrEmpty(sourceMappers.get(DiaImportColumns.CIRCUIT_INSTALLED_DATE).getNonValidatedValue())) {
            sMilestoneInstanceManager.create(service.getId(), "DATA_PROVISIONING_COMPLETE", parseDate(sourceMappers.get(DiaImportColumns.CIRCUIT_INSTALLED_DATE).getValue()));
        }
        if (!sMilestoneInstanceManager.doesMilestoneExist(service.getId(), "COMPLETE")) {
            sMilestoneInstanceManager.create(service.getId(), "COMPLETE", inventoryAddedDate);
        }

        if (!Strings.isNullOrEmpty(sourceMappers.get(DiaImportColumns.NOTES_TO_IMPORT).getNonValidatedValue())) {
            serviceNoteManager.create(service.getId(), sourceMappers.get(DiaImportColumns.NOTES_TO_IMPORT).getValue(), "DIA", importActivity.getFileAttachment().getUploadedByUserName());
        }
    }

    @Override
    protected List<String> validateRow(Map<String, ValidatingSourceMapper> sourceMappers, Long tenantId) {
        List<String> errors = super.validateRow(sourceMappers, tenantId);

        //validate dropdown values
        //timezone
        errors = validateLookupValueCol(errors, sourceMappers.get(DiaImportColumns.TIME_ZONE), "NA_TIME_ZONE", tenantId);
        //circuit owner
        errors = validateLookupValueCol(errors, sourceMappers.get(DiaImportColumns.SERVICE_BILLED_TO), "SERVICE_BILLED_TO", tenantId);
        //PROVIDER
        errors = validateLookupValueCol(errors, sourceMappers.get(DiaImportColumns.PROVIDER), "PROVIDER", tenantId);
        //underlying provider
        errors = validateLookupValueCol(errors, sourceMappers.get(DiaImportColumns.UNDERLYING_PROVIDER), "UNDERLYING_PROVIDER", tenantId);
        //last mile PROVIDER
        errors = validateLookupValueCol(errors, sourceMappers.get(DiaImportColumns.LAST_MILE_PROVIDER), "PROVIDER", tenantId);
        //contract term
        errors = validateLookupValueCol(errors, sourceMappers.get(DiaImportColumns.CONTRACT_TERM), "CONTRACT_TERM", tenantId);
        //download speed
        errors = validateLookupValueCol(errors, sourceMappers.get(DiaImportColumns.CIRCUIT_DOWNLOAD_SPEED), "SPEED", tenantId);
        //upload speed
        errors = validateLookupValueCol(errors, sourceMappers.get(DiaImportColumns.CIRCUIT_UPLOAD_SPEED), "SPEED", tenantId);
        //burstable speed
        errors = validateLookupValueCol(errors, sourceMappers.get(DiaImportColumns.BURSTABLE_SPEED), "SPEED", tenantId);
        //media type
        errors = validateLookupValueCol(errors, sourceMappers.get(DiaImportColumns.MEDIA_TYPE), "MEDIA_TYPE", tenantId);
        //interface connector
        errors = validateLookupValueCol(errors, sourceMappers.get(DiaImportColumns.INTERFACE_CONNECTOR), "INTERFACE_CONNECTOR", tenantId);
        //PROVIDER order activation method
        errors = validateLookupValueCol(errors, sourceMappers.get(DiaImportColumns.PROVIDER_ORDER_ACTIVATION_METHOD), "PROVIDER_ACTIVATION_METHOD", tenantId);
        //ip format
        errors = validateIpFormatCol(errors, sourceMappers.get(DiaImportColumns.IP_FORMAT));
        //wan block
        errors = validateLookupValueCol(errors, sourceMappers.get(DiaImportColumns.WAN_BLOCK), "ADDITIONAL_IP_BLOCK", tenantId);
        //lan block
        errors = validateLookupValueCol(errors, sourceMappers.get(DiaImportColumns.LAN_BLOCK), "ADDITIONAL_IP_BLOCK", tenantId);
        //project name
        errors = validateLookupValueCol(errors, sourceMappers.get(DiaImportColumns.PROJECT_NAME), "PROJECT_NAME", tenantId);

        //verify date columns can be parsed
        //contract signed date
        errors = validateDateCol(errors, sourceMappers.get(DiaImportColumns.CONTRACT_SIGNED_DATE));
        //contract end date
        errors = validateDateCol(errors, sourceMappers.get(DiaImportColumns.CONTRACT_END_DATE));
        //date entered in inventory
        errors = validateDateCol(errors, sourceMappers.get(DiaImportColumns.DATE_ENTERED_IN_INVENTORY));
        //circuit installed date
        errors = validateDateCol(errors, sourceMappers.get(DiaImportColumns.CIRCUIT_INSTALLED_DATE));
        //tsp code expiration date
        errors = validateDateCol(errors, sourceMappers.get(DiaImportColumns.TSP_CODE_EXPIRATION_DATE));

        //verify decimal columns can be parsed
        //mrc
        errors = validateBigDecimalCol(errors, sourceMappers.get(DiaImportColumns.MRC));
        //nrc
        errors = validateBigDecimalCol(errors, sourceMappers.get(DiaImportColumns.NRC));
        //Annual Recurring Cost
        errors =validateBigDecimalCol(errors, sourceMappers.get(DiaImportColumns.ANNUAL_RECURRING_COST));
        //original mrc
        errors = validateBigDecimalCol(errors, sourceMappers.get(DiaImportColumns.ORIGINAL_MRC));
        //burstable speed cost
        errors = validateBigDecimalCol(errors, sourceMappers.get(DiaImportColumns.BURSTABLE_SPEED_COST));

        //verify long columns can be parsed
        //bill cycle
        errors = validateLongCol(errors, sourceMappers.get(DiaImportColumns.BILL_CYCLE));

        return errors;
    }

    @Override
    protected List<String> getExpectedColumns() {
        return DiaImportColumns.getExpectedColumns();
    }

    @Override
    protected String getTemplateName() {
        return "DIA";
    }

    @Override
    protected String getImportTypeName() {
        return "DIA Services";
    }

    @Override
    protected Map<String, ValidatingSourceMapper> buildSourceMappers(final ExcelAdapter adapter) {
        HashMap<String, ValidatingSourceMapper> sourceMappers = new HashMap<>();
        handleCommonSourceMappers(sourceMappers, adapter);
        sourceMappers.put(DiaImportColumns.MASTER_CUSTOMER, new StringSourceMapper(adapter, DiaImportColumns.MASTER_CUSTOMER, 100, true));
        sourceMappers.put(DiaImportColumns.END_CUSTOMER, new StringSourceMapper(adapter, DiaImportColumns.END_CUSTOMER, 100, true));
        sourceMappers.put(DiaImportColumns.I90_PROJECT_MANAGER, new StringSourceMapper(adapter, DiaImportColumns.I90_PROJECT_MANAGER, 100, false));
        sourceMappers.put(DiaImportColumns.CLIENT_LOCATION_ID, new StringSourceMapper(adapter, DiaImportColumns.CLIENT_LOCATION_ID, 100, true));
        sourceMappers.put(DiaImportColumns.CLIENT_SERVICE_ID, new StringSourceMapper(adapter, DiaImportColumns.CLIENT_SERVICE_ID, 100, false));
        sourceMappers.put(DiaImportColumns.ADDRESS_1, new StringSourceMapper(adapter, DiaImportColumns.ADDRESS_1, 100, false));
        sourceMappers.put(DiaImportColumns.ADDRESS_2, new StringSourceMapper(adapter, DiaImportColumns.ADDRESS_2, 100, false));
        sourceMappers.put(DiaImportColumns.CITY, new StringSourceMapper(adapter, DiaImportColumns.CITY, 100, false));
        sourceMappers.put(DiaImportColumns.STATE_PROVINCE_REGION, new StringSourceMapper(adapter, DiaImportColumns.STATE_PROVINCE_REGION, 100, false));
        sourceMappers.put(DiaImportColumns.ZIP_POSTAL_CODE, new StringSourceMapper(adapter, DiaImportColumns.ZIP_POSTAL_CODE, 20, false));
        sourceMappers.put(DiaImportColumns.COUNTRY, new StringSourceMapper(adapter, DiaImportColumns.COUNTRY, 100, false));
        sourceMappers.put(DiaImportColumns.TIME_ZONE, new StringSourceMapper(adapter, DiaImportColumns.TIME_ZONE, 100, false));
        sourceMappers.put(DiaImportColumns.LOCATION_DESCRIPTION, new StringSourceMapper(adapter, DiaImportColumns.LOCATION_DESCRIPTION, 500, false));
        sourceMappers.put(DiaImportColumns.SERVICE_DESCRIPTION, new StringSourceMapper(adapter, DiaImportColumns.SERVICE_DESCRIPTION, 500, false));
        sourceMappers.put(DiaImportColumns.LCON_NAME, new StringSourceMapper(adapter, DiaImportColumns.LCON_NAME, 100, false));
        sourceMappers.put(DiaImportColumns.LCON_EMAIL, new StringSourceMapper(adapter, DiaImportColumns.LCON_EMAIL, 100, false));
        sourceMappers.put(DiaImportColumns.LCON_PHONE, new StringSourceMapper(adapter, DiaImportColumns.LCON_PHONE, 100, false));
        sourceMappers.put(DiaImportColumns.CLIENT_LOCATION_INFO, new StringSourceMapper(adapter, DiaImportColumns.CLIENT_LOCATION_INFO, 100, false));
        sourceMappers.put(DiaImportColumns.CLIENT_LOCATION_TYPE, new StringSourceMapper(adapter, DiaImportColumns.CLIENT_LOCATION_TYPE, 100, false));
        sourceMappers.put(DiaImportColumns.QUOTE_SOLUTION_ID, new StringSourceMapper(adapter, DiaImportColumns.QUOTE_SOLUTION_ID, 100, false));
        sourceMappers.put(DiaImportColumns.PROJECT_NAME, new StringSourceMapper(adapter, DiaImportColumns.PROJECT_NAME, 100, false));
        sourceMappers.put(DiaImportColumns.SERVICE_BILLED_TO, new StringSourceMapper(adapter, DiaImportColumns.SERVICE_BILLED_TO, 100, true));
        sourceMappers.put(DiaImportColumns.PROVIDER, new StringSourceMapper(adapter, DiaImportColumns.PROVIDER, 100, true));
        sourceMappers.put(DiaImportColumns.UNDERLYING_PROVIDER, new StringSourceMapper(adapter, DiaImportColumns.UNDERLYING_PROVIDER, 100, false));
        sourceMappers.put(DiaImportColumns.SUB_PRODUCT_TYPE, new StringSourceMapper(adapter, DiaImportColumns.SUB_PRODUCT_TYPE, 100, false));
        sourceMappers.put(DiaImportColumns.PO_NUMBER, new StringSourceMapper(adapter, DiaImportColumns.PO_NUMBER, 100, false));
        sourceMappers.put(DiaImportColumns.MANAGED_SERVICE, new StringSourceMapper(adapter, DiaImportColumns.MANAGED_SERVICE, 100, false));
        sourceMappers.put(DiaImportColumns.PRODUCTION_IMPACTING, new StringSourceMapper(adapter, DiaImportColumns.PRODUCTION_IMPACTING, 100, false));
        sourceMappers.put(DiaImportColumns.LAST_MILE_PROVIDER, new StringSourceMapper(adapter, DiaImportColumns.LAST_MILE_PROVIDER, 100, false));
        sourceMappers.put(DiaImportColumns.CONTRACT_TERM, new StringSourceMapper(adapter, DiaImportColumns.CONTRACT_TERM, 100, false));
        sourceMappers.put(DiaImportColumns.CONTRACT_SIGNED_DATE, new StringSourceMapper(adapter, DiaImportColumns.CONTRACT_SIGNED_DATE, 100, false));
        sourceMappers.put(DiaImportColumns.CONTRACT_END_DATE, new StringSourceMapper(adapter, DiaImportColumns.CONTRACT_END_DATE, 100, false));
        sourceMappers.put(DiaImportColumns.CONTRACT_INFO, new StringSourceMapper(adapter, DiaImportColumns.CONTRACT_INFO, 500, false));
        sourceMappers.put(DiaImportColumns.MRC, new StringSourceMapper(adapter, DiaImportColumns.MRC, 100, true));
        sourceMappers.put(DiaImportColumns.NRC, new StringSourceMapper(adapter, DiaImportColumns.NRC, 100, true));
        sourceMappers.put(DiaImportColumns.ANNUAL_RECURRING_COST, new StringSourceMapper(adapter, DiaImportColumns.ANNUAL_RECURRING_COST, 100, true));
        sourceMappers.put(DiaImportColumns.ORIGINAL_MRC, new StringSourceMapper(adapter, DiaImportColumns.ORIGINAL_MRC, 100, false));
        sourceMappers.put(DiaImportColumns.BURSTABLE_SPEED_COST, new StringSourceMapper(adapter, DiaImportColumns.BURSTABLE_SPEED_COST, 100, false));
        sourceMappers.put(DiaImportColumns.CIRCUIT_DOWNLOAD_SPEED, new StringSourceMapper(adapter, DiaImportColumns.CIRCUIT_DOWNLOAD_SPEED, 100, true));
        sourceMappers.put(DiaImportColumns.CIRCUIT_UPLOAD_SPEED, new StringSourceMapper(adapter, DiaImportColumns.CIRCUIT_UPLOAD_SPEED, 100, true));
        sourceMappers.put(DiaImportColumns.BURSTABLE_SPEED, new StringSourceMapper(adapter, DiaImportColumns.BURSTABLE_SPEED, 100, false));
        sourceMappers.put(DiaImportColumns.DATE_ENTERED_IN_INVENTORY, new StringSourceMapper(adapter, DiaImportColumns.DATE_ENTERED_IN_INVENTORY, 100, false));
        sourceMappers.put(DiaImportColumns.PROVIDER_ORDER, new StringSourceMapper(adapter, DiaImportColumns.PROVIDER_ORDER, 100, false));
        sourceMappers.put(DiaImportColumns.BILL_CYCLE, new StringSourceMapper(adapter, DiaImportColumns.BILL_CYCLE, 2, false));
        sourceMappers.put(DiaImportColumns.ACCOUNT_NUMBER_BAN, new StringSourceMapper(adapter, DiaImportColumns.ACCOUNT_NUMBER_BAN, 100, false));
        sourceMappers.put(DiaImportColumns.SUMMARY_BILL, new StringSourceMapper(adapter, DiaImportColumns.SUMMARY_BILL, 100, false));
        sourceMappers.put(DiaImportColumns.ACCESS_CIRCUIT_ID, new StringSourceMapper(adapter, DiaImportColumns.ACCESS_CIRCUIT_ID, 100, true));
        sourceMappers.put(DiaImportColumns.PROVIDER_CIRCUIT_ID, new StringSourceMapper(adapter, DiaImportColumns.PROVIDER_CIRCUIT_ID, 100, true));
        sourceMappers.put(DiaImportColumns.CIRCUIT_INSTALLED_DATE, new StringSourceMapper(adapter, DiaImportColumns.CIRCUIT_INSTALLED_DATE, 100, false));
        sourceMappers.put(DiaImportColumns.MEDIA_TYPE, new StringSourceMapper(adapter, DiaImportColumns.MEDIA_TYPE, 100, false));
        sourceMappers.put(DiaImportColumns.INTERFACE_CONNECTOR, new StringSourceMapper(adapter, DiaImportColumns.INTERFACE_CONNECTOR, 100, false));
        sourceMappers.put(DiaImportColumns.PROVIDER_ORDER_ACTIVATION_METHOD, new StringSourceMapper(adapter, DiaImportColumns.PROVIDER_ORDER_ACTIVATION_METHOD, 100, false));
        sourceMappers.put(DiaImportColumns.ACTIVATION_LINK, new StringSourceMapper(adapter, DiaImportColumns.ACTIVATION_LINK, 100, false));
        sourceMappers.put(DiaImportColumns.ACTIVATION_PHONE, new StringSourceMapper(adapter, DiaImportColumns.ACTIVATION_PHONE, 100, false));
        sourceMappers.put(DiaImportColumns.DMARC, new StringSourceMapper(adapter, DiaImportColumns.DMARC, 100, false));
        sourceMappers.put(DiaImportColumns.NPA_NXX, new StringSourceMapper(adapter, DiaImportColumns.NPA_NXX, 100, false));
        sourceMappers.put(DiaImportColumns.LOCATION_HOURS, new StringSourceMapper(adapter, DiaImportColumns.LOCATION_HOURS, 100, false));
        sourceMappers.put(DiaImportColumns.PROVIDER_ROUTER, new StringSourceMapper(adapter, DiaImportColumns.PROVIDER_ROUTER, 100, false));
        sourceMappers.put(DiaImportColumns.PROVIDER_ROUTER_MAC, new StringSourceMapper(adapter, DiaImportColumns.PROVIDER_ROUTER_MAC, 100, false));
        sourceMappers.put(DiaImportColumns.IP_FORMAT, new StringSourceMapper(adapter, DiaImportColumns.IP_FORMAT, 100, false));
        sourceMappers.put(DiaImportColumns.WAN_BLOCK, new StringSourceMapper(adapter, DiaImportColumns.WAN_BLOCK, 100, false));
        sourceMappers.put(DiaImportColumns.WAN_IPS, new StringSourceMapper(adapter, DiaImportColumns.WAN_IPS, 100, false));
        sourceMappers.put(DiaImportColumns.WAN_GATEWAY, new StringSourceMapper(adapter, DiaImportColumns.WAN_GATEWAY, 100, false));
        sourceMappers.put(DiaImportColumns.WAN_SUBNET, new StringSourceMapper(adapter, DiaImportColumns.WAN_SUBNET, 100, false));
        sourceMappers.put(DiaImportColumns.LAN_BLOCK, new StringSourceMapper(adapter, DiaImportColumns.LAN_BLOCK, 100, false));
        sourceMappers.put(DiaImportColumns.LAN_IPS, new StringSourceMapper(adapter, DiaImportColumns.LAN_IPS, 100, false));
        sourceMappers.put(DiaImportColumns.LAN_GATEWAY, new StringSourceMapper(adapter, DiaImportColumns.LAN_GATEWAY, 100, false));
        sourceMappers.put(DiaImportColumns.LAN_SUBNET, new StringSourceMapper(adapter, DiaImportColumns.LAN_SUBNET, 100, false));
        sourceMappers.put(DiaImportColumns.DNS_1, new StringSourceMapper(adapter, DiaImportColumns.DNS_1, 100, false));
        sourceMappers.put(DiaImportColumns.DNS_2, new StringSourceMapper(adapter, DiaImportColumns.DNS_2, 100, false));
        sourceMappers.put(DiaImportColumns.TSP_CODE, new StringSourceMapper(adapter, DiaImportColumns.TSP_CODE, 100, false));
        sourceMappers.put(DiaImportColumns.TSP_CODE_EXPIRATION_DATE, new StringSourceMapper(adapter, DiaImportColumns.TSP_CODE_EXPIRATION_DATE, 100, false));
        sourceMappers.put(DiaImportColumns.NOTES_TO_IMPORT, new StringSourceMapper(adapter, DiaImportColumns.NOTES_TO_IMPORT, 2147483647, false));
        return sourceMappers;
    }


}
