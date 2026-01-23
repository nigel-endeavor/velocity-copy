package com.vertek.corporate.qto.fileimport.ethernet;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.RecordSource;
import com.vertek.corporate.qto.address.AddressView;
import com.vertek.corporate.qto.address.AddressViewManager;
import com.vertek.corporate.qto.common.adapter.ExcelAdapter;
import com.vertek.corporate.qto.common.mapping.StringSourceMapper;
import com.vertek.corporate.qto.common.mapping.ValidatingSourceMapper;
import com.vertek.corporate.qto.company.Company;
import com.vertek.corporate.qto.fileimport.AbstractInventoryServiceImportColumns;
import com.vertek.corporate.qto.fileimport.AbstractInventoryServiceImporter;
import com.vertek.corporate.qto.fileimport.importactivity.ImportActivity;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstanceManager;
import com.vertek.corporate.qto.note.ServiceNoteManager;
import com.vertek.corporate.qto.service.OrderType;
import com.vertek.corporate.qto.service.ServiceManager;
import com.vertek.corporate.qto.service.ethernet.EthernetService;
import com.vertek.corporate.qto.service.ethernet.EthernetServiceManager;

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
 * @since 1/18/2024
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class EthernetImporter extends AbstractInventoryServiceImporter {

    @Inject
    private ServiceManager serviceManager;

    @Inject
    private EthernetServiceManager ethernetServiceManager;

    @Inject
    private ServiceNoteManager serviceNoteManager;

    @Inject
    private ServiceMilestoneInstanceManager sMilestoneInstanceManager;

    @Inject
    private AddressViewManager addressViewManager;


    @Override
    protected void importRow(final Map<String, ValidatingSourceMapper> sourceMappers, final ImportActivity importActivity) throws Exception {
        Date inventoryAddedDate;
        Long tenantId = importActivity.getTenantId();
        if (Strings.isNullOrEmpty(sourceMappers.get(EthernetImportColumns.DATE_ENTERED_IN_INVENTORY).getValue())) {
            inventoryAddedDate = new Date();
        } else {
            inventoryAddedDate = parseDate(sourceMappers.get(EthernetImportColumns.DATE_ENTERED_IN_INVENTORY).getValue());
        }
        Company endCustomer = handleEndCustomer(sourceMappers, tenantId);
        //Location Info
        Location location = handleOrderAndLocation(sourceMappers, tenantId, endCustomer,
                EthernetImportColumns.CLIENT_LOCATION_INFO, EthernetImportColumns.CLIENT_LOCATION_TYPE,
                EthernetImportColumns.ADDRESS_1, EthernetImportColumns.ADDRESS_2, EthernetImportColumns.CITY,
                EthernetImportColumns.STATE_PROVINCE_REGION, EthernetImportColumns.ZIP_POSTAL_CODE,
                EthernetImportColumns.COUNTRY, EthernetImportColumns.TIME_ZONE);

        //Service
        EthernetService service = new EthernetService();
        service.setCurrentInventory(true);
        service.setBillable(true);
        service.setActive(true);
        service.setOrderType(OrderType.NEW.name());
        service.setOrderId(location.getOrderId());
        service.setLocationId(location.getId());
        service.setRecordSource(RecordSource.INVENTORY_IMPORT.getName());
        service.setClientServiceId(sourceMappers.get(EthernetImportColumns.CLIENT_SERVICE_ID).getValue());
        service.setDescription(sourceMappers.get(EthernetImportColumns.SERVICE_DESCRIPTION).getValue());
        service.setQuoteSolutionId(sourceMappers.get(EthernetImportColumns.QUOTE_SOLUTION_ID).getValue());
        service.setProjectName(sourceMappers.get(EthernetImportColumns.PROJECT_NAME).getValue());
        service.setServiceBilledTo(lookupValueMap.get(sourceMappers.get(EthernetImportColumns.SERVICE_BILLED_TO).getValue()));
        service.setProvider(lookupValueMap.get(sourceMappers.get(EthernetImportColumns.PROVIDER).getValue()));
        service.setUnderlyingProvider(lookupValueMap.get(sourceMappers.get(EthernetImportColumns.UNDERLYING_PROVIDER).getValue()));
        service.setSubProductType(sourceMappers.get(EthernetImportColumns.SUB_PRODUCT_TYPE).getValue());
        service.setPoNumber(sourceMappers.get(EthernetImportColumns.PO_NUMBER).getValue());
        service.setManagedService(parseBoolean(sourceMappers.get(EthernetImportColumns.MANAGED_SERVICE).getValue()));
        service.setProductionImpacting(parseBoolean(sourceMappers.get(EthernetImportColumns.PRODUCTION_IMPACTING).getValue()));
        service.setProductType(lookupValueMap.get(sourceMappers.get(EthernetImportColumns.PRODUCT_TYPE).getValue()));
        service.setContractTerm(lookupValueMap.get(sourceMappers.get(EthernetImportColumns.CONTRACT_TERM).getValue()));
        if (!Strings.isNullOrEmpty(sourceMappers.get(EthernetImportColumns.CONTRACT_SIGNED_DATE).getNonValidatedValue())) {
            service.setContractSignedDate(parseDate(sourceMappers.get(EthernetImportColumns.CONTRACT_SIGNED_DATE).getValue()));
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(EthernetImportColumns.CONTRACT_END_DATE).getNonValidatedValue())) {
            service.setCircuitTermEndDate(parseDate(sourceMappers.get(EthernetImportColumns.CONTRACT_END_DATE).getValue()));
        }
        service.setContractInfo(sourceMappers.get(EthernetImportColumns.CONTRACT_INFO).getValue());
        service.setProviderOrderNum(sourceMappers.get(EthernetImportColumns.PROVIDER_ORDER_NUMBER).getValue());
        service.setBillCycle(parseLong(sourceMappers.get(EthernetImportColumns.BILL_CYCLE).getValue()));
        service.setAccountNumber(sourceMappers.get(EthernetImportColumns.ACCOUNT_NUMBER_BAN).getValue());
        service.setSummaryBill(sourceMappers.get(EthernetImportColumns.SUMMARY_BILL).getValue());
        service.setPortSpeed(lookupValueMap.get(sourceMappers.get(EthernetImportColumns.PORT_SPEED).getValue()));
        service.setDownloadSpeed(lookupValueMap.get(sourceMappers.get(EthernetImportColumns.ACCESS_SPEED).getValue()));
        service.setEaSpeed(lookupValueMap.get(sourceMappers.get(EthernetImportColumns.EA_SPEED).getValue()));
        service.setInterfaceConnector(lookupValueMap.get(sourceMappers.get(EthernetImportColumns.INTERFACE_CONNECTOR).getValue()));
        service.setMtu(lookupValueMap.get(sourceMappers.get(EthernetImportColumns.MTU).getValue()));
        service.setTrunkGroup(sourceMappers.get(EthernetImportColumns.TRUNK_GROUP).getValue());
        service.setMux(lookupValueMap.get(sourceMappers.get(EthernetImportColumns.MUX).getValue()));
        service.setVlanForEline(sourceMappers.get(EthernetImportColumns.CE_VLAN_FOR_E_LINE).getValue());
        service.setVlanTagging(sourceMappers.get(EthernetImportColumns.VLAN_TAGGING).getValue());
        service.setVlanId(sourceMappers.get(EthernetImportColumns.VLAN_ID).getValue());
        service.setCableCategory(lookupValueMap.get(sourceMappers.get(EthernetImportColumns.CABLE_CATEGORY).getValue()));
        service.setCableShielding(sourceMappers.get(EthernetImportColumns.CABLE_SHIELDING).getValue());
        service.setIpFormat(parseIpFormat(sourceMappers.get(EthernetImportColumns.IP_FORMAT).getValue()));
        service.setWanIps(sourceMappers.get(EthernetImportColumns.WAN_IPS).getValue());
        service.setWanGateway(sourceMappers.get(EthernetImportColumns.WAN_GATEWAY).getValue());
        service.setWanSubnet(sourceMappers.get(EthernetImportColumns.WAN_SUBNET).getValue());
        service.setLanIps(sourceMappers.get(EthernetImportColumns.LAN_IPS).getValue());
        service.setLanGateway(sourceMappers.get(EthernetImportColumns.LAN_GATEWAY).getValue());
        service.setLanSubnet(sourceMappers.get(EthernetImportColumns.LAN_SUBNET).getValue());
        service.setTspCode(sourceMappers.get(EthernetImportColumns.TSP_CODE).getValue());
        if (!Strings.isNullOrEmpty(sourceMappers.get(EthernetImportColumns.TSP_CODE_EXPIRATION_DATE).getNonValidatedValue())) {
            service.setTspCodeExpirationDate(parseDate(sourceMappers.get(EthernetImportColumns.TSP_CODE_EXPIRATION_DATE).getValue()));
        }
        service.setMrc(new BigDecimal(sourceMappers.get(EthernetImportColumns.A_END_MRC).getValue()));
        service.setNrc(new BigDecimal(sourceMappers.get(EthernetImportColumns.A_END_NRC).getValue()));
        service.setAnnualRecurringCost(new BigDecimal(sourceMappers.get(EthernetImportColumns.ANNUAL_RECURRING_COST).getValue()));
        service.setProviderCircuitId(sourceMappers.get(EthernetImportColumns.A_PROVIDER_CIRCUIT_ID).getValue());
        service.setAccessType(sourceMappers.get(EthernetImportColumns.A_ACCESS_TYPE).getValue());
        service.setInterfaceConnector(sourceMappers.get(EthernetImportColumns.A_INTERFACE_CONNECTOR).getValue());
        service.setAccessHours(sourceMappers.get(EthernetImportColumns.A_ACCESS_HOURS).getValue());
        service.setManned(parseYesNo(sourceMappers.get(EthernetImportColumns.A_MANNED).getValue()));
        service.setLoaRequired(parseYesNo(sourceMappers.get(EthernetImportColumns.A_LOA_REQUIRED).getValue()));
        service.setMediaType(lookupValueMap.get(sourceMappers.get(EthernetImportColumns.A_HANDOFF_MEDIA_TYPE).getValue()));
        service.setHandoffFiberMode(lookupValueMap.get(sourceMappers.get(EthernetImportColumns.A_HANDOFF_FIBER_MODE).getValue()));
        service.setHandoffConnectorType(sourceMappers.get(EthernetImportColumns.A_HANDOFF_CONNECTOR_TYPE).getValue());
        service.setClliCode(sourceMappers.get(EthernetImportColumns.A_CLLI_CODE).getValue());
        service.setPopClli(sourceMappers.get(EthernetImportColumns.A_POP_CLLI).getValue());
        service.setAlternatePopClli(sourceMappers.get(EthernetImportColumns.A_ALTERNATE_POP_CLLI).getValue());
        service.setFloor(sourceMappers.get(EthernetImportColumns.A_FLOOR).getValue());
        service.setNpaNxx(sourceMappers.get(EthernetImportColumns.A_NPA_NXX).getValue());
        service.setDmarc(sourceMappers.get(EthernetImportColumns.A_DMARC).getValue());
        service.setCfa(sourceMappers.get(EthernetImportColumns.A_CFA).getValue());
        service.setzAddress1(sourceMappers.get(EthernetImportColumns.Z_ADDRESS_1).getValue());
        service.setzAddress2(sourceMappers.get(EthernetImportColumns.Z_ADDRESS_2).getValue());
        service.setzCity(sourceMappers.get(EthernetImportColumns.Z_CITY).getValue());
        service.setzState(sourceMappers.get(EthernetImportColumns.Z_STATE_PROVINCE_REGION).getValue());
        service.setzPostalCode(sourceMappers.get(EthernetImportColumns.Z_ZIP_POSTAL_CODE).getValue());
        service.setzCountry(sourceMappers.get(EthernetImportColumns.Z_COUNTRY).getValue());
        service.setMrc(new BigDecimal(sourceMappers.get(EthernetImportColumns.Z_END_MRC).getValue()));
        service.setNrc(new BigDecimal(sourceMappers.get(EthernetImportColumns.Z_END_NRC).getValue()));
        service.setzProviderCircuitId(sourceMappers.get(EthernetImportColumns.Z_PROVIDER_CIRCUIT_ID).getValue());
        service.setzAccessType(sourceMappers.get(EthernetImportColumns.Z_ACCESS_TYPE).getValue());
        service.setzInterfaceConnector(sourceMappers.get(EthernetImportColumns.Z_INTERFACE_CONNECTOR).getValue());
        service.setzAccessHours(sourceMappers.get(EthernetImportColumns.Z_ACCESS_HOURS).getValue());
        service.setzManned(parseYesNo(sourceMappers.get(EthernetImportColumns.Z_MANNED).getValue()));
        service.setzLoaRequired(parseYesNo(sourceMappers.get(EthernetImportColumns.Z_LOA_REQUIRED).getValue()));
        service.setzHandoffMediaType(lookupValueMap.get(sourceMappers.get(EthernetImportColumns.Z_HANDOFF_MEDIA_TYPE).getValue()));
        service.setzHandoffFiberMode(lookupValueMap.get(sourceMappers.get(EthernetImportColumns.Z_HANDOFF_FIBER_MODE).getValue()));
        service.setzHandoffConnectorType(sourceMappers.get(EthernetImportColumns.Z_HANDOFF_CONNECTOR_TYPE).getValue());
        service.setzClliCode(sourceMappers.get(EthernetImportColumns.Z_CLLI_CODE).getValue());
        service.setzPopClli(sourceMappers.get(EthernetImportColumns.Z_POP_CLLI).getValue());
        service.setzAlternatePopClli(sourceMappers.get(EthernetImportColumns.Z_ALTERNATE_POP_CLLI).getValue());
        service.setzFloor(sourceMappers.get(EthernetImportColumns.Z_FLOOR).getValue());
        service.setzNpaNxx(sourceMappers.get(EthernetImportColumns.Z_NPA_NXX).getValue());
        service.setzDmarc(sourceMappers.get(EthernetImportColumns.Z_DMARC).getValue());
        service.setzCfa(sourceMappers.get(EthernetImportColumns.Z_CFA).getValue());
        if ("MTM".equals(service.getContractTerm()) || service.getCircuitTermEndDate() == null) {
            service.setIgnoreForRenewals(true);
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(EthernetImportColumns.ORIGINAL_MRC).getNonValidatedValue())) {
            service.setOriginalMrc(new BigDecimal(sourceMappers.get(EthernetImportColumns.ORIGINAL_MRC).getValue()));
        }
        handleCommonService(service, sourceMappers);
        service = ethernetServiceManager.create(service);

        if (!Strings.isNullOrEmpty(sourceMappers.get(EthernetImportColumns.CIRCUIT_INSTALLED_DATE).getNonValidatedValue())) {
            sMilestoneInstanceManager.create(service.getId(), "DATA_PROVISIONING_COMPLETE", parseDate(sourceMappers.get(EthernetImportColumns.CIRCUIT_INSTALLED_DATE).getValue()));
        }
        if (!sMilestoneInstanceManager.doesMilestoneExist(service.getId(), "COMPLETE")) {
            sMilestoneInstanceManager.create(service.getId(), "COMPLETE", inventoryAddedDate);
        }
        if (!Strings.isNullOrEmpty(sourceMappers.get(EthernetImportColumns.NOTES_TO_IMPORT).getNonValidatedValue())) {
            serviceNoteManager.create(service.getId(), sourceMappers.get(EthernetImportColumns.NOTES_TO_IMPORT).getValue(), "Ethernet", importActivity.getFileAttachment().getUploadedByUserName());
        }
    }

    @Override
    protected List<String> validateRow(Map<String, ValidatingSourceMapper> sourceMappers, Long tenantId) {
        List<String> errors = super.validateRow(sourceMappers, tenantId);

        //z zip code
        errors = validateZipcodeCol(errors, sourceMappers.get(EthernetImportColumns.Z_ZIP_POSTAL_CODE));

        //validate dropdown values
        //timezone
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.TIME_ZONE), "NA_TIME_ZONE", tenantId);
        //Circuit Owner
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.SERVICE_BILLED_TO), "SERVICE_BILLED_TO", tenantId);
        //PROVIDER
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.PROVIDER), "PROVIDER", tenantId);
        //underlying provider
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.UNDERLYING_PROVIDER), "UNDERLYING_PROVIDER", tenantId);
        //Product Type
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.PRODUCT_TYPE), "ETHERNET_PRODUCT_TYPE", tenantId);
        //Contract Term
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.CONTRACT_TERM), "CONTRACT_TERM", tenantId);
        //Port Speed
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.PORT_SPEED), "SPEED", tenantId);
        //Access Speed
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.ACCESS_SPEED), "SPEED", tenantId);
        //EA Speed
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.EA_SPEED), "SPEED", tenantId);
        //Interface Connector
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.INTERFACE_CONNECTOR), "INTERFACE_CONNECTOR", tenantId);
        //MTU
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.MTU), "MTU", tenantId);
        //MUX
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.MUX), "MUX", tenantId);
        //Cable Category
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.CABLE_CATEGORY), "CABLE_CATEGORY", tenantId);
        //IP Format
        errors = validateIpFormatCol(errors, sourceMappers.get(EthernetImportColumns.IP_FORMAT));
        //A Int connector, z int connector
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.A_INTERFACE_CONNECTOR), "INTERFACE_CONNECTOR", tenantId);
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.Z_INTERFACE_CONNECTOR), "INTERFACE_CONNECTOR", tenantId);
        //A Manned, Z manned
        errors = validateYesNoCol(errors, sourceMappers.get(EthernetImportColumns.A_MANNED));
        errors = validateYesNoCol(errors, sourceMappers.get(EthernetImportColumns.Z_MANNED));
        //A LOA required, Z LOA required
        errors = validateYesNoCol(errors, sourceMappers.get(EthernetImportColumns.A_LOA_REQUIRED));
        errors = validateYesNoCol(errors, sourceMappers.get(EthernetImportColumns.Z_LOA_REQUIRED));
        //A Handoff media type, Z handoff media type
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.A_HANDOFF_MEDIA_TYPE), "MEDIA_TYPE", tenantId);
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.Z_HANDOFF_MEDIA_TYPE), "MEDIA_TYPE", tenantId);
        //A handoff fiber mode, Z handoff fiber mode
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.A_HANDOFF_FIBER_MODE), "HANDOFF_FIBER_MODE", tenantId);
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.Z_HANDOFF_FIBER_MODE), "HANDOFF_FIBER_MODE", tenantId);
        //A access type, Z access type
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.A_ACCESS_TYPE), "ACCESS_TYPE", tenantId);
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.Z_ACCESS_TYPE), "ACCESS_TYPE", tenantId);
        //project name
        errors = validateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.PROJECT_NAME), "PROJECT_NAME", tenantId);

        //verify the date columns can be parsed
        //Contract Signed Date
        errors = validateDateCol(errors, sourceMappers.get(EthernetImportColumns.CONTRACT_SIGNED_DATE));
        //Contract End Date
        errors = validateDateCol(errors, sourceMappers.get(EthernetImportColumns.CONTRACT_END_DATE));
        //Date Entered in Inventory
        errors = validateDateCol(errors, sourceMappers.get(EthernetImportColumns.DATE_ENTERED_IN_INVENTORY));
        //Circuit Installed Date
        errors = validateDateCol(errors, sourceMappers.get(EthernetImportColumns.CIRCUIT_INSTALLED_DATE));
        //tsp code expiration date
        errors = validateDateCol(errors, sourceMappers.get(EthernetImportColumns.TSP_CODE_EXPIRATION_DATE));

        //verify the decimal columns can be parsed
        // A MRC, A NRC, Z MRC, Z NRC
        errors = validateBigDecimalCol(errors, sourceMappers.get(EthernetImportColumns.A_END_MRC));
        errors = validateBigDecimalCol(errors, sourceMappers.get(EthernetImportColumns.A_END_NRC));
        errors = validateBigDecimalCol(errors, sourceMappers.get(EthernetImportColumns.Z_END_MRC));
        errors = validateBigDecimalCol(errors, sourceMappers.get(EthernetImportColumns.Z_END_NRC));
        //Annual Recurring Cost
        errors = validateBigDecimalCol(errors, sourceMappers.get(EthernetImportColumns.ANNUAL_RECURRING_COST));
        //Original MRC
        errors = validateBigDecimalCol(errors, sourceMappers.get(EthernetImportColumns.ORIGINAL_MRC));

        //verify long columns can be parsed
        //bill cycle
        errors = validateLongCol(errors, sourceMappers.get(EthernetImportColumns.BILL_CYCLE));
        try {
            String address1 = sourceMappers.get(EthernetImportColumns.ADDRESS_1).getNonValidatedValue();
            String city = sourceMappers.get(EthernetImportColumns.CITY).getNonValidatedValue();
            String state = sourceMappers.get(EthernetImportColumns.STATE_PROVINCE_REGION).getNonValidatedValue();
            String postalCode = sourceMappers.get(EthernetImportColumns.ZIP_POSTAL_CODE).getNonValidatedValue();
            String country = sourceMappers.get(EthernetImportColumns.COUNTRY).getNonValidatedValue();
            if (!Strings.isNullOrEmpty(postalCode)) {
                //zip code
                errors = validateZipcodeCol(errors, sourceMappers.get(EthernetImportColumns.ZIP_POSTAL_CODE));
            }
            if (!Strings.isNullOrEmpty(state)) {
                //state
                errors = validateStateLookupValueCol(errors, sourceMappers.get(EthernetImportColumns.STATE_PROVINCE_REGION), tenantId);
            }
            if (Strings.isNullOrEmpty(address1) || Strings.isNullOrEmpty(city) || Strings.isNullOrEmpty(state) || Strings.isNullOrEmpty(postalCode) || Strings.isNullOrEmpty(country)) {
                AddressView address = addressViewManager.findByClientLocationId(sourceMappers.get(AbstractInventoryServiceImportColumns.CLIENT_LOCATION_ID).getValue());
                if (address == null) {
                    errors.add("Address is required");
                }
            }

        } catch (Exception e) {
            errors.add("Could not parse Address");
        }

        return errors;
    }

    @Override
    protected List<String> getExpectedColumns() {
        return EthernetImportColumns.getExpectedColumns();
    }

    @Override
    protected String getTemplateName() {
        return "Ethernet";
    }

    @Override
    protected String getImportTypeName() {
        return "Ethernet Service";
    }

    @Override
    protected Map<String, ValidatingSourceMapper> buildSourceMappers(final ExcelAdapter adapter) {
        HashMap<String, ValidatingSourceMapper> sourceMappers = new HashMap<>();
        handleCommonSourceMappers(sourceMappers, adapter);
        sourceMappers.put(EthernetImportColumns.MASTER_CUSTOMER, new StringSourceMapper(adapter, EthernetImportColumns.MASTER_CUSTOMER, 100, true));
        sourceMappers.put(EthernetImportColumns.END_CUSTOMER, new StringSourceMapper(adapter, EthernetImportColumns.END_CUSTOMER, 100, true));
        sourceMappers.put(EthernetImportColumns.I90_PROJECT_MANAGER, new StringSourceMapper(adapter, EthernetImportColumns.I90_PROJECT_MANAGER, 100, false));
        sourceMappers.put(EthernetImportColumns.CLIENT_LOCATION_ID, new StringSourceMapper(adapter, EthernetImportColumns.CLIENT_LOCATION_ID, 100, true));
        sourceMappers.put(EthernetImportColumns.CLIENT_SERVICE_ID, new StringSourceMapper(adapter, EthernetImportColumns.CLIENT_SERVICE_ID, 100, false));
        sourceMappers.put(EthernetImportColumns.ADDRESS_1, new StringSourceMapper(adapter, EthernetImportColumns.ADDRESS_1, 100, false));
        sourceMappers.put(EthernetImportColumns.ADDRESS_2, new StringSourceMapper(adapter, EthernetImportColumns.ADDRESS_2, 100, false));
        sourceMappers.put(EthernetImportColumns.CITY, new StringSourceMapper(adapter, EthernetImportColumns.CITY, 100, false));
        sourceMappers.put(EthernetImportColumns.STATE_PROVINCE_REGION, new StringSourceMapper(adapter, EthernetImportColumns.STATE_PROVINCE_REGION, 100, false));
        sourceMappers.put(EthernetImportColumns.ZIP_POSTAL_CODE, new StringSourceMapper(adapter, EthernetImportColumns.ZIP_POSTAL_CODE, 20, false));
        sourceMappers.put(EthernetImportColumns.COUNTRY, new StringSourceMapper(adapter, EthernetImportColumns.COUNTRY, 100, false));
        sourceMappers.put(EthernetImportColumns.TIME_ZONE, new StringSourceMapper(adapter, EthernetImportColumns.TIME_ZONE, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_ADDRESS_1, new StringSourceMapper(adapter, EthernetImportColumns.Z_ADDRESS_1, 100, true));
        sourceMappers.put(EthernetImportColumns.Z_ADDRESS_2, new StringSourceMapper(adapter, EthernetImportColumns.Z_ADDRESS_2, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_CITY, new StringSourceMapper(adapter, EthernetImportColumns.Z_CITY, 100, true));
        sourceMappers.put(EthernetImportColumns.Z_STATE_PROVINCE_REGION, new StringSourceMapper(adapter, EthernetImportColumns.Z_STATE_PROVINCE_REGION, 100, true));
        sourceMappers.put(EthernetImportColumns.Z_ZIP_POSTAL_CODE, new StringSourceMapper(adapter, EthernetImportColumns.Z_ZIP_POSTAL_CODE, 20, true));
        sourceMappers.put(EthernetImportColumns.Z_COUNTRY, new StringSourceMapper(adapter, EthernetImportColumns.Z_COUNTRY, 100, true));
        sourceMappers.put(EthernetImportColumns.LOCATION_DESCRIPTION, new StringSourceMapper(adapter, EthernetImportColumns.LOCATION_DESCRIPTION, 500, false));
        sourceMappers.put(EthernetImportColumns.SERVICE_DESCRIPTION, new StringSourceMapper(adapter, EthernetImportColumns.SERVICE_DESCRIPTION, 500, false));
        sourceMappers.put(EthernetImportColumns.LCON_NAME, new StringSourceMapper(adapter, EthernetImportColumns.LCON_NAME, 100, false));
        sourceMappers.put(EthernetImportColumns.LCON_EMAIL, new StringSourceMapper(adapter, EthernetImportColumns.LCON_EMAIL, 100, false));
        sourceMappers.put(EthernetImportColumns.LCON_PHONE, new StringSourceMapper(adapter, EthernetImportColumns.LCON_PHONE, 100, false));
        sourceMappers.put(EthernetImportColumns.CLIENT_LOCATION_INFO, new StringSourceMapper(adapter, EthernetImportColumns.CLIENT_LOCATION_INFO, 100, false));
        sourceMappers.put(EthernetImportColumns.CLIENT_LOCATION_TYPE, new StringSourceMapper(adapter, EthernetImportColumns.CLIENT_LOCATION_TYPE, 100, false));
        sourceMappers.put(EthernetImportColumns.QUOTE_SOLUTION_ID, new StringSourceMapper(adapter, EthernetImportColumns.QUOTE_SOLUTION_ID, 100, false));
        sourceMappers.put(EthernetImportColumns.PROJECT_NAME, new StringSourceMapper(adapter, EthernetImportColumns.PROJECT_NAME, 100, false));
        sourceMappers.put(EthernetImportColumns.SERVICE_BILLED_TO, new StringSourceMapper(adapter, EthernetImportColumns.SERVICE_BILLED_TO, 100, true));
        sourceMappers.put(EthernetImportColumns.PROVIDER, new StringSourceMapper(adapter, EthernetImportColumns.PROVIDER, 100, true));
        sourceMappers.put(EthernetImportColumns.UNDERLYING_PROVIDER, new StringSourceMapper(adapter, EthernetImportColumns.UNDERLYING_PROVIDER, 100, false));
        sourceMappers.put(EthernetImportColumns.SUB_PRODUCT_TYPE, new StringSourceMapper(adapter, EthernetImportColumns.SUB_PRODUCT_TYPE, 100, false));
        sourceMappers.put(EthernetImportColumns.PO_NUMBER, new StringSourceMapper(adapter, EthernetImportColumns.PO_NUMBER, 100, false));
        sourceMappers.put(EthernetImportColumns.MANAGED_SERVICE, new StringSourceMapper(adapter, EthernetImportColumns.MANAGED_SERVICE, 100, false));
        sourceMappers.put(EthernetImportColumns.PRODUCTION_IMPACTING, new StringSourceMapper(adapter, EthernetImportColumns.PRODUCTION_IMPACTING, 100, false));
        sourceMappers.put(EthernetImportColumns.PRODUCT_TYPE, new StringSourceMapper(adapter, EthernetImportColumns.PRODUCT_TYPE, 100, true));
        sourceMappers.put(EthernetImportColumns.CONTRACT_TERM, new StringSourceMapper(adapter, EthernetImportColumns.CONTRACT_TERM, 100, false));
        sourceMappers.put(EthernetImportColumns.CONTRACT_SIGNED_DATE, new StringSourceMapper(adapter, EthernetImportColumns.CONTRACT_SIGNED_DATE, 100, false));
        sourceMappers.put(EthernetImportColumns.CONTRACT_END_DATE, new StringSourceMapper(adapter, EthernetImportColumns.CONTRACT_END_DATE, 100, false));
        sourceMappers.put(EthernetImportColumns.CONTRACT_INFO, new StringSourceMapper(adapter, EthernetImportColumns.CONTRACT_INFO, 500, false));
        sourceMappers.put(EthernetImportColumns.DATE_ENTERED_IN_INVENTORY, new StringSourceMapper(adapter, EthernetImportColumns.DATE_ENTERED_IN_INVENTORY, 100, false));
        sourceMappers.put(EthernetImportColumns.PROVIDER_ORDER_NUMBER, new StringSourceMapper(adapter, EthernetImportColumns.PROVIDER_ORDER_NUMBER, 100, false));
        sourceMappers.put(EthernetImportColumns.BILL_CYCLE, new StringSourceMapper(adapter, EthernetImportColumns.BILL_CYCLE, 2, false));
        sourceMappers.put(EthernetImportColumns.ACCOUNT_NUMBER_BAN, new StringSourceMapper(adapter, EthernetImportColumns.ACCOUNT_NUMBER_BAN, 100, false));
        sourceMappers.put(EthernetImportColumns.SUMMARY_BILL, new StringSourceMapper(adapter, EthernetImportColumns.SUMMARY_BILL, 100, false));
        sourceMappers.put(EthernetImportColumns.PORT_SPEED, new StringSourceMapper(adapter, EthernetImportColumns.PORT_SPEED, 100, true));
        sourceMappers.put(EthernetImportColumns.ACCESS_SPEED, new StringSourceMapper(adapter, EthernetImportColumns.ACCESS_SPEED, 100, true));
        sourceMappers.put(EthernetImportColumns.EA_SPEED, new StringSourceMapper(adapter, EthernetImportColumns.EA_SPEED, 100, false));
        sourceMappers.put(EthernetImportColumns.CIRCUIT_INSTALLED_DATE, new StringSourceMapper(adapter, EthernetImportColumns.CIRCUIT_INSTALLED_DATE, 100, false));
        sourceMappers.put(EthernetImportColumns.INTERFACE_CONNECTOR, new StringSourceMapper(adapter, EthernetImportColumns.INTERFACE_CONNECTOR, 100, false));
        sourceMappers.put(EthernetImportColumns.MTU, new StringSourceMapper(adapter, EthernetImportColumns.MTU, 100, false));
        sourceMappers.put(EthernetImportColumns.TRUNK_GROUP, new StringSourceMapper(adapter, EthernetImportColumns.TRUNK_GROUP, 100, false));
        sourceMappers.put(EthernetImportColumns.MUX, new StringSourceMapper(adapter, EthernetImportColumns.MUX, 100, false));
        sourceMappers.put(EthernetImportColumns.CE_VLAN_FOR_E_LINE, new StringSourceMapper(adapter, EthernetImportColumns.CE_VLAN_FOR_E_LINE, 100, false));
        sourceMappers.put(EthernetImportColumns.VLAN_TAGGING, new StringSourceMapper(adapter, EthernetImportColumns.VLAN_TAGGING, 100, false));
        sourceMappers.put(EthernetImportColumns.VLAN_ID, new StringSourceMapper(adapter, EthernetImportColumns.VLAN_ID, 100, false));
        sourceMappers.put(EthernetImportColumns.CABLE_CATEGORY, new StringSourceMapper(adapter, EthernetImportColumns.CABLE_CATEGORY, 100, false));
        sourceMappers.put(EthernetImportColumns.CABLE_SHIELDING, new StringSourceMapper(adapter, EthernetImportColumns.CABLE_SHIELDING, 100, false));
        sourceMappers.put(EthernetImportColumns.IP_FORMAT, new StringSourceMapper(adapter, EthernetImportColumns.IP_FORMAT, 100, false));
        sourceMappers.put(EthernetImportColumns.WAN_IPS, new StringSourceMapper(adapter, EthernetImportColumns.WAN_IPS, 100, false));
        sourceMappers.put(EthernetImportColumns.WAN_GATEWAY, new StringSourceMapper(adapter, EthernetImportColumns.WAN_GATEWAY, 100, false));
        sourceMappers.put(EthernetImportColumns.WAN_SUBNET, new StringSourceMapper(adapter, EthernetImportColumns.WAN_SUBNET, 100, false));
        sourceMappers.put(EthernetImportColumns.LAN_IPS, new StringSourceMapper(adapter, EthernetImportColumns.LAN_IPS, 100, false));
        sourceMappers.put(EthernetImportColumns.LAN_GATEWAY, new StringSourceMapper(adapter, EthernetImportColumns.LAN_GATEWAY, 100, false));
        sourceMappers.put(EthernetImportColumns.LAN_SUBNET, new StringSourceMapper(adapter, EthernetImportColumns.LAN_SUBNET, 100, false));
        sourceMappers.put(EthernetImportColumns.TSP_CODE, new StringSourceMapper(adapter, EthernetImportColumns.TSP_CODE, 100, false));
        sourceMappers.put(EthernetImportColumns.TSP_CODE_EXPIRATION_DATE, new StringSourceMapper(adapter, EthernetImportColumns.TSP_CODE_EXPIRATION_DATE, 100, false));
        sourceMappers.put(EthernetImportColumns.NOTES_TO_IMPORT, new StringSourceMapper(adapter, EthernetImportColumns.NOTES_TO_IMPORT, 2147483647, false));
        sourceMappers.put(EthernetImportColumns.A_END_MRC, new StringSourceMapper(adapter, EthernetImportColumns.A_END_MRC, 100, true));
        sourceMappers.put(EthernetImportColumns.A_END_NRC, new StringSourceMapper(adapter, EthernetImportColumns.A_END_NRC, 100, true));
        sourceMappers.put(EthernetImportColumns.ANNUAL_RECURRING_COST, new StringSourceMapper(adapter, EthernetImportColumns.ANNUAL_RECURRING_COST,100, true));
        sourceMappers.put(EthernetImportColumns.ORIGINAL_MRC, new StringSourceMapper(adapter, EthernetImportColumns.ORIGINAL_MRC, 100, false));
        sourceMappers.put(EthernetImportColumns.A_PROVIDER_CIRCUIT_ID, new StringSourceMapper(adapter, EthernetImportColumns.A_PROVIDER_CIRCUIT_ID, 100, false));
        sourceMappers.put(EthernetImportColumns.A_ACCESS_TYPE, new StringSourceMapper(adapter, EthernetImportColumns.A_ACCESS_TYPE, 100, false));
        sourceMappers.put(EthernetImportColumns.A_INTERFACE_CONNECTOR, new StringSourceMapper(adapter, EthernetImportColumns.A_INTERFACE_CONNECTOR, 100, false));
        sourceMappers.put(EthernetImportColumns.A_ACCESS_HOURS, new StringSourceMapper(adapter, EthernetImportColumns.A_ACCESS_HOURS, 100, false));
        sourceMappers.put(EthernetImportColumns.A_MANNED, new StringSourceMapper(adapter, EthernetImportColumns.A_MANNED, 100, false));
        sourceMappers.put(EthernetImportColumns.A_LOA_REQUIRED, new StringSourceMapper(adapter, EthernetImportColumns.A_LOA_REQUIRED, 100, false));
        sourceMappers.put(EthernetImportColumns.A_HANDOFF_MEDIA_TYPE, new StringSourceMapper(adapter, EthernetImportColumns.A_HANDOFF_MEDIA_TYPE, 100, false));
        sourceMappers.put(EthernetImportColumns.A_HANDOFF_FIBER_MODE, new StringSourceMapper(adapter, EthernetImportColumns.A_HANDOFF_FIBER_MODE, 100, false));
        sourceMappers.put(EthernetImportColumns.A_HANDOFF_CONNECTOR_TYPE, new StringSourceMapper(adapter, EthernetImportColumns.A_HANDOFF_CONNECTOR_TYPE, 100, false));
        sourceMappers.put(EthernetImportColumns.A_CLLI_CODE, new StringSourceMapper(adapter, EthernetImportColumns.A_CLLI_CODE, 100, false));
        sourceMappers.put(EthernetImportColumns.A_POP_CLLI, new StringSourceMapper(adapter, EthernetImportColumns.A_POP_CLLI, 100, false));
        sourceMappers.put(EthernetImportColumns.A_ALTERNATE_POP_CLLI, new StringSourceMapper(adapter, EthernetImportColumns.A_ALTERNATE_POP_CLLI, 100, false));
        sourceMappers.put(EthernetImportColumns.A_FLOOR, new StringSourceMapper(adapter, EthernetImportColumns.A_FLOOR, 100, false));
        sourceMappers.put(EthernetImportColumns.A_NPA_NXX, new StringSourceMapper(adapter, EthernetImportColumns.A_NPA_NXX, 100, false));
        sourceMappers.put(EthernetImportColumns.A_DMARC, new StringSourceMapper(adapter, EthernetImportColumns.A_DMARC, 100, false));
        sourceMappers.put(EthernetImportColumns.A_CFA, new StringSourceMapper(adapter, EthernetImportColumns.A_CFA, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_END_MRC, new StringSourceMapper(adapter, EthernetImportColumns.Z_END_MRC, 100, true));
        sourceMappers.put(EthernetImportColumns.Z_END_NRC, new StringSourceMapper(adapter, EthernetImportColumns.Z_END_NRC, 100, true));
        sourceMappers.put(EthernetImportColumns.Z_PROVIDER_CIRCUIT_ID, new StringSourceMapper(adapter, EthernetImportColumns.Z_PROVIDER_CIRCUIT_ID, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_ACCESS_TYPE, new StringSourceMapper(adapter, EthernetImportColumns.Z_ACCESS_TYPE, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_INTERFACE_CONNECTOR, new StringSourceMapper(adapter, EthernetImportColumns.Z_INTERFACE_CONNECTOR, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_ACCESS_HOURS, new StringSourceMapper(adapter, EthernetImportColumns.Z_ACCESS_HOURS, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_MANNED, new StringSourceMapper(adapter, EthernetImportColumns.Z_MANNED, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_LOA_REQUIRED, new StringSourceMapper(adapter, EthernetImportColumns.Z_LOA_REQUIRED, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_HANDOFF_MEDIA_TYPE, new StringSourceMapper(adapter, EthernetImportColumns.Z_HANDOFF_MEDIA_TYPE, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_HANDOFF_FIBER_MODE, new StringSourceMapper(adapter, EthernetImportColumns.Z_HANDOFF_FIBER_MODE, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_HANDOFF_CONNECTOR_TYPE, new StringSourceMapper(adapter, EthernetImportColumns.Z_HANDOFF_CONNECTOR_TYPE, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_CLLI_CODE, new StringSourceMapper(adapter, EthernetImportColumns.Z_CLLI_CODE, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_POP_CLLI, new StringSourceMapper(adapter, EthernetImportColumns.Z_POP_CLLI, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_ALTERNATE_POP_CLLI, new StringSourceMapper(adapter, EthernetImportColumns.Z_ALTERNATE_POP_CLLI, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_FLOOR, new StringSourceMapper(adapter, EthernetImportColumns.Z_FLOOR, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_NPA_NXX, new StringSourceMapper(adapter, EthernetImportColumns.Z_NPA_NXX, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_DMARC, new StringSourceMapper(adapter, EthernetImportColumns.Z_DMARC, 100, false));
        sourceMappers.put(EthernetImportColumns.Z_CFA, new StringSourceMapper(adapter, EthernetImportColumns.Z_CFA, 100, false));
        return sourceMappers;
    }

}
