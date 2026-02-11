package com.endeavorms.velocity.qto.quote;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

/**
 * @author fcurran
 * @since 1/13/2023
 */
@Component
public class QuoteManager extends StandardManager<Quote> {

    /**
     * Persistence tier for Quotes.
     */
    @Inject
    private QuoteJpaDao dao;

    @Override
    protected QuoteJpaDao getDao() {
        return dao;
    }

    /**
     * Retrieves a quote by the quote ID.
     * @param quoteId the ID of the quote to get.
     * @return the matching quote.
     */
    public Quote findByVendorQuoteId(final String quoteId) {
        return dao.findByVendorQuoteId(quoteId);
    }
}
