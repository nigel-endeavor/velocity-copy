package com.endeavorms.velocity.qto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.MoreObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * From platform.
 * @author mmeehan
 * @since 1.5.0 - 11/29/12 3:01 PM
 */
@Entity
@Table(name = "tenant")
@JsonIgnoreProperties("tenantSubjectList")
public class Tenant extends StandardVersionedBaseEntity {

    /** Unique identifier. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenant_id")
    private Long id;

    /** Tenant name. */
    @Column(name = "name")
    private String name;

    /** Active flag.*/
    @Column(name = "active")
    private boolean active = true;



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


    public boolean isActive() {
        return active;
    }
    public void setActive(final boolean active) {
        this.active = active;
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
