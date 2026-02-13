package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.company.CompanyView;
import com.endeavorms.velocity.qto.company.CompanyViewManager;
import com.endeavorms.velocity.qto.company.CompanyViewSearchCriteria;
import com.endeavorms.velocity.qto.company.MasterCustomerWorklistMeta;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/companyViews")
@PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
public class CompanyViewResource extends AbstractResource<CompanyView> {

    @Override
    protected String getResourcePath() {
        return "/companyViews";
    }

    @Autowired
    private CompanyViewManager companyViewManager;

    @GetMapping
    public ResponseEntity<?> findBySearchCriteria(@ModelAttribute final CompanyViewSearchCriteria criteria) {
        PaginatedResult<CompanyView> result = companyViewManager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(CompanyViewResource.class));
    }

    @GetMapping("/meta")
    public ResponseEntity<?> getMasterCustomerWorklistMeta(@ModelAttribute final CompanyViewSearchCriteria criteria) {
        MasterCustomerWorklistMeta meta = companyViewManager.getMasterCustomerWorklistMeta(criteria);
        return ResponseEntity.ok(meta);
    }
}
