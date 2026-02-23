DROP VIEW IF EXISTS pbi_iss_location CASCADE;
Create or replace view pbi_iss_location as
SELECT l.location_id as 'Location ID',
       p.company_name AS 'Master Customer',
       c.company_name AS 'End Customer',
       pr.display_name as 'Provisioner',
       vpm.display_name as 'Vertek Project Manager',
       ae.display_name as 'Activation Engineer',
       client_project_manager as 'Client Project Manager',
       a.address_1 AS 'Address 1',
       a.address_2 AS 'Address 2',
       a.city AS 'City',
       a.state_province AS 'State',
       a.postal_code AS 'Zip',
       client_location_id AS 'Client Location ID',
       quote_location_id AS 'Quote Location ID',
       location_name AS 'Location Name',
       location_status AS 'Location Status',
       building_type AS 'Building Type',
       phone_number as 'Location Phone Number',
       client_location_type AS 'Client Location Type',
       client_location_info AS 'Client Location Info',
       l.last_update_by as 'Last Update By',
       l.last_update_date as 'Last Update Date',
       l.active as 'Active',
       progress_percentage as 'Progress Percentage',
       legacy_transaction_type as 'Legacy Transaction Type',
       legacy_transaction_amount as 'Legacy Transaction Amount',
       l.legacy_id as 'Legacy ID',
       level_of_effort  as 'Level of Effort',
       jn.note as 'Last Note',
       jn.created_date as 'Last Note Created Date',
       jn.created_by as 'Last Note Created By'
FROM location l
     JOIN orders o ON l.order_id = o.order_id
  left join v_subject pr on o.provisioner = pr.subject_id
  left join v_subject vpm on o.vertek_project_manager = vpm.subject_id
  left join v_subject ae on o.activation_engineer = ae.subject_id
     JOIN company c ON o.company_id = c.company_id
     LEFT JOIN address a ON l.address_id = a.address_id
     LEFT JOIN company p ON c.parent_company_id = p.company_id
 left join (
select ln.location_id, note, n.created_date, n.created_by from note n join location_note ln on n.note_id = ln.note_id join (
select ln.location_id, max(n.note_id) as note_id from note n join location_note ln on n.note_id = ln.note_id
group by ln.location_id) vw on ln.note_id = vw.note_id) jn on l.location_id = jn.location_id
where l.tenant_id = (select tenant_id from v_tenant where name = 'Endeavor');


DROP VIEW IF EXISTS pbi_iss_location_contacts CASCADE;
create or replace view pbi_iss_location_contacts as
SELECT lc.location_id AS 'Location ID',
       c.contact_id AS 'Contact ID',
       first_name as 'First Name',
       last_name as 'Last Name',
       contact_active as Active,
       contact_type as 'Contact Type',
       role as Rold,
       phone as Phone,
       email as Email
FROM location_contact lc
     JOIN contact c ON lc.contact_id = c.contact_id
where tenant_id = (select tenant_id from v_tenant where name = 'Endeavor');

DROP VIEW IF EXISTS pbi_iss_location_jeops CASCADE;
create or replace view pbi_iss_location_jeops as
select lji.location_id as 'Location ID',
       lji.jeop_instance_id as 'Jeopardy Instance ID',
       ji.jeop_description as 'Jeopardy Description',
       ji.responsibility as 'Responsibility',
       ji.start_date as 'Start Date',
       ji.end_date as 'End Date',
       ji.note as 'Note',
       ji.originator as 'Originator',
       ji.assigned_to as 'Assigned To'
       from location_jeop_instance lji
join jeop_instance ji ON lji.jeop_instance_id = ji.jeop_instance_id
where tenant_id = (select tenant_id from v_tenant where name = 'Endeavor');

DROP VIEW IF EXISTS pbi_iss_location_notes CASCADE;
create or replace view pbi_iss_location_notes as
select ln.location_id as 'Location ID',
       n.note as Note,
       n.created_by as 'Created By',
       n.created_date as 'Created Date'
from location_note ln
join note n on ln.note_id = n.note_id
where tenant_id = (select tenant_id from v_tenant where name = 'Endeavor');

