package com.vertek.corporate.qto.customfield.field;

import com.vertek.corporate.qto.common.StandardBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

