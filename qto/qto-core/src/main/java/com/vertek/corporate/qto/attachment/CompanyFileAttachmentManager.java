package com.vertek.corporate.qto.attachment;

import com.vertek.corporate.qto.common.AbstractFileAttachmentManager;
import com.vertek.corporate.qto.common.PaginatedResult;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
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