DROP VIEW IF EXISTS pbi_iss_location_milestone CASCADE;
create or REPLACE view pbi_iss_location_milestone as
select lmi.location_id,
       CAST(MAX(CASE WHEN (m.milestone_code = 'CANCELLED')
	                      THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                      ELSE NULL END) AS date) AS `Location Cancelled`,
       CAST(MAX(CASE WHEN (m.milestone_code = 'CHANGE_IN_ASSIGNMENT')
	                      THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                      ELSE NULL END) AS date) AS `Change In Assignment`,
       CAST(MAX(CASE WHEN (m.milestone_code = 'COMPLETE')
	                      THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                      ELSE NULL END) AS date) AS `Location Complete`,
       CAST(MAX(CASE WHEN (m.milestone_code = 'CREATED')
	                      THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                      ELSE NULL END) AS date) AS `Location Created`,
       CAST(MAX(CASE WHEN (m.milestone_code = 'CUSTOMER_CONTACT_COMPLETE')
	                      THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                      ELSE NULL END) AS date) AS `Customer Contact Complete`,
       CAST(MAX(CASE WHEN (m.milestone_code = 'DESIGN_APPROVED_BY_CUSTOMER')
	                      THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                      ELSE NULL END) AS date) AS `Design Approved By Customer`,
       CAST(MAX(CASE WHEN (m.milestone_code = 'DESIGN_SENT_TO_CUSTOMER')
	                      THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                      ELSE NULL END) AS date) AS `Design Sent To Customer`,
       CAST(MAX(CASE WHEN (m.milestone_code = 'ENGINEER_ASSIGNED')
	                      THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                      ELSE NULL END) AS date) AS `Engineer Assigned`,
       CAST(MAX(CASE WHEN (m.milestone_code = 'INITIAL_CONTACT_WITH_CUSTOMER')
	                      THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                      ELSE NULL END) AS date) AS `Initial Contact with Customer`,
       CAST(MAX(CASE WHEN (m.milestone_code = 'ON_HOLD')
	                      THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                      ELSE NULL END) AS date) AS `Location On Hold`,
       CAST(MAX(CASE WHEN (m.milestone_code = 'PROVISIONING_START')
	                      THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                      ELSE NULL END) AS date) AS `Provisioning Start`,
       CAST(MAX(CASE WHEN (m.milestone_code = 'RECEIVED')
	                      THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                      ELSE NULL END) AS date) AS `Location Received`,
       CAST(MAX(CASE WHEN (m.milestone_code = 'TDG_COMPLETE')
	                      THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                      ELSE NULL END) AS date) AS `TDG Complete`,
       CAST(MAX(CASE WHEN (m.milestone_code = 'TDG_INTERVIEW_SCHEDULED')
	                      THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                      ELSE NULL END) AS date) AS `TDG Interview Scheduled`,
       CAST(MAX(CASE WHEN (m.milestone_code = 'TDG_IN_PROGRESS')
	                      THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                      ELSE NULL END) AS date) AS `TDG In Progress`
FROM milestone_instance mi
     JOIN milestone m ON mi.milestone_id = m.milestone_id
     JOIN location_milestone_instance lmi ON mi.milestone_instance_id = lmi.milestone_instance_id
where mi.tenant_id = (select tenant_id from v_tenant where name = 'Endeavor')
Group by lmi.location_id ;

