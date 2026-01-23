set @tenantId = (select tenant_id from v_tenant where name = 'Endeavor');
set @demo = (select tenant_id from v_tenant where name = 'Demo Tenant');
set @first = (select tenant_id from v_tenant where name = 'QTO First Tenant');

# Tertiary
SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_TERTIARY');
# Tertiary - ISS - Project Manager
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Endeavor')
                              AND lookup_value = 'Project Manager');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Reschedule', 'Reschedule', 35, 1, @parentLookupValueId);

Set @lookupTypeId = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @tenantId);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @demo);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @first);
