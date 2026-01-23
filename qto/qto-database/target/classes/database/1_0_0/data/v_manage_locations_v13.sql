CREATE OR REPLACE VIEW v_manage_locations AS
SELECT o.order_id,
       l.location_id,
       c.company_name,
       c.company_id,
       pc.company_name AS parent_company_name,
       pc.company_id AS parent_company_id,
       COALESCE((select display_name from platform.subject where subject_id = provisioner), 'Unassigned') as provisioner,
       client_project_manager,
       (select display_name from platform.subject where subject_id = vertek_project_manager) as vertek_project_manager,
       o.client_order_id,
       l.client_location_id,
       l.location_name,
       l.location_status,
       sv.count_services,
       sv.services,
       l.progress_percentage,
       CONCAT(a.address_1,
              IF(LENGTH(a.address_2), CONCAT('\n', a.address_2), ''),
              '\n', a.city, ', ', a.state_province, ' ', a.postal_code
           ) AS address,
       a.address_1, a.address_2, a.city, a.state_province, a.postal_code,
       NULL AS completion_date,
       20 AS progress,
       IF((select count(*)
           from v_jeops_union vju2
           where vju2.location_id = l.location_id and jeop_level in ('Order', 'Location') and vju2.end_date is null), 1, 0) as show_jeop_icon,
       l.tenant_id,
       l.version,
       l.active,
       (select GROUP_CONCAT(distinct level_jeop) from v_jeops_union  v where v.end_date is null and l.location_id = v.location_id) open_jeops
FROM company c
         JOIN orders o ON c.company_id = o.company_id
         JOIN location l ON o.order_id = l.order_id
         LEFT JOIN company pc ON c.parent_company_id = pc.company_id
         LEFT JOIN (SELECT s.location_id,
                           COUNT(s.service_id) AS count_services,
                           CONVERT(CONCAT(
                                   CASE WHEN count(bs.service_id) > 0 THEN
                                            CASE WHEN count(bs.service_id) > 1 THEN CONCAT('Broadband(', count(bs.service_id), ')')
                                                 ELSE 'Broadband' END
                                        ELSE '' END,
                                   CASE WHEN count(bs.service_id) > 0 AND count(ds.service_id) > 0 THEN ',' ELSE '' END,
                                   CASE WHEN count(ds.service_id) > 0 THEN
                                            CASE WHEN count(ds.service_id) > 1 THEN CONCAT('DIA(', count(ds.service_id), ')')
                                                 ELSE 'DIA' END
                                        ELSE '' END,
                                   CASE WHEN (count(bs.service_id) > 0 or count(ds.service_id) > 0) and count(us.service_id) THEN ',' ELSE '' END,
                                   CASE WHEN count(us.service_id) > 0 THEN
                                            CASE WHEN count(us.service_id) > 1 THEN CONCAT('UCaaS(', count(us.service_id), ')')
                                                 ELSE 'UCaaS' END
                                        ELSE '' END,
                                   CASE WHEN (count(bs.service_id) > 0 or count(ds.service_id) > 0 or count(us.service_id) > 0) and count(gs.service_id) > 0 THEN ',' ELSE '' END,
                                   CASE WHEN count(gs.service_id) > 0 THEN
                                            CASE WHEN count(gs.service_id) > 1 THEN CONCAT('4G/5G(', count(gs.service_id), ')')
                                                 ELSE '4G/5G' END
                                        ELSE '' END
                               ), char(200)) as services
                    FROM service s
                             LEFT JOIN broadband_service bs ON s.service_id = bs.service_id
                             LEFT JOIN dia_service ds ON s.service_id = ds.service_id
                    LEFT JOIN ucaas_service us ON s.service_id = us.service_id
                    left JOIN `4g5g_service` gs ON s.service_id = gs.service_id
                    WHERE s.service_status != 'Service Cancelled'
                    GROUP BY location_id) sv ON l.location_id = sv.location_id
         LEFT JOIN (SELECT order_id, location_id, GROUP_CONCAT(distinct level_jeop) AS open_jeops
                    FROM v_jeops_union
                    GROUP BY order_id, location_id) vju ON l.location_id = vju.location_id
left join qto.address a on l.address_id = a.address_id;
