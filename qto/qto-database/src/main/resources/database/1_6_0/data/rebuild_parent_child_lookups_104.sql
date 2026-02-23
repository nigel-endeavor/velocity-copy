
# Primary
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Customer', 'Customer', 10, 1, (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Equipment', 'Equipment', 20, 1, (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Carrier', 'Carrier', 30, 1, (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Systems', 'Systems', 40, 1, (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Tech', 'Tech', 50, 1, (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Secondary


# Secondary - Customer

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Access', 'Access', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Infrastructure', 'Infrastructure', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Secondary - Equipment

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Equipment'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Cradlepoint', 'Cradlepoint', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Cabinet', 'Cabinet', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Router', 'Router', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Switch', 'Switch', 40, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'UPS', 'UPS', 50, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'VoIP', 'VoIP', 60, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'WattBox', 'WattBox', 70, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 80, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Secondary - Carrier

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Carrier'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Circuit', 'Circuit', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Modem', 'Modem', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Secondary - Systems

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Systems'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Config', 'Config', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Secondary - Tech

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'Tech'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Competency', 'Competency', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Tech Tools', 'Tech Tools', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary


# Tertiary - Customer - Access

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Customer'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'Access'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'MOD', 'MOD', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Customer - Other

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Customer'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'Other'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Customer - Infrastructure

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Customer'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'Infrastructure'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Inside Wiring', 'Inside Wiring', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Power Supply', 'Power Supply', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Site Not Ready', 'Site Not Ready', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Equipment - Cradlepoint

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'Cradlepoint'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Defective', 'Defective', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not On Site', 'Not On Site', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Signal', 'Signal', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Equipment - Cabinet

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'Cabinet'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Broken', 'Broken', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not On Site', 'Not On Site', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Equipment - Router

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'Router'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Broken', 'Broken', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not On Site', 'Not On Site', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Equipment - Switch

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'Switch'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Broken', 'Broken', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not On Site', 'Not On Site', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Equipment - UPS

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'UPS'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Broken', 'Broken', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not On Site', 'Not On Site', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Equipment - VoIP

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'VoIP'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Broken', 'Broken', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not On Site', 'Not On Site', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Equipment - WattBox

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Equipment'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'WattBox'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Broken', 'Broken', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not On Site', 'Not On Site', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Carrier - Circuit

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Carrier'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'Circuit'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Bouncing', 'Bouncing', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Incorrectly Installed', 'Incorrectly Installed', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'No Sync', 'No Sync', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not installed', 'Not installed', 40, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Tag & Locate', 'Tag & Locate', 50, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Carrier - Modem

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Carrier'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'Modem'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Config', 'Config', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Defective', 'Defective', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Surf', 'Surf', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Carrier - Other

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Carrier'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'Other'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Systems - Config

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Systems'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'Config'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Not Up/Functioning', 'Not Up/Functioning', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Script/Config/FW', 'Script/Config/FW', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Systems - Other

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Systems'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'Other'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Tech - Competency

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Tech'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'Competency'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Out of SOW', 'Out of SOW', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Out of Time', 'Out of Time', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Technical Ability', 'Technical Ability', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Tech - Tech Tools

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Tech'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'Tech Tools'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Ladder / Lift', 'Ladder / Lift', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor')),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Laptop', 'Laptop', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

# Tertiary - Tech - Other

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Tech'
                                                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'))
                              AND lookup_value = 'Other'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Other', 'Other', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));


# Order Type and Sub Order Type -->

#<!--Move-->

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ORDER_TYPE')
                              AND lookup_value = 'Move'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Same Service - Change in floor/suite/MPOE/campus building', 'Same Service - Change in floor/suite/MPOE/campus building', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'New Service - Change in floor/suite/MPOE/campus building', 'New Service - Change in floor/suite/MPOE/campus building', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'New Service - Change in main address', 'New Service - Change in main address', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
#<!--Add-->

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ORDER_TYPE')
                              AND lookup_value = 'Add'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Static IP Add', 'Static IP Add', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Equipment / MPOE Reserve', 'Equipment / MPOE Reserve', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
#<!--Change-->

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ORDER_TYPE')
                              AND lookup_value = 'Change'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Billing Party Change - Outbound', 'Billing Party Change - Outbound', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Billing Party Change - Inbound', 'Billing Party Change - Inbound', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'New Service - Change carrier', 'New Service - Change carrier', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'New Service - Speed change', 'New Service - Speed change', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Same Service - Speed change', 'Same Service - Speed change', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Same Service - Rate or term change', 'Same Service - Rate or term change', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Same service - Configuration/Feature change', 'Same service - Configuration/Feature change', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Same Service - Ethernet adding handoff/extension', 'Same Service - Ethernet adding handoff/extension', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
#<!--Disconnect-->

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ORDER_TYPE')
                              AND lookup_value = 'Disconnect'
                              AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), '--Upgraded Speed -Service replaced with same carrier', '--Upgraded Speed -Service replaced with same carrier', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), '--Poor Performance - Service replaced with same carrier', '--Poor Performance - Service replaced with same carrier', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), '--Poor Performance - Service replaced with new carrier', '--Poor Performance - Service replaced with new carrier', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), '--Service Groom - Service replaced with new carrier', '--Service Groom - Service replaced with new carrier', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), '--Customer Closed Location', '--Customer Closed Location', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), '--Customer Dissatisfied', '--Customer Dissatisfied', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id, tenant_id) VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), '--Customer Declined Renewal', '--Customer Declined Renewal', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Customer' AND tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor')), (Select tenant_id from v_tenant t where t.name = 'Endeavor'));
