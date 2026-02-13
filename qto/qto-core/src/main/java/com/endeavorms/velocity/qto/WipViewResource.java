package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.report.DashboardSearchCriteria;
import com.endeavorms.velocity.qto.report.WipLocationJeopView;
import com.endeavorms.velocity.qto.report.WipServiceJeopView;
import com.endeavorms.velocity.qto.report.WipServiceView;
import com.endeavorms.velocity.qto.report.WipViewManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @since 3/10/2023
 */
@RestController
@RequestMapping("/api/wipViews")
public class WipViewResource extends AbstractResource<WipServiceView> {

    @Override
    protected String getResourcePath() {
        return "/wipViews";
    }

    @Autowired
    private WipViewManager manager;

    @GetMapping("/wipServices")
    public ResponseEntity<?> getWipServices(@ModelAttribute final DashboardSearchCriteria criteria,
                                             @RequestParam(value = "allStatuses", defaultValue = "false") final boolean allStatuses) {
        List<WipServiceView> wipServices = manager.getWipServices(criteria, allStatuses);
        return ResponseEntity.ok(wipServices);
    }

    @GetMapping("/wipServiceJeops")
    public ResponseEntity<?> getWipServiceJeops(@ModelAttribute final DashboardSearchCriteria criteria) {
        List<WipServiceJeopView> wipServiceJeops = manager.getWipServiceJeops(criteria);
        return ResponseEntity.ok(wipServiceJeops);
    }

    @GetMapping("/wipLocationJeops")
    public ResponseEntity<?> getWipLocationJeops(@ModelAttribute final DashboardSearchCriteria criteria) {
        List<WipLocationJeopView> wipServiceJeops = manager.getWipLocationJeops(criteria);
        return ResponseEntity.ok(wipServiceJeops);
    }

    @GetMapping("/monthlySpend")
    public ResponseEntity<?> getMonthlySpend(@ModelAttribute final DashboardSearchCriteria criteria) {
        List<WipServiceView> wipServices = manager.getServicesForMonthlySpend(criteria);
        return ResponseEntity.ok(wipServices);
    }

    @GetMapping("/incrementalNetworkSpend")
    public ResponseEntity<?> getServicesForIncrementalNetworkSpend(@ModelAttribute final DashboardSearchCriteria criteria) {
        List<WipServiceView> wipServices = manager.getServicesForIncrementalNetworkSpend(criteria);
        return ResponseEntity.ok(wipServices);
    }

    @GetMapping("/unbillableNetworkExpenseAccrual")
    public ResponseEntity<?> getServicesForUnbillableNetworkExpenseAccrual(@ModelAttribute final DashboardSearchCriteria criteria) {
        List<WipServiceView> wipServices = manager.getServicesForUnbillableNetworkExpenseAccrual(criteria);
        return ResponseEntity.ok(wipServices);
    }
}
