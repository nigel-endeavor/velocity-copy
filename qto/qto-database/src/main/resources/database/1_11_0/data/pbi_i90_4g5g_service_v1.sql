DROP VIEW IF EXISTS pbi_i90_4g5g_service CASCADE;
CREATE OR REPLACE VIEW pbi_i90_4g5g_service AS
SELECT gs.service_id AS 'Service ID',
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
       summary_bill AS 'Billing Account Number',
       uid AS 'UID',
       iccid AS 'ICCID',
       imei AS 'IMEI',
       mdn AS 'MDN',
       apn AS 'APN',
       rate_plan AS 'Rate Plan',
       rsrp AS 'RSRP',
       rsrq AS 'RSRQ',
       sinr AS 'SINR',
       rssi AS 'RSSI',
       replace_4g_5g AS 'Replace 4G/5G',
       gs.mac_address AS '4G/5G MAC Address',
       serial_number AS '4G/5G Serial Number',
       circuit_priority AS 'Circuit Priority'
FROM `4g5g_service` gs
     JOIN service s ON gs.service_id = s.service_id
WHERE s.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');
