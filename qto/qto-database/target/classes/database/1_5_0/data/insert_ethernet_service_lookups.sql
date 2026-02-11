-- ETHERNET_PRODUCT_TYPE
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Ethernet Product Type', 'ETHERNET_PRODUCT_TYPE', 1, 1, 0);

SET @lookupTypeId = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeId, 'EPL', 'EPL', 0, 1),
        (@lookupTypeId, 'EVPL', 'EVPL', 0, 1),
        (@lookupTypeId, 'Eline', 'Eline', 0, 1),
        (@lookupTypeId, 'MPLS', 'MPLS', 0, 1),
        (@lookupTypeId, 'Wave', 'Wave', 0, 1),
        (@lookupTypeId, 'IPLC', 'IPLC', 0, 1),
        (@lookupTypeId, 'IPVPN', 'IPVPN', 0, 1);

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, t.tenant_id
FROM lookup_value
         join v_tenant t
WHERE lookup_type_id = @lookupTypeId;

-- MTU
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('MTU', 'MTU', 1, 1, 1);

SET @lookupTypeId = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'MTU');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeId, '1492MTU', '1492MTU', 10, 1),
       (@lookupTypeId, '1452MTU-Optimal', '1452MTU-Optimal', 20, 1);


INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, t.tenant_id
FROM lookup_value
         join v_tenant t
WHERE lookup_type_id = @lookupTypeId;

-- CABLE_CATEGORY
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Cable Category', 'CABLE_CATEGORY', 1, 1, 1);

SET @lookupTypeId = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CABLE_CATEGORY');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeId, 'Coax', 'Coax', 10, 1),
         (@lookupTypeId, 'Fiber', 'Fiber', 20, 1),
         (@lookupTypeId, 'STP', 'STP', 30, 1),
         (@lookupTypeId, 'UTP', 'UTP', 40, 1);


INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, t.tenant_id
FROM lookup_value
    join v_tenant t
WHERE lookup_type_id = @lookupTypeId;

-- MUX
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('MUX', 'MUX', 1, 1, 1);

SET @lookupTypeId = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'MUX');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeId, 'TDM', 'TDM', 10, 1),
            (@lookupTypeId, 'WDM', 'WDM', 20, 1),
            (@lookupTypeId, 'FDM', 'FDM', 30, 1),
            (@lookupTypeId, 'CDM', 'CDM', 40, 1);


INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, t.tenant_id
FROM lookup_value
    join v_tenant t
WHERE lookup_type_id = @lookupTypeId;

-- ACCESS_TYPE
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Access Type', 'ACCESS_TYPE', 1, 1, 1);

SET @lookupTypeId = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACCESS_TYPE');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeId, 'On Net', 'On Net', 10, 1),
            (@lookupTypeId, 'Off Net', 'Off Net', 20, 1);


INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, t.tenant_id
FROM lookup_value
    join v_tenant t
WHERE lookup_type_id = @lookupTypeId;

-- HANDOFF_FIBER_MODE
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Handoff Fiber Mode', 'HANDOFF_FIBER_MODE', 1, 1, 1);

SET @lookupTypeId = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'HANDOFF_FIBER_MODE');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeId, 'Single Mode', 'Single Mode', 10, 1),
            (@lookupTypeId, 'Multi-Mode', 'Multi-Mode', 20, 1),
            (@lookupTypeId, 'Plastic Optical Fiber', 'Plastic Optical Fiber', 30, 1);


INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
SELECT lookup_value_id, t.tenant_id
FROM lookup_value
    join v_tenant t
WHERE lookup_type_id = @lookupTypeId;
