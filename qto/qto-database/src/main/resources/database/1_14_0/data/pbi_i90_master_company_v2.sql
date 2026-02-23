DROP VIEW IF EXISTS pbi_i90_master_company CASCADE;
CREATE OR REPLACE VIEW pbi_i90_master_company AS
SELECT c.company_id AS master_customer_id,
       c.tenant_id AS "Tenant ID",
       company_name AS 'Master Customer',
       company_active AS 'Master Customer Active',
       billing_account_number AS 'Master Customer Billing Account Number',
       inventory_location_count AS 'Master Customer Inventory Location Count',
       inventory_mrc AS 'Master Customer Inventory MRC',
       account_notes AS 'Master Customer Account Notes',
       am.display_name AS 'Master Customer Account Manager',
       p.display_name AS 'Master Customer Provisioner',
       pm.display_name AS 'Master Customer i90 Project Manager',
       c.address_1 AS 'Master Customer Address 1',
       c.address_2 AS 'Master Customer Address 2',
       c.city AS 'Master Customer City',
       c.state_province AS 'Master Customer State',
       c.postal_code AS 'Master Customer Zip',
       c.country AS 'Master Customer Country',
       bc.first_name AS 'Master Customer Billing Contact First Name',
       bc.last_name AS 'Master Customer Billing Contact Last Name',
       bc.email AS 'Master Customer Billing Contact Email',
       bc.phone AS 'Master Customer Billing Contact Phone',
       tc.first_name AS 'Master Customer Tech Contact First Name',
       tc.last_name AS 'Master Customer Tech Contact Last Name',
       tc.email AS 'Master Customer Tech Contact Email',
       tc.phone AS 'Master Customer Tech Contact Phone',
       sc.first_name AS 'Master Customer Sales Contact First Name',
       sc.last_name AS 'Master Customer Sales Contact Last Name',
       sc.email AS 'Master Customer Sales Contact Email',
       sc.phone AS 'Master Customer Sales Contact Phone',
       ac.first_name AS 'Master Customer Authorization Contact First Name',
       ac.last_name AS 'Master Customer Authorization Contact Last Name',
       ac.email AS 'Master Customer Authorization Contact Email',
       ac.phone AS 'Master Customer Authorization Contact Phone'
FROM company c
     LEFT JOIN v_subject am ON c.account_manager = am.subject_id
     LEFT JOIN v_subject p ON c.provisioner = p.subject_id
     LEFT JOIN v_subject pm ON c.i90_project_manager = pm.subject_id
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

WHERE company_type = 'Master Customer'
	AND tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor')
