create or replace view pbi_i90_end_company_tasks as
       select c.company_id AS end_company_id,
       c.tenant_id AS "Tenant ID",
              value as "Task",
              complete_date as "Completed",
              assigned_to as "Assigned To",
              comment as "Comment"
         from company_task ct
join company c on ct.company_id = c.company_id
where c.tenant_id != (select tenant_id from v_tenant where name = 'Endeavor')
and company_type = 'End Customer';
