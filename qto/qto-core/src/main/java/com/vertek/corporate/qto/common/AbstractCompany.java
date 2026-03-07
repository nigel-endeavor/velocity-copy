package com.vertek.corporate.qto.common;

import com.google.common.base.MoreObjects;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

/**
 * Base class for Company implementations.
 * @author <a href="mailto:rconnolly@vertek.com">rconnolly</a>
 * @since 1.5.0 - 2/17/13 9:16 PM
 */
@MappedSuperclass
public abstract class AbstractCompany extends AbstractTenantOwnedEntity {

    /** System identifier.*/
    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** A universally unique identifier that we can expose to API clients.*/
    @Column(name = "company_uuid")
    private String uuid;

    /** The Company's name.*/
    @NotNull
    @Column(name = "company_name")
    private String name;

    /** Description of the type of Company this is.*/
    @Column(name = "company_type")
    private String type;

    /** Flag indicating whether this is an "active" Company or not.*/
    @Column(name = "company_active")
    private boolean active;

    /** Optional field inicating the primary business sector of the company. */
    @Column(name = "business_sector")
    private String businessSector;



    public Long getId() {
        return id;
    }
    @SuppressWarnings("unused")
    private void setId(final Long id) {
        this.id = id;
    }


    public String getUuid() {
        return uuid;
    }
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }


    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }


    public String getType() {
        return type;
    }
    public void setType(final String type) {
        this.type = type;
    }


    public boolean isActive() {
        return active;
    }
    public void setActive(final boolean active) {
        this.active = active;
    }

    public String getBusinessSector() {
        return businessSector;
    }
    public void setBusinessSector(final String businessSector) {
        this.businessSector = businessSector;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("uuid", uuid)
                .add("name", name)
                .add("businessSector", businessSector)
                .add("type", type)
                .add("active", active)
                .toString();
    }
}
