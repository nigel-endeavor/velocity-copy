DROP VIEW IF EXISTS pbi_iss_invoice_charge CASCADE;
CREATE OR REPLACE VIEW pbi_iss_invoice_charge AS
SELECT invoice_charge_id AS 'Invoice Charge ID',
       invoice_id AS 'Invoice ID',
       location_id AS 'Location ID',
       charge_credit AS 'Charge/Credit',
       unit_cost AS 'Unit Cost',
       previously_billed AS 'Previously Billed',
       invoiced_amount AS 'Invoiced Amount',
       item_desc AS 'Item Description',
       charge_desc AS 'Charge Description',
       charge_type AS 'Charge Type',
       charge_level AS 'Charge Level',
       master_customer_name AS 'Master Customer Name',
       end_customer_name AS 'End Customer Name',
       CASE WHEN billable_event_date > '2022-7-30'
	            THEN CASE WHEN billable_event_milestone_description IS NULL
		                      THEN 'Surcharge'
	                      ELSE billable_event_milestone_description END
            ELSE billable_event_milestone_description
	       END AS 'Billable Event Milestone',
       billable_event_date AS 'Billable Event Date',
       l.`IPNETW Percentage`,
       l.`IPNETW Percentage` * ic.invoiced_amount AS 'IPNETW Alloc Amt',
       l.`VoIP Percentage`,
       l.`VoIP Percentage` * ic.invoiced_amount AS 'VoIP Alloc Amt'
FROM invoice_charge ic
     JOIN pbi_iss_location l ON ic.location_id = l.`Location ID`
WHERE ic.tenant_id = (SELECT tenant_id FROM v_tenant WHERE NAME = 'Endeavor');
