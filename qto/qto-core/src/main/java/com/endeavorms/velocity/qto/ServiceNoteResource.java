package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.note.NoteUnionView;
import com.endeavorms.velocity.qto.note.NoteUnionViewSearchCriteria;
import com.endeavorms.velocity.qto.note.ServiceNote;
import com.endeavorms.velocity.qto.note.ServiceNoteManager;
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
 * @author llevit
 * @since 1/16/2023
 */
@RestController
@RequestMapping("/api/serviceNotes")
public class ServiceNoteResource extends AbstractResource<NoteUnionView> {

    @Override
    protected String getResourcePath() {
        return "/serviceNotes";
    }

    @Autowired
    private ServiceNoteManager manager;

    @Autowired
    private SubjectManager subjectManager;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getServiceNotes(@ModelAttribute final NoteUnionViewSearchCriteria criteria) {
        PaginatedResult<NoteUnionView> result = manager.findBySearchCriteria(criteria, false);
        return getCollectionResource(result, criteria, getLocation(ServiceNoteResource.class));
    }

    @GetMapping("/audit")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getServiceAuditNotes(@ModelAttribute final NoteUnionViewSearchCriteria criteria) {
        PaginatedResult<NoteUnionView> result = manager.findBySearchCriteria(criteria, true);
        return getCollectionResource(result, criteria, getLocation(ServiceNoteResource.class));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> create(@RequestBody final ServiceNote note) {
        try {
            PreconditionsUtil.checkArgument(note.getServiceId(), "A serviceId is required");
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
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final ServiceNote note) {
        try {
            PreconditionsUtil.checkArgument(note.getId(), "A note ID is required");
            ServiceNote existing = manager.retrieve(note.getId());
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
