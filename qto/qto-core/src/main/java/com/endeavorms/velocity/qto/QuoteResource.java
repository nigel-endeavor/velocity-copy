package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.quote.Quote;
import com.endeavorms.velocity.qto.quote.QuoteManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * REST resource for Quote management.
 */
@RestController
@RequestMapping("/api/quotes")
public class QuoteResource extends AbstractResource<Quote> {

    @Override
    protected String getResourcePath() {
        return "/quotes";
    }

    @Inject
    private QuoteManager manager;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> list(
            @RequestParam(defaultValue = "0") final int offset,
            @RequestParam(defaultValue = "25") final int limit) {
        try {
            PaginatedResult<Quote> result = manager.list(offset, limit);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            LOGGER.error("Error listing quotes", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> retrieve(@PathVariable("id") final Long id) {
        try {
            Quote retrieved = manager.retrieve(id);
            return ResponseEntity.ok(retrieved);
        } catch (Exception e) {
            LOGGER.error("Error retrieving quote " + id, e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
