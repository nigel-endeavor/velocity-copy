DROP VIEW IF EXISTS pbi_i90_service_notes CASCADE;
CREATE OR REPLACE VIEW pbi_i90_service_notes AS
SELECT sn.service_id AS 'service ID',
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
       n.note AS Note,
       n.created_by AS 'Created By',
       n.created_date AS 'Created Date',
       n.internal_only AS 'Internal Only'
FROM service s
     JOIN
     service_note sn ON s.service_id = sn.service_id
     JOIN note n ON sn.note_id = n.note_id
WHERE s.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');
