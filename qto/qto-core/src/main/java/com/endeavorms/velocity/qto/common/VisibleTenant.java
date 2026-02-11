package com.endeavorms.velocity.qto.common;

import com.google.common.base.MoreObjects;
import com.querydsl.core.annotations.QueryProjection;

/**
 * DTO for Tenant selection.
 * @author <a href="mailto:rconnolly@vertek.com">rconnolly</a>
 * @since 1.5.0 - 2/17/13 12:54 PM
 */
public class VisibleTenant {

    /** System Identifier.*/
    private Long id;

    /** Display Name.*/
    private String name;

    /** Flag indicating whether this is the currently selected Tenant or not.*/
    private boolean active;


    /** Default constructor.*/
    public VisibleTenant() {
    }

    /**
     * Constructor.
     * @param id identifier.
     * @param name name.
     * @param active flag indicating whether this is the currently visible Tenant or not.
     */
    @QueryProjection
    public VisibleTenant(final Long id, final String name, final boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }


    public Long getId() {
        return id;
    }
    public void setId(final Long id) {
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



    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("active", active)
                .toString();
    }
}
