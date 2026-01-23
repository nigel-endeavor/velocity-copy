CREATE OR REPLACE VIEW v_manage_locations AS
SELECT
    o.order_id,
    l.location_id,
    c.company_name,
    c.company_id,
    pc.company_name AS parent_company_name,
    pc.company_id AS parent_company_id,
    COALESCE(vs.display_name, 'Unassigned') AS provisioner,
    client_project_manager,
    vsm.display_name AS vertek_project_manager,
    o.client_order_id,
    l.client_location_id,
    l.client_location_info,
    l.client_location_type,
    l.location_name,
    l.location_status,
    sv.count_services,
    sv.count_active_services,
    sv.count_inactive_services,
    sv.services,
    l.progress_percentage,
    CONCAT(a.address_1,
           IF(LENGTH(a.address_2), CONCAT('\n', a.address_2), ''),
           '\n', a.city, ', ', a.state_province, ' ', a.postal_code) AS address,
    a.address_1,
    a.address_2,
    a.city,
    a.state_province,
    a.postal_code,
    NULL AS completion_date,
    20 AS progress,
    IFNULL(vju.show_jeop_icon, 0) AS show_jeop_icon,
    l.tenant_id,
    l.version,
    l.active,
    IFNULL(vju.open_jeops, '') AS open_jeops,
    IFNULL(complete_service.count_services_complete, 0) AS count_services_complete,
    IFNULL(cancelled_service.count_services_cancelled, 0) AS count_services_cancelled,
    IFNULL(change_in_assignment_service.count_services_change_in_assignment, 0) AS count_services_change_in_assignment,
    IFNULL(complete_service.active_complete_mrc, 0) AS active_complete_mrc,
    IFNULL(complete_service.active_complete_nrc, 0) AS active_complete_nrc,
    IFNULL(open_dispute.count_open_disputes, 0) AS count_open_disputes,
    IFNULL(open_dispute.open_dispute_mrc, 0) AS open_dispute_mrc,
    IFNULL(open_dispute.open_dispute_nrc, 0) AS open_dispute_nrc,
    0 AS macd_count,
    CASE WHEN l.active = true THEN 'Active' ELSE 'Inactive' END AS active_inactive,
    CASE WHEN l.inventory_added_date IS NOT NULL
             THEN l.inventory_added_date
         ELSE (SELECT MIN(mi.milestone_date) FROM milestone_instance mi WHERE mi.milestone_instance_id IN (
             SELECT milestone_instance_id FROM service_milestone_instance smi WHERE smi.service_id IN (
                 SELECT service_id FROM service s WHERE s.location_id = l.location_id))
                                                                          AND mi.milestone_id = (SELECT milestone_id from milestone where milestone_code = 'COMPLETE')) END as inventory_added_date
FROM company c
         JOIN orders o ON c.company_id = o.company_id
         JOIN location l ON o.order_id = l.order_id
         LEFT JOIN company pc ON c.parent_company_id = pc.company_id
         LEFT JOIN v_subject vs ON vs.subject_id = provisioner
         LEFT JOIN v_subject vsm ON vsm.subject_id = vertek_project_manager
         LEFT JOIN (
    SELECT
        s.location_id,
        COUNT(s.service_id) AS count_services,
        SUM(s.active = true) AS count_active_services,
        SUM(s.active = false) AS count_inactive_services,
        CONCAT_WS(',',
                  CASE WHEN COUNT(bs.service_id) > 0 THEN 'Broadband' END,
                  CASE WHEN COUNT(ds.service_id) > 0 THEN 'DIA' END,
                  CASE WHEN COUNT(us.service_id) > 0 THEN 'UCaaS' END,
                  CASE WHEN COUNT(gs.service_id) > 0 THEN '4G/5G' END
            ) AS services
    FROM service s
             LEFT JOIN broadband_service bs ON s.service_id = bs.service_id
             LEFT JOIN dia_service ds ON s.service_id = ds.service_id
             LEFT JOIN ucaas_service us ON s.service_id = us.service_id
             LEFT JOIN `4g5g_service` gs ON s.service_id = gs.service_id
    WHERE s.service_status != 'Service Cancelled'
    GROUP BY s.location_id
) sv ON l.location_id = sv.location_id
         LEFT JOIN (
    SELECT
        location_id,
        GROUP_CONCAT(DISTINCT level_jeop) AS open_jeops,
        COUNT(*) AS show_jeop_icon
    FROM v_jeops_union
    WHERE jeop_level IN ('Order', 'Location') AND end_date IS NULL
    GROUP BY location_id
) vju ON l.location_id = vju.location_id
    LEFT JOIN (
        SELECT
            location_id,
            COUNT(*) AS count_services_complete,
            SUM(service_mrc) AS active_complete_mrc,
            SUM(service_nrc) AS active_complete_nrc
        FROM service
        WHERE service_status = 'Service Complete' AND active = true
        GROUP BY location_id
    ) complete_service ON l.location_id = complete_service.location_id
    LEFT JOIN (
        SELECT
            location_id,
            COUNT(*) AS count_services_cancelled
        FROM service
        WHERE service_status = 'Service Cancelled'
        GROUP BY location_id
    ) cancelled_service ON l.location_id = cancelled_service.location_id
    LEFT JOIN (
        SELECT
            location_id,
            COUNT(*) AS count_services_change_in_assignment
        FROM service
        WHERE service_status = 'Change in Assignment'
        GROUP BY location_id
    ) change_in_assignment_service ON l.location_id = change_in_assignment_service.location_id
    LEFT JOIN (
        SELECT
            l.location_id,
            COUNT(*) AS count_open_disputes,
            SUM(amount_disputed_mrc) AS open_dispute_mrc,
            SUM(amount_disputed_nrc) AS open_dispute_nrc
        FROM dispute
        JOIN service s ON dispute.service_id = s.service_id
        JOIN location l ON s.location_id = l.location_id
        WHERE dispute_status != 'Dispute Closed'
        GROUP BY l.location_id
    ) open_dispute ON l.location_id = open_dispute.location_id
    LEFT JOIN qto.address a ON l.address_id = a.address_id;