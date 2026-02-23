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
       locationAddress.address_1,
       locationAddress.city,
       locationAddress.state_province,
       locationAddress.postal_code,
       locationAddress.country,
       orders.order_id,
       orders.company_id
from activation_attempt activation
inner join service on activation.service_id = service.service_id
inner join location on service.location_id = location.location_id
left outer join address locationAddress on location.address_id = locationAddress.address_id
inner join orders on location.order_id = orders.order_id;

delete from template_variable;
insert into template_variable (label, path, type, template_type, version)
values  ('Activation Attempt ID', 'activationAttemptId', 'int', 'ACTIVATION', true),
        ('Network Complete Date', 'networkCompleteDate', 'date', 'ACTIVATION', true),
        ('VOIP Complete Date', 'voipCompleteDate', 'date', 'ACTIVATION', true),
        ('Tested Download Speed', 'testedDownloadSpeed', 'string', 'ACTIVATION', true),
        ('Tested Upload Speed', 'testedUploadSpeed', 'string', 'ACTIVATION', true),
        ('Backup Download Speed', 'backupDownloadSpeed', 'string', 'ACTIVATION', true),
        ('Backup Upload Speed', 'backupUploadSpeed', 'string', 'ACTIVATION', true),
        ('Signal/RSRP', 'signalRsrp', 'string', 'ACTIVATION', true),
        ('SINR/RSRQ', 'sinrRsrq', 'string', 'ACTIVATION', true),
        ('Location Downtime for Cutover', 'locationDowntimeForCutover', 'string', 'ACTIVATION', true),
        ('Replace 4G/5G with Broadband/DIA', 'replace4g5gWithBroadbandDia', 'string', 'ACTIVATION', true),
        ('Address 1', 'address1', 'string', 'ACTIVATION', true),
        ('City', 'city', 'string', 'ACTIVATION', true),
        ('State/Province/Region', 'stateProvince', 'string', 'ACTIVATION', true),
        ('ZIP/Postal Code', 'postalCode', 'string', 'ACTIVATION', true),
        ('Country', 'country', 'string', 'ACTIVATION', true),
        ('Client Service ID', 'clientServiceId', 'string', 'ACTIVATION', true),
        ('Client Location ID', 'clientLocationId', 'string', 'ACTIVATION', true),
        ('Location Name', 'locationName', 'string', 'ACTIVATION', true),
        ('Client Location Info', 'clientLocationInfo', 'string', 'ACTIVATION', true),
        ('Carrier', 'carrier', 'string', 'ACTIVATION', true),
        ('Close Notes', 'closeNotes', 'string', 'ACTIVATION', true),
        ('Total Appointment Time', 'totalAppointmentTime', 'string', 'ACTIVATION', true);