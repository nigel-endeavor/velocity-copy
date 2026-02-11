CREATE OR REPLACE VIEW v_manage_service_inventory AS
SELECT s.service_id,
       l.location_id,
       s.order_type,
       l.client_location_id,
       CONCAT(a.address_1,
              IF(LENGTH(a.address_2), CONCAT('\n', a.address_2), ''),
              '\n', a.city, ', ', a.state_province, ' ', a.postal_code
           ) AS address,
       a.address_1, a.address_2, a.city, a.state_province, a.postal_code,
       l.order_id,
       s.service_status,
       COALESCE((select display_name from v_subject where subject_id = provisioner), 'Unassigned') as provisioner,
       o.client_project_manager AS project_manager,
       s.provider,
       s.provider_site_account_num,
       s.provider_circuit_id,
       s.client_service_id,
       s.service_mrc,
       s.service_nrc,
       s.annual_recurring_cost,
       s.contract_signed_date,
       s.contract_term,
       s.circuit_term_end_date,
       (SELECT MIN(milestone_date)
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
          AND (smi.milestone_code = 'COMPLETE' OR smi.milestone_code = 'DATA_PROVISIONING_COMPLETE')) as inventory_added_date,
       l.client_location_type,
       l.client_location_info,
       c.company_name,
       c.company_id,
       pc.company_name as parent_company_name,
       pc.company_id as master_customer_id,
       CONCAT(COALESCE(s.download_speed, ''), '/', COALESCE(s.upload_speed, '')) AS speed,
       s.service_type,
       s.version,
       s.tenant_id,
       s.active,
       s.billable,
       s.has_icb,
       CASE WHEN s.active = true THEN 'Active' ELSE 'Inactive' END                                                                    AS active_inactive,
       IFNULL(open_dispute.count_open_disputes, 0)                                                                                    AS count_open_disputes,
       IFNULL(open_dispute.open_dispute_mrc, 0)                                                                                       AS open_dispute_mrc,
       IFNULL(open_dispute.open_dispute_nrc, 0)                                                                                       AS open_dispute_nrc,
       open_dispute.dispute_types                                                                                                     as dispute_types,
       CASE WHEN s.order_type in ('Move', 'Add', 'Change', 'Disconnect') AND s.service_status != 'Service Complete' THEN 1 ELSE 0 END AS is_macd,
       s.sub_order_type,
       child_services.child_ids,
       child_services.child_order_types,
       child_services.child_sub_order_types,
       IF(child_services.child_order_types LIKE '%Disconnect%', true, false) AS show_open_disconnect_icon,
       IF(child_services.child_order_types LIKE '%Move%'
              OR child_services.child_order_types LIKE '%Add%'
              OR child_services.child_order_types LIKE '%Change%', true, false) AS show_open_mac_icon,
       IF(open_dispute.count_open_disputes > 0, true, false) AS show_open_dispute_icon
FROM service s
    JOIN inventory_location_service ils on s.service_id = ils.service_id
    JOIN location l ON ils.inventory_location_id = l.location_id
    LEFT OUTER JOIN location_contact lc on l.location_id = lc.location_id
    LEFT OUTER JOIN contact lcon on lc.contact_id = lcon.contact_id
    JOIN orders o ON l.order_id = o.order_id
    JOIN company c ON o.company_id = c.company_id
    LEFT JOIN company pc ON c.master_customer_id = pc.company_id
    LEFT JOIN address a ON l.address_id = a.address_id
    LEFT JOIN (
        SELECT
            s.service_id,
            MIN(CASE WHEN vsmi.milestone_code = 'COMPLETE' OR vsmi.milestone_code = 'DATA_PROVISIONING_COMPLETE' THEN vsmi.milestone_date END) as inventory_added_date
        FROM v_service_milestone_instance vsmi
                 INNER JOIN service s ON vsmi.service_id = s.service_id
        GROUP BY s.service_id
    ) vsmi ON s.service_id = vsmi.service_id
    LEFT JOIN (
        SELECT
            s.service_id,
            COUNT(*) AS count_open_disputes,
            SUM(amount_disputed_mrc) AS open_dispute_mrc,
            SUM(amount_disputed_nrc) AS open_dispute_nrc,
            GROUP_CONCAT(dispute_type) as dispute_types
        FROM dispute
                 JOIN service s ON dispute.service_id = s.service_id
        WHERE dispute_status != 'Dispute Closed'
        GROUP BY s.service_id
    ) open_dispute ON s.service_id = open_dispute.service_id
    LEFT JOIN (
        SELECT
            parent_service_id,
            GROUP_CONCAT(service_id) as child_ids,
            GROUP_CONCAT(order_type) as child_order_types,
            GROUP_CONCAT(sub_order_type) as child_sub_order_types
        FROM service
        WHERE parent_service_id IS NOT NULL and service_status not in ('Service Complete', 'Disconnect Complete', 'Service Cancelled', 'Change in Assignment')
        GROUP BY parent_service_id
    ) child_services ON s.service_id = child_services.parent_service_id;
