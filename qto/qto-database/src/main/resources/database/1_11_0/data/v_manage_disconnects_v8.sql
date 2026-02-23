DROP VIEW IF EXISTS v_manage_disconnects CASCADE;
CREATE OR REPLACE VIEW v_manage_disconnects AS
SELECT s.service_id,
       s.location_id,
       o.order_id,
       l.client_location_id,
       c.company_name,
       c.company_id,
       pc.company_name AS parent_company_name,
       pc.company_id AS master_customer_id,
       s.service_type,
       CONCAT(l.address_1,
              CASE WHEN l.address_2 IS NOT NULL AND TRIM(COALESCE(l.address_2,'')) <> '' THEN CONCAT(' ', l.address_2) ELSE '' END,
              '\n', l.city, ', ', l.state_province, ' ', l.postal_code
	       ) AS address,
       l.address_1,
       l.address_2,
       l.city,
       l.state_province,
       l.postal_code,
       COALESCE((SELECT display_name FROM v_subject WHERE subject_id = o.provisioner), 'Unassigned') AS provisioner,
       s.disconnect_reason,
       s.service_status,
       s.progress_percentage,
       s.provider,
       vsmi.provider_order_submitted,
       s.provider_order_num,
       vsmi.customer_requested_disconnect,
       vsmi.network_provider_foc,
       vsmi.complete,
       vsmi.created,
       COALESCE(DATEDIFF(NOW(), s.last_status_change), 0) AS status_age,
       s.client_service_id,
       ps.service_mrc,
       s.early_termination_fee,
       vsmi.billing_review_complete,
       SUBSTRING((SELECT note FROM note WHERE note_id = latest_note.note_id), 1, 500) AS latest_note,
       s.order_type,
       s.version,
       s.tenant_id,
       s.active,
       s.project_name,
       s.linked,
       s.bundled,
       IF(s.service_status NOT IN ('Service Complete', 'Service Cancelled', 'On Hold', 'Change in Assignment') AND
#           # calculates datediff excluding weekends
          (5 * (DATEDIFF(NOW(), (SELECT created_date FROM note WHERE note_id = latest_note.note_id)) DIV 7) +
           CAST(SUBSTRING(
               7 * WEEKDAY((SELECT created_date FROM note WHERE note_id = latest_note.note_id)) + WEEKDAY(NOW()) + 1 FROM 0123444401233334012222340111123400012345001234550 FOR 1) AS integer)) > 5, 1, 0) AS show_note_icon,
       IF((SELECT COUNT(*)
           FROM v_jeops_union vju2
           WHERE vju2.service_id = s.service_id
             AND jeop_level IN ('Order', 'Location', 'Service')
             AND vju2.end_date IS NULL), 1, 0) AS show_jeop_icon
FROM service s
     JOIN location l ON s.location_id = l.location_id
     JOIN orders o ON s.order_id = o.order_id
     JOIN company c ON o.company_id = c.company_id
     LEFT JOIN company pc ON c.master_customer_id = pc.company_id
     LEFT JOIN (SELECT s.service_id,
                       MAX(CASE WHEN vsmi.milestone_code = 'CUSTOMER_REQUESTED_DISCONNECT'
	                                THEN vsmi.milestone_date END) AS customer_requested_disconnect,
                       MAX(CASE WHEN vsmi.milestone_code = 'provider_ORDER_SUBMITTED'
	                                THEN vsmi.milestone_date END) AS provider_order_submitted,
                       MAX(CASE WHEN vsmi.milestone_code = 'NETWORK_PROVIDER_FOC'
	                                THEN vsmi.milestone_date END) AS network_provider_foc,
                       MAX(CASE WHEN vsmi.milestone_code = 'COMPLETE'
	                                THEN vsmi.milestone_date END) AS complete,
                       MAX(CASE WHEN vsmi.milestone_code = 'CREATED' THEN vsmi.milestone_date END) AS created,
                       MAX(CASE WHEN vsmi.milestone_code = 'BILLING_REVIEW_COMPLETE'
	                                THEN vsmi.milestone_date END) AS billing_review_complete
                FROM v_service_milestone_instance vsmi
                     INNER JOIN service s ON vsmi.service_id = s.service_id
                GROUP BY s.service_id) vsmi ON s.service_id = vsmi.service_id
     LEFT JOIN (SELECT MAX(n.note_id) AS note_id, service_id
                FROM note n
                     JOIN service_note sn ON n.note_id = sn.note_id
                GROUP BY service_id) latest_note ON latest_note.service_id = s.service_id
    LEFT JOIN service ps ON s.parent_service_id = ps.service_id;
