package com.vertek.corporate.qto.fileimport.television;

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
import com.vertek.corporate.qto.service.ServiceManager;
import com.vertek.corporate.qto.service.television.TelevisionService;
import com.vertek.corporate.qto.service.television.TelevisionServiceManager;

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
public class TelevisionImporter extends AbstractInventoryServiceImporter {

    @Inject
    private ServiceManager serviceManager;

    @Inject
    private TelevisionServiceManager televisionServiceManager;

    @Inject
    private ServiceNoteManager serviceNoteManager;

    @Inject
    private ServiceMilestoneInstanceManager sMilestoneInstanceManager;

    @Override
    protected void importRow(final Map<String, ValidatingSourceMapper> sourceMappers, final ImportActivity importActivity) throws Exception {
        Date inventoryAddedDate;
        Long tenantId = importActivity.getTenantId();
        if (Strings.isNullOrEmpty(sourceMappers.get(TelevisionImportColumns.DATE_ENTERED_IN_INVENTORY).getValue())) {
            inventoryAddedDate = new Date();
        } else {
            inventoryAddedDate = parseDate(sourceMappers.get(TelevisionImportColumns.DATE_ENTERED_IN_INVENTORY).getValue());
        }
        Company endCustomer = handleEndCustomer(sourceMappers, tenantId);
        //Location Info
        Location location = handleOrderAndLocation(sourceMappers, tenantId, endCustomer,
                TelevisionImportColumns.CLIENT_LOCATION_INFO, TelevisionImportColumns.CLIENT_LOCATION_TYPE,
                TelevisionImportColumns.ADDRESS_1, TelevisionImportColumns.ADDRESS_2, TelevisionImportColumns.CITY,
                TelevisionImportColumns.STATE_PROVINCE_REGION, TelevisionImportColumns.ZIP_POSTAL_CODE,
                TelevisionImportColumns.COUNTRY, TelevisionImportColumns.TIME_ZONE);

        //Service
        TelevisionService service = new TelevisionService();
        service.setCurrentInventory(true);
        service.setBillable(true);
        service.setActive(true);
        service.setOrderType(OrderType.NEW.name());
        service.setOrderId(location.getOrderId());
        service.setLocationId(location.getId());
        service.setRecordSource(RecordSource.INVENTORY_IMPORT.getName());
        service.setClientServiceId(sourceMappers.get(TelevisionImportColumns.CLIENT_SERVICE_ID).getValue());
        service.setDescription(sourceMappers.get(TelevisionImportColumns.SERVICE_DESCRIPTION).getValue());
        service.setQuoteSolutionId(sourceMappers.get(TelevisionImportColumns.QUOTE_SOLUTION_ID).getValue());
        service.setProjectName(sourceMappers.get(TelevisionImportColumns.PROJECT_NAME).getValue());
        service.setServiceBilledTo(lookupValueMap.get(sourceMappers.get(TelevisionImportColumns.SERVICE_BILLED_TO).getValue()));
        service.setProvider(lookupValueMap.get(sourceMappers.get(TelevisionImportColumns.PROVIDER).getValue()));
        service.setUnderlyingProvider(lookupValueMap.get(sourceMappers.get(TelevisionImportColumns.UNDERLYING_PROVIDER).getValue()));
        service.setSubProductType(sourceMappers.get(TelevisionImportColumns.SUB_PRODUCT_TYPE).getValue());
        service.setPoNumber(sourceMappers.get(TelevisionImportColumns.PO_NUMBER).getValue());
        service.setManagedService(parseBoolean(sourceMappers.get(TelevisionImportColumns.MANAGED_SERVICE).getValue()));
        service.setProductionImpacting(parseBoolean(sourceMappers.get(TelevisionImportColumns.PRODUCTION_IMPACTING).getValue()));
        service.setBillCycle(parseLong(sourceMappers.get(TelevisionImportColumns.BILL_CYCLE).getValue()));
        service.setContractTerm(lookupValueMap.get(sourceMappers.get(TelevisionImportColumns.CONTRACT_TERM).getValue()));
        if (!Strings.isNullOrEmpty(sourceMappers.get(TelevisionImportColumns.CONTRACT_SIGNED_DATE).getNonValidatedValue())) {
            service.setContractSignedDate(parseDate(sourceMappers.get(TelevisionImportColumns.CONTRACT_SIGNED_DATE).getValue()));
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(TelevisionImportColumns.CONTRACT_END_DATE).getNonValidatedValue())) {
            service.setCircuitTermEndDate(parseDate(sourceMappers.get(TelevisionImportColumns.CONTRACT_END_DATE).getValue()));
        }
        service.setContractInfo(sourceMappers.get(TelevisionImportColumns.CONTRACT_INFO).getValue());
        service.setMrc(new BigDecimal(sourceMappers.get(TelevisionImportColumns.MRC).getValue()));
        service.setNrc(new BigDecimal(sourceMappers.get(TelevisionImportColumns.NRC).getValue()));
        service.setAnnualRecurringCost(new BigDecimal(sourceMappers.get(TelevisionImportColumns.ANNUAL_RECURRING_COST).getValue()));
        service.setProviderOrderNum(sourceMappers.get(TelevisionImportColumns.PROVIDER_ORDER).getValue());
        service.setBillCycle(parseLong(sourceMappers.get(TelevisionImportColumns.BILL_CYCLE).getValue()));
        service.setAccountNumber(sourceMappers.get(TelevisionImportColumns.ACCOUNT_NUMBER_BAN).getValue());
        service.setProviderCircuitId(sourceMappers.get(TelevisionImportColumns.PROVIDER_CIRCUIT_ID).getValue());
        service.setPlan(sourceMappers.get(TelevisionImportColumns.PLAN).getValue());
        service.setDvrIncluded(parseBoolean(sourceMappers.get(TelevisionImportColumns.DVR_INCLUDED).getValue()));
        service.setMediaType(lookupValueMap.get(sourceMappers.get(TelevisionImportColumns.MEDIA_TYPE).getValue()));
        service.setDmarc(sourceMappers.get(TelevisionImportColumns.DMARC).getValue());
        service.setLocationHours(sourceMappers.get(TelevisionImportColumns.LOCATION_HOURS).getValue());
        if ("MTM".equals(service.getContractTerm()) || service.getCircuitTermEndDate() == null) {
            service.setIgnoreForRenewals(true);
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(TelevisionImportColumns.ORIGINAL_MRC).getNonValidatedValue())) {
            service.setOriginalMrc(new BigDecimal(sourceMappers.get(TelevisionImportColumns.ORIGINAL_MRC).getValue()));
        }
        handleCommonService(service, sourceMappers);
        service = televisionServiceManager.create(service);

        if (!Strings.isNullOrEmpty(sourceMappers.get(TelevisionImportColumns.CIRCUIT_INSTALLED_DATE).getNonValidatedValue())) {
            sMilestoneInstanceManager.create(service.getId(), "DATA_PROVISIONING_COMPLETE", parseDate(sourceMappers.get(TelevisionImportColumns.CIRCUIT_INSTALLED_DATE).getValue()));
        }
        if (!sMilestoneInstanceManager.doesMilestoneExist(service.getId(), "COMPLETE")) {
            sMilestoneInstanceManager.create(service.getId(), "COMPLETE", inventoryAddedDate);
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(TelevisionImportColumns.NOTES_TO_IMPORT).getNonValidatedValue())) {
            serviceNoteManager.create(service.getId(), sourceMappers.get(TelevisionImportColumns.NOTES_TO_IMPORT).getValue(), "Television", importActivity.getFileAttachment().getUploadedByUserName());
        }
    }

    @Override
    protected List<String> validateRow(Map<String, ValidatingSourceMapper> sourceMappers, Long tenantId) {
        List<String> errors = super.validateRow(sourceMappers, tenantId);

        //validate dropdown values
        //timezone
        errors = validateLookupValueCol(errors, sourceMappers.get(TelevisionImportColumns.TIME_ZONE), "NA_TIME_ZONE", tenantId);
        //circuit owner
        errors = validateLookupValueCol(errors, sourceMappers.get(TelevisionImportColumns.SERVICE_BILLED_TO), "SERVICE_BILLED_TO", tenantId);
        //provider
        errors = validateLookupValueCol(errors, sourceMappers.get(TelevisionImportColumns.PROVIDER), "PROVIDER", tenantId);
        //underlying provider
        errors = validateLookupValueCol(errors, sourceMappers.get(TelevisionImportColumns.UNDERLYING_PROVIDER), "UNDERLYING_PROVIDER", tenantId);
        //contract term
        errors = validateLookupValueCol(errors, sourceMappers.get(TelevisionImportColumns.CONTRACT_TERM), "CONTRACT_TERM", tenantId);
        //media type
        errors = validateLookupValueCol(errors, sourceMappers.get(TelevisionImportColumns.MEDIA_TYPE), "MEDIA_TYPE", tenantId);
        //project name
        errors = validateLookupValueCol(errors, sourceMappers.get(TelevisionImportColumns.PROJECT_NAME), "PROJECT_NAME", tenantId);

        //verify date columns can be parsed
        //contract signed date
        errors = validateDateCol(errors, sourceMappers.get(TelevisionImportColumns.CONTRACT_SIGNED_DATE));
        //contract end date
        errors = validateDateCol(errors, sourceMappers.get(TelevisionImportColumns.CONTRACT_END_DATE));
        //date entered in inventory
        errors = validateDateCol(errors, sourceMappers.get(TelevisionImportColumns.DATE_ENTERED_IN_INVENTORY));
        //Circuit Installed Date
        errors = validateDateCol(errors, sourceMappers.get(TelevisionImportColumns.CIRCUIT_INSTALLED_DATE));

        //verify decimal columns can be parsed
        //mrc
        errors = validateBigDecimalCol(errors, sourceMappers.get(TelevisionImportColumns.MRC));
        //nrc
        errors = validateBigDecimalCol(errors, sourceMappers.get(TelevisionImportColumns.NRC));
        //annual recurring cost
        errors = validateBigDecimalCol(errors, sourceMappers.get(TelevisionImportColumns.ANNUAL_RECURRING_COST));
        //original mrc
        errors = validateBigDecimalCol(errors, sourceMappers.get(TelevisionImportColumns.ORIGINAL_MRC));

        //verify long columns can be parsed
        //bill cycle
        errors = validateLongCol(errors, sourceMappers.get(TelevisionImportColumns.BILL_CYCLE));
        return errors;
    }

    @Override
    protected List<String> getExpectedColumns() {
        return TelevisionImportColumns.getExpectedColumns();
    }

    @Override
    protected String getTemplateName() {
        return "Television";
    }

    @Override
    protected String getImportTypeName() {
        return "Television Services";
    }

    @Override
    protected Map<String, ValidatingSourceMapper> buildSourceMappers(final ExcelAdapter adapter) {
        HashMap<String, ValidatingSourceMapper> sourceMappers = new HashMap<>();
        handleCommonSourceMappers(sourceMappers, adapter);
        sourceMappers.put(TelevisionImportColumns.MASTER_CUSTOMER, new StringSourceMapper(adapter, TelevisionImportColumns.MASTER_CUSTOMER, 100, true));
        sourceMappers.put(TelevisionImportColumns.END_CUSTOMER, new StringSourceMapper(adapter, TelevisionImportColumns.END_CUSTOMER, 100, true));
        sourceMappers.put(TelevisionImportColumns.I90_PROJECT_MANAGER, new StringSourceMapper(adapter, TelevisionImportColumns.I90_PROJECT_MANAGER, 100, false));
        sourceMappers.put(TelevisionImportColumns.CLIENT_LOCATION_ID, new StringSourceMapper(adapter, TelevisionImportColumns.CLIENT_LOCATION_ID, 100, true));
        sourceMappers.put(TelevisionImportColumns.CLIENT_SERVICE_ID, new StringSourceMapper(adapter, TelevisionImportColumns.CLIENT_SERVICE_ID, 100, false));
        sourceMappers.put(TelevisionImportColumns.ADDRESS_1, new StringSourceMapper(adapter, TelevisionImportColumns.ADDRESS_1, 100, false));
        sourceMappers.put(TelevisionImportColumns.ADDRESS_2, new StringSourceMapper(adapter, TelevisionImportColumns.ADDRESS_2, 100, false));
        sourceMappers.put(TelevisionImportColumns.CITY, new StringSourceMapper(adapter, TelevisionImportColumns.CITY, 100, false));
        sourceMappers.put(TelevisionImportColumns.STATE_PROVINCE_REGION, new StringSourceMapper(adapter, TelevisionImportColumns.STATE_PROVINCE_REGION, 100, false));
        sourceMappers.put(TelevisionImportColumns.ZIP_POSTAL_CODE, new StringSourceMapper(adapter, TelevisionImportColumns.ZIP_POSTAL_CODE, 20, false));
        sourceMappers.put(TelevisionImportColumns.COUNTRY, new StringSourceMapper(adapter, TelevisionImportColumns.COUNTRY, 100, false));
        sourceMappers.put(TelevisionImportColumns.TIME_ZONE, new StringSourceMapper(adapter, TelevisionImportColumns.TIME_ZONE, 100, false));
        sourceMappers.put(TelevisionImportColumns.LOCATION_DESCRIPTION, new StringSourceMapper(adapter, TelevisionImportColumns.LOCATION_DESCRIPTION, 500, false));
        sourceMappers.put(TelevisionImportColumns.SERVICE_DESCRIPTION, new StringSourceMapper(adapter, TelevisionImportColumns.SERVICE_DESCRIPTION, 500, false));
        sourceMappers.put(TelevisionImportColumns.LCON_NAME, new StringSourceMapper(adapter, TelevisionImportColumns.LCON_NAME, 100, false));
        sourceMappers.put(TelevisionImportColumns.LCON_EMAIL, new StringSourceMapper(adapter, TelevisionImportColumns.LCON_EMAIL, 100, false));
        sourceMappers.put(TelevisionImportColumns.LCON_PHONE, new StringSourceMapper(adapter, TelevisionImportColumns.LCON_PHONE, 100, false));
        sourceMappers.put(TelevisionImportColumns.CLIENT_LOCATION_INFO, new StringSourceMapper(adapter, TelevisionImportColumns.CLIENT_LOCATION_INFO, 100, false));
        sourceMappers.put(TelevisionImportColumns.CLIENT_LOCATION_TYPE, new StringSourceMapper(adapter, TelevisionImportColumns.CLIENT_LOCATION_TYPE, 100, false));
        sourceMappers.put(TelevisionImportColumns.QUOTE_SOLUTION_ID, new StringSourceMapper(adapter, TelevisionImportColumns.QUOTE_SOLUTION_ID, 100, false));
        sourceMappers.put(TelevisionImportColumns.PROJECT_NAME, new StringSourceMapper(adapter, TelevisionImportColumns.PROJECT_NAME, 100, false));
        sourceMappers.put(TelevisionImportColumns.SERVICE_BILLED_TO, new StringSourceMapper(adapter, TelevisionImportColumns.SERVICE_BILLED_TO, 100, true));
        sourceMappers.put(TelevisionImportColumns.PROVIDER, new StringSourceMapper(adapter, TelevisionImportColumns.PROVIDER, 100, true));
        sourceMappers.put(TelevisionImportColumns.UNDERLYING_PROVIDER, new StringSourceMapper(adapter, TelevisionImportColumns.UNDERLYING_PROVIDER, 100, false));
        sourceMappers.put(TelevisionImportColumns.SUB_PRODUCT_TYPE, new StringSourceMapper(adapter, TelevisionImportColumns.SUB_PRODUCT_TYPE, 100, false));
        sourceMappers.put(TelevisionImportColumns.PO_NUMBER, new StringSourceMapper(adapter, TelevisionImportColumns.PO_NUMBER, 100, false));
        sourceMappers.put(TelevisionImportColumns.MANAGED_SERVICE, new StringSourceMapper(adapter, TelevisionImportColumns.MANAGED_SERVICE, 100, false));
        sourceMappers.put(TelevisionImportColumns.PRODUCTION_IMPACTING, new StringSourceMapper(adapter, TelevisionImportColumns.PRODUCTION_IMPACTING, 100, false));
        sourceMappers.put(TelevisionImportColumns.CONTRACT_TERM, new StringSourceMapper(adapter, TelevisionImportColumns.CONTRACT_TERM, 100, false));
        sourceMappers.put(TelevisionImportColumns.CONTRACT_SIGNED_DATE, new StringSourceMapper(adapter, TelevisionImportColumns.CONTRACT_SIGNED_DATE, 100, false));
        sourceMappers.put(TelevisionImportColumns.CONTRACT_END_DATE, new StringSourceMapper(adapter, TelevisionImportColumns.CONTRACT_END_DATE, 100, false));
        sourceMappers.put(TelevisionImportColumns.CIRCUIT_INSTALLED_DATE, new StringSourceMapper(adapter, TelevisionImportColumns.CIRCUIT_INSTALLED_DATE, 100, false));
        sourceMappers.put(TelevisionImportColumns.CONTRACT_INFO, new StringSourceMapper(adapter, TelevisionImportColumns.CONTRACT_INFO, 100, false));
        sourceMappers.put(TelevisionImportColumns.MRC, new StringSourceMapper(adapter, TelevisionImportColumns.MRC, 100, true));
        sourceMappers.put(TelevisionImportColumns.NRC, new StringSourceMapper(adapter, TelevisionImportColumns.NRC, 100, true));
        sourceMappers.put(TelevisionImportColumns.ANNUAL_RECURRING_COST, new StringSourceMapper(adapter, TelevisionImportColumns.ANNUAL_RECURRING_COST, 100, true));
        sourceMappers.put(TelevisionImportColumns.ORIGINAL_MRC, new StringSourceMapper(adapter, TelevisionImportColumns.ORIGINAL_MRC, 100, false));
        sourceMappers.put(TelevisionImportColumns.DATE_ENTERED_IN_INVENTORY, new StringSourceMapper(adapter, TelevisionImportColumns.DATE_ENTERED_IN_INVENTORY, 100, false));
        sourceMappers.put(TelevisionImportColumns.PROVIDER_ORDER, new StringSourceMapper(adapter, TelevisionImportColumns.PROVIDER_ORDER, 100, false));
        sourceMappers.put(TelevisionImportColumns.BILL_CYCLE, new StringSourceMapper(adapter, TelevisionImportColumns.BILL_CYCLE, 2, false));
        sourceMappers.put(TelevisionImportColumns.ACCOUNT_NUMBER_BAN, new StringSourceMapper(adapter, TelevisionImportColumns.ACCOUNT_NUMBER_BAN, 100, false));
        sourceMappers.put(TelevisionImportColumns.SUMMARY_BILL, new StringSourceMapper(adapter, TelevisionImportColumns.SUMMARY_BILL, 100, false));
        sourceMappers.put(TelevisionImportColumns.PROVIDER_CIRCUIT_ID, new StringSourceMapper(adapter, TelevisionImportColumns.PROVIDER_CIRCUIT_ID, 100, true));
        sourceMappers.put(TelevisionImportColumns.PLAN, new StringSourceMapper(adapter, TelevisionImportColumns.PLAN, 100, false));
        sourceMappers.put(TelevisionImportColumns.DVR_INCLUDED, new StringSourceMapper(adapter, TelevisionImportColumns.DVR_INCLUDED, 100, false));
        sourceMappers.put(TelevisionImportColumns.MEDIA_TYPE, new StringSourceMapper(adapter, TelevisionImportColumns.MEDIA_TYPE, 100, false));
        sourceMappers.put(TelevisionImportColumns.DMARC, new StringSourceMapper(adapter, TelevisionImportColumns.DMARC, 100, false));
        sourceMappers.put(TelevisionImportColumns.LOCATION_HOURS, new StringSourceMapper(adapter, TelevisionImportColumns.LOCATION_HOURS, 100, false));
        sourceMappers.put(TelevisionImportColumns.NOTES_TO_IMPORT, new StringSourceMapper(adapter, TelevisionImportColumns.NOTES_TO_IMPORT, 2147483647, false));
        return sourceMappers;
    }

}
