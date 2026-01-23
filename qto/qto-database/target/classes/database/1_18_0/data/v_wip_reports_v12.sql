# ----------------------------------------------------------------------------------------------------
create or replace view v_wip_service as
select company_name, (select company_name from company where company_id = c.master_customer_id) as master_company_name, o.order_id, o.provisioner, o.vertek_project_manager, o.client_project_manager, client_order_id,
       l.location_id, l.client_location_id, l.location_name, l.address_1, l.address_2, l.city, l.state_province,
       s.service_id, s.client_service_id, s.provider, s.service_status, s.service_type, s.active, s.service_mrc,
       s.tenant_id, c.master_customer_id, s.service_billed_to, s.current_inventory,
       (Select mi.milestone_date
        from milestone_instance mi
                 join service_milestone_instance smi on mi.milestone_instance_id = smi.milestone_instance_id
                 join milestone m on mi.milestone_id = m.milestone_id
        where milestone_code = 'DATA_PROVISIONING_COMPLETE' and smi.service_id = s.service_id)
           as data_provisioning_complete_date,
       (Select mi.milestone_date
        from milestone_instance mi
                 join service_milestone_instance smi on mi.milestone_instance_id = smi.milestone_instance_id
                 join milestone m on mi.milestone_id = m.milestone_id
        where milestone_code = 'COMPLETE' and smi.service_id = s.service_id)
           as service_complete_date
from company c
         join orders o ON c.company_id = o.company_id
         join location l on o.order_id = l.order_id
         join service s ON l.location_id = s.location_id
where s.marked_for_deletion = FALSE;
# -------------------------------------------------------------------------------------------------------------------
create or replace view v_wip_service_jeop as
select company_name, (select company_name from company where company_id = c.master_customer_id) as master_company_name, o.order_id, o.provisioner, o.vertek_project_manager, o.client_project_manager, client_order_id,
       l.location_id, l.client_location_id, l.location_name, l.address_1, l.address_2, l.city, l.state_province,
       s.service_id, s.client_service_id, s.provider, s.service_status, s.service_type, ji.jeop_instance_id, ji.jeop_description,
       ji.start_date, ji.end_date, ji.responsibility, ji.assigned_to, s.tenant_id, c.master_customer_id, s.service_billed_to
from company c
         join orders o ON c.company_id = o.company_id
         join location l on o.order_id = l.order_id
         join service s ON l.location_id = s.location_id
         join service_jeop_instance sji on s.service_id = sji.service_id
         join jeop_instance ji on sji.jeop_instance_id = ji.jeop_instance_id
where s.marked_for_deletion = FALSE;
# -------------------------------------------------------------------------------------------------------------------
create or replace view v_wip_location_jeop as
select c.company_name, (select company_name from company where company_id = c.master_customer_id) as master_company_name,
       o.order_id, o.provisioner, o.vertek_project_manager, o.client_project_manager, client_order_id,
       l.location_id, l.client_location_id, l.location_name, l.address_1, l.address_2, l.city, l.state_province, l.location_status, l.client_location_type,
       ji.jeop_instance_id, ji.jeop_description, ji.start_date, ji.end_date, ji.responsibility, ji.assigned_to,
       l.tenant_id, c.master_customer_id
from company c
         join orders o ON c.company_id = o.company_id
         join location l on o.order_id = l.order_id
         join location_jeop_instance lji on l.location_id = lji.location_id
         join jeop_instance ji on lji.jeop_instance_id = ji.jeop_instance_id
where l.marked_for_deletion = FALSE;
