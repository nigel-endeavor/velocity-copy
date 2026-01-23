package com.vertek.corporate.qto.service;

import java.util.Arrays;
import java.util.List;

public enum TerminalServiceStatuses {
    DISCONNECT_COMPLETE("Disconnect Complete"),
    DISCONNECT_CANCELLED("Disconnect Cancelled"),
    COMPLETE("Service Complete"),
    CANCELLED("Service Cancelled"),
    CHANGE_IN_ASSIGNMENT("Change In Assignment");

    private final String status;

    TerminalServiceStatuses(final String status) {
        this.status = status;
    }

    /**
     * Returns the status enum by the given name.
     * @param status the given status.
     * @return a matching status enum, if it exists.
     * @throws IllegalArgumentException if it's unsupported service type string.
     */
    public static TerminalServiceStatuses fromStatus(String status) {
        for (TerminalServiceStatuses b : TerminalServiceStatuses.values()) {
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
        return Arrays.asList(Arrays.stream(TerminalServiceStatuses.values()).map(TerminalServiceStatuses::getStatus).toArray(String[]::new));
    }
}
