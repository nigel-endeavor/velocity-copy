INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Building Status', 'BUILDING_STATUS', 1, 1, 1);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'BUILDING_STATUS');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'On Net', 'On Net', 10, 1),
       (@lookupTypeCode, 'Near Net', 'Near Net', 20, 1),
       (@lookupTypeCode, 'Off Net', 'Off Net', 30, 1);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Network Protocol', 'NETWORK_PROTOCOL', 1, 1, 1);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'NETWORK_PROTOCOL');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'DHCP', 'DHCP', 10, 1),
       (@lookupTypeCode, 'PPPoE', 'PPPoE', 20, 1),
       (@lookupTypeCode, 'Static', 'Static', 30, 1);
