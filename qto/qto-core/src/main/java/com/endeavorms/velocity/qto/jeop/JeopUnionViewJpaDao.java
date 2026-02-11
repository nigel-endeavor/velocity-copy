package com.endeavorms.velocity.qto.jeop;

import com.google.common.base.Strings;
import com.querydsl.core.types.Order;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerJpaDao;
import com.endeavorms.velocity.qto.common.BussinessDaysUtils;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PlatformDatabase;
import com.endeavorms.velocity.qto.common.TenantSubjectManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author llevit
 */
@Component
public class JeopUnionViewJpaDao extends AbstractMasterCustomerJpaDao<Jeop> {

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

    public PaginatedResult<JeopUnionView> findBySearchCriteria(final JeopUnionViewSearchCriteria criteria) {
        String parentField;
        String levelClause = " and ";
        Long parentId;
        if (criteria.getOrderId() != null) {
            parentField = "order_id";
            parentId = criteria.getOrderId();
            levelClause += "jeop_level='order' ";
        } else if (criteria.getLocationId() != null) {
            parentField = "location_id";
            parentId = criteria.getLocationId();
            levelClause += "jeop_level in ('order', 'location') ";
        } else if (criteria.getServiceId() != null) {
            parentField = "service_id";
            parentId = criteria.getServiceId();
            levelClause += "jeop_level in ('order', 'location', 'service') ";
        } else {
            return new PaginatedResult<>();
        }

        String isOpen = " ";
        if (criteria.isOpen() != null && criteria.isOpen()) {
            isOpen = " and end_date is null ";
        }

        int count = ((Number) this.entityManager.createNativeQuery(
                "SELECT count(distinct jeop_instance_id) "
                        + "FROM v_jeops_union "
                        + "WHERE " + parentField + " = :parentId " + levelClause + isOpen)
                .setParameter("parentId", parentId)
                .getSingleResult()).intValue();

        if (count > 0) {
            String offsetStatement = criteria.getOffset() > 0 ? ", " + criteria.getOffset() : "";
            String sortField = criteria.getSortField() != null ? criteria.getSortField().replaceAll("([A-Z])", "_$1") : "end_date";
            Order sortDirection = criteria.getSortDirection() != null ? criteria.getSortDirection() : Order.ASC;
            List<Long> tenantIds = getAllowedTenantIds();
            List<Long> masterCustomerIds = getAllowedCompanyIds();
            String filterString = "";
            String tenantFilter = !tenantIds.isEmpty() ? "tenant_id in " + tenantIds.toString().replace('[', '(').replace(']', ')') : "";
            String masterCustomerFilter = !masterCustomerIds.isEmpty() ? "master_customer_id in " + masterCustomerIds.toString().replace('[', '(').replace(']', ')') : "";
            if (!Strings.isNullOrEmpty(tenantFilter) && !Strings.isNullOrEmpty(masterCustomerFilter)) {
                filterString = "AND (" + tenantFilter + " OR " + masterCustomerFilter + ") ";
            } else if (!Strings.isNullOrEmpty(tenantFilter)) {
                filterString = "AND " + tenantFilter + " ";
            } else if (!Strings.isNullOrEmpty(masterCustomerFilter)) {
                filterString = "AND " + masterCustomerFilter + " ";
            }

            List<Object[]> list = this.entityManager.createNativeQuery(
                            "SELECT distinct jeop_instance_id, jeop_level, jeop_description, start_date, end_date, note, responsibility, originator, assigned_to, business_days_open, calendar_days_open, level_jeop, version "
                                    + "FROM v_jeops_union "
                                    + "WHERE " + parentField + " = :parentId" + levelClause + isOpen + filterString
                                    + "ORDER BY " + sortField + " " + sortDirection + ", start_date DESC "
                                    + "limit :limit " + offsetStatement + ";")
                    .setParameter("parentId", parentId)
                    .setParameter("limit", criteria.getLimit())
                    .getResultList();

            List<JeopUnionView> collection = new ArrayList<>();
            for (Object[] item : list) {
                JeopUnionView jeop = new JeopUnionView();
                jeop.setId(((Integer) item[0]).longValue());
                jeop.setLevel((String) item[1]);
                jeop.setDescription((String) item[2]);
                jeop.setStartDate((Date) item[3]);
                jeop.setEndDate((Date) item[4]);
                jeop.setNote((String) item[5]);
                jeop.setResponsibility((String) item[6]);
                jeop.setOriginator((String) item[7]);
                jeop.setAssignedTo((String) item[8]);
                jeop.setBusinessDaysOpen(jeop.getEndDate() == null ? BussinessDaysUtils.getBusinessDaysCount(jeop.getStartDate(), new Date()) : (Integer) item[9]);
                jeop.setCalendarDaysOpen((Integer) item[10]);
                jeop.setLevelJeop((String) item[11]);
                jeop.setViewVersion((Integer) item[12]);
                collection.add(jeop);
            }

            PaginatedResult<JeopUnionView> result = new PaginatedResult<>();
            result.setOffset(criteria.getOffset());
            result.setLimit(criteria.getLimit());
            result.setTotal(count);
            result.setCollection(collection);

            return result;
        } else {
            return new PaginatedResult<>();
        }
    }
}
