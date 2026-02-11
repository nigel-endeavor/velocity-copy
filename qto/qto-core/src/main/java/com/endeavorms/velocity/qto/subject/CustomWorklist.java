package com.endeavorms.velocity.qto.subject;

import com.endeavorms.velocity.qto.common.AbstractTenantOwnedEntity;
import org.hibernate.annotations.Formula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "custom_worklist")
public class CustomWorklist extends AbstractTenantOwnedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_worklist_id")
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    @Formula("(select s.display_name from v_subject s " + "where s.subject_id = author_id)")
    private String authorName;

    @Column(name = "content")
    private String content;

    @Column(name = "shared")
    private boolean shared;

    @Column(name = "worklist_name")
    private String worklistName;

    @Column(name = "name")
    private String name;

    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @Override
    public Long getId() {
        return id;
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

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(final Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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

}