DROP VIEW IF EXISTS pbi_iss_pvm_report CASCADE;
Create or replace view pbi_iss_pvm_report as
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
       l.client_location_type AS 'Client Site Type',

       (SELECT CONVERT_TZ(smi.milestone_date, 'UTC', 'US/Eastern')
        FROM `qto`.`v_service_milestone_instance` `smi`
        WHERE ((`smi`.`service_id` = `s`.`service_id`) AND
               (`smi`.`milestone_code` = 'CARRIER_ORDER_SUBMITTED'))) AS 'Site Submit Date',
       (SELECT CONVERT_TZ(smi.milestone_date, 'UTC', 'US/Eastern')
        FROM `qto`.`v_service_milestone_instance` `smi`
        WHERE ((`smi`.`service_id` = `s`.`service_id`) AND
               (`smi`.`milestone_code` = 'NETWORK_PROVIDER_FOC'))) AS 'Conf FOC',
       (SELECT CONVERT_TZ(smi.milestone_date, 'UTC', 'US/Eastern')
        FROM `qto`.`v_service_milestone_instance` `smi`
        WHERE ((`smi`.`service_id` = `s`.`service_id`) AND
               (`smi`.`milestone_code` = 'ACTIVATION_REQUESTED'))) AS 'Activation Req',
       carrier_circuit_id AS 'Site DSL #',
       NULL AS 'DSL Line Type',
       jn.note AS 'Journal Note',
       CONVERT_TZ(jn.created_date, 'UTC', 'US/Eastern') AS 'JN Last Update',
       cntBroadband + cnt4G + cntDia AS '# IPNETW',
       cntUcaas AS '# VOIP',
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
       wan_subnet AS 'Subnet Mask',
       lan_gateway AS 'Gateway IP',
       dns1 AS 'Primary DSN',
       dns2 AS 'Secondary DSN',
       jeop_description AS 'Jeop Code',
       CONVERT_TZ(ji.start_date, 'UTC', 'US/Eastern') AS 'Jeop Start Date',
       ji.note AS 'Jeop Note',
       l.client_location_info AS 'Client Site Info',
       IF(has_icb = 1, 'True', 'False') AS 'Site ICB',
       NULL AS 'Estimated TTU',
       ai.primary_root_cause AS 'Primary RC',
       ai.secondary_root_cause AS 'Secondary RC',
       ai.tertiary_root_cause AS 'Tertiary RC',
       rt.template_name AS 'Requirments Template',
       l.location_id AS 'Site ID',
       (SELECT DATE_FORMAT(MIN(CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')), '%d/%m/%Y') AS received_date
        FROM service svc
             JOIN service_milestone_instance smi ON svc.service_id = smi.service_id
             JOIN milestone_instance mi ON smi.milestone_instance_id = mi.milestone_instance_id
             JOIN milestone m ON mi.milestone_id = m.milestone_id
        WHERE m.milestone_code = 'RECEIVED' and svc.location_id = l.location_id
        GROUP BY s.location_id) AS 'TicketRecDate'

FROM location l
     JOIN orders o ON o.order_id = l.order_id
     JOIN company c ON o.company_id = c.company_id
  left join company mc on c.parent_company_id = mc.company_id
     LEFT JOIN v_subject prov ON o.provisioner = prov.subject_id
     LEFT JOIN v_subject vpm ON o.vertek_project_manager = vpm.display_name
     LEFT JOIN requirement_template rt ON l.requirement_template_id = rt.requirement_template_id
     LEFT JOIN address a ON l.address_id = a.address_id
     LEFT JOIN
     (SELECT location_id,
             cntBroadband,
             cntDia,
             cnt4G,
             cntUcaas,
             CASE WHEN hasBroadband > 0
	                  THEN hasBroadband
                  WHEN hasDia > 0
	                  THEN hasDia
                  WHEN has4g > 0
	                  THEN has4G
                  ELSE hasUcaas
	             END AS service_id
      FROM (SELECT location_id,
                   MAX(CASE WHEN service_type = 'Broadband' THEN service_id ELSE 0 END) AS hasBroadband,
                   MAX(CASE WHEN service_type = 'DIA' THEN service_id ELSE 0 END) AS hasDia,
                   MAX(CASE WHEN service_type = '4G/5G' THEN service_id ELSE 0 END) AS has4G,
                   MAX(CASE WHEN service_type = 'UCaaS' THEN service_id ELSE 0 END) AS hasUcaas,
                   SUM(CASE WHEN service_type = 'Broadband' THEN 1 ELSE 0 END) AS cntBroadband,
                   SUM(CASE WHEN service_type = 'DIA' THEN 1 ELSE 0 END) AS cntDia,
                   SUM(CASE WHEN service_type = '4G/5G' THEN 1 ELSE 0 END) AS cnt4G,
                   SUM(CASE WHEN service_type = 'UCaaS' THEN 1 ELSE 0 END) AS cntUcaas
            FROM service
            where service_status not in ('Service Complete', 'Service Cancelled', 'Change In Assignment')
            GROUP BY location_id) iv) v ON l.location_id = v.location_id
     LEFT JOIN service s ON v.service_id = s.service_id
     LEFT JOIN broadband_service bs ON v.service_id = bs.service_id
     LEFT JOIN (SELECT sn.service_id, note, n.created_date, n.created_by
                FROM note n
                     JOIN service_note sn ON n.note_id = sn.note_id
                     JOIN (SELECT sn.service_id, MAX(n.note_id) AS note_id
                           FROM note n
                                JOIN service_note sn ON n.note_id = sn.note_id
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
where l.location_status not in ('Location Complete', 'Location Cancelled', 'Change In Assignment')
and l.tenant_id = (Select tenant_id from v_tenant where name = 'Endeavor');









