package com.vertek.corporate.qto.activation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ActivationWorklistMeta {

    private Map<String, Long> statusCounts = new HashMap<>();

    private BigDecimal ttuEquivalentTotal;

    public Map<String, Long> getStatusCounts() {
        return statusCounts;
    }

    public void setStatusCounts(final Map<String, Long> statusCounts) {
        this.statusCounts = statusCounts;
    }

    public BigDecimal getTtuEquivalentTotal() {
        return ttuEquivalentTotal;
    }

    public void setTtuEquivalentTotal(final BigDecimal ttuEquivalentTotal) {
        this.ttuEquivalentTotal = ttuEquivalentTotal;
    }
}
