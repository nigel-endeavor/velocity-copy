set @displaySetId = (SELECT milestone_display_set_id FROM milestone_display_set mds WHERE mds.display_group = 'CYBERTHREESIXTYIAM_SERVICE_MILESTONE');


INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Closeout Call Scheduled', 'CLOSEOUT_CALL_SCHEDULED');
INSERT INTO milestone (milestone_name, milestone_code) VALUES ('Closeout Call Completed', 'CLOSEOUT_CALL_COMPLETED');


update milestone_display_set_include set milestone_sequence = 2750 where milestone_display_set_id = @DisplaySetId and milestone_id = (select m.milestone_id from milestone m where m.milestone_code = 'TECH_DATA_GATHERING_MEETING_SCHEDULED');
update milestone_display_set_include set milestone_sequence = 2760 where milestone_display_set_id = @DisplaySetId and milestone_id = (select m.milestone_id from milestone m where m.milestone_code = 'TECH_DATA_GATHERING_MEETING_COMPLETED');
update milestone_display_set_include set milestone_sequence = 2770, status = 	'Policy & Compliance Configuration' where milestone_display_set_id = @DisplaySetId and milestone_id = (select m.milestone_id from milestone m where m.milestone_code = 'REVIEW_EXISTING_CA & MFA_POLICIES');
update milestone_display_set_include set milestone_sequence = 2780 where milestone_display_set_id = @DisplaySetId and milestone_id = (select m.milestone_id from milestone m where m.milestone_code = 'SIGN IN POLICIES ENABLED');
update milestone_display_set_include set milestone_sequence = 2790 where milestone_display_set_id = @DisplaySetId and milestone_id = (select m.milestone_id from milestone m where m.milestone_code = 'CONDITIONAL ACCESS POLICY VERIFICATION');
update milestone_display_set_include set milestone_sequence = 2800 where milestone_display_set_id = @DisplaySetId and milestone_id = (select m.milestone_id from milestone m where m.milestone_code = 'GEOGRAPHIC RESTRICTIONS ENABLED');
update milestone_display_set_include set milestone_sequence = 2810 where milestone_display_set_id = @DisplaySetId and milestone_id = (select m.milestone_id from milestone m where m.milestone_code = 'DEVICE COMPLIANCE ENABLED');
update milestone_display_set_include set milestone_sequence = 2820 where milestone_display_set_id = @DisplaySetId and milestone_id = (select m.milestone_id from milestone m where m.milestone_code = 'PASSWORD RESET ENABLED FOR SELF-SERVICE');
update milestone_display_set_include set milestone_sequence = 2830 where milestone_display_set_id = @DisplaySetId and milestone_id = (select m.milestone_id from milestone m where m.milestone_code = 'BREAK-GLASS ACCOUNT CONFIGURED');
update milestone_display_set_include set milestone_sequence = 2840 where milestone_display_set_id = @DisplaySetId and milestone_id = (select m.milestone_id from milestone m where m.milestone_code = 'PIM ENABLEMENT');
update milestone_display_set_include set milestone_sequence = 2850, status = 'Implementation Verified', progress_percentage = 85 where milestone_display_set_id = @DisplaySetId and milestone_id = (select m.milestone_id from milestone m where m.milestone_code = 'IMPLEMENTATION VERIFIED');

INSERT INTO milestone_display_set_include (milestone_display_set_id, milestone_id, milestone_active, milestone_sequence,
                                           milestone_required, adjustable, workflow_driven, has_time, disallow_future, description, inventory_flag, progress_percentage, status)
VALUES
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CLOSEOUT_CALL_SCHEDULED'), 1, 2860, 1, 1, 0, 0, 0, 'Meeting to overview the goals of the project and ensure that the goals of the project have been completed', 0, 90, 'Closeout Call Scheduled'),
(@displaySetId, (SELECT milestone_id FROM milestone WHERE milestone_code = 'CLOSEOUT_CALL_COMPLETED'), 1, 2870, 1, 1, 0, 0, 0, 'Meeting completed', 0, 95, 'Closeout Call Completed');
