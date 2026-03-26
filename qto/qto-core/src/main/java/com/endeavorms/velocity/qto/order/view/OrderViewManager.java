package com.endeavorms.velocity.qto.order.view;

import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

/**
 * Thin manager for OrderView — delegates directly to OrderViewJpaDao.
 * No mutation logic needed; the view is read-only.
 */
@Component
public class OrderViewManager extends StandardManager<OrderView> {

    @Inject
    private OrderViewJpaDao dao;

    @Override
    protected OrderViewJpaDao getDao() {
        return dao;
    }

    public PaginatedResult<OrderView> findBySearchCriteria(final OrderViewSearchCriteria criteria) {
        return dao.findBySearchCriteria(criteria);
    }
}
