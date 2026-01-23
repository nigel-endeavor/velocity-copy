package com.vertek.corporate.qto.milestone;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.common.AbstractJpaDao;
import com.vertek.corporate.qto.common.PreconditionsUtil;

import java.util.List;
import java.util.Locale;

import static com.vertek.corporate.qto.milestone.QMilestone.milestone;

/**
 * Base JPA DAO for Milestone entities.
 *
 * @author rconnolly
 * @since 1.0
 *
 * @param <T> a Milestone Type.
 */
public abstract class AbstractMilestoneJpaDao<T extends Milestone> extends AbstractJpaDao<T, Long> {

    /**
     * Fetches a Milestone by code.
     * @param code the Milestone's code.
     * @return a Milestone with the given code or null should one not be found.
     */
    public Milestone retrieveByCode(final String code) {
        PreconditionsUtil.checkArgument(code, "Milestone code is required");

        JPAQuery<Milestone> query = new JPAQuery<Milestone>(entityManager)
                .from(milestone)
                .where(milestone.code.lower().eq(code.toLowerCase(Locale.getDefault())));

        Milestone existingMilestone = null;
        List<Milestone> milestones = query.fetch();
        if (milestones.size() > 0) {
            existingMilestone = milestones.get(0);
        }
        return existingMilestone;
    }
}