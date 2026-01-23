CREATE OR REPLACE VIEW pbi_i90_location_contacts AS
SELECT l.location_id AS 'Location ID',
       c.contact_id AS 'Contact ID',
       l.tenant_id AS 'Tenant ID',
       l.current_inventory AS 'Current Inventory',
       first_name AS 'First Name',
       last_name AS 'Last Name',
       contact_active AS Active,
       contact_type AS 'Contact Type',
       role AS Rold,
       phone AS Phone,
       email AS Email
FROM location l
     JOIN location_contact lc ON l.location_id = lc.location_id
     JOIN contact c ON lc.contact_id = c.contact_id
WHERE l.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');

