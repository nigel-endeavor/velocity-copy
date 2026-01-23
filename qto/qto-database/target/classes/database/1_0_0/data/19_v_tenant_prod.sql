CREATE OR REPLACE VIEW v_tenant AS
SELECT platform_qto.tenant.tenant_id AS tenant_id,
       platform_qto.tenant.name AS name,
       platform_qto.tenant.active AS active,
       platform_qto.tenant.version AS version
FROM platform_qto.tenant;
