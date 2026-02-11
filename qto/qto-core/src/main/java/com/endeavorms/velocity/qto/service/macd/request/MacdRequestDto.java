package com.endeavorms.velocity.qto.service.macd.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for creating MACD with 1:n types.
 * @since 1.3.0
 */
public class MacdRequestDto implements Serializable {
    /** Service ID we're creating the MACD from. */
    @JsonProperty
    private Long serviceId;
    /** The order and sub order types and other properties. */
    @JsonProperty
    private List<MacdDto> macds;

    /** Service note for the macd. */
    @JsonProperty
    private String macdNote;

    @JsonProperty
    private boolean createLinkedBundled;

    /** Service attachment for the macd. */
    @JsonProperty
    private List<Long> fileAttachments;

    /** The source of the MACD request. Only applicable if this is coming in as a multi MACD from a listener. */
    private Long subjectId;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public List<MacdDto> getMacds() {
        return macds;
    }

    public void setMacds(final List<MacdDto> macds) {
        this.macds = macds;
    }

    public String getMacdNote() {
        return macdNote;
    }

    public void setMacdNote(final String macdNote) {
        this.macdNote = macdNote;
    }

    public boolean isCreateLinkedBundled() {
        return createLinkedBundled;
    }

    public void setCreateLinkedBundled(final boolean createLinkedBundled) {
        this.createLinkedBundled = createLinkedBundled;
    }

    public List<Long> getFileAttachments() {
        return fileAttachments;
    }

    public void setFileAttachments(final List<Long> fileAttachments) {
        this.fileAttachments = fileAttachments;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(final Long subjectId) {
        this.subjectId = subjectId;
    }
}
