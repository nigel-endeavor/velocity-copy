INSERT INTO milestone_display_set (display_group, display_set_label) VALUES ('CROSS_CONNECT_SERVICE_MILESTONE', 'Cross Connect Milestones');
SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'CROSS_CONNECT_SERVICE_MILESTONE');
DELETE FROM milestone_display_set_include WHERE milestone_display_set_id = @displaySetId;
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0, 'Order created in the platform'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 1, 0, 0, 0, 'Order received and working'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_REQUESTED_INSTALL'), 1, 30, 1, 1, 0, 0, 0, 'Customer requested install date'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CARRIER_ORDER_SUBMITTED'), 1, 1010, 1, 1, 0, 0, 0, 'Date order submitted to the Carrier'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_FOC'), 1, 1020, 1, 1, 0, 0, 0, 'Network Firm Order Commitment date provided by the carrier'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'DATA_PROVISIONING_COMPLETE'), 1, 1030, 1, 1, 0, 0, 0, 'Confirmation by Carrier that the Provisioning is completed'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 2010, 1, 1, 0, 0, 0, 'Order is on hold, Jeop has been noted and opened'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 2020, 1, 1, 0, 0, 0, 'All aspects of the order have been successfully completed'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 2030, 1, 1, 0, 0, 0, 'Order was cancelled with the provider prior to service being delivered'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 2040, 1, 1, 0, 0, 0, 'Date order has been reassigned to another user worklist'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'QA_CHECK_OPEN'), 1, 3010, 1, 1, 0, 0, 0, 'The date the QA Engineer begins reviewing the service items'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'FIRST_VENDOR_INVOICE'), 1, 3020, 1, 1, 0, 0, 0, 'Expected or actual first billing cycle date received from the vendor'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_ORDER_GROUP'), 1, 3030, 1, 1, 0, 0, 0, 'Date the QA engineer sent requests back to the provisioning group for input'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_SALES'), 1, 3040, 1, 1, 0, 0, 0, 'Date the QA engineer sent requests back to the sales group for input'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), 1, 3040, 1, 1, 0, 0, 0, 'Bill review successfully validated and completed');
