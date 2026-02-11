create or replace view pbi_iss_invoice_charge as
SELECT invoice_charge_id as 'Invoice Charge ID',
       invoice_id as 'Invoice ID',
       location_id as 'Location ID',
       charge_credit as 'Charge/Credit',
       unit_cost as 'Unit Cost',
       previously_billed as 'Previously Billed',
       invoiced_amount as 'Invoiced Amount',
       item_desc as 'Item Description',
       charge_desc as 'Charge Description',
       charge_type as 'Charge Type',
       charge_level as 'Charge Level',
       master_customer_name as 'Master Customer Name',
       end_customer_name as 'End Customer Name',
       billable_event_milestone_description as 'Billable Event Milestone',
       billable_event_date as 'Billable Event Date'
FROM invoice_charge ic
WHERE ic.tenant_id = (SELECT tenant_id FROM v_tenant WHERE NAME = 'Endeavor');
