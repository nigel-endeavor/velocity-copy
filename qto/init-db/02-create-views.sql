-- PostgreSQL views for QTO application
-- Must be run AFTER Hibernate creates the base tables (ddl-auto: update).
-- Order matters: dependency views first, then feature views.

-- ============================================================================
-- DEPENDENCY VIEWS (used by feature views below)
-- ============================================================================

-- v_subject: maps subject_id to display info
DROP TABLE IF EXISTS v_subject CASCADE;
DROP VIEW IF EXISTS v_subject;
CREATE VIEW v_subject AS
SELECT s.subject_id,
       s.username,
       s.display_name,
       s.email_address,
       s.active,
       s.version
FROM platform.subject s;

-- v_tenant: maps tenant_id to tenant info
DROP TABLE IF EXISTS v_tenant CASCADE;
DROP VIEW IF EXISTS v_tenant;
CREATE VIEW v_tenant AS
SELECT t.tenant_id,
       t.name,
       t.active,
       t.version
FROM platform.tenant t;

-- v_service_milestone_instance: service milestone lookup
DROP TABLE IF EXISTS v_service_milestone_instance CASCADE;
DROP VIEW IF EXISTS v_service_milestone_instance;
CREATE VIEW v_service_milestone_instance AS
SELECT smi.service_id,
       mi.milestone_instance_id,
       mi.milestone_date,
       m.milestone_id,
       m.milestone_name,
       m.milestone_code
FROM service_milestone_instance smi
JOIN milestone_instance mi ON smi.milestone_instance_id = mi.milestone_instance_id
JOIN milestone m ON mi.milestone_id = m.milestone_id;

-- v_location_milestone_instance: location milestone lookup
DROP TABLE IF EXISTS v_location_milestone_instance CASCADE;
DROP VIEW IF EXISTS v_location_milestone_instance;
CREATE VIEW v_location_milestone_instance AS
SELECT lmi.location_id,
       mi.milestone_instance_id,
       mi.milestone_date,
       m.milestone_id,
       m.milestone_name,
       m.milestone_code
FROM location_milestone_instance lmi
JOIN milestone_instance mi ON lmi.milestone_instance_id = mi.milestone_instance_id
JOIN milestone m ON mi.milestone_id = m.milestone_id;

-- ============================================================================
-- FEATURE VIEWS
-- ============================================================================

-- v_manage_services: Service Worklist view
-- Joins service, location, orders, and company tables for the Service Worklist.
-- Backs the ServiceView JPA entity and /api/serviceViews REST endpoint.

-- Drop table if Hibernate auto-created it (ddl-auto: update creates TABLE, not VIEW)
DROP TABLE IF EXISTS v_manage_services CASCADE;
-- Drop view if it already exists
DROP VIEW IF EXISTS v_manage_services;

CREATE VIEW v_manage_services AS
SELECT
  s.service_id,
  s.location_id,
  s.order_type,
  l.client_location_id,
  CONCAT(
    COALESCE(l.address_1, ''),
    CASE WHEN LENGTH(COALESCE(l.address_2, '')) > 0 THEN ' ' || l.address_2 ELSE '' END,
    ', ', COALESCE(l.city, ''),
    ', ', COALESCE(l.state_province, ''),
    ' ', COALESCE(l.postal_code, '')
  ) AS address,
  l.address_1,
  l.address_2,
  l.city,
  l.state_province,
  l.postal_code,
  s.order_id,
  s.service_status,
  s.service_billed_to,
  s.service_sub_status,
  COALESCE(o.provisioner::text, 'Unassigned') AS provisioner,
  COALESCE(o.qa_manager::text, 'Unassigned') AS qa_manager,
  o.client_project_manager AS project_manager,
  s.provider,
  s.client_service_id,
  s.service_mrc,
  s.service_nrc,
  s.service_mrr,
  s.service_nrr,
  s.progress_percentage,
  s.project_name,
  s.record_source,
  NULL::timestamp AS customer_requested_install,
  NULL::timestamp AS site_survey_submit,
  NULL::timestamp AS site_survey_due,
  NULL::timestamp AS provider_order_submitted,
  NULL::timestamp AS network_provider_foc,
  NULL::timestamp AS data_provisioning_complete,
  NULL::timestamp AS created,
  NULL::timestamp AS qa_check_open,
  NULL::timestamp AS first_vendor_invoice,
  NULL::timestamp AS returned_to_order_group,
  NULL::timestamp AS returned_to_sales,
  NULL::timestamp AS billing_review_complete,
  NULL::timestamp AS access_circuit_foc,
  NULL::timestamp AS on_hold,
  s.follow_up_date,
  NULL::text AS greatest_milestone_name,
  NULL::timestamp AS greatest_milestone_date,
  l.client_location_type,
  l.client_location_info,
  NULL::text AS lcon_phone,
  l.level_of_effort,
  COALESCE(o.vertek_project_manager::text, 'Unassigned') AS vertek_project_manager,
  c.company_name,
  c.company_id AS company_id,
  pc.company_name AS parent_company_name,
  pc.company_id AS master_customer_id,
  CONCAT(COALESCE(s.download_speed, ''), '/', COALESCE(s.upload_speed, '')) AS speed,
  s.service_type,
  false AS show_jeop_icon,
  false AS show_note_icon,
  CASE WHEN s.order_type LIKE '%Disconnect%' THEN true ELSE false END AS show_open_disconnect_icon,
  CASE WHEN s.order_type LIKE '%Move%' OR s.order_type LIKE '%Add%' OR s.order_type LIKE '%Change%' THEN true ELSE false END AS show_open_mac_icon,
  COALESCE(EXTRACT(DAY FROM NOW() - s.last_status_change)::integer, 0) AS status_age,
  s.version,
  s.tenant_id,
  s.active,
  NULL::text AS open_jeop,
  NULL::text AS open_jeop_responsibilites,
  NULL::text AS latest_note,
  s.linked,
  s.bundled,
  s.linked_bundled_parent,
  s.linked_bundled_parent_id
