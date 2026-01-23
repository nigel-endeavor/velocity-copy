package com.vertek.corporate.qto.helpdesk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class TicketRequest implements Serializable {
    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("user_email")
    private String userEmail;

    /** attachment for the helpdesk. */
    @JsonProperty ("attachment")
    private LinkedHashMap attachment;

    /** attachment file name for the helpdesk. */
    @JsonProperty ("fileName")
    private String fileName;

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(final String userEmail) {
        this.userEmail = userEmail;
    }

    public LinkedHashMap<String, Integer> getAttachment() {
        return attachment;
    }

    public void setAttachment(final LinkedHashMap attachment) {
        this.attachment = attachment;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }
}
