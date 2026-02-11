# pendidng disconnects will need some additional work as they have a FK to services on different orders
# this script will only work for orders with services that have no child services
# make sure this hasn't been invoiced
# if inventory make sure there are no open macd's
# if provisioning make sure this isn't already in inventory and if it is verify that they really want it deleted
# if they do, check to see if they want the inventory item deleted too and run it again with the inventory site id
# if they don't want the inventory item deleted, check the provisioning_id on the inventory item and reset it to either the
# previous ids or to null if there aren't any

set @order_id = 4521;
create temporary table service_ids as (select service_id from service where order_id = @order_id);
create temporary table location_ids as (select location_id from location where order_id = @order_id);


create temporary table surcharge_ids as (select surcharge_id from service_surcharge where service_id in (select service_id from service_ids));
delete from service_surcharge where surcharge_id in (select surcharge_id from surcharge_ids);
delete from surcharge where surcharge_id in (select surcharge_id from surcharge_ids);
drop table surcharge_ids;

create temporary table dispatch_ids as (select ftdi_dispatch_id from ftdi_dispatch d join activation_schedule s on d.schedule_id = s.activation_schedule_id where s.service_id in (select service_id from service_ids));
delete from ftdi_appointment where ftdi_dispatch_id in (select ftdi_dispatch_id dispatch_ids);
delete from ftdi_shipment  where ftdi_dispatch_id in (select ftdi_dispatch_id dispatch_ids);
delete from ftdi_note where ftdi_dispatch_id in (select ftdi_dispatch_id dispatch_ids);
delete from ftdi_dispatch d where ftdi_dispatch_id in (select ftdi_dispatch_id dispatch_ids);
drop table dispatch_ids;


create temporary table activation_attempt_ids as (select activation_attempt_id from activation_attempt where service_id in (select service_id from service_ids));
delete from activation_attempt_requirement where activation_attempt_id in (select activation_attempt_id from activation_attempt_ids);
delete from activation_issue where activation_attempt_id in (select activation_attempt_id from activation_attempt_ids);
delete from activation_attempt where activation_attempt_id in (select activation_attempt_id from activation_attempt_ids);
drop table activation_attempt_ids;

create temporary table activation_schedule_ids as (select activation_schedule_id from activation_schedule where service_id in (select service_id from service_ids));
delete from activation_schedule_custom where activation_schedule_id in (select activation_schedule_id from activation_schedule_ids);
delete from activation_schedule_equipment where activation_schedule_id in (select activation_schedule_id from activation_schedule_ids);
delete from activation_schedule where activation_schedule_id in (select activation_schedule_id from activation_schedule_ids);
drop table activation_schedule_ids;

drop table if exists contact_ids;
create table contact_ids as (select contact_id from location_contact where location_id in (select location_id from location_ids));
insert into contact_ids (contact_id) select contact_id from order_contact where order_id = @order_id;
delete from location_contact where contact_id in (select contact_id from contact_ids);
delete from order_contact where order_id = @order_id;
delete from contact_info where contact_id in (select contact_id from contact_ids);
delete from contact where contact_id in (select contact_id from contact_ids);


create temporary table custom_field_value_ids as (select custom_field_value_id from location_custom_field_value where location_id in (select location_id from location_ids));
insert into custom_field_value_ids (custom_field_value_id)  (select custom_field_value_id from service_custom_field_value where service_id in (select service_id from service_ids));
delete from location_custom_field_value where custom_field_value_id in (select custom_field_value_id from custom_field_value_ids);
delete from service_custom_field_value where custom_field_value_id in (select custom_field_value_id from custom_field_value_ids);
delete from custom_field_value where custom_field_value_id in (select custom_field_value_id from custom_field_value_ids);
drop table IF EXISTS custom_field_value_ids;


