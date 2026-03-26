-- ==========================================================================
-- QTO Seed Data for Development
-- Run: docker exec -i qto-postgres psql -U qto_user -d qto-main < seed-data.sql
-- ==========================================================================

BEGIN;

-- ============================
-- 1. Tenant & Subject (Dev User)
-- ============================
INSERT INTO platform.tenant (tenant_id, version, active, name)
VALUES (1, 0, true, 'Endeavor')
ON CONFLICT (tenant_id) DO NOTHING;

INSERT INTO platform.subject (subject_id, version, active, display_name, email_address, username)
VALUES (1, 0, true, 'Dev User', 'devuser@endeavorms.com', 'devuser@endeavorms.com')
ON CONFLICT (subject_id) DO NOTHING;

INSERT INTO platform.tenant_subject (tenant_subject_id, version, tenant_subject_selected, subject_id, tenant_id)
VALUES (1, 0, true, 1, 1)
ON CONFLICT (tenant_subject_id) DO NOTHING;

-- Platform company (master customer in platform schema)
INSERT INTO platform.company (company_id, version, tenant_id, company_active, business_sector, company_name, company_type, company_uuid)
VALUES
  (1, 0, 1, true, 'Telecom', 'Acme Corporation', 'MASTER_CUSTOMER', 'acme-corp-uuid'),
  (2, 0, 1, true, 'Technology', 'TechNova Inc', 'MASTER_CUSTOMER', 'technova-uuid'),
  (3, 0, 1, true, 'Finance', 'FinServ Global', 'MASTER_CUSTOMER', 'finserv-uuid')
ON CONFLICT (company_id) DO NOTHING;

-- ============================
-- 2. Companies (public schema - order-level)
-- ============================
INSERT INTO company (company_id, version, tenant_id, master_customer_id, company_active, client_id, company_name, address_1, city, state_province, postal_code, country, company_type)
VALUES
  (1, 0, 1, NULL, true, 'ACME-001', 'Acme Corporation', '100 Main St', 'New York', 'NY', '10001', 'US', 'MASTER_CUSTOMER'),
  (2, 0, 1, NULL, true, 'TECH-001', 'TechNova Inc', '200 Innovation Blvd', 'San Francisco', 'CA', '94105', 'US', 'MASTER_CUSTOMER'),
  (3, 0, 1, NULL, true, 'FIN-001', 'FinServ Global', '300 Wall Street', 'Chicago', 'IL', '60601', 'US', 'MASTER_CUSTOMER'),
  (4, 0, 1, 1, true, 'ACME-SUB-001', 'Acme East Division', '101 East Ave', 'Boston', 'MA', '02101', 'US', 'END_CUSTOMER'),
  (5, 0, 1, 2, true, 'TECH-SUB-001', 'TechNova Cloud Div', '201 Cloud Way', 'Seattle', 'WA', '98101', 'US', 'END_CUSTOMER')
ON CONFLICT (company_id) DO NOTHING;

-- ============================
-- 3. Contacts
-- ============================
INSERT INTO contact (contact_id, version, tenant_id, contact_active, company_id, email, first_name, last_name, phone, contact_type, role)
VALUES
  (1, 0, 1, true, 1, 'john.doe@acme.com', 'John', 'Doe', '212-555-0101', 'SALES', 'Sales Manager'),
  (2, 0, 1, true, 1, 'jane.smith@acme.com', 'Jane', 'Smith', '212-555-0102', 'TECH', 'Technical Lead'),
  (3, 0, 1, true, 1, 'bob.jones@acme.com', 'Bob', 'Jones', '212-555-0103', 'BILLING', 'Billing Coordinator'),
  (4, 0, 1, true, 2, 'alice.chen@technova.com', 'Alice', 'Chen', '415-555-0201', 'SALES', 'Account Executive'),
  (5, 0, 1, true, 2, 'mike.wilson@technova.com', 'Mike', 'Wilson', '415-555-0202', 'TECH', 'Network Engineer'),
  (6, 0, 1, true, 3, 'sarah.lee@finserv.com', 'Sarah', 'Lee', '312-555-0301', 'SALES', 'VP Sales')
ON CONFLICT (contact_id) DO NOTHING;

