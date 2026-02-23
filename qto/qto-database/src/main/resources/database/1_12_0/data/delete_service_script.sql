# pendidng disconnects will need some additional work as they have a FK to services on different orders
# this script will only work for orders with services that have no child services and no pending disconnects

create temporary table surcharge_ids as (select surcharge_id from service_surcharge where service_id = 4521);
delete from service_surcharge where surcharge_id in (select surcharge_id from surcharge_ids);
delete from surcharge where surcharge_id in (select surcharge_id from surcharge_ids);
drop table surcharge_ids;

create temporary table dispatch_ids as (select ftdi_dispatch_id from ftdi_dispatch d join activation_schedule s on d.schedule_id = s.activation_schedule_id where s.service_id = 4521);
delete from ftdi_appointment where ftdi_dispatch_id in (select ftdi_dispatch_id dispatch_ids);
delete from ftdi_shipment  where ftdi_dispatch_id in (select ftdi_dispatch_id dispatch_ids);
delete from ftdi_note where ftdi_dispatch_id in (select ftdi_dispatch_id dispatch_ids);
delete from ftdi_dispatch d where ftdi_dispatch_id in (select ftdi_dispatch_id dispatch_ids);
drop table dispatch_ids;


create temporary table activation_attempt_ids as (select activation_attempt_id from activation_attempt where service_id = 4521);
delete from activation_attempt_requirement where activation_attempt_id in (select activation_attempt_id from activation_attempt_ids);
delete from activation_issue where activation_attempt_id in (select activation_attempt_id from activation_attempt_ids);
delete from activation_attempt where activation_attempt_id in (select activation_attempt_id from activation_attempt_ids);
drop table activation_attempt_ids;

create temporary table activation_schedule_ids as (select activation_schedule_id from activation_schedule where service_id = 4521);
delete from activation_schedule_custom where activation_schedule_id in (select activation_schedule_id from activation_schedule_ids);
delete from activation_schedule_equipment where activation_schedule_id in (select activation_schedule_id from activation_schedule_ids);
delete from activation_schedule where activation_schedule_id in (select activation_schedule_id from activation_schedule_ids);
drop table activation_schedule_ids;



create temporary table custom_field_value_ids as (select custom_field_value_id from service_custom_field_value where service_id = 4521);
delete from service_custom_field_value where custom_field_value_id in (select custom_field_value_id from custom_field_value_ids);
delete from custom_field_value where custom_field_value_id in (select custom_field_value_id from custom_field_value_ids);
drop table IF EXISTS custom_field_value_ids;


create temporary table note_ids as select note_id from service_note where service_id = 4521;
create temporary table dispute_ids as (select dispute_id from dispute where service_id = 4521);
insert into note_ids (note_id) select note_id from dispute_note where dispute_id in (select dispute_id from dispute_ids);
create temporary table jeop_ids as (select  ji.jeop_instance_id from jeop_instance ji join service_jeop_instance sji on ji.jeop_instance_id = sji.jeop_instance_id where service_id = 4521);
insert into note_ids (note_id) select note_id from jeop_instance_note where jeop_instance_id in (select jeop_instance_id from jeop_ids);
delete from jeop_instance_note where note_id in (select note_id from note_ids);
delete from service_note where note_id in (select note_id from note_ids);
delete from dispute_note where note_id in (select note_id from note_ids);
delete from note where note_id in (select note_id from note_ids);
drop table if exists note_ids;
drop table if exists dispute_ids;
drop table if exists jeop_ids;



create temporary table file_attachment_ids as select file_attachment_id from service_file_attachment where service_id = 4521;
delete from service_file_attachment where file_attachment_id in (select file_attachment_id from file_attachment_ids);


create temporary table file_attachment_content_ids as (select file_attachment_content_id from file_attachment where file_attachment_id in (select file_attachment_id from file_attachment_ids));
delete from file_attachment where file_attachment_content_id in (select file_attachment_content_id from file_attachment_content_ids);
delete from file_attachment_content where file_attachment_content_id in (select file_attachment_content_id from file_attachment_content_ids);
drop table if exists file_attachment_ids;
drop table if exists file_attachment_content_ids;


create temporary table interval_ids as (select interval_instance_id from service_interval_instance where service_id = 4521);
delete from service_interval_instance where interval_instance_id in (select interval_instance_id from interval_ids);
delete from interval_instance where interval_instance_id in (select interval_instance_id from interval_ids);
drop table if exists interval_ids;


create temporary table jeop_instance_ids as (select jeop_instance_id from service_jeop_instance where service_id = 4521);
delete from service_jeop_instance where jeop_instance_id in (select jeop_instance_id from jeop_instance_ids);
delete from jeop_instance where jeop_instance_id in (select jeop_instance_id from jeop_instance_ids);
drop table if exists jeop_instance_ids;

create temporary table equipment_ids as (select equipment_id from service_equipment where service_id = 4521);
delete from service_equipment where equipment_id in (select equipment_id from equipment_ids);
delete from equipment where equipment_id in (select equipment_id from equipment_ids);
drop table if EXISTS equipment_ids;

create temporary table milestone_instance_ids as (select milestone_instance_id from service_milestone_instance where service_id = 4521);
delete from service_milestone_instance where milestone_instance_id in (select milestone_instance_id from milestone_instance_ids);
delete from milestone_instance_history where milestone_instance_id in (select milestone_instance_id from milestone_instance_ids);
delete from milestone_instance where milestone_instance_id in (select milestone_instance_id from milestone_instance_ids);
drop table if EXISTS milestone_instance_ids;


create temporary table shipment_tracking_ids as	(select shipment_tracking_id from service_shipment_tracking st where service_id = 4521);
delete from service_shipment_tracking where shipment_tracking_id in (select shipment_tracking_id from shipment_tracking_ids);
delete from shipment_tracking where shipment_tracking_id in (select shipment_tracking_id from shipment_tracking_ids);
drop table shipment_tracking_ids;

delete from dispute where service_id = 4521;
delete from cost_history where service_id = 4521;

# these can't be deleted these because they have a FK on the new service id  these will have to be looked at individually
# alter table pending_disconnect
# 		drop constraint FK_pending_disconnect_service_child;
# alter table pending_disconnect
# 		drop constraint FK_pending_disconnect_service_new;
# delete from pending_disconnect where parent_service_id = 4521;
# alter table pending_disconnect
#     add constraint FK_pending_disconnect_service_child
#         foreign key (child_service_id) references service (service_id);
# alter table pending_disconnect
#     add constraint FK_pending_disconnect_service_new
#         foreign key (child_service_id) references service (service_id);


delete from service_brokerage where service_id = 4521;
delete from service_snapshot where service_id = 4521;

delete from 4g5g_service where service_id = 4521;
delete from broadband_service where service_id = 4521;
delete from cross_connect_service where service_id = 4521;
delete from dia_service where service_id = 4521;
delete from ethernet_service where service_id = 4521;
delete from television_service where service_id = 4521;
delete from ucaas_service where service_id = 4521;
delete from mpls_service where service_id = 4521;
alter table service
    drop constraint fk_service_service;
delete from service where service_id = 4521;
alter table service
    add constraint fk_service_service
        foreign key (parent_service_id) references service (service_id);

