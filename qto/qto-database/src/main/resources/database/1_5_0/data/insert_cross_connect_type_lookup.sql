INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Cross Connect Type', 'CROSS_CONNECT_TYPE', true, true, false);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CROSS_CONNECT_TYPE'), 'Copper', 'Copper', 0, true),
        ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CROSS_CONNECT_TYPE'), 'Fiber', 'Fiber', 0, true);

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, t.tenant_id
FROM lookup_value
join v_tenant t
WHERE lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CROSS_CONNECT_TYPE');

