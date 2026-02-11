package com.endeavorms.velocity.qto.service.snapshot;

import com.google.common.collect.Lists;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMultitenantJpaDao;
import com.endeavorms.velocity.qto.common.PlatformDatabase;
import com.endeavorms.velocity.qto.common.Tenant;
import com.endeavorms.velocity.qto.common.TenantSubjectManager;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.report.DashboardSearchCriteria;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import static com.endeavorms.velocity.qto.service.snapshot.QServiceSnapshot.serviceSnapshot;

/**
 * @author rcasey
 * @since 3/22/2024
 */
@Component
public class ServiceSnapshotJpaDao extends AbstractMultitenantJpaDao<ServiceSnapshot, Long> {

    @Inject
    private CompanyManager companyManager;

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Inject
    protected void setPlatformEntityManager(@PlatformDatabase final EntityManager platformEntityManager) {
        this.platformEntityManager = platformEntityManager;
    }

    public List<Object[]> getInventoryValuation(final DashboardSearchCriteria criteria, final int numOfMonths) {
        String sql = loadSqlResource("/sql/inventory-valuation.sql");
        return runQuery(criteria, numOfMonths, sql);
    }

    public List<Object[]> getInventoryCounts(final DashboardSearchCriteria criteria, final int numOfMonths) {
        String sql = loadSqlResource("/sql/inventory-count.sql");
        return runQuery(criteria, numOfMonths, sql);
    }

    public List<Object[]> getNewInventory(final DashboardSearchCriteria criteria, final int numOfMonths) {
        String sql = loadSqlResource("/sql/new-inventory.sql");
        return runQuery(criteria, numOfMonths, sql);
    }

    private List<Object[]> runQuery(final DashboardSearchCriteria criteria, final int numOfMonths, final String sql) {
        Query query = getEntityManager().createNativeQuery(sql);
        Long tenantId = tenantSubjectManager.getCurrentTenant().getId();

        boolean filterOnMc = ((criteria.getMasterCompanyNames() != null) && !criteria.getMasterCompanyNames().isEmpty());
        boolean filterOnEc = ((criteria.getCompanyNames() != null) && !criteria.getCompanyNames().isEmpty());
        boolean filterOnServiceType = ((criteria.getServiceTypes() != null) && !criteria.getServiceTypes().isEmpty());
        boolean filterOnProvider = ((criteria.getProviders() != null) && !criteria.getProviders().isEmpty());
        boolean filterOnServiceBilledTo = ((criteria.getServiceBilledTos() != null) && !criteria.getServiceBilledTos().isEmpty());

        List<Long> mcIds = filterOnMc ? companyManager.getCompanyIdsFromNames(criteria.getMasterCompanyNames(), "Master Customer", tenantId) : Lists.newArrayList(-1L);
        List<Long> ecIds = filterOnEc ? companyManager.getCompanyIdsFromNames(criteria.getCompanyNames(), "End Customer", tenantId) : Lists.newArrayList(-1L);

        query.setParameter("filter_on_mc", filterOnMc);
        query.setParameter("mc_ids", mcIds);
        query.setParameter("filter_on_ec", filterOnEc);
        query.setParameter("ec_ids", ecIds);
        query.setParameter("filter_on_service_type", filterOnServiceType);
        query.setParameter("service_types", filterOnServiceType ? criteria.getServiceTypes() : Lists.newArrayList(-1L));
        query.setParameter("filter_on_provider", filterOnProvider);
        query.setParameter("providers", filterOnProvider ? criteria.getProviders() : Lists.newArrayList(-1L));
        query.setParameter("filter_on_service_billed_to", filterOnServiceBilledTo);
        query.setParameter("service_billed_tos", filterOnServiceBilledTo ? criteria.getServiceBilledTos() : Lists.newArrayList(-1L));
        query.setParameter("limit", numOfMonths);
        query.setParameter("tenant_id", tenantId);

        List<Object[]> results = query.getResultList();
        Collections.reverse(results);
        return results;
    }

    private String loadSqlResource(final String path) {
        try {
            return loadResourceAsString(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not load SQL resource file.", e);
        }
    }

    private String loadResourceAsString(final String path) throws IOException {
        InputStream inputStream = ServiceSnapshotJpaDao.class.getResourceAsStream(path);
        if (inputStream == null) {
            throw new IOException("Input stream is null");
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferredReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = bufferredReader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    public List<ServiceSnapshot> findByServiceId(Long serviceId) {
        return new JPAQuery<ServiceSnapshot>(entityManager)
                .from(serviceSnapshot)
                .where(serviceSnapshot.serviceId.eq(serviceId))
                .fetch();
    }

    public List<String> findServiceTypes() {
        Tenant selectedTenant = tenantSubjectManager.getCurrentTenant();
        return new JPAQuery<String>(entityManager)
                .select(serviceSnapshot.serviceType)
                .from(serviceSnapshot)
                .where(serviceSnapshot.tenantId.eq(selectedTenant.getId()))
                .distinct().fetch();
    }
}
