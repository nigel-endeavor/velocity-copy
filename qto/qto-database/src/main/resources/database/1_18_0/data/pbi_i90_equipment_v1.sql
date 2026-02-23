DROP VIEW IF EXISTS pbi_i90_equipment CASCADE;
CREATE OR REPLACE VIEW pbi_i90_equipment AS
SELECT se.service_id AS "Service ID",
       e.tenant_id AS "Tenant ID",
       s.current_inventory AS "Current Inventory",
       e.equipment_type AS "Equipment Type",
       e.equipment_subtype AS "Equipment Subtype",
       e.make AS "Make",
       e.model AS "Model",
       e.serial_number AS "Serial Number",
       e.mac_address AS "MAC Address",
       e.ownership AS "Ownership",
       e.tracking_info AS "Tracking Info",
       e.ship_date AS "Ship Date",
       e.delivered_date AS "Delivered Date",
       e.description AS "Description",
       e.network_ip_range AS "Network IP Range",
       e.ip_address AS "IP Address",
       e.gateway AS "Gateway",
       e.dns1 AS "DNS1",
       e.dns2 AS "DNS2",
       e.shipping_method AS "Shipping Method",
       e.decommission AS "Decommissioned",
e.decommissioned_date AS "Decommissioned Date"

FROM service_equipment se
     JOIN equipment e ON se.equipment_id = e.equipment_id
     JOIN service s ON se.service_id = s.service_id;