-- ============================
-- 4. Orders (various statuses)
-- ============================
INSERT INTO orders (order_id, version, tenant_id, master_customer_id, company_id, client_order_id, order_status, quote_id, quote_number, last_update_by, last_update_date)
VALUES
  (1, 0, 1, 1, 1, 'ACME-ORD-2026-001', 'In Progress',    'Q-1001', 'QN-1001', 'devuser@endeavorms.com', NOW()),
  (2, 0, 1, 1, 1, 'ACME-ORD-2026-002', 'New',            'Q-1002', 'QN-1002', 'devuser@endeavorms.com', NOW()),
  (3, 0, 1, 2, 2, 'TECH-ORD-2026-001', 'In Progress',    'Q-2001', 'QN-2001', 'devuser@endeavorms.com', NOW()),
  (4, 0, 1, 2, 2, 'TECH-ORD-2026-002', 'Complete',       'Q-2002', 'QN-2002', 'devuser@endeavorms.com', NOW()),
  (5, 0, 1, 3, 3, 'FIN-ORD-2026-001',  'Pending',        NULL,     NULL,       'devuser@endeavorms.com', NOW())
ON CONFLICT (order_id) DO NOTHING;

-- Order contacts
INSERT INTO order_contact (order_id, contact_id)
VALUES (1, 1), (1, 2), (2, 1), (3, 4), (3, 5), (4, 4), (5, 6)
ON CONFLICT DO NOTHING;

-- ============================
-- 5. Locations (2-3 per order)
-- ============================
INSERT INTO location (location_id, version, tenant_id, master_customer_id, order_id, location_name, address_1, city, state_province, postal_code, country, location_status, active, last_update_by, last_update_date)
VALUES
  -- Order 1 locations
  (1,  0, 1, 1, 1, 'Acme HQ',              '100 Main St',         'New York',       'NY', '10001', 'US', 'In Progress', true, 'devuser@endeavorms.com', NOW()),
  (2,  0, 1, 1, 1, 'Acme Data Center',      '150 Server Lane',     'Newark',         'NJ', '07101', 'US', 'In Progress', true, 'devuser@endeavorms.com', NOW()),
  -- Order 2 locations
  (3,  0, 1, 1, 2, 'Acme Branch Office',    '500 Commerce Dr',     'Philadelphia',   'PA', '19101', 'US', 'New',         true, 'devuser@endeavorms.com', NOW()),
  -- Order 3 locations
  (4,  0, 1, 2, 3, 'TechNova Main Office',  '200 Innovation Blvd', 'San Francisco',  'CA', '94105', 'US', 'In Progress', true, 'devuser@endeavorms.com', NOW()),
  (5,  0, 1, 2, 3, 'TechNova Lab',          '250 Research Park',   'Palo Alto',      'CA', '94304', 'US', 'In Progress', true, 'devuser@endeavorms.com', NOW()),
  (6,  0, 1, 2, 3, 'TechNova Warehouse',    '300 Logistics Way',   'Oakland',        'CA', '94601', 'US', 'New',         true, 'devuser@endeavorms.com', NOW()),
  -- Order 4 locations
  (7,  0, 1, 2, 4, 'TechNova Cloud DC',     '400 Cloud Ave',       'Ashburn',        'VA', '20147', 'US', 'Complete',    true, 'devuser@endeavorms.com', NOW()),
  (8,  0, 1, 2, 4, 'TechNova Backup DC',    '410 Redundancy Rd',   'Dallas',         'TX', '75201', 'US', 'Complete',    true, 'devuser@endeavorms.com', NOW()),
  -- Order 5 locations
  (9,  0, 1, 3, 5, 'FinServ Trading Floor', '300 Wall Street',     'Chicago',        'IL', '60601', 'US', 'Pending',     true, 'devuser@endeavorms.com', NOW()),
  (10, 0, 1, 3, 5, 'FinServ DR Site',       '350 Backup Blvd',     'Phoenix',        'AZ', '85001', 'US', 'Pending',     true, 'devuser@endeavorms.com', NOW())
ON CONFLICT (location_id) DO NOTHING;

