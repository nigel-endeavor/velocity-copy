DROP VIEW IF EXISTS pbi_i90_service_jeops CASCADE;
CREATE OR REPLACE VIEW pbi_i90_service_jeops AS
SELECT sji.service_id AS 'Service ID',
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
       sji.jeop_instance_id AS 'Jeopardy Instance ID',
       ji.jeop_description AS 'Jeopardy Description',
       ji.responsibility AS 'Responsibility',
       ji.start_date AS 'Start Date',
       ji.end_date AS 'End Date',
       ji.note AS 'Note',
       ji.originator AS 'Originator',
       ji.assigned_to AS 'Assigned To'
FROM service s
     JOIN service_jeop_instance sji ON s.service_id = sji.service_id
     JOIN jeop_instance ji ON sji.jeop_instance_id = ji.jeop_instance_id
WHERE s.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');
