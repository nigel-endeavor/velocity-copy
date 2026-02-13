package com.endeavorms.velocity.qto.invoicing.invoiceCharge;

import com.google.common.base.Strings;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.invoicing.billableMilestone.BillableMilestone;
import com.endeavorms.velocity.qto.invoicing.billableMilestone.BillableMilestoneManager;
import com.endeavorms.velocity.qto.invoicing.invoice.Invoice;
import com.endeavorms.velocity.qto.invoicing.invoice.InvoiceManager;
import com.endeavorms.velocity.qto.invoicing.levelOfEffort.LevelOfEffort;
import com.endeavorms.velocity.qto.invoicing.levelOfEffort.LevelOfEffortManager;
import com.endeavorms.velocity.qto.invoicing.surcharge.service.ServiceSurcharge;
import com.endeavorms.velocity.qto.invoicing.surcharge.service.ServiceSurchargeManager;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.milestone.LocationMilestoneInstance;
import com.endeavorms.velocity.qto.milestone.LocationMilestoneInstanceManager;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstance;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstanceManager;
import com.endeavorms.velocity.qto.notification.NotificationManager;
import com.endeavorms.velocity.qto.order.Order;
import com.endeavorms.velocity.qto.order.OrderManager;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author mwelicka
 * @since 7/30/2023
 */

