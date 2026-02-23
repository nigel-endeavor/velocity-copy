DROP VIEW IF EXISTS pbi_iss_location CASCADE;
Create or replace view pbi_iss_location as
SELECT l.location_id                                         as 'Location ID',
       p.company_name                                        AS 'Master Customer',
       c.company_name                                        AS 'End Customer',
       pr.display_name                                       as 'Provisioner',
       vpm.display_name                                      as 'Vertek Project Manager',
       ae.display_name                                       as 'Activation Engineer',
       client_project_manager                                as 'Client Project Manager',
       l.address_1                                           AS 'Address 1',
       l.address_2                                           AS 'Address 2',
       l.city                                                AS 'City',
       l.state_province                                      AS 'State',
       l.postal_code                                         AS 'Zip',
       client_location_id                                    AS 'Client Location ID',
       quote_location_id                                     AS 'Quote Location ID',
       location_name                                         AS 'Location Name',
       location_status                                       AS 'Location Status',
       building_type                                         AS 'Building Type',
       phone_number                                          as 'Location Phone Number',
       client_location_type                                  AS 'Client Location Type',
       client_location_info                                  AS 'Client Location Info',
       l.last_update_by                                      as 'Last Update By',
       l.last_update_date                                    as 'Last Update Date',
       l.active                                              as 'Active',
       progress_percentage                                   as 'Progress Percentage',
       legacy_transaction_type                               as 'Legacy Transaction Type',
       legacy_transaction_amount                             as 'Legacy Transaction Amount',
       l.legacy_id                                           as 'Legacy ID',
       level_of_effort                                       as 'Level of Effort',
       jn.note                                               as 'Last Note',
       jn.created_date                                       as 'Last Note Created Date',
       jn.created_by                                         as 'Last Note Created By',
       s.job_number                                          as 'Job Number',
       case
           when s.new_network_job_number is not null then s.new_network_job_number
           else s.old_network_job_number end                 as 'IPNETW Job Number',
       case
           when s.new_ucass_job_number is not null then s.new_ucass_job_number
           else s.old_ucaas_job_number end                   as 'VoIP Job Number',
       cntNetwork                                            as 'Count IPETW',
       cntUCaaS                                              as 'Count VoIP',
       case
           when cntNetwork > 0 and cntUCaaS = 0 then 1
           when cntNetwork > 0 and cntUCaaS > 0 then .75
           when cntNetwork = 0 then 0 end                    as 'IPNETW Percentage',
       case
           when cntUCaaS > 0 and cntNetwork = 0 then 1
           when cntUCaaS > 0 and cntNetwork > 0 then .25 end as 'VoIP Percentage'
FROM location l
         JOIN orders o ON l.order_id = o.order_id
         left join v_subject pr on o.provisioner = pr.subject_id
         left join v_subject vpm on o.vertek_project_manager = vpm.subject_id
         left join v_subject ae on o.activation_engineer = ae.subject_id
         JOIN company c ON o.company_id = c.company_id
         LEFT JOIN company p ON c.master_customer_id = p.company_id
         left join (select ln.location_id, note, n.created_date, n.created_by
                    from note n
                             join location_note ln on n.note_id = ln.note_id
                             join (select ln.location_id, max(n.note_id) as note_id
                                   from note n
                                            join location_note ln on n.note_id = ln.note_id
                                   group by ln.location_id) vw on ln.note_id = vw.note_id) jn
                   on l.location_id = jn.location_id
         left join (select location_id,
                           string_agg(DISTINCT CONCAT(service_type, '-', job_number, ',')) as job_number,
                           max(case
                                   when
                                       (service_type = 'Broadband' or service_type = 'DIA' or service_type = '4G/5G') and
                                       job_number like 'CPQ%' then job_number
                                   else null end)                                       as new_network_job_number,
                           max(case
                                   when service_type = 'Broadband' or service_type = 'DIA' or service_type = '4G/5G'
                                       then job_number
                                   else null end)                                       as old_network_job_number,
                           max(case
                                   when (service_type = 'UCaaS') and job_number like 'CPQ%' then job_number
                                   else null end)                                       as new_ucass_job_number,
                           max(case
                                   when service_type = 'UCaaS' then job_number
                                   else null end)                                       as old_ucaas_job_number,
                           SUM(CASE
                                   WHEN service_type = 'Broadband'
                                       THEN 1
                                   WHEN service_type = 'DIA'
                                       THEN 1
                                   WHEN service_type = '4G/5G'
                                       THEN 1
                                   ELSE 0 END)                                          AS cntNetwork,
                           SUM(CASE WHEN service_type = 'UCaaS' THEN 1 ELSE 0 END)      AS cntUCaaS
                    FROM service
                    where service_status not in ('Service Cancelled', 'Change In Assignment')
                    GROUP BY location_id) s on l.location_id = s.location_id
where l.tenant_id = (select tenant_id from v_tenant where name = 'Endeavor')
  AND NOT l.current_inventory;
