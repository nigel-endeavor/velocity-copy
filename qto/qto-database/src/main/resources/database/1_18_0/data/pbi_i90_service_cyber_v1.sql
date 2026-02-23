DROP VIEW IF EXISTS pbi_i90_service_cyber CASCADE;
CREATE OR REPLACE VIEW pbi_i90_service_cyber AS
SELECT s.service_id AS 'Service ID',
       location_id AS 'Location ID',
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
       service_type AS 'Service',
       s.project_name AS 'Project Name',
       client_service_id AS 'Client Service ID',
       opportunity_num AS 'Opportunity Number',
       order_type AS 'Order Type',
       sub_product_type AS 'Sub Product Type',
       provider AS 'Provider',
       field_services_provider AS 'Field Services Provider',
       service_status AS 'Service Status',
       client_service_info AS 'Service Info',
       client_service_type AS 'Service Type',
       follow_up_date AS 'Follow Up Date',
       record_source AS 'Source',
       service_description AS 'Description',
       managed_service AS 'Managed Service',
       contract_term AS 'Contract Term',
       contract_signed_date AS 'Contract Signed Date',
       circuit_term_end_date AS 'Contract Term End Date',
       ignore_for_renewals AS 'Ignore For Renewals',
       auto_renewal AS 'Auto Renewal',
       renewal_cancel_notice_period AS 'Notice Period for Renewal/Cancel',
       contract_info AS 'Contract Info',
       service_mrc AS 'MRC',
       service_nrc AS 'NRC',
       service_mrr AS 'MRR',
       service_nrr AS 'NRR',
       bill_to_location AS 'Bill To Location',
       address_1 AS 'Billing Address 1',
       address_2 AS 'Billing Address 2',
       city AS 'Billing City',
       state_province AS 'Billing State/Province',
       postal_code AS 'Billing Postal Code',
       country AS 'Billing Country',
       billing_email AS 'Billing Email',
       account_number AS 'Account Number/BAN',
       provider_order_num AS 'Provider Order Number',
       bill_cycle AS 'Bill Cycle',
       account_passcode AS 'Account Passcode',
       microsoft_licensing AS 'Microsoft Licensing',
       number_of_users AS 'Number of Users',
       number_of_endpoints AS 'Number of Endpoints/Devices',
       technical_notes AS 'Technical Notes',
       usm_anywhere_control_node AS 'USM Anywhere Control Node',
       tier_expansion_storage_size AS 'Tier Expansion Storage Size',
       log_retention_requirement AS 'Log Retention Requirement',
       hot_storage_retention AS 'Hot Storage Retention',
       sensors AS 'Sensors',
			mxdr.number_of_endpoints_devices as 'Data Sources for Logs',
			ranson.agent_deployment as 'Agent Deployment',
			risk.number_of_assets as 'Number of Assets',
			risk.number_of_ips as 'Number of IPs',
			risk.ip_technical_notes as 'IP Technical Notes',
			ms.e3 as 'E3',
			ms.e5 as 'E5',
			ms.business_premium as 'Business Premium',
			ms.microsoft_entra_id_p1 as 'Microsoft Entra ID P1',
			ms.microsoft_entra_id_p2 as 'Microsoft Entra ID P2',
			ms.microsoft_intune_plan_1 as 'Microsoft Intune Plan 1',
			ms.microsoft_intune_plan_2 as 'Microsoft Intune Plan 2',
			ms.microsoft_defender_for_endpoint_p1 as 'Microsoft Defender for Endpoint P1',
			ms.microsoft_defender_for_endpoint_p2 as 'Microsoft Defender for Endpoint P2',
       jn.note AS 'Last Note',
       jn.created_by AS 'Last Note Created By',
       jn.created_date AS 'Last Note Created Date'
FROM service s
     LEFT JOIN service_threatmdr threat ON s.service_id = threat.service_id
	LEFT JOIN service_cyber360_mxdr mxdr ON s.service_id = mxdr.service_id
	left join service_ransommdr ranson on s.service_id = ranson.service_id
	left join service_riskmdr risk on s.service_id = risk.service_id
	left join service_microsoft_licenses ms on s.service_id = ms.service_id
      LEFT JOIN (SELECT sn.service_id, note, n.created_date, n.created_by
                FROM note n
                     JOIN service_note sn ON n.note_id = sn.note_id
                     JOIN (SELECT sn.service_id, MAX(n.note_id) AS note_id
                           FROM note n
                                JOIN service_note sn ON n.note_id = sn.note_id
                           GROUP BY sn.service_id) vw ON sn.note_id = vw.note_id) jn ON s.service_id = jn.service_id
      WHERE s.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor')
		              AND s.marked_for_deletion = FALSE;
