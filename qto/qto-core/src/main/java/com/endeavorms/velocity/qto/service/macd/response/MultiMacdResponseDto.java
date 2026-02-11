package com.endeavorms.velocity.qto.service.macd.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * The response from the multi macd service.
 */
public class MultiMacdResponseDto {
    /** An ID array of all MACDs generated. */
    @JsonProperty
    private List<Long> created = new ArrayList<>();
    /** An array of all services that couldn't have MACDs generated. */
    @JsonProperty
    private List<MultiMacdError> cantEdit = new ArrayList<>();

    public List<Long> getCreated() {
        return created;
    }

    public void setCreated(final List<Long> created) {
        this.created = created;
    }

    public List<MultiMacdError> getCantEdit() {
        return cantEdit;
    }

    public void setCantEdit(final List<MultiMacdError> cantEdit) {
        this.cantEdit = cantEdit;
    }
}
