package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.company.CompanySearchCriteria;
import com.endeavorms.velocity.qto.company.task.CompanyTaskManager;
import com.endeavorms.velocity.qto.interceptors.CompanyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author rcasey
 * @since 1/19/2023
 */
@RestController
@RequestMapping("/api/companies")
public class CompanyResource extends AbstractResource<Company> {

    @Override
    protected String getResourcePath() {
        return "/companies";
    }

    @Autowired
    private CompanyManager manager;

    @Autowired
    private CompanyTaskManager companyTaskManager;

    @Autowired
    private CompanyInterceptor companyInterceptor;

    @GetMapping
    public ResponseEntity<?> getCompanies(@ModelAttribute final CompanySearchCriteria criteria) {
        PaginatedResult<Company> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(CompanyResource.class));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> create(@RequestBody final Company company) {
        try {
            Company created = manager.create(company);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('tenant-admin')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final Company company) {
        try {
            if (!id.equals(company.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            Company updated = manager.edit(company);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> retrieve(@PathVariable("id") final Long id) {
        Company retrieved = manager.retrieve(id);
        return ResponseEntity.ok(retrieved);
    }

    @GetMapping("/{companyId}/tasks")
    public ResponseEntity<?> getCompanyTasks(@PathVariable("companyId") final Long companyId) {
        return ResponseEntity.ok(companyTaskManager.findByCompanyId(companyId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('tenant-admin')")
    public ResponseEntity<?> delete(@PathVariable("id") final Long id) {
        Optional<BadRequestError> validationError = companyInterceptor.validateCompanyDelete(id);
        if (validationError.isPresent()) {
            return ResponseEntity.badRequest().body(validationError.get());
        }
        manager.remove(id);
        return ResponseEntity.ok().build();
    }
}
