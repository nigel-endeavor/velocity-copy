package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.TenantSubjectManager;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.config.CompanyConfigKey;
import com.endeavorms.velocity.qto.config.CompanyConfigPropertyManager;
import com.endeavorms.velocity.qto.config.CompanyConfigurationProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Component
@RestController
@RequestMapping("/api/companyConfigProperties")
public class CompanyConfigPropertyResource extends AbstractResource<CompanyConfigurationProperty> {

    @Override
    protected String getResourcePath() {
        return "/companyConfigProperties";
    }

    @Autowired
    private CompanyConfigPropertyManager<CompanyConfigKey> manager;

    @Autowired
    private CompanyManager companyManager;

    @Autowired
    private TenantSubjectManager tenantSubjectManager;

    @GetMapping("/{key:.+}")
    public ResponseEntity<?> getValue(@PathVariable("key") final CompanyConfigKey key) {
        Company tenantCompany = companyManager.findTenantByName(tenantSubjectManager.getCurrentTenant().getName());
        return ResponseEntity.ok(wrapResource(manager.findByKey(tenantCompany.getId(), key)));
    }

    @GetMapping("/modifiable/{id}")
    @PreAuthorize("hasAuthority('tenant-admin')")
    public ResponseEntity<?> getModifiableProperties(@PathVariable("id") final Long id) {
        List<CompanyConfigurationProperty> modifiableProperties = manager.getAllModifiableProperties(id);
        for (CompanyConfigurationProperty property : modifiableProperties) {
            if ("password".equalsIgnoreCase(property.getType())) {
                String decryptedValue = manager.decryptString(property.getValue());
                property.setDecryptedValue(decryptedValue);
            }
        }
        return ResponseEntity.ok(modifiableProperties);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('tenant-admin')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final CompanyConfigurationProperty config) {
        try {
            if (!id.equals(config.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            if ("password".equalsIgnoreCase(config.getType())) {
                String encryptedValue = manager.encryptString(config.getDecryptedValue());
                config.setValue(encryptedValue);
            }
            CompanyConfigurationProperty updated = manager.edit(config);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
