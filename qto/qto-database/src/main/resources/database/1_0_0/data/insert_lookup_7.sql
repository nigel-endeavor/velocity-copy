INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('NA Time Zones', 'NA_TIME_ZONE', 1, 1, 1);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'NA_TIME_ZONE');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Atlantic', 'Atlantic', 10, 1),
       (@lookupTypeCode, 'Eastern', 'Eastern', 20, 1),
       (@lookupTypeCode, 'Central', 'Central', 30, 1),
       (@lookupTypeCode, 'Mountain', 'Mountain', 40, 1),
       (@lookupTypeCode, 'Pacific', 'Pacific', 50, 1),
       (@lookupTypeCode, 'Alaska', 'Alaska', 60, 1),
       (@lookupTypeCode, 'Hawaii-Aleutian', 'Hawaii-Aleutian', 70, 1);