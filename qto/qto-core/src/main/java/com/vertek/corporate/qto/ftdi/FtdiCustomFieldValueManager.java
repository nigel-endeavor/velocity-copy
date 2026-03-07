package com.vertek.corporate.qto.ftdi;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class FtdiCustomFieldValueManager extends StandardManager<FtdiCustomFieldValue> {

      @Inject
    public FtdiCustomFieldValueJpaDao dao;

    @Override
    public FtdiCustomFieldValueJpaDao getDao() { return dao; }

}
