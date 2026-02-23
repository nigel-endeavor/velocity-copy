INSERT INTO milestone_display_set (display_group, display_set_label, display_type)
VALUES ('THREATMDR_SERVICE_MILESTONE', 'threatMDR Milestones', 'threatMDR');

INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Technical Data Gathering Form Sent', 'TECH_DATA_GATHERING_FORM_SENT');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Technical Data Gathering Meeting Scheduled', 'TECH_DATA_GATHERING_MEETING_SCHEDULED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Technical Data Gathering Meeting Completed', 'TECH_DATA_GATHERING_MEETING_COMPLETED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Email USM Anywhere Template Requirements', 'EMAIL_USM_TEMPLATE_REQ');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Inventory Assignment Verified', 'INVENTORY_ASSIGNMENT_VERIFIED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('New USM Anywhere Server Build', 'NEW_USM_SERVER_BUILD');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Implementation QA', 'IMPLEMENTATION_QA');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Verify Assets in SIEM DB', 'VERIFY_ASSETS_SIEM_DB');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Verify Logging Data Sources', 'VERIFY_LOGGING_DATA_SOURCES');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Schedule Vulnerability Scans', 'SCHEDULE_VULNERABILITY_SCANS');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Bulk Alarm Tuning Phase', 'BULK_ALARM_TUNING_PHASE');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('SIEM Event Filtering', 'SIEM_EVENT_FILTERING');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Filters Built for Reports', 'FILTERS_BUILT_FOR_REPORTS');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Default Alarm Rule Additions', 'DEFAULT_ALARM_RULE_ADDITIONS');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Custom Alarm Rule Additions', 'CUSTOM_ALARM_RULE_ADDITIONS');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Forward Alarms to USM Central', 'FORWARD_ALARMS_TO_USM_CENTRAL');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Forward Alarms to D3/SOC Live', 'FORWARD_ALARMS_TO_D3_SOC_LIVE');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description, inventory_flag, progress_percentage, status)
VALUES
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0, 'Order Request Received', 0, 5, 'Pending Assignment'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 0, 0, 0, 0, 'Order Received', 0, 5, 'Pending Assignment'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_REQUESTED_INSTALL'), 1, 30, 1, 0, 0, 0, 0, 'Customer Requested Install', 0, 5, 'Pending Assignment'),

((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'TECH_DATA_GATHERING_FORM_SENT'), 1, 40, 1, 0, 0, 0, 0, 'Technical Data Gathering Form Sent', 0, 5, 'Technical Data Gathering'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'TECH_DATA_GATHERING_MEETING_SCHEDULED'), 1, 50, 1, 0, 0, 0, 0, 'Technical Data Gathering Meeting Scheduled', 0, 5, 'Technical Data Gathering'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'TECH_DATA_GATHERING_MEETING_COMPLETED'), 1, 60, 1, 0, 0, 0, 0, 'Technical Data Gathering Meeting Completed', 0, 10, 'TDG Complete'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'EMAIL_USM_TEMPLATE_REQ'), 1, 70, 1, 0, 0, 0, 0, 'Email USM Anywhere Template Requirements', 0, 0, NULL),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'INVENTORY_ASSIGNMENT_VERIFIED'), 1, 80, 1, 0, 0, 0, 0, 'Inventory Assignment Verified', 0, 15, 'Inventory Assignment'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'NEW_USM_SERVER_BUILD'), 1, 90, 1, 0, 0, 0, 0, 'New USM Anywhere Server Build', 0, 25, 'Server Build'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'IMPLEMENTATION_QA'), 1, 100, 1, 0, 0, 0, 0, 'Implementation QA', 0, 30, 'Implementation QA'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'VERIFY_ASSETS_SIEM_DB'), 1, 110, 1, 0, 0, 0, 0, 'Verify Assets in SIEM DB', 0, 35, 'Asset & Logging Verification'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'VERIFY_LOGGING_DATA_SOURCES'), 1, 120, 1, 0, 0, 0, 0, 'Verify Logging Data Sources', 0, 40, 'Asset & Logging Verification'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'SCHEDULE_VULNERABILITY_SCANS'), 1, 130, 1, 0, 0, 0, 0, 'Schedule Vulnerability Scans', 0, 0, NULL),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'BULK_ALARM_TUNING_PHASE'), 1, 140, 1, 0, 0, 0, 0, 'Schedule Vulnerability Scans', 0, 50, 'Alarm Tuning'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'SIEM_EVENT_FILTERING'), 1, 150, 1, 0, 0, 0, 0, 'SIEM Event Filtering', 0, 55, 'SIEM Event Filtering'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'FILTERS_BUILT_FOR_REPORTS'), 1, 160, 1, 0, 0, 0, 0, 'Filters Built for Reports', 0, 65, 'Filter/report Building'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'DEFAULT_ALARM_RULE_ADDITIONS'), 1, 170, 1, 0, 0, 0, 0, 'Default Alarm Rule Additions', 0, 70, 'Alarm Rule Building'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOM_ALARM_RULE_ADDITIONS'), 1, 180, 1, 0, 0, 0, 0, 'Custom Alarm Rule Additions', 0, 75, 'Alarm Rule Building'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'FORWARD_ALARMS_TO_USM_CENTRAL'), 1, 190, 1, 0, 0, 0, 0, 'Forward Alarms to USM Central', 0, 80, 'Alarm Rule Building'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'FORWARD_ALARMS_TO_D3_SOC_LIVE'), 1, 200, 1, 0, 0, 0, 0, 'Forward Alarms to D3/SOC Live', 0, 85, 'Alarm Rule Building'),

((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 3010, 1, 0, 0, 0, 0, 'Order is on hold. Jeop has been opened and noted', 0, 0, 'On Hold'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 3020, 1, 0, 0, 0, 0, 'All aspects of the order have successfully been completed', 1, 100, 'Service Complete'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 3030, 1, 0, 0, 0, 0, 'Order is on hold. Jeop has been opened and noted. Order has a complete date and closed out', 0, 100, 'Service Cancelled'),
((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'THREATMDR_SERVICE_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 3040, 1, 0, 0, 0, 0, 'Date order has been assigned to another users worklist', 0, 0, 'Change In Assignment');