FROM service s
JOIN location l ON s.location_id = l.location_id
JOIN orders o ON s.order_id = o.order_id
JOIN company c ON o.company_id = c.company_id
LEFT JOIN company pc ON c.master_customer_id = pc.company_id
WHERE s.current_inventory = false AND s.marked_for_deletion = false;

-- ============================================================================
-- v_manage_disputes: Dispute Worklist view
-- Backs DisputeView JPA entity and /api/disputeViews REST endpoint.
-- ============================================================================
DROP TABLE IF EXISTS v_manage_disputes CASCADE;
DROP VIEW IF EXISTS v_manage_disputes;

CREATE VIEW v_manage_disputes AS
SELECT d.dispute_id,
       s.service_id,
       l.location_id,
       l.order_id,
       l.client_location_id,
       pc.company_name AS parent_company_name,
       pc.company_id AS master_customer_id,
       pc.client_id AS parent_company_client_id,
       c.company_name,
       c.company_id,
       c.client_id AS end_customer_client_id,
       CONCAT(
           COALESCE(l.address_1, ''),
           CASE WHEN LENGTH(COALESCE(l.address_2, '')) > 0 THEN ' ' || l.address_2 ELSE '' END,
           ', ', COALESCE(l.city, ''),
           ', ', COALESCE(l.state_province, ''),
           ' ', COALESCE(l.postal_code, '')
       ) AS address,
       l.address_1,
       l.address_2,
       l.city,
       l.state_province,
       l.postal_code,
       s.service_type,
       d.dispute_status,
       d.dispute_type,
       d.dispute_assignment,
       s.provider,
       s.client_service_id,
       s.service_billed_to,
       d.amount_disputed_mrc,
       d.amount_disputed_nrc,
       s.provider_circuit_id,
       s.summary_bill,
       d.open_date,
       d.dispute_follow_up_date,
       d.credit_recognized,
       d.billing_review_complete_date,
       d.dispute_closed_date,
       d.invoice_num,
       d.vendor_tracking_num,
       d.realized_credit,
       d.realized_mrc_adjustment,
       d.annualized_mrc_save,
       s.service_mrc,
       s.service_nrc,
       CONCAT(COALESCE(s.download_speed, ''), '/', COALESCE(s.upload_speed, '')) AS speed,
       s.has_icb,
       s.active,
       SUBSTRING((SELECT note FROM note WHERE note_id = latest_note.note_id), 1, 500) AS latest_note,
       d.tenant_id,
       d.version,
       CASE
           WHEN d.dispute_follow_up_date IS NOT NULL
                AND d.dispute_status != 'Dispute Closed'
                AND d.dispute_follow_up_date::date <= CURRENT_DATE
           THEN true ELSE false
       END AS show_dispute_follow_up_icon
