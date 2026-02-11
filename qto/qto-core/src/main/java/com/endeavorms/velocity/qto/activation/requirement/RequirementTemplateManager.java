package com.endeavorms.velocity.qto.activation.requirement;

import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.common.lookup.LookupValueManager;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author rcasey
 * @since 3/3/2023
 */
@Component
public class RequirementTemplateManager extends StandardManager<RequirementTemplate> {

    /**
     * Persistence tier for RequirementTemplate.
     */
    @Inject
    private RequirementTemplateJpaDao dao;

    @Inject
    private LookupValueManager lookupValueManager;

    @Inject
    private CompanyManager companyManager;

    @Override
    protected RequirementTemplateJpaDao getDao() {
        return dao;
    }

    public List<RequirementTemplate> findByCompanyId(final Long companyId, final boolean showInactive) {
        return dao.findByCompanyId(companyId, showInactive);
    }

    public RequirementTemplate getDefaultTemplate(final Long companyId) {
        return dao.getDefaultTemplate(companyId);
    }

    @Override
    public RequirementTemplate create(final RequirementTemplate entity) {
        Company company = companyManager.retrieve(entity.getCompanyId());
        entity.setTenantId(company.getTenantId());
        entity.setMasterCustomerId(company.getMasterCustomerId());
        List<Requirement> requirements = new ArrayList<>(entity.getRequirements());
        entity.setRequirements(Collections.EMPTY_LIST);
        RequirementTemplate created = super.create(entity);
        for (Requirement requirement : requirements) {
            requirement.setRequirementTemplateId(created.getId());
            requirement.setLookupValue(lookupValueManager.retrieve(requirement.getLookupValue().getId()));
            created.getRequirements().add(requirement);
        }
        return super.edit(created);
    }

    @Override
    public RequirementTemplate edit(final RequirementTemplate entity) {
        Company company = companyManager.retrieve(entity.getCompanyId());
        entity.setTenantId(company.getTenantId());
        entity.setMasterCustomerId(company.getMasterCustomerId());
        for (Requirement requirement : entity.getRequirements()) {
            requirement.setRequirementTemplateId(entity.getId());
            requirement.setLookupValue(lookupValueManager.retrieve(requirement.getLookupValue().getId()));
        }
        return super.edit(entity);
    }
}
