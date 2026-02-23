DROP VIEW IF EXISTS pbi_i90_end_company CASCADE;
CREATE OR REPLACE VIEW pbi_i90_end_company AS
SELECT c.company_id AS end_company_id,
       c.master_customer_id AS master_customer_id,
       c.tenant_id AS "Tenant ID",
       company_name AS 'End Customer',
       company_active AS 'End Customer Active',
       billing_account_number AS 'End Customer Billing Account Number',
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
       ac.phone AS 'End Customer Authorization Contact Phone'
 FROM company c
    LEFT JOIN (select company_id, first_name, last_name, email, phone from contact c
	         where contact_type = 'BILLING' and  c.contact_id not in (select contact_id from location_contact)
						and contact_id not in (select contact_id from order_contact)) bc on c.company_id = bc.company_id
    LEFT JOIN (select company_id, first_name, last_name, email, phone from contact c
	         where contact_type = 'TECH' and  c.contact_id not in (select contact_id from location_contact)
						and contact_id not in (select contact_id from order_contact)) tc on c.company_id = tc.company_id
    LEFT JOIN (select company_id, first_name, last_name, email, phone from contact c
	         where contact_type = 'SALES' and  c.contact_id not in (select contact_id from location_contact)
						and contact_id not in (select contact_id from order_contact)) sc on c.company_id = sc.company_id
    LEFT JOIN (select company_id, first_name, last_name, email, phone from contact c
	         where contact_type = 'AUTHORIZATION' and  c.contact_id not in (select contact_id from location_contact)
						and contact_id not in (select contact_id from order_contact)) ac on c.company_id = ac.company_id
WHERE company_type = 'End Customer'
	AND c.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor')
