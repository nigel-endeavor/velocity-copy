package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.common.TenantSubjectManager;
import com.endeavorms.velocity.qto.common.TenantView;
import com.endeavorms.velocity.qto.common.TenantViewManager;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import com.endeavorms.velocity.qto.subject.SubjectSearchCriteria;
import com.endeavorms.velocity.qto.subject.TenantSubject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subjects")
@PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
public class SubjectResource extends AbstractResource<TenantSubject> {

    @Override
    protected String getResourcePath() {
        return "/subjects";
    }

    @Inject
    private SubjectManager manager;

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    @Inject
    private TenantViewManager tenantViewManager;

    @GetMapping
    public ResponseEntity<List<Subject>> getSubjects(@ModelAttribute final SubjectSearchCriteria criteria) {
        List<Subject> subjects = manager.findAll(criteria);
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/me")
    public ResponseEntity<Subject> me() {
        Subject me = manager.findByUsername(SecurityUtils.getLoggedInUser());
        return ResponseEntity.ok(me);
    }

    @GetMapping("/me/tenantAccess")
    public ResponseEntity<Boolean> myTenantAccess() {
        List<Long> tenantIds = tenantSubjectManager.getAllowedTenantIds();
        return ResponseEntity.ok(!tenantIds.isEmpty());
    }

    @PostMapping("/setTenant/{tenant}")
    @PreAuthorize("hasAuthority('change-tenant')")
    public ResponseEntity<?> setTenant(@PathVariable("tenant") final String tenant) {
        tenantSubjectManager.updateSelectedTenant(tenant);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tenants")
    @PreAuthorize("hasAuthority('change-tenant')")
    public ResponseEntity<List<String>> getTenants() {
        List<String> tenants = tenantViewManager.getAllTenants().stream().map(TenantView::getName).collect(Collectors.toList());
        return ResponseEntity.ok(tenants);
    }
}
