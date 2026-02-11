package com.endeavorms.velocity.qto.address;

import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

/**
 * @author rcasey
 * @since 6/12/2024
 */
@Component
public class AddressViewManager extends StandardManager<AddressView> {

    @Inject
    private AddressViewJpaDao dao;

    @Override
    protected AddressViewJpaDao getDao() {
        return dao;
    }

    public PaginatedResult<AddressView> findBySearchCriteria(final AddressViewSearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }

    public AddressView findByClientLocationId(final String clientLocationId) {
        return getDao().findByClientLocationId(clientLocationId);
    }

}
