package com.vertek.corporate.qto.address;

import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author rcasey
 * @since 6/12/2024
 */
@Stateless
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
