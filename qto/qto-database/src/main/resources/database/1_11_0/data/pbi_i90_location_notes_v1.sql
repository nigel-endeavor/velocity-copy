CREATE OR REPLACE VIEW pbi_i90_location_notes AS
SELECT l.location_id AS 'Location ID',
       l.tenant_id AS 'Tenant ID',
       l.current_inventory AS 'Current Inventory',
       n.note AS Note,
       n.created_by AS 'Created By',
       n.created_date AS 'Created Date',
       n.internal_only AS 'Internal Only'
FROM location l
     JOIN location_note ln ON l.location_id = ln.location_id
     JOIN note n ON ln.note_id = n.note_id
WHERE l.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');
