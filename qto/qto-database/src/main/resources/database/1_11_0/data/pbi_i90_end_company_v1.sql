DROP VIEW IF EXISTS pbi_i90_end_company CASCADE;
CREATE OR REPLACE VIEW pbi_i90_end_company AS
SELECT company_id AS end_company_id,
       master_customer_id AS master_customer_id,
       tenant_id AS "Tenant ID",
       company_name AS 'End Customer',
       company_active AS 'End Customer Active',
       billing_account_number AS 'End Customer Billing Account Number',
       inventory_location_count AS 'End Customer Inventory Location Count',
       inventory_mrc AS 'End Customer Inventory MRC',
       account_notes AS 'End Customer Account Notes',
       am.display_name AS 'End Customer Account Manager',
       p.display_name AS 'End Customer Provisioner',
       pm.display_name AS 'End Customer Project Manager',
       company.address_1 AS 'End Customer Address 1',
       company.address_2 AS 'End Customer Address 2',
       company.city AS 'End Customer City',
       company.state_province AS 'End Customer State',
       company.postal_code AS 'End Customer Zip',
       company.country AS 'End Customer Country'
FROM company
     LEFT JOIN v_subject am ON company.account_manager = am.subject_id
     LEFT JOIN v_subject p ON company.provisioner = p.subject_id
     LEFT JOIN v_subject pm ON company.i90_project_manager = pm.subject_id
WHERE company_type = 'End Customer'
	AND tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor')
