package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.customfield.field.CustomField;
import com.endeavorms.velocity.qto.customfield.field.CustomFieldManager;
import com.endeavorms.velocity.qto.customfield.field.CustomFieldTabValue;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/customfields")
public class CustomFieldResource extends AbstractResource<CustomField> {

    @Override
    protected String getResourcePath() {
        return "/customfields";
    }

    @Autowired
    private CustomFieldManager manager;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getCustomFields(@RequestParam("tab") final CustomFieldTabValue tab) {
        try {
            PreconditionsUtil.checkArgument(tab, "tab is required");
            List<CustomField> customFields = manager.findActiveByTab(tab);
            return ResponseEntity.ok(customFields);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
