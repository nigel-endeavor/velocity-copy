INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Cost Change Reason', 'COST_CHANGE_REASON', 1, 1, 1);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'COST_CHANGE_REASON');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Reconciled to Quote', 'Reconciled to Quote', 10, 1),
       (@lookupTypeCode, 'Recurring Fees Incurred', 'Recurring Fees Incurred', 20, 1),
       (@lookupTypeCode, 'Term Ended-Not Renewed', 'Term Ended-Not Renewed', 30, 1),
       (@lookupTypeCode, 'Tier Discount Reached', 'Tier Discount Reached', 40, 1),
       (@lookupTypeCode, 'Other', 'Other', 50, 1);

set @tenantId = (select tenant_id from v_tenant where name = 'QTO First Tenant');
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, @tenantId
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'COST_CHANGE_REASON'
);

set @tenantId = (select tenant_id from v_tenant where name = 'Endeavor');
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, @tenantId
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'COST_CHANGE_REASON'
);

set @tenantId = (select tenant_id from v_tenant where name = 'Demo Tenant');
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, @tenantId
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'COST_CHANGE_REASON'
);
