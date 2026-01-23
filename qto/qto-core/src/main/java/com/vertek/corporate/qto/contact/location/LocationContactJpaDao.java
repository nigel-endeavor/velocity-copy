package com.vertek.corporate.qto.contact.location;

import com.google.common.base.Strings;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;
import com.vertek.corporate.qto.contact.ContactType;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.util.List;

import static com.vertek.corporate.qto.contact.location.QLocationContact.locationContact;

/**
 * Persistence tier for location contacts.
 * @author fcurran
 * @since 3/31/2023
 */
@Stateless
public class LocationContactJpaDao extends AbstractMasterCustomerJpaDao<LocationContact> {
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

    /**
     * Returns location contacts that match the provided search criteria.
     * @param criteria what to match location contacts on.
     * @return the matching location contacts, if any.
     */
    public PaginatedResult<LocationContact> findBySearchCriteria(final LocationContactSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<LocationContact>(entityManager).from(locationContact)
                .where(getExpression(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .orderBy(locationContact.sortOrder.desc(), locationContact.id.desc())
                .fetchResults()); //todo: assess deprecation, this will require changes to PaginatedResult
    }

    /**
     * Gets the where clause for a given criteria.
     * @param criteria the criteria to filter by.
     * @return relevant criteria.
     */
    private Predicate getExpression(final LocationContactSearchCriteria criteria) {
        BooleanExpression expression = locationContact.locationId.eq(criteria.getLocationId());
        expression = addMasterCustomerAndTenantFilter(expression, locationContact.masterCustomerId, locationContact.tenantId, true);

        if (!Strings.isNullOrEmpty(criteria.getSearch())) {
            expression = expression.and(
                    locationContact.firstName.contains(criteria.getSearch())
                            .or(locationContact.lastName.contains(criteria.getSearch()))
                            .or(locationContact.email.contains(criteria.getSearch()))
                            .or(locationContact.phone.contains(criteria.getSearch())));
        }

        return expression;
    }


    /**
     * Returns the contact for the given type and location id.
     * @param type the contact type to search for.
     * @param locationId the location id to search for.
     * @return the contact for the given type and location id.
     */
    public LocationContact retrieveByTypeAndLocationId(final ContactType type, final Long locationId) {
        return new JPAQuery<LocationContact>(entityManager).from(locationContact)
                .where(locationContact.locationId.eq(locationId)
                        .and(locationContact.type.eq(type)))
                .fetchOne();
    }

    /**
     * Returns the contact for the given type and location id.
     *
     * @param locationId the location id to search for.
     * @return the contact for the given type and location id.
     */
    public List<LocationContact> retrieveByLocationId(Long locationId) {
        return new JPAQuery<LocationContact>(entityManager).from(locationContact)
            .where(locationContact.locationId.eq(locationId))
            .fetch();
    }
}
