DROP VIEW IF EXISTS pbi_i90_end_company CASCADE;
CREATE OR REPLACE VIEW pbi_i90_end_company AS
SELECT c.company_id AS end_company_id,
       c.master_customer_id AS master_customer_id,
       c.tenant_id AS "Tenant ID",
       company_name AS 'End Customer',
       company_active AS 'End Customer Active',
       client_id AS 'End Customer Client ID',
       inventory_location_count AS 'End Customer Inventory Location Count',
       inventory_mrc AS 'End Customer Inventory MRC',
       account_notes AS 'End Customer Account Notes',
       c.address_1 AS 'End Customer Address 1',
       c.address_2 AS 'End Customer Address 2',
       c.city AS 'End Customer City',
       c.state_province AS 'End Customer State',
       c.postal_code AS 'End Customer Zip',
       c.country AS 'End Customer Country',
       bc.first_name AS 'End Customer Billing Contact First Name',
       bc.last_name AS 'End Customer Billing Contact Last Name',
       bc.email AS 'End Customer Billing Contact Email',
       bc.phone AS 'End Customer Billing Contact Phone',
       tc.first_name AS 'End Customer Tech Contact First Name',
       tc.last_name AS 'End Customer Tech Contact Last Name',
       tc.email AS 'End Customer Tech Contact Email',
       tc.phone AS 'End Customer Tech Contact Phone',
       sc.first_name AS 'End Customer Sales Contact First Name',
       sc.last_name AS 'End Customer Sales Contact Last Name',
       sc.email AS 'End Customer Sales Contact Email',
       sc.phone AS 'End Customer Sales Contact Phone',
       ac.first_name AS 'End Customer Authorization Contact First Name',
       ac.last_name AS 'End Customer Authorization Contact Last Name',
       ac.email AS 'End Customer Authorization Contact Email',
       ac.phone AS 'End Customer Authorization Contact Phone',
       tg.template_name AS 'End Customer Task Group'
FROM company c
     LEFT JOIN (SELECT company_id, first_name, last_name, email, phone
                FROM contact c
                WHERE contact_type = 'BILLING'
		              AND c.contact_id NOT IN (SELECT contact_id FROM location_contact)
		              AND contact_id NOT IN (SELECT contact_id FROM order_contact)) bc ON c.company_id = bc.company_id
     LEFT JOIN (SELECT company_id, first_name, last_name, email, phone
                FROM contact c
                WHERE contact_type = 'TECH'
		              AND c.contact_id NOT IN (SELECT contact_id FROM location_contact)
		              AND contact_id NOT IN (SELECT contact_id FROM order_contact)) tc ON c.company_id = tc.company_id
     LEFT JOIN (SELECT company_id, first_name, last_name, email, phone
                FROM contact c
                WHERE contact_type = 'SALES'
		              AND c.contact_id NOT IN (SELECT contact_id FROM location_contact)
		              AND contact_id NOT IN (SELECT contact_id FROM order_contact)) sc ON c.company_id = sc.company_id
     LEFT JOIN (SELECT company_id, first_name, last_name, email, phone
                FROM contact c
                WHERE contact_type = 'AUTHORIZATION'
		              AND c.contact_id NOT IN (SELECT contact_id FROM location_contact)
		              AND contact_id NOT IN (SELECT contact_id FROM order_contact)) ac ON c.company_id = ac.company_id
     LEFT JOIN task_group tg ON c.task_group_id = tg.task_group_id
WHERE company_type = 'End Customer'
	AND c.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor')
