create or replace view v_manage_services as
select
    s.service_id,
    s.location_id,
    l.client_location_id,
    CONCAT(a.address_1,
           IF(LENGTH(a.address_2), CONCAT('\n', a.address_2), ''),
           '\n', a.city, ', ', a.state_province
        ) as address,
    s.order_id,
    s.service_status,
    o.provisioner,
    o.client_project_manager as project_manager,
    s.carrier,
    (select milestone_date from v_service_milestone_instance smi where smi.service_id = s.service_id and smi.milestone_code = 'CUSTOMER_DESIRED_DUE') as customer_desired_due,
    (select milestone_date from v_service_milestone_instance smi where smi.service_id = s.service_id and smi.milestone_code = 'SITE_SURVEY_SUBMIT') as site_survey_submit,
    (select milestone_date from v_service_milestone_instance smi where smi.service_id = s.service_id and smi.milestone_code = 'SITE_SURVEY_DUE') as site_survey_due,
    (select milestone_date from v_service_milestone_instance smi where smi.service_id = s.service_id and smi.milestone_code = 'CARRIER_ORDER_SUBMITTED') as carrier_order_submitted,
    (select milestone_date from v_service_milestone_instance smi where smi.service_id = s.service_id and smi.milestone_code = 'NETWORK_PROVIDER_FOC') as network_provider_foc,
    (select milestone_date from v_service_milestone_instance smi where smi.service_id = s.service_id and smi.milestone_code = 'DATA_PROVISIONING_COMPLETE') as data_provisioning_complete,
    (select milestone_name from v_service_milestone_instance smi where smi.service_id = s.service_id order by milestone_date desc, milestone_instance_id desc limit 1) as greatest_milestone_name,
    (select milestone_date from v_service_milestone_instance smi where smi.service_id = s.service_id order by milestone_date desc, milestone_instance_id desc limit 1) as greatest_milestone_date,
    l.client_location_type,
    l.client_location_info,
    c.company_name,
    CONCAT(COALESCE(s.download_speed, ''), '/', COALESCE(s.upload_speed, '')) as speed,
    s.service_type,
    s.version,
    s.tenant_id,
    (select vju.jeop_description from v_jeops_union vju where vju.end_date is null and vju.service_id = s.service_id order by vju.jeop_instance_id desc limit 1) as open_jeop,
    (select n.note from note n join service_note sn on n.note_id = sn.note_id where sn.service_id = s.service_id order by n.created_date desc limit 1) as latest_note
from service s
         join location l on s.location_id = l.location_id
         join orders o on s.order_id = o.order_id
         join company c on o.company_id = c.company_id
         join address a on l.address_id = a.address_id;
