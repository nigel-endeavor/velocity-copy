create or replace view v_carrier_intervals as
select ii.interval_instance_id, company_name, (select company_name from company where company_id = c.master_customer_id) as master_company_name,
       o.order_id, o.client_order_id, l.location_id, l.location_name,
       l.client_location_id, s.service_id, s.client_service_id, s.carrier, s.service_type,
       it.interval_type_code, it.interval_type_desc, om.milestone_code as open_milestone_code,
       omi.milestone_date as start_date, cm.milestone_code as close_milestone_code,
       cmi.milestone_date as end_date, ii.calendar_day_interval_time, ii.carrier_calendar_day_deduct_time,
       ii.customer_calendar_day_deduct_time, ii.client_calendar_day_deduct_time,
       ii.business_day_interval_time, ii.carrier_business_day_deduct_time,
       ii.client_business_day_deduct_time, s.active, s.tenant_id, s.master_customer_id
from interval_instance ii
         join interval_type it on ii.interval_type_id = it.interval_type_id
         join service_interval_instance sii ON ii.interval_instance_id = sii.interval_instance_id
         join service s ON sii.service_id = s.service_id
         join location l on s.location_id = l.location_id
         join orders o on l.order_id = o.order_id
         join company c on o.company_id = c.company_id
         join milestone_instance omi on ii.open_milestone_instance_id = omi.milestone_instance_id
         join milestone om on omi.milestone_id = om.milestone_id
         left join milestone_instance cmi on ii.close_milestone_instance_id = cmi.milestone_instance_id
         left join milestone cm on cmi.milestone_id = cm.milestone_id;