-- ============================
-- 6. Services (1-2 per location)
-- ============================
INSERT INTO service (service_id, version, tenant_id, master_customer_id, order_id, location_id, service_type, service_status, service_description, provider, service_mrc, service_nrc, service_mrr, service_nrr, download_speed, upload_speed, active, last_update_by, last_update_date)
VALUES
  -- Order 1 / Location 1 - Acme HQ
  (1,  0, 1, 1, 1, 1, 'DIA',       'In Progress', '1 Gbps DIA - Acme HQ',             'Lumen',    2500.00, 500.00, 3000.00, 600.00, '1000 Mbps', '1000 Mbps', true, 'devuser@endeavorms.com', NOW()),
  (2,  0, 1, 1, 1, 1, 'Broadband', 'In Progress', 'Backup Broadband - Acme HQ',       'Comcast',   150.00,   0.00,  200.00,   0.00, '500 Mbps',  '50 Mbps',   true, 'devuser@endeavorms.com', NOW()),
  -- Order 1 / Location 2 - Acme DC
  (3,  0, 1, 1, 1, 2, 'Ethernet',  'In Progress', '10 Gbps Ethernet - Acme DC',       'Zayo',     8000.00, 2000.00,10000.00,2500.00, '10000 Mbps','10000 Mbps',true, 'devuser@endeavorms.com', NOW()),
  -- Order 2 / Location 3
  (4,  0, 1, 1, 2, 3, 'DIA',       'New',         '500 Mbps DIA - Branch',             'AT&T',     1200.00, 300.00, 1500.00, 400.00, '500 Mbps',  '500 Mbps',  true, 'devuser@endeavorms.com', NOW()),
  -- Order 3 / Location 4 - TechNova Main
  (5,  0, 1, 2, 3, 4, 'DIA',       'In Progress', '2 Gbps DIA - TechNova Main',       'Cogent',   3500.00, 700.00, 4200.00, 840.00, '2000 Mbps', '2000 Mbps', true, 'devuser@endeavorms.com', NOW()),
  (6,  0, 1, 2, 3, 4, 'UCaaS',     'In Progress', 'UCaaS 200 seats - TechNova Main',  'RingCentral',4000.00, 0.00, 5000.00,   0.00,  NULL,        NULL,        true, 'devuser@endeavorms.com', NOW()),
  -- Order 3 / Location 5 - TechNova Lab
  (7,  0, 1, 2, 3, 5, 'Ethernet',  'In Progress', '1 Gbps Ethernet - TechNova Lab',   'Crown Castle',2000.00,500.00,2500.00, 600.00,'1000 Mbps', '1000 Mbps', true, 'devuser@endeavorms.com', NOW()),
  -- Order 4 / Location 7 - Cloud DC
  (8,  0, 1, 2, 4, 7, 'CrossConnect','Complete',   'Cross Connect - Cloud DC',         'Equinix',   300.00,  150.00, 400.00,  200.00, NULL,        NULL,        true, 'devuser@endeavorms.com', NOW()),
  (9,  0, 1, 2, 4, 7, 'DIA',       'Complete',    '10 Gbps DIA - Cloud DC',           'Zayo',    12000.00,3000.00,15000.00,3500.00, '10000 Mbps','10000 Mbps',true, 'devuser@endeavorms.com', NOW()),
  -- Order 4 / Location 8 - Backup DC
  (10, 0, 1, 2, 4, 8, 'DIA',       'Complete',    '5 Gbps DIA - Backup DC',           'Lumen',    6000.00,1500.00, 7500.00,1800.00, '5000 Mbps', '5000 Mbps', true, 'devuser@endeavorms.com', NOW()),
  -- Order 5 / Location 9 - Trading Floor
  (11, 0, 1, 3, 5, 9, 'MPLS',      'Pending',     'MPLS - Trading Floor',             'AT&T',     5000.00,1000.00, 6000.00,1200.00, '1000 Mbps', '1000 Mbps', true, 'devuser@endeavorms.com', NOW()),
  -- Order 5 / Location 10 - DR Site
  (12, 0, 1, 3, 5, 10,'DIA',       'Pending',     '1 Gbps DIA - DR Site',             'CenturyLink',2000.00,400.00,2500.00, 500.00, '1000 Mbps', '1000 Mbps', true, 'devuser@endeavorms.com', NOW())
ON CONFLICT (service_id) DO NOTHING;

-- Fix boolean primitive fields (Hibernate requires non-null for primitives)
UPDATE service SET auto_renewal = false, bill_to_location = false, billable = true, bundled = false,
  co_terminus = false, eligible_for_inventory = false, eligible_for_update = false,
  expedite_order = false, final_update = false, has_icb = false, has_osp = false,
  ignore_for_renewals = false, linked = false, linked_bundled_parent = false,
  managed_service = false, marked_for_deletion = false, production_impacting = false,
  update_client = false
WHERE auto_renewal IS NULL;

-- ============================
-- 7. Quotes
-- ============================
INSERT INTO quote (quote_id, tenant_id, account_id, account_name, vendor_quote_id, quote_number, order_id, user_name, user_email, quote_provider, quote_handled_time)
VALUES
  (1, 1, 1, 'Acme Corporation',  'VQ-1001', 'QN-1001', 1, 'Dev User', 'devuser@endeavorms.com', 'Manual', NOW()),
  (2, 1, 1, 'Acme Corporation',  'VQ-1002', 'QN-1002', 2, 'Dev User', 'devuser@endeavorms.com', 'Manual', NOW()),
  (3, 1, 2, 'TechNova Inc',      'VQ-2001', 'QN-2001', 3, 'Dev User', 'devuser@endeavorms.com', 'API',    NOW())
