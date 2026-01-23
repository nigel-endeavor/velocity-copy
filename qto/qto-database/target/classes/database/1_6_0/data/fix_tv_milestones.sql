SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'TELEVISION_SERVICE_MILESTONE');
DELETE FROM milestone_display_set_include WHERE milestone_display_set_id = @displaySetId;

INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description, inventory_flag, progress_percentage, status)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0, 'Order created in the platform', 0,5,'Pending Assignment'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_SURVEY_SUBMIT'), 1, 1010, 1, 0, 0, 0, 0, 'The date the site survey was requested to the carrier', 0,10,'Site Survey In Progress' ),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_SURVEY_COMPLETE'), 1, 1030, 1, 0, 0, 0, 0, 'Date Site Survey results received from carrier', 0, 15, 'Site Survey Complete'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_START'), 1, 1040, 1, 0, 0, 0, 0, 'Network Provider Construction Start date provided', 0, 20, 'Network Provider Construction'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_COMPLETE'), 1, 1050, 1, 0, 0, 0, 0, 'Network Provider Construction Complete date provided', 0, 25, 'Network Provider Construction Complete'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CARRIER_ORDER_SUBMITTED'), 1, 1060, 1, 0, 0, 0, 0, 'Date order submitted to the Carrier', 0, 25, 'Provisioning'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_FOC'), 1, 1080, 1, 0, 0, 0, 0, 'Network Firm Order Commitment date provided by the carrier', 0, 40, 'Provisioning'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'DATA_PROVISIONING_COMPLETE'), 1, 1120, 1, 0, 0, 0, 0, 'Confirmation by Carrier that the Provisioning is completed', 1, 60, 'Circuit Complete' ),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_REQUESTED'), 1, 2020, 1, 0, 0, 0, 0, 'Date activation was requested to be scheduled', 0, 70, 'Activation Requested'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_SCHEDULED'), 1, 2030, 1, 0, 0, 0, 0, 'Confirmation activation has been scheduled on requested date', 0, 80, 'Activation Scheduled'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_COMPLETE'), 1, 2040, 1, 0, 0, 0, 0, 'Date all activation activities successfully completed', 0, 100, 'Service Complete'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 3020, 1, 0, 0, 0, 0, 'All aspects of the order have successfully been completed', 1, 100, 'Service Complete'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 3030, 1, 0, 0, 0, 0, 'Order is on hold. Jeop has been opened and noted. Order has a complete date and closed out', 0, 100, 'Service Cancelled'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 3040, 1, 0, 0, 0, 0, 'Date order has been assigned to another users worklist', 0, 100, 'Change In Assignment'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ENGINEER_ASSIGNED'), 1, 25, 1, 0, 1, 0, 0, '', 0, 100, 'Engineer Assigned');

INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description, inventory_flag, progress_percentage)

VALUES   (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 0, 0, 0, 0, 'Order received and working', 0,5),
         (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_REQUESTED_INSTALL'), 1, 30, 1, 0, 0, 0, 0, 'Customer requested install date', 0,5),
         (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_COMPLETION_NOTIFICATION_SENT'), 1, 2050, 1, 0, 0, 0, 0, 'Email completion notification sent to LCON', 0, 100);

INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description, status)
VALUES  (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_ORDERED'), 1, 1090, 1, 1, 0, 0, 0, 'Date Equipment Order was submitted to the equipment vendor', 'Equipment Ordered'),
        (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_CONFIGURED'), 1, 1100, 1, 1, 0, 0, 0, 'Confirmed date Equipment Configuration has completed', 'Equipment Configured' ),
        (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_RECEIVED'), 1, 1110, 1, 1, 0, 0, 0, 'Date the Equipment order was delivered to it''s destination', 'Equipment Received' ),
        (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 3010, 1, 0, 0, 0, 0, 'Order is on hold.  Jeop has been opened and noted', 'On Hold');

INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_SURVEY_DUE'), 1, 1020, 1, 0, 0, 0, 0, 'Site Survey due date') ,
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_ACCESS_CONFIRMED'), 1, 1070, 1, 1, 0, 0, 0, 'Date Site access has been confirmed'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'PRE_ACTIVATION_CALL_COMPLETE'), 1, 2010, 1, 0, 0, 0, 0, 'Pre-activation tasks completed with tech and/or LCON'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'QA_CHECK_OPEN'), 1, 4000, 1, 1, 0, 0, 0, 'The date the QA Engineer begins reviewing the service items'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'FIRST_VENDOR_INVOICE'), 1, 4010, 1, 1, 0, 0, 0, 'Expected or actual first billing cycle date received from the vendor'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_ORDER_GROUP'), 1, 4020, 1, 1, 0, 0, 0, '	Date the QA engineer sent requests back to the provisioning group for input'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_SALES'), 1, 4030, 1, 1, 0, 0, 0, 'Date the QA engineer sent requests back to the sales group for input'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), 1, 4040, 1, 1, 0, 0, 0, 'Bill review successfully validated and completed');
