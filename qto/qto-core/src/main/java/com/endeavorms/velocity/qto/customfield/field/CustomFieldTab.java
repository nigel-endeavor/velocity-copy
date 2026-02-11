package com.endeavorms.velocity.qto.customfield.field;

import com.endeavorms.velocity.qto.common.StandardBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "custom_field_tab")
public class CustomFieldTab extends StandardBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_field_tab_id")
    private Long id;

    @Column(name = "custom_field_id")
    private Long customFieldId;

    @Column(name = "tab")
    @Enumerated(EnumType.STRING)
    private CustomFieldTabValue tab;

    public Long getId() {
        return id;
    }

    public Long getCustomFieldId() {
        return customFieldId;
    }

    public void setCustomFieldId(final Long customFieldId) {
        this.customFieldId = customFieldId;
    }

    public CustomFieldTabValue getTab() {
        return tab;
    }

    public void setTab(final CustomFieldTabValue tab) {
        this.tab = tab;
    }
}

