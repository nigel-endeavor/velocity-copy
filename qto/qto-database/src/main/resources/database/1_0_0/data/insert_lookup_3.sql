INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Interface Connector', 'INTERFACE_CONNECTOR', true, true, true);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'INTERFACE_CONNECTOR'), 'RJ45', 'RJ45', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'INTERFACE_CONNECTOR'), 'ST', 'ST', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'INTERFACE_CONNECTOR'), 'SC', 'SC', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'INTERFACE_CONNECTOR'), 'FC', 'FC', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'INTERFACE_CONNECTOR'), 'MT-RJ', 'MT-RJ', 50, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'INTERFACE_CONNECTOR'), 'LC', 'LC', 60, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'INTERFACE_CONNECTOR'), 'MPO/MTP', 'MPO/MTP', 70, true);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Carrier Activation Method', 'CARRIER_ACTIVATION_METHOD', true, true, true);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'INTERFACE_CONNECTOR'), 'Remote', 'Remote', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'INTERFACE_CONNECTOR'), 'On-Site', 'On-Site', 20, true);
