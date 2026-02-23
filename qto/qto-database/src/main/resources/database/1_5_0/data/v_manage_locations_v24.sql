DROP VIEW IF EXISTS v_manage_locations CASCADE;
CREATE OR REPLACE VIEW v_manage_locations AS
SELECT
    o.order_id,
    l.location_id,
    c.company_name,
    c.company_id,
    pc.company_name AS parent_company_name,
    pc.company_id AS master_customer_id,
    COALESCE(vs.display_name, 'Unassigned') AS provisioner,
    o.client_order_id,
    l.client_location_id,
    l.location_name,
    l.location_status,
    IFNULL(sv.count_services, 0) as count_services,
    sv.services,
    l.progress_percentage,
    CONCAT(a.address_1,
           CASE WHEN a.address_2 IS NOT NULL AND TRIM(COALESCE(a.address_2,'')) <> '' THEN CONCAT(E'\n', a.address_2) ELSE '' END,
           '\n', a.city, ', ', a.state_province, ' ', a.postal_code) AS address,
    a.address_1,
    a.address_2,
    a.city,
    a.state_province,
    a.postal_code,
    (SELECT mi.milestone_date
         FROM milestone_instance mi
         JOIN location_milestone_instance lmi ON mi.milestone_instance_id = lmi.milestone_instance_id
         JOIN milestone m ON mi.milestone_id = m.milestone_id
         WHERE lmi.location_id = l.location_id AND m.milestone_code = 'COMPLETE') completion_date,
    IFNULL(vju.show_jeop_icon, 0) AS show_jeop_icon,
    l.tenant_id,
    l.version,
    l.active,
    IFNULL(vju.open_jeops, '') AS open_jeops,
    IFNULL(sv.mac_count, 0) AS mac_count
FROM company c
         JOIN orders o ON c.company_id = o.company_id
         JOIN location l ON o.order_id = l.order_id
         LEFT JOIN company pc ON c.master_customer_id = pc.company_id
         LEFT JOIN address a ON l.address_id = a.address_id
         LEFT JOIN v_subject vs ON vs.subject_id = provisioner
         LEFT JOIN v_subject vsm ON vsm.subject_id = vertek_project_manager
         LEFT JOIN (
            SELECT
                s.location_id,
                COUNT(s.service_id) AS count_services,
                SUM(s.active = true) AS count_active_services,
                SUM(s.active = false) AS count_inactive_services,
                string_agg(DISTINCT s.service_type, ',') as services,
                COUNT(CASE WHEN s.order_type in ('Move', 'Add', 'Change') THEN 1 END) AS mac_count
            FROM service s
            WHERE s.service_status != 'Service Cancelled'
            GROUP BY s.location_id
        ) sv ON l.location_id = sv.location_id
         LEFT JOIN (
            SELECT
                location_id,
                string_agg(DISTINCT level_jeop, ',') AS open_jeops,
                COUNT(*) AS show_jeop_icon
            FROM v_jeops_union
            WHERE jeop_level IN ('Order', 'Location') AND end_date IS NULL
            GROUP BY location_id
        ) vju ON l.location_id = vju.location_id;