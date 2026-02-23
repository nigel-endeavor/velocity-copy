DROP VIEW IF EXISTS pbi_i90_location CASCADE;
CREATE OR REPLACE VIEW pbi_i90_location AS
SELECT l.location_id AS 'Location ID',
       o.company_id AS 'Company ID',
       l.tenant_id AS 'Tenant ID',
       l.current_inventory AS 'Current Inventory',
       pr.display_name AS 'Provisioner',
       o.client_order_id AS 'Client Order ID',
       vpm.display_name AS 'i90 Project Manager',
       ae.display_name AS 'Activation Engineer',
       client_project_manager AS 'Client Project Manager',
       qa.display_name AS 'QA Manager',
       l.address_1 AS 'Address 1',
       l.address_2 AS 'Address 2',
       l.city AS 'City',
       l.state_province AS 'State',
       l.postal_code AS 'Zip',
       client_location_id AS 'Client Location ID',
       quote_location_id AS 'Quote Location ID',
       location_name AS 'Location Name',
       location_status AS 'Location Status',
       timezone as 'Timezone',
       phone_number AS 'Location Phone Number',
       client_location_type AS 'Client Location Type',
       client_location_info AS 'Client Location Info',
       l.last_update_by AS 'Last Update By',
       l.last_update_date AS 'Last Update Date',
       l.active AS 'Active',
       progress_percentage AS 'Progress Percentage',
       level_of_effort AS 'Level of Effort',
       l.record_source as'Source',
       location_description AS 'Location Description',
       jn.note AS 'Last Note',
       jn.created_date AS 'Last Note Created Date',
       jn.created_by AS 'Last Note Created By'
FROM location l
     JOIN orders o ON l.order_id = o.order_id
     LEFT JOIN v_subject pr ON o.provisioner = pr.subject_id
     LEFT JOIN v_subject vpm ON o.vertek_project_manager = vpm.subject_id
     LEFT JOIN v_subject ae ON o.activation_engineer = ae.subject_id
     LEFT JOIN v_subject qa ON o.qa_manager = qa.subject_id
     LEFT JOIN (SELECT ln.location_id, note, n.created_date, n.created_by
                FROM note n
                     JOIN location_note ln ON n.note_id = ln.note_id
                     JOIN (SELECT ln.location_id, MAX(n.note_id) AS note_id
                           FROM note n
                                JOIN location_note ln ON n.note_id = ln.note_id
                           GROUP BY ln.location_id) vw ON ln.note_id = vw.note_id) jn
               ON l.location_id = jn.location_id
WHERE l.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor')
and marked_for_deletion = false;
