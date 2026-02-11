package com.endeavorms.velocity.qto.graph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stub implementation - Azure AD / MS Graph removed per migration plan.
 * Returns empty groups. Replace with LDAP or other identity provider as needed.
 */
@Component
public class MSGraph {

    private static final Logger LOGGER = LoggerFactory.getLogger(MSGraph.class);

    /** Map of group name to list of email addresses */
    private Map<String, List<String>> groups = new HashMap<>();

    @PostConstruct
    public void init() {
        loadGroups();
    }

    public Map<String, List<String>> loadGroups() {
        LOGGER.debug("MSGraph stub: Azure AD removed, returning empty groups");
        this.groups = new HashMap<>();
        return this.groups;
    }

    public Map<String, List<String>> getGroups() {
        return groups != null ? groups : Collections.emptyMap();
    }
}
