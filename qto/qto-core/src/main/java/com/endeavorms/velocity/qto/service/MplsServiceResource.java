package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.mpls.MplsService;
import com.endeavorms.velocity.qto.service.mpls.MplsServiceManager;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * @author rcasey
 * @since 5/17/2024
 */
@RestController
@RequestMapping("/api/mplsServices")
public class MplsServiceResource extends AbstractServiceResource<MplsService> {

        @Inject
        private MplsServiceManager manager;

        @Override
        protected MplsServiceManager getManager() {
            return manager;
        }

    @Override
    protected String getResourcePath() {
        return "/mplsServices";
    }
}
