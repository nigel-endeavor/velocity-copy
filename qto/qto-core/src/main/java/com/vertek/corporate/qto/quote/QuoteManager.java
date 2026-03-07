package com.vertek.corporate.qto.quote;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

/**
 * @author fcurran
 * @since 1/13/2023
 */
@Stateless
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
