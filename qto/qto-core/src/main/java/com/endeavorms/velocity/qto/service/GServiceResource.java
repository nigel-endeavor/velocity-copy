package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service._4g5g.GService;
import com.endeavorms.velocity.qto.service._4g5g.GServiceManager;

import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rcasey
 * @sicne 6/7/2023
 */
@RestController
@RequestMapping("/api/4G5GServices")
public class GServiceResource extends AbstractServiceResource<GService> {

    @Inject
    private GServiceManager manager;

    @Override
    protected GServiceManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/4G5GServices";
    }
}
