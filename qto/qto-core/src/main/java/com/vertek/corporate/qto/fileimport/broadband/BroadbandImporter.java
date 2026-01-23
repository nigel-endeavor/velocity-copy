package com.vertek.corporate.qto.fileimport.broadband;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.RecordSource;
import com.vertek.corporate.qto.common.adapter.ExcelAdapter;
import com.vertek.corporate.qto.common.mapping.StringSourceMapper;
import com.vertek.corporate.qto.common.mapping.ValidatingSourceMapper;
import com.vertek.corporate.qto.company.Company;
import com.vertek.corporate.qto.fileimport.AbstractInventoryServiceImporter;
import com.vertek.corporate.qto.fileimport.importactivity.ImportActivity;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstanceManager;
import com.vertek.corporate.qto.note.ServiceNoteManager;
import com.vertek.corporate.qto.service.OrderType;
import com.vertek.corporate.qto.service.broadband.BroadbandService;
import com.vertek.corporate.qto.service.broadband.BroadbandServiceManager;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rcasey
 * @since 9/13/2023
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BroadbandImporter extends AbstractInventoryServiceImporter {

    @Inject
    private BroadbandServiceManager broadbandServiceManager;

    @Inject
    private ServiceNoteManager serviceNoteManager;

    @Inject
    private ServiceMilestoneInstanceManager sMilestoneInstanceManager;

    @Override
    protected void importRow(final Map<String, ValidatingSourceMapper> sourceMappers, final ImportActivity importActivity) throws Exception {
        Date inventoryAddedDate;
        Long tenantId = importActivity.getTenantId();
        if (Strings.isNullOrEmpty(sourceMappers.get(BroadbandImportColumns.DATE_ENTERED_IN_INVENTORY).getValue())) {
            inventoryAddedDate = new Date();
        } else {
            inventoryAddedDate = parseDate(sourceMappers.get(BroadbandImportColumns.DATE_ENTERED_IN_INVENTORY).getValue());
        }
        Company endCustomer = handleEndCustomer(sourceMappers, tenantId);
        //Location Info
        Location location = handleOrderAndLocation(sourceMappers, tenantId, endCustomer,
                BroadbandImportColumns.CLIENT_LOCATION_INFO, BroadbandImportColumns.CLIENT_LOCATION_TYPE,
                BroadbandImportColumns.ADDRESS_1, BroadbandImportColumns.ADDRESS_2, BroadbandImportColumns.CITY,
                BroadbandImportColumns.STATE_PROVINCE_REGION, BroadbandImportColumns.ZIP_POSTAL_CODE,
                BroadbandImportColumns.COUNTRY, BroadbandImportColumns.TIME_ZONE);

        //Service
        BroadbandService service = new BroadbandService();
        service.setCurrentInventory(true);
        service.setBillable(true);
        service.setActive(true);
        service.setOrderType(OrderType.NEW.name());
        service.setOrderId(location.getOrderId());
        service.setLocationId(location.getId());
        service.setRecordSource(RecordSource.INVENTORY_IMPORT.getName());
        service.setClientServiceId(sourceMappers.get(BroadbandImportColumns.CLIENT_SERVICE_ID).getValue());
        service.setDescription(sourceMappers.get(BroadbandImportColumns.SERVICE_DESCRIPTION).getValue());
        service.setQuoteSolutionId(sourceMappers.get(BroadbandImportColumns.QUOTE_SOLUTION_ID).getValue());
        service.setProjectName(sourceMappers.get(BroadbandImportColumns.PROJECT_NAME).getValue());
        service.setServiceBilledTo(lookupValueMap.get(sourceMappers.get(BroadbandImportColumns.SERVICE_BILLED_TO).getValue()));
        service.setProvider(lookupValueMap.get(sourceMappers.get(BroadbandImportColumns.PROVIDER).getValue()));
        service.setUnderlyingProvider(lookupValueMap.get(sourceMappers.get(BroadbandImportColumns.UNDERLYING_PROVIDER).getValue()));
        service.setSubProductType(sourceMappers.get(BroadbandImportColumns.SUB_PRODUCT_TYPE).getValue());
        service.setPoNumber(sourceMappers.get(BroadbandImportColumns.PO_NUMBER).getValue());
        service.setManagedService(parseBoolean(sourceMappers.get(BroadbandImportColumns.MANAGED_SERVICE).getValue()));
        service.setProductionImpacting(parseBoolean(sourceMappers.get(BroadbandImportColumns.PRODUCTION_IMPACTING).getValue()));
        service.setContractTerm(lookupValueMap.get(sourceMappers.get(BroadbandImportColumns.CONTRACT_TERM).getValue()));
        if (!Strings.isNullOrEmpty(sourceMappers.get(BroadbandImportColumns.CONTRACT_SIGNED_DATE).getNonValidatedValue())) {
            service.setContractSignedDate(parseDate(sourceMappers.get(BroadbandImportColumns.CONTRACT_SIGNED_DATE).getValue()));
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(BroadbandImportColumns.CONTRACT_END_DATE).getNonValidatedValue())) {
            service.setCircuitTermEndDate(parseDate(sourceMappers.get(BroadbandImportColumns.CONTRACT_END_DATE).getValue()));
        }
        service.setContractInfo(sourceMappers.get(BroadbandImportColumns.CONTRACT_INFO).getValue());
        service.setMrc(new BigDecimal(sourceMappers.get(BroadbandImportColumns.MRC).getValue()));
        service.setNrc(new BigDecimal(sourceMappers.get(BroadbandImportColumns.NRC).getValue()));
        service.setAnnualRecurringCost(new BigDecimal(sourceMappers.get(BroadbandImportColumns.ANNUAL_RECURRING_COST).getValue()));
        service.setSpeed(service.getDownloadSpeed() + "/" + service.getUploadSpeed());
        service.setProviderOrderNum(sourceMappers.get(BroadbandImportColumns.PROVIDER_ORDER).getValue());
        service.setBillCycle(parseLong(sourceMappers.get(BroadbandImportColumns.BILL_CYCLE).getValue()));
        service.setAccountNumber(sourceMappers.get(BroadbandImportColumns.ACCOUNT_NUMBER_BAN).getValue());
        service.setSummaryBill(sourceMappers.get(BroadbandImportColumns.SUMMARY_BILL).getValue());
        //BB Technical?
        service.setDownloadSpeed(lookupValueMap.get(sourceMappers.get(BroadbandImportColumns.CIRCUIT_DOWNLOAD_SPEED).getValue()));
        service.setUploadSpeed(lookupValueMap.get(sourceMappers.get(BroadbandImportColumns.CIRCUIT_UPLOAD_SPEED).getValue()));
        service.setProviderCircuitId(sourceMappers.get(BroadbandImportColumns.PROVIDER_CIRCUIT_ID).getValue());
        service.setMediaType(lookupValueMap.get(sourceMappers.get(BroadbandImportColumns.MEDIA_TYPE).getValue()));
        service.setDmarc(sourceMappers.get(BroadbandImportColumns.DMARC).getValue());
        service.setLocationHours(sourceMappers.get(BroadbandImportColumns.LOCATION_HOURS).getValue());
        service.setModemMake(sourceMappers.get(BroadbandImportColumns.PROVIDER_MODEM).getValue());
        service.setMacAddress(sourceMappers.get(BroadbandImportColumns.PROVIDER_MODEM_MAC).getValue());
        service.setNetworkProtocol(lookupValueMap.get(sourceMappers.get(BroadbandImportColumns.NETWORK_PROTOCOL).getValue()));
        service.setIpFormat(parseIpFormat(sourceMappers.get(BroadbandImportColumns.IP_FORMAT).getValue()));
        service.setAdditionalIpBlock(lookupValueMap.get(sourceMappers.get(BroadbandImportColumns.WAN_BLOCK).getValue()));
        service.setWanIps(sourceMappers.get(BroadbandImportColumns.WAN_IPS).getValue());
        service.setWanGateway(sourceMappers.get(BroadbandImportColumns.WAN_GATEWAY).getValue());
        service.setWanSubnet(sourceMappers.get(BroadbandImportColumns.WAN_SUBNET).getValue());
        service.setLanBlock(lookupValueMap.get(sourceMappers.get(BroadbandImportColumns.LAN_BLOCK).getValue()));
        service.setLanIps(sourceMappers.get(BroadbandImportColumns.LAN_IPS).getValue());
        service.setLanGateway(sourceMappers.get(BroadbandImportColumns.LAN_GATEWAY).getValue());
        service.setLanSubnet(sourceMappers.get(BroadbandImportColumns.LAN_SUBNET).getValue());
        service.setDns1(sourceMappers.get(BroadbandImportColumns.DNS1).getValue());
        service.setDns2(sourceMappers.get(BroadbandImportColumns.DNS2).getValue());
        service.setTspCode(sourceMappers.get(BroadbandImportColumns.TSP_CODE).getValue());
        if (!Strings.isNullOrEmpty(sourceMappers.get(BroadbandImportColumns.TSP_CODE_EXPIRATION_DATE).getNonValidatedValue())) {
            service.setTspCodeExpirationDate(parseDate(sourceMappers.get(BroadbandImportColumns.TSP_CODE_EXPIRATION_DATE).getValue()));
        }
        if ("MTM".equals(service.getContractTerm()) || service.getCircuitTermEndDate() == null) {
            service.setIgnoreForRenewals(true);
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(BroadbandImportColumns.ORIGINAL_MRC).getNonValidatedValue())) {
            service.setOriginalMrc(new BigDecimal(sourceMappers.get(BroadbandImportColumns.ORIGINAL_MRC).getValue()));
        }
        handleCommonService(service, sourceMappers);
        service = broadbandServiceManager.create(service);

        if (!Strings.isNullOrEmpty(sourceMappers.get(BroadbandImportColumns.CIRCUIT_INSTALLED_DATE).getNonValidatedValue())) {
            sMilestoneInstanceManager.create(service.getId(), "DATA_PROVISIONING_COMPLETE", parseDate(sourceMappers.get(BroadbandImportColumns.CIRCUIT_INSTALLED_DATE).getValue()));
        }
        if (!sMilestoneInstanceManager.doesMilestoneExist(service.getId(), "COMPLETE")) {
            sMilestoneInstanceManager.create(service.getId(), "COMPLETE", inventoryAddedDate);
        }

        if (!Strings.isNullOrEmpty(sourceMappers.get(BroadbandImportColumns.NOTES_TO_IMPORT).getNonValidatedValue())) {
            serviceNoteManager.create(service.getId(), sourceMappers.get(BroadbandImportColumns.NOTES_TO_IMPORT).getValue(), "Broadband", importActivity.getFileAttachment().getUploadedByUserName());
        }
    }

    @Override
    protected List<String> validateRow(Map<String, ValidatingSourceMapper> sourceMappers, Long tenantId) {
        List<String> errors = super.validateRow(sourceMappers, tenantId);

        //validate dropdown values
        //timezone
        errors = validateLookupValueCol(errors, sourceMappers.get(BroadbandImportColumns.TIME_ZONE), "NA_TIME_ZONE", tenantId);
        //circuit owner
        errors = validateLookupValueCol(errors, sourceMappers.get(BroadbandImportColumns.SERVICE_BILLED_TO), "SERVICE_BILLED_TO", tenantId);
        //provider
        errors = validateLookupValueCol(errors, sourceMappers.get(BroadbandImportColumns.PROVIDER), "PROVIDER", tenantId);
        //underlying provider
        errors = validateLookupValueCol(errors, sourceMappers.get(BroadbandImportColumns.UNDERLYING_PROVIDER), "UNDERLYING_PROVIDER", tenantId);
        //contract term
        errors = validateLookupValueCol(errors, sourceMappers.get(BroadbandImportColumns.CONTRACT_TERM), "CONTRACT_TERM", tenantId);
        //download speed
        errors = validateLookupValueCol(errors, sourceMappers.get(BroadbandImportColumns.CIRCUIT_DOWNLOAD_SPEED), "SPEED", tenantId);
        //upload speed
        errors = validateLookupValueCol(errors, sourceMappers.get(BroadbandImportColumns.CIRCUIT_UPLOAD_SPEED), "SPEED", tenantId);
        //media type
        errors = validateLookupValueCol(errors, sourceMappers.get(BroadbandImportColumns.MEDIA_TYPE), "MEDIA_TYPE", tenantId);
        //network protocol
        errors = validateLookupValueCol(errors, sourceMappers.get(BroadbandImportColumns.NETWORK_PROTOCOL), "NETWORK_PROTOCOL", tenantId);
        //ip format
        errors = validateIpFormatCol(errors, sourceMappers.get(BroadbandImportColumns.IP_FORMAT));
        //wan block
        errors = validateLookupValueCol(errors, sourceMappers.get(BroadbandImportColumns.WAN_BLOCK), "ADDITIONAL_IP_BLOCK", tenantId);
        //lan block
        errors = validateLookupValueCol(errors, sourceMappers.get(BroadbandImportColumns.LAN_BLOCK), "ADDITIONAL_IP_BLOCK", tenantId);
        //project name
        errors = validateLookupValueCol(errors, sourceMappers.get(BroadbandImportColumns.PROJECT_NAME), "PROJECT_NAME", tenantId);

        //verify date columns can be parsed
        //contract signed date
        errors = validateDateCol(errors, sourceMappers.get(BroadbandImportColumns.CONTRACT_SIGNED_DATE));
        //contract end date
        errors = validateDateCol(errors, sourceMappers.get(BroadbandImportColumns.CONTRACT_END_DATE));
        //date entered in inventory
        errors = validateDateCol(errors, sourceMappers.get(BroadbandImportColumns.DATE_ENTERED_IN_INVENTORY));
        //circuit installed date
        errors = validateDateCol(errors, sourceMappers.get(BroadbandImportColumns.CIRCUIT_INSTALLED_DATE));
        //tsp code expiration date
        errors = validateDateCol(errors, sourceMappers.get(BroadbandImportColumns.TSP_CODE_EXPIRATION_DATE));

        //verify decimal columns can be parsed
        //mrc
        errors = validateBigDecimalCol(errors, sourceMappers.get(BroadbandImportColumns.MRC));
        //nrc
        errors = validateBigDecimalCol(errors, sourceMappers.get(BroadbandImportColumns.NRC));
        //annual recurring cost
        errors = validateBigDecimalCol(errors, sourceMappers.get(BroadbandImportColumns.ANNUAL_RECURRING_COST));
        //original MRC
        errors = validateBigDecimalCol(errors, sourceMappers.get(BroadbandImportColumns.ORIGINAL_MRC));

        //verify long columns can be parsed
        //bill cycle
        errors = validateLongCol(errors, sourceMappers.get(BroadbandImportColumns.BILL_CYCLE));

        return errors;
    }

    @Override
    protected List<String> getExpectedColumns() {
        return BroadbandImportColumns.getExpectedColumns();
    }

    @Override
    protected String getTemplateName() {
        return "Broadband";
    }

    @Override
    protected String getImportTypeName() {
        return "Broadband Services";
    }

    @Override
    protected Map<String, ValidatingSourceMapper> buildSourceMappers(final ExcelAdapter adapter) {
        HashMap<String, ValidatingSourceMapper> sourceMappers = new HashMap<>();
        handleCommonSourceMappers(sourceMappers, adapter);
        sourceMappers.put(BroadbandImportColumns.MASTER_CUSTOMER, new StringSourceMapper(adapter, BroadbandImportColumns.MASTER_CUSTOMER, 100, true));
        sourceMappers.put(BroadbandImportColumns.END_CUSTOMER, new StringSourceMapper(adapter, BroadbandImportColumns.END_CUSTOMER, 100, true));
        sourceMappers.put(BroadbandImportColumns.I90_PROJECT_MANAGER, new StringSourceMapper(adapter, BroadbandImportColumns.I90_PROJECT_MANAGER, 100, false));
        sourceMappers.put(BroadbandImportColumns.CLIENT_LOCATION_ID, new StringSourceMapper(adapter, BroadbandImportColumns.CLIENT_LOCATION_ID, 100, true));
        sourceMappers.put(BroadbandImportColumns.CLIENT_SERVICE_ID, new StringSourceMapper(adapter, BroadbandImportColumns.CLIENT_SERVICE_ID, 100, false));
        sourceMappers.put(BroadbandImportColumns.ADDRESS_1, new StringSourceMapper(adapter, BroadbandImportColumns.ADDRESS_1, 100, false));
        sourceMappers.put(BroadbandImportColumns.ADDRESS_2, new StringSourceMapper(adapter, BroadbandImportColumns.ADDRESS_2, 100, false));
        sourceMappers.put(BroadbandImportColumns.CITY, new StringSourceMapper(adapter, BroadbandImportColumns.CITY, 100, false));
        sourceMappers.put(BroadbandImportColumns.STATE_PROVINCE_REGION, new StringSourceMapper(adapter, BroadbandImportColumns.STATE_PROVINCE_REGION, 100, false));
        sourceMappers.put(BroadbandImportColumns.ZIP_POSTAL_CODE, new StringSourceMapper(adapter, BroadbandImportColumns.ZIP_POSTAL_CODE, 20, false));
        sourceMappers.put(BroadbandImportColumns.COUNTRY, new StringSourceMapper(adapter, BroadbandImportColumns.COUNTRY, 100, false));
        sourceMappers.put(BroadbandImportColumns.TIME_ZONE, new StringSourceMapper(adapter, BroadbandImportColumns.TIME_ZONE, 100, false));
        sourceMappers.put(BroadbandImportColumns.LOCATION_DESCRIPTION, new StringSourceMapper(adapter, BroadbandImportColumns.LOCATION_DESCRIPTION, 500, false));
        sourceMappers.put(BroadbandImportColumns.SERVICE_DESCRIPTION, new StringSourceMapper(adapter, BroadbandImportColumns.SERVICE_DESCRIPTION, 500, false));
        sourceMappers.put(BroadbandImportColumns.LCON_NAME, new StringSourceMapper(adapter, BroadbandImportColumns.LCON_NAME, 100, false));
        sourceMappers.put(BroadbandImportColumns.LCON_EMAIL, new StringSourceMapper(adapter, BroadbandImportColumns.LCON_EMAIL, 100, false));
        sourceMappers.put(BroadbandImportColumns.LCON_PHONE, new StringSourceMapper(adapter, BroadbandImportColumns.LCON_PHONE, 100, false));
        sourceMappers.put(BroadbandImportColumns.CLIENT_LOCATION_INFO, new StringSourceMapper(adapter, BroadbandImportColumns.CLIENT_LOCATION_INFO, 100, false));
        sourceMappers.put(BroadbandImportColumns.CLIENT_LOCATION_TYPE, new StringSourceMapper(adapter, BroadbandImportColumns.CLIENT_LOCATION_TYPE, 100, false));
        sourceMappers.put(BroadbandImportColumns.QUOTE_SOLUTION_ID, new StringSourceMapper(adapter, BroadbandImportColumns.QUOTE_SOLUTION_ID, 100, false));
        sourceMappers.put(BroadbandImportColumns.PROJECT_NAME, new StringSourceMapper(adapter, BroadbandImportColumns.PROJECT_NAME, 100, false));
        sourceMappers.put(BroadbandImportColumns.SERVICE_BILLED_TO, new StringSourceMapper(adapter, BroadbandImportColumns.SERVICE_BILLED_TO, 100, true));
        sourceMappers.put(BroadbandImportColumns.PROVIDER, new StringSourceMapper(adapter, BroadbandImportColumns.PROVIDER, 100, true));
        sourceMappers.put(BroadbandImportColumns.UNDERLYING_PROVIDER, new StringSourceMapper(adapter, BroadbandImportColumns.UNDERLYING_PROVIDER, 100, false));
        sourceMappers.put(BroadbandImportColumns.SUB_PRODUCT_TYPE, new StringSourceMapper(adapter, BroadbandImportColumns.SUB_PRODUCT_TYPE, 100, false));
        sourceMappers.put(BroadbandImportColumns.PO_NUMBER, new StringSourceMapper(adapter, BroadbandImportColumns.PO_NUMBER, 100, false));
        sourceMappers.put(BroadbandImportColumns.MANAGED_SERVICE, new StringSourceMapper(adapter, BroadbandImportColumns.MANAGED_SERVICE, 100, false));
        sourceMappers.put(BroadbandImportColumns.PRODUCTION_IMPACTING, new StringSourceMapper(adapter, BroadbandImportColumns.PRODUCTION_IMPACTING, 100, false));
        sourceMappers.put(BroadbandImportColumns.CONTRACT_TERM, new StringSourceMapper(adapter, BroadbandImportColumns.CONTRACT_TERM, 100, false));
        sourceMappers.put(BroadbandImportColumns.CONTRACT_SIGNED_DATE, new StringSourceMapper(adapter, BroadbandImportColumns.CONTRACT_SIGNED_DATE, 100, false));
        sourceMappers.put(BroadbandImportColumns.CONTRACT_END_DATE, new StringSourceMapper(adapter, BroadbandImportColumns.CONTRACT_END_DATE, 100, false));
        sourceMappers.put(BroadbandImportColumns.CONTRACT_INFO, new StringSourceMapper(adapter, BroadbandImportColumns.CONTRACT_INFO, 500, false));
        sourceMappers.put(BroadbandImportColumns.MRC, new StringSourceMapper(adapter, BroadbandImportColumns.MRC, 100, true));
        sourceMappers.put(BroadbandImportColumns.NRC, new StringSourceMapper(adapter, BroadbandImportColumns.NRC, 100, true));
        sourceMappers.put(BroadbandImportColumns.ANNUAL_RECURRING_COST, new StringSourceMapper(adapter, BroadbandImportColumns.ANNUAL_RECURRING_COST, 100, true));
        sourceMappers.put(BroadbandImportColumns.ORIGINAL_MRC, new StringSourceMapper(adapter, BroadbandImportColumns.ORIGINAL_MRC, 100, false));
        sourceMappers.put(BroadbandImportColumns.CIRCUIT_DOWNLOAD_SPEED, new StringSourceMapper(adapter, BroadbandImportColumns.CIRCUIT_DOWNLOAD_SPEED, 100, true));
        sourceMappers.put(BroadbandImportColumns.CIRCUIT_UPLOAD_SPEED, new StringSourceMapper(adapter, BroadbandImportColumns.CIRCUIT_UPLOAD_SPEED, 100, true));
        sourceMappers.put(BroadbandImportColumns.DATE_ENTERED_IN_INVENTORY, new StringSourceMapper(adapter, BroadbandImportColumns.DATE_ENTERED_IN_INVENTORY, 100, false));
        sourceMappers.put(BroadbandImportColumns.PROVIDER_ORDER, new StringSourceMapper(adapter, BroadbandImportColumns.PROVIDER_ORDER, 100, false));
        sourceMappers.put(BroadbandImportColumns.BILL_CYCLE, new StringSourceMapper(adapter, BroadbandImportColumns.BILL_CYCLE, 2, false));
        sourceMappers.put(BroadbandImportColumns.ACCOUNT_NUMBER_BAN, new StringSourceMapper(adapter, BroadbandImportColumns.ACCOUNT_NUMBER_BAN, 100, false));
        sourceMappers.put(BroadbandImportColumns.SUMMARY_BILL, new StringSourceMapper(adapter, BroadbandImportColumns.SUMMARY_BILL, 100, false));
        sourceMappers.put(BroadbandImportColumns.PROVIDER_CIRCUIT_ID, new StringSourceMapper(adapter, BroadbandImportColumns.PROVIDER_CIRCUIT_ID, 100, true));
        sourceMappers.put(BroadbandImportColumns.CIRCUIT_INSTALLED_DATE, new StringSourceMapper(adapter, BroadbandImportColumns.CIRCUIT_INSTALLED_DATE, 100, false));
        sourceMappers.put(BroadbandImportColumns.MEDIA_TYPE, new StringSourceMapper(adapter, BroadbandImportColumns.MEDIA_TYPE, 100, false));
        sourceMappers.put(BroadbandImportColumns.DMARC, new StringSourceMapper(adapter, BroadbandImportColumns.DMARC, 100, false));
        sourceMappers.put(BroadbandImportColumns.LOCATION_HOURS, new StringSourceMapper(adapter, BroadbandImportColumns.LOCATION_HOURS, 100, false));
        sourceMappers.put(BroadbandImportColumns.PROVIDER_MODEM, new StringSourceMapper(adapter, BroadbandImportColumns.PROVIDER_MODEM, 100, false));
        sourceMappers.put(BroadbandImportColumns.PROVIDER_MODEM_MAC, new StringSourceMapper(adapter, BroadbandImportColumns.PROVIDER_MODEM_MAC, 100, false));
        sourceMappers.put(BroadbandImportColumns.NETWORK_PROTOCOL, new StringSourceMapper(adapter, BroadbandImportColumns.NETWORK_PROTOCOL, 100, false));
        sourceMappers.put(BroadbandImportColumns.IP_FORMAT, new StringSourceMapper(adapter, BroadbandImportColumns.IP_FORMAT, 100, false));
        sourceMappers.put(BroadbandImportColumns.WAN_BLOCK, new StringSourceMapper(adapter, BroadbandImportColumns.WAN_BLOCK, 100, false));
        sourceMappers.put(BroadbandImportColumns.WAN_IPS, new StringSourceMapper(adapter, BroadbandImportColumns.WAN_IPS, 100, false));
        sourceMappers.put(BroadbandImportColumns.WAN_GATEWAY, new StringSourceMapper(adapter, BroadbandImportColumns.WAN_GATEWAY, 100, false));
        sourceMappers.put(BroadbandImportColumns.WAN_SUBNET, new StringSourceMapper(adapter, BroadbandImportColumns.WAN_SUBNET, 100, false));
        sourceMappers.put(BroadbandImportColumns.LAN_BLOCK, new StringSourceMapper(adapter, BroadbandImportColumns.LAN_BLOCK, 100, false));
        sourceMappers.put(BroadbandImportColumns.LAN_IPS, new StringSourceMapper(adapter, BroadbandImportColumns.LAN_IPS, 100, false));
        sourceMappers.put(BroadbandImportColumns.LAN_GATEWAY, new StringSourceMapper(adapter, BroadbandImportColumns.LAN_GATEWAY, 100, false));
        sourceMappers.put(BroadbandImportColumns.LAN_SUBNET, new StringSourceMapper(adapter, BroadbandImportColumns.LAN_SUBNET, 100, false));
        sourceMappers.put(BroadbandImportColumns.DNS1, new StringSourceMapper(adapter, BroadbandImportColumns.DNS1, 100, false));
        sourceMappers.put(BroadbandImportColumns.DNS2, new StringSourceMapper(adapter, BroadbandImportColumns.DNS2, 100, false));
        sourceMappers.put(BroadbandImportColumns.TSP_CODE, new StringSourceMapper(adapter, BroadbandImportColumns.TSP_CODE, 100, false));
        sourceMappers.put(BroadbandImportColumns.TSP_CODE_EXPIRATION_DATE, new StringSourceMapper(adapter, BroadbandImportColumns.TSP_CODE_EXPIRATION_DATE, 100, false));
        sourceMappers.put(BroadbandImportColumns.NOTES_TO_IMPORT, new StringSourceMapper(adapter, BroadbandImportColumns.NOTES_TO_IMPORT, 2147483647, false));
        return sourceMappers;
    }
}
