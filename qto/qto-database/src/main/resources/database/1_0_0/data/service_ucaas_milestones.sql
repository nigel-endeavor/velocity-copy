INSERT INTO milestone_display_set (display_group, display_set_label) VALUES ('UCAAS_SERVICE_MILESTONE', 'UCaaS Milestones');

SET @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set WHERE display_group = 'UCAAS_SERVICE_MILESTONE');

INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future)
VALUES (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CREATED'), 1, 10, 1, 0, 1, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'RECEIVED'), 1, 20, 1, 1, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ENGINEER_ASSIGNED'), 1, 30, 1, 0, 1, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_ORDERED'), 1, 40, 1, 1, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_RECEIVED'), 1, 50, 1, 1, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'EQUIPMENT_CONFIGURED'), 1, 60, 1, 1, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_REQUESTED'), 1, 70, 1, 1, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_SCHEDULED'), 1, 80, 1, 1, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ACTIVATION_COMPLETE'), 1, 90, 1, 1, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'ON_HOLD'), 1, 1010, 1, 1, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'COMPLETE'), 1, 1020, 1, 1, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CANCELLED'), 1, 1030, 1, 1, 0, 0, 0),
       (@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CHANGE_IN_ASSIGNMENT'), 1, 1040, 1, 1, 0, 0, 0);