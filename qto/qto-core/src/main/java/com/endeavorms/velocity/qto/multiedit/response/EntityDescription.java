package com.endeavorms.velocity.qto.multiedit.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Simple class with the ID and a description of the entity in question.
 */
public class EntityDescription {
    /** The ID of the entity. */
    @JsonProperty
    private Long id;
    /** Some string that identifies the entity. */
    @JsonProperty
    private String displayName;

    /**
     * Default constructor.
     */
    public EntityDescription() {
    }

    /**
     * Constructor.
     * @param id The ID of the entity.
     * @param displayName some string that identifies the entity.
     */
    public EntityDescription(final Long id, final String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
}
