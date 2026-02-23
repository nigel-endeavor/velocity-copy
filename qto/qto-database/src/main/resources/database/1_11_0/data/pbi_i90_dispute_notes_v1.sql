DROP VIEW IF EXISTS pbi_i90_dispute_notes CASCADE;
CREATE OR REPLACE VIEW pbi_i90_dispute_notes AS
SELECT d.dispute_id,
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
       n.note AS 'Note',
       n.created_by AS 'Created By',
       created_date AS 'Created Date',
       n.internal_only AS 'Internal Only'
FROM dispute d
     JOIN dispute_note dn ON d.dispute_id = dn.dispute_id
     JOIN note n ON dn.note_id = n.note_id
     JOIN service s ON d.service_id = s.service_id
WHERE s.tenant_id != (SELECT tenant_id FROM v_tenant WHERE NAME = 'Endeavor');
