package com.endeavorms.velocity.qto.note;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerJpaDao;
import com.endeavorms.velocity.qto.common.PlatformDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.endeavorms.velocity.qto.note.QLocationNote.locationNote;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Component
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
