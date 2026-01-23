INSERT INTO milestone_display_set (display_group, display_set_label) VALUES ('ORDER_MILESTONE', 'Order Milestones');
INSERT INTO milestone_display_set (display_group, display_set_label) VALUES ('LOCATION_MILESTONE', 'Location Milestones');
INSERT INTO milestone_display_set (display_group, display_set_label) VALUES ('BROADBAND_SERVICE_MILESTONE', 'Broadband Milestones');
INSERT INTO milestone_display_set (display_group, display_set_label) VALUES ('DIA_SERVICE_MILESTONE', 'DIA Milestones');

INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Created', 'CREATED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Received', 'RECEIVED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('On Hold', 'ON_HOLD');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Change In Assignment', 'CHANGE_IN_ASSIGNMENT');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('In Progress', 'IN_PROGRESS');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Complete', 'COMPLETE');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Cancelled', 'CANCELLED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Engineer Assigned', 'ENGINEER_ASSIGNED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Initial Contact with Customer', 'INITIAL_CONTACT_WITH_CUSTOMER');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('TDG Interview Scheduled', 'TDG_INTERVIEW_SCHEDULED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Customer Contact Complete', 'CUSTOMER_CONTACT_COMPLETE');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('TDG In Progress', 'TDG_IN_PROGRESS');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Design Sent To Customer', 'DESIGN_SENT_TO_CUSTOMER');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Design Approved By Customer', 'DESIGN_APPROVED_BY_CUSTOMER');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('TDG Complete', 'TDG_COMPLETE');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Provisioning Start', 'PROVISIONING_START');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Site Survey Due', 'SITE_SURVEY_DUE');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Customer Desired Due', 'CUSTOMER_DESIRED_DUE');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Carrier Order Submitted', 'CARRIER_ORDER_SUBMITTED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Network Provider Construction Start', 'NETWORK_PROVIDER_CONSTRUCTION_START');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Network Provider Construction Complete', 'NETWORK_PROVIDER_CONSTRUCTION_COMPLETE');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Network Provider FOC', 'NETWORK_PROVIDER_FOC');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Data Provisioning Complete', 'DATA_PROVISIONING_COMPLETE');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Activation Requested', 'ACTIVATION_REQUESTED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Activation Scheduled', 'ACTIVATION_SCHEDULED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Activation Complete', 'ACTIVATION_COMPLETE');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Customer Completion Notification Sent', 'CUSTOMER_COMPLETION_NOTIFICATION_SENT');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Customer Bill Stop', 'CUSTOMER_BILL_STOP');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Billing Review Complete', 'BILLING_REVIEW_COMPLETE');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Access Circuit FOC', 'ACCESS_CIRCUIT_FOC');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Equipment Ordered', 'EQUIPMENT_ORDERED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Equipment Received', 'EQUIPMENT_RECEIVED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Equipment Configured', 'EQUIPMENT_CONFIGURED');




SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 30, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 40, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ENGINEER_ASSIGNED'), 1, 50, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'IN_PROGRESS'), 1, 60, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 70, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 80, 1, 0, 0, 0, 0);


SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'LOCATION_MILESTONE');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 30, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 40, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ENGINEER_ASSIGNED'), 1, 50, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'INITIAL_CONTACT_WITH_CUSTOMER'), 1, 60, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'TDG_INTERVIEW_SCHEDULED'), 1, 70, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_CONTACT_COMPLETE'), 1, 80, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'TDG_IN_PROGRESS'), 1, 90, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'DESIGN_SENT_TO_CUSTOMER'), 1, 100, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'DESIGN_APPROVED_BY_CUSTOMER'), 1, 110, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'TDG_COMPLETE'), 1, 120, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'PROVISIONING_START'), 1, 130, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 140, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 150, 1, 0, 0, 0, 0);






SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'BROADBAND_SERVICE_MILESTONE');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 30, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 40, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_SURVEY_DUE'), 1, 50, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_DESIRED_DUE'), 1, 60, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CARRIER_ORDER_SUBMITTED'), 1,780, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_START'), 1, 80, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_COMPLETE'), 1, 90, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_FOC'), 1, 100, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'DATA_PROVISIONING_COMPLETE'), 1, 110, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_REQUESTED'), 1, 120, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_SCHEDULED'), 1, 130, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_COMPLETE'), 1, 140, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_COMPLETION_NOTIFICATION_SENT'), 1, 150, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_BILL_STOP'), 1, 160, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), 1, 170, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 180, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 190, 1, 0, 0, 0, 0);





SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'DIA_SERVICE_MILESTONE');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 30, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 40, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_SURVEY_DUE'), 1, 50, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_DESIRED_DUE'), 1, 60, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CARRIER_ORDER_SUBMITTED'), 1, 70, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_START'), 1, 80, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_COMPLETE'), 1, 90, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_FOC'), 1, 100, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACCESS_CIRCUIT_FOC'), 1, 120, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_ORDERED'), 1, 130, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_RECEIVED'), 1, 140, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_CONFIGURED'), 1, 150, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'DATA_PROVISIONING_COMPLETE'), 1, 160, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_REQUESTED'), 1, 170, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_SCHEDULED'), 1, 180, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_COMPLETE'), 1, 190, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_COMPLETION_NOTIFICATION_SENT'), 1, 200, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_BILL_STOP'), 1, 210, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), 1, 220, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 230, 1, 0, 0, 0, 0);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 240, 1, 0, 0, 0, 0);