FROM dispute d
JOIN service s ON d.service_id = s.service_id
JOIN location l ON s.location_id = l.location_id
JOIN orders o ON s.order_id = o.order_id
JOIN company c ON o.company_id = c.company_id
LEFT JOIN company pc ON c.master_customer_id = pc.company_id
LEFT JOIN (
    SELECT MAX(n.note_id) AS note_id, dn.dispute_id
    FROM note n
    JOIN dispute_note dn ON n.note_id = dn.note_id
    GROUP BY dn.dispute_id
) latest_note ON latest_note.dispute_id = d.dispute_id
WHERE s.marked_for_deletion = false;

-- ============================================================================
-- v_company: Company/Customer view
-- Backs CompanyView JPA entity and /api/companyViews REST endpoint.
-- ============================================================================
DROP TABLE IF EXISTS v_company CASCADE;
DROP VIEW IF EXISTS v_company;

CREATE VIEW v_company AS
SELECT lct.complete_date,
       c.*,
       COALESCE(
           (SELECT display_name FROM v_subject WHERE subject_id = c.account_manager),
           'Unassigned'
       ) AS account_manager_name,
       TRIM(CONCAT(
           CASE WHEN bc.first_name IS NOT NULL THEN bc.first_name ELSE '' END,
           ' ',
           CASE WHEN bc.last_name IS NOT NULL THEN bc.last_name ELSE '' END
       )) AS billing_contact_name,
       bc.email AS billing_contact_email,
       bc.phone AS billing_contact_phone,
       t.name AS tenant_name,
       CASE
           WHEN c.task_group_id IS NULL AND c.company_active THEN 'active'
           WHEN c.task_group_id IS NOT NULL
                AND (cts.completed_tasks = cts.total_tasks
                     OR (cts.completed_tasks IS NULL AND cts.total_tasks IS NULL))
                AND c.company_active THEN 'active'
           WHEN c.task_group_id IS NOT NULL
                AND cts.completed_tasks != cts.total_tasks
                AND c.company_active THEN 'onboarding'
           ELSE 'inactive'
       END AS status,
       lct.value AS last_completed_task,
       nct.value AS next_task,
       nct.assigned_to AS next_task_assigned_to,
       COALESCE(cts.total_tasks - cts.completed_tasks, 0) AS remaining_tasks,
       ROUND(
           CASE
               WHEN c.task_group_id IS NULL
                    OR (cts.completed_tasks IS NULL AND cts.total_tasks IS NULL) THEN 1
               WHEN cts.total_tasks = 0 THEN 1
               ELSE cts.completed_tasks::numeric / cts.total_tasks::numeric
           END * 100
       ) AS progress_percentage
FROM company c
LEFT JOIN contact bc ON bc.company_id = c.company_id AND bc.contact_type = 'BILLING'
LEFT JOIN (
    SELECT ct.company_id,
           COUNT(*) AS total_tasks,
           SUM(CASE WHEN ct.complete_date IS NOT NULL THEN 1 ELSE 0 END) AS completed_tasks,
           MAX(ct.complete_date) AS last_completed_task_date
    FROM company_task ct
    GROUP BY ct.company_id
) cts ON cts.company_id = c.company_id
LEFT JOIN company_task lct ON lct.company_id = c.company_id
    AND lct.company_task_id = (
        SELECT ct2.company_task_id
        FROM company_task ct2
        WHERE ct2.company_id = c.company_id
              AND ct2.complete_date = (
                  SELECT MAX(complete_date)
                  FROM company_task ct3
                  WHERE ct3.company_id = c.company_id
              )
        ORDER BY ct2.company_task_id DESC
        LIMIT 1
    )
LEFT JOIN (
    SELECT ct.*
    FROM company_task ct
    INNER JOIN (
        SELECT MIN(mct.company_task_id) AS mcti, mct.company_id
        FROM company_task mct
        WHERE mct.complete_date IS NULL
        GROUP BY mct.company_id
    ) ctn ON ctn.mcti = ct.company_task_id
) nct ON nct.company_id = c.company_id
JOIN v_tenant t ON t.tenant_id = c.tenant_id;

-- ============================================================================
-- v_manage_service_inventory: Service Inventory view
-- Backs ServiceInventoryView JPA entity and /api/serviceInventoryViews REST endpoint.
-- ============================================================================
DROP TABLE IF EXISTS v_manage_service_inventory CASCADE;
DROP VIEW IF EXISTS v_manage_service_inventory;

