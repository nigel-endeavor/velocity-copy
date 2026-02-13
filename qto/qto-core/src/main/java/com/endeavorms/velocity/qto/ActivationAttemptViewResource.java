package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.report.ActivationAttemptView;
import com.endeavorms.velocity.qto.report.ActivationAttemptViewManager;
import com.endeavorms.velocity.qto.report.DashboardSearchCriteria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author bmccormick
 */
@RestController
@RequestMapping("/api/activationViews")
public class ActivationAttemptViewResource extends AbstractResource<ActivationAttemptView> {

    @Override
    protected String getResourcePath() {
        return "/activationViews";
    }

    @Autowired
    private ActivationAttemptViewManager activationAttemptManager;

    @GetMapping("/activationIntervals")
    public ResponseEntity<?> getServiceIntervals(@ModelAttribute final DashboardSearchCriteria criteria,
                                                 @RequestParam("numOfMonths") final int numOfMonths) {
        List<ActivationAttemptView> activationAttempts = activationAttemptManager.getServiceIntervals(criteria, numOfMonths);
        return ResponseEntity.ok(activationAttempts);
    }
}
