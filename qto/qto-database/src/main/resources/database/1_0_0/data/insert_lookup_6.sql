INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Client Project Manager', 'CLIENT_PROJECT_MANAGER', true, true, false);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CLIENT_PROJECT_MANAGER'), 'Tom Smith', 'Tom Smith', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CLIENT_PROJECT_MANAGER'), 'Bob Lemon', 'Bob Lemon', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CLIENT_PROJECT_MANAGER'), 'John Doe', 'John Doe', 0, true);