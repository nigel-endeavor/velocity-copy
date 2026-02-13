package com.endeavorms.velocity.qto.task;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.company.task.TaskGroup;
import com.endeavorms.velocity.qto.company.task.TaskGroupManager;
import com.endeavorms.velocity.qto.company.task.TaskGroupSearchCriteria;
import com.endeavorms.velocity.qto.interceptors.TaskGroupInterceptor;
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

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fcurran
 * @since 9/5/2024
 */
@RestController
@RequestMapping("/api/taskGroups")
public class TaskGroupResource extends AbstractResource<TaskGroup> {

    @Override
    protected String getResourcePath() {
        return "/taskGroups";
    }

    @Autowired
    private TaskGroupManager manager;

    @Autowired
    private TaskGroupInterceptor taskGroupInterceptor;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getTaskGroups(@ModelAttribute final TaskGroupSearchCriteria criteria) {
        PaginatedResult<TaskGroup> templates = manager.findBySearchCriteria(criteria);
        return getCollectionResource(templates, criteria, getLocation(TaskGroupResource.class));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> retrieve(@PathVariable("id") final Long id) {
        TaskGroup retrieved = manager.retrieve(id);
        return ResponseEntity.ok(retrieved);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> create(@RequestBody final TaskGroup template) {
        try {
            BadRequestError validationError = taskGroupInterceptor.validateForCreateOrEdit(template);
            if (validationError != null) {
                return ResponseEntity.badRequest().body(validationError);
            }
            TaskGroup created = manager.create(template);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final TaskGroup template) {
        try {
            BadRequestError validationError = taskGroupInterceptor.validateForCreateOrEdit(template);
            if (validationError != null) {
                return ResponseEntity.badRequest().body(validationError);
            }
            if (!id.equals(template.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            TaskGroup updated = manager.edit(template);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> remove(@PathVariable("id") final Long id) {
        try {
            BadRequestError validationError = taskGroupInterceptor.validateForRemove(id);
            if (validationError != null) {
                return ResponseEntity.badRequest().body(validationError);
            }
            manager.remove(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
