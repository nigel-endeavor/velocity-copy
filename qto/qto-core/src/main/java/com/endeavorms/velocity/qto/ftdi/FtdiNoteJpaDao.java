package com.endeavorms.velocity.qto.ftdi;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.ftdi.QFtdiNote.ftdiNote;

@Component
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
