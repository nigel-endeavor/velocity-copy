package com.vertek.corporate.qto.fileimport;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.RecordSource;
import com.vertek.corporate.qto.address.AddressView;
import com.vertek.corporate.qto.address.AddressViewManager;
import com.vertek.corporate.qto.common.adapter.ExcelAdapter;
import com.vertek.corporate.qto.common.mapping.StringSourceMapper;
import com.vertek.corporate.qto.common.mapping.ValidatingSourceMapper;
import com.vertek.corporate.qto.company.Company;
import com.vertek.corporate.qto.company.CompanyManager;
import com.vertek.corporate.qto.config.CompanyConfigKey;
import com.vertek.corporate.qto.config.CompanyConfigPropertyManager;
import com.vertek.corporate.qto.contact.ContactType;
import com.vertek.corporate.qto.contact.location.LocationContact;
import com.vertek.corporate.qto.fileimport.importactivity.ImportActivity;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.location.TerminalLocationStatuses;
import com.vertek.corporate.qto.order.Order;
import com.vertek.corporate.qto.order.OrderManager;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractInventoryServiceImporter extends AbstractImporter {

    @Inject
    private CompanyManager companyManager;

    @Inject
    private OrderManager orderManager;

    @Inject
    private LocationManager locationManager;

    @Inject
    private SubjectManager subjectManager;

    @Inject
    private ServiceManager serviceManager;

    @Inject
    private AddressViewManager addressViewManager;

    // List of client service id's from the current spreadsheet
    private List<String> clientServiceIds;

    @Inject
    private CompanyConfigPropertyManager<CompanyConfigKey> companyConfigPropertyManager;

    /**
     * If the incoming end customer exists, it'll be returned, otherwise a new one will be created.
     * @param sourceMappers The source mappers.
     * @param tenantId The tenant ID.
     * @return The end customer.
     * @throws Exception If an error occurs.
     */
    protected Company handleEndCustomer(final Map<String, ValidatingSourceMapper> sourceMappers, final Long tenantId) throws Exception {
        Company masterCustomer = companyManager.findMasterCustomerByNameAndTenantId(sourceMappers.get(AbstractInventoryServiceImportColumns.MASTER_CUSTOMER).getValue(), tenantId);
        Company endCustomer = companyManager.findEndCustomerByNameAndTenantId(sourceMappers.get(AbstractInventoryServiceImportColumns.END_CUSTOMER).getValue(), tenantId);
        if (endCustomer == null) {
            endCustomer = new Company();
            endCustomer.setName(sourceMappers.get(AbstractInventoryServiceImportColumns.END_CUSTOMER).getValue());
            endCustomer.setType("End Customer");
            endCustomer.setActive(true);
            endCustomer.setParentCompany(masterCustomer);
            endCustomer.setTenantId(tenantId);
            endCustomer = companyManager.create(endCustomer);
        }
        return endCustomer;
    }

    /**
     * Handles the import of an order and location. Determined by client location ID, if these elements exist,
     * the location will be returned. If they do not, they will be created and the location will be returned.
     * @param sourceMappers The source mappers.
     * @param tenantId The tenant ID.
     * @param endCustomer The end customer.
     * @return The location.
     * @throws Exception If an error occurs.
     */
    protected Location handleOrderAndLocation(final Map<String, ValidatingSourceMapper> sourceMappers,
                                              final Long tenantId, final Company endCustomer,
                                              final String clientLocationInfoHeader,
                                              final String clientLocationTypeHeader,
                                              final String address1Header,
                                              final String address2Header,
                                              final String cityHeader,
                                              final String stateProvinceRegionHeader,
                                              final String zipPostalCodeHeader,
                                              final String countryHeader,
                                              final String timeZoneHeader) throws Exception {
        //Location Info
        String clientLocationId = sourceMappers.get(AbstractInventoryServiceImportColumns.CLIENT_LOCATION_ID).getValue();
        List<Location> locationList = locationManager.findByClientLocIdAndTenant(clientLocationId, tenantId);
        List<Location> openLocations = locationList.stream()
                .filter(l -> !TerminalLocationStatuses.getStatuses().contains(l.getStatus())
                        && !l.isCurrentInventory()
                        && l.getInventoryLocationId() == null).collect(Collectors.toList());
        Location provisioningLocation = !openLocations.isEmpty() ? openLocations.get(0) : null;
        List<Location> inventoryLocations = locationList.stream()
                .filter(Location::isCurrentInventory).collect(Collectors.toList());
        Location location;
        Order order;
        if (locationList.isEmpty()) {
            order = createOrder(endCustomer, clientLocationId, tenantId, sourceMappers);
            location = getLocation(order);
        } else {
            //get locations that are in inventory (should just be one)
            //if any, get that order and use it
            if (!inventoryLocations.isEmpty()) {
                order = orderManager.retrieve(inventoryLocations.get(0).getOrderId());
                location = locationManager.retrieve(inventoryLocations.get(0).getId());
            } else {
                //we need to make a new inventory order and inventory location to put our inventory service on
                order = createOrder(endCustomer, clientLocationId, tenantId, sourceMappers);
                location = getLocation(order);
                //associate the inventory order/location with the provisioning order/location
                if (provisioningLocation != null) {
                    order.setProvisioningOrderId(provisioningLocation.getOrderId());
                    Order provisioningOrder = orderManager.retrieve(provisioningLocation.getOrderId());
                    provisioningOrder.setInventoryOrderId(order.getId());
                    orderManager.editSuper(provisioningOrder);

                    location.setProvisioningLocationId(provisioningLocation.getId());
                    location.setParentLocationId(provisioningLocation.getId());
                }
            }

            if (!Strings.isNullOrEmpty(
                    sourceMappers.get(AbstractInventoryServiceImportColumns.I90_PROJECT_MANAGER).getValue())) {
                Subject subject = subjectManager.findByDisplayNameAndTenantId(
                        sourceMappers.get(AbstractInventoryServiceImportColumns.I90_PROJECT_MANAGER).getValue(),
                        tenantId);
                order.setVertekProjectManager(subject.getId());
            }
            order.setCompany(endCustomer);
            orderManager.edit(order);
        }

        //Location Address
        //if the location already exists, we don't want to overwrite the address if the incoming fields are empty
        //if the cell is empty, validation would have failed unless the inventory location already existed
        //so we can assume that the address is already populated, ignore the incoming value if blank
        String address1 = sourceMappers.get(address1Header).getValue();
        if (!Strings.isNullOrEmpty(address1)) {
            location.setAddress1(address1);
        }
        String address2 = sourceMappers.get(address2Header).getValue();
        if (!Strings.isNullOrEmpty(address2)) {
            location.setAddress2(address2);
        }
        String city = sourceMappers.get(cityHeader).getValue();
        if (!Strings.isNullOrEmpty(city)) {
            location.setCity(city);
        }
        String state = lookupValueMap.get(sourceMappers.get(stateProvinceRegionHeader).getValue());
        if (!Strings.isNullOrEmpty(state)) {
            location.setState(state);
        }
        String postalCode = sourceMappers.get(zipPostalCodeHeader).getValue();
        if (!Strings.isNullOrEmpty(postalCode)) {
            location.setPostalCode(postalCode);
        }
        String country = sourceMappers.get(countryHeader).getValue();
        if (!Strings.isNullOrEmpty(country)) {
            location.setCountry(country);
        }
        //Location Info
        location.setTenantId(tenantId);
        location.setActive(true);
        location.setClientLocationId(clientLocationId);
        location.setTimezone(lookupValueMap.get(sourceMappers.get(timeZoneHeader).getValue()));
        location.setClientLocationInfo(sourceMappers.get(clientLocationInfoHeader).getValue());
        location.setClientLocationType(sourceMappers.get(clientLocationTypeHeader).getValue());
        location.setRecordSource(RecordSource.INVENTORY_IMPORT.getName());
        location.setDescription(sourceMappers.get(
                AbstractInventoryServiceImportColumns.LOCATION_DESCRIPTION).getValue());
        if (Strings.isNullOrEmpty(location.getLevelOfEffort())) {
            location.setLevelOfEffort("Inventory Import");
        }

        //Location LCON
        if (location.getLcon() == null) {
            LocationContact lcon = new LocationContact();
            lcon.setLocationId(location.getId());
            lcon.setType(ContactType.LCON);
            lcon.setTenantId(tenantId);
            lcon.setCompanyId(endCustomer.getId());
            location.setLcon(lcon);
        }
        String[] name = sourceMappers.get(
                AbstractInventoryServiceImportColumns.LCON_NAME).getValue().split(" ", 2);
        location.getLcon().setFirstName(name[0]);
        if (name.length > 1) {
            location.getLcon().setLastName(name[1]);
        }
        location.getLcon().setEmail(sourceMappers.get(AbstractInventoryServiceImportColumns.LCON_EMAIL).getValue());
        location.getLcon().setPhone(sourceMappers.get(AbstractInventoryServiceImportColumns.LCON_PHONE).getValue());
        if (location.getId() == null) {
            location = locationManager.create(location);
            //update openlocation to have the current inventory id of the created location
            if (provisioningLocation != null) {
                provisioningLocation.setInventoryLocationId(location.getId());
                locationManager.editSuper(provisioningLocation);
            }
        } else {
            location = locationManager.editSuper(location);
        }
        return location;
    }

    private Location getLocation(final Order order) {
        Location location;
        location = new Location();
        location.setCurrentInventory(true);
        location.setOrderId(order.getId());
        return location;
    }

    private Order createOrder(final Company endCustomer, final String clientLocationId, final Long tenantId,
                              final Map<String, ValidatingSourceMapper> sourceMappers) throws Exception {
        Order order = new Order();
        Company masterCustomer = endCustomer.getParentCompany();
        if (masterCustomer.getProvisioner() != null) {
            order.setProvisioner(masterCustomer.getProvisioner());
        }
        if (masterCustomer.getI90ProjectManager() != null) {
            order.setVertekProjectManager(masterCustomer.getI90ProjectManager());
        }
        order.setCurrentInventory(true);
        order.setClientOrderId(clientLocationId);
        order.setCompany(endCustomer);
        if (!Strings.isNullOrEmpty(sourceMappers.get(AbstractInventoryServiceImportColumns.I90_PROJECT_MANAGER).getValue())) {
            Subject subject = subjectManager.findByDisplayNameAndTenantId(
                    sourceMappers.get(AbstractInventoryServiceImportColumns.I90_PROJECT_MANAGER).getValue(),
                    tenantId);
            order.setVertekProjectManager(subject.getId());
        }
        return orderManager.create(order);
    }

    protected Service handleCommonService(final Service service,
                                          final Map<String, ValidatingSourceMapper> sourceMappers)
            throws Exception {
        service.setAddress1(sourceMappers.get(AbstractInventoryServiceImportColumns.BILLING_ADDRESS_1).getValue());
        service.setAddress2(sourceMappers.get(AbstractInventoryServiceImportColumns.BILLING_ADDRESS_2).getValue());
        service.setCity(sourceMappers.get(AbstractInventoryServiceImportColumns.BILLING_CITY).getValue());
        service.setState(lookupValueMap.get(sourceMappers.get(AbstractInventoryServiceImportColumns.BILLING_STATE_PROVINCE_REGION).getValue()));
        service.setPostalCode(sourceMappers.get(AbstractInventoryServiceImportColumns.BILLING_ZIP_POSTAL_CODE).getValue());
        service.setCountry(sourceMappers.get(AbstractInventoryServiceImportColumns.BILLING_COUNTRY).getValue());
        service.setBillingEmail(sourceMappers.get(AbstractInventoryServiceImportColumns.BILLING_EMAIL).getValue());
        return service;
    }


    protected HashMap<String, ValidatingSourceMapper> handleCommonSourceMappers(
            final HashMap<String, ValidatingSourceMapper> sourceMappers, final ExcelAdapter adapter) {
        sourceMappers.put(AbstractInventoryServiceImportColumns.BILLING_ADDRESS_1,
                new StringSourceMapper(adapter, AbstractInventoryServiceImportColumns.BILLING_ADDRESS_1, 100, false));
        sourceMappers.put(AbstractInventoryServiceImportColumns.BILLING_ADDRESS_2,
                new StringSourceMapper(adapter, AbstractInventoryServiceImportColumns.BILLING_ADDRESS_2, 100, false));
        sourceMappers.put(AbstractInventoryServiceImportColumns.BILLING_CITY,
                new StringSourceMapper(adapter, AbstractInventoryServiceImportColumns.BILLING_CITY, 100, false));
        sourceMappers.put(AbstractInventoryServiceImportColumns.BILLING_STATE_PROVINCE_REGION,
                new StringSourceMapper(adapter, AbstractInventoryServiceImportColumns.BILLING_STATE_PROVINCE_REGION, 100, false));
        sourceMappers.put(AbstractInventoryServiceImportColumns.BILLING_ZIP_POSTAL_CODE,
                new StringSourceMapper(adapter, AbstractInventoryServiceImportColumns.BILLING_ZIP_POSTAL_CODE, 100, false));
        sourceMappers.put(AbstractInventoryServiceImportColumns.BILLING_COUNTRY,
                new StringSourceMapper(adapter, AbstractInventoryServiceImportColumns.BILLING_COUNTRY, 100, false));
        sourceMappers.put(AbstractInventoryServiceImportColumns.BILLING_EMAIL
                , new StringSourceMapper(adapter, AbstractInventoryServiceImportColumns.BILLING_EMAIL, 100, false));
        return sourceMappers;
    }

    @Override
    protected List<String> validateRow(final Map<String, ValidatingSourceMapper> sourceMappers, final Long tenantId) {
        List<String> errors = new ArrayList<>();

        //verify MC exists
        Company masterCustomer = null;
        try {
            if (!Strings.isNullOrEmpty(sourceMappers.get(AbstractInventoryServiceImportColumns.MASTER_CUSTOMER).getNonValidatedValue())) {
                masterCustomer = getCompanyManager().findMasterCustomerByNameAndTenantId(sourceMappers.get(AbstractInventoryServiceImportColumns.MASTER_CUSTOMER).getValue(), tenantId);
                if (masterCustomer == null) {
                    errors.add("Master Customer does not exist");
                }
            }
        } catch (Exception e) {
            errors.add("Could not parse Master Customer");
        }
        //verify, if EC exists, it's under the specified MC
        Company endCustomer = null;
        try {
            if (!Strings.isNullOrEmpty(sourceMappers.get(AbstractInventoryServiceImportColumns.END_CUSTOMER).getNonValidatedValue())) {
                endCustomer = getCompanyManager().findEndCustomerByNameAndTenantId(sourceMappers.get(AbstractInventoryServiceImportColumns.END_CUSTOMER).getValue(), tenantId);
                if (endCustomer != null && masterCustomer != null && !endCustomer.getMasterCustomerId().equals(masterCustomer.getId())) {
                    Company parentCompany = getCompanyManager().retrieve(endCustomer.getMasterCustomerId());
                    errors.add("End Customer already exists under Master Customer " + parentCompany.getName());
                }
            }
        } catch (Exception e) {
            errors.add("Could not parse Master Customer");
        }
        //verify, if i90 Project Manager is defined, it exists
        try {
            if (!Strings.isNullOrEmpty(sourceMappers.get(AbstractInventoryServiceImportColumns.I90_PROJECT_MANAGER).getNonValidatedValue())) {
                Subject subject = getSubjectManager().findByDisplayNameAndTenantId(sourceMappers.get(AbstractInventoryServiceImportColumns.I90_PROJECT_MANAGER).getValue(), tenantId);
                if (subject == null) {
                    errors.add("i90 Project Manager does not exist");
                }
            }
        } catch (Exception e) {
            errors.add("Could not parse i90 Project Manager");
        }

        //verify client service is unique
        try {
            Company tenantCompany = companyManager.findTenantByTenantId(tenantId);
            boolean autoCreateClientServiceId = companyConfigPropertyManager.getBoolean(tenantCompany.getId(), CompanyConfigKey.AUTO_CREATE_CLIENT_SERVICE_ID);
            if (!Strings.isNullOrEmpty(sourceMappers.get(AbstractInventoryServiceImportColumns.CLIENT_SERVICE_ID).getNonValidatedValue())) {
                String clientServiceId = sourceMappers.get(AbstractInventoryServiceImportColumns.CLIENT_SERVICE_ID).getValue();
                List<Service> serviceList = serviceManager.findByClientServiceIdAndTenant(clientServiceId, tenantId);
                if (!serviceList.isEmpty() || clientServiceIds.contains(clientServiceId)) {
                    errors.add("Client Service ID already exists");
                }
                clientServiceIds.add(clientServiceId);
            } else if (!autoCreateClientServiceId && Strings.isNullOrEmpty(sourceMappers.get(AbstractInventoryServiceImportColumns.CLIENT_SERVICE_ID).getNonValidatedValue())) {
                errors.add("Client Service ID is required");
            }
        } catch (Exception e) {
            errors.add("Could not parse Client Service ID");
        }

        //verify address is populated, or client location id maps to an existing address
        //Don't do this for ethernet
        try {
            boolean keyExists = false;
            for (String key : sourceMappers.keySet()) {
                if ("City (Z)".equals(key)) {
                    keyExists = true;
                    break;
                }
            }
            if (!keyExists) {
                String address1 = sourceMappers.get(AbstractInventoryServiceImportColumns.ADDRESS_1).getNonValidatedValue();
                String city = sourceMappers.get(AbstractInventoryServiceImportColumns.CITY).getNonValidatedValue();
                String state = sourceMappers.get(AbstractInventoryServiceImportColumns.STATE_PROVINCE_REGION).getNonValidatedValue();
                String postalCode = sourceMappers.get(AbstractInventoryServiceImportColumns.ZIP_POSTAL_CODE).getNonValidatedValue();
                String country = sourceMappers.get(AbstractInventoryServiceImportColumns.COUNTRY).getNonValidatedValue();
                if (!Strings.isNullOrEmpty(postalCode)) {
                    //zip code
                    errors = validateZipcodeCol(errors, sourceMappers.get(AbstractInventoryServiceImportColumns.ZIP_POSTAL_CODE));
                }
                if (!Strings.isNullOrEmpty(state)) {
                    //state
                    errors = validateStateLookupValueCol(errors, sourceMappers.get(AbstractInventoryServiceImportColumns.STATE_PROVINCE_REGION), tenantId);
                }
                if (Strings.isNullOrEmpty(address1) || Strings.isNullOrEmpty(city) || Strings.isNullOrEmpty(state) || Strings.isNullOrEmpty(postalCode) || Strings.isNullOrEmpty(country)) {
                    AddressView address = addressViewManager.findByClientLocationId(sourceMappers.get(AbstractInventoryServiceImportColumns.CLIENT_LOCATION_ID).getValue());
                    if (address == null) {
                        errors.add("Address is required");
                    }
                }
            }
        } catch (Exception e) {
            errors.add("Could not parse Address");
        }

        //verify if client location id already exists that it belongs to the same master customer/end customer
        try {
            String clientLocationId = sourceMappers.get(AbstractInventoryServiceImportColumns.CLIENT_LOCATION_ID).getNonValidatedValue();
            List<Location> locationList = locationManager.findByClientLocIdAndTenant(clientLocationId, tenantId);
            List<Location> inventoryLocations = locationList.stream()
                .filter(Location::isCurrentInventory).collect(Collectors.toList());
            if (!inventoryLocations.isEmpty()) {
                Company invMc = companyManager.retrieve(inventoryLocations.get(0).getMasterCustomerId());
                if (masterCustomer != null && invMc != null && !invMc.getId().equals(masterCustomer.getId())) {
                    errors.add("Client Location ID already exists under Master Customer " + invMc.getName());
                } else {
                    Order order = orderManager.retrieve(inventoryLocations.get(0).getOrderId());
                    if (endCustomer != null && order != null && !order.getCompany().getId().equals(endCustomer.getId())) {
                        errors.add("Client Location ID already exists under End Customer " + order.getCompany().getName());
                    }

                }
            }
        } catch (Exception e) {
            errors.add("Could not parse Client Location ID");
        }

        return errors;
    }

    @Override
    public ImportActivity importFile(final Long id) {
        clientServiceIds = new ArrayList<>();
        return super.importFile(id);
    }

    public SubjectManager getSubjectManager() {
        return subjectManager;
    }

    public CompanyManager getCompanyManager() {
        return companyManager;
    }

}
