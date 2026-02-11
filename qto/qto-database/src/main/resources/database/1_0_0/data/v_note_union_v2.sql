DROP VIEW if exists v_note_union;
CREATE OR REPLACE VIEW v_note_union AS
SELECT DISTINCT s.location_id, s.service_id, n.note_id, n.category, n.created_by, n.created_date, note, n.internal_only, n.tenant_id
FROM service s
         JOIN service_note sn ON s.service_id = sn.service_id
         JOIN note n ON sn.note_id = n.note_id
UNION ALL
SELECT DISTINCT s.location_id, s.service_id, n.note_id, n.category, n.created_by, n.created_date, note, n.internal_only, n.tenant_id
FROM service s
         JOIN location_note ln ON s.location_id = ln.location_id
         JOIN note n ON ln.note_id = n.note_id
UNION ALL
SELECT DISTINCT l.location_id, s.service_id, n.note_id, n.category, n.created_by, n.created_date, n.note, n.internal_only, n.tenant_id
FROM location_note ln
         JOIN qto.location l ON ln.location_id = l.location_id
         JOIN qto.note n ON ln.note_id = n.note_id
         LEFT JOIN service s ON l.location_id = s.location_id
WHERE service_id IS NULL;