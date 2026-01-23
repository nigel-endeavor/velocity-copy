 CREATE OR REPLACE VIEW pbi_iss_pvm_service_report AS

SELECT l.client_location_id AS 'Client Service ID',
       service_status AS Status,
       NULL AS 'Account Segment',
       mc.company_name AS 'End Customer',
       ((5 * ((TO_DAYS(NOW()) -
               TO_DAYS((SELECT last_status_change
                        FROM service
                        WHERE (service_id = s.service_id)))) DIV
              7)) + SUBSTR(
		        '0123444401233334012222340111123400012345001234550',
		        (((7 *
		           WEEKDAY((SELECT last_status_change
		                    FROM service
		                    WHERE (service_id = s.service_id)))) +
		          WEEKDAY(NOW())) +
		         1), 1)) AS 'Status Age',
       NULL AS Project,
       prov.display_name AS 'Assigned To',
       vpm.display_name AS 'Project Manager',
       client_project_manager AS 'Client Project Manager',
       media_type AS 'Access Method',
       s.carrier AS Carrier,
       aa.field_tech_name AS 'LEC Tech Name',
       aa.field_tech_phone AS 'LEC Tech #',
       aa.field_tech_check_in AS 'LEC Tech Check In',
       aa.field_tech_check_out AS 'LEC Tech Check Out',
       s.carrier_order_num AS 'Supplier Order #',
       s.client_service_type AS 'Client Site Type',

       (SELECT CONVERT_TZ(smi.milestone_date, 'UTC', 'US/Eastern')
        FROM `v_service_milestone_instance` `smi`
        WHERE ((`smi`.`service_id` = `s`.`service_id`) AND
               (`smi`.`milestone_code` = 'CARRIER_ORDER_SUBMITTED'))) AS 'Site Submit Date',
       (SELECT CONVERT_TZ(smi.milestone_date, 'UTC', 'US/Eastern')
        FROM `v_service_milestone_instance` `smi`
        WHERE ((`smi`.`service_id` = `s`.`service_id`) AND
               (`smi`.`milestone_code` = 'NETWORK_PROVIDER_FOC'))) AS 'Conf FOC',
       (SELECT CONVERT_TZ(smi.milestone_date, 'UTC', 'US/Eastern')
        FROM `v_service_milestone_instance` `smi`
        WHERE ((`smi`.`service_id` = `s`.`service_id`) AND
               (`smi`.`milestone_code` = 'ACTIVATION_REQUESTED'))) AS 'Activation Req',
       carrier_circuit_id AS 'Site DSL #',
       NULL AS 'DSL Line Type',
       jn.note AS 'Journal Note',
       CONVERT_TZ(jn.created_date, 'UTC', 'US/Eastern') AS 'JN Last Update',
       null AS '# IPNETW',
       null AS '# VOIP',
       0 AS '# Aircard',
       CONCAT(IFNULL(s.media_type, ''), IF((s.service_type IS NULL), '', CONCAT(' ', s.service_type)),
              IF((s.speed IS NULL), '', CONCAT(' ', s.speed))) AS 'Ticket Sub Product Type',
       a.address_1 AS 'Site Street',
       a.address_2 AS 'Site Address 2',
       a.city AS 'City',
       a.state_province AS 'State',
       a.postal_code AS 'Zip',
       (SELECT c.phone
        FROM contact c
             JOIN location_contact lc ON c.contact_id = lc.contact_id
        WHERE c.contact_type = 'LCON'
		      AND lc.location_id = l.location_id
        LIMIT 1) AS 'LCON Phone',
       NULL AS 'Client Due Date',
       location_name AS 'Site Name',
       bs.network_protocol AS 'Network Protocol',
       bs.pppoe_username AS 'PPPoE Username',
       bs.pppoe_password AS 'PPPoE Password',
       lan_ips AS 'Static IPs',
       lan_subnet AS 'Subnet Mask',
       lan_gateway AS 'Gateway IP',
       dns1 AS 'Primary DSN',
       dns2 AS 'Secondary DSN',
       jeop_description AS 'Jeop Code',
       CONVERT_TZ(ji.start_date, 'UTC', 'US/Eastern') AS 'Jeop Start Date',
       ji.note AS 'Jeop Note',
       s.client_service_info AS 'Client Site Info',
       IF(has_icb = 1, 'True', 'False') AS 'Site ICB',
       NULL AS 'Estimated TTU',
       ai.primary_root_cause AS 'Primary RC',
       ai.secondary_root_cause AS 'Secondary RC',
       ai.tertiary_root_cause AS 'Tertiary RC',
       rt.template_name AS 'Requirments Template',
       if(s.service_id IN (136, 145, 175, 178, 196, 220, 223, 229, 232, 241, 716, 720, 731, 739, 743, 751, 884, 985, 1059, 1260, 1340, 1529,
                           1532, 1538, 1970, 1975, 3206, 3209, 3363, 3366, 4559, 4603, 4621, 4635, 4802, 4952, 5130, 5487), s.service_id,
       IF(rec.milestone_date < '2023-10-24', IF(s.legacy_dia_id IS NULL, s.location_id, s.legacy_dia_id), s.service_id)) AS 'Site ID',
       received_date AS 'TicketRecDate',
       s.legacy_dia_id AS 'Legacy DIA ID',
       l.location_id AS 'Location ID',
       s.service_type, s.service_id,
			 s.job_number,
			 dpc.data_prov_complete_date AS 'Data Prov Complete Date'

