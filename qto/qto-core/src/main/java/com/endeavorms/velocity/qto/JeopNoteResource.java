package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.note.JeopNote;
import com.endeavorms.velocity.qto.note.JeopNoteManager;
import com.endeavorms.velocity.qto.note.JeopNoteSearchCriteria;
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
@RequestMapping("/api/jeopNotes")
public class JeopNoteResource extends AbstractResource<JeopNote> {

    @Override
    protected String getResourcePath() {
        return "/jeopNotes";
    }

    @Autowired
    private JeopNoteManager manager;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getJeopNotes(@ModelAttribute final JeopNoteSearchCriteria criteria) {
        PaginatedResult<JeopNote> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(JeopNoteResource.class));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> create(@RequestBody final JeopNote note) {
        try {
            if (note.getJeopInstanceId() == null) {
                throw new IllegalArgumentException("Missing jeopId.");
            }
            JeopNote returnedNote = manager.create(note);
            return ResponseEntity.ok(returnedNote);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
