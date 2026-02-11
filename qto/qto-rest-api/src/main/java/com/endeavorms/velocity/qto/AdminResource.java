package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.common.TenantViewManager;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.graph.MSGraph;
import com.endeavorms.velocity.qto.inventory.PendingDisconnectManager;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.snapshot.ServiceSnapshotManager;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import com.endeavorms.velocity.qto.common.SchedulerSecurityContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

/**
 * Resource class for admin operations.
 *
 * @author rcasey
 * @since 7/31/2023
 */
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('*')")
public class AdminResource {

    /** Private Logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminResource.class);

    /** The MSGraph service. */
    @Inject
    private MSGraph msGraph;

    @Inject
    private ServiceSnapshotManager serviceSnapshotManager;

    @Inject
    private CompanyManager companyManager;

    @Inject
    private SubjectManager subjectManager;

    @Inject
    private TenantViewManager tenantViewManager;

    @Inject
    private PendingDisconnectManager pendingDisconnectManager;

    @Inject
    private ServiceManager serviceManager;

    @Inject
    private LocationManager locationManager;

    @GetMapping("/azureAdGroups")
    public ResponseEntity<?> getAzureAdGroups() {
        try {
            return ResponseEntity.ok(msGraph.getGroups());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/refreshAzureAdGroups")
    public ResponseEntity<?> refreshAzureAdGroups() {
        try {
            LOGGER.debug("Azure AD group refresh requested by {}", SecurityUtils.getLoggedInUser());
            Map<String, List<String>> groups = msGraph.loadGroups();
            subjectManager.loadGroups();
            return ResponseEntity.ok(groups);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Test endpoint for ServiceSnapshotJob.
     * @return a Response.
     */
    @PostMapping("/runServiceSnapshotJob")
    public ResponseEntity<?> runInventoryHistoryJob() {
        try {
            LOGGER.debug("Service snapshot job requested by {}", SecurityUtils.getLoggedInUser());
            SchedulerSecurityContext.runAsScheduler(() -> serviceSnapshotManager.createServiceSnapshots());
            LOGGER.debug("Service snapshot job manual run finished");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/updateCompanyInventoryCounts")
    public ResponseEntity<?> updateCompanyInventoryCounts() {
        try {
            LOGGER.debug("Company inventory counts update requested by {}", SecurityUtils.getLoggedInUser());
            SchedulerSecurityContext.runAsScheduler(() -> companyManager.updateAllInventoryCounts());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/runPendingDisconnects")
    public ResponseEntity<?> runPendingDisconnects() {
        try {
            LOGGER.debug("Pending Disconnects requested by {}", SecurityUtils.getLoggedInUser());
            SchedulerSecurityContext.runAsScheduler(() -> pendingDisconnectManager.processPendingDisconnects());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/runDeleteJob")
    public ResponseEntity<?> runDeleteJob() {
        try {
            LOGGER.debug("Delete Job requested by {}", SecurityUtils.getLoggedInUser());
            SchedulerSecurityContext.runAsScheduler(() -> {
                serviceManager.deleteServiceJob();
                locationManager.deleteLocationJob();
            });
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
