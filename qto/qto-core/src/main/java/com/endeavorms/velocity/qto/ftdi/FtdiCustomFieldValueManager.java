package com.endeavorms.velocity.qto.ftdi;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

@Component
public class FtdiCustomFieldValueManager extends StandardManager<FtdiCustomFieldValue> {

      @Inject
    public FtdiCustomFieldValueJpaDao dao;

    @Override
    public FtdiCustomFieldValueJpaDao getDao() { return dao; }

}
