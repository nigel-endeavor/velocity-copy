CREATE OR REPLACE VIEW v_manage_services AS
SELECT s.service_id,
       s.location_id,
       s.order_type,
       l.client_location_id,
       CONCAT(a.address_1,
              IF(LENGTH(a.address_2), CONCAT('\n', a.address_2), ''),
              '\n', a.city, ', ', a.state_province, ' ', a.postal_code
           ) AS address,
       a.address_1, a.address_2, a.city, a.state_province, a.postal_code,
       s.order_id,
       s.service_status,
       COALESCE((select display_name from v_subject where subject_id = provisioner), 'Unassigned') as provisioner,
       o.client_project_manager AS project_manager,
       s.carrier,
       s.client_service_id,
       s.service_mrc,
       s.service_nrc,
       s.progress_percentage,
       vsmi.customer_requested_install,
       vsmi.site_survey_submit,
       vsmi.site_survey_due,
       vsmi.carrier_order_submitted,
       vsmi.network_provider_foc,
       vsmi.data_provisioning_complete,
       vsmi.created,
       (SELECT milestone_name
            FROM v_service_milestone_instance smi
            WHERE smi.service_id = s.service_id
            ORDER BY milestone_date DESC, milestone_instance_id DESC
            LIMIT 1
       ) AS greatest_milestone_name,
       (SELECT milestone_date
            FROM v_service_milestone_instance smi
            WHERE smi.service_id = s.service_id
            ORDER BY milestone_date DESC, milestone_instance_id DESC
            LIMIT 1
        ) AS greatest_milestone_date,

       l.client_location_type,
       l.client_location_info,
       lcon.phone as lcon_phone,
       l.level_of_effort,
       (SELECT display_name FROM v_subject WHERE subject_id = vertek_project_manager) AS vertek_project_manager,
       c.company_name,
       c.company_id,
       pc.company_name as parent_company_name,
       pc.company_id as master_customer_id,
       CONCAT(COALESCE(s.download_speed, ''), '/', COALESCE(s.upload_speed, '')) AS speed,
       s.service_type,
       IF((SELECT COUNT(*)
           FROM v_jeops_union vju2
           WHERE vju2.service_id = s.service_id
             AND jeop_level IN ('Order', 'Location', 'Service')
             AND vju2.end_date IS NULL), 1, 0) AS show_jeop_icon,
       IF(s.service_status NOT IN ('Service Complete', 'Service Cancelled', 'On Hold', 'Change in Assignment') AND
#           # calculates datediff excluding weekends
          (5 * (DATEDIFF(NOW(), (SELECT created_date from note where note_id = latest_note.note_id)) DIV 7) + MID('0123444401233334012222340111123400012345001234550',
                                                                                                                  7 * WEEKDAY((SELECT created_date from note where note_id = latest_note.note_id)) + WEEKDAY(NOW()) + 1,
                                                                                                                  1)) > 5, 1, 0) AS show_note_icon,
       COALESCE(DATEDIFF(NOW(), last_status_change), 0) as status_age,
       s.version,
       s.tenant_id,
       s.active,
       (SELECT vju.jeop_description
            FROM v_jeops_union vju
            WHERE vju.end_date IS NULL
              AND vju.service_id = s.service_id
            ORDER BY vju.jeop_instance_id DESC
            LIMIT 1) AS open_jeop,
       SUBSTRING((SELECT note from note where note_id = latest_note.note_id), 1, 500) AS latest_note
FROM service s
         JOIN location l ON s.location_id = l.location_id
         LEFT OUTER JOIN location_contact lc on l.location_id = lc.location_id
         LEFT OUTER JOIN contact lcon on lc.contact_id = lcon.contact_id
         JOIN orders o ON s.order_id = o.order_id
         JOIN company c ON o.company_id = c.company_id
         LEFT JOIN company pc ON c.master_customer_id = pc.company_id
         LEFT JOIN address a ON l.address_id = a.address_id
         LEFT JOIN (
            SELECT
                s.service_id,
                MAX(CASE WHEN vsmi.milestone_code = 'CUSTOMER_REQUESTED_INSTALL' THEN vsmi.milestone_date END) AS customer_requested_install,
                MAX(CASE WHEN vsmi.milestone_code = 'SITE_SURVEY_SUBMIT' THEN vsmi.milestone_date END) AS site_survey_submit,
                MAX(CASE WHEN vsmi.milestone_code = 'SITE_SURVEY_DUE' THEN vsmi.milestone_date END) AS site_survey_due,
                MAX(CASE WHEN vsmi.milestone_code = 'CARRIER_ORDER_SUBMITTED' THEN vsmi.milestone_date END) AS carrier_order_submitted,
                MAX(CASE WHEN vsmi.milestone_code = 'NETWORK_PROVIDER_FOC' THEN vsmi.milestone_date END) AS network_provider_foc,
                MAX(CASE WHEN vsmi.milestone_code = 'DATA_PROVISIONING_COMPLETE' THEN vsmi.milestone_date END) AS data_provisioning_complete,
                MAX(CASE WHEN vsmi.milestone_code = 'CREATED' THEN vsmi.milestone_date END) AS created
            FROM v_service_milestone_instance vsmi
            INNER JOIN service s ON vsmi.service_id = s.service_id
            GROUP BY s.service_id
         ) vsmi ON s.service_id = vsmi.service_id
         LEFT JOIN (SELECT MAX(n.note_id) as note_id, service_id
                    FROM note n
                         JOIN service_note sn ON n.note_id = sn.note_id
                    GROUP BY service_id) latest_note on latest_note.service_id = s.service_id;