ON CONFLICT (quote_id) DO NOTHING;

-- ============================
-- 8. Config Properties (needed by Angular UI)
-- ============================
INSERT INTO config_property (config_property_id, version, config_property_key, config_property_value)
VALUES
  (1, 0, 'TELECOM_CLIENT', 'true'),
  (2, 0, 'DEMO_MODE', 'false'),
  (3, 0, 'APP_VERSION', '1.18.1')
ON CONFLICT (config_property_id) DO NOTHING;

INSERT INTO company_config_property (company_config_property_id, version, tenant_id, company_id, config_property_key, config_property_value, description, modifiable, type)
VALUES
  (1, 0, 1, NULL, 'TELECOM_CLIENT', 'true', 'Enable telecom client features', true, 'BOOLEAN')
ON CONFLICT (company_config_property_id) DO NOTHING;

-- ============================
-- 9. Lookup Types & Values (dropdowns)
-- ============================
INSERT INTO lookup_type (lookup_type_id, version, lookup_type_active, category, modifiable, lookup_type_descr, lookup_type_code) VALUES
  (1, 0, true, 'SERVICE', false, 'Service Type',     'SERVICE_TYPE'),
  (2, 0, true, 'ORDER',   false, 'Order Status',     'ORDER_STATUS'),
  (3, 0, true, 'SERVICE', false, 'Service Status',   'SERVICE_STATUS'),
  (4, 0, true, 'LOCATION',false, 'Location Status',  'LOCATION_STATUS'),
  (5, 0, true, 'SERVICE', false, 'Provider',         'PROVIDER'),
  (6, 0, true, 'ORDER',   false, 'Order Type',       'ORDER_TYPE')
ON CONFLICT (lookup_type_id) DO NOTHING;

INSERT INTO lookup_value (lookup_value_id, version, tenant_id, lookup_value_active, lookup_display, sort_seq, lookup_value, lookup_type_id) VALUES
  -- Service Types
  (1,  0, 1, true, 'DIA',            1, 'DIA',          1),
  (2,  0, 1, true, 'Broadband',      2, 'Broadband',    1),
  (3,  0, 1, true, 'Ethernet',       3, 'Ethernet',     1),
  (4,  0, 1, true, 'MPLS',           4, 'MPLS',         1),
  (5,  0, 1, true, 'UCaaS',          5, 'UCaaS',        1),
  (6,  0, 1, true, 'CrossConnect',   6, 'CrossConnect', 1),
  (7,  0, 1, true, 'Television',     7, 'Television',   1),
  (8,  0, 1, true, '4G/5G',          8, '4G5G',         1),
  -- Order Statuses
  (10, 0, 1, true, 'New',            1, 'New',          2),
  (11, 0, 1, true, 'In Progress',    2, 'In Progress',  2),
  (12, 0, 1, true, 'Pending',        3, 'Pending',      2),
  (13, 0, 1, true, 'Complete',       4, 'Complete',     2),
  (14, 0, 1, true, 'Cancelled',      5, 'Cancelled',   2),
  -- Service Statuses
  (20, 0, 1, true, 'New',            1, 'New',          3),
  (21, 0, 1, true, 'In Progress',    2, 'In Progress',  3),
  (22, 0, 1, true, 'Pending',        3, 'Pending',      3),
  (23, 0, 1, true, 'Complete',       4, 'Complete',     3),
  (24, 0, 1, true, 'Cancelled',      5, 'Cancelled',   3),
  -- Location Statuses
  (30, 0, 1, true, 'New',            1, 'New',          4),
  (31, 0, 1, true, 'In Progress',    2, 'In Progress',  4),
  (32, 0, 1, true, 'Pending',        3, 'Pending',      4),
  (33, 0, 1, true, 'Complete',       4, 'Complete',     4),
  -- Providers
  (40, 0, 1, true, 'Lumen',          1,  'Lumen',       5),
  (41, 0, 1, true, 'AT&T',           2,  'AT&T',        5),
  (42, 0, 1, true, 'Comcast',        3,  'Comcast',     5),
  (43, 0, 1, true, 'Zayo',           4,  'Zayo',        5),
  (44, 0, 1, true, 'Cogent',         5,  'Cogent',      5),
  (45, 0, 1, true, 'Crown Castle',   6,  'Crown Castle',5),
  (46, 0, 1, true, 'Equinix',        7,  'Equinix',     5),
  (47, 0, 1, true, 'CenturyLink',    8,  'CenturyLink', 5),
  (48, 0, 1, true, 'RingCentral',    9,  'RingCentral', 5),
  -- Order Types
  (50, 0, 1, true, 'New Install',    1, 'New Install',  6),
  (51, 0, 1, true, 'MACD',           2, 'MACD',         6),
  (52, 0, 1, true, 'Disconnect',     3, 'Disconnect',   6),
  (53, 0, 1, true, 'Renewal',        4, 'Renewal',      6)
