INSERT INTO milestone_display_set (display_group, display_set_label) VALUES ('EXISTING_MAC_MILESTONE', 'Existing MAC Milestones');
INSERT INTO milestone_display_set (display_group, display_set_label) VALUES ('DISCONNECT_MILESTONE', 'Disconnect Milestones');

INSERT INTO milestone (milestone_name, milestone_code) VALUES ('QA Check Open', 'QA_CHECK_OPEN');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('1st Vendor Invoice date', 'FIRST_VENDOR_INVOICE');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Returned to Order Group', 'RETURNED_TO_ORDER_GROUP');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Returned to Sales', 'RETURNED_TO_SALES');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Carrier Disconnect Complete', 'CARRIER_DISCONNECT_COMPLETE');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Customer Requested Disconnect', 'CUSTOMER_REQUESTED_DISCONNECT');

SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0, 'Order created in the platform');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 1, 0, 0, 0, 'Order received and working');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_DESIRED_DUE'), 1, 30, 1, 1, 0, 0, 0, 'Customer requested install date');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CARRIER_ORDER_SUBMITTED'), 1,1000, 1, 1, 0, 0, 0, 'Date order submitted to the Carrier');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_ACCESS_CONFIRMED'), 1, 1010, 1, 1, 0, 0, 0, 'Date Site access has been confirmed');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_FOC'), 1, 1020, 1, 1, 0, 0, 0, 'Network Firm Order Commitment date provided by the carrier');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'DATA_PROVISIONING_COMPLETE'), 1, 1030, 1, 1, 0, 0, 0, 'Confirmation by Carrier that the Provisioning is completed');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'PRE_ACTIVATION_CALL_COMPLETE'), 1, 2000, 1, 1, 0, 0, 0, 'Pre activation tasks successfully completed with tech and/or LCON');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_REQUESTED'), 1, 2010, 1, 1, 0, 0, 0, 'Date the Activation was requested to be scheduled');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_SCHEDULED'), 1, 2020, 1, 1, 0, 0, 0, 'Confirmated Activation scheduled on requested date');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_COMPLETE'), 1, 2030, 1, 1, 1, 0, 0, 'Date all activation activites were successfully completed');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 3000, 1, 1, 0, 0, 0, 'Order is on hold, Jeop has been noted and opened');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 3010, 1, 1, 0, 0, 0, 'All aspects of the order have been successfully completed');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 3020, 1, 1, 0, 0, 0, 'Order has been cancelled');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 3030, 1, 1, 0, 0, 0, 'Date order has been reassigned to another user worklist ');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'QA_CHECK_OPEN'), 1, 4000, 1, 1, 0, 0, 0, 'The date the QA Engineer begins reviewing the service items');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'FIRST_VENDOR_INVOICE'), 1, 4010, 1, 1, 0, 0, 0, 'Expected or actual first billing cycle date received from the vendor');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_ORDER_GROUP'), 1, 4020, 1, 1, 0, 0, 0, '	Date the QA engineer sent requests back to the provisioning group for input');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_SALES'), 1, 4030, 1, 1, 0, 0, 0, 'Date the QA engineer sent requests back to the sales group for input');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), 1, 4040, 1, 1, 0, 0, 0, 'Bill review successfully validated and completed');


SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'DISCONNECT_MILESTONE');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0, 'Order created in the platform');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 1, 0, 0, 0, 'Order received and working');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_REQUESTED_DISCONNECT'), 1, 30, 1, 1, 0, 0, 0, 'Customer requested disconnect date');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CARRIER_ORDER_SUBMITTED'), 1,1000, 1, 1, 0, 0, 0, 'Date disconnect order submitted to the Carrier');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_FOC'), 1, 1010, 1, 1, 0, 0, 0, 'The access providers Firm Order Commitment date');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CARRIER_DISCONNECT_COMPLETE'), 1, 1020, 1, 1, 0, 0, 0, 'Confirmation by Carrier that the service has been disconnected');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 3000, 1, 1, 0, 0, 0, 'Order is on hold, Jeop has been noted and opened');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 3010, 1, 1, 0, 0, 0, 'All aspects of the order have been successfully completed');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 3020, 1, 1, 0, 0, 0, 'Disconnect order has been cancelled');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 3030, 1, 1, 0, 0, 0, 'Date order has been reassigned to another user worklist');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_BILL_STOP'), 1, 4010, 1, 1, 0, 0, 0, 'Service stop bill date confirmed');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), 1, 4020, 1, 1, 0, 0, 0, 'Bill review successfully validated and completed');



SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'DIA_SERVICE_MILESTONE');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'QA_CHECK_OPEN'), 1, 4000, 1, 1, 0, 0, 0, 'The date the QA Engineer begins reviewing the service items');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'FIRST_VENDOR_INVOICE'), 1, 4010, 1, 1, 0, 0, 0, 'Expected or actual first billing cycle date received from the vendor');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_ORDER_GROUP'), 1, 4020, 1, 1, 0, 0, 0, '	Date the QA engineer sent requests back to the provisioning group for input');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_SALES'), 1, 4030, 1, 1, 0, 0, 0, 'Date the QA engineer sent requests back to the sales group for input');
update milestone_display_set_include set milestone_sequence = 4040 where milestone_id = (select milestone_id from milestone where milestone_code = 'BILLING_REVIEW_COMPLETE') and milestone_display_set_id = @displaySetId;
Delete from milestone_display_set_include where milestone_id = (select milestone_id from milestone where milestone_code = 'CUSTOMER_BILL_STOP') and milestone_display_set_id = @displaySetId;


SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'BROADBAND_SERVICE_MILESTONE');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'QA_CHECK_OPEN'), 1, 4000, 1, 1, 0, 0, 0, 'The date the QA Engineer begins reviewing the service items');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'FIRST_VENDOR_INVOICE'), 1, 4010, 1, 1, 0, 0, 0, 'Expected or actual first billing cycle date received from the vendor');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_ORDER_GROUP'), 1, 4020, 1, 1, 0, 0, 0, '	Date the QA engineer sent requests back to the provisioning group for input');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_SALES'), 1, 4030, 1, 1, 0, 0, 0, 'Date the QA engineer sent requests back to the sales group for input');
update milestone_display_set_include set milestone_sequence = 4040 where milestone_id = (select milestone_id from milestone where milestone_code = 'BILLING_REVIEW_COMPLETE') and milestone_display_set_id = @displaySetId;
Delete from milestone_display_set_include where milestone_id = (select milestone_id from milestone where milestone_code = 'CUSTOMER_BILL_STOP') and milestone_display_set_id = @displaySetId;

SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = '4G5G_SERVICE_MILESTONE');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'QA_CHECK_OPEN'), 1, 4000, 1, 1, 0, 0, 0, 'The date the QA Engineer begins reviewing the service items');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'FIRST_VENDOR_INVOICE'), 1, 4010, 1, 1, 0, 0, 0, 'Expected or actual first billing cycle date received from the vendor');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_ORDER_GROUP'), 1, 4020, 1, 1, 0, 0, 0, '	Date the QA engineer sent requests back to the provisioning group for input');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_SALES'), 1, 4030, 1, 1, 0, 0, 0, 'Date the QA engineer sent requests back to the sales group for input');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), 1, 4020, 1, 1, 0, 0, 0, 'Bill review successfully validated and completed');


SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'UCAAS_SERVICE_MILESTONE');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_DESIRED_DUE'), 1, 30, 1, 1, 0, 0, 0, 'Customer requested install date');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'QA_CHECK_OPEN'), 1, 4000, 1, 1, 0, 0, 0, 'The date the QA Engineer begins reviewing the service items');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'FIRST_VENDOR_INVOICE'), 1, 4010, 1, 1, 0, 0, 0, 'Expected or actual first billing cycle date received from the vendor');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_ORDER_GROUP'), 1, 4020, 1, 1, 0, 0, 0, '	Date the QA engineer sent requests back to the provisioning group for input');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_SALES'), 1, 4030, 1, 1, 0, 0, 0, 'Date the QA engineer sent requests back to the sales group for input');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), 1, 4020, 1, 1, 0, 0, 0, 'Bill review successfully validated and completed');
Delete from milestone_display_set_include where milestone_id = (select milestone_id from milestone where milestone_code = 'ENGINEER_ASSIGNED') and milestone_display_set_id = @displaySetId;
