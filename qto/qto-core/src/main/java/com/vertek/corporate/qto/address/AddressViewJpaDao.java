package com.vertek.corporate.qto.address;

import com.google.common.base.Strings;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import static com.vertek.corporate.qto.address.QAddressView.addressView;

/**
 * @author rcasey
 * @since 6/12/2024
 */
@Stateless
public class AddressViewJpaDao extends AbstractMasterCustomerJpaDao<AddressView> {

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

    public PaginatedResult<AddressView> findBySearchCriteria(final AddressViewSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<AddressView>(entityManager)
                .from(addressView)
                .where(getExpression(criteria))
                .orderBy(addressView.state.asc(), addressView.address1.asc())
                .fetchResults());
    }

    public Predicate getExpression(final AddressViewSearchCriteria criteria) {
        BooleanExpression expression = addressView.address1.isNotNull();
        if (!Strings.isNullOrEmpty(criteria.getSearch())) {
            expression = expression.and(addressView.address1.containsIgnoreCase(criteria.getSearch())
                    .or(addressView.address2.containsIgnoreCase(criteria.getSearch()))
                    .or(addressView.city.containsIgnoreCase(criteria.getSearch()))
                    .or(addressView.state.containsIgnoreCase(criteria.getSearch()))
                    .or(addressView.postalCode.containsIgnoreCase(criteria.getSearch()))
                    .or(addressView.country.containsIgnoreCase(criteria.getSearch()))
                    .or(addressView.clientLocationId.containsIgnoreCase(criteria.getSearch())));
        }
        if (criteria.getType()) {
            expression = expression.and(addressView.type.in("End Customer", "Location", "Billing"));
        } else {
            expression = expression.and(addressView.type.in("End Customer", "Billing"));
        }
        if (criteria.getCompanyId() != null) {
            expression = expression.and(addressView.companyId.eq(criteria.getCompanyId()));
        }
        return expression;
    }

    public AddressView findByClientLocationId(final String clientLocationId) {
        return new JPAQuery<AddressView>(entityManager)
                .from(addressView)
                .where(addressView.clientLocationId.eq(clientLocationId))
                .fetchFirst();
    }

}
