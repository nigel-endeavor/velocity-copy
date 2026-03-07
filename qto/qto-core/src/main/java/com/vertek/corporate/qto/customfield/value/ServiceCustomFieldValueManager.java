package com.vertek.corporate.qto.customfield.value;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ServiceCustomFieldValueManager extends AbstractCustomFieldValueManager<ServiceCustomFieldValue> {

    @Inject
    private ServiceCustomFieldValueJpaDao serviceCustomFieldValueJpaDao;

    @Inject
    private ServiceManager serviceManager;

    @Override
    protected ServiceCustomFieldValueJpaDao getDao() {
        return serviceCustomFieldValueJpaDao;
    }

    public List<ServiceCustomFieldValue> saveValues(final List<ServiceCustomFieldValue> values) {
        List<ServiceCustomFieldValue> saved = new ArrayList<>();
        for (ServiceCustomFieldValue value : values) {
            if (value.getId() == null) {
                saved.add(create(value));
            } else {
                saved.add(edit(value));
            }
        }
        return saved;
    }

    @Override
    public ServiceCustomFieldValue edit(final ServiceCustomFieldValue entity) {
        Service service = serviceManager.retrieve(entity.getServiceId());
        entity.setTenantId(service.getTenantId());
        return super.edit(entity);
    }

    @Override
    public ServiceCustomFieldValue create(final ServiceCustomFieldValue entity) {
        Service service = serviceManager.retrieve(entity.getServiceId());
        entity.setTenantId(service.getTenantId());
        return super.create(entity);
    }

    @Override
    public List<ServiceCustomFieldValue> findByRecordId(final Long recordId) {
        return serviceCustomFieldValueJpaDao.findByRecordId(recordId);
    }

}