ON CONFLICT (lookup_value_id) DO NOTHING;

-- Reset sequences to avoid collisions
SELECT setval('platform.tenant_tenant_id_seq', (SELECT MAX(tenant_id) FROM platform.tenant));
SELECT setval('platform.subject_subject_id_seq', (SELECT MAX(subject_id) FROM platform.subject));
SELECT setval('platform.tenant_subject_tenant_subject_id_seq', (SELECT MAX(tenant_subject_id) FROM platform.tenant_subject));
SELECT setval('platform.company_company_id_seq', (SELECT MAX(company_id) FROM platform.company));
SELECT setval('company_company_id_seq', (SELECT MAX(company_id) FROM company));
SELECT setval('contact_contact_id_seq', (SELECT MAX(contact_id) FROM contact));
SELECT setval('orders_order_id_seq', (SELECT MAX(order_id) FROM orders));
SELECT setval('location_location_id_seq', (SELECT MAX(location_id) FROM location));
SELECT setval('service_service_id_seq', (SELECT MAX(service_id) FROM service));
SELECT setval('quote_quote_id_seq', (SELECT MAX(quote_id) FROM quote));
SELECT setval('config_property_config_property_id_seq', (SELECT MAX(config_property_id) FROM config_property));
SELECT setval('company_config_property_company_config_property_id_seq', (SELECT MAX(company_config_property_id) FROM company_config_property));
SELECT setval('lookup_type_lookup_type_id_seq', (SELECT MAX(lookup_type_id) FROM lookup_type));
SELECT setval('lookup_value_lookup_value_id_seq', (SELECT MAX(lookup_value_id) FROM lookup_value));

-- ============================
-- 10. Milestones (needed for WIP views)
-- ============================
INSERT INTO milestone (milestone_id, version, milestone_name, milestone_code)
VALUES
  (1, 0, 'Created',                      'CREATED'),
  (2, 0, 'Customer Requested Install',   'CUSTOMER_REQUESTED_INSTALL'),
  (3, 0, 'Provider Order Submitted',     'PROVIDER_ORDER_SUBMITTED'),
  (4, 0, 'Network Provider FOC',         'NETWORK_PROVIDER_FOC'),
  (5, 0, 'Data Provisioning Complete',   'DATA_PROVISIONING_COMPLETE'),
  (6, 0, 'Complete',                     'COMPLETE'),
  (7, 0, 'Site Survey Submit',           'SITE_SURVEY_SUBMIT'),
  (8, 0, 'Site Survey Due',              'SITE_SURVEY_DUE'),
  (9, 0, 'Access Circuit FOC',           'ACCESS_CIRCUIT_FOC')
ON CONFLICT (milestone_id) DO NOTHING;

