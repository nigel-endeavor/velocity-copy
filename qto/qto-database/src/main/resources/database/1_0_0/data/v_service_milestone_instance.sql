create or replace view v_service_milestone_instance as
select smi.service_id, mi.milestone_instance_id, mi.milestone_date, m.milestone_id, m.milestone_name, m.milestone_code
from service_milestone_instance smi
         join milestone_instance mi on smi.milestone_instance_id = mi.milestone_instance_id
         join milestone m on mi.milestone_id = m.milestone_id;
