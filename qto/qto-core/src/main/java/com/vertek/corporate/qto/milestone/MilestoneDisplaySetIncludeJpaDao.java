package com.vertek.corporate.qto.milestone;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
