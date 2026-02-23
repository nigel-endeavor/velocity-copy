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
       activation.close_notes,
       TIMESTAMPDIFF(MINUTE, activation.field_tech_check_in, activation.field_tech_check_out) as 'total_appointment_time',
       activation.version,
       activation.tenant_id,
       service.service_id,
       service.client_service_id,
       service.provider,
       location.location_id,
       location.client_location_id,
       location.client_location_info,
       location.location_name,
       location.address_1,
       location.city,
       location.state_province,
       location.postal_code,
       location.country,
       orders.order_id,
       orders.company_id
from activation_attempt activation
inner join service on activation.service_id = service.service_id
inner join location on service.location_id = location.location_id
inner join orders on location.order_id = orders.order_id;
