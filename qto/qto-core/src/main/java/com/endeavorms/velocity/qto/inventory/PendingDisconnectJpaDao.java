package com.endeavorms.velocity.qto.inventory;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;
import com.endeavorms.velocity.qto.service.Service;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.inventory.QPendingDisconnect.pendingDisconnect;

/**
 * Persistence for pending disconnects.
 */
@Component
public class PendingDisconnectJpaDao extends AbstractJpaDao<PendingDisconnect, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     * Retrieves all pending disconnects matching the given criteria.
     * @param parentId ID.
     * @return PendingDisconnect.
     */
    public PendingDisconnect findByParent(final Long parentId) {
      return new JPAQuery<PendingDisconnect>(entityManager)
              .from(pendingDisconnect)
              .where(pendingDisconnect.parentService.id.eq(parentId))
              .fetchOne();
  }

    /**
     * Retrieves all pending disconnects matching the given criteria.
     * @param newId ID.
     * @return PendingDisconnect.
     */
  public PendingDisconnect findByNew(final Long newId) {
      return new JPAQuery<PendingDisconnect>(entityManager)
              .from(pendingDisconnect)
              .where(pendingDisconnect.newService.id.eq(newId))
              .fetchOne();
  }

    /**
     * Retrieves all pending disconnects matching the given criteria.
     * @param childId ID.
     * @return matching pending disconnects.
     */
  public PendingDisconnect findByChild(final Long childId) {
      return new JPAQuery<PendingDisconnect>(entityManager)
              .from(pendingDisconnect)
              .where(pendingDisconnect.childService.id.eq(childId))
              .fetchOne();
  }

   /**
     * Retrieves all pending disconnects matching the given criteria.
     * @return List of PendingDisconnects.
     */
    public List<PendingDisconnect> findOpen() {
      return new JPAQuery<PendingDisconnect>(entityManager)
              .from(pendingDisconnect)
              .where(pendingDisconnect.childService.isNull()
                      .and(pendingDisconnect.newServiceCompleteDate.isNotNull()))
              .fetch();
  }

}
