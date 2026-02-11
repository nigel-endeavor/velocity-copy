CREATE OR REPLACE VIEW pbi_i90_service_cyber_brokerage AS
SELECT s.service_id AS 'Service ID',
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
       commissionable_mrc AS 'Commissionable MRC',
       commissionable_nrc AS 'Commissionable NRC',
       agent AS 'Agent',
       agent_rep AS 'Agent Rep',
       agent_percent / 100 AS 'Agent Percent',
       sub_agent_percent / 100 AS 'Sub Agent Percent',
       sub_agent AS 'Sub Agent',
       sub_agent_rep AS 'Sub Agent Rep',
       referral AS 'Referral',
       referral_name AS 'Referral Name',
       referral_percent / 100 AS 'Company Referral Percent',
       commission_payment_type AS 'Commission Payment Type',
       expected_commission AS 'Expected Commission',
       commission_reduction_percent / 100 AS 'Commission Reduction Percent',
       internal_commissions_comments AS 'Internal Commissions Comments'
FROM service_brokerage sb
     JOIN service s ON sb.service_id = s.service_id
WHERE s.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');
