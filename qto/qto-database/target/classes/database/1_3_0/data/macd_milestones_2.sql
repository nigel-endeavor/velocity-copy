SET @displaySetId = (SELECT milestone_display_set_id
                     FROM milestone_display_set mds
                     WHERE mds.display_group = 'EXISTING_MAC_MILESTONE');

DELETE
FROM milestone_display_set_include
WHERE milestone_id = (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_DESIRED_DUE')
	AND milestone_display_set_id = @displaySetId;

INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future,
                                           description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_REQUESTED_INSTALL'), 1, 30, 1, 1,
        0, 0, 0, 'Customer requested install date');



SET @displaySetId = (SELECT milestone_display_set_id
                     FROM milestone_display_set mds
                     WHERE mds.display_group = 'UCAAS_SERVICE_MILESTONE');
DELETE
FROM milestone_display_set_include
WHERE milestone_id = (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_DESIRED_DUE')
	AND milestone_display_set_id = @displaySetId;
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future,
                                           description)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_REQUESTED_INSTALL'), 1, 30, 1, 1,
        0, 0, 0, 'Customer requested install date');

