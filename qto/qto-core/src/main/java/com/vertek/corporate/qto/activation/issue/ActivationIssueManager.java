package com.vertek.corporate.qto.activation.issue;

import com.vertek.corporate.qto.activation.attempt.ActivationAttempt;
import com.vertek.corporate.qto.activation.attempt.ActivationAttemptManager;
import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author rcasey
 * @since 3/29/2023
 */
@Stateless
public class ActivationIssueManager extends StandardManager<ActivationIssue> {

    /**
     * Persistence tier for ActivationIssues.
     */
    @Inject
    private ActivationIssueJpaDao dao;

    @Inject
    private ActivationAttemptManager activationAttemptManager;

    @Override
    protected ActivationIssueJpaDao getDao() {
        return dao;
    }

    /**
     * Finds all ActivationIssues with the given activation attempt id, ordered by rank.
     * @param attemptId activation attempt id
     * @return list of corresponding ActivationIssues
     */
    public List<ActivationIssue> findByAttemptId(final Long attemptId) {
        return dao.findByAttemptId(attemptId);
    }

    @Override
    public ActivationIssue create(final ActivationIssue entity) {
        ActivationAttempt attempt = activationAttemptManager.retrieve(entity.getActivationAttemptId());
        entity.setTenantId(attempt.getTenantId());
        entity.setMasterCustomerId(attempt.getMasterCustomerId());
        return super.create(entity);
    }

    @Override
    public ActivationIssue edit(final ActivationIssue entity) {
        ActivationAttempt attempt = activationAttemptManager.retrieve(entity.getActivationAttemptId());
        entity.setTenantId(attempt.getTenantId());
        entity.setMasterCustomerId(attempt.getMasterCustomerId());
        return super.edit(entity);
    }
}
