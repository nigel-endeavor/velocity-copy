DROP VIEW IF EXISTS v_manage_activations CASCADE;
create or replace view v_manage_activations as
select
    aa.activation_attempt_id,
    s.service_id,
    s.location_id,
    s.order_id,
    s.client_service_id,
    COALESCE(aa.scheduled_attempt_status, 'Scheduled') as scheduled_attempt_status,
    COALESCE(aa.internal_tech_assigned, 'Unassigned') as internal_tech_assigned,
    aa.scheduled_check_in_time,
    aa.field_tech_check_in,
    s.last_update_by,
    l.client_location_type,
    l.client_location_info,
    l.client_location_id,
    rt.ttu_equivalent,
    s.tenant_id,
    s.version
from service s
         join location l on s.location_id = l.location_id
         left join requirement_template rt on l.requirement_template_id = rt.requirement_template_id
         join activation_attempt aa on s.service_id = aa.service_id;