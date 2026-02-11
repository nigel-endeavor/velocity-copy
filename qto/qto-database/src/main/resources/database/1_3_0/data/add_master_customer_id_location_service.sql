ALTER TABLE location
ADD COLUMN master_customer_id INT NULL;

ALTER TABLE location
ADD CONSTRAINT FK_location_master_customer_id
FOREIGN KEY (master_customer_id) REFERENCES company(company_id);

UPDATE location
JOIN orders o ON location.order_id = o.order_id
SET location.master_customer_id = o.master_customer_id;


ALTER TABLE service
ADD COLUMN master_customer_id INT NULL;

ALTER TABLE service
ADD CONSTRAINT FK_service_master_customer_id
FOREIGN KEY (master_customer_id) REFERENCES company(company_id);

UPDATE service
JOIN orders o ON service.order_id = o.order_id
SET service.master_customer_id = o.master_customer_id;