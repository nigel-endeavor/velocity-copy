package com.endeavorms.velocity.qto.service.macd;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.endeavorms.velocity.qto.service.macd.request.MacdDto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for creating MACD(s) with 1:n types.
 * @since 1.3.0
 */
public class MultiMacdRequestDto implements Serializable {
    /** Service ID(s) we're creating the MACD from. */
    @JsonProperty
    private List<Long> ids;
    /** The order and sub order types and other properties. */
    @JsonProperty
    private List<MacdDto> macds;
    /** Service note for the MACD. */
    @JsonProperty
    private String macdNote;
    /** Subject ID for the MACD request. */
    private Long subjectId;

    /** Service attachment for the macd. */
    @JsonProperty
    private List<Long> fileAttachments;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(final List<Long> ids) {
        this.ids = ids;
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

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(final Long subjectId) {
        this.subjectId = subjectId;
    }

    public List<Long> getFileAttachments() {
        return fileAttachments;
    }

    public void setFileAttachments(final List<Long> fileAttachments) {
        this.fileAttachments = fileAttachments;
    }
}
