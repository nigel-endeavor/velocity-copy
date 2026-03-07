package com.vertek.corporate.qto.note;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PlatformDatabase;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.note.QServiceNote.serviceNote;


/**
 * @author llevit
 * @since 1/16/2023
 */
@Stateless
public class ServiceNoteJpaDao extends AbstractMasterCustomerJpaDao<ServiceNote> {

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
     * Find all notes for a service.
     *
     * @param serviceId the service id
     * @return the list of notes
     */
    public List<ServiceNote> findByServiceId(Long serviceId) {
        return new JPAQuery<ServiceNote>(entityManager)
                .from(serviceNote).where(serviceNote.serviceId.eq(serviceId)).fetch();
    }
}
