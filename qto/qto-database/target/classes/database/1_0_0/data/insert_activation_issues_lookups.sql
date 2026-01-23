# Primary
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Activation Issue Primary', 'ACTIVATION_ISSUE_PRIMARY', 1, 1, 1);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY');
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Customer', 'Customer', 10, 1),
       (@lookupTypeCode, 'Equipment', 'Equipment', 20, 1),
       (@lookupTypeCode, 'Carrier', 'Carrier', 30, 1),
       (@lookupTypeCode, 'Systems', 'Systems', 40, 1),
       (@lookupTypeCode, 'Tech', 'Tech', 50, 1);

# Secondary
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Activation Issue Secondary', 'ACTIVATION_ISSUE_SECONDARY', 1, 1, 1);
SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY');

# Secondary - Customer
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                           WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                               AND lookup_value = 'Customer');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Access', 'Access', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Other', 'Other', 20, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Infrastructure', 'Infrastructure', 30, 1, @parentLookupValueId);

# Secondary - Equipment
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Equipment');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Cradlepoint', 'Cradlepoint', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Cabinet', 'Cabinet', 20, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Router', 'Router', 30, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Switch', 'Switch', 40, 1, @parentLookupValueId),
       (@lookupTypeCode, 'UPS', 'UPS', 50, 1, @parentLookupValueId),
       (@lookupTypeCode, 'VoIP', 'VoIP', 60, 1, @parentLookupValueId),
       (@lookupTypeCode, 'WattBox', 'WattBox', 70, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Other', 'Other', 80, 1, @parentLookupValueId);

# Secondary - Carrier
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Carrier');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Circuit', 'Circuit', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Modem', 'Modem', 20, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Other', 'Other', 30, 1, @parentLookupValueId);

# Secondary - Systems
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Systems');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Config', 'Config', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Other', 'Other', 20, 1, @parentLookupValueId);

# Secondary - Tech
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Tech');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Competency', 'Competency', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Tech Tools', 'Tech Tools', 20, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Other', 'Other', 30, 1, @parentLookupValueId);

# Tertiary
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Activation Issue Tertiary', 'ACTIVATION_ISSUE_TERTIARY', 1, 1, 1);
SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_TERTIARY');

# Tertiary - Customer - Access
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Customer')
                              AND lookup_value = 'Access');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'MOD', 'MOD', 10, 1, @parentLookupValueId);

# Tertiary - Customer - Other
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Customer')
                              AND lookup_value = 'Other');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Other', 'Other', 10, 1, @parentLookupValueId);

# Tertiary - Customer - Infrastructure
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Customer')
                              AND lookup_value = 'Infrastructure');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Inside Wiring', 'Inside Wiring', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Power Supply', 'Power Supply', 20, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Site Not Ready', 'Site Not Ready', 30, 1, @parentLookupValueId);

# Tertiary - Equipment - Cradlepoint
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment')
                              AND lookup_value = 'Cradlepoint');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Defective', 'Defective', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Not On Site', 'Not On Site', 20, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Signal', 'Signal', 30, 1, @parentLookupValueId);

# Tertiary - Equipment - Cabinet
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment')
                              AND lookup_value = 'Cabinet');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Broken', 'Broken', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Not On Site', 'Not On Site', 20, 1, @parentLookupValueId);

# Tertiary - Equipment - Router
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment')
                              AND lookup_value = 'Router');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Broken', 'Broken', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Not On Site', 'Not On Site', 20, 1, @parentLookupValueId);

# Tertiary - Equipment - Switch
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment')
                              AND lookup_value = 'Switch');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Broken', 'Broken', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Not On Site', 'Not On Site', 20, 1, @parentLookupValueId);

# Tertiary - Equipment - UPS
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment')
                              AND lookup_value = 'UPS');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Broken', 'Broken', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Not On Site', 'Not On Site', 20, 1, @parentLookupValueId);

# Tertiary - Equipment - VoIP
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment')
                              AND lookup_value = 'VoIP');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Broken', 'Broken', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Not On Site', 'Not On Site', 20, 1, @parentLookupValueId);

# Tertiary - Equipment - WattBox
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment')
                              AND lookup_value = 'WattBox');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Broken', 'Broken', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Not On Site', 'Not On Site', 20, 1, @parentLookupValueId);

# Tertiary - Carrier - Circuit
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Carrier')
                              AND lookup_value = 'Circuit');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Bouncing', 'Bouncing', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Incorrectly Installed', 'Incorrectly Installed', 20, 1, @parentLookupValueId),
       (@lookupTypeCode, 'No Sync', 'No Sync', 30, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Not installed', 'Not installed', 40, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Tag & Locate', 'Tag & Locate', 50, 1, @parentLookupValueId);

# Tertiary - Carrier - Modem
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Carrier')
                              AND lookup_value = 'Modem');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Config', 'Config', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Defective', 'Defective', 20, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Surf', 'Surf', 30, 1, @parentLookupValueId);

# Tertiary - Carrier - Other
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Carrier')
                              AND lookup_value = 'Other');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Other', 'Other', 10, 1, @parentLookupValueId);

# Tertiary - Systems - Config
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Systems')
                              AND lookup_value = 'Config');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Not Up/Functioning', 'Not Up/Functioning', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Script/Config/FW', 'Script/Config/FW', 20, 1, @parentLookupValueId);

# Tertiary - Systems - Other
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Systems')
                              AND lookup_value = 'Other');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Other', 'Other', 10, 1, @parentLookupValueId);

# Tertiary - Tech - Competency
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Tech')
                              AND lookup_value = 'Competency');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Out of SOW', 'Out of SOW', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Out of Time', 'Out of Time', 20, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Technical Ability', 'Technical Ability', 30, 1, @parentLookupValueId);

# Tertiary - Tech - Tech Tools
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Tech')
                              AND lookup_value = 'Tech Tools');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Ladder / Lift', 'Ladder / Lift', 10, 1, @parentLookupValueId),
       (@lookupTypeCode, 'Laptop', 'Laptop', 20, 1, @parentLookupValueId);

# Tertiary - Tech - Other
SET @parentLookupValueId = (SELECT lookup_value_id FROM lookup_value
                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Tech')
                              AND lookup_value = 'Other');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES (@lookupTypeCode, 'Other', 'Other', 10, 1, @parentLookupValueId);
