package com.endeavorms.velocity.qto.service.snapshot;

import com.endeavorms.velocity.qto.common.DashboardDataset;
import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.common.TenantView;
import com.endeavorms.velocity.qto.common.TenantViewManager;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.config.CompanyConfigKey;
import com.endeavorms.velocity.qto.config.CompanyConfigPropertyManager;
import com.endeavorms.velocity.qto.config.CompanyConfigurationProperty;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstance;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstanceManager;
import com.endeavorms.velocity.qto.order.Order;
import com.endeavorms.velocity.qto.order.OrderManager;
import com.endeavorms.velocity.qto.report.DashboardSearchCriteria;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Resource;
import jakarta.ejb.EJBContext;
import org.springframework.stereotype.Component;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import jakarta.transaction.UserTransaction;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rcasey
 * @since 3/22/2024
 */
@Component
@TransactionManagement(TransactionManagementType.BEAN)
public class ServiceSnapshotManager extends StandardManager<ServiceSnapshot> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceSnapshotManager.class);

    @Inject
    private ServiceSnapshotJpaDao dao;

    @Inject
    private TenantViewManager tenantViewManager;

    @Inject
    private CompanyManager companyManager;

    @Inject
    private CompanyConfigPropertyManager<CompanyConfigKey> companyConfigPropertyManager;

    @Inject
    private ServiceManager serviceManager;

    @Inject
    private LocationManager locationManager;

    @Inject
    private OrderManager orderManager;

    @Inject
    private ServiceMilestoneInstanceManager serviceMilestoneManager;

    /** Context from which we can get a transaction. */
    @Resource
    protected EJBContext ctx;

    @Override
    protected ServiceSnapshotJpaDao getDao() {
        return dao;
    }

    public void createServiceSnapshots() {
        UserTransaction dbTrans = ctx.getUserTransaction();
        long startTime = System.currentTimeMillis();
        LOGGER.debug("Creating service snapshots");

        try {
            dbTrans.begin();
            List<TenantView> tenants = tenantViewManager.getAllTenants();
            dbTrans.commit();
            LOGGER.debug("Found {} tenants: {}", tenants.size(), tenants.stream().map(TenantView::getName).toArray());
            Date today = new Date();

            for (TenantView tenant : tenants) {
                dbTrans.begin();
                // Check if nightly snapshots are disabled for this tenant
                Company tenantCompany = companyManager.findTenantByName(tenant.getName());
                CompanyConfigurationProperty configProperty = companyConfigPropertyManager.findByKey(tenantCompany.getId(), CompanyConfigKey.NIGHTLY_SERVICE_SNAPSHOTS_DISABLED);
                if (configProperty != null && Boolean.parseBoolean(configProperty.getValue())) {
                    LOGGER.debug("Service snapshots are disabled for tenant: {}", tenant.getName());
                    dbTrans.commit();
                    continue;
                }

                List<Service> services = serviceManager.findByTenantId(tenant.getId());
                dbTrans.commit();
                LOGGER.debug("Creating service snapshots for tenant: {} - {}.  Found {} services.", tenant.getId(), tenant.getName(), services.size());
                for (Service service : services) {
                    try {
                        dbTrans.begin();
                        Location location = locationManager.retrieve(service.getLocationId());
                        Order order = orderManager.retrieve(service.getOrderId());
                        ServiceMilestoneInstance dataProvCompleteMilestone = serviceMilestoneManager.retrieveCurrentMilestoneByCode(service.getId(), "DATA_PROVISIONING_COMPLETE");
                        ServiceMilestoneInstance completeMilestone = serviceMilestoneManager.retrieveCurrentMilestoneByCode(service.getId(), "COMPLETE");

                        ServiceSnapshot snapshot = new ServiceSnapshot();
                        snapshot.setSnapshotDate(today);
                        snapshot.setServiceId(service.getId());
                        snapshot.setLocationId(service.getLocationId());
                        snapshot.setOrderId(service.getOrderId());
                        snapshot.setEndCustomerId(order.getCompany().getId());
                        snapshot.setMasterCustomerId(order.getMasterCustomerId());
                        snapshot.setTenantId(tenant.getId());
                        snapshot.setClientServiceId(service.getClientServiceId());
                        snapshot.setClientLocationId(location.getClientLocationId());
                        snapshot.setServiceType(service.getType());
                        snapshot.setServiceBilledTo(service.getServiceBilledTo());
                        snapshot.setProvider(service.getProvider());
                        snapshot.setSubProductType(service.getSubProductType());
                        snapshot.setStatus(service.getStatus());
                        snapshot.setActive(service.isActive());
                        snapshot.setCurrentInventory(service.isCurrentInventory());
                        if (dataProvCompleteMilestone != null) {
                            snapshot.setDataProvisioningCompleteDate(dataProvCompleteMilestone.getMilestoneDate());
                        }
                        if (completeMilestone != null) {
                            snapshot.setCompleteDate(completeMilestone.getMilestoneDate());
                        }
                        snapshot.setMrc(service.getMrc());
                        snapshot.setNrc(service.getNrc());
                        snapshot.setAnnualRecurringCost(service.getAnnualRecurringCost());
                        snapshot.setOsp(service.getOsp());
                        snapshot.setIcb(service.getIcb());
                        create(snapshot);
                        dbTrans.commit();
                    } catch (Exception e) {
                        LOGGER.error("Error creating service snapshot for service: {}", service.getId(), e);
                        try {
                            dbTrans.rollback();
                        } catch (Exception e2) {
                            LOGGER.error("Error rolling back transaction", e2);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error creating service snapshots", e);
            try {
                dbTrans.rollback();
            } catch (Exception e2) {
                LOGGER.error("Error rolling back transaction", e2);
            }
        }

        LOGGER.debug("Done creating service snapshots. Process took {} ms", System.currentTimeMillis() - startTime);
    }

    public DashboardDataset<BigDecimal> getInventoryValuation(final DashboardSearchCriteria criteria, final int numOfMonths) {
        List<Object[]> rows = dao.getInventoryValuation(criteria, numOfMonths);
        List<BigDecimal> data = rows.stream().map(row -> (BigDecimal) row[0]).collect(Collectors.toList());
        List<String> labels = rows.stream().map(row -> (String) row[1]).collect(Collectors.toList());
        return new DashboardDataset<>(labels, data);
    }

    public DashboardDataset<BigInteger> getInventoryCounts(final DashboardSearchCriteria criteria, final int numOfMonths) {
        List<Object[]> rows = dao.getInventoryCounts(criteria, numOfMonths);
        List<BigInteger> data = rows.stream().map(row -> (BigInteger) row[0]).collect(Collectors.toList());
        List<String> labels = rows.stream().map(row -> (String) row[1]).collect(Collectors.toList());
        return new DashboardDataset<>(labels, data);
    }

    public DashboardDataset<BigInteger> getNewInventory(final DashboardSearchCriteria criteria, final int numOfMonths) {
        List<Object[]> rows = dao.getNewInventory(criteria, numOfMonths);
        List<BigInteger> data = rows.stream().map(row -> (BigInteger) row[0]).collect(Collectors.toList());
        List<String> labels = rows.stream().map(row -> (String) row[1]).collect(Collectors.toList());
        return new DashboardDataset<>(labels, data);
    }

    public List<ServiceSnapshot> findByServiceId(final Long serviceId) {
        return dao.findByServiceId(serviceId);
    }

    public List<String> findServiceTypes(){
        return getDao().findServiceTypes();
    }

}
