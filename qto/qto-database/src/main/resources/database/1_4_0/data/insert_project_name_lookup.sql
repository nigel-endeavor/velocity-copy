INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Project Name', 'PROJECT_NAME', 1, 1, 0);

SET @lookupTypeId = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'PROJECT_NAME');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeId, 'Circuit Groom - Internal', 'Circuit Groom - Internal', 0, 1),
        (@lookupTypeId, '2 Phone Upgrade', '2 Phone Upgrade', 0, 1),
        (@lookupTypeId, 'Domino''s Q1', 'Domino''s Q1', 0, 1),
        (@lookupTypeId, 'Stacy''s 2nd Project', 'Stacy''s 2nd Project', 0, 1),
        (@lookupTypeId, 'Bass Pro Phone Migration', 'Bass Pro Phone Migration', 0, 1);

set @tenantId = (select tenant_id from v_tenant where name = 'QTO First Tenant');
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, @tenantId
FROM lookup_value
WHERE lookup_type_id = @lookupTypeId;

set @tenantId = (select tenant_id from v_tenant where name = 'Demo Tenant');
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, @tenantId
FROM lookup_value
WHERE lookup_type_id = @lookupTypeId;

