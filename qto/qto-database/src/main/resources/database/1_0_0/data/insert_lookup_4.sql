INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Jeopardy Responsibility', 'JEOPARDY_RESPONSIBILITY', true, true, false);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'JEOPARDY_RESPONSIBILITY'), 'Carrier', 'Carrier', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'JEOPARDY_RESPONSIBILITY'), 'Customer', 'Customer', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'JEOPARDY_RESPONSIBILITY'), 'Vertek', 'Vertek', 0, true);