package com.vertek.corporate.qto.ftdi;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.ftdi.QFtdiNote.ftdiNote;

@Stateless
public class FtdiNoteJpaDao extends AbstractJpaDao<FtdiNote, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     * Get notes for a schedule item.
     *
     * @param id Schedule .
     * @return Equipment.
     */
    public List<FtdiNote> findByDispathVendorId(final Long id) {
        return new JPAQuery<FtdiNote>(entityManager)
                .from(ftdiNote)
                .where(ftdiNote.dispatchVendorId.eq(id))
                .fetch();
    }
}
