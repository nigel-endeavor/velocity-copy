package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.report.DashboardSearchCriteria;
import com.endeavorms.velocity.qto.report.ProviderIntervalsView;
import com.endeavorms.velocity.qto.report.ProviderViewManager;
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
 * @author llevit
 */
@RestController
@RequestMapping("/api/providerViews")
public class ProviderViewResource extends AbstractResource<ProviderIntervalsView> {

    @Override
    protected String getResourcePath() {
        return "/providerViews";
    }

    @Autowired
    private ProviderViewManager providerManager;

    @Autowired
    private WipViewManager wipManager;

    @GetMapping("/providerIntervals")
    public ResponseEntity<?> getProviderIntervals(@ModelAttribute final DashboardSearchCriteria criteria,
                                                 @RequestParam("intervalTypeCode") final String intervalTypeCode,
                                                 @RequestParam("numOfMonths") final int numOfMonths) {
        PreconditionsUtil.checkArgument(intervalTypeCode, "Interval Type Code is required.");
        List<ProviderIntervalsView> providerIntervals = providerManager.getProviderIntervals(criteria, intervalTypeCode, numOfMonths);
        return ResponseEntity.ok(providerIntervals);
    }

    @GetMapping("/providerReliance")
    public ResponseEntity<?> getProviderReliance(@ModelAttribute final DashboardSearchCriteria criteria) {
        List<WipServiceView> services = wipManager.getProviderReliance(criteria);
        return ResponseEntity.ok(services);
    }
}
