CREATE OR REPLACE VIEW pbi_i90_master_company AS
SELECT company_id AS master_customer_id,
       tenant_id AS "Tenant ID",
       company_name AS 'Master Customer',
       company_active AS 'Master Customer Active',
       billing_account_number AS 'Master Customer Billing Account Number',
       inventory_location_count AS 'Master Customer Inventory Location Count',
       inventory_mrc AS 'Master Customer Inventory MRC',
       account_notes AS 'Master Customer Account Notes',
       am.display_name AS 'Master Customer Account Manager',
       p.display_name AS 'Master Customer Provisioner',
       pm.display_name AS 'Master Customer Project Manager',
       company.address_1 AS 'Master Customer Address 1',
       company.address_2 AS 'Master Customer Address 2',
       company.city AS 'Master Customer City',
       company.state_province AS 'Master Customer State',
       company.postal_code AS 'Master Customer Zip',
       company.country AS 'Master Customer Country'
FROM company
     LEFT JOIN v_subject am ON company.account_manager = am.subject_id
     LEFT JOIN v_subject p ON company.provisioner = p.subject_id
     LEFT JOIN v_subject pm ON company.i90_project_manager = pm.subject_id
WHERE company_type = 'Master Customer'
	AND tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor')
