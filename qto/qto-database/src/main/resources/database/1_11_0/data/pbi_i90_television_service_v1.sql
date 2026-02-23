DROP VIEW IF EXISTS pbi_i90_television_service CASCADE;
CREATE OR REPLACE VIEW pbi_i90_television_service AS
SELECT s.service_id,
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
       ts.plan AS 'Plan',
       ts.dvr_included AS 'DVR Included',
       ts.receiver AS 'Receiver',
       ts.receiver_mac AS 'Receiver MAC',
       ts.dvr AS 'DVR',
       ts.dvr_mac AS 'DVR MAC'
FROM service s
     JOIN television_service ts ON s.service_id = ts.service_id
WHERE s.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor')
