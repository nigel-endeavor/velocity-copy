DROP VIEW IF EXISTS v_activations_email_template CASCADE;
create or replace view v_activations_email_template as
select activation.activation_attempt_id,
       activation.network_complete_date,
       activation.voip_complete_date,
       activation.tested_download_speed,
       activation.tested_upload_speed,
       activation.signal_rsrp,
       activation.sinr_rsrq,
       activation.backup_download_speed,
       activation.backup_upload_speed,
       activation.location_downtown_for_cutover,
       activation.replace_4g_5g,
       service.client_service_id,
       service.carrier,
       location.client_location_id,
       location.client_location_info,
       location.location_name,
       locationAddress.address_1,
       locationAddress.city,
       locationAddress.state_province,
       locationAddress.postal_code,
       locationAddress.country,
       TIMESTAMPDIFF(MINUTE, activation.field_tech_check_in, activation.field_tech_check_out) as 'total_appointment_time',
       activation.version,
       orders.company_id,
       activation.tenant_id
from activation_attempt activation
inner join service on activation.service_id = service.service_id
inner join location on service.location_id = location.location_id
left outer join address locationAddress on location.address_id = locationAddress.address_id
inner join orders on location.order_id = orders.order_id