create temporary table note_ids as (select note_id from order_note where order_id = @order_id);
insert into note_ids (note_id) select note_id from location_note where location_id in (select location_id from location_ids);
insert into note_ids (note_id) select note_id from service_note where service_id in (select service_id from service_ids);
create temporary table dispute_ids as (select dispute_id from dispute where service_id in (select service_id from service_ids));
insert into note_ids (note_id) select note_id from dispute_note where dispute_id in (select dispute_id from dispute_ids);
create temporary table jeop_ids as (select  ji.jeop_instance_id from jeop_instance ji join service_jeop_instance sji on ji.jeop_instance_id = sji.jeop_instance_id where service_id in (select service_id from service_ids));
insert into jeop_ids (jeop_instance_id) select ji.jeop_instance_id from jeop_instance ji join location_jeop_instance lji on ji.jeop_instance_id = lji.jeop_instance_id where location_id in (select location_id from location_ids);
insert into jeop_ids (jeop_instance_id) select ji.jeop_instance_id from jeop_instance ji join order_jeop_instance oji on ji.jeop_instance_id = oji.jeop_instance_id where order_id = @order_id;
insert into note_ids (note_id) select note_id from jeop_instance_note where jeop_instance_id in (select jeop_instance_id from jeop_ids);
delete from order_note where note_id in (select note_id from note_ids);
delete from jeop_instance_note where note_id in (select note_id from note_ids);
delete from service_note where note_id in (select note_id from note_ids);
delete from dispute_note where note_id in (select note_id from note_ids);
delete from location_note where note_id in (select note_id from note_ids);
delete from note where note_id in (select note_id from note_ids);
drop table if exists note_ids;
drop table if exists dispute_ids;
drop table if exists jeop_ids;


create temporary table file_attachment_ids as (select file_attachment_id from location_file_attachment where location_id in (select location_id from location_ids));
insert into file_attachment_ids (file_attachment_id) select file_attachment_id from order_file_attachment where order_id = @order_id;
insert into file_attachment_ids (file_attachment_id) select file_attachment_id from service_file_attachment where service_id in (select service_id from service_ids);
delete from location_file_attachment where file_attachment_id in (select file_attachment_id from file_attachment_ids);
delete from order_file_attachment where file_attachment_id in (select file_attachment_id from file_attachment_ids);
delete from service_file_attachment where file_attachment_id in (select file_attachment_id from file_attachment_ids);
delete from import_activity where file_attachment_id in   (select file_attachment_id from file_attachment_ids);
drop table if exists file_attachment_ids;


create temporary table file_attachment_content_ids as (select file_attachment_content_id from file_attachment where file_attachment_id in (select file_attachment_id from file_attachment_ids));
delete from file_attachment where file_attachment_content_id in (select file_attachment_content_id from file_attachment_content_ids);
delete from file_attachment_content where file_attachment_content_id in (select file_attachment_content_id from file_attachment_content_ids);
drop table if exists file_attachment_content_ids;


create temporary table interval_ids as (select interval_instance_id from location_interval_instance where location_id in (select location_id from location_ids));
insert into interval_ids (interval_instance_id) (select interval_instance_id from service_interval_instance where service_id in (select service_id from service_ids));
delete from location_interval_instance where interval_instance_id in (select interval_instance_id from interval_ids);
delete from service_interval_instance where interval_instance_id in (select interval_instance_id from interval_ids);
delete from interval_instance where interval_instance_id in (select interval_instance_id from interval_ids);
drop table if exists interval_ids;


create temporary table jeop_instance_ids as (select jeop_instance_id from location_jeop_instance where location_id in (select location_id from location_ids));
insert into jeop_instance_ids (jeop_instance_id) (select jeop_instance_id from service_jeop_instance where service_id in (select service_id from service_ids));
insert into jeop_instance_ids (jeop_instance_id) (select jeop_instance_id from order_jeop_instance where order_id = @order_id);
delete from location_jeop_instance where jeop_instance_id in  (select jeop_instance_id from jeop_instance_ids);
delete from service_jeop_instance where jeop_instance_id in (select jeop_instance_id from jeop_instance_ids);
delete from order_jeop_instance where jeop_instance_id in (select jeop_instance_id from jeop_instance_ids);
delete from jeop_instance where jeop_instance_id in (select jeop_instance_id from jeop_instance_ids);
drop table if exists jeop_instance_ids;

