package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.DashboardDataset;
import com.endeavorms.velocity.qto.report.DashboardSearchCriteria;
import com.endeavorms.velocity.qto.service.snapshot.ServiceSnapshotManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author rcasey
 * @since 3/25/2024
 */
@RestController
@RequestMapping("/api/serviceSnapshots")
public class ServiceSnapshotResource extends AbstractResource {

    @Override
    protected String getResourcePath() {
        return "/serviceSnapshots";
    }

    @Autowired
    private ServiceSnapshotManager manager;

    @GetMapping("/inventoryValuation")
    public ResponseEntity<?> getInventoryValuation(@ModelAttribute final DashboardSearchCriteria criteria,
                                                    @RequestParam(value = "numOfMonths", defaultValue = "6") final int numOfMonths) {
        DashboardDataset<BigDecimal> dataset = manager.getInventoryValuation(criteria, numOfMonths);
        return ResponseEntity.ok(dataset);
    }

    @GetMapping("/inventoryCounts")
    public ResponseEntity<?> getInventoryCounts(@ModelAttribute final DashboardSearchCriteria criteria,
                                                 @RequestParam(value = "numOfMonths", defaultValue = "6") final int numOfMonths) {
        DashboardDataset<BigInteger> dataset = manager.getInventoryCounts(criteria, numOfMonths);
        return ResponseEntity.ok(dataset);
    }

    @GetMapping("/newInventory")
    public ResponseEntity<?> getNewInventory(@ModelAttribute final DashboardSearchCriteria criteria,
                                             @RequestParam(value = "numOfMonths", defaultValue = "6") final int numOfMonths) {
        DashboardDataset<BigInteger> dataset = manager.getNewInventory(criteria, numOfMonths);
        return ResponseEntity.ok(dataset);
    }

    @GetMapping("/serviceTypes")
    public ResponseEntity<?> getServiceTypes() {
        List<String> serviceType = manager.findServiceTypes();
        return ResponseEntity.ok(serviceType);
    }
}
