package com.endeavorms.velocity.qto.solution;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

/**
 * @author fcurran
 * @since 1/13/2023
 */
@Component
public class SolutionManager extends StandardManager<Solution> {

    /**
     * Persistence tier for Solutions.
     */
    @Inject
    private SolutionJpaDao dao;

    @Override
    protected SolutionJpaDao getDao() {
        return dao;
    }

            /**
     * Retrieves a quote by the vendor quote ID.
     * @param vendorSolutionId the ID of the quote to get.
     * @return the matching quote.
     */
    public Solution findByVendorSolutionId(final String vendorSolutionId) {
        return getDao().findByVendorSolutionId(vendorSolutionId);
    }
}
