package com.endeavorms.velocity.qto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.endeavorms.velocity.qto.activation.schedule.ActivationSchedule;
import com.endeavorms.velocity.qto.activation.schedule.ActivationScheduleManager;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.ValidationError;
import com.endeavorms.velocity.qto.interceptors.ActivationScheduleInterceptor;
import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.config.CompanyConfigKey;
import com.endeavorms.velocity.qto.config.CompanyConfigPropertyManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author rcasey
 * @since 3/22/2023
 */
@RestController
@RequestMapping("/api/activationSchedules")
public class ActivationScheduleResource extends AbstractResource {

    @Override
    protected String getResourcePath() {
        return "/activationSchedules";
    }

    @Autowired
    private ActivationScheduleManager manager;

    @Autowired
    private CompanyConfigPropertyManager<CompanyConfigKey> companyConfigPropertyManager;

    @Autowired
    private CompanyManager companyManager;

    @Autowired
    private ActivationScheduleInterceptor activationScheduleInterceptor;

    private static ObjectMapper mapper;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getSchedules(@RequestParam("serviceId") final Long serviceId) {
        List<ActivationSchedule> schedules = manager.findByServiceId(serviceId);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getSchedule(@PathVariable("id") final Long id) {
        ActivationSchedule schedule = manager.retrieve(id);
        return ResponseEntity.ok(schedule);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> create(@RequestBody final ActivationSchedule schedule) {
        BadRequestError validationError = activationScheduleInterceptor.validate(schedule);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }
        try {
            ActivationSchedule created = manager.create(schedule);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final ActivationSchedule schedule) {
        if (!id.equals(schedule.getId())) {
            return ResponseEntity.badRequest().body(new BadRequestError(
                List.of(new ValidationError("id", "identifier in path does not match that of passed entity"))));
        }
        BadRequestError validationError = activationScheduleInterceptor.validate(schedule);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }
        try {
            ActivationSchedule updated = manager.edit(schedule);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public static synchronized ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        }
        return mapper;
    }
}
