package com.vertek.corporate.qto.common.lookup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.common.base.MoreObjects;
import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * Models a Lookup Type.
 *
 * @author rconnolly
 * @since 1.0
 */
@Entity
@Table(name = "lookup_type")
@JacksonXmlRootElement
@JsonIgnoreProperties(value = { "lookupValues" })
public class LookupType extends StandardVersionedBaseEntity {

    /** serial version.*/
    private static final long serialVersionUID = -770927210503234682L;

    /** System identifier.*/
    @Id
    @JacksonXmlProperty(isAttribute = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lookup_type_id")
    private Long id;

    /** The type name (type is a reserved word when working with Ember Data and is causing unintentional
     * polymorphism in this case).*/
    @Column(name = "lookup_type_descr")
    private String name;

    /** Type code.*/
    @Column(name = "lookup_type_code")
    private String typeCode;

    /** Flag indicating whether this is active or not.*/
    @Column(name = "lookup_type_active", columnDefinition = "bit default 1")
    private boolean active;

    /** Flag indicating whether this can be modified or not.*/
    @Column(name = "modifiable", columnDefinition = "bit default 0")
    private boolean modifiable;

    /**
     *  If 0 sort alphabetically by LookupValuedisplay value, if 1 sort by sort sequence, if 2 sort by LookupValue id.
     */
    @Column(name = "sort_strategy", columnDefinition = "int default 0")
    private Integer sortStrategy;

    @Column(name = "category")
    private String category;

    @Column(name = "parent_lookup_type_id")
    private Long parentLookupTypeId;

    @Transient
    private List<LookupValue> values = new ArrayList<>();

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


    public String getTypeCode() {
        return typeCode;
    }
    public void setTypeCode(final String typeCode) {
        this.typeCode = typeCode;
    }


    public boolean isActive() {
        return active;
    }
    public void setActive(final boolean active) {
        this.active = active;
    }


    public boolean isModifiable() {
        return modifiable;
    }
    public void setModifiable(final boolean modifiable) {
        this.modifiable = modifiable;
    }

    public Integer getSortStrategy() {
        return sortStrategy;
    }
    public void setSortStrategy(final Integer sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public Long getParentLookupTypeId() {
        return parentLookupTypeId;
    }

    public void setParentLookupTypeId(final Long parentLookupTypeId) {
        this.parentLookupTypeId = parentLookupTypeId;
    }

    public List<LookupValue> getValues() {
        return values;
    }

    public void setValues(final List<LookupValue> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("typeCode", typeCode)
                .add("active", active)
                .add("modifiable", modifiable)
                .toString();
    }

}
