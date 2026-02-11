set @tenantId = (select tenant_id from v_tenant where name = 'QTO First Tenant');
SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'TENANT_SERVICE_TYPES');
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, tenant_id)
VALUES (@lookupTypeCode, '4G/5G', '4G/5G', 0, 1, @tenantId),
       (@lookupTypeCode, 'Broadband', 'Broadband', 0, 1,  @tenantId),
       (@lookupTypeCode, 'DIA', 'DIA', 0, 1,  @tenantId),
       (@lookupTypeCode, 'Ethernet', 'Ethernet', 0, 1,  @tenantId),
       (@lookupTypeCode, 'MPLS', 'MPLS', 0, 1,  @tenantId),
       (@lookupTypeCode, 'Cross Connect', 'Cross Connect', 0, 1,  @tenantId),
       (@lookupTypeCode, 'Television', 'Television', 0, 1,  @tenantId),
       (@lookupTypeCode, 'UCaaS', 'UCaaS', 0, 1,  @tenantId);

set @tenantId = (select tenant_id from v_tenant where name = 'Demo Tenant');
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, tenant_id)
VALUES (@lookupTypeCode, '4G/5G', '4G/5G', 0, 1, @tenantId),
       (@lookupTypeCode, 'Broadband', 'Broadband', 0, 1,  @tenantId),
       (@lookupTypeCode, 'DIA', 'DIA', 0, 1,  @tenantId),
       (@lookupTypeCode, 'Ethernet', 'Ethernet', 0, 1,  @tenantId),
       (@lookupTypeCode, 'MPLS', 'MPLS', 0, 1,  @tenantId),
       (@lookupTypeCode, 'Cross Connect', 'Cross Connect', 0, 1,  @tenantId),
       (@lookupTypeCode, 'Television', 'Television', 0, 1,  @tenantId),
       (@lookupTypeCode, 'UCaaS', 'UCaaS', 0, 1,  @tenantId);

set @tenantId = (select tenant_id from v_tenant where name = 'Endeavor');
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, tenant_id)
VALUES (@lookupTypeCode, '4G/5G', '4G/5G', 0, 1, @tenantId),
       (@lookupTypeCode, 'Broadband', 'Broadband', 0, 1,  @tenantId),
       (@lookupTypeCode, 'DIA', 'DIA', 0, 1,  @tenantId),
       (@lookupTypeCode, 'Ethernet', 'Ethernet', 0, 1,  @tenantId),
       (@lookupTypeCode, 'MPLS', 'MPLS', 0, 1,  @tenantId),
       (@lookupTypeCode, 'Cross Connect', 'Cross Connect', 0, 1,  @tenantId),
       (@lookupTypeCode, 'Television', 'Television', 0, 1,  @tenantId),
       (@lookupTypeCode, 'UCaaS', 'UCaaS', 0, 1,  @tenantId);

update company_config_property set config_property_value = '4G/5G, Broadband, Cross Connect, DIA, Ethernet, MPLS, Television, UCaaS' where config_property_key = 'ASSIGNED_SERVICE_TYPES';
