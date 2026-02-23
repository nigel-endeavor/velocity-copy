

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SERVICE_JEOPARDY'), 'Pending Cost Approval', 'Pending Cost Approval', 205, true);

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (SELECT tenant_id from v_tenant where name = 'Endeavor'));


