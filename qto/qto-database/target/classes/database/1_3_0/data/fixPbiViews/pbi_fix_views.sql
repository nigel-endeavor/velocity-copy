

CREATE OR REPLACE VIEW pbi_iss_service_notes AS
SELECT sn.service_id AS 'service ID',
       n.note AS Note,
       n.created_by AS 'Created By',
       n.created_date AS 'Created Date',
       n.internal_only AS 'Internal Only'
FROM service_note sn
     LEFT JOIN note n ON sn.note_id = n.note_id
WHERE n.tenant_id IN (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');


CREATE OR REPLACE VIEW pbi_iss_activation_issues AS
SELECT s.service_id,
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
     LEFT JOIN activation_attempt aa ON s.service_id = aa.service_id
     LEFT JOIN activation_issue ai ON aa.activation_attempt_id = ai.activation_attempt_id
WHERE s.tenant_id = (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');
