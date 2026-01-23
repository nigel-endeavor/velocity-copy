package com.vertek.corporate.qto.note;

import com.google.common.base.Strings;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.service.historyview.ServiceHistoryViewManager;
import org.apache.shiro.SecurityUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.vertek.corporate.qto.note.QNoteUnionView.noteUnionView;

/**
 * @author llevit
 * @since 1/30/2023
 */
@Stateless
public class NoteUnionViewJpaDao extends AbstractMasterCustomerJpaDao<Note> {
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

    @Inject
    private ServiceHistoryViewManager serviceHistoryViewManager;

    @Inject
    private LocationManager locationManager;

    public PaginatedResult<NoteUnionView> findBySearchCriteria(final NoteUnionViewSearchCriteria criteria, final boolean auditNotes) {
        String parentField;
//        List<Long> parentIds = new ArrayList<;
        Long parentId = 0L;
        boolean hideInternalNotes = !SecurityUtils.getSubject().isPermitted(Permissions.ORDER_WRITE);
        if (criteria.getLocationId() != null) {
            parentField = "v_note_union.location_id";
            parentId = criteria.getLocationId();
        } else if (criteria.getServiceId() != null) {
            parentField = "v_note_union.service_id";
            parentId = criteria.getServiceId();
        } else {
            return new PaginatedResult<>();
        }


        int count = ((Number) this.entityManager.createNativeQuery(
                "SELECT count(distinct note_id) "
                        + "FROM v_note_union "
                        + "WHERE " + parentField + " = :parentId "
                        + (auditNotes ? "AND category = 'Audit' " : ""))
                .setParameter("parentId", parentId)
                .getSingleResult()).intValue();

        if (count > 0) {
            String offsetStatement = criteria.getOffset() > 0 ? ", " + criteria.getOffset() : "";
            String sortField = criteria.getSortField().replaceAll("([A-Z])", "_$1");
            List<Long> tenantIds = getAllowedTenantIds();
            List<Long> masterCustomerIds = getAllowedCompanyIds();
            String filterString = "";
            String tenantFilter = !tenantIds.isEmpty() ? "v_note_union.tenant_id in " + tenantIds.toString().replace('[', '(').replace(']', ')') : "";
            String masterCustomerFilter = !masterCustomerIds.isEmpty() ? "v_note_union.master_customer_id in " + masterCustomerIds.toString().replace('[', '(').replace(']', ')') : "";
            if (!Strings.isNullOrEmpty(tenantFilter) && !Strings.isNullOrEmpty(masterCustomerFilter)) {
                filterString = "AND (" + tenantFilter + " OR " + masterCustomerFilter + ") ";
            } else if (!Strings.isNullOrEmpty(tenantFilter)) {
                filterString = "AND " + tenantFilter + " ";
            } else if (!Strings.isNullOrEmpty(masterCustomerFilter)) {
                filterString = "AND " + masterCustomerFilter + " ";
            }

            String joinString = "";
            String ownerId = "";
            if (criteria.getLocationId() != null) {
                ownerId = ", sn.service_id";
                joinString = "LEFT OUTER JOIN service_note sn ON v_note_union.note_id = sn.note_id ";
            } else if (criteria.getServiceId() != null) {
                ownerId = ", s.location_id";
                joinString = "LEFT OUTER JOIN service s ON v_note_union.service_id = s.service_id ";
            }

            List<Object[]> list = this.entityManager.createNativeQuery(
                            "SELECT distinct v_note_union.note_id, category, created_by, created_date, note, internal_only, " + parentField + ", edited_by, edited_date, v_note_union.version, created_by_id" + ownerId + " "
                                    + "FROM v_note_union "
                                    + joinString
                                    + "WHERE " + parentField + " = :parentId "
                                    + (hideInternalNotes ? "AND internal_only = false " : "")
                                    + filterString
                                    + "AND category " + (auditNotes ? "=" : "!=") + " 'Audit' "
                                    + "ORDER BY " + sortField + " " + criteria.getSortDirection() + " "
                                    + "limit :limit " + offsetStatement + ";")
                    .setParameter("parentId", parentId)
                    .setParameter("limit", criteria.getLimit())
                    .getResultList();

            List<NoteUnionView> collection = new ArrayList<>();
            for (Object[] item : list) {
                NoteUnionView note = new NoteUnionView();
                note.setId(((Integer) item[0]).longValue());
                note.setCategory((String) item[1]);
                note.setCreatedBy((String) item[2]);
                note.setCreatedDate((Date) item[3]);
                note.setNote((String) item[4]);
                note.setInternalOnly((Boolean) item[5]);
                if (parentField.equals("v_note_union.location_id")) {
                    note.setLocationId(((Integer) item[6]).longValue());
                    Integer serviceId = ((Integer) item[11]);
                    if (serviceId != null) {
                        note.setServiceId(serviceId.longValue());
                    }
                } else {
                    note.setServiceId(((Integer) item[6]).longValue());
                    note.setLocationId(((Integer) item[11]).longValue());
                }
                note.setEditedBy((String) item[7]);
                note.setEditedDate((Date) item[8]);
                note.setVersion((Integer) item[9]);
                Integer createdById = ((Integer) item[10]);
                if (createdById != null) {
                    note.setCreatedById(createdById.longValue());
                }
                collection.add(note);
            }

            PaginatedResult<NoteUnionView> result = new PaginatedResult<>();
            result.setOffset(criteria.getOffset());
            result.setLimit(criteria.getLimit());
            result.setTotal(count);
            result.setCollection(collection);

            return result;
        } else {
            return new PaginatedResult<>();
        }
    }

    public List<NoteUnionView> getActivationNotes(final Long serviceId) {
        return new JPAQuery<NoteUnionView>(entityManager)
                .from(noteUnionView)
                .where(noteUnionView.category.eq("Activation")
                        .and(noteUnionView.serviceId.eq(serviceId)))
                .fetch();
    }
}
