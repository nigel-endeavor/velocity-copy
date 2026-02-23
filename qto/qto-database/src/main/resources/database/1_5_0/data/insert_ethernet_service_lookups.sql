-- ETHERNET_PRODUCT_TYPE
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Ethernet Product Type', 'ETHERNET_PRODUCT_TYPE', true, true, false);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'EPL', 'EPL', 0, true),
        ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'EVPL', 'EVPL', 0, true),
        ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'Eline', 'Eline', 0, true),
        ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'MPLS', 'MPLS', 0, true),
        ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'Wave', 'Wave', 0, true),
        ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'IPLC', 'IPLC', 0, true),
        ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'IPVPN', 'IPVPN', 0, true);

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, t.tenant_id
FROM lookup_value
         join v_tenant t
WHERE lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE');

-- MTU
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('MTU', 'MTU', true, true, true);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), '1492MTU', '1492MTU', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), '1452MTU-Optimal', '1452MTU-Optimal', 20, true);


INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, t.tenant_id
FROM lookup_value
         join v_tenant t
WHERE lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE');

-- CABLE_CATEGORY
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Cable Category', 'CABLE_CATEGORY', true, true, true);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'Coax', 'Coax', 10, true),
         ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'Fiber', 'Fiber', 20, true),
         ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'STP', 'STP', 30, true),
         ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'UTP', 'UTP', 40, true);


INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, t.tenant_id
FROM lookup_value
    join v_tenant t
WHERE lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE');

-- MUX
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('MUX', 'MUX', true, true, true);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'TDM', 'TDM', 10, true),
            ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'WDM', 'WDM', 20, true),
            ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'FDM', 'FDM', 30, true),
            ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'CDM', 'CDM', 40, true);


INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, t.tenant_id
FROM lookup_value
    join v_tenant t
WHERE lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE');

-- ACCESS_TYPE
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Access Type', 'ACCESS_TYPE', true, true, true);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'On Net', 'On Net', 10, true),
            ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'Off Net', 'Off Net', 20, true);


INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, t.tenant_id
FROM lookup_value
    join v_tenant t
WHERE lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE');

-- HANDOFF_FIBER_MODE
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Handoff Fiber Mode', 'HANDOFF_FIBER_MODE', true, true, true);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'Single Mode', 'Single Mode', 10, true),
            ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'Multi-Mode', 'Multi-Mode', 20, true),
            ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE'), 'Plastic Optical Fiber', 'Plastic Optical Fiber', 30, true);


INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, t.tenant_id
FROM lookup_value
    join v_tenant t
WHERE lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE');
