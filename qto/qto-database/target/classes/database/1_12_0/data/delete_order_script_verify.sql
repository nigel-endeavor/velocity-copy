# make sure this hasn't been invoiced
# if inventory make sure there are no open macd's
# make sure this isn't already in inventory and if it is verify that they really want it deleted
# if they do, check to see if they want the inventory item deleted too and run it again with the inventory order id
# if they don't want the inventory item deleted, check the provisioning_id on the inventory item and reset it to either the
# previous ids or to null if there aren't any

SET @order_id = 4521;


select  'service_surcharge',   count(*) from service_surcharge ss join surcharge s on ss.surcharge_id = s.surcharge_id  where service_id in (select service_id from service where order_id = @order_id)
union

select  'ftdi_appointment',  count(*) from ftdi_appointment a join ftdi_dispatch d on a.ftdi_dispatch_id = d.ftdi_dispatch_id join activation_schedule s on d.schedule_id = s.activation_schedule_id  where s.service_id in (select service_id from service where order_id = @order_id)
union
select  'ftdi_shipment',  count(*) from ftdi_shipment  a join ftdi_dispatch d on a.ftdi_dispatch_id = d.ftdi_dispatch_id join activation_schedule s on d.schedule_id = s.activation_schedule_id  where s.service_id in (select service_id from service where order_id = @order_id)
union
select  'ftdi_note',  count(*) from ftdi_note  a join ftdi_dispatch d on a.ftdi_dispatch_id = d.ftdi_dispatch_id join activation_schedule s on d.schedule_id = s.activation_schedule_id  where s.service_id in (select service_id from service where order_id = @order_id)
union
select  'ftdi_dispatch',  count(*) from ftdi_dispatch d join activation_schedule s on d.schedule_id = s.activation_schedule_id  where s.service_id in (select service_id from service where order_id = @order_id)
union

select  'activation_attempt_requirement',  count(*) from activation_attempt_requirement r join activation_attempt aa on r.activation_attempt_id = aa.activation_attempt_id where aa.service_id in (select service_id from service where order_id = @order_id)
union
select  'activation_issue',  count(*) from activation_issue ai join activation_attempt aa on ai.activation_attempt_id = aa.activation_attempt_id where aa.service_id in (select service_id from service where order_id = @order_id)
union
select  'activation_attempt',  count(*) from activation_attempt where service_id in (select service_id from service where order_id = @order_id)
union
select  'activation_schedule_custom',  count(*) from activation_schedule_custom c join activation_schedule s on c.activation_schedule_id = s.activation_schedule_id  where s.service_id in (select service_id from service where order_id = @order_id)
union
select  'activation_schedule_equipment',  count(*) from activation_schedule_equipment c join activation_schedule s on c.activation_schedule_id = s.activation_schedule_id  where s.service_id in (select service_id from service where order_id = @order_id)
union
select  'activation_schedule',  count(*) from activation_schedule s where s.service_id in (select service_id from service where order_id = @order_id)
union

select  'location_contact',  count(*) from location_contact l join contact c on l.contact_id = c.contact_id where location_id in (select location_id from location where order_id = @order_id)
union
select  'order_contact',  count(*) from order_contact l join contact c on l.contact_id = c.contact_id where order_id = @order_id
union
select  'contact',  count(*) from contact c left join location_contact lc on c.contact_id = lc.contact_id left join order_contact oc on c.contact_id = oc.contact_id where lc.location_id in (select location_id from location where order_id = @order_id) or oc.order_id = @order_id
union

select  'location_custom_field_value',  count(*) from location_custom_field_value v join custom_field_value fv on v.custom_field_value_id = fv.custom_field_value_id where location_id in (select location_id from location where order_id = @order_id)
union
select  'service_custom_field_value',  count(*) from service_custom_field_value v join custom_field_value fv on v.custom_field_value_id = fv.custom_field_value_id where service_id in (select service_id from service where order_id = @order_id)
union
select  'custom_field_value',  count(*) from custom_field_value cf left join location_custom_field_value lcf on cf.custom_field_value_id = lcf.custom_field_value_id left join service_custom_field_value scf on cf.custom_field_value_id = scf.custom_field_value_id where lcf.location_id in (select location_id from location where order_id = @order_id) or scf.service_id in (select service_id from service where order_id = @order_id)
union

select  'dispute_note',  count(*) from dispute_note c  join dispute d on c.dispute_id = d.dispute_id where d.service_id in (select service_id from service where order_id = @order_id)
union
select  'location_note',  count(*) from location_note c  where c.location_id in (select location_id from location where order_id = @order_id)
union
select  'order_note',  count(*) from order_note c  where c.order_id = @order_id
union
select  'service_note',  count(*) from service_note c  where c.service_id in (select service_id from service where order_id = @order_id)
union
# add up the above to find the count of notes

select  'location_file_attachment',  count(*) from location_file_attachment c where c.location_id in (select location_id from location where order_id = @order_id)
union
select  'order_file_attachment',  count(*) from order_file_attachment c join file_attachment fa on c.file_attachment_id = fa.file_attachment_id where c.order_id = @order_id
union
select  'service_file_attachment',  count(*) from service_file_attachment c where service_id in (Select service_id from service where order_id = @order_id)
union

