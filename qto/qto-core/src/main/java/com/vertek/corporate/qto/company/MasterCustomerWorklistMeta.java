package com.vertek.corporate.qto.company;

import java.math.BigDecimal;

/**
 * Container for master customer worklist meta data.
 */
public class MasterCustomerWorklistMeta {
    /**
     * Total active customers for the currently displayed result set.
     */
    private int totalActiveCustomers;

    /**
     * Total onboarding customers for the currently displayed result set.
     */
    private int totalOnboardingCustomers;

    public int getTotalActiveCustomers() {
        return totalActiveCustomers;
    }

    public void setTotalActiveCustomers(final int totalActiveCustomers) {
        this.totalActiveCustomers = totalActiveCustomers;
    }

    public int getTotalOnboardingCustomers() {
        return totalOnboardingCustomers;
    }

    public void setTotalOnboardingCustomers(final int totalOnboardingCustomers) {
        this.totalOnboardingCustomers = totalOnboardingCustomers;
    }
}
