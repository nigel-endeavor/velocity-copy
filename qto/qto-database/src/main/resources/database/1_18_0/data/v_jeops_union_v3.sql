DROP VIEW IF EXISTS v_jeops_union CASCADE;
CREATE OR REPLACE VIEW v_jeops_union AS

select distinct s.order_id, s.location_id, s.service_id, ji.*, concat(jeop_level, ':', jeop_description) as level_jeop
from service s
       join location_jeop_instance lji ON s.location_id = lji.location_id
	join location l on lji.location_id = l.location_id
join jeop_instance ji on lji.jeop_instance_id = ji.jeop_instance_id
where l.marked_for_deletion = false

UNION
  select distinct s.order_id, s.location_id, s.service_id, ji.*, concat(jeop_level, ':', jeop_description) as level_jeop
from service s
       join service_jeop_instance sji ON s.service_id = sji.service_id
join jeop_instance ji on sji.jeop_instance_id = ji.jeop_instance_id
where s.marked_for_deletion = false

union

select distinct s.order_id, s.location_id, s.service_id, ji.*, concat(jeop_level, ':', jeop_description) as level_jeop from service s
       join order_jeop_instance oji ON s.order_id = oji.order_id
join jeop_instance ji on oji.jeop_instance_id = ji.jeop_instance_id;







