DROP VIEW IF EXISTS pbi_i90_location_jeops CASCADE;
CREATE OR REPLACE VIEW pbi_i90_location_jeops AS
SELECT l.location_id AS 'Location ID',
       l.tenant_id AS 'Tenant ID',
       l.current_inventory AS 'Current Inventory',
       lji.jeop_instance_id AS 'Jeopardy Instance ID',
       ji.jeop_description AS 'Jeopardy Description',
       ji.responsibility AS 'Responsibility',
       ji.start_date AS 'Start Date',
       ji.end_date AS 'End Date',
       ji.note AS 'Note',
       ji.originator AS 'Originator',
       ji.assigned_to AS 'Assigned To'
FROM location l
     JOIN location_jeop_instance lji ON l.location_id = lji.location_id
     JOIN jeop_instance ji ON lji.jeop_instance_id = ji.jeop_instance_id
WHERE l.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');
