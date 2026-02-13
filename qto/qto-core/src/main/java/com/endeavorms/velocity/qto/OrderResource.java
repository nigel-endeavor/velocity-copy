package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.order.Order;
import com.endeavorms.velocity.qto.order.OrderManager;
import com.endeavorms.velocity.qto.order.dto.OrderCreateDtoWrapper;
import com.endeavorms.velocity.qto.common.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * @author rcasey
 * @since 1/10/2023
 */
@RestController
@RequestMapping("/api/orders")
public class OrderResource extends AbstractResource<Order> {

    @Override
    protected String getResourcePath() {
        return "/orders";
    }

    @Inject
    private OrderManager manager;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> retrieve(@PathVariable("id") final Long id) {
        try {
            Order retrieved = manager.retrieve(id);
            return ResponseEntity.ok(retrieved);
        } catch (Exception e) {
            String errorMessage = "Access denied: contact a platform admin to view this client group.";
            LOGGER.error(e.getMessage());
            return ResponseEntity.internalServerError().body(errorMessage);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('order:create')")
    public ResponseEntity<?> create(@RequestBody final OrderCreateDtoWrapper dtoWrapper) {
        try {
            OrderCreateDtoWrapper created = manager.createFromDto(dtoWrapper);
            return ResponseEntity.ok(created);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final Order order) {
        try {
            if (!id.equals(order.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            Order updated = manager.edit(order);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