FROM location l
     JOIN orders o ON o.order_id = l.order_id
     JOIN company c ON o.company_id = c.company_id
     JOIN (SELECT location_id, service_id, service_type, service_status, media_type, carrier, carrier_circuit_id, speed,
                  carrier_order_num, lan_ips, lan_subnet, lan_gateway, dns1, dns2, has_icb, legacy_dia_id, job_number, client_service_type, client_service_info
           FROM service s
           WHERE (service_id IN
                  (SELECT service_id
                   FROM service
                   WHERE location_id NOT IN (SELECT DISTINCT location_id
                                             FROM service
                                             WHERE service_type IN ('DIA', 'Broadband')
		                                           AND service_status NOT IN
		                                               ('Service Complete', 'Service Cancelled',
		                                                'Change In Assignment')))
	           OR service_id IN (SELECT DISTINCT service_id
	                             FROM service
	                             WHERE service_type IN ('DIA', 'Broadband')
			                           AND service_status NOT IN
			                               ('Service Complete', 'Service Cancelled', 'Change In Assignment')))
		         AND service_status NOT IN ('Service Complete', 'Service Cancelled', 'Change In Assignment')) s
          ON l.location_id = s.location_id
     LEFT JOIN company mc ON c.master_customer_id = mc.company_id
     LEFT JOIN v_subject prov ON o.provisioner = prov.subject_id
     LEFT JOIN v_subject vpm ON o.vertek_project_manager = vpm.subject_id
     LEFT JOIN requirement_template rt ON l.requirement_template_id = rt.requirement_template_id
     LEFT JOIN address a ON l.address_id = a.address_id
     Left JOIN broadband_service bs ON s.service_id = bs.service_id
     LEFT JOIN (SELECT sn.service_id, note, n.created_date, n.created_by
                FROM note n
                     JOIN service_note sn ON n.note_id = sn.note_id
                     JOIN (SELECT sn.service_id, MAX(n.note_id) AS note_id
                           FROM note n
                                JOIN service_note sn ON n.note_id = sn.note_id
                           WHERE internal_only = 0
                           GROUP BY sn.service_id) vw ON sn.note_id = vw.note_id) jn ON s.service_id = jn.service_id
     LEFT JOIN (SELECT sj.service_id, j.jeop_instance_id, j.jeop_description, note, j.start_date
                FROM jeop_instance j
                     JOIN service_jeop_instance sj ON j.jeop_instance_id = sj.jeop_instance_id
                     JOIN (SELECT sj.service_id, MAX(j.jeop_instance_id) AS jeop_instance_id
                           FROM jeop_instance j
                                JOIN service_jeop_instance sj ON j.jeop_instance_id = sj.jeop_instance_id
                           WHERE j.end_date IS NULL
                           GROUP BY sj.service_id) vw ON sj.jeop_instance_id = vw.jeop_instance_id) ji
               ON s.service_id = ji.service_id
     LEFT JOIN (SELECT aa.service_id,
                       aa.activation_attempt_id,
                       aa.field_tech_name,
                       aa.field_tech_phone,
                       aa.field_tech_check_in,
                       aa.field_tech_check_out
                FROM activation_attempt aa
                     JOIN (SELECT aa.service_id, MAX(aa.activation_attempt_id) AS activation_attempt_id
                           FROM activation_attempt aa
                           GROUP BY aa.service_id) vw ON aa.activation_attempt_id = vw.activation_attempt_id) aa
               ON s.service_id = aa.service_id
     LEFT JOIN (SELECT ai.activation_attempt_id,
                       ai.activation_issue_id,
                       ai.primary_root_cause,
                       ai.secondary_root_cause,
                       ai.tertiary_root_cause
                FROM activation_issue ai
                     JOIN (SELECT ai.activation_attempt_id, MAX(ai.activation_issue_id) AS activation_issue_id
                           FROM activation_issue ai
                           GROUP BY ai.activation_attempt_id) vw
                          ON ai.activation_issue_id = vw.activation_issue_id) ai
               ON aa.activation_attempt_id = ai.activation_attempt_id
Left Join (SELECT smi.service_id, DATE_FORMAT(CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern'), '%m/%d/%Y') AS received_date,
                  milestone_date
        FROM service svc
             JOIN service_milestone_instance smi ON svc.service_id = smi.service_id
             JOIN milestone_instance mi ON smi.milestone_instance_id = mi.milestone_instance_id
             JOIN milestone m ON mi.milestone_id = m.milestone_id
        WHERE m.milestone_code = 'RECEIVED') rec ON s.service_id = rec.service_id
Left Join (SELECT smi.service_id, DATE_FORMAT(CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern'), '%m/%d/%Y') AS data_prov_complete_date,
                  milestone_date
        FROM service svc
             JOIN service_milestone_instance smi ON svc.service_id = smi.service_id
             JOIN milestone_instance mi ON smi.milestone_instance_id = mi.milestone_instance_id
             JOIN milestone m ON mi.milestone_id = m.milestone_id
        WHERE m.milestone_code = 'DATA_PROVISIONING_COMPLETE') dpc ON s.service_id = rec.service_id
WHERE l.tenant_id = (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');



select * from milestone
