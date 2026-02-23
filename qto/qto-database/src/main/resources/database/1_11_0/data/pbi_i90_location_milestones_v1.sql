DROP VIEW IF EXISTS pbi_i90_location_milestone CASCADE;
CREATE OR REPLACE VIEW pbi_i90_location_milestone AS
SELECT lmi.location_id,
       l.tenant_id AS 'Tenant ID',
       l.current_inventory AS 'Current Inventory',
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
       CAST(MAX(CASE WHEN (m.milestone_code = 'REMOVED_FROM_INVENTORY')
	                     THEN CONVERT_TZ(mi.milestone_date, 'UTC', 'US/Eastern')
                     ELSE NULL END) AS date) AS `Removed from Inventory`,
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
     JOIN location l ON lmi.location_id = l.location_id
WHERE l.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor')
GROUP BY lmi.location_id;









