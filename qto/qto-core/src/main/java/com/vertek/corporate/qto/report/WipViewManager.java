package com.vertek.corporate.qto.report;

import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Stateless
public class WipViewManager extends StandardManager<WipServiceView> {

    @Inject
    private WipServiceJpaDao dao;

    @Inject
    private SubjectManager subjectManager;

    @Override
    protected WipServiceJpaDao getDao() {
        return dao;
    }


    public PaginatedResult<WipServiceView> findBySearchCriteria(final WipViewSearchCriteria crit) {
        return null;
    }


    public List<WipServiceView> getWipServices(final DashboardSearchCriteria criteria, final boolean allStatuses) {
        List<WipServiceView> wipServices = getDao().getWipServices(criteria, allStatuses);
        setSubjectNames(wipServices);
        return wipServices;
    }

    public List<WipServiceJeopView> getWipServiceJeops(final DashboardSearchCriteria criteria) {
        List<WipServiceJeopView> wipServiceJeops = getDao().getWipServiceJeops(criteria);
        setSubjectNames(wipServiceJeops);
        return wipServiceJeops;
    }

    public List<WipLocationJeopView> getWipLocationJeops(final DashboardSearchCriteria criteria) {
        List<WipLocationJeopView> wipLocationJeops = getDao().getWipLocationJeops(criteria);
        setSubjectNames(wipLocationJeops);
        return wipLocationJeops;
    }

    public List<WipServiceView> getProviderReliance(final DashboardSearchCriteria criteria) {
        List<WipServiceView> wipServices = getDao().getProviderReliance(criteria);
        setSubjectNames(wipServices);
        return wipServices;
    }

    private void setSubjectNames(final List<? extends WipService> wipServices) {
        List<Long> provisionerIds = new ArrayList<>();
        List<Long> vertekProjectManagerIds = new ArrayList<>();
//        List<Long> clientProjectManagerIds = new ArrayList<>();

        wipServices.forEach(wipService -> {
            if (!provisionerIds.contains(wipService.getProvisionerId())) {
                provisionerIds.add(wipService.getProvisionerId());
            }
            if (!vertekProjectManagerIds.contains(wipService.getVertekProjectManagerId())) {
                vertekProjectManagerIds.add(wipService.getVertekProjectManagerId());
            }
//            clientProjectManagerIds.add(wipService.getClientProjectManagerId());
        });


            Map<Long, String> subjects = Stream.of(provisionerIds, vertekProjectManagerIds)
                    .flatMap(Collection::stream)
                    .distinct()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(id -> id, id -> getDisplayName(id)));


        wipServices.forEach(wipService -> {
            wipService.setProvisioner(subjects.get(wipService.getProvisionerId()));
            wipService.setVertekProjectManager(subjects.get(wipService.getVertekProjectManagerId()));
//            wipService.setClientProjectManager(subjects.get(wipService.getClientProjectManagerId()));
        });
    }

    private String getDisplayName(Long subjectId) {
        Subject subject = subjectManager.findBySubjectId(subjectId);
        if (subject == null) {
            return "";
        } else {
            return subject.getDisplayName();
        }
    }

    /**
     * Returns services that meet the conditions of the monthly spend dashboard.
     * @param criteria the search criteria to filter services by.
     * @return matching services.
     */
    public List<WipServiceView> getServicesForMonthlySpend(final DashboardSearchCriteria criteria) {
        List<WipServiceView> wipServices = getDao().getServicesForMonthlySpend(criteria);
        setSubjectNames(wipServices);
        return wipServices;
    }

    /**
     * Returns services that meet the conditions of the incremental network spend dashboard.
     * @param criteria the search criteria to filter services by.
     * @return matching services.
     */
    public List<WipServiceView> getServicesForIncrementalNetworkSpend(final DashboardSearchCriteria criteria) {
        List<WipServiceView> wipServices = getDao().getServicesForIncrementalNetworkSpend(criteria);
        setSubjectNames(wipServices);
        return wipServices;
    }

    /**
     * Returns services that meet the conditions of the unbillable network expense accrual dashboard.
     * @param criteria the search criteria to filter services by.
     * @return matching services.
     */
    public List<WipServiceView> getServicesForUnbillableNetworkExpenseAccrual(final DashboardSearchCriteria criteria) {
        List<WipServiceView> wipServices = getDao().getServicesForUnbillableNetworkExpenseAccrual(criteria);
        setSubjectNames(wipServices);
        return wipServices;
    }
}
