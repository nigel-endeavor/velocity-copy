package com.endeavorms.velocity.qto.note;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author fcurran
 * @since 9/28/2023
 */
@RestController
@RequestMapping("/api/disputeNotes")
public class DisputeNoteResource extends AbstractResource<DisputeNote> {

    @Override
    protected String getResourcePath() {
        return "/disputeNotes";
    }

    @Autowired
    private DisputeNoteManager manager;

    @Autowired
    private SubjectManager subjectManager;

    @GetMapping
    public ResponseEntity<?> getDisputeNotes(@ModelAttribute final DisputeNoteSearchCriteria criteria) {
        PaginatedResult<DisputeNote> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(DisputeNoteResource.class));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('inventory:write')")
    public ResponseEntity<?> create(@RequestBody final DisputeNote note) {
        try {
            PreconditionsUtil.checkArgument(note.getDisputeId(), "A disputeId is required");
            String loggedInUser = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(loggedInUser);
            loggedInUser = subject.getDisplayName();
            note.setCreatedById(subject.getId());
            note.setCreatedBy(loggedInUser);
            note.setCreatedDate(new Date());
            return ResponseEntity.ok(manager.create(note));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final DisputeNote note) {
        try {
            PreconditionsUtil.checkArgument(note.getId(), "A note ID is required");
            DisputeNote existing = manager.retrieve(note.getId());
            if (!manager.determineEditability(existing)) {
                return ResponseEntity.internalServerError().body("You do not have permission to edit this note.");
            }
            String loggedInUser = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(loggedInUser);
            loggedInUser = subject.getDisplayName();
            note.setEditedBy(loggedInUser);
            note.setEditedDate(new Date());
            return ResponseEntity.ok(manager.edit(note));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
