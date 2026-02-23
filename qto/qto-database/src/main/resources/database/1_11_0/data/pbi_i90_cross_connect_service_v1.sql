DROP VIEW IF EXISTS pbi_i90_cross_connect_service CASCADE;
CREATE OR REPLACE VIEW pbi_i90_cross_connect_service AS
SELECT s.service_id,
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
       ccs.cross_connect_id AS 'Cross Connect ID',
       ccs.cross_connect_room AS 'Cross Connect Room',
       ccs.cross_connect_rack AS 'Cross Connect Rack',
       ccs.cross_connect_port AS 'Cross Connect Port',
       ccs.cross_connect_type AS 'Cross Connect Type',
       ccs.cross_connect_data_center_name AS 'Cross Connect Data Center Name'
FROM service s
     JOIN cross_connect_service ccs ON s.service_id = ccs.service_id
WHERE s.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');
