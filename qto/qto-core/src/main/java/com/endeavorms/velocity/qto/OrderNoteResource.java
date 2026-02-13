package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.note.OrderNote;
import com.endeavorms.velocity.qto.note.OrderNoteManager;
import com.endeavorms.velocity.qto.note.OrderNoteSearchCriteria;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author llevit
 * @since 1/16/2023
 */
@RestController
@RequestMapping("/api/orderNotes")
public class OrderNoteResource extends AbstractResource<OrderNote> {

    @Override
    protected String getResourcePath() {
        return "/orderNotes";
    }

    @Autowired
    private OrderNoteManager manager;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getOrderNotes(@ModelAttribute final OrderNoteSearchCriteria criteria) {
        PaginatedResult<OrderNote> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(OrderNoteResource.class));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> create(@RequestBody final OrderNote note) {
        try {
            if (note.getOrderId() == null) {
                throw new IllegalArgumentException("Missing orderId.");
            }
            OrderNote returnedNote = manager.create(note);
            return ResponseEntity.ok(returnedNote);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
