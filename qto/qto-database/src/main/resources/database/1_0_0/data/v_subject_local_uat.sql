DROP VIEW IF EXISTS v_subject CASCADE;
CREATE OR REPLACE VIEW v_subject AS
SELECT s.subject_id,
    s.username,
    s.display_name,
    s.email_address,
    s.active,
    s.version
    from platform.subject s;