CREATE VIEW v_manage_service_inventory AS
SELECT
    s.service_id,
    l.location_id,
    s.order_type,
    l.client_location_id,
    CONCAT(
        COALESCE(l.address_1, ''),
        CASE WHEN LENGTH(COALESCE(l.address_2, '')) > 0 THEN ' ' || l.address_2 ELSE '' END,
        ', ', COALESCE(l.city, ''),
        ', ', COALESCE(l.state_province, ''),
        ' ', COALESCE(l.postal_code, '')
    ) AS address,
    l.address_1,
    l.address_2,
    l.city,
    l.state_province,
    l.postal_code,
    l.order_id,
    s.service_status,
    s.service_billed_to,
    COALESCE((SELECT display_name FROM v_subject WHERE subject_id = o.provisioner), 'Unassigned') AS provisioner,
    o.client_project_manager AS project_manager,
    s.provider,
    s.summary_bill,
    s.provider_circuit_id,
    s.client_service_id,
    s.service_mrc,
    s.service_nrc,
    s.service_mrr,
    s.service_nrr,
    s.annual_recurring_cost,
    s.contract_signed_date,
    s.contract_term,
    s.circuit_term_end_date,
    (SELECT MIN(milestone_date)
     FROM v_service_milestone_instance smi
     WHERE smi.service_id = s.service_id
       AND smi.milestone_code = 'CREATED') AS inventory_added_date,
    l.client_location_type,
    l.client_location_info,
    c.company_name,
    c.company_id,
    c.client_id AS end_customer_client_id,
    pc.company_name AS parent_company_name,
    pc.company_id AS master_customer_id,
    pc.client_id AS parent_company_client_id,
    CONCAT(COALESCE(s.download_speed, ''), '/', COALESCE(s.upload_speed, '')) AS speed,
    s.service_type,
    s.version,
    s.tenant_id,
    s.active,
    s.billable,
    s.has_icb,
    CASE WHEN s.active = true THEN 'Active' ELSE 'Inactive' END AS active_inactive,
    COALESCE(open_dispute.count_open_disputes, 0) AS count_open_disputes,
    COALESCE(open_dispute.open_dispute_mrc, 0) AS open_dispute_mrc,
    COALESCE(open_dispute.open_dispute_nrc, 0) AS open_dispute_nrc,
    open_dispute.dispute_types AS dispute_types,
    CASE WHEN s.order_type IN ('Move', 'Add', 'Change', 'Disconnect') AND s.service_status != 'Service Complete' THEN 1 ELSE 0 END AS is_macd,
    s.sub_order_type,
    s.linked,
    s.bundled,
    s.account_number,
    child_services.child_ids,
    child_services.child_order_types,
    child_services.child_sub_order_types,
    CASE WHEN child_services.child_order_types LIKE '%Disconnect%' THEN true ELSE false END AS show_open_disconnect_icon,
    CASE WHEN child_services.child_order_types LIKE '%Move%'
              OR child_services.child_order_types LIKE '%Add%'
              OR child_services.child_order_types LIKE '%Change%' THEN true ELSE false END AS show_open_mac_icon,
    CASE WHEN COALESCE(open_dispute.count_open_disputes, 0) > 0 THEN true ELSE false END AS show_open_dispute_icon
FROM service s
JOIN location l ON s.location_id = l.location_id
JOIN orders o ON l.order_id = o.order_id
JOIN company c ON o.company_id = c.company_id
LEFT JOIN company pc ON c.master_customer_id = pc.company_id
LEFT JOIN (
    SELECT
        s2.service_id,
        MIN(CASE WHEN vsmi.milestone_code = 'COMPLETE' OR vsmi.milestone_code = 'DATA_PROVISIONING_COMPLETE' THEN vsmi.milestone_date END) AS inventory_added_date
    FROM v_service_milestone_instance vsmi
    INNER JOIN service s2 ON vsmi.service_id = s2.service_id
    GROUP BY s2.service_id
) vsmi ON s.service_id = vsmi.service_id
LEFT JOIN (
    SELECT
        d.service_id,
        COUNT(*) AS count_open_disputes,
        SUM(d.amount_disputed_mrc) AS open_dispute_mrc,
        SUM(d.amount_disputed_nrc) AS open_dispute_nrc,
        STRING_AGG(d.dispute_type, ',') AS dispute_types
    FROM dispute d
    WHERE d.dispute_status != 'Dispute Closed'
    GROUP BY d.service_id
) open_dispute ON s.service_id = open_dispute.service_id
LEFT JOIN (
    SELECT
        parent_service_id,
        STRING_AGG(service_id::text, ',') AS child_ids,
        STRING_AGG(order_type, ',') AS child_order_types,
        STRING_AGG(sub_order_type, ',') AS child_sub_order_types
    FROM service
    WHERE parent_service_id IS NOT NULL
          AND service_status NOT IN ('Service Complete', 'Disconnect Complete', 'Service Cancelled', 'Change in Assignment')
    GROUP BY parent_service_id
) child_services ON s.service_id = child_services.parent_service_id
WHERE s.current_inventory = true AND s.marked_for_deletion = false;

