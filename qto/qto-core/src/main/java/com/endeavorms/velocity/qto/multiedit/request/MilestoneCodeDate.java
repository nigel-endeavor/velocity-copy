package com.endeavorms.velocity.qto.multiedit.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Simple class representing a date change for a milestone.
 */
public class MilestoneCodeDate implements Serializable {
    /** The code of the related milestone. */
    @JsonProperty("code")
    private String code;
    /** The date to be applied. */
    @JsonProperty("date")
    private Date date;

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }
}
