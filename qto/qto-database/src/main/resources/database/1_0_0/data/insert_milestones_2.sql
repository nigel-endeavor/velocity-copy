INSERT INTO milestone (milestone_name, milestone_code)
VALUES ('Customer Requested Install', 'CUSTOMER_REQUESTED_INSTALL'),
       ('Site Survery Submit', 'SITE_SURVEY_SUBMIT'),
       ('Site Survey Complete', 'SITE_SURVEY_COMPLETE'),
       ('Pre-Activation Call Complete', 'PRE_ACTIVATION_CALL_COMPLETE');


SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'BROADBAND_SERVICE_MILESTONE');
DELETE FROM milestone_display_set_include WHERE milestone_display_set_id = @displaySetId;
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_REQUESTED_INSTALL'), 1, 30, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_SURVEY_SUBMIT'), 1, 40, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_SURVEY_DUE'), 1, 50, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_SURVEY_COMPLETE'), 1, 60, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_START'), 1, 70, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_COMPLETE'), 1, 80, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CARRIER_ORDER_SUBMITTED'), 1, 90, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_FOC'), 1, 100, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'DATA_PROVISIONING_COMPLETE'), 1, 110, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'PRE_ACTIVATION_CALL_COMPLETE'), 1, 120, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_REQUESTED'), 1, 130, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_SCHEDULED'), 1, 140, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_COMPLETE'), 1, 150, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_COMPLETION_NOTIFICATION_SENT'), 1, 160, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_BILL_STOP'), 1, 170, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), 1, 180, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 1010, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 1020, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 1030, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 1040, 1, 0, 0, 0, 0);


SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'DIA_SERVICE_MILESTONE');
DELETE FROM milestone_display_set_include WHERE milestone_display_set_id = @displaySetId;
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_REQUESTED_INSTALL'), 1, 30, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_SURVEY_SUBMIT'), 1, 40, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_SURVEY_DUE'), 1, 50, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_SURVEY_COMPLETE'), 1, 60, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_START'), 1, 70, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_COMPLETE'), 1, 80, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CARRIER_ORDER_SUBMITTED'), 1, 90, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACCESS_CIRCUIT_FOC'), 1, 100, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_FOC'), 1, 110, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_ORDERED'), 1, 120, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_RECEIVED'), 1, 130, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_CONFIGURED'), 1, 140, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'DATA_PROVISIONING_COMPLETE'), 1, 150, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'PRE_ACTIVATION_CALL_COMPLETE'), 1, 160, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_REQUESTED'), 1, 170, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_SCHEDULED'), 1, 180, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_COMPLETE'), 1, 190, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_COMPLETION_NOTIFICATION_SENT'), 1, 200, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_BILL_STOP'), 1, 210, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), 1, 220, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 1010, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 1020, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 1030, 1, 0, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 1040, 1, 0, 0, 0, 0);
