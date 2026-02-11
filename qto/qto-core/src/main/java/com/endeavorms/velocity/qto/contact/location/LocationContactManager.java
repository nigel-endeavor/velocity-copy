package com.endeavorms.velocity.qto.contact.location;

import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.contact.ContactType;
import com.endeavorms.velocity.qto.location.LocationManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Business logic tier for location contacts.
 *
 * @author fcurran
 * @since 3/31/2023
 */
@Component
public class LocationContactManager extends StandardManager<LocationContact> {

    /**
     * Persistence tier for location contacts.
     */
    @Inject
    private LocationContactJpaDao dao;

    /**
     * Business logic tier for companies.
     */
    @Inject
    private CompanyManager companyManager;

    /**
     * Business logic tier for locations.
     */
    @Inject
    private LocationManager locationManager;

    @Override
    protected LocationContactJpaDao getDao() {
        return dao;
    }

    @Override
    public LocationContact create(final LocationContact locationContact) {
        if (locationContact.getTenantId() == null) {
            Company parentCompany = companyManager.retrieve(locationContact.getCompanyId());
            locationContact.setTenantId(parentCompany.getTenantId());
            locationContact.setMasterCustomerId(parentCompany.getMasterCustomerId());
        }
        LocationContact persisted = super.create(locationContact);
        return persisted;
    }

    @Override
    public LocationContact edit(final LocationContact locationContact) {
        if (locationContact.getTenantId() == null) {
            Company parentCompany = companyManager.retrieve(locationContact.getCompanyId());
            locationContact.setTenantId(parentCompany.getTenantId());
            locationContact.setMasterCustomerId(parentCompany.getMasterCustomerId());
        }
        return super.edit(locationContact);
    }



    /**
     * Returns location contacts that match the provided search criteria.
     *
     * @param criteria what to match location contacts on.
     * @return the matching location contacts, if any.
     */
    public PaginatedResult<LocationContact> findBySearchCriteria(final LocationContactSearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }

    /**
     * Returns the contact for the given type and location id.
     *
     * @param type       the contact type to search for.
     * @param locationId the location id to search for.
     * @return the contact for the given type and location id.
     */
    public LocationContact retrieveByTypeAndLocationId(final ContactType type, final Long locationId) {
        return getDao().retrieveByTypeAndLocationId(type, locationId);
    }

    /**
     * Returns the contact for the given type and location id.
     *
     * @param locationId the location id to search for.
     * @return the contact for the given type and location id.
     */
    public List<LocationContact> retrieveByLocationId(final Long locationId) {
        return getDao().retrieveByLocationId(locationId);
    }
}
