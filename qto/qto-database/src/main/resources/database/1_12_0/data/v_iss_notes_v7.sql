

DROP VIEW IF EXISTS v_iss_notes_file CASCADE;
CREATE OR REPLACE VIEW v_iss_notes_file AS
SELECT n.note_id,
       n.tenant_id,
       s.ticket_number,
       n.note,
       DATE_FORMAT(n.created_date, '%m/%d/%Y %h:%i:%s %p') AS created_date,
        concat(n.created_by, '|') as created_by,
      s.client_service_id
FROM service s
     JOIN service_note sn ON s.service_id = sn.service_id
     JOIN note n ON sn.note_id = n.note_id
WHERE n.update_client = TRUE
	AND n.internal_only = FALSE;
