package com.vertek.corporate.qto.subject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class CustomWorklistSubjectDto implements Serializable {

    @JsonProperty
    private Long id;

   @JsonProperty
    private String authorName;

   @JsonProperty
    private String name;

   @JsonProperty
    private boolean favorite;

   @JsonProperty
    private Date lastViewedDate;

    @JsonProperty
    private Long authorId;

   @JsonProperty
    private boolean shared;

   @JsonProperty
    private String worklistName;

    @JsonProperty
    private Date lastModifiedDate;

   @JsonProperty
    private Long subjectCustomWorklistId;

   @JsonProperty
    private Long subjectId;

   @JsonProperty
    private String content;

   private Long tenantId;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(final Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(final String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(final boolean shared) {
        this.shared = shared;
    }

    public String getWorklistName() {
        return worklistName;
    }

    public void setWorklistName(final String worklistName) {
        this.worklistName = worklistName;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(final Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getSubjectCustomWorklistId() {
        return subjectCustomWorklistId;
    }

    public void setSubjectCustomWorklistId(final Long subjectCustomWorklistId) {
        this.subjectCustomWorklistId = subjectCustomWorklistId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(final Long subjectId) {
        this.subjectId = subjectId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(final boolean favorite) {
        this.favorite = favorite;
    }

    public Date getLastViewedDate() {
        return lastViewedDate;
    }

    public void setLastViewedDate(final Date lastViewedDate) {
        this.lastViewedDate = lastViewedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(final Long tenantId) {
        this.tenantId = tenantId;
    }
}
