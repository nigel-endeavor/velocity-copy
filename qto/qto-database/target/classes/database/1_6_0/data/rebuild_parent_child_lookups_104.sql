SET @tenantId = (Select tenant_id from v_tenant t where t.name = 'Endeavor');
# Primary
SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY');
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, tenant_id)
VALUES (@lookupTypeCode, 'Customer', 'Customer', 10, 1, @tenantId),
       (@lookupTypeCode, 'Equipment', 'Equipment', 20, 1, @tenantId),
       (@lookupTypeCode, 'Carrier', 'Carrier', 30, 1, @tenantId),
       (@lookupTypeCode, 'Systems', 'Systems', 40, 1, @tenantId),
       (@lookupTypeCode, 'Tech', 'Tech', 50, 1, @tenantId);

# Secondary
SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY');

# Secondary - Customer
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Customer'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Access', 'Access', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Other', 'Other', 20, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Infrastructure', 'Infrastructure', 30, 1, @parentLookupValueId, @tenantId);

# Secondary - Equipment
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Equipment'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Cradlepoint', 'Cradlepoint', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Cabinet', 'Cabinet', 20, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Router', 'Router', 30, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Switch', 'Switch', 40, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'UPS', 'UPS', 50, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'VoIP', 'VoIP', 60, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'WattBox', 'WattBox', 70, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Other', 'Other', 80, 1, @parentLookupValueId, @tenantId);

# Secondary - Carrier
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Carrier'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Circuit', 'Circuit', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Modem', 'Modem', 20, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Other', 'Other', 30, 1, @parentLookupValueId, @tenantId);

# Secondary - Systems
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Systems'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Config', 'Config', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Other', 'Other', 20, 1, @parentLookupValueId, @tenantId);

# Secondary - Tech
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Tech'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Competency', 'Competency', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Tech Tools', 'Tech Tools', 20, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Other', 'Other', 30, 1, @parentLookupValueId, @tenantId);

# Tertiary
SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_TERTIARY');

# Tertiary - Customer - Access
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Customer'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'Access'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'MOD', 'MOD', 10, 1, @parentLookupValueId, @tenantId);

# Tertiary - Customer - Other
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Customer'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'Other'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Other', 'Other', 10, 1, @parentLookupValueId, @tenantId);

# Tertiary - Customer - Infrastructure
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Customer'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'Infrastructure'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Inside Wiring', 'Inside Wiring', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Power Supply', 'Power Supply', 20, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Site Not Ready', 'Site Not Ready', 30, 1, @parentLookupValueId, @tenantId);

# Tertiary - Equipment - Cradlepoint
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'Cradlepoint'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Defective', 'Defective', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Not On Site', 'Not On Site', 20, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Signal', 'Signal', 30, 1, @parentLookupValueId, @tenantId);

# Tertiary - Equipment - Cabinet
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'Cabinet'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Broken', 'Broken', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Not On Site', 'Not On Site', 20, 1, @parentLookupValueId, @tenantId);

# Tertiary - Equipment - Router
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'Router'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Broken', 'Broken', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Not On Site', 'Not On Site', 20, 1, @parentLookupValueId, @tenantId);

# Tertiary - Equipment - Switch
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'Switch'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Broken', 'Broken', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Not On Site', 'Not On Site', 20, 1, @parentLookupValueId, @tenantId);

# Tertiary - Equipment - UPS
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'UPS'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Broken', 'Broken', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Not On Site', 'Not On Site', 20, 1, @parentLookupValueId, @tenantId);

# Tertiary - Equipment - VoIP
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'VoIP'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Broken', 'Broken', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Not On Site', 'Not On Site', 20, 1, @parentLookupValueId, @tenantId);

# Tertiary - Equipment - WattBox
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'WattBox'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Broken', 'Broken', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Not On Site', 'Not On Site', 20, 1, @parentLookupValueId, @tenantId);

# Tertiary - Carrier - Circuit
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Carrier'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'Circuit'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Bouncing', 'Bouncing', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Incorrectly Installed', 'Incorrectly Installed', 20, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'No Sync', 'No Sync', 30, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Not installed', 'Not installed', 40, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Tag & Locate', 'Tag & Locate', 50, 1, @parentLookupValueId, @tenantId);

# Tertiary - Carrier - Modem
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Carrier'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'Modem'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Config', 'Config', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Defective', 'Defective', 20, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Surf', 'Surf', 30, 1, @parentLookupValueId, @tenantId);

# Tertiary - Carrier - Other
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Carrier'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'Other'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Other', 'Other', 10, 1, @parentLookupValueId, @tenantId);

