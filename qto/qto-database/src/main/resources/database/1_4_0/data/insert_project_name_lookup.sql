INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Project Name', 'PROJECT_NAME', true, true, false);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'PROJECT_NAME'), 'Circuit Groom - Internal', 'Circuit Groom - Internal', 0, true),
        ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'PROJECT_NAME'), '2 Phone Upgrade', '2 Phone Upgrade', 0, true),
        ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'PROJECT_NAME'), 'Domino''s Q1', 'Domino''s Q1', 0, 1),
        ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'PROJECT_NAME'), 'Stacy''s 2nd Project', 'Stacy''s 2nd Project', 0, 1),
        ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'PROJECT_NAME'), 'Bass Pro Phone Migration', 'Bass Pro Phone Migration', 0, true);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, (select tenant_id from v_tenant where name = 'QTO First Tenant')
FROM lookup_value
WHERE lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'PROJECT_NAME');


INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, (select tenant_id from v_tenant where name = 'QTO First Tenant')
FROM lookup_value
WHERE lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'PROJECT_NAME');

