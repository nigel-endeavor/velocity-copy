CREATE OR REPLACE VIEW pbi_i90_service_interval AS
SELECT sii.service_id,
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
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
       provider_business_day_deduct_time AS 'Carrier Business Day Deduct Time',
       provider_calendar_day_deduct_time AS 'Carrier Calendar Day Deduct Time'
FROM service_interval_instance sii
     JOIN interval_instance ii ON sii.interval_instance_id = ii.interval_instance_id
     JOIN service s ON sii.service_id = s.service_id
     JOIN interval_type it ON ii.interval_type_id = it.interval_type_id
     JOIN milestone_instance omi ON ii.open_milestone_instance_id = omi.milestone_instance_id
     JOIN milestone om ON omi.milestone_id = om.milestone_id
     LEFT JOIN milestone_instance cmi ON ii.close_milestone_instance_id = cmi.milestone_instance_id
     LEFT JOIN milestone cm ON cmi.milestone_id = cm.milestone_id
WHERE s.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');
