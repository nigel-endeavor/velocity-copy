DROP VIEW IF EXISTS v_location_milestone_instance CASCADE;
create or replace view v_location_milestone_instance as
select lmi.location_id, mi.milestone_instance_id, mi.milestone_date, m.milestone_id, m.milestone_name, m.milestone_code
from location_milestone_instance lmi
         join milestone_instance mi on lmi.milestone_instance_id = mi.milestone_instance_id
         join milestone m on mi.milestone_id = m.milestone_id;