-- ============================================================================
-- v_manage_location_inventory: Location Inventory view
-- Backs LocationInventoryView JPA entity and /api/locationInventoryViews REST endpoint.
-- ============================================================================
DROP TABLE IF EXISTS v_manage_location_inventory CASCADE;
DROP VIEW IF EXISTS v_manage_location_inventory;

CREATE VIEW v_manage_location_inventory AS
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
    o.client_project_manager,
    COALESCE(vsm.display_name, 'Unassigned') AS vertek_project_manager,
    o.client_order_id,
    l.client_location_id,
    l.client_location_info,
    l.client_location_type,
    l.location_name,
    l.location_status,
    COALESCE(sv.count_services, 0) AS count_services,
    sv.count_active_services,
    sv.count_inactive_services,
    sv.services,
    l.progress_percentage,
    CONCAT(
        COALESCE(l.address_1, ''),
        CASE WHEN LENGTH(COALESCE(l.address_2, '')) > 0 THEN ' ' || l.address_2 ELSE '' END,
        ', ', COALESCE(l.city, ''), ', ',
        COALESCE(l.state_province, ''), ' ',
        COALESCE(l.postal_code, '')
    ) AS address,
    l.address_1,
    l.address_2,
    l.city,
    l.state_province,
    l.postal_code,
    l.tenant_id,
    l.version,
    l.active,
    COALESCE(sv.count_services_complete, 0) AS count_services_complete,
    COALESCE(sv_dead.count_services_cancelled, 0) AS count_services_cancelled,
    COALESCE(sv_dead.count_services_change_in_assignment, 0) AS count_services_change_in_assignment,
    COALESCE(sv.active_complete_mrc, 0) AS active_complete_mrc,
    COALESCE(sv.active_complete_nrc, 0) AS active_complete_nrc,
    COALESCE(sv.active_complete_mrr, 0) AS active_complete_mrr,
    COALESCE(sv.active_complete_nrr, 0) AS active_complete_nrr,
    COALESCE(sv.annual_recurring_cost, 0) AS annual_recurring_cost,
    COALESCE(open_dispute.count_open_disputes, 0) AS count_open_disputes,
    COALESCE(open_dispute.open_dispute_mrc, 0) AS open_dispute_mrc,
    COALESCE(open_dispute.open_dispute_nrc, 0) AS open_dispute_nrc,
    COALESCE(sv.macd_count, 0) AS macd_count,
    CASE WHEN l.active = true THEN 'Active' ELSE 'Inactive' END AS active_inactive,
    (SELECT MIN(milestone_date)
     FROM v_location_milestone_instance lmi
     WHERE lmi.location_id = l.location_id
       AND lmi.milestone_code = 'CREATED') AS inventory_added_date,
    sv.sub_order_types,
    CASE WHEN sv.child_order_types LIKE '%Disconnect%' THEN true ELSE false END AS show_open_disconnect_icon,
    CASE WHEN sv.child_order_types LIKE '%Move%'
              OR sv.child_order_types LIKE '%Add%'
              OR sv.child_order_types LIKE '%Change%' THEN true ELSE false END AS show_open_mac_icon,
    CASE WHEN COALESCE(open_dispute.count_open_disputes, 0) > 0 THEN true ELSE false END AS show_open_dispute_icon,
    CASE WHEN COALESCE(sv.count_linked_services, 0) > 0 THEN true ELSE false END AS show_linked_icon,
    CASE WHEN COALESCE(sv.count_bundled_services, 0) > 0 THEN true ELSE false END AS show_bundled_icon
