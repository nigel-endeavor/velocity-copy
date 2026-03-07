package com.vertek.corporate.qto.ftdi;

import com.vertek.corporate.qto.common.GenericDao;
import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
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
