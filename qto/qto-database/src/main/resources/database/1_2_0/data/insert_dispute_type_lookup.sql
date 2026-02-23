INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Dispute Type', 'DISPUTE_TYPE', true, true, false);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'DISPUTE_TYPE'), 'Wrong MRC', 'Wrong MRC', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'DISPUTE_TYPE'), 'Wrong NRC', 'Wrong NRC', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'DISPUTE_TYPE'), 'Wrong Bill End Date', 'Wrong Bill End Date', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'DISPUTE_TYPE'), 'Billed for Disconnected Service', 'Billed for Disconnected Service', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'DISPUTE_TYPE'), 'Billed on Multiple Accounts', 'Billed on Multiple Accounts', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'DISPUTE_TYPE'), 'Billed to Wrong Address', 'Billed to Wrong Address', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'DISPUTE_TYPE'), 'Tax Error', 'Tax Error', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'DISPUTE_TYPE'), 'Billed for Components Not Ordered', 'Billed for Components Not Ordered', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'DISPUTE_TYPE'), 'Unfulfilled Credit/Discount', 'Unfulfilled Credit/Discount', 0, true);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, (select tenant_id from v_tenant where name = 'QTO First Tenant')
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'DISPUTE_TYPE'
);


INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, (select tenant_id from v_tenant where name = 'QTO First Tenant')
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'DISPUTE_TYPE'
);
