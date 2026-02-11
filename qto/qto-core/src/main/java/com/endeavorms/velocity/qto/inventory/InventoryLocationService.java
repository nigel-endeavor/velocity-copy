package com.endeavorms.velocity.qto.inventory;

import com.endeavorms.velocity.qto.common.StandardBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Deprecated
@Entity
@Table(
        name = "inventory_location_service"
)
public class InventoryLocationService extends StandardBaseEntity {
    @Id
    @Column(name = "service_id")
    private Long id;

    @Column(name = "inventory_location_id")
    private Long inventoryLocationId;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getInventoryLocationId() {
        return this.inventoryLocationId;
    }


    public void setInventoryLocationId(final Long inventoryLocationId) {
        this.inventoryLocationId = inventoryLocationId;
    }


}
