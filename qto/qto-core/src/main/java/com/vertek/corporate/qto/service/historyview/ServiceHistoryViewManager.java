package com.vertek.corporate.qto.service.historyview;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author rcasey
 * @since 10/27/2023
 */
@Stateless
public class ServiceHistoryViewManager extends StandardManager<ServiceHistoryView> {

    @Inject
    private ServiceHistoryViewJpaDao dao;

    @Override
    protected ServiceHistoryViewJpaDao getDao() {
        return dao;
    }

    public PaginatedResult<ServiceHistoryView> getServiceTreePaginatedResult(final Long serviceId, final String sortDir, final String sortField) {
        if (serviceId == null) {
            return new PaginatedResult<>();
        }

        List<ServiceHistoryView> services = getServiceTreeList(serviceId);

        Comparator<ServiceHistoryView> comparator = getComparator(sortDir, sortField);
        services.sort(comparator);

        PaginatedResult<ServiceHistoryView> result = new PaginatedResult<>();
        result.setCollection(services);
        result.setTotal(services.size());
        return result;
    }

    /**
     * Gets all services in the tree for the given service id.
     * @param serviceId serviceId
     * @return root service id
     */
    public List<ServiceHistoryView> getServiceTreeList(final Long serviceId) {
        List<ServiceHistoryView> services = new ArrayList<>();
        List<Long> leaves = new ArrayList<>();

        //get the root service in the tree, adds it to the list
        Long rootId = dao.getServiceTreeRoot(serviceId);
        ServiceHistoryView rootService = retrieve(rootId);
        services.add(rootService);
        leaves.add(rootId);

        //while there are leaves to evaluate, identify their children and add them to the list
        while (!leaves.isEmpty()) {
            List<ServiceHistoryView> children = dao.findByParentServiceId(leaves.get(0));
            for (ServiceHistoryView child : children) {
                services.add(child);
                leaves.add(child.getId());
            }
            //remove processed leaf from the list
            leaves.remove(0);
        }
        return services;
    }


    private Comparator<ServiceHistoryView> getComparator(final String sortDir, final String sortField) {
        Comparator<ServiceHistoryView> comparator;
        Comparator<String> nullSafeStringComparator = Comparator.nullsFirst(String::compareToIgnoreCase);
        Comparator<Long> nullSafeLongComparator = Comparator.nullsFirst(Long::compareTo);
        Comparator<Date> nullSafeDateComparator = Comparator.nullsFirst(Date::compareTo);

        if (Strings.isNullOrEmpty(sortField)) {
            return Comparator.comparing(ServiceHistoryView::getCreated, nullSafeDateComparator);
        }

        switch (sortField) {
            case "provider":
                comparator = Comparator.comparing(ServiceHistoryView::getProvider, nullSafeStringComparator);
                break;
            case "orderType":
                comparator = Comparator.comparing(ServiceHistoryView::getOrderType, nullSafeStringComparator);
                break;
            case "subOrderType":
                comparator = Comparator.comparing(ServiceHistoryView::getSubOrderType, nullSafeStringComparator);
                break;
            case "providerOrderNum":
                comparator = Comparator.comparing(ServiceHistoryView::getProviderOrderNum, nullSafeStringComparator);
                break;
            case "complete":
                comparator = Comparator.comparing(ServiceHistoryView::getComplete, nullSafeDateComparator);
                break;
            case "parentServiceId":
                comparator = Comparator.comparing(ServiceHistoryView::getParentServiceId, nullSafeLongComparator);
                break;
            case "id":
                comparator = Comparator.comparing(ServiceHistoryView::getId, nullSafeLongComparator);
                break;
            case "created":
            default:
                comparator = Comparator.comparing(ServiceHistoryView::getCreated, nullSafeDateComparator);
                break;
        }

        if ("DESC".equals(sortDir)) {
            return comparator.reversed();
        }
        return comparator;
    }

}
