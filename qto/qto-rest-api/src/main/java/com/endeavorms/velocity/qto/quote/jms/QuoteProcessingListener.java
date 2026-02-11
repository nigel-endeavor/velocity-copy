package com.endeavorms.velocity.qto.quote.jms;

import com.google.common.base.Strings;
import com.endeavorms.velocity.qto.RecordSource;
import com.endeavorms.velocity.qto.common.SchedulerSecurityContext;
import com.endeavorms.velocity.qto.common.TenantViewManager;
import com.endeavorms.velocity.qto.common.lookup.LookupType;
import com.endeavorms.velocity.qto.common.lookup.LookupTypeManager;
import com.endeavorms.velocity.qto.common.lookup.LookupValue;
import com.endeavorms.velocity.qto.common.lookup.LookupValueManager;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.contact.ContactType;
import com.endeavorms.velocity.qto.contact.order.OrderContact;
import com.endeavorms.velocity.qto.contact.order.OrderContactManager;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.note.ServiceNote;
import com.endeavorms.velocity.qto.note.ServiceNoteManager;
import com.endeavorms.velocity.qto.order.Order;
import com.endeavorms.velocity.qto.order.OrderManager;
import com.endeavorms.velocity.qto.quote.Quote;
import com.endeavorms.velocity.qto.quote.QuoteManager;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceType;
import com.endeavorms.velocity.qto.service._4g5g.GService;
import com.endeavorms.velocity.qto.service._4g5g.GServiceManager;
import com.endeavorms.velocity.qto.service.broadband.BroadbandService;
import com.endeavorms.velocity.qto.service.broadband.BroadbandServiceManager;
import com.endeavorms.velocity.qto.service.dia.DiaService;
import com.endeavorms.velocity.qto.service.dia.DiaServiceManager;
import com.endeavorms.velocity.qto.service.ucaas.UcaasService;
import com.endeavorms.velocity.qto.service.ucaas.UcaasServiceManager;
import com.endeavorms.velocity.qto.solution.Solution;
import com.endeavorms.velocity.qto.solution.SolutionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuoteProcessingListener {

    /** Private Logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteProcessingListener.class);

    /** QuoteProcessingQueue. */
    public static final String QUOTE_PROCESSING_QUEUE = "qto.QuoteProcessingQueue";

    /** Manager for orders. */
    @Autowired
    private OrderManager orderManager;
    /** Manager for location notes. */
    @Autowired
    private ServiceNoteManager serviceNoteManager;
    /** Manager for companies. */
    @Autowired
    private CompanyManager companyManager;
    /** Manager for contacts. */
    @Autowired
    private OrderContactManager orderContactManager;
    /** Manager for a quote. */
    @Autowired
    private QuoteManager quoteManager;
    /** Manager for a solution. */
    @Autowired
    private SolutionManager solutionManager;
    /** DiaService Manager. */
    @Autowired
    private DiaServiceManager diaServiceManager;
    /** BroadbandService Manager. */
    @Autowired
    private BroadbandServiceManager broadbandServiceManager;
    /** UcaasService Manager. */
    @Autowired
    private UcaasServiceManager ucaasServiceManager;
    /** GService Manager. */
    @Autowired
    private GServiceManager gServiceManager;

    /** Lookup value manager. */
    @Autowired
    private LookupValueManager lookupValueManager;

    @Autowired
    private TenantViewManager tenantViewManager;

    /** Lookup type manager. */
    @Autowired
    private LookupTypeManager lookupTypeManager;

    @Autowired
    private LocationManager locationManager;

    @JmsListener(destination = QUOTE_PROCESSING_QUEUE)
    public void onMessage(final Message message) {
        String jmsMessageId = null;
        try {
            jmsMessageId = message.getJMSMessageID();
            LOGGER.info("Got message JMSMessageId = {}", jmsMessageId);
            Long quoteId = message.getLongProperty("quoteId");
            List<Long> solutionIds = (List<Long>) ((ObjectMessage) message).getObject();

            SchedulerSecurityContext.runAsScheduler(() -> {
                Quote quote = quoteManager.retrieve(quoteId);
                Order order = orderManager.getByQuoteId(quote.getVendorQuoteId());

                boolean isNewOrder = order == null;
                OrderContact contact = null;
                if (isNewOrder) {
                    Company company = getCompany(quote);
                    order = populateOrder(quote, company);
                    contact = getContact(company, quote, order);
                }

                populateLocationsAndPersistOrderLocations(quote, order, contact);
                populateAndPersistServices(solutionIds, order);

                if (isNewOrder) {
                    quote.setHandledTime(new Date());
                    quoteManager.edit(quote);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            LOGGER.info("Done with message {}", jmsMessageId);
        }
    }

    /**
     * Given a quote and order (persisted or not), populates and persists all new locations for that order. As part of
     * this process the order is persisted/updated.
     *
     * @param quote   the quote defining the locations.
     * @param order   the order that the locations will be related to.
     * @param contact
     * @return Order.
     */
    private Order populateLocationsAndPersistOrderLocations(final Quote quote, final Order order,
                                                            final OrderContact contact) {
        Order persisted;
        if (order.getId() == null) {
            persisted = orderManager.create(order);
        } else {
            persisted = orderManager.edit(order);
        }
        if (contact != null) {
            contact.setOrderId(persisted.getId());
            orderContactManager.create(contact);
        }
        //location and service
        List<Location> locations = order.getLocations();
        LOGGER.info(locations.size() + " Locations found");
        for (Solution solution : quote.getSolutions()) {
            //a location can be reused throughout a quote. We reuse those created where applicable.
            Location location = locations.stream()
                    .filter(l -> l.getQuoteLocationId().equals(solution.getLocationId()))
                    .findFirst().orElse(null);
            if (location == null) {
                //location
                location = populateLocation(solution, persisted);
                locations.add(locationManager.create(location));
            }
        }
        return persisted;
    }

    /**
     * Given a quote and an existing order, populates and persists for that order from the solutions on the quote.
     * @param solutionIds the solutionIds that services will need to be created for.
     * @param order the order that contains the locations that the services will be related to.
     */
    private void populateAndPersistServices(final List<Long> solutionIds, final Order order) {
        for (Long solutionId : solutionIds) {
            Solution solution = solutionManager.retrieve(solutionId);
            //service
            Location location = order.getLocations().stream()
                    .filter(l -> l.getQuoteLocationId().equals(solution.getLocationId()))
                    .findFirst().orElse(null);
            Service service = populateAndPersistService(solution.getQuote(), solution, order, location);

            //operations after service creation
            if (service != null) {
                //service note
                ServiceNote note = getServiceNote(solution, service);
                serviceNoteManager.create(note);
            }
            solution.setHandledTime(new Date());
            solutionManager.edit(solution);
        }
    }

    /**
     * Populates an order based on a quote.
     * @param quote the quote to populate the order from.
     * @param company a company the order should be associated with.
     * @return the populated order.
     */
    private static Order populateOrder(final Quote quote, final Company company) {
        Order order = new Order();
        order.setQuoteId(quote.getVendorQuoteId());
        order.setCompany(company);
        order.setQuoteNumber(quote.getQuoteNumber());
        order.setStatus("Order Received");
        return order;
    }

    /**
     * Populates a location based on a solution from a quote and associates it with the given order and address.
     * @param solution the solution from which to populate the location.
     * @param order the order to be associated with the location.
     * @return the populated location.
     */
    private static Location populateLocation(final Solution solution, final Order order) {

        Location location;
        location = new Location();
        location.setRecordSource(RecordSource.CONNECTBASE.getName());
        location.setAddress1(solution.getAddress());
        location.setCity(solution.getCity());
        location.setState(solution.getState());
        location.setPostalCode(solution.getZip());
        location.setCountry(solution.getCountryCode());
        location.setName(solution.getSiteName());
        location.setQuoteLocationId(solution.getLocationId());
        String clientLocationId = Strings.isNullOrEmpty(solution.getCustomerLocationId())
                ? solution.getLocationId() : solution.getCustomerLocationId();
        location.setClientLocationId(clientLocationId);
        location.setStatus("Pending Assignment");
        location.setClientLocationType(solution.getFlexField1());
        location.setClientLocationInfo(solution.getFlexField2());
        location.setOrderId(order.getId());
        location.setTenantId(order.getTenantId());
        location.setIcb(new BigDecimal(0));
        location.setMrc(new BigDecimal(0));
        location.setNrc(new BigDecimal(0));
        location.setOsp(new BigDecimal(0));
        return location;
    }

    /**
     * Creates a service note from a given solution from a quote.
     *
     * @param solution the solution to use to create the note.
     * @param service  the service to associate the note with.
     * @return the populated note.
     */
    private static ServiceNote getServiceNote(final Solution solution,
                                              final Service service) {
        ServiceNote note = new ServiceNote();
        note.setNote(String.format("Note: %1$s, Status: %2$s", solution.getNote(), solution.getStatus()));
        note.setCategory(service.getType());
        note.setCreatedBy(solution.getQuote().getQuoteProvider());
        note.setCreatedDate(new Date());
        note.setInternalOnly(false);
        note.setServiceId(service.getId());
        return note;
    }

    /**
     * Populates a service.
     * @param quote the quote to use to populate the service.
     * @param solution the solution to use to populate the service.
     * @param order the order the service will be associated with.
     * @param location the location the service will be associated with.
     */
    private Service populateAndPersistService(final Quote quote, final Solution solution,
                                              final Order order, final Location location) {
        //service
        Service service = null;
        ServiceType serviceType = ServiceType.fromServiceName(solution.getProduct());
        switch (serviceType) {
            case DIA:
                service = new DiaService();
                ((DiaService) service).setLastMileProvider(solution.getLastmileSupplier());
                break;
            case BROADBAND:
                service = new BroadbandService();
                break;
            case UCAAS:
                service = new UcaasService();
                break;
            case G:
                service = new GService();
                break;
            default:
                //an exception will have already been thrown at this point.
                break;
        }
        service.setBuildingStatus(solution.getBldgStatus());
        service.setQuoteSolutionId(solution.getVendorSolutionId());
        //if contract term is not null and not MTM, append " Month" to the end of the term to get the matching lookup value
        String contractTermValue = !Strings.isNullOrEmpty(solution.getTerm()) && !solution.getTerm().equals("MTM")
                ? solution.getTerm() + " Month"
                : solution.getTerm();
        service.setContractTerm(getLookupValue("CONTRACT_TERM", contractTermValue, quote.getTenantId()));
        service.setProvider(getLookupValue("PROVIDER", solution.getProvider(), solution.getTenantId()));
        service.setMrc(solution.getMrc());
        service.setNrc(solution.getNrc());

        String clientServiceId = Strings.isNullOrEmpty(solution.getSecondaryNumber())
                ? solution.getVendorSolutionId() : solution.getSecondaryNumber();
        service.setClientServiceId(clientServiceId);
        String uploadSpeed = solution.getUploadSpeed() > 1000 ? solution.getUploadSpeed() / 1000 + "G" : solution.getUploadSpeed() + "M";
        service.setUploadSpeed(getLookupValue("SPEED", uploadSpeed, quote.getTenantId()));
        String downloadSpeed = solution.getDownloadSpeed() > 1000 ? solution.getDownloadSpeed() / 1000 + "G" : solution.getDownloadSpeed() + "M";
        service.setDownloadSpeed(getLookupValue("SPEED", downloadSpeed, quote.getTenantId()));
        service.setSpeed(service.getDownloadSpeed() + " / " + service.getUploadSpeed());
        service.setMediaType(solution.getMediaType());
        service.setNetStatus(solution.getNetStatus());
        service.setProductInstallInterval(
                solution.getInstallInterval() != null ? solution.getInstallInterval().toString(): null);
        service.setLocationId(location.getId());
        service.setCurrency(solution.getQuoteCurrency());
        service.setOrderId(order.getId());
        service.setTenantId(quote.getTenantId());
        service.setRecordSource(RecordSource.CONNECTBASE.getName());

        switch (serviceType) {
            case DIA:
                service = diaServiceManager.create((DiaService) service);
                break;
            case BROADBAND:
                service = broadbandServiceManager.create((BroadbandService) service);
                break;
            case UCAAS:
                service = ucaasServiceManager.create((UcaasService) service);
                break;
            case G:
                service = gServiceManager.create((GService) service);
                break;
            default:
                break;
        }
        return service;
    }

    private String getLookupValue(String lookupTypeCode, String value, Long tenantId) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        LookupValue lookupValue = lookupValueManager.retrieveByTypeAndValue(lookupTypeCode, value);
        //if the lookup value doesn't exist, create it
        if (lookupValue == null) {
            LookupType lookupType = lookupTypeManager.retrieveByTypeCode(lookupTypeCode);
            LookupValue newValue = new LookupValue();
            newValue.setLookupType(lookupType);
            newValue.setValue(value);
            newValue.setDisplay(value);
            newValue.setTenantId(tenantId);
            newValue.setActive(true);
            lookupValue = lookupValueManager.create(newValue);
            if (lookupType.getSortStrategy() == 1) {
                //custom logic for setting the sortSequence on SPEED lookup values
                if ("SPEED".equals(lookupTypeCode)) {
                    //get all SPEEDs for this tenant
                    List<LookupValue> speeds = lookupValueManager.findByTypeCodeAndTenantId(lookupTypeCode, tenantId);
                    //filter by suffix (M or G)
                    String speedSuffix = value.substring(value.length() - 1);
                    speeds = speeds.stream().filter(speed -> speed.getDisplay().endsWith(speedSuffix)).collect(Collectors.toList());
                    //create a new TenantLookupValue for the new speed
                    LookupValue newSpeed = new LookupValue();
                    newSpeed.setTenantId(tenantId);
                    newSpeed.setValue(value);
                    newSpeed.setLookupType(lookupType);
                    newSpeed.setDisplay(value);
                    speeds.add(newSpeed);
                    //sort the speeds by the numeric value
                    speeds.sort((speed1, speed2) -> {
                        String speed1Display = speed1.getDisplay();
                        String speed2Display = speed2.getDisplay();
                        String speed1Value = speed1Display.substring(0, speed1Display.length() - 1);
                        String speed2Value = speed2Display.substring(0, speed2Display.length() - 1);
                        return (int) (Double.parseDouble(speed1Value) - Double.parseDouble(speed2Value));
                    });
                    //set the sort sequence for each speed
                    int sortSequence = speeds.get(0).getSortSequence();
                    for (LookupValue speed : speeds) {
                        speed.setSortSequence(sortSequence++);
                        lookupValueManager.edit(speed);
                    }
                }
            }
        } else {
            //if the lookup value isn't active, activate it
            if (!lookupValue.isActive()) {
                lookupValue.setActive(true);
                lookupValue = lookupValueManager.edit(lookupValue);
            }
        }

        return lookupValue.getDisplay();
    }

    /**
     * If a contact exists, retrieve it, otherwise, create based on incoming quote.
     *
     * @param company the company to filter by or create and associate with.
     * @param quote the incoming quote with contact details.
     * @param order the order to associate the contact with.
     * @return a contact.
     */
    private OrderContact getContact(final Company company, final Quote quote,
                                    final Order order) {
        List<OrderContact> contacts
                = orderContactManager.getByCompanyAndEmail(company.getId(), quote.getUserEmail());
        OrderContact contact;
        //if no existing contact for that company with that email address.
        if (contacts.isEmpty()) {
            OrderContact newContact = new OrderContact();
            newContact.setOrderId(order.getId());
            //name
            newContact.setFirstName(getFirstName(quote.getUserName()));
            newContact.setLastName(getLastName(quote.getUserName()));
            newContact.setType(ContactType.QUOTE);
            //company (will relate to a tenant)
            newContact.setCompanyId(company.getId());
            newContact.setEmail(quote.getUserEmail());
            contact = newContact;
        } else {
            //grab the first matching contact returned
            contact = contacts.get(0);
            if (contacts.size() > 1) {
                LOGGER.warn("Multiple companies returned by the name {}.", quote.getAccountName());
            }
        }
        return contact;
    }

    /**
     * Takes a full name and tries to extract the first name. If there is a middle name, it'll be included.
     * @param fullName the full name string.
     * @return best guess at the first name.
     */
    private static String getFirstName(String fullName) {
        int index = fullName.lastIndexOf(" ");
        if (index > -1) {
            return fullName.substring(0, index);
        }
        return fullName;
    }

    /**
     * Takes a full name and tries to extract the second name. Does not support suffixes.
     * @param fullName the full name string.
     * @return best guess at the last name.
     */
    private static String getLastName(String fullName) {
        int index = fullName.lastIndexOf(" ");
        if (index > -1) {
            return fullName.substring(index + 1);
        }
        return "";
    }

    /**
     * Gets an existing company, or creates a new one, by the supplied quote information.
     * @param quote the quote.
     * @return a Company.
     */
    private Company getCompany(final Quote quote) {
        Company company = companyManager.findEndCustomerByNameAndTenantId(quote.getAccountName(), quote.getTenantId());
        //if there are no matching companies
        if (company == null) {
            Company newCompany = new Company();
            newCompany.setName(quote.getAccountName());
            newCompany.setClientId(quote.getAccountId().toString());
            newCompany.setTenantId(quote.getTenantId());
            newCompany.setType("End Customer");
            company = companyManager.create(newCompany);
        }
        return company;
    }
}
