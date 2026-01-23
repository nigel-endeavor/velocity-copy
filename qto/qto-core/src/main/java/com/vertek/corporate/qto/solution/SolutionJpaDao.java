package com.vertek.corporate.qto.solution;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;
import com.vertek.corporate.qto.quote.Quote;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import static com.vertek.corporate.qto.solution.QSolution.solution;

/**
 * @author fcurran
 * @since 1/13/2023
 */
@Stateless
public class SolutionJpaDao extends AbstractJpaDao<Solution, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

        /**
     * Retrieves a quote by the vendor quote ID.
     * @param vendorSolutionId the ID of the quote to get.
     * @return the matching quote.
     */
    public Solution findByVendorSolutionId(final String vendorSolutionId) {
        return new JPAQuery<Solution>(entityManager).select(solution).from(solution)
                .where(solution.vendorSolutionId.eq(vendorSolutionId)).fetchOne();
    }
}
