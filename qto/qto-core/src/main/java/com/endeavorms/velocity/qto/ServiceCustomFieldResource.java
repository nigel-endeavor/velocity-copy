package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.customfield.value.ServiceCustomFieldValue;
import com.endeavorms.velocity.qto.customfield.value.ServiceCustomFieldValueListDto;
import com.endeavorms.velocity.qto.customfield.value.ServiceCustomFieldValueManager;
import com.endeavorms.velocity.qto.interceptors.ServiceCustomFieldValueInterceptor;
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
@RequestMapping("/api/serviceCustomfieldValues")
@PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
public class ServiceCustomFieldResource {

    @Autowired
    private ServiceCustomFieldValueManager serviceCustomFieldValueManager;

    @Autowired
    private ServiceCustomFieldValueInterceptor serviceCustomFieldValueInterceptor;

    @GetMapping
    public ResponseEntity<?> findByServiceId(@RequestParam("serviceId") final Long serviceId) {
        List<ServiceCustomFieldValue> serviceCustomFieldValues = serviceCustomFieldValueManager.findByRecordId(serviceId);
        return ResponseEntity.ok(serviceCustomFieldValues);
    }

    @PostMapping("/saveValues")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> saveValues(@RequestBody final ServiceCustomFieldValueListDto serviceCustomFieldValues) {
        try {
            serviceCustomFieldValueInterceptor.validateBeforeSave(serviceCustomFieldValues);
            List<ServiceCustomFieldValue> created = serviceCustomFieldValueManager.saveValues(serviceCustomFieldValues.getValues());
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
