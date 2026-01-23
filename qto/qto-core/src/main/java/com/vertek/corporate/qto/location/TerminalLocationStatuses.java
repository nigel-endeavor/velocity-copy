package com.vertek.corporate.qto.location;

import java.util.Arrays;
import java.util.List;

public enum TerminalLocationStatuses {
    COMPLETE("Location Complete"),
    CANCELLED("Location Cancelled"),
    CHANGE_IN_ASSIGNMENT("Change In Assignment");

    private final String status;

    TerminalLocationStatuses(final String status) {
        this.status = status;
    }

    /**
     * Returns the status enum by the given name.
     * @param status the given status.
     * @return a matching status enum, if it exists.
     * @throws IllegalArgumentException if it's unsupported service type string.
     */
    public static TerminalLocationStatuses fromStatus(String status) {
        for (TerminalLocationStatuses b : TerminalLocationStatuses.values()) {
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
        return Arrays.asList(Arrays.stream(TerminalLocationStatuses.values()).map(TerminalLocationStatuses::getStatus).toArray(String[]::new));
    }
}
