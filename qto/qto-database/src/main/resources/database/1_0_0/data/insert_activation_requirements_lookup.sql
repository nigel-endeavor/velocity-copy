INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Activation Requirements', 'ACTIVATION_REQUIREMENTS', true, true, true);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REQUIREMENTS'), 'Site Access', 'Site Access', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REQUIREMENTS'), 'Equipment Inventory', 'Equipment Inventory', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REQUIREMENTS'), 'Router Activated', 'Router Activated', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REQUIREMENTS'), '4G Signal', '4G Signal', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REQUIREMENTS'), 'DMZ Test', 'DMZ Test', 50, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REQUIREMENTS'), 'Cabling Complete', 'Cabling Complete', 60, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REQUIREMENTS'), 'Site on Primary', 'Site on Primary', 70, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REQUIREMENTS'), 'Site Converted', 'Site Converted', 80, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REQUIREMENTS'), 'LAN Tested', 'LAN Tested', 90, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REQUIREMENTS'), 'WIFI Covered', 'WIFI Covered', 100, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REQUIREMENTS'), 'VoIP Phones Activated', 'VoIP Phones Activated', 110, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REQUIREMENTS'), 'Network Complete', 'Network Complete', 120, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REQUIREMENTS'), 'Network Cutover', 'Network Cutover', 130, true);
