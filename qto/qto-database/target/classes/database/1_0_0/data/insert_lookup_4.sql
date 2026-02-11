INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Jeopardy Responsibility', 'JEOPARDY_RESPONSIBILITY', 1, 1, 0);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'JEOPARDY_RESPONSIBILITY');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Carrier', 'Carrier', 0, 1),
       (@lookupTypeCode, 'Customer', 'Customer', 0, 1),
       (@lookupTypeCode, 'Vertek', 'Vertek', 0, 1);