-- Milestone instances (timestamps for various services)
INSERT INTO milestone_instance (milestone_instance_id, version, tenant_id, milestone_id, milestone_date)
VALUES
  -- Service 1 (Acme HQ DIA) - In Progress, data provisioning done
  (1,  0, 1, 1, '2025-09-15 10:00:00'),   -- CREATED
  (2,  0, 1, 3, '2025-09-20 14:00:00'),   -- PROVIDER_ORDER_SUBMITTED
  (3,  0, 1, 5, '2025-11-01 09:00:00'),   -- DATA_PROVISIONING_COMPLETE
  -- Service 2 (Acme HQ Broadband) - In Progress
  (4,  0, 1, 1, '2025-09-15 10:00:00'),   -- CREATED
  (5,  0, 1, 3, '2025-09-22 11:00:00'),   -- PROVIDER_ORDER_SUBMITTED
  -- Service 3 (Acme DC Ethernet) - In Progress, data provisioning done
  (6,  0, 1, 1, '2025-10-01 08:00:00'),   -- CREATED
  (7,  0, 1, 5, '2025-12-15 16:00:00'),   -- DATA_PROVISIONING_COMPLETE
  -- Service 5 (TechNova DIA) - In Progress, data provisioning done
  (8,  0, 1, 1, '2025-08-01 09:00:00'),   -- CREATED
  (9,  0, 1, 5, '2025-10-15 10:00:00'),   -- DATA_PROVISIONING_COMPLETE
  -- Service 6 (TechNova UCaaS) - In Progress, data provisioning done
  (10, 0, 1, 1, '2025-08-01 09:00:00'),   -- CREATED
  (11, 0, 1, 5, '2026-01-10 14:00:00'),   -- DATA_PROVISIONING_COMPLETE
  -- Service 7 (TechNova Lab Ethernet) - In Progress
  (12, 0, 1, 1, '2025-08-15 09:00:00'),   -- CREATED
  (13, 0, 1, 3, '2025-09-01 10:00:00'),   -- PROVIDER_ORDER_SUBMITTED
  (14, 0, 1, 5, '2025-11-20 11:00:00'),   -- DATA_PROVISIONING_COMPLETE
  -- Service 8 (Cloud DC CrossConnect) - Complete
  (15, 0, 1, 1, '2025-03-01 09:00:00'),   -- CREATED
  (16, 0, 1, 5, '2025-04-15 14:00:00'),   -- DATA_PROVISIONING_COMPLETE
  (17, 0, 1, 6, '2025-05-01 10:00:00'),   -- COMPLETE
  -- Service 9 (Cloud DC DIA) - Complete
  (18, 0, 1, 1, '2025-03-01 09:00:00'),   -- CREATED
  (19, 0, 1, 5, '2025-05-10 14:00:00'),   -- DATA_PROVISIONING_COMPLETE
  (20, 0, 1, 6, '2025-06-01 10:00:00'),   -- COMPLETE
  -- Service 10 (Backup DC DIA) - Complete
  (21, 0, 1, 1, '2025-04-01 09:00:00'),   -- CREATED
  (22, 0, 1, 5, '2025-06-15 14:00:00'),   -- DATA_PROVISIONING_COMPLETE
  (23, 0, 1, 6, '2025-07-01 10:00:00'),   -- COMPLETE
  -- Service 4 (Acme Branch DIA) - New
  (24, 0, 1, 1, '2026-01-15 09:00:00'),   -- CREATED
  -- Service 11 (FinServ MPLS) - Pending
  (25, 0, 1, 1, '2026-02-01 09:00:00'),   -- CREATED
  -- Service 12 (FinServ DR DIA) - Pending
  (26, 0, 1, 1, '2026-02-01 09:00:00')    -- CREATED
ON CONFLICT (milestone_instance_id) DO NOTHING;

-- Service milestone instances (link services to milestones)
INSERT INTO service_milestone_instance (service_id, milestone_instance_id)
VALUES
  (1, 1), (1, 2), (1, 3),       -- Service 1
  (2, 4), (2, 5),                -- Service 2
  (3, 6), (3, 7),                -- Service 3
  (5, 8), (5, 9),                -- Service 5
  (6, 10), (6, 11),              -- Service 6
  (7, 12), (7, 13), (7, 14),    -- Service 7
  (8, 15), (8, 16), (8, 17),    -- Service 8
  (9, 18), (9, 19), (9, 20),    -- Service 9
  (10, 21), (10, 22), (10, 23), -- Service 10
  (4, 24),                       -- Service 4
  (11, 25),                      -- Service 11
  (12, 26)                       -- Service 12
ON CONFLICT DO NOTHING;

-- ============================
-- 11. Invoices & Invoice Charges
-- ============================
INSERT INTO invoice (invoice_id, version, tenant_id, client_name, invoice_number, invoice_status, total_charges, invoice_start, invoice_end, generated_by, generated_date)
VALUES
  (1, 0, 1, 'Acme Corporation',  'INV-2026-001', 'Draft',  15250.00, '2026-01-01', '2026-01-31', 'devuser@endeavorms.com', '2026-02-05 10:00:00'),
  (2, 0, 1, 'TechNova Inc',      'INV-2026-002', 'Final', 21500.00, '2026-01-01', '2026-01-31', 'devuser@endeavorms.com', '2026-02-10 14:00:00'),
  (3, 0, 1, 'Acme Corporation',  'INV-2026-003', 'Draft',   8650.00, '2026-02-01', '2026-02-28', 'devuser@endeavorms.com', '2026-03-05 09:00:00'),
  (4, 0, 1, 'TechNova Inc',      'INV-2026-004', 'Draft',  9500.00, '2026-02-01', '2026-02-28', 'devuser@endeavorms.com', '2026-03-05 09:00:00')
