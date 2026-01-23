package com.vertek.corporate.qto.subject;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "subject_custom_worklist")
public class SubjectCustomWorklist extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_custom_worklist_id")
    private Long id;

    @Column(name = "custom_worklist_id")
    private Long customWorklistId;

    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "favorite")
    private boolean favorite;

    @Column(name = "last_viewed_date")
    private Date lastViewedDate;

    @Override
    public Long getId() {
        return id;
    }

    public Long getCustomWorklistId() {
        return customWorklistId;
    }

    public void setCustomWorklistId(final Long customWorklistId) {
        this.customWorklistId = customWorklistId;
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
}
