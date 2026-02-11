SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ADDITIONAL_IP_BLOCK');

DELETE FROM tenant_lookup_value WHERE lookup_value_id IN (
    SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = @lookupTypeCode
    );
DELETE FROM lookup_value WHERE lookup_type_id = @lookupTypeCode;

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, '/24', '/24', 10, 1),
       (@lookupTypeCode, '/25', '/25', 20, 1),
       (@lookupTypeCode, '/26', '/26', 30, 1),
       (@lookupTypeCode, '/27', '/27', 40, 1),
       (@lookupTypeCode, '/28', '/28', 50, 1),
       (@lookupTypeCode, '/29', '/29', 60, 1),
       (@lookupTypeCode, '/30', '/30', 70, 1);

set @tenantId = (select tenant_id from v_tenant where name = 'QTO First Tenant');
INSERT INTO tenant_lookup_value
SELECT lookup_value_id, @tenantId
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'ADDITIONAL_IP_BLOCK'
);

set @tenantId = (select tenant_id from v_tenant where name = 'Endeavor');
INSERT INTO tenant_lookup_value
SELECT lookup_value_id, @tenantId
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code = 'ADDITIONAL_IP_BLOCK'
);

