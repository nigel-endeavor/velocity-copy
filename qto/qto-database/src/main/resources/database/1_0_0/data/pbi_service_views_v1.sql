create or replace view pbi_iss_service as
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
       osp_const_interval_est as 'OSP Construction Interval Estimate',
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
       jn.note as 'Last Note',
       jn.created_by as 'Last Note Created By',
       jn.created_date as 'Last Note Created Date'
FROM service s
  left join (
select sn.service_id, note, n.created_date, n.created_by from note n join service_note sn on n.note_id = sn.note_id join (
select sn.service_id, max(n.note_id) as note_id from note n join service_note sn on n.note_id = sn.note_id
group by sn.service_id) vw on sn.note_id = vw.note_id) jn on s.service_id = jn.service_id
where s.tenant_id in (select tenant_id from v_tenant where name = 'Endeavor');


create or replace view pbi_iss_broadband_service as
select bs.service_id AS 'Service ID',
       modem_make AS 'Modem Make',
       bs.mac_address AS 'Broadband MAC Address',
       customer_prem_equipment AS 'Customer Prem Equipment',
       network_protocol AS 'Network Protocol',
       pppoe_username AS 'PPPoE Username',
       pppoe_password AS 'PPPoE Password',
       modem_serial_number AS 'Modem Serial Number'
from broadband_service bs
join service s on bs.service_id = s.service_id
where tenant_id in (select tenant_id from v_tenant where name = 'Endeavor');

create or replace view pbi_iss_dia_service as
select ds.service_id AS 'Service ID',
       carrier_activation_method AS 'Carrier Activation Method',
       interface_connector AS 'Interface Connector',
       npa_nxx AS 'NPA NXX',
       last_mile_carrier AS 'Last Mile Carrier',
       new_access_circuit_id AS 'New Access Circuit ID',
       burstable_speed AS 'Burstable Speed',
       burstable_speed_cost AS 'Burstable Speed Cost',
       router_serial_number AS 'Router Serial Number',
       router_mac_address AS 'Router MAC Address'
from dia_service ds
join service s on ds.service_id = s.service_id
where tenant_id in (select tenant_id from v_tenant where name = 'Endeavor');

create or replace view pbi_iss_4g5g_service as
select gs.service_id AS 'Service ID',
       billing_account_number AS 'Billing Account Number',
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
from `4g5g_service` gs
join service s on gs.service_id = s.service_id
where tenant_id in (select tenant_id from v_tenant where name = 'Endeavor');

create or replace view pbi_iss_ucass_service as
select us.service_id AS 'Service ID',
       published_tn AS 'Published TN',
       temporary_tn AS 'Temporary TN',
       number_of_handsets AS 'Number of Handsets'
from ucaas_service us
join service s on us.service_id = s.service_id
where tenant_id in (select tenant_id from v_tenant where name = 'Endeavor');

Create or replace view pbi_iss_activation_attempts as
SELECT aa.service_id as 'Service ID',
       aa.activation_attempt_id as 'Activation Attempt ID',
       scheduled_attempt_status as 'Scheduled Attempt Status',
       internal_tech_assigned as 'Internal Tech Assigned',
       field_dispatch_vendor as 'Field Dispatch Vendor',
       ftdi_vendor_id as 'FTDI Vendor ID',
       field_tech_name as 'Field Tech Name',
       field_tech_phone as 'Field Tech Phone',
       field_tech_check_in as 'FT Check-in Time',
       field_tech_check_out as 'FT Check-out Time',
       tested_download_speed as 'Tested Download Speed',
       tested_upload_speed as 'Tested Upload Speed',
       latency as Latency,
       backup_download_speed as 'Backup Download Speed',
       backup_upload_speed as 'Backup Upload Speed',
       signal_rsrp as 'Signal/RSRP',
       sinr_rsrq as 'SINR/RSRQ',
       location_downtown_for_cutover as 'Location Downtime For Cutover',
       closeout_code as 'Closeout Code',
       network_complete_date as 'Network Complete Date',
       voip_complete_date as 'UCaaS Complete Date',
       issue_notes as 'Internal TTU Notes',
       scheduled_check_in_time as 'Scheduled Check-in Time',
       attempt_number 'Attempt Number',
       replace_4g_5g 'Replace 4G/5G with Broadband/DIA',
       primary_uid as 'Primary UID',
       secondary_uid as 'Secondary UID'
FROM activation_attempt aa
where tenant_id in (select tenant_id from v_tenant where name = 'Endeavor');

create or replace view pbi_iss_activation_issues as
select activation_issue_id as 'Activation Issue ID',
       activation_attempt_id as 'Activation Attempt ID',
       primary_root_cause as 'Primary Root Cause',
       secondary_root_cause as 'Secondary Root Cause',
       ai.tertiary_root_cause as 'Tertiary Root Cause',
       ai.issue_rank as 'Rank',
       note as Note
from activation_issue ai
where tenant_id = (Select tenant_id from v_tenant where name = 'Endeavor');




create or replace view pbi_iss_service_interval as
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
join service s on sii.service_id = s.service_id
join interval_type it ON ii.interval_type_id = it.interval_type_id
join milestone_instance omi ON ii.open_milestone_instance_id = omi.milestone_instance_id
join milestone om on omi.milestone_id = om.milestone_id
left join milestone_instance cmi on ii.close_milestone_instance_id = cmi.milestone_instance_id
left join milestone cm on cmi.milestone_id = cm.milestone_id
where s.tenant_id in (select tenant_id from v_tenant where name = 'Endeavor');




create or replace VIEW pbi_iss_service_jeops as
select sji.service_id as 'Service ID',
       sji.jeop_instance_id as 'Jeopardy Instance ID',
       ji.jeop_description as 'Jeopardy Description',
       ji.responsibility as 'Responsibility',
       ji.start_date as 'Start Date',
       ji.end_date as 'End Date',
       ji.note as 'Note',
       ji.originator as 'Originator',
       ji.assigned_to as 'Assigned To'
       from service_jeop_instance sji
join jeop_instance ji ON sji.jeop_instance_id = ji.jeop_instance_id
where tenant_id = (select tenant_id from v_tenant where name = 'Endeavor');



create or replace view pbi_iss_service_notes as
select sn.service_id as 'service ID',
       n.note as Note,
       n.created_by as 'Created By',
       n.created_date as 'Created Date'
from service_note sn
join note n on sn.note_id = n.note_id
where tenant_id in (select tenant_id from v_tenant where name = 'Endeavor');







create or replace view pbi_iss_service_milestone as
SELECT
       smi.service_id,

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
where mi.tenant_id = (select tenant_id from v_tenant where name = 'Endeavor')
Group by smi.service_id ;
