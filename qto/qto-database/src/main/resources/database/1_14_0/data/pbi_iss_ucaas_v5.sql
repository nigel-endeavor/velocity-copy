
DROP VIEW IF EXISTS pbi_iss_ucass_service CASCADE;
CREATE OR REPLACE VIEW pbi_iss_ucass_service AS
SELECT us.service_id AS 'Service ID',
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
       published_tn AS 'Published TN',
       temporary_tn AS 'Temporary TN',
       number_of_handsets AS 'Number of Handsets'
FROM ucaas_service us
     JOIN service s ON us.service_id = s.service_id
WHERE tenant_id IN (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');
