package com.endeavorms.velocity.qto.common;

import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import com.endeavorms.velocity.qto.subject.TenantSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
public class TenantSubjectManager extends StandardManager<TenantSubject> {

    /** Logging Facade. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TenantSubjectManager.class);

    /** Data access for TenantSubject entities.*/
    @Autowired
    private TenantSubjectJpaDao dao;

    @Autowired
    private SubjectManager subjectManager;

    @Autowired
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

