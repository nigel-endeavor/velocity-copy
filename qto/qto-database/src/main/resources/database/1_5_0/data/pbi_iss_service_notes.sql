DROP VIEW IF EXISTS pbi_iss_service_notes CASCADE;
CREATE OR REPLACE VIEW pbi_iss_service_notes AS
SELECT sn.service_id AS 'service ID',
       n.note AS Note,
       n.created_by AS 'Created By',
       n.created_date AS 'Created Date',
       n.internal_only AS 'Internal Only'
FROM service_note sn
     LEFT JOIN note n ON sn.note_id = n.note_id
WHERE n.tenant_id IN (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');
