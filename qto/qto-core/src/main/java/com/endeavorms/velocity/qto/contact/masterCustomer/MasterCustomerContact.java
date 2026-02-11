package com.endeavorms.velocity.qto.contact.masterCustomer;

import com.endeavorms.velocity.qto.contact.Contact;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "master_customer_contact")
public class MasterCustomerContact extends Contact {

      /** The master customer this contact is associated with. */
    @Column(name = "master_customer_contact_id")
    private Long masterCustomerContactId;

    public Long getMasterCustomerContactId() {
        return masterCustomerContactId;
    }

    public void setMasterCustomerContactId(final Long masterCustomerContactId) {
        this.masterCustomerContactId = masterCustomerContactId;
    }
}