ON CONFLICT (invoice_id) DO NOTHING;

INSERT INTO invoice_charge (invoice_charge_id, version, tenant_id, invoice_id, location_id, charge_credit, unit_cost, invoiced_amount, item_desc, charge_desc, charge_type, charge_level, master_customer_name, end_customer_name, billable_event_milestone_description, billable_event_date, previously_billed)
VALUES
  -- Invoice 1 charges (Acme Corp January)
  (1, 0, 1, 1, 1, 'Charge', 2500.00, 2500.00, '1 Gbps DIA',           'Monthly Recurring',  'MRC',  'Service', 'Acme Corporation', 'Acme Corporation', 'Data Provisioning Complete', '2025-11-01', 0.00),
  (2, 0, 1, 1, 1, 'Charge',  150.00,  150.00, 'Backup Broadband',      'Monthly Recurring',  'MRC',  'Service', 'Acme Corporation', 'Acme Corporation', 'Provider Order Submitted',   '2025-09-22', 0.00),
  (3, 0, 1, 1, 2, 'Charge', 8000.00, 8000.00, '10 Gbps Ethernet',      'Monthly Recurring',  'MRC',  'Service', 'Acme Corporation', 'Acme Corporation', 'Data Provisioning Complete', '2025-12-15', 0.00),
  (4, 0, 1, 1, 2, 'Charge', 2000.00, 2000.00, '10 Gbps Ethernet',      'Installation Fee',   'NRC',  'Service', 'Acme Corporation', 'Acme Corporation', 'Provider Order Submitted',   '2025-10-01', 0.00),
  (5, 0, 1, 1, 1, 'Charge',  500.00,  500.00, '1 Gbps DIA',            'Installation Fee',   'NRC',  'Service', 'Acme Corporation', 'Acme Corporation', 'Provider Order Submitted',   '2025-09-20', 0.00),
  (6, 0, 1, 1, 1, 'Credit', -100.00, -100.00, 'Service Credit',        'Outage Credit',      'Credit','Service','Acme Corporation', 'Acme Corporation', 'Complete',                   '2025-11-15', 2500.00),
  -- Invoice 2 charges (TechNova January)
  (7,  0, 1, 2, 4, 'Charge', 3500.00, 3500.00, '2 Gbps DIA',           'Monthly Recurring',  'MRC',  'Service', 'TechNova Inc', 'TechNova Inc', 'Data Provisioning Complete', '2025-10-15', 0.00),
  (8,  0, 1, 2, 4, 'Charge', 4000.00, 4000.00, 'UCaaS 200 seats',      'Monthly Recurring',  'MRC',  'Service', 'TechNova Inc', 'TechNova Inc', 'Data Provisioning Complete', '2026-01-10', 0.00),
  (9,  0, 1, 2, 5, 'Charge', 2000.00, 2000.00, '1 Gbps Ethernet',      'Monthly Recurring',  'MRC',  'Service', 'TechNova Inc', 'TechNova Inc', 'Data Provisioning Complete', '2025-11-20', 0.00),
  (10, 0, 1, 2, 7, 'Charge',  300.00,  300.00, 'Cross Connect',         'Monthly Recurring',  'MRC',  'Service', 'TechNova Inc', 'TechNova Inc', 'Complete',                   '2025-05-01', 0.00),
  (11, 0, 1, 2, 7, 'Charge',12000.00,12000.00, '10 Gbps DIA',          'Monthly Recurring',  'MRC',  'Service', 'TechNova Inc', 'TechNova Inc', 'Complete',                   '2025-06-01', 0.00),
  -- Invoice 3 charges (Acme February)
  (12, 0, 1, 3, 1, 'Charge', 2500.00, 2500.00, '1 Gbps DIA',           'Monthly Recurring',  'MRC',  'Service', 'Acme Corporation', 'Acme Corporation', 'Data Provisioning Complete', '2025-11-01', 2500.00),
  (13, 0, 1, 3, 1, 'Charge',  150.00,  150.00, 'Backup Broadband',      'Monthly Recurring',  'MRC',  'Service', 'Acme Corporation', 'Acme Corporation', 'Provider Order Submitted',   '2025-09-22', 150.00),
  (14, 0, 1, 3, 2, 'Charge', 8000.00, 8000.00, '10 Gbps Ethernet',      'Monthly Recurring',  'MRC',  'Service', 'Acme Corporation', 'Acme Corporation', 'Data Provisioning Complete', '2025-12-15', 8000.00),
  -- Invoice 4 charges (TechNova February)
  (15, 0, 1, 4, 4, 'Charge', 3500.00, 3500.00, '2 Gbps DIA',           'Monthly Recurring',  'MRC',  'Service', 'TechNova Inc', 'TechNova Inc', 'Data Provisioning Complete', '2025-10-15', 3500.00),
  (16, 0, 1, 4, 4, 'Charge', 4000.00, 4000.00, 'UCaaS 200 seats',      'Monthly Recurring',  'MRC',  'Service', 'TechNova Inc', 'TechNova Inc', 'Data Provisioning Complete', '2026-01-10', 4000.00),
  (17, 0, 1, 4, 5, 'Charge', 2000.00, 2000.00, '1 Gbps Ethernet',      'Monthly Recurring',  'MRC',  'Service', 'TechNova Inc', 'TechNova Inc', 'Data Provisioning Complete', '2025-11-20', 2000.00)
