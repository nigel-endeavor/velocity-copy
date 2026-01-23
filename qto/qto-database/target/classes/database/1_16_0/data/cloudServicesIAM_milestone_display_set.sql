INSERT INTO milestone_display_set (display_group, display_set_label, display_type)
VALUES ('CLOUD_SERVICES_IAM_SERVICE_MILESTONE', 'Cloud Services-IAM Milestones', 'Cloud Services-IAM');

INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Review existing CA & MFA Policies', 'REVIEW EXISTING CA & MFA POLICIES');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Sign In Policies Enabled', 'SIGN IN POLICIES ENABLED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Conditional Access Policy Verification', 'CONDITIONAL ACCESS POLICY VERIFICATION');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Geographic Restrictions Enabled', 'GEOGRAPHIC RESTRICTIONS ENABLED');

INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Device Compliance Enabled', 'DEVICE COMPLIANCE ENABLED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Password Reset Enabled for self-service', 'PASSWORD RESET ENABLED FOR SELF-SERVICE');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Break-Glass Account Configured', 'BREAK-GLASS ACCOUNT CONFIGURED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('PIM Enablement', 'PIM ENABLEMENT');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Implementation verified', 'IMPLEMENTATION VERIFIED');


SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'CLOUD_SERVICES_IAM_SERVICE_MILESTONE');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description, inventory_flag, progress_percentage, status)
VALUES
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0, 'Order Request Received', 0, 5, 'Pending Assignment'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 0, 0, 0, 0, 'Order Received', 0, 5, 'Pending Assignment'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_REQUESTED_INSTALL'), 1, 30, 1, 0, 0, 0, 0, 'Customer Requested Install', 0, 5, 'Pending Assignment'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ENGINEER_ASSIGNED'), 1, 40, 1, 0, 1, 0, 0, 'Engineer Assigned', 0, 10, 'Engineer Assigned'),

(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'TECH_DATA_GATHERING_MEETING_SCHEDULED'), 1, 2500, 1, 1, 0, 0, 0, 'Kickoff call with customer to determine meeting frequency and gain access to the tenant', 0, 15, 'Technical Data Gathering'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'TECH_DATA_GATHERING_MEETING_COMPLETED'), 1, 2510, 1, 1, 0, 0, 0, 'Kickoff call/TDG completed with Customer', 0, 20, 'Technical Data Gathering Complete'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'REVIEW EXISTING CA & MFA POLICIES'), 1, 2520, 1, 1, 0, 0, 0, 'Review existing Conditional Access and MFA Policies', 0, 30, 'Existing Policy review'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SIGN IN POLICIES ENABLED'), 1, 2530, 1, 1, 0, 0, 0, 'Ensure User and Sign-In Risk policies are enabled', 0, 40,'Policy & Compliance Configuration'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CONDITIONAL ACCESS POLICY VERIFICATION'), 1, 2540, 1, 1, 0, 0, 0, 'Ensure all users and applications are protected by CA policies', 0, 50, 'Policy & Compliance Configuration'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'GEOGRAPHIC RESTRICTIONS ENABLED'), 1, 2550, 1, 1, 0, 0, 0, 'Ensure access from restricted countries is blocked', 0, 60, 'Policy & Compliance Configuration'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'DEVICE COMPLIANCE ENABLED'), 1, 2560, 1, 1, 0, 0, 0, 'Ensure Device Compliance is enabled', 0, 65, 'Policy & Compliance Configuration'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'PASSWORD RESET ENABLED FOR SELF-SERVICE'), 1, 2570, 1, 1, 0, 0, 0, 'Ensure Self Service Password Reset is enabled', 0, 70, 'Policy & Compliance Configuration'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'BREAK-GLASS ACCOUNT CONFIGURED'), 1, 2575, 1, 1, 0, 0, 0, 'Ensure Break-Glass account is configured to avoid complete lockout from Azure', 0, 75, 'Policy & Compliance Configuration'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'PIM ENABLEMENT'), 1, 2580, 1, 1, 0, 0, 0, 'Ensure PIM is enabled for privileged role management', 0, 80, 'Policy & Compliance Configuration'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'IMPLEMENTATION VERIFIED'), 1, 2590, 1, 1, 0, 0, 0, 'Wrap up call to ensure goals of the contract are completed and to close out the project', 0, 90, 'Implement Complete'),


(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 3010, 1, 1, 0, 0, 0, 'Order is on hold. Jeop has been opened and noted', 0, 0, 'On Hold'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 3020, 1, 1, 0, 0, 0, 'All aspects of the order have successfully been completed', 1, 100, 'Service Complete'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 3030, 1, 1, 0, 0, 0, 'Order is on hold. Jeop has been opened and noted. Order has a complete date and closed out', 0, 100, 'Service Cancelled'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 3040, 1, 1, 0, 0, 0, 'Date order has been assigned to another users worklist', 0, 0, 'Change In Assignment');
