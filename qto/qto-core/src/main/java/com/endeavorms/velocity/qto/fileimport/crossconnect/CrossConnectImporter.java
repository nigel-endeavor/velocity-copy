package com.endeavorms.velocity.qto.fileimport.crossconnect;

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
import com.endeavorms.velocity.qto.service.crossconnect.CrossConnectService;
import com.endeavorms.velocity.qto.service.crossconnect.CrossConnectServiceManager;

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
 * @author fcurran
 * @since 1/16/2024
 */
@Component
@TransactionManagement(TransactionManagementType.BEAN)
public class CrossConnectImporter extends AbstractInventoryServiceImporter {
    @Inject
    private ServiceManager serviceManager;

    @Inject
    private CrossConnectServiceManager crossConnectServiceManager;

    @Inject
    private ServiceNoteManager serviceNoteManager;

    @Inject
    private ServiceMilestoneInstanceManager sMilestoneInstanceManager;

    @Override
    protected void importRow(final Map<String, ValidatingSourceMapper> sourceMappers, final ImportActivity importActivity) throws Exception {
        Date inventoryAddedDate;
        Long tenantId = importActivity.getTenantId();
        if (Strings.isNullOrEmpty(sourceMappers.get(CrossConnectImportColumns.DATE_ENTERED_IN_INVENTORY).getValue())) {
            inventoryAddedDate = new Date();
        } else {
            inventoryAddedDate = parseDate(sourceMappers.get(CrossConnectImportColumns.DATE_ENTERED_IN_INVENTORY).getValue());
        }
        Company endCustomer = handleEndCustomer(sourceMappers, tenantId);
        //Location Info
        Location location = handleOrderAndLocation(sourceMappers, tenantId, endCustomer,
                CrossConnectImportColumns.CLIENT_LOCATION_INFO, CrossConnectImportColumns.CLIENT_LOCATION_TYPE,
                CrossConnectImportColumns.ADDRESS_1, CrossConnectImportColumns.ADDRESS_2, CrossConnectImportColumns.CITY,
                CrossConnectImportColumns.STATE_PROVINCE_REGION, CrossConnectImportColumns.ZIP_POSTAL_CODE,
                CrossConnectImportColumns.COUNTRY, CrossConnectImportColumns.TIME_ZONE);;

        //Service
        CrossConnectService service = new CrossConnectService();
        service.setCurrentInventory(true);
        service.setBillable(true);
        service.setActive(true);
        service.setOrderType(OrderType.NEW.name());
        service.setOrderId(location.getOrderId());
        service.setLocationId(location.getId());
        service.setRecordSource(RecordSource.INVENTORY_IMPORT.getName());
        service.setClientServiceId(sourceMappers.get(CrossConnectImportColumns.CLIENT_SERVICE_ID).getValue());
        service.setDescription(sourceMappers.get(CrossConnectImportColumns.SERVICE_DESCRIPTION).getValue());
        service.setQuoteSolutionId(sourceMappers.get(CrossConnectImportColumns.QUOTE_SOLUTION_ID).getValue());
        service.setProjectName(sourceMappers.get(CrossConnectImportColumns.PROJECT_NAME).getValue());
        service.setServiceBilledTo(lookupValueMap.get(sourceMappers.get(CrossConnectImportColumns.SERVICE_BILLED_TO).getValue()));
        service.setProvider(lookupValueMap.get(sourceMappers.get(CrossConnectImportColumns.PROVIDER).getValue()));
        service.setUnderlyingProvider(lookupValueMap.get(sourceMappers.get(CrossConnectImportColumns.UNDERLYING_PROVIDER).getValue()));
        service.setSubProductType(sourceMappers.get(CrossConnectImportColumns.SUB_PRODUCT_TYPE).getValue());
        service.setPoNumber(sourceMappers.get(CrossConnectImportColumns.PO_NUMBER).getValue());
        service.setManagedService(parseBoolean(sourceMappers.get(CrossConnectImportColumns.MANAGED_SERVICE).getValue()));
        service.setProductionImpacting(parseBoolean(sourceMappers.get(CrossConnectImportColumns.PRODUCTION_IMPACTING).getValue()));
        service.setContractTerm(lookupValueMap.get(sourceMappers.get(CrossConnectImportColumns.CONTRACT_TERM).getValue()));
        if (!Strings.isNullOrEmpty(sourceMappers.get(CrossConnectImportColumns.CONTRACT_SIGNED_DATE).getNonValidatedValue())) {
            service.setContractSignedDate(parseDate(sourceMappers.get(CrossConnectImportColumns.CONTRACT_SIGNED_DATE).getValue()));
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(CrossConnectImportColumns.CONTRACT_END_DATE).getNonValidatedValue())) {
            service.setCircuitTermEndDate(parseDate(sourceMappers.get(CrossConnectImportColumns.CONTRACT_END_DATE).getValue()));
        }
        service.setContractInfo(sourceMappers.get(CrossConnectImportColumns.CONTRACT_INFO).getValue());
        service.setMrc(new BigDecimal(sourceMappers.get(CrossConnectImportColumns.MRC).getValue()));
        service.setNrc(new BigDecimal(sourceMappers.get(CrossConnectImportColumns.NRC).getValue()));
        service.setAnnualRecurringCost(new BigDecimal(sourceMappers.get(CrossConnectImportColumns.ANNUAL_RECURRING_COST).getValue()));
        service.setSpeed(service.getDownloadSpeed() + "/" + service.getUploadSpeed());
        service.setProviderOrderNum(sourceMappers.get(CrossConnectImportColumns.PROVIDER_ORDER).getValue());
        service.setBillCycle(parseLong(sourceMappers.get(CrossConnectImportColumns.BILL_CYCLE).getValue()));
        service.setAccountNumber(sourceMappers.get(CrossConnectImportColumns.ACCOUNT_NUMBER_BAN).getValue());
        service.setSummaryBill(sourceMappers.get(CrossConnectImportColumns.SUMMARY_BILL).getValue());
        //CrossConnectTechnical
        service.setCrossConnectId(sourceMappers.get(CrossConnectImportColumns.CROSS_CONNECT_ID).getValue());
        service.setCrossConnectRoom(sourceMappers.get(CrossConnectImportColumns.ROOM).getValue());
        service.setCrossConnectRack(sourceMappers.get(CrossConnectImportColumns.RACK).getValue());
        service.setCrossConnectPort(sourceMappers.get(CrossConnectImportColumns.PORT).getValue());
        service.setCrossConnectType(sourceMappers.get(CrossConnectImportColumns.XC_TYPE).getValue());
        service.setCrossConnectDataCenterName(sourceMappers.get(CrossConnectImportColumns.COLO_DC_NAME).getValue());
        if ("MTM".equals(service.getContractTerm()) || service.getCircuitTermEndDate() == null) {
            service.setIgnoreForRenewals(true);
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(CrossConnectImportColumns.ORIGINAL_MRC).getNonValidatedValue())) {
            service.setOriginalMrc(new BigDecimal(sourceMappers.get(CrossConnectImportColumns.ORIGINAL_MRC).getValue()));
        }
        handleCommonService(service, sourceMappers);
        service = crossConnectServiceManager.create(service);

        if (!Strings.isNullOrEmpty(sourceMappers.get(CrossConnectImportColumns.CIRCUIT_INSTALLED_DATE).getNonValidatedValue())) {
            sMilestoneInstanceManager.create(service.getId(), "DATA_PROVISIONING_COMPLETE", parseDate(sourceMappers.get(CrossConnectImportColumns.CIRCUIT_INSTALLED_DATE).getValue()));
        }
        if (!sMilestoneInstanceManager.doesMilestoneExist(service.getId(), "COMPLETE")) {
            sMilestoneInstanceManager.create(service.getId(), "COMPLETE", inventoryAddedDate);
        }

        if (!Strings.isNullOrEmpty(sourceMappers.get(CrossConnectImportColumns.NOTES_TO_IMPORT).getNonValidatedValue())) {
            serviceNoteManager.create(service.getId(), sourceMappers.get(CrossConnectImportColumns.NOTES_TO_IMPORT).getValue(), "Cross Connect", importActivity.getFileAttachment().getUploadedByUserName());
        }
    }

    @Override
    protected List<String> validateRow(Map<String, ValidatingSourceMapper> sourceMappers, Long tenantId) {
        List<String> errors = super.validateRow(sourceMappers, tenantId);

        //validate dropdown values
        //timezone
        errors = validateLookupValueCol(errors, sourceMappers.get(CrossConnectImportColumns.TIME_ZONE), "NA_TIME_ZONE", tenantId);
        //circuit owner
        errors = validateLookupValueCol(errors, sourceMappers.get(CrossConnectImportColumns.SERVICE_BILLED_TO), "SERVICE_BILLED_TO", tenantId);
        //PROVIDER
        errors = validateLookupValueCol(errors, sourceMappers.get(CrossConnectImportColumns.PROVIDER), "PROVIDER", tenantId);
        //underlying provider
        errors = validateLookupValueCol(errors, sourceMappers.get(CrossConnectImportColumns.UNDERLYING_PROVIDER), "UNDERLYING_PROVIDER", tenantId);
        //contract term
        errors = validateLookupValueCol(errors, sourceMappers.get(CrossConnectImportColumns.CONTRACT_TERM), "CONTRACT_TERM", tenantId);
        //cross connect type
        errors = validateLookupValueCol(errors, sourceMappers.get(CrossConnectImportColumns.XC_TYPE), "CROSS_CONNECT_TYPE", tenantId);

        //verify date columns can be parsed
        //contract signed date
        errors = validateDateCol(errors, sourceMappers.get(CrossConnectImportColumns.CONTRACT_SIGNED_DATE));
        //contract end date
        errors = validateDateCol(errors, sourceMappers.get(CrossConnectImportColumns.CONTRACT_END_DATE));
        //date entered in inventory
        errors = validateDateCol(errors, sourceMappers.get(CrossConnectImportColumns.DATE_ENTERED_IN_INVENTORY));
        //circuit installed date
        errors = validateDateCol(errors, sourceMappers.get(CrossConnectImportColumns.CIRCUIT_INSTALLED_DATE));
        //project name
        errors = validateLookupValueCol(errors, sourceMappers.get(CrossConnectImportColumns.PROJECT_NAME), "PROJECT_NAME", tenantId);

        //verify decimal columns can be parsed
        //mrc
        errors = validateBigDecimalCol(errors, sourceMappers.get(CrossConnectImportColumns.MRC));
        //nrc
        errors = validateBigDecimalCol(errors, sourceMappers.get(CrossConnectImportColumns.NRC));
        //Annual Recurring Cost
        errors = validateBigDecimalCol(errors, sourceMappers.get(CrossConnectImportColumns.ANNUAL_RECURRING_COST));
        //original mrc
        errors = validateBigDecimalCol(errors, sourceMappers.get(CrossConnectImportColumns.ORIGINAL_MRC));

        //verify long columns can be parsed
        //bill cycle
        errors = validateLongCol(errors, sourceMappers.get(CrossConnectImportColumns.BILL_CYCLE));

        return errors;
    }

    @Override
    protected List<String> getExpectedColumns() {
        return CrossConnectImportColumns.getExpectedColumns();
    }

    @Override
    protected String getTemplateName() {
        return "Cross Connect";
    }

    @Override
    protected String getImportTypeName() {
        return "Cross Connect Services";
    }

    @Override
    protected Map<String, ValidatingSourceMapper> buildSourceMappers(final ExcelAdapter adapter) {
        HashMap<String, ValidatingSourceMapper> sourceMappers = new HashMap<>();
        handleCommonSourceMappers(sourceMappers, adapter);
        sourceMappers.put(CrossConnectImportColumns.MASTER_CUSTOMER, new StringSourceMapper(adapter, CrossConnectImportColumns.MASTER_CUSTOMER, 100, true));
        sourceMappers.put(CrossConnectImportColumns.END_CUSTOMER, new StringSourceMapper(adapter, CrossConnectImportColumns.END_CUSTOMER, 100, true));
        sourceMappers.put(CrossConnectImportColumns.I90_PROJECT_MANAGER, new StringSourceMapper(adapter, CrossConnectImportColumns.I90_PROJECT_MANAGER, 100, false));
        sourceMappers.put(CrossConnectImportColumns.CLIENT_LOCATION_ID, new StringSourceMapper(adapter, CrossConnectImportColumns.CLIENT_LOCATION_ID, 100, true));
        sourceMappers.put(CrossConnectImportColumns.CLIENT_SERVICE_ID, new StringSourceMapper(adapter, CrossConnectImportColumns.CLIENT_SERVICE_ID, 100, false));
        sourceMappers.put(CrossConnectImportColumns.ADDRESS_1, new StringSourceMapper(adapter, CrossConnectImportColumns.ADDRESS_1, 100, false));
        sourceMappers.put(CrossConnectImportColumns.ADDRESS_2, new StringSourceMapper(adapter, CrossConnectImportColumns.ADDRESS_2, 100, false));
        sourceMappers.put(CrossConnectImportColumns.CITY, new StringSourceMapper(adapter, CrossConnectImportColumns.CITY, 100, false));
        sourceMappers.put(CrossConnectImportColumns.STATE_PROVINCE_REGION, new StringSourceMapper(adapter, CrossConnectImportColumns.STATE_PROVINCE_REGION, 100, false));
        sourceMappers.put(CrossConnectImportColumns.ZIP_POSTAL_CODE, new StringSourceMapper(adapter, CrossConnectImportColumns.ZIP_POSTAL_CODE, 20, false));
        sourceMappers.put(CrossConnectImportColumns.COUNTRY, new StringSourceMapper(adapter, CrossConnectImportColumns.COUNTRY, 100, false));
        sourceMappers.put(CrossConnectImportColumns.TIME_ZONE, new StringSourceMapper(adapter, CrossConnectImportColumns.TIME_ZONE, 100, false));
        sourceMappers.put(CrossConnectImportColumns.LOCATION_DESCRIPTION, new StringSourceMapper(adapter, CrossConnectImportColumns.LOCATION_DESCRIPTION, 500, false));
        sourceMappers.put(CrossConnectImportColumns.SERVICE_DESCRIPTION, new StringSourceMapper(adapter, CrossConnectImportColumns.SERVICE_DESCRIPTION, 500, false));
        sourceMappers.put(CrossConnectImportColumns.LCON_NAME, new StringSourceMapper(adapter, CrossConnectImportColumns.LCON_NAME, 100, false));
        sourceMappers.put(CrossConnectImportColumns.LCON_EMAIL, new StringSourceMapper(adapter, CrossConnectImportColumns.LCON_EMAIL, 100, false));
        sourceMappers.put(CrossConnectImportColumns.LCON_PHONE, new StringSourceMapper(adapter, CrossConnectImportColumns.LCON_PHONE, 100, false));
        sourceMappers.put(CrossConnectImportColumns.CLIENT_LOCATION_INFO, new StringSourceMapper(adapter, CrossConnectImportColumns.CLIENT_LOCATION_INFO, 100, false));
        sourceMappers.put(CrossConnectImportColumns.CLIENT_LOCATION_TYPE, new StringSourceMapper(adapter, CrossConnectImportColumns.CLIENT_LOCATION_TYPE, 100, false));
        sourceMappers.put(CrossConnectImportColumns.QUOTE_SOLUTION_ID, new StringSourceMapper(adapter, CrossConnectImportColumns.QUOTE_SOLUTION_ID, 100, false));
        sourceMappers.put(CrossConnectImportColumns.PROJECT_NAME, new StringSourceMapper(adapter, CrossConnectImportColumns.PROJECT_NAME, 100, false));
        sourceMappers.put(CrossConnectImportColumns.SERVICE_BILLED_TO, new StringSourceMapper(adapter, CrossConnectImportColumns.SERVICE_BILLED_TO, 100, true));
        sourceMappers.put(CrossConnectImportColumns.PROVIDER, new StringSourceMapper(adapter, CrossConnectImportColumns.PROVIDER, 100, true));
        sourceMappers.put(CrossConnectImportColumns.UNDERLYING_PROVIDER, new StringSourceMapper(adapter, CrossConnectImportColumns.UNDERLYING_PROVIDER, 100, false));
        sourceMappers.put(CrossConnectImportColumns.SUB_PRODUCT_TYPE, new StringSourceMapper(adapter, CrossConnectImportColumns.SUB_PRODUCT_TYPE, 100, false));
        sourceMappers.put(CrossConnectImportColumns.PO_NUMBER, new StringSourceMapper(adapter, CrossConnectImportColumns.PO_NUMBER, 100, false));
        sourceMappers.put(CrossConnectImportColumns.MANAGED_SERVICE, new StringSourceMapper(adapter, CrossConnectImportColumns.MANAGED_SERVICE, 100, false));
        sourceMappers.put(CrossConnectImportColumns.PRODUCTION_IMPACTING, new StringSourceMapper(adapter, CrossConnectImportColumns.PRODUCTION_IMPACTING, 100, false));
        sourceMappers.put(CrossConnectImportColumns.CONTRACT_TERM, new StringSourceMapper(adapter, CrossConnectImportColumns.CONTRACT_TERM, 100, false));
        sourceMappers.put(CrossConnectImportColumns.CONTRACT_SIGNED_DATE, new StringSourceMapper(adapter, CrossConnectImportColumns.CONTRACT_SIGNED_DATE, 100, false));
        sourceMappers.put(CrossConnectImportColumns.CONTRACT_END_DATE, new StringSourceMapper(adapter, CrossConnectImportColumns.CONTRACT_END_DATE, 100, false));
        sourceMappers.put(CrossConnectImportColumns.CONTRACT_INFO, new StringSourceMapper(adapter, CrossConnectImportColumns.CONTRACT_INFO, 500, false));
        sourceMappers.put(CrossConnectImportColumns.MRC, new StringSourceMapper(adapter, CrossConnectImportColumns.MRC, 100, true));
        sourceMappers.put(CrossConnectImportColumns.NRC, new StringSourceMapper(adapter, CrossConnectImportColumns.NRC, 100, true));
        sourceMappers.put(CrossConnectImportColumns.ORIGINAL_MRC, new StringSourceMapper(adapter, CrossConnectImportColumns.ORIGINAL_MRC, 100, false));
        sourceMappers.put(CrossConnectImportColumns.ANNUAL_RECURRING_COST, new StringSourceMapper(adapter, CrossConnectImportColumns.ANNUAL_RECURRING_COST, 100, true));
        sourceMappers.put(CrossConnectImportColumns.DATE_ENTERED_IN_INVENTORY, new StringSourceMapper(adapter, CrossConnectImportColumns.DATE_ENTERED_IN_INVENTORY, 100, false));
        sourceMappers.put(CrossConnectImportColumns.PROVIDER_ORDER, new StringSourceMapper(adapter, CrossConnectImportColumns.PROVIDER_ORDER, 100, false));
        sourceMappers.put(CrossConnectImportColumns.BILL_CYCLE, new StringSourceMapper(adapter, CrossConnectImportColumns.BILL_CYCLE, 2, false));
        sourceMappers.put(CrossConnectImportColumns.ACCOUNT_NUMBER_BAN, new StringSourceMapper(adapter, CrossConnectImportColumns.ACCOUNT_NUMBER_BAN, 100, false));
        sourceMappers.put(CrossConnectImportColumns.SUMMARY_BILL, new StringSourceMapper(adapter, CrossConnectImportColumns.SUMMARY_BILL, 100, false));
        sourceMappers.put(CrossConnectImportColumns.CROSS_CONNECT_ID, new StringSourceMapper(adapter, CrossConnectImportColumns.CROSS_CONNECT_ID, 100, false));
        sourceMappers.put(CrossConnectImportColumns.ROOM, new StringSourceMapper(adapter, CrossConnectImportColumns.ROOM, 100, false));
        sourceMappers.put(CrossConnectImportColumns.RACK, new StringSourceMapper(adapter, CrossConnectImportColumns.RACK, 100, false));
        sourceMappers.put(CrossConnectImportColumns.PORT, new StringSourceMapper(adapter, CrossConnectImportColumns.PORT, 100, false));
        sourceMappers.put(CrossConnectImportColumns.XC_TYPE, new StringSourceMapper(adapter, CrossConnectImportColumns.XC_TYPE, 100, false));
        sourceMappers.put(CrossConnectImportColumns.COLO_DC_NAME, new StringSourceMapper(adapter, CrossConnectImportColumns.COLO_DC_NAME, 100, false));
        sourceMappers.put(CrossConnectImportColumns.CIRCUIT_INSTALLED_DATE, new StringSourceMapper(adapter, CrossConnectImportColumns.CIRCUIT_INSTALLED_DATE, 100, false));
        sourceMappers.put(CrossConnectImportColumns.NOTES_TO_IMPORT, new StringSourceMapper(adapter, CrossConnectImportColumns.NOTES_TO_IMPORT, 2147483647, false));
        return sourceMappers;
    }
}
