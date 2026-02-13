package com.endeavorms.velocity.qto.invocing.levelOfEffort;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.invoicing.levelOfEffort.LevelOfEffort;
import com.endeavorms.velocity.qto.invoicing.levelOfEffort.LevelOfEffortManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rcasey
 * @since 7/12/2023
 */
@RestController
@RequestMapping("/api/levelOfEffort")
public class LevelOfEffortResource extends AbstractResource<LevelOfEffort> {

    @Override
    protected String getResourcePath() {
        return "/levelOfEffort";
    }

    @Autowired
    private LevelOfEffortManager manager;

    @GetMapping
    public ResponseEntity<?> getLevelOfEffort(@RequestParam("companyId") final Long companyId) {
        return ResponseEntity.ok(manager.findByCompanyId(companyId));
    }
}
