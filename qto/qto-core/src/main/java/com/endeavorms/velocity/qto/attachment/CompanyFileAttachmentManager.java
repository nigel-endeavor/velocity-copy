package com.endeavorms.velocity.qto.attachment;

import com.endeavorms.velocity.qto.common.AbstractFileAttachmentManager;
import com.endeavorms.velocity.qto.common.PaginatedResult;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

@Component
public class CompanyFileAttachmentManager extends AbstractFileAttachmentManager<CompanyFileAttachment> {

    @Inject
    private CompanyFileAttachmentJpaDao dao;

    @Override
    protected CompanyFileAttachmentJpaDao getDao() {
        return dao;
    }

    public PaginatedResult<CompanyFileAttachment> findByCompanyId(final Long masterCustomerId) {
        return getDao().findByCompanyId(masterCustomerId);
    }
}
