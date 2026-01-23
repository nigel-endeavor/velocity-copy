INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Activation Requirements', 'ACTIVATION_REQUIREMENTS', 1, 1, 1);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_REQUIREMENTS');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Site Access', 'Site Access', 10, 1),
       (@lookupTypeCode, 'Equipment Inventory', 'Equipment Inventory', 20, 1),
       (@lookupTypeCode, 'Router Activated', 'Router Activated', 30, 1),
       (@lookupTypeCode, '4G Signal', '4G Signal', 40, 1),
       (@lookupTypeCode, 'DMZ Test', 'DMZ Test', 50, 1),
       (@lookupTypeCode, 'Cabling Complete', 'Cabling Complete', 60, 1),
       (@lookupTypeCode, 'Site on Primary', 'Site on Primary', 70, 1),
       (@lookupTypeCode, 'Site Converted', 'Site Converted', 80, 1),
       (@lookupTypeCode, 'LAN Tested', 'LAN Tested', 90, 1),
       (@lookupTypeCode, 'WIFI Covered', 'WIFI Covered', 100, 1),
       (@lookupTypeCode, 'VoIP Phones Activated', 'VoIP Phones Activated', 110, 1),
       (@lookupTypeCode, 'Network Complete', 'Network Complete', 120, 1),
       (@lookupTypeCode, 'Network Cutover', 'Network Cutover', 130, 1);
