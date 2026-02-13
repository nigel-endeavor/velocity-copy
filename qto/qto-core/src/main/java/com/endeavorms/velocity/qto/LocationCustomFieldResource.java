package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.customfield.value.LocationCustomFieldValue;
import com.endeavorms.velocity.qto.customfield.value.LocationCustomFieldValueListDto;
import com.endeavorms.velocity.qto.customfield.value.LocationCustomFieldValueManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/locationCustomfieldValues")
@PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
public class LocationCustomFieldResource {

    @Autowired
    private LocationCustomFieldValueManager locationCustomFieldValueManager;

    @GetMapping
    public ResponseEntity<?> findByLocationId(@RequestParam("locationId") final Long locationId) {
        List<LocationCustomFieldValue> locationCustomFieldValues = locationCustomFieldValueManager.findByRecordId(locationId);
        return ResponseEntity.ok(locationCustomFieldValues);
    }

    @PostMapping("/saveValues")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> saveValues(@RequestBody final LocationCustomFieldValueListDto locationCustomFieldValues) {
        try {
            List<LocationCustomFieldValue> created = locationCustomFieldValueManager.saveValues(locationCustomFieldValues.getValues());
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
