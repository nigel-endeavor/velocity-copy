DROP VIEW IF EXISTS v_manage_disconnects CASCADE;
CREATE OR REPLACE VIEW v_manage_disconnects AS
SELECT s.service_id,
       s.location_id,
       o.order_id,
       l.client_location_id,
       c.company_name,
       c.company_id,
       pc.company_name                                                                             as parent_company_name,
       pc.company_id                                                                               as master_customer_id,
       s.service_type,
       CONCAT(a.address_1,
              CASE WHEN a.address_2 IS NOT NULL AND TRIM(COALESCE(a.address_2,'')) <> '' THEN CONCAT(E'\n', a.address_2) ELSE '' END,
              '\n', a.city, ', ', a.state_province, ' ', a.postal_code
           )                                                                                       AS address,
       a.address_1,
       a.address_2,
       a.city,
       a.state_province,
       a.postal_code,
       COALESCE((select display_name from v_subject where subject_id = provisioner), 'Unassigned') as provisioner,
       s.disconnect_reason,
       s.service_status,
       s.progress_percentage,
       s.carrier,
       (SELECT milestone_date
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
          AND smi.milestone_code = 'CARRIER_ORDER_SUBMITTED')                                      AS carrier_order_submitted,
       s.carrier_order_num,
       (SELECT milestone_date
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
          AND smi.milestone_code = 'CUSTOMER_REQUESTED_DISCONNECT')                                         AS customer_requested_disconnect,
       (SELECT milestone_date
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
          AND smi.milestone_code = 'NETWORK_PROVIDER_FOC')                                         AS network_provider_foc,
       (SELECT milestone_date
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
          AND smi.milestone_code = 'COMPLETE')                                                     AS complete,
       (SELECT milestone_date
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
          AND smi.milestone_code = 'CREATED')                                                      AS created,
       COALESCE(DATEDIFF(NOW(), last_status_change), 0)                                            as status_age,
       s.client_service_id,
       s.service_mrc,
       s.early_termination_fee,
       (SELECT milestone_date
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
          AND smi.milestone_code = 'BILLING_REVIEW_COMPLETE')                                      AS billing_review_complete,
       SUBSTRING((SELECT note from note where note_id = latest_note.note_id), 1, 500)              AS latest_note,
       s.order_type,
       s.version,
       s.tenant_id,
       s.active
FROM service s
         JOIN location l ON s.location_id = l.location_id
         LEFT OUTER JOIN location_contact lc on l.location_id = lc.location_id
         LEFT OUTER JOIN contact lcon on lc.contact_id = lcon.contact_id
         JOIN orders o ON s.order_id = o.order_id
         JOIN company c ON o.company_id = c.company_id
         LEFT JOIN company pc ON c.master_customer_id = pc.company_id
         LEFT JOIN address a ON l.address_id = a.address_id
         LEFT JOIN (SELECT MAX(n.note_id) as note_id, service_id
                    FROM note n
                             JOIN service_note sn ON n.note_id = sn.note_id
                    GROUP BY service_id) latest_note on latest_note.service_id = s.service_id;
