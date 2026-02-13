package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.address.AddressView;
import com.endeavorms.velocity.qto.address.AddressViewManager;
import com.endeavorms.velocity.qto.address.AddressViewSearchCriteria;
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
 * @since 2/6/2023
 */
@RestController
@RequestMapping("/api/addresses")
public class AddressResource extends AbstractResource<AddressView> {

    @Override
    protected String getResourcePath() {
        return "/addresses";
    }

    @Autowired
    private AddressViewManager manager;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getAddresses(@ModelAttribute final AddressViewSearchCriteria criteria) {
        PaginatedResult<AddressView> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(AddressResource.class));
    }
}
