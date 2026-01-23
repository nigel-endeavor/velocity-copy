# file attachment
ALTER TABLE file_attachment
    ADD COLUMN master_customer_id INT NULL;

ALTER TABLE file_attachment
    ADD CONSTRAINT fk_file_attachment_master_customer_id
        FOREIGN KEY (master_customer_id) REFERENCES company(company_id);

UPDATE file_attachment
    LEFT JOIN location_file_attachment lfa ON file_attachment.file_attachment_id = lfa.file_attachment_id
    LEFT JOIN service_file_attachment sfa ON file_attachment.file_attachment_id = sfa.file_attachment_id
SET file_attachment.master_customer_id = CASE WHEN lfa.location_id IS NOT NULL THEN (SELECT master_customer_id FROM location WHERE location_id = lfa.location_id)
                                              WHEN sfa.service_id IS NOT NULL THEN (SELECT master_customer_id FROM service WHERE service_id = sfa.service_id)
                                         END;


# requirement template
ALTER TABLE requirement_template
    ADD COLUMN master_customer_id INT NULL;

ALTER TABLE requirement_template
    ADD CONSTRAINT fk_requirement_template_master_customer_id
        FOREIGN KEY (master_customer_id) REFERENCES company(company_id);

UPDATE requirement_template
    JOIN company c ON requirement_template.company_id = c.company_id
SET requirement_template.master_customer_id = c.master_customer_id;


# note
ALTER TABLE note
    ADD COLUMN master_customer_id INT NULL;

ALTER TABLE note
    ADD CONSTRAINT fk_note_master_customer_id
        FOREIGN KEY (master_customer_id) REFERENCES company(company_id);

UPDATE note
    LEFT JOIN dispute_note dn ON note.note_id = dn.note_id
    LEFT JOIN order_note orn ON note.note_id = orn.note_id
    LEFT JOIN location_note ln ON note.note_id = ln.note_id
    LEFT JOIN service_note sn ON note.note_id = sn.note_id
    LEFT JOIN jeop_instance_note jin ON note.note_id = jin.note_id
SET note.master_customer_id = CASE WHEN dn.dispute_id IS NOT NULL THEN (SELECT master_customer_id FROM dispute WHERE dispute_id = dn.dispute_id)
                                   WHEN orn.order_id IS NOT NULL THEN (SELECT master_customer_id FROM orders WHERE order_id = orn.order_id)
                                   WHEN ln.location_id IS NOT NULL THEN (SELECT master_customer_id FROM location WHERE location_id = ln.location_id)
                                   WHEN sn.service_id IS NOT NULL THEN (SELECT master_customer_id FROM service WHERE service_id = sn.service_id)
                                   WHEN jin.jeop_instance_id IS NOT NULL THEN (SELECT master_customer_id FROM jeop_instance WHERE jeop_instance_id = jin.jeop_instance_id)
                              END;


# jeop
ALTER TABLE jeop_instance
    ADD COLUMN master_customer_id INT NULL;

ALTER TABLE jeop_instance
    ADD CONSTRAINT fk_jeop_instance_master_customer_id
        FOREIGN KEY (master_customer_id) REFERENCES company(company_id);

UPDATE jeop_instance
    LEFT JOIN order_jeop_instance oji ON jeop_instance.jeop_instance_id = oji.jeop_instance_id
    LEFT JOIN location_jeop_instance lji ON jeop_instance.jeop_instance_id = lji.jeop_instance_id
    LEFT JOIN service_jeop_instance sji ON jeop_instance.jeop_instance_id = sji.jeop_instance_id
SET jeop_instance.master_customer_id = CASE WHEN oji.order_id IS NOT NULL THEN (SELECT master_customer_id FROM orders WHERE order_id = oji.order_id)
                                            WHEN lji.location_id IS NOT NULL THEN (SELECT master_customer_id FROM location WHERE location_id = lji.location_id)
                                            WHEN sji.service_id IS NOT NULL THEN (SELECT master_customer_id FROM service WHERE service_id = sji.service_id)
                                       END;


# contact
ALTER TABLE contact
    ADD COLUMN master_customer_id INT NULL;

ALTER TABLE contact
    ADD CONSTRAINT fk_contact_master_customer_id
        FOREIGN KEY (master_customer_id) REFERENCES company(company_id);

UPDATE contact
    LEFT JOIN order_contact oc ON contact.contact_id = oc.contact_id
    LEFT JOIN location_contact lc ON contact.contact_id = lc.contact_id
SET contact.master_customer_id = CASE WHEN oc.order_id IS NOT NULL THEN (SELECT master_customer_id FROM orders WHERE order_id = oc.order_id)
                                      WHEN lc.location_id IS NOT NULL THEN (SELECT master_customer_id FROM location WHERE location_id = lc.location_id)
                                 END;


# dispute
ALTER TABLE dispute
    ADD COLUMN master_customer_id INT NULL;

ALTER TABLE dispute
    ADD CONSTRAINT fk_dispute_master_customer_id
        FOREIGN KEY (master_customer_id) REFERENCES company(company_id);

UPDATE dispute
    JOIN service s ON dispute.service_id = s.service_id
SET dispute.master_customer_id = s.master_customer_id;


# surcharge
ALTER TABLE surcharge
    ADD COLUMN master_customer_id INT NULL;

ALTER TABLE surcharge
    ADD CONSTRAINT fk_surcharge_master_customer_id
        FOREIGN KEY (master_customer_id) REFERENCES company(company_id);

UPDATE surcharge
    LEFT JOIN service_surcharge ss ON surcharge.surcharge_id = ss.surcharge_id
SET surcharge.master_customer_id = CASE WHEN ss.service_id IS NOT NULL THEN (SELECT master_customer_id FROM service WHERE service_id = ss.service_id)
                                       END;


# message thread
ALTER TABLE message_thread
    ADD COLUMN master_customer_id INT NULL;

ALTER TABLE message_thread
    ADD CONSTRAINT fk_message_thread_master_customer_id
        FOREIGN KEY (master_customer_id) REFERENCES company(company_id);

UPDATE message_thread
    JOIN location l on message_thread.location_id = l.location_id
SET message_thread.master_customer_id = l.master_customer_id;


# milestone instance
ALTER TABLE milestone_instance
    ADD COLUMN master_customer_id INT NULL;

ALTER TABLE milestone_instance
    ADD CONSTRAINT fk_milestone_instance_master_customer_id
        FOREIGN KEY (master_customer_id) REFERENCES company(company_id);

UPDATE milestone_instance
    LEFT JOIN order_milestone_instance omi ON milestone_instance.milestone_instance_id = omi.milestone_instance_id
    LEFT JOIN location_milestone_instance lmi ON milestone_instance.milestone_instance_id = lmi.milestone_instance_id
    LEFT JOIN service_milestone_instance smi ON milestone_instance.milestone_instance_id = smi.milestone_instance_id
SET milestone_instance.master_customer_id = CASE WHEN omi.order_id IS NOT NULL THEN (SELECT master_customer_id FROM orders WHERE order_id = omi.order_id)
                                                 WHEN lmi.location_id IS NOT NULL THEN (SELECT master_customer_id FROM location WHERE location_id = lmi.location_id)
                                                 WHEN smi.service_id IS NOT NULL THEN (SELECT master_customer_id FROM service WHERE service_id = smi.service_id)
                                            END;

