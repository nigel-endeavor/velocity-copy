package com.vertek.corporate.qto.customfield.field;

import com.vertek.corporate.qto.common.AbstractTenantOwnedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "custom_field")
@Inheritance(strategy = InheritanceType.JOINED)
public class CustomField extends AbstractTenantOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_field_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "label")
    private String label;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CustomFieldType type;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "required")
    private Boolean required;

    @OneToMany(fetch = FetchType.EAGER)
//    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "custom_field_id", referencedColumnName = "custom_field_id")
    private List<CustomFieldTab> tabs;

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public CustomFieldType getType() {
        return type;
    }

    public void setType(final CustomFieldType type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(final Boolean required) {
        this.required = required;
    }

    public List<CustomFieldTab> getTabs() {
        return tabs;
    }

    public void setTabs(final List<CustomFieldTab> tabs) {
        this.tabs = tabs;
    }
}