FROM company c
JOIN orders o ON c.company_id = o.company_id
JOIN location l ON o.order_id = l.order_id
LEFT JOIN company pc ON c.master_customer_id = pc.company_id
LEFT JOIN v_subject vs ON vs.subject_id = o.provisioner
LEFT JOIN v_subject vsm ON vsm.subject_id = o.vertek_project_manager
LEFT JOIN (
    SELECT
        s.location_id,
        COUNT(s.service_id) AS count_services,
        SUM(CASE WHEN s.active = true THEN 1 ELSE 0 END) AS count_active_services,
        SUM(CASE WHEN s.active = false THEN 1 ELSE 0 END) AS count_inactive_services,
        SUM(
            CASE WHEN child_services.child_ids IS NOT NULL
                 THEN (LENGTH(child_services.child_ids) - LENGTH(REPLACE(child_services.child_ids, ',', '')) + 1)
                 ELSE 0 END
        ) AS macd_count,
        STRING_AGG(child_services.child_order_types, ',') AS child_order_types,
        STRING_AGG(DISTINCT s.service_type, ',') AS services,
        STRING_AGG(DISTINCT s.sub_order_type, ',') AS sub_order_types,
        SUM(CASE WHEN vsmi.milestone_date IS NOT NULL THEN s.service_mrc ELSE 0 END) AS active_complete_mrc,
        SUM(CASE WHEN vsmi.milestone_date IS NOT NULL THEN s.service_nrc ELSE 0 END) AS active_complete_nrc,
        SUM(CASE WHEN vsmi.milestone_date IS NOT NULL THEN s.service_mrr ELSE 0 END) AS active_complete_mrr,
        SUM(CASE WHEN vsmi.milestone_date IS NOT NULL THEN s.service_nrr ELSE 0 END) AS active_complete_nrr,
        SUM(CASE WHEN vsmi.milestone_date IS NOT NULL THEN s.annual_recurring_cost ELSE 0 END) AS annual_recurring_cost,
        SUM(CASE WHEN vsmi.milestone_date IS NOT NULL THEN 1 ELSE 0 END) AS count_services_complete,
        SUM(CASE WHEN s.linked THEN 1 ELSE 0 END) AS count_linked_services,
        SUM(CASE WHEN s.bundled THEN 1 ELSE 0 END) AS count_bundled_services
    FROM service s
    LEFT JOIN (
        SELECT service_id, MIN(milestone_date) AS milestone_date
        FROM v_service_milestone_instance
        WHERE milestone_code IN ('COMPLETE', 'DATA_PROVISIONING_COMPLETE')
        GROUP BY service_id
    ) vsmi ON s.service_id = vsmi.service_id
    LEFT JOIN (
        SELECT
            parent_service_id,
            STRING_AGG(service_id::text, ',') AS child_ids,
            STRING_AGG(order_type, ',') AS child_order_types
        FROM service
        WHERE parent_service_id IS NOT NULL
              AND service_status NOT IN ('Service Complete', 'Disconnect Complete', 'Service Cancelled', 'Change in Assignment')
              AND order_type != 'New'
        GROUP BY parent_service_id
    ) child_services ON s.service_id = child_services.parent_service_id
    WHERE s.service_status != 'Service Cancelled'
    GROUP BY s.location_id
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
        s.location_id,
        COUNT(*) AS count_open_disputes,
        SUM(d.amount_disputed_mrc) AS open_dispute_mrc,
        SUM(d.amount_disputed_nrc) AS open_dispute_nrc
    FROM dispute d
    JOIN service s ON d.service_id = s.service_id
    WHERE d.dispute_status != 'Dispute Closed'
    GROUP BY s.location_id
) open_dispute ON l.location_id = open_dispute.location_id
WHERE l.current_inventory = true AND l.marked_for_deletion = false;

-- ============================================================================
-- WIP VIEWS (Dashboard / Expenses feature)
-- Ported from 104bf38 v_wip_reports_v12.sql
-- ============================================================================

-- v_wip_service: WIP service view for dashboard financials and expense accrual
DROP TABLE IF EXISTS v_wip_service CASCADE;
DROP VIEW IF EXISTS v_wip_service;

CREATE VIEW v_wip_service AS
SELECT c.company_name,
       (SELECT company_name FROM company WHERE company_id = c.master_customer_id) AS master_company_name,
       o.order_id,
       o.provisioner,
       o.vertek_project_manager,
       o.client_project_manager,
       o.client_order_id,
       l.location_id,
       l.client_location_id,
       l.location_name,
       l.address_1,
       l.address_2,
       l.city,
       l.state_province,
       s.service_id,
       s.client_service_id,
       s.provider,
       s.service_status,
       s.service_type,
       s.active,
       s.service_mrc,
       s.tenant_id,
       c.master_customer_id,
       s.service_billed_to,
       s.current_inventory,
       (SELECT mi.milestone_date
        FROM milestone_instance mi
        JOIN service_milestone_instance smi ON mi.milestone_instance_id = smi.milestone_instance_id
        JOIN milestone m ON mi.milestone_id = m.milestone_id
        WHERE m.milestone_code = 'DATA_PROVISIONING_COMPLETE' AND smi.service_id = s.service_id
        LIMIT 1) AS data_provisioning_complete_date,
       (SELECT mi.milestone_date
        FROM milestone_instance mi
        JOIN service_milestone_instance smi ON mi.milestone_instance_id = smi.milestone_instance_id
        JOIN milestone m ON mi.milestone_id = m.milestone_id
        WHERE m.milestone_code = 'COMPLETE' AND smi.service_id = s.service_id
        LIMIT 1) AS service_complete_date
