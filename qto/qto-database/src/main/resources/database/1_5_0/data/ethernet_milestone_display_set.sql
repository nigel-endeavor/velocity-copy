INSERT INTO milestone (milestone_name, milestone_code)
VALUES ('Cross Connect Complete', 'CROSS_CONNECT_COMPLETE');

INSERT INTO milestone_display_set (display_group, display_set_label) VALUES ('ETHERNET_SERVICE_MILESTONE', 'Ethernet Milestones');
SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'ETHERNET_SERVICE_MILESTONE');
DELETE FROM milestone_display_set_include WHERE milestone_display_set_id = @displaySetId;
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0, 'Order created in the platform'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 1, 0, 0, 0, 'Order received and working'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_REQUESTED_INSTALL'), 1, 30, 1, 1, 0, 0, 0, 'Customer requested install date'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_SURVEY_SUBMIT'), 1, 1010, 1, 1, 0, 0, 0, 'The date the site survey was requested to the carrier'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_SURVEY_DUE'), 1, 1020, 1, 1, 0, 0, 0, 'Site Survey due date'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_SURVEY_COMPLETE'), 1, 1030, 1, 1, 0, 0, 0, 'Date Site Survey results received from carrier'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_START'), 1, 1040, 1, 1, 0, 0, 0, 'Network Provider Construction Start date provided'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_COMPLETE'), 1, 1050, 1, 1, 0, 0, 0, 'Network Provider Construction Complete date provided'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CARRIER_ORDER_SUBMITTED'), 1, 1060, 1, 1, 0, 0, 0, 'Date order submitted to the Carrier'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_ACCESS_CONFIRMED'), 1, 1070, 1, 1, 0, 0, 0, 'Date Site access has been confirmed'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACCESS_CIRCUIT_FOC'), 1, 1080, 1, 1, 0, 0, 0, 'The access providers Firm Order Commitment date'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_FOC'), 1, 1090, 1, 1, 0, 0, 0, 'Network Firm Order Commitment date provided by the carrier'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_ORDERED'), 1, 1200, 1, 1, 0, 0, 0, 'Date Equipment Order was submitted to the equipment vendor'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_CONFIGURED'), 1, 1210, 1, 1, 0, 0, 0, 'Confirmed date Equipment Configuration has completed'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_RECEIVED'), 1, 1220, 1, 1, 0, 0, 0, 'Date the Equipment order was delivered to it''s destination'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'DATA_PROVISIONING_COMPLETE'), 1, 1230, 1, 1, 0, 0, 0, 'Confirmation by Carrier that the Provisioning is completed'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CROSS_CONNECT_COMPLETE'), 1, 1240, 1, 1, 0, 0, 0, 'Confirmed Date the Cross Connect was Completed'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'PRE_ACTIVATION_CALL_COMPLETE'), 1, 2000, 1, 1, 0, 0, 0, 'Pre activation tasks successfully completed with tech and/or LCON'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_REQUESTED'), 1, 2010, 1, 1, 0, 0, 0, 'Date the Activation was requested to be scheduled'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_SCHEDULED'), 1, 2020, 1, 1, 0, 0, 0, 'Confirmated Activation scheduled on requested date'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_COMPLETE'), 1, 2030, 1, 1, 0, 0, 0, 'Date all activation activities were successfully completed'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_COMPLETION_NOTIFICATION_SENT'), 1, 2040, 1, 1, 0, 0, 0, 'Emailed completion notification sent to LCON'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 3010, 1, 1, 0, 0, 0, 'Order is on hold, Jeop has been noted and opened'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 3020, 1, 1, 0, 0, 0, 'All aspects of the order have been successfully completed'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 3030, 1, 1, 0, 0, 0, 'Order was cancelled with the provider prior to service being delivered'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 3040, 1, 1, 0, 0, 0, 'Date order has been reassigned to another user worklist'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'QA_CHECK_OPEN'), 1, 4010, 1, 1, 0, 0, 0, 'The date the QA Engineer begins reviewing the service items'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'FIRST_VENDOR_INVOICE'), 1, 4020, 1, 1, 0, 0, 0, 'Expected or actual first billing cycle date received from the vendor'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_ORDER_GROUP'), 1, 4030, 1, 1, 0, 0, 0, 'Date the QA engineer sent requests back to the provisioning group for input'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_SALES'), 1, 4040, 1, 1, 0, 0, 0, 'Date the QA engineer sent requests back to the sales group for input'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), 1, 4040, 1, 1, 0, 0, 0, 'Bill review successfully validated and completed');