package com.endeavorms.velocity.qto.solution;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;
import com.endeavorms.velocity.qto.quote.Quote;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.endeavorms.velocity.qto.solution.QSolution.solution;

/**
 * @author fcurran
 * @since 1/13/2023
 */
@Component
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
