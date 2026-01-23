CREATE OR REPLACE VIEW v_manage_locations AS
SELECT o.order_id,
       l.location_id,
       c.company_name,
       c.company_id,
       c.client_id AS end_customer_client_id,
       pc.company_name AS parent_company_name,
       pc.company_id AS master_customer_id,
       pc.client_id AS parent_company_client_id,
       COALESCE((SELECT display_name FROM v_subject WHERE subject_id = o.provisioner), 'Unassigned') AS provisioner,
       COALESCE((SELECT display_name FROM v_subject WHERE subject_id = vertek_project_manager),
                'Unassigned') AS vertek_project_manager,
       o.client_project_manager,
       o.client_order_id,
       l.client_location_id,
       l.location_name,
       l.location_status,
       IFNULL(sv.count_services, 0) AS count_services,
       sv.services,
       l.progress_percentage,
       CONCAT(IFNULL(l.address_1, ''),
              IF(LENGTH(l.address_2), CONCAT(' ', l.address_2), ''),
              '\n',
              IFNULL(l.city, ''), ', ',
              IFNULL(l.state_province, ''), ' ',
              IFNULL(l.postal_code, '')
       ) AS address,
       l.address_1,
       l.address_2,
       l.city,
       l.state_province,
       l.postal_code,
       (SELECT mi.milestone_date
        FROM milestone_instance mi
             JOIN location_milestone_instance lmi ON mi.milestone_instance_id = lmi.milestone_instance_id
             JOIN milestone m ON mi.milestone_id = m.milestone_id
        WHERE lmi.location_id = l.location_id
		      AND m.milestone_code = 'COMPLETE') completion_date,
       IFNULL(vju.show_jeop_icon, 0) AS show_jeop_icon,
       l.tenant_id,
       l.version,
       l.active,
       l.record_source,
       l.client_location_info,
       l.client_location_type,
       IFNULL(vju.open_jeops, '') AS open_jeops,
       (SELECT GROUP_CONCAT(DISTINCT ji.responsibility SEPARATOR ', ')
        FROM location_jeop_instance lji
             INNER JOIN jeop_instance ji ON lji.jeop_instance_id = ji.jeop_instance_id
        WHERE lji.location_id = l.location_id
		      AND ji.end_date IS NULL) AS open_jeop_responsibilites,
       IFNULL(sv.mac_count, 0) AS mac_count,
       (SELECT milestone_name
        FROM v_location_milestone_instance lmi
        WHERE lmi.location_id = l.location_id
        ORDER BY milestone_date DESC, milestone_instance_id DESC
        LIMIT 1) AS greatest_milestone_name,
       (SELECT milestone_date
        FROM v_location_milestone_instance lmi
        WHERE lmi.location_id = l.location_id
        ORDER BY milestone_date DESC, milestone_instance_id DESC
        LIMIT 1) AS greatest_milestone_date,
       (SELECT vju.jeop_description
        FROM v_jeops_union vju
        WHERE vju.end_date IS NULL
		      AND vju.location_id = l.location_id
        ORDER BY vju.jeop_instance_id DESC
        LIMIT 1) AS open_jeop,
       SUBSTRING((SELECT note FROM note WHERE note_id = latest_note.note_id), 1, 500) AS latest_note,
       sv.mrc AS mrc,
       sv.annual_recurring_cost AS annual_recurring_cost,
       sv.nrc AS nrc,
       sv.mrr AS mrr,
       sv.nrr AS nrr,
       IF(sv.count_linked_services > 0, TRUE, FALSE) AS show_linked_icon,
       IF(sv.count_bundled_services > 0, TRUE, FALSE) AS show_bundled_icon,
       IF(disconnect_count > 0, TRUE, FALSE) AS show_open_disconnect_icon,
       IF(mac_count > 0, TRUE, FALSE) AS show_open_mac_icon
FROM company C
     JOIN orders o
          ON C.company_id = o.company_id
     JOIN location l ON o.order_id = l.order_id
     LEFT JOIN company pc ON C.master_customer_id = pc.company_id
     LEFT JOIN v_subject vsm ON vsm.subject_id = vertek_project_manager
     LEFT JOIN (SELECT s.location_id,
                       COUNT(s.service_id) AS count_services,
                       SUM(s.active = TRUE) AS count_active_services,
                       SUM(s.active = FALSE) AS count_inactive_services,
                       SUM(s.service_mrc) AS mrc,
                       SUM(s.service_nrc) AS nrc,
                       SUM(s.service_mrr) AS mrr,
                       SUM(s.service_nrr) AS nrr,
                       SUM(s.annual_recurring_cost) AS annual_recurring_cost,
                       GROUP_CONCAT(DISTINCT s.service_type) AS services,
                       COUNT(CASE WHEN s.order_type IN ('Move', 'Add', 'Change') THEN 1 END) AS mac_count,
                       SUM(CASE WHEN (s.linked) THEN 1 ELSE 0 END) AS count_linked_services,
                       SUM(CASE WHEN (s.bundled) THEN 1 ELSE 0 END) AS count_bundled_services,
                       SUM(CASE WHEN (s.order_type LIKE '%Disconnect%') THEN 1 ELSE 0 END) AS disconnect_count
                FROM service s
                WHERE s.service_status != 'Service Cancelled'
		              AND s.record_source != 'Inventory Import'
                GROUP BY s.location_id) sv ON l.location_id = sv.location_id
     LEFT JOIN (SELECT location_id,
                       GROUP_CONCAT(DISTINCT level_jeop) AS open_jeops,
                       COUNT(*) AS show_jeop_icon
                FROM v_jeops_union
                WHERE jeop_level IN ('Order', 'Location')
		              AND end_date IS NULL
                GROUP BY location_id) vju ON l.location_id = vju.location_id
     LEFT JOIN (SELECT MAX(n.note_id) AS note_id, location_id
                FROM note n
                     JOIN location_note ln ON n.note_id = ln.note_id
                GROUP BY location_id) latest_note ON latest_note.location_id = l.location_id
WHERE l.current_inventory = FALSE and l.marked_for_deletion = FALSE;
