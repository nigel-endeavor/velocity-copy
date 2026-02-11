INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Dispute Type', 'DISPUTE_TYPE', 1, 1, 0);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'DISPUTE_TYPE');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Wrong MRC', 'Wrong MRC', 0, 1),
       (@lookupTypeCode, 'Wrong NRC', 'Wrong NRC', 0, 1),
       (@lookupTypeCode, 'Wrong Bill End Date', 'Wrong Bill End Date', 0, 1),
       (@lookupTypeCode, 'Billed for Disconnected Service', 'Billed for Disconnected Service', 0, 1),
       (@lookupTypeCode, 'Billed on Multiple Accounts', 'Billed on Multiple Accounts', 0, 1),
       (@lookupTypeCode, 'Billed to Wrong Address', 'Billed to Wrong Address', 0, 1),
       (@lookupTypeCode, 'Tax Error', 'Tax Error', 0, 1),
       (@lookupTypeCode, 'Billed for Components Not Ordered', 'Billed for Components Not Ordered', 0, 1),
       (@lookupTypeCode, 'Unfulfilled Credit/Discount', 'Unfulfilled Credit/Discount', 0, 1);

set @tenantId = (select tenant_id from v_tenant where name = 'QTO First Tenant');
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, @tenantId
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'DISPUTE_TYPE'
);

set @tenantId = (select tenant_id from v_tenant where name = 'Endeavor');
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, @tenantId
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'DISPUTE_TYPE'
);
