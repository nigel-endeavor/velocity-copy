SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'CROSS_CONNECT_SERVICE_MILESTONE');

DELETE FROM milestone_display_set_include WHERE milestone_display_set_id = @displaySetId;

INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description, inventory_flag, progress_percentage, status)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0, 'Order created in the platform', 0, 5, 'Pending Assignment'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 0, 0, 0, 0, 'Order received and working', 0, null, 'Pending Assignment'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ENGINEER_ASSIGNED'), 1, 30, 0, 0, 0, 0, 0, null, 0, 5, 'Engineer Assigned'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_REQUESTED_INSTALL'), 1, 40, 1, 0, 0, 0, 0, 'Customer requested install date', 0, null, null),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'PROVIDER_ORDER_SUBMITTED'), 1, 1010, 1, 0, 0, 0, 0, 'Date order submitted to the Carrier', 0, 25, 'Provisioning'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'NETWORK_PROVIDER_FOC'), 1, 1020, 1, 0, 0, 0, 0, 'Network Firm Order Commitment date provided by the carrier', 0, 40, 'Provisioning'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'DATA_PROVISIONING_COMPLETE'), 1, 1030, 1, 0, 0, 0, 0, 'Confirmation by Carrier that the Provisioning is completed', 1, 60, 'Circuit Complete'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 3010, 1, 0, 0, 0, 0, 'Order is on hold, Jeop has been noted and opened', 0, null, 'On Hold'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 3020, 1, 0, 0, 0, 0, 'All aspects of the order have been successfully completed', 1, 100, 'Service Complete'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 3030, 1, 0, 0, 0, 0, 'Order is on hold, Jeop has been noted and opened with a complete date and order closed out', 0, 100, 'Service Cancelled'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 3040, 1, 0, 0, 0, 0, 'Date order has been reassigned to another user worklist', 0, 100, 'Change In Assignment'),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'QA_CHECK_OPEN'), 1, 4000, 1, 0, 0, 0, 0, 'The date the QA Engineer begins reviewing the service items', 0, null, null),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'FIRST_VENDOR_INVOICE'), 1, 4010, 1, 0, 0, 0, 0, 'Expected or actual first billing cycle date received from the vendor', 0, null, null),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_ORDER_GROUP'), 1, 4020, 1, 0, 0, 0, 0, 'Date the QA engineer sent requests back to the provisioning group for input', 0, null, null),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RETURNED_TO_SALES'), 1, 4030, 1, 0, 0, 0, 0, 'Date the QA engineer sent requests back to the sales group for input', 0, null, null),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'BILLING_REVIEW_COMPLETE'), 1, 4040, 1, 0, 0, 0, 0, 'Bill review successfully validated and completed', 0, null, null);
