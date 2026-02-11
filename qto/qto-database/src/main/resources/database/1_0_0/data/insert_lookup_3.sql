INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Interface Connector', 'INTERFACE_CONNECTOR', 1, 1, 1);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'INTERFACE_CONNECTOR');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'RJ45', 'RJ45', 10, 1),
       (@lookupTypeCode, 'ST', 'ST', 20, 1),
       (@lookupTypeCode, 'SC', 'SC', 30, 1),
       (@lookupTypeCode, 'FC', 'FC', 40, 1),
       (@lookupTypeCode, 'MT-RJ', 'MT-RJ', 50, 1),
       (@lookupTypeCode, 'LC', 'LC', 60, 1),
       (@lookupTypeCode, 'MPO/MTP', 'MPO/MTP', 70, 1);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Carrier Activation Method', 'CARRIER_ACTIVATION_METHOD', 1, 1, 1);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CARRIER_ACTIVATION_METHOD');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Remote', 'Remote', 10, 1),
       (@lookupTypeCode, 'On-Site', 'On-Site', 20, 1);
