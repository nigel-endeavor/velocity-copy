package com.endeavorms.velocity.qto.ftdi;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

import static com.endeavorms.velocity.qto.ftdi.QFtdiCustomField.ftdiCustomField;

@Component
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
