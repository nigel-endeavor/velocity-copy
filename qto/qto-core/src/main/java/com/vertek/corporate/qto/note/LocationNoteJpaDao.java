package com.vertek.corporate.qto.note;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PlatformDatabase;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.util.List;

import static com.vertek.corporate.qto.note.QLocationNote.locationNote;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Stateless
public class LocationNoteJpaDao extends AbstractMasterCustomerJpaDao<LocationNote> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Inject
    protected void setPlatformEntityManager(@PlatformDatabase final EntityManager platformEntityManager) {
        this.platformEntityManager = platformEntityManager;
    }

    /**
     * Find all notes for a location.
     *
     * @param locationId the location id
     * @return the list of notes
     */
    public List<LocationNote> findByLocationId(Long locationId) {
        return new JPAQuery<LocationNote>(entityManager)
                .from(locationNote).where(locationNote.locationId.eq(locationId)).fetch();
    }

}
