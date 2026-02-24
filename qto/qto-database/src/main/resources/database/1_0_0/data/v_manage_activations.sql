DROP VIEW IF EXISTS v_manage_activations CASCADE;
create or replace view v_manage_activations as
select
    s.service_id,
    s.location_id,
    s.order_id,
    s.client_service_id,
    COALESCE(aa.scheduled_attempt_status, 'Scheduled') as scheduled_attempt_status,
    COALESCE(aa.internal_tech_assigned, 'Unassigned') as internal_tech_assigned,
    (select milestone_date from v_service_milestone_instance smi where smi.service_id = s.service_id and smi.milestone_code = 'ACTIVATION_SCHEDULED') activation_scheduled_date,
    COALESCE(to_char(aa.scheduled_check_in_time, 'FMHH12:MI AM'), '') as scheduled_check_in_time,
    aa.field_tech_check_in,
    s.last_update_by,
    l.client_location_type,
    l.client_location_info,
    null as ttu_equivalent, -- location.requirement_template.ttu_equivalent
    s.tenant_id,
    s.version
FROM service s
JOIN location l 
  ON s.location_id = l.location_id
JOIN (
    SELECT *
    FROM activation_attempt a
    WHERE a.scheduled_check_in_time::date = (
      SELECT milestone_date
      FROM v_service_milestone_instance smi
      WHERE smi.service_id = a.service_id
        AND smi.milestone_code = 'ACTIVATION_SCHEDULED'
    )::date
) aa 
  ON s.service_id = aa.service_id;
