CREATE OR REPLACE VIEW v_subject AS
SELECT s.subject_id,
    s.username,
    s.display_name,
    s.email_address,
    s.active,
    s.version
    FROM platform.subject s;