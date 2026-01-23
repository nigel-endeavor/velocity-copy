set @tenantId = (select tenant_id from v_tenant where name = 'QTO First Tenant');

INSERT INTO tenant_lookup_value
SELECT lookup_value_id, @tenantId
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code in (
        'ADDITIONAL_IP_BLOCK',
        'ACTIVATION_REQUIREMENTS',
        'STATE_PROVINCE',
        'BROADBAND_STATUS',
        'DIA_STATUS',
        'NA_TIME_ZONE',
        'COUNTRY',
        'LOCATION_STATUS',
        'ACTIVATION_ATTEMPT_STATUS',
        'CARRIER'
    )
);


set @tenantId = (select tenant_id from v_tenant where name = 'Endeavor');

INSERT INTO tenant_lookup_value
SELECT lookup_value_id, @tenantId
FROM lookup_value
WHERE lookup_type_id in (
    SELECT lookup_type_id
    FROM lookup_type
    WHERE lookup_type_code in (
        'ADDITIONAL_IP_BLOCK',
        'ACTIVATION_REQUIREMENTS',
        'STATE_PROVINCE',
        'BROADBAND_STATUS',
        'DIA_STATUS',
        'NA_TIME_ZONE',
        'COUNTRY',
        'LOCATION_STATUS',
        'ACTIVATION_ATTEMPT_STATUS',
        'CARRIER'
    )
);
