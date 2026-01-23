package com.vertek.corporate.qto.note;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.dispute.DisputeManager;
import com.vertek.corporate.qto.subject.Subject;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author fcurran
 * @since 9/25/2023
 */
@Stateless
public class DisputeNoteManager extends AbstractNoteManager<DisputeNote> {

    /**
     * Persistence tier for DisputeNotes.
     */
    @Inject
    private DisputeNoteJpaDao dao;

    /** Manager for disputes. */
    @Inject
    private DisputeManager disputeManager;

    @Override
    protected DisputeNoteJpaDao getDao() {
        return dao;
    }

    public PaginatedResult<DisputeNote> findBySearchCriteria(final DisputeNoteSearchCriteria criteria) {
        PaginatedResult<DisputeNote> results = getDao().findBySearchCriteria(criteria);

        for (DisputeNote note : results.getCollection()) {
            note.setEditable(determineEditability(note));
        }
        return results;
    }

    @Override
    protected StandardManager getParentManager() {
        return disputeManager;
    }

    @Override
    protected Long getOwnerEntityId(final DisputeNote note) {
        return note.getDisputeId();
    }

    @Override
    public boolean determineEditability(final DisputeNote note) {
        boolean editable = false;
        if (org.apache.shiro.SecurityUtils.getSubject().isPermitted(Permissions.ADMIN)
                || org.apache.shiro.SecurityUtils.getSubject().isPermitted(Permissions.INVENTORY_WRITE)) {
            if (org.apache.shiro.SecurityUtils.getSubject().isPermitted(Permissions.ADMIN)
                    || org.apache.shiro.SecurityUtils.getSubject().isPermitted(Permissions.INVENTORY_WRITE)) {
                editable = true;
            }
        }
        if (!editable) {
            //can use the logged-in username to find the subject
            String username = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(username);
            //need to use the subject id because the subject's name could be changed
            if (note.getCreatedById() != null && note.getCreatedById().equals(subject.getId())) {
                editable = true;
            }
        }
        return editable;
    }

    public List<DisputeNote> findByDisputeId(final Long disputeId) {
        return dao.findByDisputeId(disputeId);
    }
}
