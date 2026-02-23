DROP VIEW IF EXISTS pbi_i90_activation_issues CASCADE;
CREATE OR REPLACE VIEW pbi_i90_activation_issues AS
SELECT s.service_id,
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
       aa.activation_attempt_id AS 'Activation Attempt ID',
       CONCAT('svc-', s.service_id,
              IF(aa.activation_attempt_id IS NULL, '',
                 CONCAT('aa-', aa.activation_attempt_id))) AS 'Composite ID',
       activation_issue_id AS 'Activation Issue ID',
       primary_root_cause AS 'Primary Root Cause',
       secondary_root_cause AS 'Secondary Root Cause',
       ai.tertiary_root_cause AS 'Tertiary Root Cause',
       ai.issue_rank AS 'Rank',
       note AS Note
FROM service s
     JOIN activation_attempt aa ON s.service_id = aa.service_id
     JOIN activation_issue ai ON aa.activation_attempt_id = ai.activation_attempt_id
WHERE s.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');
