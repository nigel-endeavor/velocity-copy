INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Cross Connect Type', 'CROSS_CONNECT_TYPE', 1, 1, 0);

SET @lookupTypeId = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CROSS_CONNECT_TYPE');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeId, 'Copper', 'Copper', 0, 1),
        (@lookupTypeId, 'Fiber', 'Fiber', 0, 1);

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, t.tenant_id
FROM lookup_value
join v_tenant t
WHERE lookup_type_id = @lookupTypeId;

