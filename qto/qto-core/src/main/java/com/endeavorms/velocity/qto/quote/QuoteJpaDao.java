package com.endeavorms.velocity.qto.quote;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.endeavorms.velocity.qto.quote.QQuote.quote;

/**
 * @author fcurran
 * @since 1/13/2023
 */
@Component
public class QuoteJpaDao extends AbstractJpaDao<Quote, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Retrieves a quote by the vendor quote ID.
     * @param vendorQuoteId the ID of the quote to get.
     * @return the matching quote.
     */
    public Quote findByVendorQuoteId(final String vendorQuoteId) {
        return new JPAQuery<Quote>(entityManager).select(quote).from(quote)
                .where(quote.vendorQuoteId.eq(vendorQuoteId)).fetchOne();
    }
}
