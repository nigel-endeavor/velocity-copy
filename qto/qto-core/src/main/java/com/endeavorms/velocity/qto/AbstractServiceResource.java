package com.endeavorms.velocity.qto;

import com.google.common.base.Strings;
import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.interceptors.ServiceDeleteInterceptor;
import com.endeavorms.velocity.qto.interceptors.ServiceInterceptor;
import com.endeavorms.velocity.qto.service.AbstractServiceManager;
import com.endeavorms.velocity.qto.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

/**
 * The core operations that a Resource for a Service should perform.
 * @param <T> the type of Service.
 */
public abstract class AbstractServiceResource<T extends Service> extends AbstractResource<T> {
    @Autowired
    private ServiceInterceptor serviceInterceptor;
    @Autowired
    private ServiceDeleteInterceptor serviceDeleteInterceptor;

    /** Subclasses must return their path, e.g. "/broadbandService". */
    @Override
    protected abstract String getResourcePath();
    /**
     * The manager for the concrete Resource.
     * @return an injected business logic tier for the given Service of type <T>.
     */
    protected abstract AbstractServiceManager<T> getManager();

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> retrieve(@PathVariable("id") final Long id) {
        T retrieved = getManager().retrieve(id);
        return ResponseEntity.ok(retrieved);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> create(@RequestBody final T entity) {
        Optional<BadRequestError> validationError = serviceInterceptor.validateService(entity);
        if (validationError.isPresent()) {
            return ResponseEntity.badRequest().body(validationError.get());
        }
        try {
            if (Strings.isNullOrEmpty(entity.getRecordSource())) {
                entity.setRecordSource(RecordSource.MANUAL_ENTRY.getName());
            }
            T created = getManager().create(entity);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final T entity) {
        Optional<BadRequestError> validationError = serviceInterceptor.validateService(entity);
        if (validationError.isPresent()) {
            return ResponseEntity.badRequest().body(validationError.get());
        }
        try {
            if (!id.equals(entity.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            T updated = getManager().edit(entity);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/cloneAndCancel")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> cloneAndCancel(@RequestBody final Service entityToClone) {
        try {
            T created = getManager().cloneAndCancel(entityToClone);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/pullFromCrm")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> pullFromCrm(@RequestBody final T entity) {
        try {
            T edited = getManager().pullFromCrm(entity);
            return ResponseEntity.ok(edited);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> delete(@PathVariable("id") final Long id) {
        Optional<BadRequestError> validationError = serviceDeleteInterceptor.validateServiceDelete(id);
        if (validationError.isPresent()) {
            return ResponseEntity.badRequest().body(validationError.get());
        }
        getManager().markForDeletion(id);
        return ResponseEntity.ok().build();
    }
}
