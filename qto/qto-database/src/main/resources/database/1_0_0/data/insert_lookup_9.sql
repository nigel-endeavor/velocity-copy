INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Contact Roles', 'CONTACT_ROLE', true, true, true);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CONTACT_ROLE'), 'Customer LCON', 'Customer LCON', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CONTACT_ROLE'), 'Property Manager', 'Property Manager', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CONTACT_ROLE'), 'Carrier', 'Carrier', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CONTACT_ROLE'), 'Construction', 'Construction', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CONTACT_ROLE'), 'Circuit Alert', 'Circuit Alert', 50, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CONTACT_ROLE'), 'Security', 'Security', 60, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CONTACT_ROLE'), 'Field Dispatch', 'Field Dispatch', 70, true);
