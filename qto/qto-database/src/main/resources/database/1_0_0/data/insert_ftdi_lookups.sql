

#####order type
INSERT INTO ftdi_order_type (vendor_order_type_id, vendor_name, category, org_id, sort1, sort2,
                             order_type, last_update, active)
VALUES ('18571','Endeavor','Pizza Hut Franchise (PIN) BYOB Installation',null,
        'Yum Brand,Pizza Hut Franchise','PIN','Pizza Hut Franchise (PIN) BYOB Installation - XM VPN',
        '2013-04-02 00:00:00',1);

set @otId = LAST_INSERT_ID();

####equipment
INSERT INTO ftdi_equipment_type (equipment_type, part_number, client_part_number, item_number, active)
VALUES ('ISS 0E-1205AD4 W BOX 12VDC 5 AMP CCTV POWER SUPPLY' ,'ISS 0E-1205AD4 W BOX 12VDC 5 AMP CCTV POWER SUPPLY',null,'ISS~0001827',1);

set @equipId = LAST_INSERT_ID();

INSERT INTO ftdi_order_type_equipment (ftdi_order_type_id, ftdi_equipment_type_id) VALUES (@otId, @equipId);


######custome fields
INSERT INTO ftdi_custom_fields (field_name, data_type, active) VALUES ('Shipping Method','List', 1);

set @custId = LAST_INSERT_ID();

INSERT INTO ftdi_custom_field_values (custom_field_id, field_value) VALUES (@custId,'Ground');
INSERT INTO ftdi_custom_field_values (custom_field_id, field_value) VALUES (@custId,'Express Saver');
INSERT INTO ftdi_custom_field_values (custom_field_id, field_value) VALUES (@custId,'Express 2 Day');


INSERT INTO ftdi_order_type_custom_fields (ftdi_order_type_id, ftdi_custom_field_id) VALUES (@otId, @custId);

INSERT INTO ftdi_custom_fields (field_name, data_type, active) VALUES ('Needs configuration?','Boolean', 1);
set @custId = LAST_INSERT_ID();
INSERT INTO ftdi_order_type_custom_fields (ftdi_order_type_id, ftdi_custom_field_id) VALUES (@otId, @custId);

INSERT INTO ftdi_custom_fields (field_name, data_type, active) VALUES ('What equipment needs to be retrieved?','String', 1);
set @custId = LAST_INSERT_ID();
INSERT INTO ftdi_order_type_custom_fields (ftdi_order_type_id, ftdi_custom_field_id) VALUES (@otId, @custId);

INSERT INTO ftdi_custom_fields (field_name, data_type, active) VALUES ('Required Delivery Date','Date', 1);
set @custId = LAST_INSERT_ID();
INSERT INTO ftdi_order_type_custom_fields (ftdi_order_type_id, ftdi_custom_field_id) VALUES (@otId, @custId);



#################################################
#####order type
INSERT INTO ftdi_order_type (vendor_order_type_id, vendor_name, category, org_id, sort1, sort2,
                             order_type, last_update, active)
VALUES ('26922','Endeavor','Sally Beauty - Logistics', null,
        'Sallys - Phase 9 New Build CCTV MSO','ISS Install','Sallys - Phase 9 New Build CCTV MSO (ISS Install)',
        '2013-04-02 00:00:00',1);

set @otId = LAST_INSERT_ID();

####equipment
INSERT INTO ftdi_equipment_type (equipment_type, part_number, client_part_number, item_number, active)
VALUES ('ISS 4A-XP400 - 400VA 200W UPS' ,'ISS 4A-XP400 - 400VA 200W UPS',null,'ISS~0001827',1);

set @equipId = LAST_INSERT_ID();

INSERT INTO ftdi_order_type_equipment (ftdi_order_type_id, ftdi_equipment_type_id) VALUES (@otId, @equipId);


######custome fields
INSERT INTO ftdi_custom_fields (field_name, data_type, active) VALUES ('Shipping Method','List', 1);

set @custId = LAST_INSERT_ID();

INSERT INTO ftdi_custom_field_values (custom_field_id, field_value) VALUES (@custId,'Ground');
INSERT INTO ftdi_custom_field_values (custom_field_id, field_value) VALUES (@custId,'Express Saver');
INSERT INTO ftdi_custom_field_values (custom_field_id, field_value) VALUES (@custId,'Express 2 Day');


INSERT INTO ftdi_order_type_custom_fields (ftdi_order_type_id, ftdi_custom_field_id) VALUES (@otId, @custId);

INSERT INTO ftdi_custom_fields (field_name, data_type, active) VALUES ('Needs configuration?','Boolean', 1);
set @custId = LAST_INSERT_ID();
INSERT INTO ftdi_order_type_custom_fields (ftdi_order_type_id, ftdi_custom_field_id) VALUES (@otId, @custId);

INSERT INTO ftdi_custom_fields (field_name, data_type, active) VALUES ('What equipment needs to be retrieved?','String', 1);
set @custId = LAST_INSERT_ID();
INSERT INTO ftdi_order_type_custom_fields (ftdi_order_type_id, ftdi_custom_field_id) VALUES (@otId, @custId);

INSERT INTO ftdi_custom_fields (field_name, data_type, active) VALUES ('Required Delivery Date','Date', 1);
set @custId = LAST_INSERT_ID();
INSERT INTO ftdi_order_type_custom_fields (ftdi_order_type_id, ftdi_custom_field_id) VALUES (@otId, @custId);
