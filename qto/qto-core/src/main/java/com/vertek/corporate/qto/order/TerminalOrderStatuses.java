package com.vertek.corporate.qto.order;

import java.util.Arrays;
import java.util.List;

public enum TerminalOrderStatuses {
    COMPLETE("Order Complete"),
    CANCELLED("Order Cancelled"),
    CHANGE_IN_ASSIGNMENT("Change In Assignment");

    private final String status;

    TerminalOrderStatuses(final String status) {
        this.status = status;
    }

    /**
     * Returns the status enum by the given name.
     * @param status the given status.
     * @return a matching status enum, if it exists.
     * @throws IllegalArgumentException if it's unsupported service type string.
     */
    public static TerminalOrderStatuses fromStatus(String status) {
        for (TerminalOrderStatuses b : TerminalOrderStatuses.values()) {
            if (b.status.equalsIgnoreCase(status)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unsupported terminal location status '" + status + "'.");
    }

    public String getStatus() {
        return status;
    }

    public static List<String> getStatuses() {
        return Arrays.asList(Arrays.stream(TerminalOrderStatuses.values()).map(TerminalOrderStatuses::getStatus).toArray(String[]::new));
    }
}
