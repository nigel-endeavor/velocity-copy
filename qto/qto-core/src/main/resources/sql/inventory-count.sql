SELECT
    COUNT(DISTINCT ss.service_id),
    DATE_FORMAT(ss.snapshot_date, '%M %Y') AS display_date,
    DATE_FORMAT(ss.snapshot_date, '%Y-%m') AS order_date
FROM service_snapshot ss
WHERE
    ss.current_inventory
AND
    ss.active
AND
    ((:filter_on_mc = 0) OR (ss.master_customer_id in :mc_ids))
AND
    ((:filter_on_ec = 0) OR (ss.end_customer_id in :ec_ids))
AND
    ((:filter_on_service_type = 0) OR (ss.service_type in :service_types))
AND
    ((:filter_on_provider = 0) OR (ss.provider in :providers))
AND
    ((:filter_on_service_billed_to = 0) OR (ss.service_billed_to in :service_billed_tos))
AND
    ss.tenant_id = :tenant_id
GROUP BY display_date, order_date
ORDER BY order_date DESC
LIMIT :limit