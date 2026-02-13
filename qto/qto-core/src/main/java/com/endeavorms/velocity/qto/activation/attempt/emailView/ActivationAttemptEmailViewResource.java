package com.endeavorms.velocity.qto.activation.attempt.emailView;

import com.endeavorms.velocity.qto.common.AbstractResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/activationAttemptEmailViews")
public class ActivationAttemptEmailViewResource extends AbstractResource<ActivationAttemptEmailView> {

    @Override
    protected String getResourcePath() {
        return "/activationAttemptEmailViews";
    }

    @Autowired
    private ActivationAttemptEmailViewManager manager;

    @GetMapping("/{id}")
    public ResponseEntity<?> getActivationAttemptEmailView(@PathVariable("id") final Long id) {
        ActivationAttemptEmailView retrieved = manager.retrieve(id);
        return ResponseEntity.ok(retrieved);
    }
}
