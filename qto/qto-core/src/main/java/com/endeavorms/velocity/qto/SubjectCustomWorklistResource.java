package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.subject.CustomWorklist;
import com.endeavorms.velocity.qto.subject.CustomWorklistManager;
import com.endeavorms.velocity.qto.subject.CustomWorklistSubjectDto;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectCustomWorklist;
import com.endeavorms.velocity.qto.subject.SubjectCustomWorklistManager;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

@RestController
@RequestMapping("/api/subjectCustomWorklists")
public class SubjectCustomWorklistResource extends AbstractResource<SubjectCustomWorklist> {

    @Override
    protected String getResourcePath() {
        return "/subjectCustomWorklists";
    }

    protected static final Logger LOGGER = LoggerFactory.getLogger(SubjectCustomWorklistResource.class);

    @Autowired
    private SubjectManager subjectManager;

    @Autowired
    private SubjectCustomWorklistManager manager;

    @Autowired
    private CustomWorklistManager customWorklistManager;

    @GetMapping
    public ResponseEntity<?> getCustomWorklists(@RequestParam(value = "worklistName", required = false) final String worklistName) {
        List<CustomWorklistSubjectDto> customWorklists = manager.findFullCustomWorklist(worklistName);
        return ResponseEntity.ok(customWorklists);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody final CustomWorklistSubjectDto customWorklist) {
        manager.createFromDto(customWorklist);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/saveFavoriteWorklist")
    public ResponseEntity<?> saveFavoriteWorklist(@RequestBody final CustomWorklistSubjectDto customWorklist) {
        manager.editFavorite(customWorklist);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/saveLastViewedDate")
    public ResponseEntity<?> saveLastViewedDate(@RequestBody final CustomWorklistSubjectDto customWorklist) {
        manager.editLastViewedDate(customWorklist);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") final Long id, @RequestBody final CustomWorklistSubjectDto customWorklist) {
        String username = SecurityUtils.getLoggedInUser();
        Subject subject = subjectManager.findByUsername(username);
        if (!subject.getId().equals(customWorklist.getAuthorId())) {
            LOGGER.info("Only the Author can edit a Custom Worklist");
            return ResponseEntity.internalServerError().body("Only the Author can edit a shared Custom Worklist.");
        }
        manager.editFromDto(customWorklist);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") final Long id) {
        CustomWorklist customWorklist = customWorklistManager.retrieve(id);
        String username = SecurityUtils.getLoggedInUser();
        Subject subject = subjectManager.findByUsername(username);
        if (!subject.getId().equals(customWorklist.getAuthorId())) {
            LOGGER.info("Only the Author can delete a Custom Worklist");
            return ResponseEntity.internalServerError().body("Only the Author can delete a Custom Worklist.");
        }
        List<SubjectCustomWorklist> subjectCustomWorklists = manager.findByCustomWorklistId(customWorklist.getId());
        for (SubjectCustomWorklist subjectCustomWorklist : subjectCustomWorklists) {
            manager.remove(subjectCustomWorklist.getId());
        }
        customWorklistManager.remove(id);
        return ResponseEntity.ok().build();
    }
}
