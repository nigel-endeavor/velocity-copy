DROP VIEW IF EXISTS pbi_iss_service CASCADE;
CREATE OR REPLACE VIEW pbi_iss_service AS
SELECT s.service_id AS 'Service ID',
       location_id AS 'Location ID',
       client_service_id AS 'Client Service ID',
       quote_solution_id AS 'Quote Solution ID',
       service_status AS 'Service Status',
       service_sub_status AS 'Service Sub Status',
       sub_product_type AS 'Sub Product Type',
       order_type AS 'Order Type',
       activation_link AS 'Activation Link',
       activation_phone AS 'Activation Phone',
       follow_up_date AS 'Follow Up Date',
       contract_term AS 'Contract Term',
       contract_signed_date AS 'Contract Signed Date',
       po_number AS 'PO Number',
       service_mrc AS 'Service MRC',
       service_nrc AS 'Service NRC',
       service_icb AS 'Service ICB',
       service_osp AS 'Service OSP',
       carrier AS 'Carrier',
       carrier_site_account_num AS 'Carrier Site Account Number',
       carrier_order_num AS 'Carrier Order Number',
       carrier_circuit_id AS 'Carrier Circuit ID',
       inside_wiring_required AS 'Inside Wiring Required',
       dmarc AS 'DMARC',
       additional_ip_block_required AS 'Additional IP Block Required',
       additional_ip_block AS 'Additional IP Block',
       wan_ips AS 'WAN IPs',
       wan_gateway AS 'WAN Gateway',
       wan_subnet AS 'WAN Subnet',
       lan_ips AS 'LAN IPs',
       lan_gateway AS 'LAN Gateway',
       lan_subnet AS 'LAN Subnet',
       dns1 AS 'DNS1',
       dns2 AS 'DNS2',
       osp_const_interval_est AS 'OSP Construction Interval Estimate',
       speed AS 'Speed',
       download_speed AS 'Download Speed',
       upload_speed AS 'Upload Speed',
       media_type AS 'Media Type',
       net_status AS 'Net Status',
       location_hours AS 'Location Hours',
       product_install_interval AS 'Product Install Interval',
       trunk_group AS 'Trunk Group',
       connection_handoff_type AS 'Connection Handoff Type',
       tie_down_info AS 'Tie Down Info',
       service_type AS 'Service Type',
       currency AS 'Currency',
       building_status AS 'Building Status',
       ip_format AS 'IP Format',
       expedite_order AS 'Expedite Order',
       service_description AS 'Service Description',
       last_update_by AS 'Last Update By',
       last_update_date AS 'Last Update Date',
       circuit_term_end_date AS 'Circuit Term End Date',
       active AS 'Active',
       last_status_change AS 'Last Status Change',
       lan_block AS 'LAN Block',
       progress_percentage AS 'Progress Percentage',
       ticket_number AS 'Ticket Number',
       update_client AS 'Update Client',
       circuit_owner AS 'Circuit Owner',
       job_number AS 'Job Number',
       legacy_id AS 'Legacy ID',
       has_icb AS 'Has ICB',
       has_osp AS 'Has OSP',
       jn.note AS 'Last Note',
       jn.created_by AS 'Last Note Created By',
       jn.created_date AS 'Last Note Created Date',
       (SELECT COUNT(*)
        FROM activation_attempt aa
        WHERE aa.scheduled_attempt_status IN ('In Progress', 'Incomplete - Pending Re-Schedule', 'Complete')
		      AND aa.service_id = s.service_id) AS 'Truck Rolls'
FROM service s
     LEFT JOIN (SELECT sn.service_id, note, n.created_date, n.created_by
                FROM note n
                     JOIN service_note sn ON n.note_id = sn.note_id
                     JOIN (SELECT sn.service_id, MAX(n.note_id) AS note_id
                           FROM note n
                                JOIN service_note sn ON n.note_id = sn.note_id
                           GROUP BY sn.service_id) vw ON sn.note_id = vw.note_id) jn ON s.service_id = jn.service_id
