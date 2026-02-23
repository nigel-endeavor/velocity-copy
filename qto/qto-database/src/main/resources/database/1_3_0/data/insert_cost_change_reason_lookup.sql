INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Cost Change Reason', 'COST_CHANGE_REASON', true, true, true);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'COST_CHANGE_REASON'), 'Reconciled to Quote', 'Reconciled to Quote', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'COST_CHANGE_REASON'), 'Recurring Fees Incurred', 'Recurring Fees Incurred', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'COST_CHANGE_REASON'), 'Term Ended-Not Renewed', 'Term Ended-Not Renewed', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'COST_CHANGE_REASON'), 'Tier Discount Reached', 'Tier Discount Reached', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'COST_CHANGE_REASON'), 'Other', 'Other', 50, true);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, (select tenant_id from v_tenant where name = 'QTO First Tenant')
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'COST_CHANGE_REASON'
);


INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, (select tenant_id from v_tenant where name = 'QTO First Tenant')
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'COST_CHANGE_REASON'
);


INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, (select tenant_id from v_tenant where name = 'QTO First Tenant')
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'COST_CHANGE_REASON'
);
