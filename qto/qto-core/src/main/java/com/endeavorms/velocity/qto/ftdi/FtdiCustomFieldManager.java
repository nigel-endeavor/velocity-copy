package com.endeavorms.velocity.qto.ftdi;

import com.endeavorms.velocity.qto.common.GenericDao;
import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

@Component
public class FtdiCustomFieldManager extends StandardManager<FtdiCustomField> {

      @Inject
    public FtdiCustomFieldJpaDao dao;

    @Override
    public FtdiCustomFieldJpaDao getDao() { return dao; }

    /**
     * Get Custom Field.
     * @param fieldName field name.
     * @return Custom Field object.
     */
    public FtdiCustomField findCustomFieldByName(final String fieldName)    {
        return dao.findCustomFieldByName(fieldName);
    }

}
