CREATE OR REPLACE VIEW pbi_i90_dia_service AS
SELECT ds.service_id AS 'Service ID',
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
       provider_activation_method AS 'Carrier Activation Method',
       interface_connector AS 'Interface Connector',
       npa_nxx AS 'NPA NXX',
       last_mile_provider AS 'Last Mile Carrier',
       new_access_circuit_id AS 'New Access Circuit ID',
       burstable_speed AS 'Burstable Speed',
       burstable_speed_cost AS 'Burstable Speed Cost',
       router_serial_number AS 'Router Serial Number',
       router_mac_address AS 'Router MAC Address'
FROM dia_service ds
     JOIN service s ON ds.service_id = s.service_id
WHERE s.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');
