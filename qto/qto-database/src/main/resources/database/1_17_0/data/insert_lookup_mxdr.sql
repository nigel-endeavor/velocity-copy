SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ALL_SERVICE_TYPES');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, tenant_id)
VALUES (@lookupTypeCode, 'cyber360-MXDR', 'cyber360-MXDR', 0, 1, 0);
