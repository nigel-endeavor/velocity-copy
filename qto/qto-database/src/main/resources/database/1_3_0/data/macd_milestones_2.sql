

DELETE
FROM milestone_display_set_include
WHERE milestone_id = (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_DESIRED_DUE')
	AND milestone_display_set_id = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE');

INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future,
                                           description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_REQUESTED_INSTALL'), true, 30, true, true,
        false, false, false, 'Customer requested install date');




                     FROM milestone_display_set mds
                     WHERE mds.display_group = 'UCAAS_SERVICE_MILESTONE');
DELETE
FROM milestone_display_set_include
WHERE milestone_id = (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_DESIRED_DUE')
	AND milestone_display_set_id = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE');
INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future,
                                           description)
VALUES ((SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id FROM milestone WHERE milestone_code = 'CUSTOMER_REQUESTED_INSTALL'), true, 30, true, true,
        false, false, false, 'Customer requested install date');

