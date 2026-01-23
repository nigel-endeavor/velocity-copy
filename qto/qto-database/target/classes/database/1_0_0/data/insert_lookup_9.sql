INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Contact Roles', 'CONTACT_ROLE', 1, 1, 1);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CONTACT_ROLE');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Customer LCON', 'Customer LCON', 10, 1),
       (@lookupTypeCode, 'Property Manager', 'Property Manager', 20, 1),
       (@lookupTypeCode, 'Carrier', 'Carrier', 30, 1),
       (@lookupTypeCode, 'Construction', 'Construction', 40, 1),
       (@lookupTypeCode, 'Circuit Alert', 'Circuit Alert', 50, 1),
       (@lookupTypeCode, 'Security', 'Security', 60, 1),
       (@lookupTypeCode, 'Field Dispatch', 'Field Dispatch', 70, 1);
