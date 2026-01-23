package com.vertek.corporate.qto.quote;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import static com.vertek.corporate.qto.quote.QQuote.quote;

/**
 * @author fcurran
 * @since 1/13/2023
 */
@Stateless
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
