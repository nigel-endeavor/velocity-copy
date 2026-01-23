set @tenantId = (select tenant_id from v_tenant where name = 'Endeavor');
set @demo = (select tenant_id from v_tenant where name = 'Demo Tenant');
set @first = (select tenant_id from v_tenant where name = 'QTO First Tenant');

# Primary

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY');
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Endeavor', 'Endeavor', 60, 1);

Set @lookupTypeId = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @tenantId);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @demo);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @first);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY');
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'General', 'General', 70, 1);

Set @lookupTypeId = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @tenantId);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @demo);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @first);

# Secondary
SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY');

# Secondary - Tech
# None needed "Competency" already exists

# Secondary - ISS
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Endeavor');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Project Manager', 'Project Manager', 10, 1, @parentLookupValueId);

Set @lookupTypeId = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @tenantId);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @demo);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @first);


# Secondary - General
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'General');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Production', 'Production', 10, 1, @parentLookupValueId);

Set @lookupTypeId = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @tenantId);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @demo);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @first);


# Tertiary
SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_TERTIARY');

# Tertiary - Tech	- Competency
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Tech')
                              AND lookup_value = 'Competency');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'No call to TTU', 'No call to TTU', 5, 1, @parentLookupValueId);

Set @lookupTypeId = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @tenantId);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @demo);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @first);

# Tertiary - ISS - Project Manager
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Endeavor')
                              AND lookup_value = 'Project Manager');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Cancel Request', 'Cancel Request', 10, 1, @parentLookupValueId);

Set @lookupTypeId = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @tenantId);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @demo);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @first);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Next Day Cancel Request', 'Next Day Cancel Request', 20, 1, @parentLookupValueId);

Set @lookupTypeId = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @tenantId);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @demo);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @first);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Replace 4g/5g Ticket with Circuit', 'Replace 4g/5g Ticket with Circuit', 30, 1, @parentLookupValueId);

Set @lookupTypeId = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @tenantId);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @demo);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @first);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Same Day Cancel Request', 'Same Day Cancel Request', 40, 1, @parentLookupValueId);

Set @lookupTypeId = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @tenantId);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @demo);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @first);



# Tertiary - General - Production
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'General')
                              AND lookup_value = 'Production');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Cancel in Error', 'Cancel in Error', 10, 1, @parentLookupValueId);

Set @lookupTypeId = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @tenantId);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @demo);
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (@lookupTypeId, @first);
