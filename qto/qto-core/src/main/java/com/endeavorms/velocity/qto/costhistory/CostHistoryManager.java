package com.endeavorms.velocity.qto.costhistory;

import com.google.common.base.Strings;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.historyview.ServiceHistoryView;
import com.endeavorms.velocity.qto.service.historyview.ServiceHistoryViewManager;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.endeavorms.velocity.qto.common.SecurityUtils.SCHEDULER;

/**
 * @author rcasey
 * @since 11/1/2023
 */
@Component
public class CostHistoryManager extends StandardManager<CostHistory> {

    @Inject
    private CostHistoryJpaDao dao;

    @Inject
    private SubjectManager subjectManager;

    @Inject
    private ServiceHistoryViewManager serviceHistoryViewManager;

    @Inject
    private ServiceManager serviceManager;

    @Override
    protected CostHistoryJpaDao getDao() {
        return dao;
    }

    @Override
    public CostHistory edit(final CostHistory var1) {
        throw new RuntimeException("Cost History records should not be edited.");
    }

    //this one is for updating the Master Customer ID if it changes on the service
    public CostHistory editSuper(final CostHistory ch) {
        return super.edit(ch);
    }

    @Override
    public CostHistory create(final CostHistory entity) {
        entity.setUpdateDate(new Date());
        String username = SecurityUtils.getLoggedInUser();
        if (!SCHEDULER.equals(username)) {
            Subject subject = subjectManager.findByUsername(username);
            username = subject.getDisplayName();
        }
        entity.setUpdateBy(username);
        return super.create(entity);
    }
    public CostHistory create(final Service oldService, final Service newService,
                              final String costType, final String changeReason) {
        String changeReasonToUse
                = !Strings.isNullOrEmpty(changeReason) ? changeReason : newService.getCostChangeReason();
        CostHistory costHistory = new CostHistory();
        costHistory.setServiceId(newService.getId());
        String typeProvider = newService.getType();
        if (!Strings.isNullOrEmpty(newService.getProvider())) {
            typeProvider += ": " + newService.getProvider();
        }
        costHistory.setTypeProvider(typeProvider);
        costHistory.setCostType(costType);
        costHistory.setChangeReason(changeReasonToUse);
        if ("MRC".equals(costType)) {
            if (oldService != null) {
                costHistory.setOldValue(oldService.getMrc());
            }
            if ("Original MRC".equals(changeReasonToUse)) {
                costHistory.setNewValue(newService.getOriginalMrc());
            } else {
                costHistory.setNewValue(newService.getMrc());
            }
        } else if ("MRR".equals(costType)) {
            if (oldService != null) {
                costHistory.setOldValue(oldService.getMrr());
            }
            if ("Original MRR".equals(changeReasonToUse)) {
                costHistory.setNewValue(newService.getOriginalMrr());
            } else {
                costHistory.setNewValue(newService.getMrr());
            }
        } else if ("NRR".equals(costType)) {
            if (oldService != null) {
                costHistory.setOldValue(oldService.getNrr());
            }
            if ("Original NRR".equals(changeReasonToUse)) {
                costHistory.setNewValue(newService.getOriginalNrr());
            } else {
                costHistory.setNewValue(newService.getNrr());
            }
        } else if ("NRC".equals(costType)) {
            if (oldService != null) {
                costHistory.setOldValue(oldService.getNrc());
            }
            costHistory.setNewValue(newService.getNrc());
        } else if ("Annual Recurring Cost".equals(costType)) {
            if (oldService != null) {
                costHistory.setOldValue(oldService.getAnnualRecurringCost());
            }
            costHistory.setNewValue(newService.getAnnualRecurringCost());
        }
        if (oldService != null) {
            costHistory.setTenantId(oldService.getTenantId());
            costHistory.setMasterCustomerId(oldService.getMasterCustomerId());
        } else {
            costHistory.setTenantId(newService.getTenantId());
            costHistory.setMasterCustomerId(newService.getMasterCustomerId());
        }
        return create(costHistory);
    }

    public CostHistory create(final Service oldService, final Service newService, final String costType) {
        return create(oldService, newService, costType, null);
    }

    public PaginatedResult<CostHistory> findBySearchCriteria(final CostHistorySearchCriteria criteria) {
        if (criteria.getServiceId() == null && criteria.getLocationId() == null) {
            return new PaginatedResult<>();
        }
        CostHistorySearchCriteria updatedCriteria = updateSearchCriteria(criteria);
        return getDao().findBySearchCriteria(updatedCriteria);
    }

    public CostHistoryMeta getCostHistoryMeta(final CostHistorySearchCriteria criteria) {
        CostHistorySearchCriteria updatedCriteria = updateSearchCriteria(criteria);
        return getDao().getCostHistoryMeta(updatedCriteria);
    }

    private CostHistorySearchCriteria updateSearchCriteria(final CostHistorySearchCriteria criteria) {
        if (criteria.getServiceId() != null) {
            List<ServiceHistoryView> services = serviceHistoryViewManager.getServiceTreeList(criteria.getServiceId());
            criteria.setServiceIds(services.stream().map(ServiceHistoryView::getId).collect(Collectors.toList()));
        } else {
            List<Service> locationServices = serviceManager.findByLocationId(criteria.getLocationId());
            for (Service locationService : locationServices) {
                if (criteria.getServiceIds() == null) {
                    criteria.setServiceIds(new ArrayList<>());
                }
                List<ServiceHistoryView> services = serviceHistoryViewManager.getServiceTreeList(locationService.getId());
                criteria.getServiceIds().addAll(services.stream().map(ServiceHistoryView::getId).collect(Collectors.toList()));
            }
        }
        return criteria;
    }

    public List<CostHistory> findByServiceId(final Long serviceId) {
        return getDao().findByServiceId(serviceId);
    }
}