FROM company c
JOIN orders o ON c.company_id = o.company_id
JOIN location l ON o.order_id = l.order_id
JOIN service s ON l.location_id = s.location_id
WHERE s.marked_for_deletion = FALSE;

-- v_wip_service_jeop: Service jeopardy view for WIP dashboard
DROP TABLE IF EXISTS v_wip_service_jeop CASCADE;
DROP VIEW IF EXISTS v_wip_service_jeop;

CREATE VIEW v_wip_service_jeop AS
SELECT ji.jeop_instance_id,
       c.company_name,
       (SELECT company_name FROM company WHERE company_id = c.master_customer_id) AS master_company_name,
       o.order_id,
       o.provisioner,
       o.vertek_project_manager,
       o.client_project_manager,
       o.client_order_id,
       l.location_id,
       l.client_location_id,
       l.location_name,
       l.address_1,
       l.address_2,
       l.city,
       l.state_province,
       s.service_id,
       s.client_service_id,
       s.provider,
       s.service_status,
       s.service_type,
       s.active,
       s.service_mrc,
       s.tenant_id,
       c.master_customer_id,
       s.service_billed_to,
       ji.jeop_description,
       ji.start_date,
       ji.end_date,
       ji.responsibility,
       ji.assigned_to
FROM company c
JOIN orders o ON c.company_id = o.company_id
JOIN location l ON o.order_id = l.order_id
JOIN service s ON l.location_id = s.location_id
JOIN service_jeop_instance sji ON s.service_id = sji.service_id
JOIN jeop_instance ji ON sji.jeop_instance_id = ji.jeop_instance_id
WHERE s.marked_for_deletion = FALSE;

-- v_wip_location_jeop: Location jeopardy view for WIP dashboard
DROP TABLE IF EXISTS v_wip_location_jeop CASCADE;
DROP VIEW IF EXISTS v_wip_location_jeop;

CREATE VIEW v_wip_location_jeop AS
SELECT ji.jeop_instance_id,
       c.company_name,
       (SELECT company_name FROM company WHERE company_id = c.master_customer_id) AS master_company_name,
       o.order_id,
       o.provisioner,
       o.vertek_project_manager,
       o.client_project_manager,
       o.client_order_id,
       l.location_id,
       l.client_location_id,
       l.location_name,
       l.address_1,
       l.address_2,
       l.city,
       l.state_province,
       l.location_status,
       l.client_location_type,
       l.tenant_id,
       c.master_customer_id,
       ji.jeop_description,
       ji.start_date,
       ji.end_date,
       ji.responsibility,
       ji.assigned_to
FROM company c
JOIN orders o ON c.company_id = o.company_id
JOIN location l ON o.order_id = l.order_id
JOIN location_jeop_instance lji ON l.location_id = lji.location_id
JOIN jeop_instance ji ON lji.jeop_instance_id = ji.jeop_instance_id
WHERE l.marked_for_deletion = FALSE;

-- v_provider_intervals: Provider install/survey interval view for Providers dashboard
DROP TABLE IF EXISTS v_provider_intervals CASCADE;
DROP VIEW IF EXISTS v_provider_intervals;

CREATE VIEW v_provider_intervals AS
SELECT ii.interval_instance_id,
       c.company_name,
       (SELECT company_name FROM company WHERE company_id = c.master_customer_id) AS master_company_name,
       o.order_id,
       o.client_order_id,
       l.location_id,
       l.location_name,
       l.client_location_id,
       s.service_id,
       s.client_service_id,
       s.provider,
       s.service_type,
       it.interval_type_code,
       it.interval_type_desc,
       om.milestone_code AS open_milestone_code,
       omi.milestone_date AS start_date,
       cm.milestone_code AS close_milestone_code,
       cmi.milestone_date AS end_date,
       ii.calendar_day_interval_time,
       ii.provider_calendar_day_deduct_time,
       ii.customer_calendar_day_deduct_time,
       ii.client_calendar_day_deduct_time,
       ii.business_day_interval_time,
       ii.provider_business_day_deduct_time,
       ii.client_business_day_deduct_time,
       s.active,
       s.tenant_id,
       s.master_customer_id,
       s.service_billed_to
