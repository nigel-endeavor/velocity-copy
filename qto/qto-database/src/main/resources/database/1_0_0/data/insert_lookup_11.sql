

DELETE FROM tenant_lookup_value WHERE lookup_value_id IN (
    SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ADDITIONAL_IP_BLOCK')
    );
DELETE FROM lookup_value WHERE lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ADDITIONAL_IP_BLOCK');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ADDITIONAL_IP_BLOCK'), '/24', '/24', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ADDITIONAL_IP_BLOCK'), '/25', '/25', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ADDITIONAL_IP_BLOCK'), '/26', '/26', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ADDITIONAL_IP_BLOCK'), '/27', '/27', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ADDITIONAL_IP_BLOCK'), '/28', '/28', 50, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ADDITIONAL_IP_BLOCK'), '/29', '/29', 60, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ADDITIONAL_IP_BLOCK'), '/30', '/30', 70, true);
INSERT INTO tenant_lookup_value
SELECT lookup_value_id, (select tenant_id from v_tenant where name = 'QTO First Tenant')
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'ADDITIONAL_IP_BLOCK'
);


INSERT INTO tenant_lookup_value
SELECT lookup_value_id, (select tenant_id from v_tenant where name = 'QTO First Tenant')
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'ADDITIONAL_IP_BLOCK'
);

