package com.endeavorms.velocity.qto.milestone;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.endeavorms.velocity.qto.milestone.QMilestoneDisplaySetInclude.milestoneDisplaySetInclude;

@Component
public class MilestoneDisplaySetIncludeJpaDao extends AbstractJpaDao<MilestoneDisplaySetInclude, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
