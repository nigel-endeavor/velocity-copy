SET @tenantId = (SELECT tenant_id FROM v_tenant WHERE name = 'Demo Tenant');

INSERT INTO custom_field (name, label, type, active, required, tenant_id)
VALUES
    ('ORDER_NUMBER', 'Order #', 'TEXT', true, false, @tenantId),
    ('BTN', 'BTN', 'TEXT', true, false, @tenantId);

INSERT INTO custom_field_tab (custom_field_id, tab)
VALUES
    ((SELECT custom_field_id FROM custom_field WHERE name = 'LUMEN_CIE_REP' AND tenant_id = @tenantId), 'NEW_ORDER_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'LUMEN_REGION' AND tenant_id = @tenantId), 'NEW_ORDER_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'IN_REGION_NEED_TAX_ID' AND tenant_id = @tenantId), 'NEW_ORDER_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'IN_OUT_REGION_NEED_SALES_ID' AND tenant_id = @tenantId), 'NEW_ORDER_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'LUMEN_AG' AND tenant_id = @tenantId), 'NEW_ORDER_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'ORDER_NUMBER' AND tenant_id = @tenantId), 'NEW_ORDER_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'BTN' AND tenant_id = @tenantId), 'NEW_ORDER_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'ORDER_NUMBER' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE'),
    ((SELECT custom_field_id FROM custom_field WHERE name = 'BTN' AND tenant_id = @tenantId), 'SERVICE_BROKERAGE');


