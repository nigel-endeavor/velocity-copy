package com.vertek.corporate.qto.subject;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.common.AbstractJpaDao;
import com.vertek.corporate.qto.common.PlatformDatabase;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.graph.AzureADGroups;
import com.vertek.corporate.qto.graph.MSGraph;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.vertek.corporate.qto.subject.QTenantSubject.tenantSubject;
import static com.vertek.corporate.qto.subject.QSubject.subject;

@Stateless
public class SubjectJpaDao extends AbstractJpaDao<Subject, Long> {

    @Inject
    private MSGraph msGraph;

    /** Expression for excluding Developers from query to Subjects. */
    private BooleanExpression isNotDeveloper;
    /** Expression for excluding Readonly Ordering Users from query to Subjects. */
    private BooleanExpression isOrderReadOnly;
    /** Expression for capturing Inventory write Users. */
    private BooleanExpression isInventoryWriteUser;


    @PostConstruct
    public void init(){
        loadGroups();
    }

    public void loadGroups() {
        List<String> developers = (msGraph.getGroups().get(AzureADGroups.DEVELOPER.getName()) == null) ? new ArrayList<>() : msGraph.getGroups().get(AzureADGroups.DEVELOPER.getName());
        List<String> orderWriteUsers = (msGraph.getGroups().get(AzureADGroups.ORDER_WRITE.getName()) == null) ? new ArrayList<>() : msGraph.getGroups().get(AzureADGroups.ORDER_WRITE.getName());
        List<String> orderReadUsers = (msGraph.getGroups().get(AzureADGroups.ORDER_READ.getName()) == null) ? new ArrayList<>() : msGraph.getGroups().get(AzureADGroups.ORDER_READ.getName());
        List<String> inventoryWriteUsers = (msGraph.getGroups().get(AzureADGroups.INVENTORY_WRITE.getName()) == null) ? new ArrayList<>() : msGraph.getGroups().get(AzureADGroups.INVENTORY_WRITE.getName());

        //get the list of read users that aren't also write users
        isOrderReadOnly = subject.emailAddress.in(orderReadUsers).and(subject.emailAddress.notIn(orderWriteUsers));
        isNotDeveloper = subject.emailAddress.notIn(developers);
        //get the list of inventory write users
        isInventoryWriteUser = inventoryWriteUsers.isEmpty() ? subject.id.isNotNull() : subject.emailAddress.in(inventoryWriteUsers);
    }

    @Override
    @Inject
    protected void setEntityManager(@PlatformDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Gets a List of Subjects for the given tenantIds.
     * Filters out Developers and Readonly Users.
     *
     * @param tenantIds the tenantIds to filter by.
     * @param criteria additional criteria to filter by.
     * @return a List of Subjects for the given tenantIds.
     */
    public List<Subject> findByTenantIds(final List<Long> tenantIds, final SubjectSearchCriteria criteria) {
        return new JPAQuery<Subject>(entityManager)
                .from(subject)
                .join(tenantSubject).on(tenantSubject.subject.id.eq(subject.id))
                .where(getExpression(tenantIds, criteria))
                .orderBy(subject.displayName.asc())
                .groupBy(subject.id)
                .fetch();
    }

    /**
     * Gets the where clause for a given SubjectSearchCriteria and specific tenantIds.
     *
     * @param tenantIds the tenantIds to filter by.
     * @param criteria  the criteria to filter by.
     * @return relevant results.
     */
    public Predicate getExpression(final List<Long> tenantIds, final SubjectSearchCriteria criteria) {
        BooleanExpression expression = tenantSubject.tenant.id.in(tenantIds)
                .and(isNotDeveloper);

        if (Boolean.TRUE.equals(criteria.getIsInventoryWrite())) {
            expression = expression.and(isInventoryWriteUser);

        } else {
            expression = expression.and(isOrderReadOnly.not());
        }
        expression = getContainsExpression(expression, subject.displayName, criteria.getName());
        return expression;
    }

    /**
     * Gets the Subject with the given username.
     *
     * @param username the Subject's username.
     * @return the Subject with the given username.
     */
    public Subject findByUsername(final String username) {
        PreconditionsUtil.checkArgument(username, "A username is required");
        return new JPAQuery<Subject>(entityManager)
                .from(subject)
                .where(subject.emailAddress.eq(username))
                .fetchOne();
    }

    /**
     * Gets the Subject with the given subject id.
     *
     * @param subjectId the Subject's ID.
     * @return the Subject with the given ID.
     */
    public Subject findBySubjectId(final Long subjectId) {
        PreconditionsUtil.checkArgument(subjectId, "A subject ID is required");
        return new JPAQuery<Subject>(entityManager)
                .from(subject)
                .where(subject.id.eq(subjectId))
                .fetchOne();
    }

    /**
     * Gets the Subject with the given emailAddress.
     *
     * @param emailAddress the Subject's emailAddress.
     * @return the Subject with the given emailAddress.
     */
    public Subject findByEmailAddress(final String emailAddress) {
        PreconditionsUtil.checkArgument(emailAddress, "An email Address is required");
        return new JPAQuery<Subject>(entityManager)
                .from(subject)
                .where(subject.emailAddress.eq(emailAddress))
                .fetchOne();
    }

    /**
     * Gets the Subject with the given display name.
     *
     * @param displayName the Subject's display name.
     * @return the Subject with the given display name.
     */
    public Subject findByDisplayName(final String displayName) {
        PreconditionsUtil.checkArgument(displayName, "A displayName is required");
        return new JPAQuery<Subject>(entityManager)
                .from(subject)
                .where(subject.displayName.eq(displayName))
                .fetchOne();
    }

    /**
     * Gets the Subject with the given display name and tenant ID.
     *
     * @param displayName the Subject's display name.
     * @param tenantId the tenant ID.
     * @return the Subject with the given display name.
     */
    public Subject findByDisplayNameAndTenantId(final String displayName, final Long tenantId) {
        PreconditionsUtil.checkArgument(displayName, "A displayName is required");
        PreconditionsUtil.checkArgument(tenantId, "A tenant ID is required");
        return new JPAQuery<Subject>(entityManager)
                .from(subject)
                .join(tenantSubject).on(tenantSubject.subject.id.eq(subject.id))
                .where(subject.displayName.eq(displayName).and(tenantSubject.tenant.id.eq(tenantId)))
                .fetchOne();
    }
}
