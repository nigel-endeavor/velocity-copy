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
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), true, 10, true, false, true, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), true, 20, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), true, 30, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), true, 40, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ENGINEER_ASSIGNED'), true, 50, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'IN_PROGRESS'), true, 60, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), true, 70, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), true, 80, true, false, false, false, false);



INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), true, 10, true, false, true, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), true, 20, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), true, 30, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), true, 40, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ENGINEER_ASSIGNED'), true, 50, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'INITIAL_CONTACT_WITH_CUSTOMER'), true, 60, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'TDG_INTERVIEW_SCHEDULED'), true, 70, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_CONTACT_COMPLETE'), true, 80, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'TDG_IN_PROGRESS'), true, 90, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'DESIGN_SENT_TO_CUSTOMER'), true, 100, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'DESIGN_APPROVED_BY_CUSTOMER'), true, 110, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'TDG_COMPLETE'), true, 120, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'PROVISIONING_START'), true, 130, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), true, 140, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), true, 150, true, false, false, false, false);







INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), true, 10, true, false, true, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), true, 20, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), true, 30, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), true, 40, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_SURVEY_DUE'), true, 50, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_DESIRED_DUE'), true, 60, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CARRIER_ORDER_SUBMITTED'), true,780, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_START'), true, 80, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_COMPLETE'), true, 90, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_FOC'), true, 100, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'DATA_PROVISIONING_COMPLETE'), true, 110, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_REQUESTED'), true, 120, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_SCHEDULED'), true, 130, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_COMPLETE'), true, 140, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_COMPLETION_NOTIFICATION_SENT'), true, 150, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_BILL_STOP'), true, 160, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), true, 170, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), true, 180, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), true, 190, true, false, false, false, false);






INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), true, 10, true, false, true, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), true, 20, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), true, 30, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), true, 40, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_SURVEY_DUE'), true, 50, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_DESIRED_DUE'), true, 60, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CARRIER_ORDER_SUBMITTED'), true, 70, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_START'), true, 80, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_COMPLETE'), true, 90, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_FOC'), true, 100, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACCESS_CIRCUIT_FOC'), true, 120, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_ORDERED'), true, 130, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_RECEIVED'), true, 140, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_CONFIGURED'), true, 150, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'DATA_PROVISIONING_COMPLETE'), true, 160, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_REQUESTED'), true, 170, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_SCHEDULED'), true, 180, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_COMPLETE'), true, 190, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_COMPLETION_NOTIFICATION_SENT'), true, 200, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_BILL_STOP'), true, 210, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), true, 220, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), true, 230, true, false, false, false, false);
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ORDER_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), true, 240, true, false, false, false, false);
