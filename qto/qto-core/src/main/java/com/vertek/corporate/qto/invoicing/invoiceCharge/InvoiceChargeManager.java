package com.vertek.corporate.qto.invoicing.invoiceCharge;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.company.Company;
import com.vertek.corporate.qto.company.CompanyManager;
import com.vertek.corporate.qto.invoicing.billableMilestone.BillableMilestone;
import com.vertek.corporate.qto.invoicing.billableMilestone.BillableMilestoneManager;
import com.vertek.corporate.qto.invoicing.invoice.Invoice;
import com.vertek.corporate.qto.invoicing.invoice.InvoiceManager;
import com.vertek.corporate.qto.invoicing.levelOfEffort.LevelOfEffort;
import com.vertek.corporate.qto.invoicing.levelOfEffort.LevelOfEffortManager;
import com.vertek.corporate.qto.invoicing.surcharge.service.ServiceSurcharge;
import com.vertek.corporate.qto.invoicing.surcharge.service.ServiceSurchargeManager;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.milestone.LocationMilestoneInstance;
import com.vertek.corporate.qto.milestone.LocationMilestoneInstanceManager;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstance;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstanceManager;
import com.vertek.corporate.qto.notification.NotificationManager;
import com.vertek.corporate.qto.order.Order;
import com.vertek.corporate.qto.order.OrderManager;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Resource;
import jakarta.ejb.EJBContext;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author mwelicka
 * @since 7/30/2023
 */

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class InvoiceChargeManager extends StandardManager<InvoiceCharge> {
    /**
     * Private logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceChargeManager.class);
    /**
     * Context from which we can get a transaction.
     */
    @Resource
    protected EJBContext ctx;

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
        UserTransaction dbTrans = ctx.getUserTransaction();
        try {
            // retrieve the current invoice
            Invoice invoice = invoiceManager.retrieve(invoiceId);
            Calendar cal = Calendar.getInstance();
            cal.setTime(invoice.getInvoiceEnd());
            cal.add(Calendar.DATE, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date enddate = cal.getTime();
            // remove any existing charges
            LOGGER.debug("Removing existing charges for invoice {}", invoiceId);
            dbTrans.begin();
            dao.removeExistingCharges(invoiceId);
            dbTrans.commit();
            dbTrans.begin();
            StringBuilder errMsg = new StringBuilder();
            // get the billable milestones for the tenant
            List<BillableMilestone> billableMilestones = billableMilestoneManager.findByTenantId(invoice.getTenantId());
            //for each billable milestone get the milestone instances and generate the charges
            for (BillableMilestone bm : billableMilestones) {
                String code = bm.getMilestone().getCode();
                String level = bm.getLevel();
                BigDecimal percentage = BigDecimal.valueOf(bm.getPercentage());
                LOGGER.debug("Generating charges for milestone {} at level {} with percentage {}",
                        code, level, percentage);
                if ("Service".equalsIgnoreCase(level)) {
                    List<ServiceMilestoneInstance> milestones = serviceMilestoneManager.retrieveBillableMilestones(
                            invoice.getTenantId(), code, enddate);
                    LOGGER.debug("Found {} milestones for Code {}", milestones.size(), code);
                    for (ServiceMilestoneInstance milestone : milestones) {
                        errMsg.append(calculateServiceCharges(milestone, percentage, invoice, enddate));
                    }
                } else if ("Location".equalsIgnoreCase(level)) {
                    List<LocationMilestoneInstance> milestones = locationMilestoneManager.retrieveBillableMilestones(
                            invoice.getTenantId(), code, enddate);
                    LOGGER.debug("Found {} milestones for Code {}", milestones.size(), code);
                    for (LocationMilestoneInstance milestone : milestones) {
                        errMsg.append(calculateLocationCharges(milestone, percentage, invoice, enddate));
                    }
                }
            }
            //calculate surcharges
            calculateSurcharges(invoice, enddate);
            Subject subject = subjectManager.retrieve(subjectId);
            invoice.setGeneratedDate(new Date());
            invoice.setGeneratedBy(subject.getDisplayName());
            String statusMsg = "";
            if (Strings.isNullOrEmpty(messageType)) {
                invoice.setInvoiceStatus("Draft");
                statusMsg = "Generate Charges";
            } else {
                //toggles the status of the invoice
                if (invoice.getInvoiceStatus().equalsIgnoreCase("Draft")) {
                    invoice.setInvoiceStatus("Final");
                    statusMsg = "Finalize and Generate Charges";
                } else {
                    invoice.setInvoiceStatus("Draft");
                    statusMsg = "Unfinalize and Generate Charges";
                }

            }
            //calculate the total charges for the invoice
            invoice.setTotalCharges(dao.getTotalCharges(invoiceId));
            invoiceManager.edit(invoice);
            dbTrans.commit();
            String header = header = "Invoice " + statusMsg + " Complete";
            String body = "Invoice is available for download.";
            notificationManager.create(subjectId, header, body.toString(), "done", null);
        } catch (NotSupportedException e) {
            handleException(dbTrans, e);
            throw new RuntimeException(e);
        } catch (SystemException e) {
            handleException(dbTrans, e);
            throw new RuntimeException(e);
        } catch (HeuristicRollbackException e) {
            handleException(dbTrans, e);
            throw new RuntimeException(e);
        } catch (HeuristicMixedException e) {
            handleException(dbTrans, e);
            throw new RuntimeException(e);
        } catch (RollbackException e) {
            handleException(dbTrans, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Handle an exception by logging a message and rolling back the transaction.
     *
     * @param dbTrans the db transaction.
     * @param e       the exception that was caught.
     */
    private void handleException(final UserTransaction dbTrans, final Exception e) {
        LOGGER.error("Failed generating charges");
        try {
            dbTrans.rollback();
        } catch (SystemException e1) {
            LOGGER.error("Error rolling back transaction for invoice charge generation", e1);
        }
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
