INSERT INTO milestone_display_set (display_group, display_set_label) VALUES ('EXISTING_MAC_MILESTONE', 'Existing MAC Milestones');
INSERT INTO milestone_display_set (display_group, display_set_label) VALUES ('DISCONNECT_MILESTONE', 'Disconnect Milestones');

INSERT INTO milestone (milestone_name, milestone_code) VALUES ('QA Check Open', 'QA_CHECK_OPEN');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('1st Vendor Invoice date', 'FIRST_VENDOR_INVOICE');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Returned to Order Group', 'RETURNED_TO_ORDER_GROUP');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Returned to Sales', 'RETURNED_TO_SALES');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Carrier Disconnect Complete', 'CARRIER_DISCONNECT_COMPLETE');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Customer Requested Disconnect', 'CUSTOMER_REQUESTED_DISCONNECT');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), true, 10, true, false, true, false, false, 'Order created in the platform');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), true, 20, true, true, false, false, false, 'Order received and working');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_DESIRED_DUE'), true, 30, true, true, false, false, false, 'Customer requested install date');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CARRIER_ORDER_SUBMITTED'), true,1000, true, true, false, false, false, 'Date order submitted to the Carrier');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'SITE_ACCESS_CONFIRMED'), true, 1010, true, true, false, false, false, 'Date Site access has been confirmed');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_FOC'), true, 1020, true, true, false, false, false, 'Network Firm Order Commitment date provided by the carrier');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'DATA_PROVISIONING_COMPLETE'), true, 1030, true, true, false, false, false, 'Confirmation by Carrier that the Provisioning is completed');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'PRE_ACTIVATION_CALL_COMPLETE'), true, 2000, true, true, false, false, false, 'Pre activation tasks successfully completed with tech and/or LCON');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_REQUESTED'), true, 2010, true, true, false, false, false, 'Date the Activation was requested to be scheduled');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_SCHEDULED'), true, 2020, true, true, false, false, false, 'Confirmated Activation scheduled on requested date');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_COMPLETE'), true, 2030, true, true, true, false, false, 'Date all activation activites were successfully completed');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), true, 3000, true, true, false, false, false, 'Order is on hold, Jeop has been noted and opened');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), true, 3010, true, true, false, false, false, 'All aspects of the order have been successfully completed');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), true, 3020, true, true, false, false, false, 'Order has been cancelled');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), true, 3030, true, true, false, false, false, 'Date order has been reassigned to another user worklist ');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'QA_CHECK_OPEN'), true, 4000, true, true, false, false, false, 'The date the QA Engineer begins reviewing the service items');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'FIRST_VENDOR_INVOICE'), true, 4010, true, true, false, false, false, 'Expected or actual first billing cycle date received from the vendor');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_ORDER_GROUP'), true, 4020, true, true, false, false, false, '	Date the QA engineer sent requests back to the provisioning group for input');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_SALES'), true, 4030, true, true, false, false, false, 'Date the QA engineer sent requests back to the sales group for input');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), true, 4040, true, true, false, false, false, 'Bill review successfully validated and completed');



INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), true, 10, true, false, true, false, false, 'Order created in the platform');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), true, 20, true, true, false, false, false, 'Order received and working');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_REQUESTED_DISCONNECT'), true, 30, true, true, false, false, false, 'Customer requested disconnect date');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CARRIER_ORDER_SUBMITTED'), true,1000, true, true, false, false, false, 'Date disconnect order submitted to the Carrier');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_FOC'), true, 1010, true, true, false, false, false, 'The access providers Firm Order Commitment date');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CARRIER_DISCONNECT_COMPLETE'), true, 1020, true, true, false, false, false, 'Confirmation by Carrier that the service has been disconnected');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), true, 3000, true, true, false, false, false, 'Order is on hold, Jeop has been noted and opened');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), true, 3010, true, true, false, false, false, 'All aspects of the order have been successfully completed');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), true, 3020, true, true, false, false, false, 'Disconnect order has been cancelled');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), true, 3030, true, true, false, false, false, 'Date order has been reassigned to another user worklist');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_BILL_STOP'), true, 4010, true, true, false, false, false, 'Service stop bill date confirmed');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), true, 4020, true, true, false, false, false, 'Bill review successfully validated and completed');




INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'QA_CHECK_OPEN'), true, 4000, true, true, false, false, false, 'The date the QA Engineer begins reviewing the service items');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'FIRST_VENDOR_INVOICE'), true, 4010, true, true, false, false, false, 'Expected or actual first billing cycle date received from the vendor');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_ORDER_GROUP'), true, 4020, true, true, false, false, false, '	Date the QA engineer sent requests back to the provisioning group for input');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_SALES'), true, 4030, true, true, false, false, false, 'Date the QA engineer sent requests back to the sales group for input');
update milestone_display_set_include set milestone_sequence = 4040 where milestone_id = (select milestone_id from milestone where milestone_code = 'BILLING_REVIEW_COMPLETE') and milestone_display_set_id = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE');
Delete from milestone_display_set_include where milestone_id = (select milestone_id from milestone where milestone_code = 'CUSTOMER_BILL_STOP') and milestone_display_set_id = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE');



INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'QA_CHECK_OPEN'), true, 4000, true, true, false, false, false, 'The date the QA Engineer begins reviewing the service items');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'FIRST_VENDOR_INVOICE'), true, 4010, true, true, false, false, false, 'Expected or actual first billing cycle date received from the vendor');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_ORDER_GROUP'), true, 4020, true, true, false, false, false, '	Date the QA engineer sent requests back to the provisioning group for input');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_SALES'), true, 4030, true, true, false, false, false, 'Date the QA engineer sent requests back to the sales group for input');
update milestone_display_set_include set milestone_sequence = 4040 where milestone_id = (select milestone_id from milestone where milestone_code = 'BILLING_REVIEW_COMPLETE') and milestone_display_set_id = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE');
Delete from milestone_display_set_include where milestone_id = (select milestone_id from milestone where milestone_code = 'CUSTOMER_BILL_STOP') and milestone_display_set_id = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE');


INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'QA_CHECK_OPEN'), true, 4000, true, true, false, false, false, 'The date the QA Engineer begins reviewing the service items');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'FIRST_VENDOR_INVOICE'), true, 4010, true, true, false, false, false, 'Expected or actual first billing cycle date received from the vendor');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_ORDER_GROUP'), true, 4020, true, true, false, false, false, '	Date the QA engineer sent requests back to the provisioning group for input');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_SALES'), true, 4030, true, true, false, false, false, 'Date the QA engineer sent requests back to the sales group for input');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), true, 4020, true, true, false, false, false, 'Bill review successfully validated and completed');



INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_DESIRED_DUE'), true, 30, true, true, false, false, false, 'Customer requested install date');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'QA_CHECK_OPEN'), true, 4000, true, true, false, false, false, 'The date the QA Engineer begins reviewing the service items');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'FIRST_VENDOR_INVOICE'), true, 4010, true, true, false, false, false, 'Expected or actual first billing cycle date received from the vendor');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_ORDER_GROUP'), true, 4020, true, true, false, false, false, '	Date the QA engineer sent requests back to the provisioning group for input');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_SALES'), true, 4030, true, true, false, false, false, 'Date the QA engineer sent requests back to the sales group for input');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), true, 4020, true, true, false, false, false, 'Bill review successfully validated and completed');
Delete from milestone_display_set_include where milestone_id = (select milestone_id from milestone where milestone_code = 'ENGINEER_ASSIGNED') and milestone_display_set_id = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE');
