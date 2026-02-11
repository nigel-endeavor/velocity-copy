SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SERVICE_JEOPARDY');
set @tenantId = (SELECT tenant_id from qto.v_tenant where name = 'Endeavor');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Pending Cost Approval', 'Pending Cost Approval', 205, 1);

Set @lookupTypeId = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @tenantId);


