CREATE OR REPLACE VIEW v_manage_services AS
SELECT s.service_id,
       s.location_id,
       l.client_location_id,
       CONCAT(a.address_1,
              IF(LENGTH(a.address_2), CONCAT('\n', a.address_2), ''),
              '\n', a.city, ', ', a.state_province
	       ) AS address,
       s.order_id,
       s.service_status,
       o.provisioner,
       o.client_project_manager AS project_manager,
       s.carrier,
       (SELECT milestone_date
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
		      AND smi.milestone_code = 'CUSTOMER_DESIRED_DUE') AS customer_desired_due,
       (SELECT milestone_date
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
		      AND smi.milestone_code = 'SITE_SURVEY_SUBMIT') AS site_survey_submit,
       (SELECT milestone_date
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
		      AND smi.milestone_code = 'SITE_SURVEY_DUE') AS site_survey_due,
       (SELECT milestone_date
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
		      AND smi.milestone_code = 'CARRIER_ORDER_SUBMITTED') AS carrier_order_submitted,
       (SELECT milestone_date
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
		      AND smi.milestone_code = 'NETWORK_PROVIDER_FOC') AS network_provider_foc,
       (SELECT milestone_date
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
		      AND smi.milestone_code = 'DATA_PROVISIONING_COMPLETE') AS data_provisioning_complete,
       (SELECT milestone_name
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
        ORDER BY milestone_date DESC, milestone_instance_id DESC
        LIMIT 1) AS greatest_milestone_name,
       (SELECT milestone_date
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
        ORDER BY milestone_date DESC, milestone_instance_id DESC
        LIMIT 1) AS greatest_milestone_date,
       l.client_location_type,
       l.client_location_info,
       c.company_name,
       CONCAT(COALESCE(s.download_speed, ''), '/', COALESCE(s.upload_speed, '')) AS speed,
       s.service_type,
       IF((SELECT COUNT(*)
           FROM v_jeops_union vju2
           WHERE vju2.service_id = s.service_id
		         AND jeop_level IN ('Order', 'Location', 'Service')
		         AND vju2.end_date IS NULL), 1, 0) AS show_jeop_icon,
       IF(s.service_status NOT IN ('Complete', 'Cancelled', 'On Hold', 'Change in Assignment') AND
          (SELECT COUNT(*)
           FROM v_note_union vnu
           WHERE vnu.service_id = s.service_id
		         AND
#           # calculates datediff excluding weekends
		           (5 * (DATEDIFF(NOW(), vnu.created_date) DIV 7) + MID('0123444401233334012222340111123400012345001234550',
		                                                                7 * WEEKDAY(vnu.created_date) + WEEKDAY(NOW()) + 1,
		                                                                1)) > 5), 1, 0) AS show_note_icon,
       s.version,
       s.tenant_id,
       (SELECT vju.jeop_description
        FROM v_jeops_union vju
        WHERE vju.end_date IS NULL
		      AND vju.service_id = s.service_id
        ORDER BY vju.jeop_instance_id DESC
        LIMIT 1) AS open_jeop,
       (SELECT n.note
        FROM note n
             JOIN service_note sn ON n.note_id = sn.note_id
        WHERE sn.service_id = s.service_id
        ORDER BY n.created_date DESC
        LIMIT 1) AS latest_note
FROM service s
     JOIN location l ON s.location_id = l.location_id
     JOIN orders o ON s.order_id = o.order_id
     JOIN company c ON o.company_id = c.company_id
     LEFT JOIN address a ON l.address_id = a.address_id;
