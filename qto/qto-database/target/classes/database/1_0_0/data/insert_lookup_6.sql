INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Client Project Manager', 'CLIENT_PROJECT_MANAGER', 1, 1, 0);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CLIENT_PROJECT_MANAGER');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Tom Smith', 'Tom Smith', 0, 1),
       (@lookupTypeCode, 'Bob Lemon', 'Bob Lemon', 0, 1),
       (@lookupTypeCode, 'John Doe', 'John Doe', 0, 1);