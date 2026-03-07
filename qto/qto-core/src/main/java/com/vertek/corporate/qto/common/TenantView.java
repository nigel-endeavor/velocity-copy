package com.vertek.corporate.qto.common;

import com.google.common.base.MoreObjects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(
        name = "v_tenant"
)
public class TenantView extends StandardVersionedBaseEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "tenant_id"
    )
    private Long id;
    @Column(
            name = "name"
    )
    private String name;
    @Column(
            name = "active"
    )
    private boolean active = true;

    public TenantView() {
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", this.id).add("name", this.name).add("active", this.active).toString();
    }
}
