DROP VIEW IF EXISTS v_service_history CASCADE;
CREATE OR REPLACE VIEW v_service_history AS
SELECT
    s.service_id,
    s.location_id,
    s.order_id,
    COALESCE(s.provider, 'None') as provider,
    case when s.current_inventory = true then 'Inventory' else s.order_type end as order_type,
    s.sub_order_type,
    s.provider_order_num,
    s.service_status,
    s.parent_service_id,
    s.master_customer_id,
    s.tenant_id,
    s.version,
    s.client_service_id,
    vsmi.created,
    vsmi.complete,
    vsmi.cancelled,
    IF(ps.service_id IS NOT NULL,
        CONCAT('/order/', ps.order_id, '/location/', ps.location_id, '/service/', ps.service_id),
        ''
    ) as parent_service_link
FROM service s
    LEFT JOIN service ps ON s.parent_service_id = ps.service_id
    LEFT JOIN (
        SELECT
            vsmi.service_id,
            MAX(CASE WHEN vsmi.milestone_code = 'CREATED' THEN vsmi.milestone_date END) AS created,
            MAX(CASE WHEN vsmi.milestone_code = 'COMPLETE' THEN vsmi.milestone_date END) AS complete,
            MAX(CASE WHEN vsmi.milestone_code = 'CANCELLED' THEN vsmi.milestone_date END) AS cancelled
        FROM v_service_milestone_instance vsmi
        GROUP BY service_id
    ) vsmi ON s.service_id = vsmi.service_id
where s.marked_for_deletion = FALSE;
