# make sure this hasn't been invoiced
# if inventory make sure there are no open macd's
# if provisioning make sure this isn't already in inventory and if it is verify that they really want it deleted
# if they do, check to see if they want the inventory item deleted too and run it again with the inventory site id
# if they don't want the inventory item deleted, check the provisioning_id on the inventory item and reset it to either the
# previous ids or to null if there aren't any
# check to see if there is a pending disconnect and if so, delete it



select  'service_surcharge',   count(*) from service_surcharge ss join surcharge s on ss.surcharge_id = s.surcharge_id  where service_id = 10046
union

select  'ftdi_appointment',  count(*) from ftdi_appointment a join ftdi_dispatch d on a.ftdi_dispatch_id = d.ftdi_dispatch_id join activation_schedule s on d.schedule_id = s.activation_schedule_id  where service_id = 10046
union
select  'ftdi_shipment',  count(*) from ftdi_shipment  a join ftdi_dispatch d on a.ftdi_dispatch_id = d.ftdi_dispatch_id join activation_schedule s on d.schedule_id = s.activation_schedule_id  where service_id = 10046
union
select  'ftdi_note',  count(*) from ftdi_note  a join ftdi_dispatch d on a.ftdi_dispatch_id = d.ftdi_dispatch_id join activation_schedule s on d.schedule_id = s.activation_schedule_id  where service_id = 10046
union
select  'ftdi_dispatch',  count(*) from ftdi_dispatch d join activation_schedule s on d.schedule_id = s.activation_schedule_id  where service_id = 10046
union

select  'activation_attempt_requirement',  count(*) from activation_attempt_requirement r join activation_attempt aa on r.activation_attempt_id = aa.activation_attempt_id where aa.service_id = 10046
union
select  'activation_issue',  count(*) from activation_issue ai join activation_attempt aa on ai.activation_attempt_id = aa.activation_attempt_id where aa.service_id = 10046
union
select  'activation_attempt',  count(*) from activation_attempt where service_id = 10046
union
select  'activation_schedule_custom',  count(*) from activation_schedule_custom c join activation_schedule s on c.activation_schedule_id = s.activation_schedule_id  where service_id = 10046
union
select  'activation_schedule_equipment',  count(*) from activation_schedule_equipment c join activation_schedule s on c.activation_schedule_id = s.activation_schedule_id  where service_id = 10046
union
select  'activation_schedule',  count(*) from activation_schedule s where service_id = 10046
union

select  'service_custom_field_value',  count(*) from service_custom_field_value v join custom_field_value fv on v.custom_field_value_id = fv.custom_field_value_id where service_id = 10046
union


select  'dispute_note',  count(*) from dispute_note c  join dispute d on c.dispute_id = d.dispute_id where d.service_id = 10046
union
select  'service_note',  count(*) from service_note c  where c.service_id = 10046
union
# add up the above to find the count of notes


select  'service_file_attachment',  count(*) from service_file_attachment c where service_id = 10046
union



select  'service_interval_instance',  count(*) from service_interval_instance c where c.service_id = 10046
union

select  'service_jeop_instance',  count(*) from service_jeop_instance c join jeop_instance j on c.jeop_instance_id = j.jeop_instance_id where service_id = 10046
union
select  'service_milestone_instance',  count(*) from service_milestone_instance c join milestone_instance mi on c.milestone_instance_id = mi.milestone_instance_id where service_id = 10046
union

select  'service_shipment_tracking',  count(*) from service_shipment_tracking c join service s on c.service_id = s.service_id where c.service_id = 10046
union

select DISTINCT 'service_equipment', count(*) from service_equipment se where se.service_id = 10046
UNION

select  'dispute',  count(*) from dispute where service_id = 10046
union
select  'cost_history',  count(*) from cost_history where service_id = 10046
union
select  'pending_disconnect',  count(*) from pending_disconnect c  where parent_service_id = 10046
union


select  'service_brokerage',  count(*) from service_brokerage where service_id = 10046
union
select  'service_snapshot',  count(*) from service_snapshot where service_id = 10046
union

select  '4g5g_service',  count(*) from 4g5g_service c join service s on c.service_id = s.service_id where c.service_id = 10046
union
select  'broadband_service',  count(*) from broadband_service c join service s on c.service_id = s.service_id where c.service_id = 10046
union
select  'cross_connect_service',  count(*) from cross_connect_service c join service s on c.service_id = s.service_id  where c.service_id = 10046
union
select  'dia_service',  count(*) from dia_service c join service s on c.service_id = s.service_id  where c.service_id = 10046
union
select  'ethernet_service',  count(*) from ethernet_service c join service s on c.service_id = s.service_id  where c.service_id = 10046
union
select  'television_service',  count(*) from television_service c join service s on c.service_id = s.service_id  where c.service_id = 10046
union
select  'ucaas_service',  count(*) from ucaas_service c join service s on c.service_id = s.service_id  where c.service_id = 10046
union
select  'mpls_service',  count(*) from mpls_service c join service s on c.service_id = s.service_id  where c.service_id = 10046
union


select  'service',   count(*) from service  where service_id = 10046


