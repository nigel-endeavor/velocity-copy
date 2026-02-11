package com.endeavorms.velocity.qto.ftdi;

import com.endeavorms.velocity.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ftdi_note")
public class FtdiNote extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ftdi_note_id")
    private Long id;

    @Column(name="legacy_id")
    private Long legacyId;

    /**
     * dispatch id.
     */
    @Column(name = "ftdi_dispatch_id")
    private Long ftdiDispatchId;

    /**
     * sr.
     */
    @Column(name = "sr")
    private Long sr;

    /**
     * dispatchVendorId.
     */
    @Column(name = "dispatch_vendor_id")
    private Long dispatchVendorId;

    /**
     * note.
     */
    @Column(name = "note")
    private String note;

    /**
     * level.
     */
    @Column(name = "level")
    private String level;

    /**
     * post_date.
     */
    @Column(name = "post_date")
    private Date postDate;

    /**
     * posted_by.
     */
    @Column(name = "posted_by")
    private String postedBy;

    /**
     * end_id.
     */
    @Column(name = "end_id")
    private Long endId;

    /**
     * note_parsed.
     */
    @Column(name = "note_parsed")
    private Long noteParsed;


    @Override
    public Long getId() {
        return id;
    }

    public Long getFtdiDispatchId() {
        return ftdiDispatchId;
    }

    public void setFtdiDispatchId(final Long ftdiDispatchId) {
        this.ftdiDispatchId = ftdiDispatchId;
    }

    public Long getSr() {
        return sr;
    }

    public void setSr(final Long sr) {
        this.sr = sr;
    }

    public Long getDispatchVendorId() {
        return dispatchVendorId;
    }

    public void setDispatchVendorId(final Long dispatchVendorId) {
        this.dispatchVendorId = dispatchVendorId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(final Date postDate) {
        this.postDate = postDate;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(final String postedBy) {
        this.postedBy = postedBy;
    }

    public Long getEndId() {
        return endId;
    }

    public void setEndId(final Long endId) {
        this.endId = endId;
    }

    public Long getNoteParsed() {
        return noteParsed;
    }

    public void setNoteParsed(final Long noteParsed) {
        this.noteParsed = noteParsed;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(final String level) {
        this.level = level;
    }

    public Long getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(final Long legacyId) {
        this.legacyId = legacyId;
    }
}
