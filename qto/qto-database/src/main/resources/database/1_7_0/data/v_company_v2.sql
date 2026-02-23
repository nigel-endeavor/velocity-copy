DROP VIEW IF EXISTS v_company CASCADE;
CREATE OR REPLACE VIEW v_company AS
    SELECT
        c.*,
        TRIM(CONCAT(CASE WHEN (bc.first_name is not null) THEN bc.first_name ELSE '' END, ' ', CASE WHEN (bc.last_name is not null) THEN bc.last_name ELSE '' END)) AS billing_contact_name,
        bc.email AS billing_contact_email,
        bc.phone AS billing_contact_phone,
        t.name as tenant_name
    FROM company c
    LEFT JOIN contact bc ON bc.company_id = c.company_id AND bc.contact_type = 'BILLING'
    JOIN v_tenant t ON t.tenant_id = c.tenant_id;