DROP VIEW IF EXISTS v_manage_location_inventory CASCADE;
CREATE OR REPLACE VIEW v_manage_location_inventory AS
SELECT
    o.order_id,
    l.location_id,
    c.company_name,
    c.company_id,
    pc.company_name AS parent_company_name,
    pc.company_id AS master_customer_id,
    COALESCE(vs.display_name, 'Unassigned') AS provisioner,
    client_project_manager,
    vsm.display_name AS vertek_project_manager,
    o.client_order_id,
    l.client_location_id,
    l.client_location_info,
    l.client_location_type,
    l.location_name,
    l.location_status,
    IFNULL(sv.count_services, 0) as count_services,
    sv.count_active_services,
    sv.count_inactive_services,
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
    l.tenant_id,
    l.version,
    l.active,
    IFNULL(sv.count_services_complete, 0) AS count_services_complete,
    IFNULL(cancelled_service.count_services_cancelled, 0) AS count_services_cancelled,
    IFNULL(change_in_assignment_service.count_services_change_in_assignment, 0) AS count_services_change_in_assignment,
    IFNULL(sv.active_complete_mrc, 0) AS active_complete_mrc,
    IFNULL(sv.active_complete_nrc, 0) AS active_complete_nrc,
    IFNULL(open_dispute.count_open_disputes, 0) AS count_open_disputes,
    IFNULL(open_dispute.open_dispute_mrc, 0) AS open_dispute_mrc,
    IFNULL(open_dispute.open_dispute_nrc, 0) AS open_dispute_nrc,
    IFNULL(sv.macd_count, 0) as macd_count,
    CASE WHEN l.active = true THEN 'Active' ELSE 'Inactive' END AS active_inactive,
    sv.inventory_added_date as inventory_added_date,
    sv.sub_order_types,
    IF(sv.child_order_types LIKE '%Disconnect%', true, false) AS show_open_disconnect_icon,
    IF(sv.child_order_types LIKE '%Move%'
           OR sv.child_order_types LIKE '%Add%'
           OR sv.child_order_types LIKE '%Change%', true, false) AS show_open_mac_icon,
    IF(open_dispute.count_open_disputes > 0, true, false) AS show_open_dispute_icon
FROM company c
         JOIN orders o ON c.company_id = o.company_id
         JOIN location l ON o.order_id = l.order_id
         LEFT JOIN address a ON l.address_id = a.address_id
         LEFT JOIN company pc ON c.master_customer_id = pc.company_id
         LEFT JOIN v_subject vs ON vs.subject_id = provisioner
         LEFT JOIN v_subject vsm ON vsm.subject_id = vertek_project_manager
         LEFT JOIN (
    SELECT
        ils.inventory_location_id,
        COUNT(s.service_id) AS count_services,
        SUM(s.active = true) AS count_active_services,
        SUM(s.active = false) AS count_inactive_services,
        SUM((CHAR_LENGTH(child_services.child_ids) - CHAR_LENGTH(REPLACE(child_services.child_ids, ',', '')) + 1)) as macd_count,
        string_agg(child_services.child_order_types, ',') as child_order_types,
        CONCAT_WS(',',
                  CASE WHEN COUNT(bs.service_id) > 0 THEN 'Broadband' END,
                  CASE WHEN COUNT(ds.service_id) > 0 THEN 'DIA' END,
                  CASE WHEN COUNT(us.service_id) > 0 THEN 'UCaaS' END,
                  CASE WHEN COUNT(gs.service_id) > 0 THEN '4G/5G' END
            ) AS services,
        string_agg(DISTINCT s.sub_order_type, ',') AS sub_order_types,
        MIN(vsmi.milestone_date) as inventory_added_date,
        SUM(case when (vsmi.milestone_date is not null) then service_mrc else 0 end) as active_complete_mrc,
        SUM(case when (vsmi.milestone_date is not null) then service_nrc else 0 end) as active_complete_nrc,
        SUM(case when (vsmi.milestone_date is not null) then 1 else 0 end) as count_services_complete
    FROM inventory_location_service ils
             JOIN service s ON ils.service_id = s.service_id
             LEFT JOIN broadband_service bs ON s.service_id = bs.service_id
             LEFT JOIN dia_service ds ON s.service_id = ds.service_id
             LEFT JOIN ucaas_service us ON s.service_id = us.service_id
             LEFT JOIN `4g5g_service` gs ON s.service_id = gs.service_id
             LEFT JOIN (
        SELECT service_id, MIN(milestone_date) as milestone_date
        FROM v_service_milestone_instance
        WHERE milestone_code IN ('COMPLETE', 'DATA_PROVISIONING_COMPLETE')
        GROUP BY service_id
    ) vsmi ON s.service_id = vsmi.service_id
             LEFT JOIN (
        SELECT
            parent_service_id,
            string_agg(service_id, ',') as child_ids,
            string_agg(order_type, ',') as child_order_types
        FROM service
        WHERE parent_service_id IS NOT NULL and service_status not in ('Service Complete', 'Disconnect Complete', 'Service Cancelled', 'Change in Assignment')
        GROUP BY parent_service_id
    ) child_services ON s.service_id = child_services.parent_service_id
    WHERE s.service_status != 'Service Cancelled'
    GROUP BY ils.inventory_location_id
) sv ON l.location_id = sv.inventory_location_id
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
        ils.inventory_location_id as location_id,
        COUNT(*) AS count_open_disputes,
        SUM(amount_disputed_mrc) AS open_dispute_mrc,
        SUM(amount_disputed_nrc) AS open_dispute_nrc
    FROM dispute
             JOIN service s ON dispute.service_id = s.service_id
             JOIN inventory_location_service ils ON s.service_id = ils.service_id
    WHERE dispute_status != 'Dispute Closed'
    GROUP BY ils.inventory_location_id
) open_dispute ON l.location_id = open_dispute.location_id
WHERE l.location_id in (select inventory_location_id from inventory_location_service);