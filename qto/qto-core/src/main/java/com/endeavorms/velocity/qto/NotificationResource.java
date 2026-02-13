package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.notification.Notification;
import com.endeavorms.velocity.qto.notification.NotificationManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * @author rcasey
 * @since 6/16/2023
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationResource extends AbstractResource<Notification> {

    @Override
    protected String getResourcePath() {
        return "/notifications";
    }

    @Inject
    private NotificationManager manager;

    @PostMapping("/{id}/dismiss")
    public ResponseEntity<?> dismiss(@PathVariable("id") final Long id) {
        try {
            manager.dismiss(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/dismissAll")
    public ResponseEntity<?> dismissAll() {
        try {
            manager.dismissAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('*')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody final Notification notification) {
        try {
            Notification created = manager.create(notification);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