create temporary table equipment_ids as (select equipment_id from service_equipment where service_id in (select service_id from service_ids));
delete from service_equipment where equipment_id in (select equipment_id from equipment_ids);
delete from equipment where equipment_id in (select equipment_id from equipment_ids);
drop table if EXISTS equipment_ids;


create temporary table milestone_instance_ids as (select milestone_instance_id from location_milestone_instance where location_id in (select location_id from location_ids));
insert into milestone_instance_ids (milestone_instance_id) (select milestone_instance_id from service_milestone_instance where service_id in (select service_id from service_ids));
insert into milestone_instance_ids (milestone_instance_id) (select milestone_instance_id from order_milestone_instance where order_id = @order_id);
delete from location_milestone_instance where milestone_instance_id in (select milestone_instance_id from milestone_instance_ids);
delete from service_milestone_instance where milestone_instance_id in (select milestone_instance_id from milestone_instance_ids);
delete from order_milestone_instance where milestone_instance_id in (select milestone_instance_id from milestone_instance_ids);
delete from milestone_instance_history where milestone_instance_id in (select milestone_instance_id from milestone_instance_ids);
delete from milestone_instance where milestone_instance_id in (select milestone_instance_id from milestone_instance_ids);
drop table if EXISTS milestone_instance_ids;


create temporary table shipment_tracking_ids as	(select shipment_tracking_id from location_shipment_tracking st where location_id in (select location_id from location_ids));
insert into shipment_tracking_ids (shipment_tracking_id) (select shipment_tracking_id from service_shipment_tracking st where service_id in (select service_id from service_ids));
delete from location_shipment_tracking where shipment_tracking_id in (select shipment_tracking_id from shipment_tracking_ids);
delete from service_shipment_tracking where shipment_tracking_id in (select shipment_tracking_id from shipment_tracking_ids);
delete from shipment_tracking where shipment_tracking_id in (select shipment_tracking_id from shipment_tracking_ids);
drop table shipment_tracking_ids;

delete from dispute where service_id in (select service_id from service_ids);
delete from cost_history where service_id in (select service_id from service_ids);
# these can't be deleted these because they have a FK on the new service id  these will have to be looked at individually

alter table pending_disconnect
		drop constraint FK_pending_disconnect_service_child;
alter table pending_disconnect
		drop constraint FK_pending_disconnect_service_new;
delete from pending_disconnect where parent_service_id in (select service_id from service_ids);
alter table pending_disconnect
    add constraint FK_pending_disconnect_service_child
        foreign key (child_service_id) references service (service_id);
alter table pending_disconnect
    add constraint FK_pending_disconnect_service_new
        foreign key (child_service_id) references service (service_id);


delete from service_brokerage where service_id in (select service_id from service_ids);
delete from service_snapshot where service_id in (select service_id from service_ids);


create temporary table message_ids as (select message_thread_id from message_thread where location_id in (select location_id from location_ids));
delete from message where message_thread_id in (select message_thread_id from message_ids);
delete from message_thread_watcher where message_thread_id in (select message_thread_id from message_ids);
delete from message_thread where message_thread_id in (select message_thread_id from message_ids);
drop table if exists message_ids;


delete from 4g5g_service where service_id in (select service_id from service_ids);
delete from broadband_service where service_id in (select service_id from service_ids);
delete from cross_connect_service where service_id in (select service_id from service_ids);
delete from dia_service where service_id in (select service_id from service_ids);
delete from ethernet_service where service_id in (select service_id from service_ids);
delete from television_service where service_id in (select service_id from service_ids);
delete from ucaas_service where service_id in (select service_id from service_ids);
delete from mpls_service where service_id in (select service_id from service_ids);
alter table service
    drop constraint fk_service_service;
delete from service where service_id in (select service_id from service_ids);
alter table service
    add constraint fk_service_service
        foreign key (parent_service_id) references service (service_id);
delete from location where location_id in (select location_id from location_ids);
delete from orders where order_id = @order_id;


drop table if exists service_ids;
drop table if exists location_ids;

