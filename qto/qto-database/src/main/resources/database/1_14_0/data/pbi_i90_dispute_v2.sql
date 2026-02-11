CREATE OR REPLACE VIEW pbi_i90_dispute AS
SELECT d.dispute_id,
       d.service_id,
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
       d.dispute_status AS 'Dispute Status',
       d.dispute_type AS 'Dispute Type',
       d.invoice_num AS 'Invoice Number',
       d.amount_disputed_mrc AS 'Amount Disputed MRC',
       d.amount_disputed_nrc AS 'Amount Disputed NRC',
       d.vendor_tracking_num AS 'Vendor Tracking Number',
       d.credit_recognized AS 'Credit Recognized',
       d.open_date AS 'Open Date',
       d.dispute_follow_up_date AS 'Dispute Follow Up Date',
       billing_review_complete_date AS 'Billing Review Complete Date',
       dispute_closed_date AS 'Dispute Closed Date',
       realized_credit AS 'Realized Credit',
       realized_mrc_adjustment AS 'Realized MRC Adjustment',
       annualized_mrc_save AS 'Annualized MRC Save',
       dispute_assignment AS 'Dispute Assignment',
       jn.note AS 'Last Note',
       jn.created_by AS 'Last Note Created By',
       jn.created_date AS 'Last Note Created Date'
FROM service s
     JOIN dispute d ON s.service_id = d.service_id
     LEFT JOIN (SELECT dn.dispute_id, note, n.created_date, n.created_by
                FROM note n
                     JOIN dispute_note dn ON n.note_id = dn.note_id
                     JOIN (SELECT dn.dispute_id, MAX(n.note_id) AS note_id
                           FROM note n
                                JOIN dispute_note dn ON n.note_id = dn.note_id
                           GROUP BY dn.dispute_id) vw ON dn.note_id = vw.note_id) jn
               ON d.dispute_id = jn.dispute_id
WHERE s.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');


