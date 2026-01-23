package com.vertek.corporate.qto.milestone;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.common.base.MoreObjects;
import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Instances of {@link MilestoneDisplaySet} belong to a single {@link MilestoneSet} and model various groups of
 * milestones, which can be displayed in the UI. There (multiple display sets for various groupings of milestones).
 *
 * @author rconnolly
 * @since 1.0
 */
@Entity
@JacksonXmlRootElement
@Table(name = "milestone_display_set")
public class MilestoneDisplaySet extends StandardVersionedBaseEntity {

    /** serial version.*/
    private static final long serialVersionUID = 4924859873489180466L;

    /** System identifier.*/
    @Id
    @Column(name = "milestone_display_set_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The display group.*/
    @Column(name = "display_group")
    private String displayGroup;

    /** The display set label used by the UI.*/
    @Column(name = "display_set_label")
    private String displaySetLabel;

    /** The DisplaySet Includes.*/
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "milestone_display_set_id", referencedColumnName = "milestone_display_set_id")
    @OrderBy("milestone_sequence")
    private List<MilestoneDisplaySetInclude> displaySetIncludes = new ArrayList<>();

    /** The display type.*/
    @Column(name = "display_type")
    private String displayType;

    @Override
    public Long getId() {
        return id;
    }
    @SuppressWarnings("unused")
    private void setId(final Long id) {
        this.id = id;
    }


    public String getDisplayGroup() {
        return displayGroup;
    }
    public void setDisplayGroup(final String displayGroup) {
        this.displayGroup = displayGroup;
    }

    public String getDisplaySetLabel() {
        return displaySetLabel;
    }
    public void setDisplaySetLabel(final String displaySetLabel) {
        this.displaySetLabel = displaySetLabel;
    }

    public List<MilestoneDisplaySetInclude> getDisplaySetIncludes() {
        return displaySetIncludes;
    }
    public void setDisplaySetIncludes(final List<MilestoneDisplaySetInclude> displaySetIncludes) {
        this.displaySetIncludes = displaySetIncludes;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(final String displayType) {
        this.displayType = displayType;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("displayGroup", displayGroup)
                .add("displaySetLabel", displaySetLabel)
                .add("displayType", displayType)
                .toString();
    }
}
