package com.endeavorms.velocity.qto.invocing.surcharge;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.interceptors.SurchargeInterceptor;
import com.endeavorms.velocity.qto.invoicing.surcharge.service.ServiceSurcharge;
import com.endeavorms.velocity.qto.invoicing.surcharge.service.ServiceSurchargeManager;
import com.endeavorms.velocity.qto.invoicing.surcharge.service.ServiceSurchargeSearchCriteria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fcurran
 * @since 2023-07-12
 */
@RestController
@RequestMapping("/api/serviceSurcharges")
public class ServiceSurchargeResource extends AbstractResource<ServiceSurcharge> {

    @Override
    protected String getResourcePath() {
        return "/serviceSurcharges";
    }

    @Autowired
    private ServiceSurchargeManager manager;

    @Autowired
    private SurchargeInterceptor surchargeInterceptor;

    @GetMapping
    public ResponseEntity<?> getServiceSurcharges(@ModelAttribute final ServiceSurchargeSearchCriteria criteria) {
        PreconditionsUtil.checkArgument(criteria.getServiceId(), "A serviceId is required");
        PaginatedResult<ServiceSurcharge> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceSurchargeResource.class));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody final ServiceSurcharge serviceSurcharge) {
        try {
            PreconditionsUtil.checkArgument(serviceSurcharge.getServiceId(), "A serviceId is required");
            ServiceSurcharge createServiceSurcharge = manager.create(serviceSurcharge);
            return ResponseEntity.ok(createServiceSurcharge);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final ServiceSurcharge serviceSurcharge) {
        try {
            surchargeInterceptor.validateForEdit(serviceSurcharge);
            if (!id.equals(serviceSurcharge.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            PreconditionsUtil.checkArgument(serviceSurcharge.getServiceId(), "A serviceId is required");
            ServiceSurcharge updatedServiceSurcharge = manager.edit(serviceSurcharge);
            return ResponseEntity.ok(updatedServiceSurcharge);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") final Long id) {
        surchargeInterceptor.validateForRemove(id);
        manager.remove(id);
        return ResponseEntity.noContent().build();
    }
}
