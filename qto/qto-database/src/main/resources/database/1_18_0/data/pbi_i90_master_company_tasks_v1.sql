DROP VIEW IF EXISTS pbi_i90_master_company_tasks CASCADE;
create or replace view pbi_i90_master_company_tasks as
       select c.company_id AS master_customer_id,
       c.tenant_id AS "Tenant ID",
              value as "Master Customer Task",
              complete_date as "Completed",
              assigned_to as "Assigned To",
              comment as "Comment"
         from company_task ct
join company c on ct.company_id = c.company_id
where c.tenant_id != (select tenant_id from v_tenant where name = 'Endeavor')
and company_type = 'Master Customer';