ON CONFLICT (invoice_charge_id) DO NOTHING;

-- ============================
-- 12. Surcharge Types & Surcharges
-- ============================
INSERT INTO surcharge_type (surcharge_type_id, version, tenant_id, surcharge_type, surcharge_level, surcharge_amount, start_date)
VALUES
  (1, 0, 1, 'Expedite Fee',              'Service', 250.00, '2025-01-01'),
  (2, 0, 1, 'Late Payment',              'Invoice', 50.00,  '2025-01-01'),
  (3, 0, 1, 'Network Outage Credit',     'Service', -100.00,'2025-01-01'),
  (4, 0, 1, 'Regulatory Recovery Fee',   'Invoice', 15.00,  '2025-01-01')
ON CONFLICT (surcharge_type_id) DO NOTHING;

-- ============================
-- 13. Additional Subjects (for PM resolution)
-- ============================
INSERT INTO platform.subject (subject_id, version, active, display_name, email_address, username)
VALUES
  (2, 0, true, 'Alice Manager', 'alice.manager@endeavorms.com', 'alice.manager@endeavorms.com'),
  (3, 0, true, 'Bob Provisioner', 'bob.provisioner@endeavorms.com', 'bob.provisioner@endeavorms.com')
ON CONFLICT (subject_id) DO NOTHING;

-- Update orders with provisioner and project manager assignments
UPDATE orders SET provisioner = 3, vertek_project_manager = 2 WHERE order_id = 1;
UPDATE orders SET provisioner = 3, vertek_project_manager = 2 WHERE order_id = 2;
UPDATE orders SET provisioner = 3, vertek_project_manager = 1 WHERE order_id = 3;
UPDATE orders SET provisioner = 3, vertek_project_manager = 1 WHERE order_id = 4;
UPDATE orders SET provisioner = 3, vertek_project_manager = 2 WHERE order_id = 5;

-- Update services with service_billed_to and current_inventory
UPDATE service SET service_billed_to = 'Endeavor', current_inventory = false WHERE service_id IN (1, 2, 3, 4, 5, 6, 7, 11, 12);
UPDATE service SET service_billed_to = 'Customer', current_inventory = true WHERE service_id IN (8, 9, 10);

-- Add data_provisioning_complete_date equivalent via milestone data (already done above)
-- Services 1,3,5,6,7 have data_provisioning_complete but NO complete milestone = expense accrual candidates

-- ============================
-- 14. Reset additional sequences
-- ============================
SELECT setval('milestone_milestone_id_seq', (SELECT MAX(milestone_id) FROM milestone));
SELECT setval('milestone_instance_milestone_instance_id_seq', (SELECT MAX(milestone_instance_id) FROM milestone_instance));
SELECT setval('invoice_invoice_id_seq', (SELECT MAX(invoice_id) FROM invoice));
SELECT setval('invoice_charge_invoice_charge_id_seq', (SELECT MAX(invoice_charge_id) FROM invoice_charge));
SELECT setval('surcharge_type_surcharge_type_id_seq', (SELECT MAX(surcharge_type_id) FROM surcharge_type));
SELECT setval('platform.subject_subject_id_seq', (SELECT MAX(subject_id) FROM platform.subject));

-- ============================
-- 15. Company-Subject links (security filtering)
-- ============================
INSERT INTO company_subject (company_subject_id, version, company_id, subject_id, company_subject_selected) VALUES
(1, 0, 1, 1, true),
(2, 0, 2, 1, false),
(3, 0, 3, 1, false)
ON CONFLICT (company_subject_id) DO NOTHING;

-- Master customers need master_customer_id set to self for WIP view join
UPDATE company SET master_customer_id = company_id WHERE company_type = 'MASTER_CUSTOMER' AND master_customer_id IS NULL;

COMMIT;
