INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Building Status', 'BUILDING_STATUS', true, true, true);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'BUILDING_STATUS'), 'On Net', 'On Net', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'BUILDING_STATUS'), 'Near Net', 'Near Net', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'BUILDING_STATUS'), 'Off Net', 'Off Net', 30, true);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Network Protocol', 'NETWORK_PROTOCOL', true, true, true);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'BUILDING_STATUS'), 'DHCP', 'DHCP', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'BUILDING_STATUS'), 'PPPoE', 'PPPoE', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'BUILDING_STATUS'), 'Static', 'Static', 30, true);
