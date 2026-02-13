package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.message.Message;
import com.endeavorms.velocity.qto.message.MessageThread;
import com.endeavorms.velocity.qto.message.MessageThreadManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author rcasey
 * @since 4/26/2023
 */
@RestController
@RequestMapping("/api/messageThreads")
@PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
public class MessageThreadResource extends AbstractResource<MessageThread> {

    @Override
    protected String getResourcePath() {
        return "/messageThreads";
    }

    @Autowired
    private MessageThreadManager manager;

    @GetMapping
    public ResponseEntity<?> getMessageThreads(@RequestParam("locationId") final Long locationId) {
        List<MessageThread> messageThreads = manager.findByLocationId(locationId);
        return ResponseEntity.ok(messageThreads);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody final MessageThread messageThread) {
        try {
            MessageThread created = manager.create(messageThread);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> createMessage(@PathVariable("id") final Long messageThreadId, @RequestBody final Message message) {
        try {
            MessageThread updated = manager.createMessage(messageThreadId, message);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/subjects")
    public ResponseEntity<?> setSubjects(@PathVariable("id") final Long messageThreadId, @RequestBody final List<Integer> subjectIds) {
        try {
            MessageThread updated = manager.setSubjects(messageThreadId, subjectIds);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
