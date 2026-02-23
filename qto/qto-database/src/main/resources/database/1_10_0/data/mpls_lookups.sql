INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy, category)
VALUES ('MPLS Type', 'MPLS_TYPE', true, true, false, 'Service Technical - Product Specific');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'MPLS_TYPE'), 'IPVPN', 'IPVPN', 0, 1, 100),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'MPLS_TYPE'), 'VPLS', 'VPLS', 0, 1, 100),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'MPLS_TYPE'), 'IPVPN', 'IPVPN', 0, 1, (Select tenant_id from v_tenant t where t.name = 'Demo Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'MPLS_TYPE'), 'VPLS', 'VPLS', 0, 1, (Select tenant_id from v_tenant t where t.name = 'Demo Tenant')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'MPLS_TYPE'), 'IPVPN', 'IPVPN', 0, 1, (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'MPLS_TYPE'), 'VPLS', 'VPLS', 0, 1, (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
