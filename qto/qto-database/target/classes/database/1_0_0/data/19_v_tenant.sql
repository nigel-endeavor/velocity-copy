CREATE OR REPLACE VIEW v_tenant AS
SELECT platform.tenant.tenant_id AS tenant_id,
       platform.tenant.name AS name,
       platform.tenant.active AS active,
       platform.tenant.version AS version
FROM platform.tenant;
