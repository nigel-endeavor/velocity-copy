

# Primary
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Endeavor', 'Endeavor', 60, true);

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Endeavor'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Demo Tenant'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'QTO First Tenant'));


INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'General', 'General', 70, true);

Set LASTVAL() = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Endeavor'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Demo Tenant'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'QTO First Tenant'));

# Secondary


# Secondary - Tech
# None needed "Competency" already exists

# Secondary - ISS

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Project Manager', 'Project Manager', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Endeavor'));

Set LASTVAL() = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Endeavor'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Demo Tenant'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'QTO First Tenant'));


# Secondary - General

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                              AND lookup_value = 'General');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Production', 'Production', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Endeavor'));

Set LASTVAL() = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Endeavor'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Demo Tenant'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'QTO First Tenant'));


# Tertiary


# Tertiary - Tech	- Competency

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Tech')
                              AND lookup_value = 'Competency');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'No call to TTU', 'No call to TTU', 5, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Endeavor'));

Set LASTVAL() = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Endeavor'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Demo Tenant'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'QTO First Tenant'));

# Tertiary - ISS - Project Manager

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'Endeavor')
                              AND lookup_value = 'Project Manager');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Cancel Request', 'Cancel Request', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Endeavor'));

Set LASTVAL() = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Endeavor'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Demo Tenant'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'QTO First Tenant'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Next Day Cancel Request', 'Next Day Cancel Request', 20, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Endeavor'));

Set LASTVAL() = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Endeavor'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Demo Tenant'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'QTO First Tenant'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Replace 4g/5g Ticket with Circuit', 'Replace 4g/5g Ticket with Circuit', 30, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Endeavor'));

Set LASTVAL() = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Endeavor'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Demo Tenant'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'QTO First Tenant'));

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Same Day Cancel Request', 'Same Day Cancel Request', 40, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Endeavor'));

Set LASTVAL() = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Endeavor'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Demo Tenant'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'QTO First Tenant'));



# Tertiary - General - Production

                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY')
                              AND parent_lookup_value_id = (SELECT lookup_value_id FROM lookup_value
                                                            WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY')
                                                              AND lookup_value = 'General')
                              AND lookup_value = 'Production');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, parent_lookup_value_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY'), 'Cancel in Error', 'Cancel in Error', 10, 1, (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (select lookup_type_id from lookup_type where lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY') AND lookup_value = 'Endeavor'));

Set LASTVAL() = LAST_INSERT_ID();

INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Endeavor'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'Demo Tenant'));
INSERT INTO tenant_lookup_value (lookup_value_id, tenant_id)
values (LASTVAL(), (select tenant_id from v_tenant where name = 'QTO First Tenant'));
