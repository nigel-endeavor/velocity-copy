INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Dispatch Vendor', 'DISPATCH_VENDOR', true, true, true);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'DISPATCH_VENDOR'), 'Endeavor', 'Endeavor', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'DISPATCH_VENDOR'), 'Client', 'Client', 20, true);