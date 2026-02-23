CREATE OR REPLACE VIEW v_manage_location_inventory AS
SELECT
    o.order_id,
    l.location_id,
    c.company_name,
    c.company_id,
    c.client_id AS end_customer_client_id,
    pc.company_name AS parent_company_name,
    pc.company_id AS master_customer_id,
    pc.client_id AS parent_company_client_id,
    COALESCE(vs.display_name, 'Unassigned') AS provisioner,
    client_project_manager,
    vsm.display_name AS vertek_project_manager,
    o.client_order_id,
    l.client_location_id,
    l.client_location_info,
    l.client_location_type,
    l.location_name,
    l.location_status,
    IFNULL(sv.count_services, 0) AS count_services,
    sv.count_active_services,
    sv.count_inactive_services,
    sv.services,
    l.progress_percentage,
       CONCAT(IFNULL(l.address_1, ''),
              CASE WHEN l.address_2 IS NOT NULL AND TRIM(COALESCE(l.address_2,'')) <> '' THEN CONCAT(' ', l.address_2) ELSE '' END,
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
    l.tenant_id,
    l.version,
    l.active,
    IFNULL(sv.count_services_complete, 0) AS count_services_complete,
    IFNULL(sv_dead.count_services_cancelled, 0) AS count_services_cancelled,
    IFNULL(sv_dead.count_services_change_in_assignment, 0) AS count_services_change_in_assignment,
    IFNULL(sv.active_complete_mrc, 0) AS active_complete_mrc,
    IFNULL(sv.active_complete_nrc, 0) AS active_complete_nrc,
    IFNULL(sv.active_complete_mrr, 0) AS active_complete_mrr,
    IFNULL(sv.active_complete_nrr, 0) AS active_complete_nrr,
    IFNULL(sv.annual_recurring_cost, 0) AS annual_recurring_cost,
    IFNULL(open_dispute.count_open_disputes, 0) AS count_open_disputes,
    IFNULL(open_dispute.open_dispute_mrc, 0) AS open_dispute_mrc,
    IFNULL(open_dispute.open_dispute_nrc, 0) AS open_dispute_nrc,
    IFNULL(sv.macd_count, 0) AS macd_count,
    CASE WHEN l.active = true THEN 'Active' ELSE 'Inactive' END AS active_inactive,

    (SELECT MIN(milestone_date)
     FROM v_location_milestone_instance lmi
     WHERE lmi.location_id = l.location_id
       AND lmi.milestone_code = 'CREATED') AS inventory_added_date,
    sv.sub_order_types,
    IF(sv.child_order_types LIKE '%Disconnect%', true, false) AS show_open_disconnect_icon,
    IF(
            sv.child_order_types LIKE '%Move%'
                OR sv.child_order_types LIKE '%Add%'
                OR sv.child_order_types LIKE '%Change%', true, false
    ) AS show_open_mac_icon,
    IF(open_dispute.count_open_disputes > 0, true, false) AS show_open_dispute_icon,
    IF(sv.count_linked_services > 0, true, false) AS show_linked_icon,
    IF(sv.count_bundled_services > 0, true, false) AS show_bundled_icon
FROM
    company c
        JOIN orders o ON c.company_id = o.company_id
        JOIN location l ON o.order_id = l.order_id
        LEFT JOIN company pc ON c.master_customer_id = pc.company_id
        LEFT JOIN v_subject vs ON vs.subject_id = o.provisioner
        LEFT JOIN v_subject vsm ON vsm.subject_id = vertek_project_manager
        LEFT JOIN (
        SELECT
            l.location_id,
            COUNT(s.service_id) AS count_services,
            SUM(s.active = true) AS count_active_services,
            SUM(s.active = false) AS count_inactive_services,
            SUM(
                    (CHAR_LENGTH(child_services.child_ids) - CHAR_LENGTH(REPLACE(child_services.child_ids, ',', '')) + 1)
            ) AS macd_count,
            GROUP_CONCAT(child_services.child_order_types) AS child_order_types,
            GROUP_CONCAT(DISTINCT s.service_type) AS services,
            GROUP_CONCAT(DISTINCT s.sub_order_type) AS sub_order_types,
            SUM(
                    CASE WHEN (vsmi.milestone_date IS NOT NULL) THEN service_mrc ELSE 0 END
            ) AS active_complete_mrc,
            SUM(
                    CASE WHEN (vsmi.milestone_date IS NOT NULL) THEN service_nrc ELSE 0 END
            ) AS active_complete_nrc,
            SUM(
                    CASE WHEN (vsmi.milestone_date IS NOT NULL) THEN service_mrr ELSE 0 END
            ) AS active_complete_mrr,
            SUM(
                    CASE WHEN (vsmi.milestone_date IS NOT NULL) THEN service_nrr ELSE 0 END
            ) AS active_complete_nrr,
            SUM(
                    CASE WHEN (vsmi.milestone_date IS NOT NULL) THEN annual_recurring_cost ELSE 0 END
            ) AS annual_recurring_cost,
            SUM(
                    CASE WHEN (vsmi.milestone_date IS NOT NULL) THEN 1 ELSE 0 END
            ) AS count_services_complete,
            SUM(
                    CASE WHEN (s.linked) THEN 1 ELSE 0 END
            ) AS count_linked_services,
            SUM(
                    CASE WHEN (s.bundled) THEN 1 ELSE 0 END
            ) AS count_bundled_services
        FROM
            service s
                LEFT JOIN location l on s.location_id = l.location_id
                LEFT JOIN (
                SELECT service_id, MIN(milestone_date) AS milestone_date
                FROM v_service_milestone_instance
                WHERE milestone_code IN ('COMPLETE', 'DATA_PROVISIONING_COMPLETE')
                GROUP BY service_id
            ) vsmi ON s.service_id = vsmi.service_id
                LEFT JOIN (
                SELECT
                    parent_service_id,
                    GROUP_CONCAT(service_id) AS child_ids,
                    GROUP_CONCAT(order_type) AS child_order_types
                FROM service
                WHERE parent_service_id IS NOT NULL
                  AND service_status NOT IN ('Service Complete', 'Disconnect Complete', 'Service Cancelled', 'Change in Assignment')
                  AND order_type != 'New'
                GROUP BY parent_service_id
            ) child_services ON s.service_id = child_services.parent_service_id
        WHERE s.service_status != 'Service Cancelled'
        GROUP BY l.location_id
    ) sv ON l.location_id = sv.location_id
        LEFT JOIN (
        SELECT
            location_id,
            SUM(CASE WHEN service_status = 'Service Cancelled' THEN 1 ELSE 0 END) AS count_services_cancelled,
            SUM(CASE WHEN service_status = 'Change in Assignment' THEN 1 ELSE 0 END) AS count_services_change_in_assignment
        FROM service
        GROUP BY location_id
    ) sv_dead ON l.location_id = sv_dead.location_id
        LEFT JOIN (
        SELECT
            l.location_id AS location_id,
            COUNT(*) AS count_open_disputes,
            SUM(amount_disputed_mrc) AS open_dispute_mrc,
            SUM(amount_disputed_nrc) AS open_dispute_nrc
        FROM dispute
                 JOIN service s ON dispute.service_id = s.service_id
                 JOIN location l ON s.location_id = l.location_id
        WHERE dispute_status != 'Dispute Closed'
        GROUP BY l.location_id
    ) open_dispute ON l.location_id = open_dispute.location_id
WHERE
    l.current_inventory = true and l.marked_for_deletion = false;
