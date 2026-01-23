package com.vertek.corporate.qto.contact.masterCustomer;

import com.vertek.corporate.qto.contact.Contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
