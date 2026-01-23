package com.vertek.corporate.qto.milestone;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.common.base.MoreObjects;
import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * MilestoneSet describes various "sets" of milestones: an application may use one or more sets of milestones
 * specific to it. For example,
 *
 * MilestoneSet
 * - Ethernet Over Copper Migration
 * -- {@link MilestoneDisplaySet}
 * --- Display Set 1
 * --- Display Set 2
 * - Some other Application
 * - Another App
 * - Another App's second set
 *
 * @author rconnolly
 * @since 1.0
 */
@Entity
@JacksonXmlRootElement
@Table(name = "milestone_set")
@JsonIgnoreProperties(value = { "displaySets" })
public class MilestoneSet extends StandardVersionedBaseEntity {

    /** serial version.*/
    private static final long serialVersionUID = -4404932792612546734L;

    /** System identifier.*/
    @Id
    @JacksonXmlProperty(isAttribute = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_set_id")
    private Long id;

    /** the set name.*/
    @Column(name = "milestone_set_name")
    private String name;

    /** Flag indicating whether it is active or not.*/
    @Column(name = "milestone_set_active")
    private boolean active;

    /** All associated MilestoneDisplaySets.*/
    @OneToMany
    @JsonIgnore
    private Set<MilestoneDisplaySet> displaySets;


    @Override
    public Long getId() {
        return id;
    }
    @SuppressWarnings("unused")
    private void setId(final Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }


    public boolean isActive() {
        return active;
    }
    public void setActive(final boolean active) {
        this.active = active;
    }


    public Set<MilestoneDisplaySet> getDisplaySets() {
        return displaySets;
    }
    public void setDisplaySets(final Set<MilestoneDisplaySet> displaySets) {
        this.displaySets = displaySets;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("active", active)
                .toString();
    }

}