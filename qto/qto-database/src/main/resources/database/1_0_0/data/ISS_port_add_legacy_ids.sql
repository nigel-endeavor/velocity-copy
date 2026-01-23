ALTER TABLE company
	ADD legacy_id int NULL;

ALTER TABLE orders
	ADD legacy_id int NULL;

ALTER TABLE location
	ADD legacy_id int NULL;

ALTER TABLE service
	ADD legacy_id int NULL;

ALTER TABLE jeop_instance
	ADD legacy_id int NULL;

ALTER TABLE note
	ADD legacy_id int NULL;

ALTER TABLE contact
	ADD legacy_id int NULL;

ALTER TABLE activation_schedule
	ADD legacy_id int NULL;

ALTER TABLE activation_attempt
	ADD legacy_id int NULL;

ALTER TABLE milestone_instance
	ADD legacy_id int NULL;

ALTER TABLE address
	ADD legacy_id int NULL;

ALTER TABLE ftdi_appointment
	ADD legacy_id int NULL;

ALTER TABLE ftdi_dispatch
	ADD legacy_id int NULL;

ALTER TABLE ftdi_note
	ADD legacy_id int NULL;

ALTER TABLE ftdi_shipment
	ADD legacy_id int NULL;
