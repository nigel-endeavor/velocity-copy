DROP VIEW IF EXISTS pbi_i90_broadband_service CASCADE;
CREATE OR REPLACE VIEW pbi_i90_broadband_service AS
SELECT bs.service_id AS 'Service ID',
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
       modem_make AS 'Modem Make',
       bs.mac_address AS 'Broadband MAC Address',
       customer_prem_equipment AS 'Customer Prem Equipment',
       network_protocol AS 'Network Protocol',
       pppoe_username AS 'PPPoE Username',
       pppoe_password AS 'PPPoE Password',
       modem_serial_number AS 'Modem Serial Number'
FROM broadband_service bs
     JOIN service s ON bs.service_id = s.service_id
WHERE s.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');
