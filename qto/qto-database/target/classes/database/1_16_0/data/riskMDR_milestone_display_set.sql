INSERT INTO milestone_display_set (display_group, display_set_label, display_type)
VALUES ('RISKMDR_SERVICE_MILESTONE', 'riskMDR Milestones', 'riskMDR');

INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Technical Data Gathering Meeting Scheduled', 'TECHNICAL_DATA_GATHERING_MEETING_SCHEDULED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Technical Data Gathering Meeting Completed', 'TECHNICAL_DATA_GATHERING_MEETING_COMPLETED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Deploy Consulting Tenant', 'DEPLOY_CONSULTING_TENANT');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Deploy Vulnerability Scans', 'DEPLOY_VULNERABILITY_SCANS');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Provided Customer with Report', 'PROVIDED_CUSTOMER_WITH_REPORT');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Discuss Future Cyrisma Management', 'DISCUSS_FUTURE_CYRISMA_MANAGEMENT');


SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'RISKMDR_SERVICE_MILESTONE');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description, inventory_flag, progress_percentage, status)
VALUES
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0, 'Order Request Received', 0, 5, 'Pending Assignment'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 0, 0, 0, 0, 'Order Received', 0, 5, 'Pending Assignment'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_REQUESTED_INSTALL'), 1, 30, 1, 0, 0, 0, 0, 'Customer Requested Install', 0, 5, 'Pending Assignment'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ENGINEER_ASSIGNED'), 1, 40, 1, 0, 1, 0, 0, 'Engineer Assigned', 0, 10, 'Engineer Assigned'),

(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'TECHNICAL_DATA_GATHERING_MEETING_SCHEDULED'), 1, 2500, 1, 1, 0, 0, 0, 'Technical Data Gathering Meeting Scheduled', 0, 20, 'Technical Data Gathering'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'TECHNICAL_DATA_GATHERING_MEETING_COMPLETED'), 1, 2510, 1, 1, 0, 0, 0, 'Technical Data Gathering Meeting Completed', 0, 40, 'Technical Data Gathering Complete'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'DEPLOY_CONSULTING_TENANT'), 1, 2520, 1, 1, 0, 0, 0, 'Deploy Consulting Tenant', 0, 50, 'Consulting & Vulnerability Scan'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'DEPLOY_VULNERABILITY_SCANS'), 1, 2530, 1, 1, 0, 0, 0, 'Deploy Vulnerability Scans', 0, 60,'Consulting & Vulnerability Scan'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'PROVIDED_CUSTOMER_WITH_REPORT'), 1, 2540, 1, 1, 0, 0, 0, 'Provided Customer with Report', 0, 75, 'Results Presented'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'DISCUSS_FUTURE_CYRISMA_MANAGEMENT'), 1, 2550, 1, 1, 0, 0, 0, 'Discuss Future Cyrisma Management', 0, 90, 'Future Engagement Conversation'),

(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 3010, 1, 1, 0, 0, 0, 'Order is on hold. Jeop has been opened and noted', 0, 0, 'On Hold'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 3020, 1, 1, 0, 0, 0, 'All aspects of the order have successfully been completed', 1, 100, 'Service Complete'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 3030, 1, 1, 0, 0, 0, 'Order is on hold. Jeop has been opened and noted. Order has a complete date and closed out', 0, 100, 'Service Cancelled'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 3040, 1, 1, 0, 0, 0, 'Date order has been assigned to another users worklist', 0, 0, 'Change In Assignment');
