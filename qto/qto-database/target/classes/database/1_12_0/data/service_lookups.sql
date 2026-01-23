INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('All Service Types', 'ALL_SERVICE_TYPES', 1, 0, 0);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ALL_SERVICE_TYPES');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, tenant_id)
VALUES (@lookupTypeCode, '4G/5G', '4G/5G', 0, 1, 0),
       (@lookupTypeCode, 'Broadband', 'Broadband', 0, 1, 0),
       (@lookupTypeCode, 'DIA', 'DIA', 0, 1, 0),
       (@lookupTypeCode, 'Ethernet', 'Ethernet', 0, 1, 0),
       (@lookupTypeCode, 'MPLS', 'MPLS', 0, 1, 0),
       (@lookupTypeCode, 'Cross Connect', 'Cross Connect', 0, 1, 0),
       (@lookupTypeCode, 'Television', 'Television', 0, 1, 0),
       (@lookupTypeCode, 'UCaaS', 'UCaaS', 0, 1, 0);

INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Service Types', 'TENANT_SERVICE_TYPES', 1, 0, 0);



INSERT INTO company_config_property (company_id, config_property_key, config_property_value, tenant_id, modifiable, description, type)
VALUES ((SELECT company_id FROM company WHERE company_name = 'QTO First Tenant' AND company_type = 'Vertek Client'), 'ASSIGNED_SERVICE_TYPES', '', (SELECT tenant_id FROM v_tenant WHERE name = 'QTO First Tenant'), true, 'Available Service Types', 'ALL_SERVICE_TYPES'),
       ((SELECT company_id FROM company WHERE company_name = 'Demo Tenant' AND company_type = 'Vertek Client'), 'ASSIGNED_SERVICE_TYPES', '', (SELECT tenant_id FROM v_tenant WHERE name = 'Demo Tenant'), true, 'Available Service Types', 'ALL_SERVICE_TYPES'),
       ((SELECT company_id FROM company WHERE company_name = 'Endeavor' AND company_type = 'Vertek Client'), 'ASSIGNED_SERVICE_TYPES', '', (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor'), true, 'Available Service Types', 'ALL_SERVICE_TYPES');
