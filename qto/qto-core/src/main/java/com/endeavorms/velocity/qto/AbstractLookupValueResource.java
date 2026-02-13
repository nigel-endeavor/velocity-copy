package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.authentication.Permissions;
import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.NoCacheResponse;
import com.endeavorms.velocity.qto.common.lookup.AbstractLookupValueManager;
import com.endeavorms.velocity.qto.common.lookup.LookupValue;
import com.endeavorms.velocity.qto.common.lookup.LookupValueSearchCriteria;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Base service class for LookupValues.
 * @author rconnolly
 * @since 1.1.0
 * @param <T> a LookupValue Type.
 */
public abstract class AbstractLookupValueResource<T extends LookupValue> extends AbstractResource<T> {

    protected static final String PERMISSION = Permissions.USER;

    protected abstract AbstractLookupValueManager<T> getManager();

    @Override
    protected abstract String getResourcePath();

    @GetMapping
    public ResponseEntity<?> findBySearchCriteria(@ModelAttribute final LookupValueSearchCriteria criteria) {
        try {
            return NoCacheResponse.ok(getManager().findBySearchCriteria(criteria));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(getManager().retrieve(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final T entity) {
        if (!id.equals(entity.getId())) {
            throw new IllegalArgumentException("identifier in path does not match that of passed entity");
        }
        T updated = getManager().edit(entity);
        return ResponseEntity.ok(updated);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> create(@RequestBody final T entity) {
        return ResponseEntity.ok(getManager().create(entity));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> remove(@PathVariable("id") final Long id) {
        getManager().remove(id);
        return ResponseEntity.noContent().build();
    }
}
