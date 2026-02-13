package com.endeavorms.velocity.qto.task;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.company.task.CompanyTask;
import com.endeavorms.velocity.qto.company.task.CompanyTaskManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * REST API for CompanyTask entities.
 */
@RestController
@RequestMapping("/api/companyTasks")
public class CompanyTaskResource extends AbstractResource<CompanyTask> {

    @Override
    protected String getResourcePath() {
        return "/companyTasks";
    }

    @Autowired
    private CompanyTaskManager manager;

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> updateCompanyTask(@PathVariable("id") final Long companyTaskId, @RequestBody final CompanyTask companyTask) {
        PreconditionsUtil.checkArgument(companyTaskId, "Customer Task ID is required");
        CompanyTask updatedCompanyTask = manager.edit(companyTask);
        return ResponseEntity.ok(updatedCompanyTask);
    }
}
