package com.vertek.corporate.qto.common;

import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;
import com.vertek.corporate.qto.subject.TenantSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TenantSubjectManager extends StandardManager<TenantSubject> {

    /** Logging Facade. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TenantSubjectManager.class);

    /** Data access for TenantSubject entities.*/
    @Inject
    private TenantSubjectJpaDao dao;

    @Inject
    private SubjectManager subjectManager;

    @Inject
    private TenantViewManager tenantViewManager;


    @Override
    protected TenantSubjectJpaDao getDao() {
        return dao;
    }

    /**
     * Gets the currently selected Tenant.
     * @return the currently selected Tenant.
     */
    public Tenant getCurrentTenant() {
        return getDao().getCurrentTenant();
    }

    public List<Long> getAllowedTenantIds() {
        return getDao().getAllowedTenantIds();
    }


    public void updateSelectedTenant(final String tenant) {
        TenantView tenantView = tenantViewManager.findByName(tenant);
        Subject subject = subjectManager.findByUsername(SecurityUtils.getLoggedInUser());
        getDao().updateSelectedTenant(subject.getId(), tenantView.getId());
    }
}

