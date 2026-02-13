package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.milestone.MilestoneDisplaySet;
import com.endeavorms.velocity.qto.milestone.MilestoneDisplaySetManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/milestoneDisplaySets")
public class MilestoneDisplaySetResource {

    @Autowired
    private MilestoneDisplaySetManager manager;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getDisplaySet(@RequestParam("displayGroup") final String displayGroup) {
        MilestoneDisplaySet displaySet = manager.getByDisplayGroup(displayGroup);
        return ResponseEntity.ok(displaySet);
    }
}
