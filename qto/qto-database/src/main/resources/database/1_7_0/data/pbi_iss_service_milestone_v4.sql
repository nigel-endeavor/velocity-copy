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

       CAST(MAX(CASE WHEN (m.milestone_code = 'PROVIDER_ORDER_SUBMITTED')
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
