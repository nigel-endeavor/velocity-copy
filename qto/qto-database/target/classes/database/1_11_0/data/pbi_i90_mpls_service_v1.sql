CREATE OR REPLACE VIEW pbi_i90_mpls_service AS
SELECT s.service_id,
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
       ms.last_mile_provider AS 'Last Mile Provider',
       ms.access_circuit_id AS 'Access Circuit ID',
       ms.port_circuit_id AS 'Port Circuit ID',
       ms.mpls_type AS 'MPLS Type',
       ms.port_speed AS 'Port Speed',
       ms.interface_connector AS 'Interface Connector',
       ms.provider_activation_method AS 'Provider Activation Method',
       ms.npa_nxx AS 'NPA NXX',
       ms.routing_protocol AS 'Routing Protocol',
       ms.cer_ips AS 'CER IPs',
       ms.per_ips AS 'PER IPs',
       ms.vlan_tag_1 AS 'VLAN Tag 1',
       ms.vlan_tag_2 AS 'VLAN Tag 2',
       ms.vlan_tag_3 AS 'VLAN Tag 3',
       ms.vlan_tag_4 AS 'VLAN Tag 4',
       ms.other_technical_notes AS 'Other Technical Notes'
FROM service s
     JOIN mpls_service ms ON s.service_id = ms.service_id
WHERE s.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor')
