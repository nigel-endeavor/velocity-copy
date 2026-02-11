package com.endeavorms.velocity.qto.multiedit.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an entity and all the fields that failed to update for it.
 */
public class MultiEditError {
    /** The service. **/
    @JsonProperty
    private EntityDescription description;
    /** The fields. **/
    @JsonProperty
    private List<String> fields = new ArrayList<>();

    public EntityDescription getDescription() {
        return description;
    }

    public void setDescription(final EntityDescription description) {
        this.description = description;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(final List<String> fields) {
        this.fields = fields;
    }
}