WHERE s.tenant_id IN (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');


DROP VIEW IF EXISTS pbi_iss_broadband_service CASCADE;
CREATE OR REPLACE VIEW pbi_iss_broadband_service AS
SELECT bs.service_id AS 'Service ID',
       modem_make AS 'Modem Make',
       bs.mac_address AS 'Broadband MAC Address',
       customer_prem_equipment AS 'Customer Prem Equipment',
       network_protocol AS 'Network Protocol',
       pppoe_username AS 'PPPoE Username',
       pppoe_password AS 'PPPoE Password',
       modem_serial_number AS 'Modem Serial Number'
FROM broadband_service bs
     JOIN service s ON bs.service_id = s.service_id
WHERE tenant_id IN (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');

DROP VIEW IF EXISTS pbi_iss_dia_service CASCADE;
CREATE OR REPLACE VIEW pbi_iss_dia_service AS
SELECT ds.service_id AS 'Service ID',
       carrier_activation_method AS 'Carrier Activation Method',
       interface_connector AS 'Interface Connector',
       npa_nxx AS 'NPA NXX',
       last_mile_carrier AS 'Last Mile Carrier',
       new_access_circuit_id AS 'New Access Circuit ID',
       burstable_speed AS 'Burstable Speed',
       burstable_speed_cost AS 'Burstable Speed Cost',
       router_serial_number AS 'Router Serial Number',
       router_mac_address AS 'Router MAC Address'
FROM dia_service ds
     JOIN service s ON ds.service_id = s.service_id
WHERE tenant_id IN (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');

DROP VIEW IF EXISTS pbi_iss_4g5g_service CASCADE;
CREATE OR REPLACE VIEW pbi_iss_4g5g_service AS
SELECT gs.service_id AS 'Service ID',
       carrier_site_account_num AS 'Billing Account Number',
       uid AS 'UID',
       iccid AS 'ICCID',
       imei AS 'IMEI',
       mdn AS 'MDN',
       apn AS 'APN',
       rate_plan AS 'Rate Plan',
       rsrp AS 'RSRP',
       rsrq AS 'RSRQ',
       sinr AS 'SINR',
       rssi AS 'RSSI',
       replace_4g_5g AS 'Replace 4G/5G',
       gs.mac_address AS '4G/5G MAC Address',
       serial_number AS '4G/5G Serial Number'
FROM `4g5g_service` gs
     JOIN service s ON gs.service_id = s.service_id
WHERE tenant_id IN (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');

DROP VIEW IF EXISTS pbi_iss_ucass_service CASCADE;
CREATE OR REPLACE VIEW pbi_iss_ucass_service AS
SELECT us.service_id AS 'Service ID',
       published_tn AS 'Published TN',
       temporary_tn AS 'Temporary TN',
       number_of_handsets AS 'Number of Handsets'
FROM ucaas_service us
     JOIN service s ON us.service_id = s.service_id
WHERE tenant_id IN (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');

DROP VIEW IF EXISTS pbi_iss_activation_attempts CASCADE;
CREATE OR REPLACE VIEW pbi_iss_activation_attempts AS
SELECT aa.service_id AS 'Service ID',
       aa.activation_attempt_id AS 'Activation Attempt ID',
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
FROM activation_attempt aa
WHERE tenant_id IN (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');

DROP VIEW IF EXISTS pbi_iss_activation_issues CASCADE;
CREATE OR REPLACE VIEW pbi_iss_activation_issues AS
SELECT activation_issue_id AS 'Activation Issue ID',
       activation_attempt_id AS 'Activation Attempt ID',
       primary_root_cause AS 'Primary Root Cause',
       secondary_root_cause AS 'Secondary Root Cause',
       ai.tertiary_root_cause AS 'Tertiary Root Cause',
       ai.issue_rank AS 'Rank',
       note AS Note
FROM activation_issue ai
WHERE tenant_id = (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');



DROP VIEW IF EXISTS pbi_iss_service_interval CASCADE;
CREATE OR REPLACE VIEW pbi_iss_service_interval AS
SELECT sii.service_id,
       ii.interval_instance_id AS 'Interval Instance ID',
       it.interval_type_desc AS 'Interval Type',
       open_milestone_instance_id,
       om.milestone_name AS 'Open Milestone',
       omi.milestone_date AS 'Open Milestone Date',
       close_milestone_instance_id,
       cm.milestone_name AS 'Close Milestone',
       cmi.milestone_date AS 'Close Milestone Date',
       business_day_interval_time AS 'Business Day Interval Time',
       calendar_day_interval_time AS 'Calendar Day Interval Time',
       client_business_day_deduct_time AS 'Client Business Day Deduct Time',
       client_calendar_day_deduct_time AS 'Client Calendar Day Deduct Time',
       customer_business_day_deduct_time AS 'Customer Business Day Deduct Time',
       customer_calendar_day_deduct_time AS 'Customer Calendar Day Deduct Time',
       carrier_business_day_deduct_time AS 'Carrier Business Day Deduct Time',
       carrier_calendar_day_deduct_time AS 'Carrier Calendar Day Deduct Time'
FROM service_interval_instance sii
     JOIN interval_instance ii ON sii.interval_instance_id = ii.interval_instance_id
     JOIN service s ON sii.service_id = s.service_id
     JOIN interval_type it ON ii.interval_type_id = it.interval_type_id
     JOIN milestone_instance omi ON ii.open_milestone_instance_id = omi.milestone_instance_id
     JOIN milestone om ON omi.milestone_id = om.milestone_id
     LEFT JOIN milestone_instance cmi ON ii.close_milestone_instance_id = cmi.milestone_instance_id
     LEFT JOIN milestone cm ON cmi.milestone_id = cm.milestone_id
WHERE s.tenant_id IN (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');



DROP VIEW IF EXISTS pbi_iss_service_jeops CASCADE;
CREATE OR REPLACE VIEW pbi_iss_service_jeops AS
SELECT sji.service_id AS 'Service ID',
       sji.jeop_instance_id AS 'Jeopardy Instance ID',
       ji.jeop_description AS 'Jeopardy Description',
       ji.responsibility AS 'Responsibility',
       ji.start_date AS 'Start Date',
       ji.end_date AS 'End Date',
       ji.note AS 'Note',
       ji.originator AS 'Originator',
       ji.assigned_to AS 'Assigned To'
FROM service_jeop_instance sji
     JOIN jeop_instance ji ON sji.jeop_instance_id = ji.jeop_instance_id
WHERE tenant_id = (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');



DROP VIEW IF EXISTS pbi_iss_service_notes CASCADE;
CREATE OR REPLACE VIEW pbi_iss_service_notes AS
SELECT sn.service_id AS 'service ID',
       n.note AS Note,
       n.created_by AS 'Created By',
       n.created_date AS 'Created Date'
FROM service_note sn
     JOIN note n ON sn.note_id = n.note_id
WHERE tenant_id IN (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');



DROP VIEW IF EXISTS pbi_iss_service_milestone CASCADE;
CREATE OR REPLACE VIEW pbi_iss_service_milestone AS
SELECT smi.service_id,

       CAST(MAX(CASE WHEN (m.milestone_code = 'ACCESS_CIRCUIT_FOC')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Access Circuit FOC`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'ACTIVATION_COMPLETE')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Activation Complete`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'ACTIVATION_REQUESTED')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Activation Requested`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'ACTIVATION_SCHEDULED')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Activation Scheduled`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'BILLING_REVIEW_COMPLETE')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Billing Review Complete`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'CANCELLED')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Service Cancelled`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'CARRIER_ORDER_SUBMITTED')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Carrier Order Submitted`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'CHANGE_IN_ASSIGNMENT')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Change In Assignment`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'COMPLETE')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Service Complete`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'CREATED')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Service Created`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'CUSTOMER_BILL_STOP')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Customer Bill Stop`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'CUSTOMER_COMPLETION_NOTIFICATION_SENT')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Customer Completion Notification Sent`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'CUSTOMER_REQUESTED_INSTALL')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Customer Requested Install`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'DATA_PROVISIONING_COMPLETE')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Data Provisioning Complete`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'ENGINEER_ASSIGNED')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Engineer Assigned`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'EQUIPMENT_CONFIGURED')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Equipment Configured`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'EQUIPMENT_ORDERED')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Equipment Ordered`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'EQUIPMENT_RECEIVED')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Equipment Received`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_COMPLETE')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Network Provider Construction Complete`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_START')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Network Provider Construction Start`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'NETWORK_PROVIDER_FOC')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Network Provider FOC`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'ON_HOLD')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `On Hold`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'PRE_ACTIVATION_CALL_COMPLETE')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Pre-Activation Call Complete`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'RECEIVED')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Service Received`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'SITE_ACCESS_CONFIRMED')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Site Access Confirmed`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'SITE_SURVEY_COMPLETE')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Site Survey Complete`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'SITE_SURVEY_DUE')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Site Survey Due`,

       CAST(MAX(CASE WHEN (m.milestone_code = 'SITE_SURVEY_SUBMIT')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Site Survey Submit`
FROM milestone_instance mi
     JOIN milestone m ON mi.milestone_id = m.milestone_id
     JOIN service_milestone_instance smi ON mi.milestone_instance_id = smi.milestone_instance_id
WHERE mi.tenant_id = (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor')
GROUP BY smi.service_id;
