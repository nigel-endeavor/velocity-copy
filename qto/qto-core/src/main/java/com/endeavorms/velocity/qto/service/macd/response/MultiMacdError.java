package com.endeavorms.velocity.qto.service.macd.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.endeavorms.velocity.qto.multiedit.response.EntityDescription;

/**
 * Represents an entity and all the fields that failed to update for it.
 */
public class MultiMacdError {
    /** The service. **/
    @JsonProperty
    private EntityDescription description;
    /** The fields. **/
    @JsonProperty
    private String error;

    public EntityDescription getDescription() {
        return description;
    }

    public void setDescription(final EntityDescription description) {
        this.description = description;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return getDescription().getDisplayName() + " - " + getError();
    }
}
