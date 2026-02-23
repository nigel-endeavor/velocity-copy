INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Activation Attempt Status', 'ACTIVATION_ATTEMPT_STATUS', true, true, true);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ATTEMPT_STATUS'), 'Schedule Date Confirmed', 'Schedule Date Confirmed', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ATTEMPT_STATUS'), 'In Progress', 'In Progress', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ATTEMPT_STATUS'), 'Incomplete-Pending Reschedule', 'Incomplete-Pending Reschedule', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ATTEMPT_STATUS'), 'Complete', 'Complete', 40, true);