# Tertiary - Systems - Config
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Systems'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'Config'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Not Up/Functioning', 'Not Up/Functioning', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Script/Config/FW', 'Script/Config/FW', 20, 1, @parentLookupValueId, @tenantId);

# Tertiary - Systems - Other
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Systems'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'Other'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Other', 'Other', 10, 1, @parentLookupValueId, @tenantId);

# Tertiary - Tech - Competency
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Tech'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'Competency'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Out of SOW', 'Out of SOW', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Out of Time', 'Out of Time', 20, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Technical Ability', 'Technical Ability', 30, 1, @parentLookupValueId, @tenantId);

# Tertiary - Tech - Tech Tools
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Tech'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'Tech Tools'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Ladder / Lift', 'Ladder / Lift', 10, 1, @parentLookupValueId, @tenantId),
       (@lookupTypeCode, 'Laptop', 'Laptop', 20, 1, @parentLookupValueId, @tenantId);

# Tertiary - Tech - Other
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Tech'
                                                              AND tenant_id = @tenantId)
                              AND lookup_value = 'Other'
                              AND tenant_id = @tenantId);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES (@lookupTypeCode, 'Other', 'Other', 10, 1, @parentLookupValueId, @tenantId);


# Order Type and Sub Order Type -->
SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SUB_ORDER_TYPE');
#<!--Move-->
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ORDER_TYPE')
                              AND lookup_value = 'Move'
                              AND tenant_id = @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, 'Same Service - Change in floor/suite/MPOE/campus building', 'Same Service - Change in floor/suite/MPOE/campus building', 10, 1, @parentLookupValueId, @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, 'New Service - Change in floor/suite/MPOE/campus building', 'New Service - Change in floor/suite/MPOE/campus building', 20, 1, @parentLookupValueId, @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, 'New Service - Change in main address', 'New Service - Change in main address', 30, 1, @parentLookupValueId, @tenantId);
#<!--Add-->
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ORDER_TYPE')
                              AND lookup_value = 'Add'
                              AND tenant_id = @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, 'Static IP Add', 'Static IP Add', 10, 1, @parentLookupValueId, @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, 'Equipment / MPOE Reserve', 'Equipment / MPOE Reserve', 20, 1, @parentLookupValueId, @tenantId);
#<!--Change-->
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ORDER_TYPE')
                              AND lookup_value = 'Change'
                              AND tenant_id = @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, 'Billing Party Change - Outbound', 'Billing Party Change - Outbound', 10, 1, @parentLookupValueId, @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, 'Billing Party Change - Inbound', 'Billing Party Change - Inbound', 10, 1, @parentLookupValueId, @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, 'New Service - Change carrier', 'New Service - Change carrier', 10, 1, @parentLookupValueId, @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, 'New Service - Speed change', 'New Service - Speed change', 10, 1, @parentLookupValueId, @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, 'Same Service - Speed change', 'Same Service - Speed change', 10, 1, @parentLookupValueId, @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, 'Same Service - Rate or term change', 'Same Service - Rate or term change', 10, 1, @parentLookupValueId, @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, 'Same service - Configuration/Feature change', 'Same service - Configuration/Feature change', 10, 1, @parentLookupValueId, @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, 'Same Service - Ethernet adding handoff/extension', 'Same Service - Ethernet adding handoff/extension', 10, 1, @parentLookupValueId, @tenantId);
#<!--Disconnect-->
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ORDER_TYPE')
                              AND lookup_value = 'Disconnect'
                              AND tenant_id = @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, '--Upgraded Speed -Service replaced with same carrier', '--Upgraded Speed -Service replaced with same carrier', 10, 1, @parentLookupValueId, @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, '--Poor Performance - Service replaced with same carrier', '--Poor Performance - Service replaced with same carrier', 10, 1, @parentLookupValueId, @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, '--Poor Performance - Service replaced with new carrier', '--Poor Performance - Service replaced with new carrier', 10, 1, @parentLookupValueId, @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, '--Service Groom - Service replaced with new carrier', '--Service Groom - Service replaced with new carrier', 10, 1, @parentLookupValueId, @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, '--Customer Closed Location', '--Customer Closed Location', 10, 1, @parentLookupValueId, @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, '--Customer Dissatisfied', '--Customer Dissatisfied', 10, 1, @parentLookupValueId, @tenantId);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES (@lookupTypeCode, '--Customer Declined Renewal', '--Customer Declined Renewal', 10, 1, @parentLookupValueId, @tenantId);
