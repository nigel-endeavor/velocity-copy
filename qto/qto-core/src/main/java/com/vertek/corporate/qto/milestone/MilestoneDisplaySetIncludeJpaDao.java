package com.vertek.corporate.qto.milestone;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.vertek.corporate.qto.milestone.QMilestoneDisplaySetInclude.milestoneDisplaySetInclude;

@Stateless
public class MilestoneDisplaySetIncludeJpaDao extends AbstractJpaDao<MilestoneDisplaySetInclude, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
