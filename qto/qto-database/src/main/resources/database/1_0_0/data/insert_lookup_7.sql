INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('NA Time Zones', 'NA_TIME_ZONE', true, true, true);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'NA_TIME_ZONE'), 'Atlantic', 'Atlantic', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'NA_TIME_ZONE'), 'Eastern', 'Eastern', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'NA_TIME_ZONE'), 'Central', 'Central', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'NA_TIME_ZONE'), 'Mountain', 'Mountain', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'NA_TIME_ZONE'), 'Pacific', 'Pacific', 50, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'NA_TIME_ZONE'), 'Alaska', 'Alaska', 60, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'NA_TIME_ZONE'), 'Hawaii-Aleutian', 'Hawaii-Aleutian', 70, true);