FROM interval_instance ii
JOIN interval_type it ON ii.interval_type_id = it.interval_type_id
JOIN service_interval_instance sii ON ii.interval_instance_id = sii.interval_instance_id
JOIN service s ON sii.service_id = s.service_id
JOIN location l ON s.location_id = l.location_id
JOIN orders o ON l.order_id = o.order_id
JOIN company c ON o.company_id = c.company_id
JOIN milestone_instance omi ON ii.open_milestone_instance_id = omi.milestone_instance_id
JOIN milestone om ON omi.milestone_id = om.milestone_id
LEFT JOIN milestone_instance cmi ON ii.close_milestone_instance_id = cmi.milestone_instance_id
LEFT JOIN milestone cm ON cmi.milestone_id = cm.milestone_id
WHERE s.marked_for_deletion = FALSE;

-- v_activation_attempts: Activation attempt view for Activations dashboard
-- NOTE: Uses PostgreSQL-compatible EXTRACT instead of MySQL MONTH()/YEAR()/TIMESTAMPDIFF()
DROP TABLE IF EXISTS v_activation_attempts CASCADE;
DROP VIEW IF EXISTS v_activation_attempts;

CREATE VIEW v_activation_attempts AS
SELECT aa.activation_attempt_id,
       c.company_name,
       (SELECT company_name FROM company WHERE company_id = c.master_customer_id) AS master_company_name,
       o.tenant_id,
       o.master_customer_id,
       o.order_id,
       o.client_order_id,
       l.location_id,
       l.location_name,
       l.client_location_id,
       s.service_id,
       s.client_service_id,
       s.provider,
       s.service_type,
       l.client_location_type,
       l.client_location_info,
       aa.attempt_number,
       aa.field_tech_check_in,
       aa.field_tech_check_out,
       aa.scheduled_attempt_status,
       EXTRACT(MONTH FROM aa.field_tech_check_out)::integer AS aa_month,
       EXTRACT(YEAR FROM aa.field_tech_check_out)::integer AS aa_year,
       EXTRACT(EPOCH FROM (aa.field_tech_check_out - aa.field_tech_check_in))::integer / 60 AS service_activation_interval,
       laa_view.location_activation_interval,
       (SELECT mi.milestone_date
        FROM milestone_instance mi
        JOIN service_milestone_instance smi ON mi.milestone_instance_id = smi.milestone_instance_id
        JOIN milestone m ON mi.milestone_id = m.milestone_id
        WHERE m.milestone_code = 'DATA_PROVISIONING_COMPLETE' AND smi.service_id = s.service_id
        LIMIT 1) AS data_provisioning_complete,
       (SELECT mi.milestone_date
        FROM milestone_instance mi
        JOIN service_milestone_instance smi ON mi.milestone_instance_id = smi.milestone_instance_id
        JOIN milestone m ON mi.milestone_id = m.milestone_id
        WHERE m.milestone_code = 'COMPLETE' AND smi.service_id = s.service_id
        LIMIT 1) AS service_complete,
       s.service_billed_to
FROM activation_attempt aa
JOIN service s ON aa.service_id = s.service_id
JOIN location l ON s.location_id = l.location_id
JOIN (
    SELECT s2.location_id,
           EXTRACT(MONTH FROM aa2.field_tech_check_out)::integer AS aa_month,
           EXTRACT(YEAR FROM aa2.field_tech_check_out)::integer AS aa_year,
           SUM(EXTRACT(EPOCH FROM (aa2.field_tech_check_out - aa2.field_tech_check_in))::integer / 60) AS location_activation_interval
    FROM activation_attempt aa2
    JOIN service s2 ON aa2.service_id = s2.service_id
    GROUP BY s2.location_id, EXTRACT(MONTH FROM aa2.field_tech_check_out)::integer, EXTRACT(YEAR FROM aa2.field_tech_check_out)::integer
) laa_view ON l.location_id = laa_view.location_id
    AND EXTRACT(MONTH FROM aa.field_tech_check_out)::integer = laa_view.aa_month
    AND EXTRACT(YEAR FROM aa.field_tech_check_out)::integer = laa_view.aa_year
JOIN orders o ON l.order_id = o.order_id
JOIN company c ON o.company_id = c.company_id
WHERE s.marked_for_deletion = FALSE;
