

INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_sequence, workflow_driven, status, progress_percentage)
VALUES
    ((SELECT milestone_display_set_id from milestone_display_set where display_group = 'BROADBAND_SERVICE_MILESTONE'), (SELECT milestone_id from milestone where milestone_code = 'ENGINEER_ASSIGNED'), 25, 1, 'Engineer Assigned', 5),
    ((SELECT milestone_display_set_id from milestone_display_set where display_group = 'DIA_SERVICE_MILESTONE'), (SELECT milestone_id from milestone where milestone_code = 'ENGINEER_ASSIGNED'), 25, 1, 'Engineer Assigned', 5),
    ((SELECT milestone_display_set_id from milestone_display_set where display_group = 'UCAAS_SERVICE_MILESTONE'), (SELECT milestone_id from milestone where milestone_code = 'ENGINEER_ASSIGNED'), 30, 1, 'Engineer Assigned', 5),
    ((SELECT milestone_display_set_id from milestone_display_set where display_group = '4G5G_SERVICE_MILESTONE'), (SELECT milestone_id from milestone where milestone_code = 'ENGINEER_ASSIGNED'), 30, 1, 'Engineer Assigned', 5),
    ((SELECT milestone_display_set_id from milestone_display_set where display_group = 'EXISTING_MAC_MILESTONE'), (SELECT milestone_id from milestone where milestone_code = 'ENGINEER_ASSIGNED'), 30, 1, 'Engineer Assigned', 5),
    ((SELECT milestone_display_set_id from milestone_display_set where display_group = 'DISCONNECT_MILESTONE'), (SELECT milestone_id from milestone where milestone_code = 'ENGINEER_ASSIGNED'), 25, 1, 'Engineer Assigned', 5),
    ((SELECT milestone_display_set_id from milestone_display_set where display_group = 'CROSS_CONNECT_SERVICE_MILESTONE'), (SELECT milestone_id from milestone where milestone_code = 'ENGINEER_ASSIGNED'), 25, 1, 'Engineer Assigned', 5),
    ((SELECT milestone_display_set_id from milestone_display_set where display_group = 'ETHERNET_SERVICE_MILESTONE'), (SELECT milestone_id from milestone where milestone_code = 'ENGINEER_ASSIGNED'), 25, 1, 'Engineer Assigned', 5);
