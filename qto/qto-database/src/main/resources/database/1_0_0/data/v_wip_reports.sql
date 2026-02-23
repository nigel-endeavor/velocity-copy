-- ----------------------------------------------------------------------------------------------------
DROP VIEW IF EXISTS v_wip_service CASCADE;
create or replace view v_wip_service as
select company_name, o.order_id, o.provisioner, o.vertek_project_manager, o.client_project_manager, client_order_id,
       l.location_id, l.client_location_id, l.location_name, a.address_1, a.address_2, a.city, a.state_province,
       s.service_id, s.client_service_id, s.carrier, s.service_status
from company c
join orders o ON c.company_id = o.company_id
join location l on o.order_id = l.order_id
join service s ON l.location_id = s.location_id
left join address a on l.address_id = a.address_id;

-- -------------------------------------------------------------------------------------------------------------------
DROP VIEW IF EXISTS v_wip_service_jeop CASCADE;
create or replace view v_wip_service_jeop as
select company_name, o.order_id, o.provisioner, o.vertek_project_manager, o.client_project_manager, client_order_id,
       l.location_id, l.client_location_id, l.location_name, a.address_1, a.address_2, a.city, a.state_province,
       s.service_id, s.client_service_id, s.carrier, s.service_status, ji.jeop_instance_id, ji.jeop_description,
       ji.start_date, ji.end_date, ji.responsibility, ji.assigned_to
from company c
join orders o ON c.company_id = o.company_id
join location l on o.order_id = l.order_id
join service s ON l.location_id = s.location_id
left join address a on l.address_id = a.address_id
join service_jeop_instance sji on s.service_id = sji.service_id
join jeop_instance ji on sji.jeop_instance_id = ji.jeop_instance_id;