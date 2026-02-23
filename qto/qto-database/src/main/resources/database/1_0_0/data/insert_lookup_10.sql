INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Activation Replace 4G/5G', 'ACTIVATION_REPLACE_4G5G', true, true, true);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REPLACE_4G5G'), 'No', 'No', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REPLACE_4G5G'), 'RMA AT&T', 'RMA AT&T', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REPLACE_4G5G'), 'RMA Verizon', 'RMA Verizon', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REPLACE_4G5G'), 'RMA T-Mobile', 'RMA T-Mobile', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REPLACE_4G5G'), 'RMA Bell Mobility', 'RMA Bell Mobility', 50, true);
INSERT INTO tenant_lookup_value
SELECT lookup_value_id, (select tenant_id from v_tenant where name = 'QTO First Tenant')
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'ACTIVATION_REPLACE_4G5G'
);


INSERT INTO tenant_lookup_value
SELECT lookup_value_id, (select tenant_id from v_tenant where name = 'QTO First Tenant')
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'ACTIVATION_REPLACE_4G5G'
);
