SET @tenantId = (SELECT tenant_id FROM v_tenant WHERE name = 'Demo Tenant');

INSERT INTO custom_field (name, label, type, active, required, tenant_id)
VALUES
    ('LUMEN_CIE_REP', 'Lumen CIE Rep', 'TEXT', true, false, @tenantId),
    ('LUMEN_REGION', 'Lumen Region', 'DROPDOWN', true, false, @tenantId),
    ('LUMEN_CORE_NUMBER', 'Lumen Core #', 'TEXT', true, false, @tenantId),
    ('LUMEN_AG_ORDER', 'Lumen AG Order', 'BOOLEAN', true, false, @tenantId),
    ('LUMEN_CIE_APPROVAL_NUMBER', 'Lumen CIE Approval #', 'TEXT', true, false, @tenantId),
    ('IN_REGION_NEED_TAX_ID', 'In Region- Need Tax ID', 'TEXT', true, false, @tenantId),
    ('IN_OUT_REGION_NEED_SALES_ID', 'In & Out of Region- Need Sales ID', 'TEXT', true, false, @tenantId),
    ('LUMEN_AG', 'Lumen AG', 'TEXT', true, false, @tenantId),
    ('LUMEN_RELATED_ORDER_NUMBER', 'Lumen related Order #', 'TEXT', true, false, @tenantId),
    ('CIE_TEAMED_DEAL_INFO', 'CIE/Teamed Deal info', 'TEXT', true, false, @tenantId),
    ('INTERNET_DEDICATED_SERVICES_SERVICE_ID', 'Internet Dedicated Services, Service ID', 'TEXT', true, false, @tenantId),
    ('ORDER_REQUEST_ID', 'Order Request ID#', 'TEXT', true, false, @tenantId),
    ('TELARUS_ID_NUMBER', 'Telarus ID Number', 'TEXT', true, false, @tenantId),
    ('SUBACCOUNT_NUMBER', 'Subaccount #', 'TEXT', true, false, @tenantId),
    ('ORIGINAL_REPORTED_MRC', 'Original Reported MRC', 'CURRENCY', true, false, @tenantId);

INSERT INTO custom_field_tab (custom_field_id, tab)
VALUES
    ((SELECT custom_field_id FROM custom_field WHERE name = 'LUMEN_CIE_REP' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'LUMEN_REGION' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'LUMEN_CORE_NUMBER' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'LUMEN_AG_ORDER' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'LUMEN_CIE_APPROVAL_NUMBER' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'IN_REGION_NEED_TAX_ID' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'IN_OUT_REGION_NEED_SALES_ID' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'LUMEN_AG' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'LUMEN_RELATED_ORDER_NUMBER' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'CIE_TEAMED_DEAL_INFO' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'INTERNET_DEDICATED_SERVICES_SERVICE_ID' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'ORDER_REQUEST_ID' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'TELARUS_ID_NUMBER' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'SUBACCOUNT_NUMBER' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'ORIGINAL_REPORTED_MRC' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE');