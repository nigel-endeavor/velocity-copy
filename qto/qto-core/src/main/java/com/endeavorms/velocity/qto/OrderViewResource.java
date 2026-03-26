package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.order.view.OrderView;
import com.endeavorms.velocity.qto.order.view.OrderViewManager;
import com.endeavorms.velocity.qto.order.view.OrderViewSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoint for the orders list view.
 * Backed by the v_manage_orders database view via OrderView entity —
 * returns in milliseconds vs the 45-180s full entity-graph load.
 */
@RestController
@RequestMapping("/api/orderViews")
public class OrderViewResource extends AbstractResource<OrderView> {

    @Override
    protected String getResourcePath() {
        return "/orderViews";
    }

    @Autowired
    private OrderViewManager manager;

    @GetMapping
    @PreAuthorize("hasAuthority('order:read')")
    public ResponseEntity<?> getOrderViews(@ModelAttribute final OrderViewSearchCriteria criteria) {
        PaginatedResult<OrderView> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(OrderViewResource.class));
    }
}
