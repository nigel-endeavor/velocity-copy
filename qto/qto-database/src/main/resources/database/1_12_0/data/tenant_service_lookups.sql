
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), '4G/5G', '4G/5G', 0, 1, (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'Broadband', 'Broadband', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'DIA', 'DIA', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'Ethernet', 'Ethernet', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'MPLS', 'MPLS', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'Cross Connect', 'Cross Connect', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'Television', 'Television', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'UCaaS', 'UCaaS', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant'));


INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), '4G/5G', '4G/5G', 0, 1, (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'Broadband', 'Broadband', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'DIA', 'DIA', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'Ethernet', 'Ethernet', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'MPLS', 'MPLS', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'Cross Connect', 'Cross Connect', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'Television', 'Television', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'UCaaS', 'UCaaS', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant'));


INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), '4G/5G', '4G/5G', 0, 1, (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'Broadband', 'Broadband', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'DIA', 'DIA', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'Ethernet', 'Ethernet', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'MPLS', 'MPLS', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'Cross Connect', 'Cross Connect', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'Television', 'Television', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES'), 'UCaaS', 'UCaaS', 0, 1,  (select tenant_id from v_tenant where name = 'QTO First Tenant'));

update company_config_property set config_property_value = '4G/5G, Broadband, Cross Connect, DIA, Ethernet, MPLS, Television, UCaaS' where config_property_key = 'ASSIGNED_SERVICE_TYPES';
