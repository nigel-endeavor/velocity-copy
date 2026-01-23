CREATE OR REPLACE VIEW v_manage_disputes AS
SELECT d.dispute_id,
       s.service_id,
       l.location_id,
       l.order_id,
       pc.company_name as parent_company_name,
       pc.company_id as master_customer_id,
       c.company_name,
       c.company_id,
       CONCAT(a.address_1,
              IF(LENGTH(a.address_2), CONCAT(' ', a.address_2), ''),
              '\n', a.city, ', ', a.state_province, ' ', a.postal_code
           ) AS address,
       a.address_1, a.address_2, a.city, a.state_province, a.postal_code,
       s.service_type,
       d.dispute_status,
       d.dispute_type,
       d.dispute_assignment,
       s.provider,
       d.amount_disputed_mrc,
       d.amount_disputed_nrc,
       s.provider_circuit_id,
       s.provider_site_account_num,
       d.open_date,
       d.dispute_follow_up_date,
       d.credit_recognized,
       d.billing_review_complete_date,
       d.dispute_closed_date,
       d.invoice_num,
       d.vendor_tracking_num,
       d.realized_credit,
       d.realized_mrc_adjustment,
       d.annualized_mrc_save,
       s.service_mrc,
       s.service_nrc,
       s.speed,
       s.has_icb,
       s.active,
       SUBSTRING((SELECT note from note where note_id = latest_note.note_id), 1, 500) AS latest_note,
       d.tenant_id,
       d.version,
       IF(
                   d.dispute_follow_up_date IS NOT NULL
                   AND d.dispute_status != 'Dispute Closed'
                   AND  DATE(d.dispute_follow_up_date) <= CURDATE(),
                   1, 0)
           AS show_dispute_follow_up_icon
FROM dispute d
         JOIN service s on d.service_id = s.service_id
         JOIN inventory_location_service ils ON s.service_id = ils.service_id
         JOIN location l ON ils.inventory_location_id = l.location_id
         JOIN orders o ON s.order_id = o.order_id
         JOIN company c ON o.company_id = c.company_id
         LEFT JOIN company pc ON c.master_customer_id = pc.company_id
         LEFT JOIN address a ON l.address_id = a.address_id
         LEFT JOIN (SELECT MAX(n.note_id) as note_id, dispute_id
                    FROM note n
                             JOIN dispute_note sn ON n.note_id = sn.note_id
                    GROUP BY dispute_id) latest_note on latest_note.dispute_id = d.dispute_id;