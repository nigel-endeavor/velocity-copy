CREATE OR REPLACE VIEW v_manage_locations AS
SELECT o.order_id,
       l.location_id,
       company_name,
       provisioner,
       o.client_order_id,
       l.client_location_id,
       l.location_name,
       l.location_status,
       sv.count_services,
       sv.services,
       NULL AS completion_date,
       20 AS progress,
       l.tenant_id,
       l.version,
       (select GROUP_CONCAT(distinct level_jeop) from v_jeops_union  v where v.end_date is null and l.location_id = v.location_id) open_jeops
FROM company c
         JOIN orders o ON c.company_id = o.company_id
         JOIN location l ON o.order_id = l.order_id
         LEFT JOIN (SELECT s.location_id,
                           COUNT(s.service_id) AS count_services,
                           CONCAT(
                                   CASE WHEN count(bs.service_id) > 0 THEN
                                            CASE WHEN count(bs.service_id) > 1 THEN CONCAT('Broadband(', count(bs.service_id), ')')
                                                 ELSE 'Broadband' END
                                        ELSE '' END,
                                   CASE WHEN count(bs.service_id) > 0 AND count(ds.service_id) > 0 THEN ',' ELSE '' END,
                                   CASE WHEN count(ds.service_id) > 0 THEN
                                            CASE WHEN count(ds.service_id) > 1 THEN CONCAT('DIA(', count(ds.service_id), ')')
                                                 ELSE 'DIA' END
                                        ELSE '' END
                               ) as services
                    FROM service s
                             LEFT JOIN broadband_service bs ON s.service_id = bs.service_id
                             LEFT JOIN dia_service ds ON s.service_id = ds.service_id
                    GROUP BY location_id) sv ON l.location_id = sv.location_id
         LEFT JOIN (SELECT order_id, location_id, GROUP_CONCAT(distinct level_jeop) AS open_jeops
                    FROM v_jeops_union
                    GROUP BY order_id, location_id) vju ON l.location_id = vju.location_id

;

