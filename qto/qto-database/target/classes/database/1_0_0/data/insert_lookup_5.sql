SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SERVICE_JEOPARDY');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Network Provider Construction', 'Network Provider Construction', 0, 1);
