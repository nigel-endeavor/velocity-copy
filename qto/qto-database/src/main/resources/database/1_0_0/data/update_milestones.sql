UPDATE milestone_display_set_include
SET adjustable = 1
WHERE milestone_display_set_id IN
      (SELECT milestone_display_set_id
       FROM milestone_display_set mds
       WHERE mds.display_group IN
             ('LOCATION_MILESTONE', 'BROADBAND_SERVICE_MILESTONE', 'DIA_SERVICE_MILESTONE'));

update milestone_display_set_include set workflow_driven = 1, adjustable = 0 where milestone_id in
(Select milestone_id from milestone m where milestone_code in
  ('ENGINEER_ASSIGNED', 'PROVISIONING_START', 'ACTIVATION_COMPLETE', 'CREATED'));

update milestone_display_set_include set workflow_driven = 1, adjustable = 0 where milestone_display_set_include_id = (Select milestone_display_set_include_id from milestone_display_set_include mdsi join milestone m on mdsi.milestone_id = m.milestone_id join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id where milestone_code = 'COMPLETE' and mds.display_group = 'LOCATION_MILESTONE');
