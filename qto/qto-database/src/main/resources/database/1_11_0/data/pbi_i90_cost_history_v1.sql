DROP VIEW IF EXISTS pbi_i90_cost_history CASCADE;
CREATE OR REPLACE VIEW pbi_i90_cost_history AS
SELECT s.service_id AS 'Service ID',
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
       ch.cost_type AS 'Cost Type',
       ch.old_value AS 'Old Value',
       ch.new_value AS 'New Value',
       ch.change_reason AS 'Change Reason',
       ch.update_by AS 'Update By',
       ch.update_date AS 'Update Date',
       ch.type_provider AS 'Type Provider'
FROM service s
     JOIN cost_history ch ON s.service_id = ch.service_id
WHERE s.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');
