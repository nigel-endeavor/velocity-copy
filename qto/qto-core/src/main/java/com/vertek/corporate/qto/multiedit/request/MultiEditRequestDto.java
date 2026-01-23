package com.vertek.corporate.qto.multiedit.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * DTO for multi-editing entities.
 */
public class MultiEditRequestDto implements Serializable {
    /** ids we're attempting to update. */
    @JsonProperty
    private List<Long> ids;
    /** The fields/values we're attempting to change. */
    @JsonProperty
    private Map<String, Object> fieldValues;
    /** The milestones we're updating. */
    @JsonProperty
    private List<MilestoneCodeDate> milestones;

    private Long subjectId;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(final List<Long> ids) {
        this.ids = ids;
    }

    public Map<String, Object> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(final Map<String, Object> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public List<MilestoneCodeDate> getMilestones() {
        return milestones;
    }

    public void setMilestones(final List<MilestoneCodeDate> milestones) {
        this.milestones = milestones;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(final Long subjectId) {
        this.subjectId = subjectId;
    }
}
