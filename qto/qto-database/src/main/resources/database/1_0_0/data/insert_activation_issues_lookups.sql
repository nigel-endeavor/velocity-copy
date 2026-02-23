# Primary
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Activation Issue Primary', 'ACTIVATION_ISSUE_PRIMARY', true, true, true);
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Customer', 'Customer', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Equipment', 'Equipment', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Carrier', 'Carrier', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Systems', 'Systems', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Tech', 'Tech', 50, true);

# Secondary
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Activation Issue Secondary', 'ACTIVATION_ISSUE_SECONDARY', true, true, true);


# Secondary - Customer

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Access', 'Access', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Infrastructure', 'Infrastructure', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Secondary - Equipment

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Equipment');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Cradlepoint', 'Cradlepoint', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Cabinet', 'Cabinet', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Router', 'Router', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Switch', 'Switch', 40, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'UPS', 'UPS', 50, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'VoIP', 'VoIP', 60, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'WattBox', 'WattBox', 70, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 80, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Secondary - Carrier

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Carrier');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Circuit', 'Circuit', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Modem', 'Modem', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Secondary - Systems

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Systems');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Config', 'Config', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Secondary - Tech

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Tech');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Competency', 'Competency', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Tech Tools', 'Tech Tools', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Activation Issue Tertiary', 'ACTIVATION_ISSUE_TERTIARY', true, true, true);


# Tertiary - Customer - Access

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Customer')
                              AND lookup_value = 'Access');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'MOD', 'MOD', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Customer - Other

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Customer')
                              AND lookup_value = 'Other');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Customer - Infrastructure

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Customer')
                              AND lookup_value = 'Infrastructure');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Inside Wiring', 'Inside Wiring', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Power Supply', 'Power Supply', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Site Not Ready', 'Site Not Ready', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Equipment - Cradlepoint

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment')
                              AND lookup_value = 'Cradlepoint');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Defective', 'Defective', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not On Site', 'Not On Site', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Signal', 'Signal', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Equipment - Cabinet

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment')
                              AND lookup_value = 'Cabinet');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Broken', 'Broken', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not On Site', 'Not On Site', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Equipment - Router

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment')
                              AND lookup_value = 'Router');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Broken', 'Broken', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not On Site', 'Not On Site', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Equipment - Switch

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment')
                              AND lookup_value = 'Switch');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Broken', 'Broken', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not On Site', 'Not On Site', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Equipment - UPS

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment')
                              AND lookup_value = 'UPS');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Broken', 'Broken', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not On Site', 'Not On Site', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Equipment - VoIP

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment')
                              AND lookup_value = 'VoIP');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Broken', 'Broken', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not On Site', 'Not On Site', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Equipment - WattBox

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment')
                              AND lookup_value = 'WattBox');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Broken', 'Broken', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not On Site', 'Not On Site', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Carrier - Circuit

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Carrier')
                              AND lookup_value = 'Circuit');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Bouncing', 'Bouncing', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Incorrectly Installed', 'Incorrectly Installed', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'No Sync', 'No Sync', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not installed', 'Not installed', 40, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Tag & Locate', 'Tag & Locate', 50, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Carrier - Modem

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Carrier')
                              AND lookup_value = 'Modem');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Config', 'Config', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Defective', 'Defective', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Surf', 'Surf', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Carrier - Other

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Carrier')
                              AND lookup_value = 'Other');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Systems - Config

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Systems')
                              AND lookup_value = 'Config');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not Up/Functioning', 'Not Up/Functioning', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Script/Config/FW', 'Script/Config/FW', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Systems - Other

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Systems')
                              AND lookup_value = 'Other');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Tech - Competency

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Tech')
                              AND lookup_value = 'Competency');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Out of SOW', 'Out of SOW', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Out of Time', 'Out of Time', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Technical Ability', 'Technical Ability', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Tech - Tech Tools

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Tech')
                              AND lookup_value = 'Tech Tools');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Ladder / Lift', 'Ladder / Lift', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Laptop', 'Laptop', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));

# Tertiary - Tech - Other

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Tech')
                              AND lookup_value = 'Other');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer'));
