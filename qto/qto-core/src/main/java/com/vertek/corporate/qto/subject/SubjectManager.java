package com.vertek.corporate.qto.subject;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.common.TenantSubjectManager;
import com.vertek.corporate.qto.order.Order;
import com.vertek.corporate.qto.order.OrderManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class SubjectManager extends StandardManager<Subject> {
    @Inject
    private OrderManager orderManager;
    @Inject
    private TenantSubjectManager tenantSubjectManager;
    @Inject
    private CompanySubjectManager companySubjectManager;
    @Inject
    private SubjectJpaDao dao;

    @Override
    protected SubjectJpaDao getDao(){
        return dao;
    }

    /**
     * Gets the Subject with the given username.
     * @param username the Subject's username.
     * @return the Subject with the given username.
     */
    public Subject findByUsername(final String username) {
        PreconditionsUtil.checkArgument(username, "A username is required");
        Subject subject = getDao().findByUsername(username);
        if (subject == null) {
            throw new IllegalArgumentException("No account was found for the given username: " + username);
        }
        return subject;
    }

    public Subject findByEmailAddress(final String emailAddress) {
        PreconditionsUtil.checkArgument(StringUtils.isNotBlank(emailAddress), "An email address is required");
        Subject subject = getDao().findByEmailAddress(emailAddress);
        if (subject == null) {
            throw new IllegalArgumentException("No account was found for the given email address: " + emailAddress);
        }
        return subject;
    }

    /**
     * Gets the Subject with the given displayName.
     * @param displayName the Subject's displayName.
     * @return the Subject with the given displayName.
     */
    public Subject findByDisplayName(final String displayName) {
        PreconditionsUtil.checkArgument(displayName, "A displayName is required");
        Subject subject = getDao().findByDisplayName(displayName);
        if (subject == null) {
            throw new IllegalArgumentException("No account was found for the given displayName: " + displayName);
        }
        return subject;
    }

    /**
     * Gets the Subject with the given displayName and tenant ID.
     * @param displayName the Subject's displayName.
     * @param tenantId the tenant ID.
     * @return the Subject with the given displayName.
     */
    public Subject findByDisplayNameAndTenantId(final String displayName, final Long tenantId) {
        PreconditionsUtil.checkArgument(displayName, "A displayName is required");
        PreconditionsUtil.checkArgument(tenantId, "A tenant ID is required");
        return getDao().findByDisplayNameAndTenantId(displayName, tenantId);
    }

    /**
     * Gets a List of Subjects for the given criteria.
     * @param criteria the criteria to filter by.
     * @return a List of Subjects for the given criteria.
     */
    public List<Subject> findAll(final SubjectSearchCriteria criteria) {
        List<Long> tenantIds = new ArrayList<>();
        if (criteria.getOrderId() != null) {
            Order order = orderManager.retrieve(criteria.getOrderId());
            tenantIds.add(order.getTenantId());
        } else {
            tenantIds = tenantSubjectManager.getAllowedTenantIds();
            tenantIds.addAll(companySubjectManager.getAllowedMasterCustomerTenantIds());
        }
        return dao.findByTenantIds(tenantIds, criteria);
    }

    public void loadGroups() {
        getDao().loadGroups();
    }

        /**
     * Gets the Subject with the given subject id.
     *
     * @param subjectId the Subject's ID.
     * @return the Subject with the given ID.
     */
    public Subject findBySubjectId(final Long subjectId) {
        return getDao().findBySubjectId(subjectId);
    }
}
