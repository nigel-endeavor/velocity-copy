package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.helpdesk.HelpDeskManager;
import com.endeavorms.velocity.qto.helpdesk.TicketRequest;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * @author bmccormick
 * @since 3/28/2024
 */
@RestController
@RequestMapping("/api/helpdesk")
@PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
public class HelpDeskResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelpDeskResource.class);

    @Inject
    private HelpDeskManager manager;

    @Inject
    private SubjectManager subjectManager;

    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody final TicketRequest requestData) {
        try {
            Subject subject = subjectManager.findByEmailAddress(SecurityUtils.getLoggedInUser());
            String emailAddress = subject.getEmailAddress();
            requestData.setUserEmail(emailAddress);
            manager.createTicket(requestData);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error creating helpdesk ticket: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
