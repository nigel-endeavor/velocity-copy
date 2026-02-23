DROP VIEW IF EXISTS v_activation_attempts CASCADE;
create or replace view v_activation_attempts as
SELECT company_name,
       o.tenant_id,
       o.master_customer_id,
       (select company_name from company where company_id = c.master_customer_id) as master_company_name,
       o.order_id,
       o.client_order_id,
       l.location_id,
       l.location_name,
       l.client_location_id,
       s.service_id,
       s.client_service_id,
       s.carrier,
       s.service_type,
       l.client_location_type,
       l.client_location_info,
    aa.activation_attempt_id,
       aa.attempt_number,
       aa.field_tech_check_in,
       aa.field_tech_check_out,
    aa.scheduled_attempt_status,
    MONTH(aa.field_tech_check_out) AS aa_month,
    YEAR(aa.field_tech_check_out) AS aa_year,
    TIMESTAMPDIFF(MINUTE, aa.field_tech_check_in,
    aa.field_tech_check_out) AS service_activation_interval,
    laa_view.location_activation_interval,
    (SELECT mi.milestone_date
    FROM milestone_instance mi
    JOIN service_milestone_instance smi
    ON mi.milestone_instance_id = smi.milestone_instance_id
    JOIN milestone m ON mi.milestone_id = m.milestone_id
    WHERE milestone_code = 'DATA_PROVISIONING_COMPLETE'
    AND smi.service_id = s.service_id) AS data_provisioning_complete,

    (SELECT mi.milestone_date
    FROM milestone_instance mi
    JOIN service_milestone_instance smi
    ON mi.milestone_instance_id = smi.milestone_instance_id
    JOIN milestone m ON mi.milestone_id = m.milestone_id
    WHERE milestone_code = 'COMPLETE'
    AND smi.service_id = s.service_id) AS service_complete
FROM activation_attempt aa
    JOIN service s ON aa.service_id = s.service_id
    JOIN location l ON s.location_id = l.location_id
    JOIN (SELECT location_id,
    MONTH(aa.field_tech_check_out) AS aa_month,
    YEAR(aa.field_tech_check_out) AS aa_year,
    SUM(TIMESTAMPDIFF(MINUTE, aa.field_tech_check_in,
    aa.field_tech_check_out)) AS location_activation_interval
    FROM activation_attempt aa
    JOIN service s ON aa.service_id = s.service_id
    GROUP BY location_id, aa_month, aa_year) laa_view
    ON l.location_id = laa_view.location_id AND
    MONTH(aa.field_tech_check_out) = laa_view.aa_month
    AND YEAR(aa.field_tech_check_out) = laa_view.aa_year
    JOIN orders o ON l.order_id = o.order_id
    JOIN company c ON o.company_id = c.company_id