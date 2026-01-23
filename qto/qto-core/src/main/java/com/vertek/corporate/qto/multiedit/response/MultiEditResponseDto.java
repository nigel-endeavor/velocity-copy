package com.vertek.corporate.qto.multiedit.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * The response from the multiedit service.
 */
public class MultiEditResponseDto {
    /** An array of all services that couldn't be edited. */
    @JsonProperty
    private List<MultiEditError> cantEdit = new ArrayList<>();

    public List<MultiEditError> getCantEdit() {
        return cantEdit;
    }

    public void setCantEdit(final List<MultiEditError> cantEdit) {
        this.cantEdit = cantEdit;
    }
}
