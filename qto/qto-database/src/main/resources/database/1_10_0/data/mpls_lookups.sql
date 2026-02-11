INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy, category)
VALUES ('MPLS Type', 'MPLS_TYPE', 1, 1, 0, 'Service Technical - Product Specific');

SET @lookupTypeId = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'MPLS_TYPE');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, tenant_id)
VALUES (@lookupTypeId, 'IPVPN', 'IPVPN', 0, 1, 100),
       (@lookupTypeId, 'VPLS', 'VPLS', 0, 1, 100),
       (@lookupTypeId, 'IPVPN', 'IPVPN', 0, 1, (Select tenant_id from v_tenant t where t.name = 'Demo Tenant')),
       (@lookupTypeId, 'VPLS', 'VPLS', 0, 1, (Select tenant_id from v_tenant t where t.name = 'Demo Tenant')),
       (@lookupTypeId, 'IPVPN', 'IPVPN', 0, 1, (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       (@lookupTypeId, 'VPLS', 'VPLS', 0, 1, (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
