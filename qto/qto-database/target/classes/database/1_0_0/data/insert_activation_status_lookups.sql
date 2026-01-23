INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Activation Attempt Status', 'ACTIVATION_ATTEMPT_STATUS', 1, 1, 1);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ATTEMPT_STATUS');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Schedule Date Confirmed', 'Schedule Date Confirmed', 10, 1),
       (@lookupTypeCode, 'In Progress', 'In Progress', 20, 1),
       (@lookupTypeCode, 'Incomplete-Pending Reschedule', 'Incomplete-Pending Reschedule', 30, 1),
       (@lookupTypeCode, 'Complete', 'Complete', 40, 1);
