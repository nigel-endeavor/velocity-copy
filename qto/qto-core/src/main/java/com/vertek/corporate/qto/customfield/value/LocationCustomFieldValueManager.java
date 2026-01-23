package com.vertek.corporate.qto.customfield.value;

import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class LocationCustomFieldValueManager extends AbstractCustomFieldValueManager<LocationCustomFieldValue> {

    @Inject
    private LocationCustomFieldValueJpaDao locationCustomFieldValueJpaDao;

    @Inject
    private LocationManager locationManager;

    @Override
    protected LocationCustomFieldValueJpaDao getDao() {
        return locationCustomFieldValueJpaDao;
    }

    public List<LocationCustomFieldValue> saveValues(final List<LocationCustomFieldValue> values) {
        List<LocationCustomFieldValue> saved = values;
        for (LocationCustomFieldValue value : values) {
            if (value.getId() == null) {
                saved.add(create(value));
            } else {
                saved.add(edit(value));
            }
        }
        return saved;
    }

    @Override
    public LocationCustomFieldValue create(final LocationCustomFieldValue entity) {
        Location location = locationManager.retrieve(entity.getLocationId());
        entity.setTenantId(location.getTenantId());
        return super.create(entity);
    }

    @Override
    public LocationCustomFieldValue edit(final LocationCustomFieldValue entity) {
        Location location = locationManager.retrieve(entity.getLocationId());
        entity.setTenantId(location.getTenantId());
        return super.edit(entity);
    }

    public List<LocationCustomFieldValue> findByRecordId(final Long recordId) {
        return locationCustomFieldValueJpaDao.findByRecordId(recordId);
    }

}
