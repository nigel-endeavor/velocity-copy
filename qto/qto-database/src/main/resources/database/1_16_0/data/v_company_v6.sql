DROP VIEW IF EXISTS v_company CASCADE;
CREATE OR REPLACE VIEW v_company AS
SELECT lct.complete_date,
       c.*,
       COALESCE((SELECT display_name FROM v_subject WHERE subject_id = c.account_manager), 'Unassigned') AS account_manager_name,
       TRIM(CONCAT(CASE WHEN (bc.first_name is not null) THEN bc.first_name ELSE '' END, ' ',
                   CASE WHEN (bc.last_name is not null) THEN bc.last_name ELSE '' END)) AS billing_contact_name,
       bc.email                                                                         AS billing_contact_email,
       bc.phone                                                                         AS billing_contact_phone,
       t.name                                                                           as tenant_name,
       (CASE
            WHEN (c.task_group_id is null AND c.company_active) THEN 'active'
            ELSE
                (CASE
                     WHEN (c.task_group_id is not null AND (cts.completedTasks = cts.totalTasks OR
                                                            (cts.completedTasks is null AND cts.totalTasks is null)) AND
                           c.company_active) THEN 'active'
                     ELSE
                         (CASE
                              WHEN (c.task_group_id is not null AND cts.completedTasks != cts.totalTasks AND
                                    c.company_active) THEN 'onboarding'
                              ELSE 'inactive' END) END) END)                            as status,
       lct.value                                                                        as last_completed_task,
       nct.value                                                                        as next_task,
       nct.assigned_to                                                                  as next_task_assigned_to,
       (cts.totalTasks - cts.completedTasks)                                            as remaining_tasks,
       ROUND((CASE
                  WHEN c.task_group_id is null OR (cts.completedTasks is null AND cts.totalTasks is null) THEN 1
                  ELSE (cts.completedTasks / cts.totalTasks) END) * 100)                as progress_percentage
FROM company c
         LEFT JOIN contact bc ON bc.company_id = c.company_id AND bc.contact_type = 'BILLING'
         LEFT JOIN (SELECT ct.company_id,
                           count(*)                                                      as totalTasks,
                           sum(CASE WHEN ct.complete_date is not null THEN 1 ELSE 0 END) as completedTasks,
                           max(ct.complete_date)                                         as last_completed_task_date
                    from company_task ct
                    group by ct.company_id) cts on cts.company_id = c.company_id
         LEFT JOIN company_task lct
                   on lct.company_id = c.company_id and lct.company_task_id = (select ct2.company_task_id
                                                                               from company_task ct2
                                                                               where ct2.company_id = c.company_id
                                                                                 and ct2.complete_date =
                                                                                     (select max(complete_date)
                                                                                      from company_task ct3
                                                                                      where ct3.company_id = c.company_id)
                                                                               order by ct2.company_id desc
                                                                               limit 1)
         LEFT JOIN (select ct.*
                    from company_task ct
                             inner join (select min(mct.company_task_id) as mcti, mct.company_id
                                         from company_task mct
                                         where mct.complete_date is null
                                         group by mct.company_id) ctn on ctn.mcti = ct.company_task_id) nct
                   on nct.company_id = c.company_id
         JOIN v_tenant t ON t.tenant_id = c.tenant_id