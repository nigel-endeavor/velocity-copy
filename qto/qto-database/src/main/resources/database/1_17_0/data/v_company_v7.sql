DROP VIEW IF EXISTS v_company CASCADE;
CREATE OR REPLACE VIEW v_company AS
SELECT lct.complete_date,
       c.*,
       COALESCE((SELECT display_name FROM v_subject WHERE subject_id = c.account_manager),
                'Unassigned') AS account_manager_name,
       TRIM(CONCAT(CASE WHEN (bc.first_name IS NOT NULL)
	                        THEN bc.first_name
                        ELSE '' END, ' ',
                   CASE WHEN (bc.last_name IS NOT NULL)
	                        THEN bc.last_name
                        ELSE '' END)) AS billing_contact_name,
       bc.email AS billing_contact_email,
       bc.phone AS billing_contact_phone,
       t.name AS tenant_name,
       (CASE
	       WHEN (c.task_group_id IS NULL AND c.company_active)
		       THEN 'active'
	       ELSE
		       (CASE
			       WHEN (c.task_group_id IS NOT NULL AND (cts.completedTasks = cts.totalTasks OR
			                                              (cts.completedTasks IS NULL AND cts.totalTasks IS NULL)) AND
			             c.company_active)
				       THEN 'active'
			       ELSE
				       (CASE
					       WHEN (c.task_group_id IS NOT NULL AND cts.completedTasks != cts.totalTasks AND
					             c.company_active)
						       THEN 'onboarding'
					       ELSE 'inactive' END) END) END) AS status,
       lct.value AS last_completed_task,
       nct.value AS next_task,
       nct.assigned_to AS next_task_assigned_to,
       (cts.totalTasks - cts.completedTasks) AS remaining_tasks,
       ROUND((CASE
	       WHEN c.task_group_id IS NULL OR (cts.completedTasks IS NULL AND cts.totalTasks IS NULL)
		       THEN 1
	       ELSE (cts.completedTasks / cts.totalTasks) END) * 100) AS progress_percentage
FROM company c
     LEFT JOIN contact bc ON bc.company_id = c.company_id AND bc.contact_type = 'BILLING'
     LEFT JOIN (SELECT ct.company_id,
                       COUNT(*) AS totalTasks,
                       SUM(CASE WHEN ct.complete_date IS NOT NULL THEN 1 ELSE 0 END) AS completedTasks,
                       MAX(ct.complete_date) AS last_completed_task_date
                FROM company_task ct
                GROUP BY ct.company_id) cts ON cts.company_id = c.company_id
     LEFT JOIN company_task lct
               ON lct.company_id = c.company_id AND lct.company_task_id = (SELECT ct2.company_task_id
                                                                           FROM company_task ct2
                                                                           WHERE ct2.company_id = c.company_id
		                                                                         AND ct2.complete_date =
		                                                                             (SELECT MAX(complete_date)
		                                                                              FROM company_task ct3
		                                                                              WHERE ct3.company_id = c.company_id)
                                                                           ORDER BY ct2.company_id DESC
                                                                           LIMIT 1)
     LEFT JOIN (SELECT ct.*
                FROM company_task ct
                     INNER JOIN (SELECT MIN(mct.company_task_id) AS mcti, mct.company_id
                                 FROM company_task mct
                                 WHERE mct.complete_date IS NULL
                                 GROUP BY mct.company_id) ctn ON ctn.mcti = ct.company_task_id) nct
               ON nct.company_id = c.company_id
     JOIN v_tenant t ON t.tenant_id = c.tenant_id
