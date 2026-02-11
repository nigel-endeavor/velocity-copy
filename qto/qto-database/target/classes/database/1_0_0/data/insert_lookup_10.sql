INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Activation Replace 4G/5G', 'ACTIVATION_REPLACE_4G5G', 1, 1, 1);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REPLACE_4G5G');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'No', 'No', 10, 1),
       (@lookupTypeCode, 'RMA AT&T', 'RMA AT&T', 20, 1),
       (@lookupTypeCode, 'RMA Verizon', 'RMA Verizon', 30, 1),
       (@lookupTypeCode, 'RMA T-Mobile', 'RMA T-Mobile', 40, 1),
       (@lookupTypeCode, 'RMA Bell Mobility', 'RMA Bell Mobility', 50, 1);

set @tenantId = (select tenant_id from v_tenant where name = 'QTO First Tenant');
INSERT INTO tenant_lookup_value
SELECT lookup_value_id, @tenantId
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'ACTIVATION_REPLACE_4G5G'
);

set @tenantId = (select tenant_id from v_tenant where name = 'Endeavor');
INSERT INTO tenant_lookup_value
SELECT lookup_value_id, @tenantId
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'ACTIVATION_REPLACE_4G5G'
);
