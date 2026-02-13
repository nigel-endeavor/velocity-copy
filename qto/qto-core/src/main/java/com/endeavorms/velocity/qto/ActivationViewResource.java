package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.activation.ActivationViewManager;
import com.endeavorms.velocity.qto.activation.ActivationView;
import com.endeavorms.velocity.qto.activation.ActivationViewSearchCriteria;
import com.endeavorms.velocity.qto.activation.ActivationWorklistMeta;
import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rcasey
 * @since 3/1/2023
 */
@RestController
@RequestMapping("/api/activationViews")
public class ActivationViewResource extends AbstractResource<ActivationView> {

    @Override
    protected String getResourcePath() {
        return "/activationViews";
    }

    @Autowired
    private ActivationViewManager manager;

    @GetMapping
    public ResponseEntity<?> getActivationViews(@ModelAttribute final ActivationViewSearchCriteria criteria) {
        ActivationViewSearchCriteria crit = getExportCriteria(criteria);
        PaginatedResult<ActivationView> result = manager.findBySearchCriteria(crit);
        return getCollectionResource(result, crit, getLocation(ActivationViewResource.class));
    }

    @GetMapping("/meta")
    public ResponseEntity<?> getActivationWorklistMeta(@ModelAttribute final ActivationViewSearchCriteria criteria) {
        ActivationWorklistMeta meta = manager.getWorklistMeta(criteria);
        return ResponseEntity.ok(meta);
    }
}
