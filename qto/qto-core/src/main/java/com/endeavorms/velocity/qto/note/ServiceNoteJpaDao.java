package com.endeavorms.velocity.qto.note;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerJpaDao;
import com.endeavorms.velocity.qto.common.PlatformDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.note.QServiceNote.serviceNote;


/**
 * @author llevit
 * @since 1/16/2023
 */
@Component
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
