package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.television.TelevisionService;
import com.endeavorms.velocity.qto.service.television.TelevisionServiceManager;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * @author bmccormick
 * @since 2/7/2024
 */
@RestController
@RequestMapping("/api/televisionService")
public class TelevisionServiceResource extends AbstractServiceResource<TelevisionService> {

    @Inject
    private TelevisionServiceManager manager;

    @Override
    protected TelevisionServiceManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/televisionService";
    }
}