#shld be the sum of the above three
# select  'file_attachment',  count(*) from file_attachment GROUP BY tenant_id
# union
# select  'file_attachment_content',  count(*) from file_attachment_content c join file_attachment fa on c.file_attachment_content_id = fa.file_attachment_content_id GROUP BY tenant_id
# union

select  'location_interval_instance',  count(*) from location_interval_instance c WHERE c.location_id in (select location_id from location where order_id = @order_id)
union
select  'service_interval_instance',  count(*) from service_interval_instance c where c.service_id in (select service_id from service where order_id = @order_id)
union
# should be the sum ot the above two
# select   count(*) from interval_instance ii join location

select  'location_jeop_instance',  count(*) from location_jeop_instance c join jeop_instance j on c.jeop_instance_id = j.jeop_instance_id where location_id in (select location_id from location where order_id = @order_id)
union
select  'service_jeop_instance',  count(*) from service_jeop_instance c join jeop_instance j on c.jeop_instance_id = j.jeop_instance_id where service_id in (select service_id from service where order_id = @order_id)
union
select  'order_jeop_instance',  count(*) from order_jeop_instance c join jeop_instance j on c.jeop_instance_id = j.jeop_instance_id where order_id = @order_id
# should be the sum of the above three
                                                                                                                                    # union
# select  'jeop_instance',  count(*) from jeop_instance
UNION
select 'service_equipment', count(*) from service_equipment se join equipment e on se.equipment_id = e.equipment_id where se.service_id in (select service_id from service where order_id = @order_id)

union
select  'location_milestone_instance',  count(*) from location_milestone_instance c join milestone_instance mi on c.milestone_instance_id = mi.milestone_instance_id where location_id in (select location_id from location where order_id = @order_id)
union
select  'service_milestone_instance',  count(*) from service_milestone_instance c join milestone_instance mi on c.milestone_instance_id = mi.milestone_instance_id where service_id in (select service_id from service where order_id = @order_id)
union
select  'order_milestone_instance',  count(*) from order_milestone_instance c join milestone_instance mi on c.milestone_instance_id = mi.milestone_instance_id where order_id = @order_id
union
# should be the sum ot the above three
# select  'milestone_instance',  count(*) from milestone_instance
# union


select  'location_shipment_tracking',  count(*) from location_shipment_tracking c join location l on c.location_id = l.location_id where c.location_id in (select location_id from location where order_id = @order_id)
union
select  'service_shipment_tracking',  count(*) from service_shipment_tracking c join service s on c.service_id = s.service_id where c.service_id in (select service_id from service where order_id = @order_id)
union
# should be the sum ot the above two
# select   count(*) from shipment_tracking where shipment_tracking_id in (select shipment_tracking_id from locationShipmentIds) or shipment_tracking_id in (select shipment_tracking_id from serviceShipmentIds)


select  'dispute',  count(*) from dispute where service_id in (select service_id from service where order_id = @order_id)
union
select  'cost_history',  count(*) from cost_history where service_id in (select service_id from service where order_id = @order_id)
union
select  'pending_disconnect',  count(*) from pending_disconnect c  where parent_service_id in (select service_id from service where order_id = @order_id)
union


select  'service_brokerage',  count(*) from service_brokerage where service_id in (select service_id from service where order_id = @order_id)
union
select  'service_snapshot',  count(*) from service_snapshot where service_id in (select service_id from service where order_id = @order_id)
union

select 'message',  count(*) from message c join message_thread mt on c.message_thread_id = mt.message_thread_id where mt.location_id in (select location_id from location where order_id = @order_id)
union
select 'message_thread_watcher',  count(*) from message_thread_watcher c join message_thread mt on c.message_thread_id = mt.message_thread_id where location_id in (select location_id from location where order_id = @order_id)
union
select  'message_thread',  count(*) from message_thread where location_id in (select location_id from location where order_id = @order_id)
union

select  '4g5g_service',  count(*) from 4g5g_service c join service s on c.service_id = s.service_id where c.service_id in (select service_id from service where order_id = @order_id)
union
select  'broadband_service',  count(*) from broadband_service c join service s on c.service_id = s.service_id where c.service_id in (select service_id from service where order_id = @order_id)
union
select  'cross_connect_service',  count(*) from cross_connect_service c join service s on c.service_id = s.service_id  where c.service_id in (select service_id from service where order_id = @order_id)
union
select  'dia_service',  count(*) from dia_service c join service s on c.service_id = s.service_id  where c.service_id in (select service_id from service where order_id = @order_id)
union
select  'ethernet_service',  count(*) from ethernet_service c join service s on c.service_id = s.service_id  where c.service_id in (select service_id from service where order_id = @order_id)
union
select  'television_service',  count(*) from television_service c join service s on c.service_id = s.service_id  where c.service_id in (select service_id from service where order_id = @order_id)
union
select  'ucaas_service',  count(*) from ucaas_service c join service s on c.service_id = s.service_id  where c.service_id in (select service_id from service where order_id = @order_id)
union
select  'mpls_service',  count(*) from mpls_service c join service s on c.service_id = s.service_id  where c.service_id in (select service_id from service where order_id = @order_id)
union


select  'service',   count(*) from service  where service_id in (select service_id from service where order_id = @order_id)
union
select  'location',  count(*) from location where location_id in (select location_id from location where order_id = @order_id)
union
select  'orders',  count(*) from orders where order_id = @order_id;