@Component
public class InvoiceChargeManager extends StandardManager<InvoiceCharge> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceChargeManager.class);

    @Inject
    private TransactionTemplate transactionTemplate;

    @Inject
    private InvoiceChargeJpaDao dao;

    @Inject
    private InvoiceManager invoiceManager;

    @Inject
    private BillableMilestoneManager billableMilestoneManager;

    @Inject
    private ServiceMilestoneInstanceManager serviceMilestoneManager;

    @Inject
    private LocationMilestoneInstanceManager locationMilestoneManager;

    @Inject
    private ServiceManager serviceManager;

    @Inject
    private LocationManager locationManager;

    @Inject
    private LevelOfEffortManager loeManager;

    @Inject
    private OrderManager orderManager;

    @Inject
    private CompanyManager companyManager;

    @Inject
    private ServiceSurchargeManager surchargeManager;

    @Inject
    private NotificationManager notificationManager;

    @Inject
    private SubjectManager subjectManager;


    @Override
    protected InvoiceChargeJpaDao getDao() {
        return dao;
    }

    /**
     * Finds all invoice charges by the provided criteria.
     *
     * @param criteria The criteria to search by.
     * @return A paginated result of invoice charges.
     */
    public PaginatedResult<InvoiceCharge> findBySearchCriteria(final InvoiceChargeSearchCriteria criteria) {
        return dao.findBySearchCriteria(criteria);
    }

    /**
     * Generates the invoice charges for a given invoice.
     *
     * @param invoiceId
     * @param messageType
     */
    public void generateInvoiceCharges(final Long invoiceId, final Long subjectId, final String messageType) {
        Invoice invoice = invoiceManager.retrieve(invoiceId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(invoice.getInvoiceEnd());
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date enddate = cal.getTime();

        LOGGER.debug("Removing existing charges for invoice {}", invoiceId);
        transactionTemplate.executeWithoutResult(s -> dao.removeExistingCharges(invoiceId));

        transactionTemplate.executeWithoutResult(s -> {
            StringBuilder errMsg = new StringBuilder();
            List<BillableMilestone> billableMilestones = billableMilestoneManager.findByTenantId(invoice.getTenantId());
            for (BillableMilestone bm : billableMilestones) {
                String code = bm.getMilestone().getCode();
                String level = bm.getLevel();
                BigDecimal percentage = BigDecimal.valueOf(bm.getPercentage());
                LOGGER.debug("Generating charges for milestone {} at level {} with percentage {}", code, level, percentage);
                if ("Service".equalsIgnoreCase(level)) {
                    List<ServiceMilestoneInstance> milestones = serviceMilestoneManager.retrieveBillableMilestones(
                            invoice.getTenantId(), code, enddate);
                    for (ServiceMilestoneInstance milestone : milestones) {
                        errMsg.append(calculateServiceCharges(milestone, percentage, invoice, enddate));
                    }
                } else if ("Location".equalsIgnoreCase(level)) {
                    List<LocationMilestoneInstance> milestones = locationMilestoneManager.retrieveBillableMilestones(
                            invoice.getTenantId(), code, enddate);
                    for (LocationMilestoneInstance milestone : milestones) {
                        errMsg.append(calculateLocationCharges(milestone, percentage, invoice, enddate));
                    }
                }
            }
            calculateSurcharges(invoice, enddate);
            Subject subject = subjectManager.retrieve(subjectId);
            invoice.setGeneratedDate(new Date());
            invoice.setGeneratedBy(subject.getDisplayName());
            String statusMsg;
            if (Strings.isNullOrEmpty(messageType)) {
                invoice.setInvoiceStatus("Draft");
                statusMsg = "Generate Charges";
            } else {
                if (invoice.getInvoiceStatus().equalsIgnoreCase("Draft")) {
                    invoice.setInvoiceStatus("Final");
                    statusMsg = "Finalize and Generate Charges";
                } else {
                    invoice.setInvoiceStatus("Draft");
                    statusMsg = "Unfinalize and Generate Charges";
                }
            }
            invoice.setTotalCharges(dao.getTotalCharges(invoiceId));
            invoiceManager.edit(invoice);
            String header = "Invoice " + statusMsg + " Complete";
            notificationManager.create(subjectId, header, "Invoice is available for download.", "done", null);
        });
    }

    private void calculateSurcharges(Invoice invoice, Date enddate) {
        List<ServiceSurcharge> surcharges = surchargeManager.findForInvoice(enddate, invoice.getTenantId());
        LOGGER.debug("Found {} surcharges", surcharges.size());
        for (ServiceSurcharge surcharge : surcharges) {
            Service service = serviceManager.retrieve(surcharge.getServiceId());
            //surcharges are not brought into inventory, but I'm putting this here just in case that changes
            //or someone inadvertently adds a surcharge to inventory
            if (service.isCurrentInventory()) {
                continue;
            }
            Location location = locationManager.retrieve(service.getLocationId());
            Order order = orderManager.retrieve(location.getOrderId());
            String masterCompanyName = null;
            if (order.getCompany() != null && order.getCompany().getMasterCustomerId() != null) {
                Company masterCompany = companyManager.retrieve(order.getCompany().getMasterCustomerId());
                if (masterCompany != null) {
                    masterCompanyName = masterCompany.getName();
                }
            }
            InvoiceCharge invoiceCharge = new InvoiceCharge();
            invoiceCharge.setInvoiceId(invoice.getId());
            invoiceCharge.setLocationId(location.getId());
            invoiceCharge.setChargeCredit("Charge");
            invoiceCharge.setUnitCost(surcharge.getSurchargeType().getAmount());
            invoiceCharge.setPreviouslyBilled(BigDecimal.valueOf(0));
            invoiceCharge.setInvoicedAmount(surcharge.getSurchargeType().getAmount());
            invoiceCharge.setItemDesc(location.getName());
            invoiceCharge.setChargeDesc(surcharge.getSurchargeType().getType());
            invoiceCharge.setChargeType("Surcharge");
            invoiceCharge.setChargeLevel(surcharge.getSurchargeType().getLevel());
            invoiceCharge.setMasterCustomerName(masterCompanyName);
            invoiceCharge.setEndCustomerName(order.getCompany().getName());
            invoiceCharge.setBillableEventMilestoneDescription(null);
            invoiceCharge.setBillableEventDate(surcharge.getSurchargeDate());
            invoiceCharge.setTenantId(invoice.getTenantId());
            invoiceCharge = dao.create(invoiceCharge);

            surcharge.setInvoiceChargeId(invoiceCharge.getId());
            surchargeManager.edit(surcharge);
        }
    }

    private String calculateLocationCharges(final LocationMilestoneInstance milestone,
                                            final BigDecimal percentage,
                                            final Invoice invoice,
                                            final Date enddate) {

        Location location = locationManager.retrieve(milestone.getLocationId());
        if (location.isCurrentInventory()) {
            return "";
        }
        if (Strings.isNullOrEmpty(location.getLevelOfEffort())) {
            return location.getClientLocationId() + "/n";
        }
        Order order = orderManager.retrieve(location.getOrderId());
        String masterCompanyName = null;
        if (order.getCompany() != null && order.getCompany().getMasterCustomerId() != null) {
            Company masterCompany = companyManager.retrieve(order.getCompany().getMasterCustomerId());
            if (masterCompany != null) {
                masterCompanyName = masterCompany.getName();
            }
        }
        // get the level of efforts for a location and filter by end date if necessary;
        List<LevelOfEffort> loes = loeManager.findCurrentLoeByNameAndTenant(location.getLevelOfEffort(), location.getTenantId());
        LevelOfEffort loe = null;
        for (LevelOfEffort l : loes) {
            if (l.getEndDate() == null || l.getEndDate().before(enddate)) {
                loe = l;
                break;
            }
        }
        // get the level of effort amount
        BigDecimal loeAmount = loe.getAmount();
        //calculate billable amount for the milestone
        BigDecimal billableAmount = loeAmount.multiply(percentage.divide(new BigDecimal(100)));
        //get the previously billed amount for the location
        BigDecimal previouslyBilledAmt = dao.getPreviouslyBilledAmt(location.getId());
        previouslyBilledAmt = previouslyBilledAmt == null ? BigDecimal.valueOf(0) : previouslyBilledAmt;
        //check to see if there is more to be billed
        BigDecimal amountToBill = BigDecimal.valueOf(0);
        if (previouslyBilledAmt == null) {
            amountToBill = billableAmount;
        } else {
            amountToBill = billableAmount.subtract(previouslyBilledAmt);
        }
        InvoiceCharge invoiceCharge = null;
        if (amountToBill.compareTo(BigDecimal.valueOf(0)) > 0) {
            //create the invoice charge
            invoiceCharge = new InvoiceCharge();
            invoiceCharge.setInvoiceId(invoice.getId());
            invoiceCharge.setLocationId(location.getId());
            invoiceCharge.setChargeCredit("Charge");
            invoiceCharge.setUnitCost(loeAmount);
            invoiceCharge.setPreviouslyBilled(previouslyBilledAmt);
            invoiceCharge.setInvoicedAmount(amountToBill);
            invoiceCharge.setItemDesc(location.getName());
            invoiceCharge.setChargeDesc(loe.getLevelOfEffort());
            invoiceCharge.setChargeType("Level Of Effort");
            invoiceCharge.setChargeLevel(loe.getLevelOfEffort());
            invoiceCharge.setMasterCustomerName(masterCompanyName);
            invoiceCharge.setEndCustomerName(order.getCompany().getName());
            invoiceCharge.setBillableEventMilestoneDescription(milestone.getMilestone().getName());
            invoiceCharge.setBillableEventDate(milestone.getMilestoneDate());
            invoiceCharge.setMilestoneInstanceId(milestone.getId());
            invoiceCharge.setTenantId(invoice.getTenantId());
            invoiceCharge = dao.create(invoiceCharge);
        }


        milestone.setInvoiceId(invoice.getId());
        locationMilestoneManager.editInvoiceId(milestone);
        return "";
    }

    private String calculateServiceCharges(final ServiceMilestoneInstance milestone,
                                           final BigDecimal percentage,
                                           final Invoice invoice,
                                           final Date enddate) {

        Service service = serviceManager.retrieve(milestone.getServiceId());
        if (service.isCurrentInventory()) {
            return "";
        }
        Location location = locationManager.retrieve(service.getLocationId());
        // get the level of efforts for a location and filter by end date if necessary;
        if (Strings.isNullOrEmpty(location.getLevelOfEffort())) {
            return location.getClientLocationId() + "/n";
        }
        Order order = orderManager.retrieve(location.getOrderId());
        String masterCompanyName = null;
        if (order.getCompany() != null && order.getCompany().getMasterCustomerId() != null) {
            Company masterCompany = companyManager.retrieve(order.getCompany().getMasterCustomerId());
            if (masterCompany != null) {
                masterCompanyName = masterCompany.getName();
            }
        }
        List<LevelOfEffort> loes = loeManager.findCurrentLoeByNameAndTenant(location.getLevelOfEffort(), location.getTenantId());
        LevelOfEffort loe = null;
        for (LevelOfEffort l : loes) {
            if (l.getEndDate() == null || l.getEndDate().before(enddate)) {
                loe = l;
                break;
            }
        }
        // get the level of effort amount
        BigDecimal loeAmount = loe.getAmount();
        //calculate billable amount for the milestone
        BigDecimal billableAmount = loeAmount.multiply(percentage.divide(new BigDecimal(100)));
        //get the previously billed amount for the location
        BigDecimal previouslyBilledAmt = dao.getPreviouslyBilledAmt(location.getId());
        previouslyBilledAmt = previouslyBilledAmt == null ? BigDecimal.valueOf(0) : previouslyBilledAmt;
        //check to see if there is more to be billed
        BigDecimal amountToBill = BigDecimal.valueOf(0);
        if (previouslyBilledAmt == null) {
            amountToBill = billableAmount;
        } else {
            amountToBill = billableAmount.subtract(previouslyBilledAmt);
        }
        InvoiceCharge invoiceCharge = null;
        //if the previously billed amount is less than the amount to bill then create a charge
        if (amountToBill.compareTo(BigDecimal.valueOf(0)) > 0) {
            //create the invoice charge
            invoiceCharge = new InvoiceCharge();
            invoiceCharge.setInvoiceId(invoice.getId());
            invoiceCharge.setLocationId(location.getId());
            invoiceCharge.setChargeCredit("Charge");
            invoiceCharge.setUnitCost(loeAmount);
            invoiceCharge.setPreviouslyBilled(previouslyBilledAmt);
            invoiceCharge.setInvoicedAmount(amountToBill);
            invoiceCharge.setItemDesc(location.getName());
            invoiceCharge.setChargeDesc(loe.getLevelOfEffort());
            invoiceCharge.setChargeType("Level Of Effort");
            invoiceCharge.setChargeLevel(loe.getLevelOfEffort());
            invoiceCharge.setMasterCustomerName(masterCompanyName);
            invoiceCharge.setEndCustomerName(order.getCompany().getName());
            invoiceCharge.setBillableEventMilestoneDescription(milestone.getMilestone().getName());
            invoiceCharge.setBillableEventDate(milestone.getMilestoneDate());
            invoiceCharge.setMilestoneInstanceId(milestone.getId());
            invoiceCharge.setTenantId(invoice.getTenantId());
            invoiceCharge = dao.create(invoiceCharge);
        }

        milestone.setInvoiceId(invoice.getId());
        serviceMilestoneManager.editInvoiceId(milestone);
        return "";
    }
}
