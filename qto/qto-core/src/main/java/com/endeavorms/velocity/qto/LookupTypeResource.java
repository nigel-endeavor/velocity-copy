package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.lookup.LookupType;
import com.endeavorms.velocity.qto.common.lookup.LookupTypeManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * @author rcasey
 * @since 1.5.0
 */
@Component
@RestController
@RequestMapping("/api/lookupTypes")
@PreAuthorize("hasAuthority('lookup-admin')")
public class LookupTypeResource extends AbstractResource<LookupType> {

    @Override
    protected String getResourcePath() {
        return "/lookupTypes";
    }

    @Inject
    private LookupTypeManager manager;

    @GetMapping
    public ResponseEntity<?> listModifiableAlphabetically(
            @RequestParam(defaultValue = "0") final int offset,
            @RequestParam(defaultValue = "100") final int limit) {
        try {
            return ResponseEntity.ok(manager.listModifiableAlphabetically(offset, limit));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> setValues(@PathVariable("id") final Long id, @RequestBody final LookupType type) {
        try {
            manager.setValues(type);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
