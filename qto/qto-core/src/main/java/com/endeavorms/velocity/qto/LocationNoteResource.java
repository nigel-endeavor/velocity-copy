package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.note.LocationNote;
import com.endeavorms.velocity.qto.note.LocationNoteManager;
import com.endeavorms.velocity.qto.note.NoteUnionView;
import com.endeavorms.velocity.qto.note.NoteUnionViewSearchCriteria;
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
@RequestMapping("/api/locationNotes")
public class LocationNoteResource extends AbstractResource<NoteUnionView> {

    @Override
    protected String getResourcePath() {
        return "/locationNotes";
    }

    @Autowired
    private LocationNoteManager manager;

    @Autowired
    private SubjectManager subjectManager;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getLocationNotes(@ModelAttribute final NoteUnionViewSearchCriteria criteria) {
        PaginatedResult<NoteUnionView> result = manager.findBySearchCriteria(criteria, false);
        return getCollectionResource(result, criteria, getLocation(LocationNoteResource.class));
    }

    @GetMapping("/audit")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getLocationAuditNotes(@ModelAttribute final NoteUnionViewSearchCriteria criteria) {
        PaginatedResult<NoteUnionView> result = manager.findBySearchCriteria(criteria, true);
        return getCollectionResource(result, criteria, getLocation(LocationNoteResource.class));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> create(@RequestBody final LocationNote note) {
        try {
            PreconditionsUtil.checkArgument(note.getLocationId(), "A locationId is required");
            String loggedInUser = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(loggedInUser);
            loggedInUser = subject.getDisplayName();
            note.setCreatedById(subject.getId());
            note.setCreatedBy(loggedInUser);
            note.setCreatedDate(new Date());
            return ResponseEntity.ok(manager.create(note));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final LocationNote note) {
        try {
            PreconditionsUtil.checkArgument(note.getId(), "A note ID is required");
            LocationNote existing = manager.retrieve(note.getId());
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
