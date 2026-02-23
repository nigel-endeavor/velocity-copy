DROP VIEW IF EXISTS pbi_iss_activation_attempts CASCADE;
CREATE OR REPLACE VIEW pbi_iss_activation_attempts AS
SELECT s.service_id AS 'Service ID',
       aa.activation_attempt_id AS 'Activation Attempt ID',
       CONCAT('svc-', s.service_id,
              IF(aa.activation_attempt_id IS NULL, '',
                 CONCAT('aa-', aa.activation_attempt_id))) AS 'Composite ID',
       scheduled_attempt_status AS 'Scheduled Attempt Status',
       internal_tech_assigned AS 'Internal Tech Assigned',
       field_dispatch_vendor AS 'Field Dispatch Vendor',
       ftdi_vendor_id AS 'FTDI Vendor ID',
       field_tech_name AS 'Field Tech Name',
       field_tech_phone AS 'Field Tech Phone',
       field_tech_check_in AS 'FT Check-in Time',
       field_tech_check_out AS 'FT Check-out Time',
       tested_download_speed AS 'Tested Download Speed',
       tested_upload_speed AS 'Tested Upload Speed',
       latency AS Latency,
       backup_download_speed AS 'Backup Download Speed',
       backup_upload_speed AS 'Backup Upload Speed',
       signal_rsrp AS 'Signal/RSRP',
       sinr_rsrq AS 'SINR/RSRQ',
       location_downtown_for_cutover AS 'Location Downtime For Cutover',
       closeout_code AS 'Closeout Code',
       network_complete_date AS 'Network Complete Date',
       voip_complete_date AS 'UCaaS Complete Date',
       issue_notes AS 'Internal TTU Notes',
       scheduled_check_in_time AS 'Scheduled Check-in Time',
       attempt_number 'Attempt Number',
       replace_4g_5g 'Replace 4G/5G with Broadband/DIA',
       primary_uid AS 'Primary UID',
       secondary_uid AS 'Secondary UID'
FROM service s
     LEFT JOIN activation_attempt aa ON s.service_id = aa.service_id
WHERE s.tenant_id IN (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');

DROP VIEW IF EXISTS pbi_iss_activation_issues CASCADE;
CREATE OR REPLACE VIEW pbi_iss_activation_issues AS
SELECT s.service_id,
       aa.activation_attempt_id AS 'Activation Attempt ID',
       CONCAT('svc-', s.service_id,
              IF(aa.activation_attempt_id IS NULL, '',
                 CONCAT('aa-', aa.activation_attempt_id))) AS 'Composite ID',
       activation_issue_id AS 'Activation Issue ID',
       primary_root_cause AS 'Primary Root Cause',
       secondary_root_cause AS 'Secondary Root Cause',
       ai.tertiary_root_cause AS 'Tertiary Root Cause',
       ai.issue_rank AS 'Rank',
       note AS Note
FROM service s
     LEFT JOIN activation_attempt aa ON s.service_id = aa.service_id
     LEFT JOIN activation_issue ai ON aa.activation_attempt_id = ai.activation_attempt_id
WHERE s.tenant_id = (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');



DROP VIEW IF EXISTS pbi_iss_service_jeops CASCADE;
CREATE OR REPLACE VIEW pbi_iss_service_jeops AS
SELECT s.service_id AS 'Service ID',
       sji.jeop_instance_id AS 'Jeopardy Instance ID',
       ji.jeop_description AS 'Jeopardy Description',
       ji.responsibility AS 'Responsibility',
       ji.start_date AS 'Start Date',
       ji.end_date AS 'End Date',
       ji.note AS 'Note',
       ji.originator AS 'Originator',
       ji.assigned_to AS 'Assigned To'
FROM service s
     LEFT JOIN service_jeop_instance sji ON s.service_id = sji.service_id
     LEFT JOIN jeop_instance ji ON sji.jeop_instance_id = ji.jeop_instance_id
WHERE s.tenant_id = (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');



DROP VIEW IF EXISTS pbi_iss_service_notes CASCADE;
CREATE OR REPLACE VIEW pbi_iss_service_notes AS
SELECT s.service_id AS 'service ID',
       n.note AS Note,
       n.created_by AS 'Created By',
       n.created_date AS 'Created Date',
       n.internal_only AS 'Internal Only'
FROM service s
     LEFT JOIN service_note sn ON s.service_id = sn.service_id
     LEFT JOIN note n ON sn.note_id = n.note_id
WHERE s.tenant_id IN (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');

