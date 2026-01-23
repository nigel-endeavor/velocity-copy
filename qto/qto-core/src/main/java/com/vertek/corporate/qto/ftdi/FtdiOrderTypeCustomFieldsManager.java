package com.vertek.corporate.qto.ftdi;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.common.StandardManager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import static com.vertek.corporate.qto.ftdi.QFtdiCustomField.ftdiCustomField;

@Stateless
public class FtdiOrderTypeCustomFieldsManager extends StandardManager<FtdiOrderTypeCustomFields> {

    @Inject
    FtdiOrderTypeCustomFieldsJpaDao dao;

    @Override
    public FtdiOrderTypeCustomFieldsJpaDao getDao() {
        return dao;
    }

            /**
     * Get Custom Field.
     * @param id field name Id.
     * @return Custom Field object.
     */
    public FtdiOrderTypeCustomFields findByCustomFieldIdAndOrder(final Long id, final Long orderId)    {
        return dao.findByCustomFieldIdAndOrder(id, orderId);
    }
}
