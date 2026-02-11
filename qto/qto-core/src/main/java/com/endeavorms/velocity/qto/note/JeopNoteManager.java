package com.endeavorms.velocity.qto.note;

import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Component
public class JeopNoteManager extends StandardManager<JeopNote> {

    /**
     * Persistence tier for JeopNote.
     */
    @Inject
    private JeopNoteJpaDao dao;

    @Override
    protected JeopNoteJpaDao getDao() {
        return dao;
    }

    public PaginatedResult<JeopNote> findBySearchCriteria(final JeopNoteSearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }

    public List<JeopNote> findByJeopId(final Long jeopId) {
        return getDao().findByJeopId(jeopId);
    }
}
