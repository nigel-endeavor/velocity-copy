INSERT INTO milestone_display_set (display_group, display_set_label, display_type)
VALUES ('RANSOMMDR_SERVICE_MILESTONE', 'ransomMDR Milestones', 'ransomMDR');

INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Host List Provided by Client', 'HOST_LIST_PROVIDED_BY_CLIENT');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Halcyon Package Given to Client', 'HALCYON_PACKAGE_GIVEN_TO_CLIENT');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Halcyon Deployed to Hosts', 'HALCYON_DEPLOYED_TO_HOSTS');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Devops Notified of Halcyon Addition', 'DEVOPS_NOTIFIED_OF_HALCYON_ADDITION');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Halcyon API Token Added to D3', 'HALCYON_API_TOKEN_ADDED_TO_D3');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('D3 Connection Verified', 'D3_CONNECTION_VERIFIED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('End Learning Mode', 'END_LEARNING_MODE');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Test Email Sent to Client', 'TEST_EMAIL_SENT_TO_CLIENT');

SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'RANSOMMDR_SERVICE_MILESTONE');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description, inventory_flag, progress_percentage, status)
VALUES
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0, 'Order Request Received', 0, 5, 'Pending Assignment'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 0, 0, 0, 0, 'Order Received', 0, 5, 'Pending Assignment'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_REQUESTED_INSTALL'), 1, 30, 1, 0, 0, 0, 0, 'Customer Requested Install', 0, 5, 'Pending Assignment'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ENGINEER_ASSIGNED'), 1, 40, 1, 0, 1, 0, 0, 'Engineer Assigned', 0, 10, 'Engineer Assigned'),

(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'HOST_LIST_PROVIDED_BY_CLIENT'), 1, 2500, 1, 1, 0, 0, 0, 'Host List Provided by Client', 0, 15, 'Front End Client Engagement'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'HALCYON_PACKAGE_GIVEN_TO_CLIENT'), 1, 2510, 1, 1, 0, 0, 0, 'Halcyon Package Given to Client', 0, 20, 'Front End Client Engagement'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'HALCYON_DEPLOYED_TO_HOSTS'), 1, 2520, 1, 1, 0, 0, 0, 'Halcyon Deployed to Hosts', 0, 30, 'Front End Client Engagement'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'DEVOPS_NOTIFIED_OF_HALCYON_ADDITION'), 1, 2530, 1, 1, 0, 0, 0, 'Devops Notified of Halcyon Addition', 0, 40,'Devops Engagement'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'HALCYON_API_TOKEN_ADDED_TO_D3'), 1, 2540, 1, 1, 0, 0, 0, 'Halcyon API Token Added to D3', 0, 50, 'D3 Implementation'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'D3_CONNECTION_VERIFIED'), 1, 2550, 1, 1, 0, 0, 0, 'D3 Connection Verified', 0, 60, 'D3 Implementation'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'END_LEARNING_MODE'), 1, 2560, 1, 1, 0, 0, 0, 'End Learning Mode', 0, 75, 'End Learning Mode'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'TEST_EMAIL_SENT_TO_CLIENT'), 1, 2570, 1, 1, 0, 0, 0, 'Test Email Sent to Client', 0, 90, 'Final Test Email Process'),

(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 3010, 1, 1, 0, 0, 0, 'Order is on hold. Jeop has been opened and noted', 0, 0, 'On Hold'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 3020, 1, 1, 0, 0, 0, 'All aspects of the order have successfully been completed', 1, 100, 'Service Complete'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 3030, 1, 1, 0, 0, 0, 'Order is on hold. Jeop has been opened and noted. Order has a complete date and closed out', 0, 100, 'Service Cancelled'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 3040, 1, 1, 0, 0, 0, 'Date order has been assigned to another users worklist', 0, 0, 'Change In Assignment');
