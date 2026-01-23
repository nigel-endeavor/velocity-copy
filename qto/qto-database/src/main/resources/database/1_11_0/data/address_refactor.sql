ALTER TABLE company
ADD COLUMN address_1 VARCHAR(100),
ADD COLUMN address_2 VARCHAR(100),
ADD COLUMN city VARCHAR(100),
ADD COLUMN state_province VARCHAR(100),
ADD COLUMN postal_code VARCHAR(20),
ADD COLUMN country VARCHAR(100);

UPDATE company
JOIN address ON company.address_id = address.address_id
SET company.address_1 = address.address_1,
company.address_2 = address.address_2,
company.city = address.city,
company.state_province = address.state_province,
company.postal_code = address.postal_code,
company.country = address.country;

ALTER TABLE location
ADD COLUMN address_1 VARCHAR(100),
ADD COLUMN address_2 VARCHAR(100),
ADD COLUMN city VARCHAR(100),
ADD COLUMN state_province VARCHAR(100),
ADD COLUMN postal_code VARCHAR(20),
ADD COLUMN country VARCHAR(100);

UPDATE location
JOIN address ON location.address_id = address.address_id
SET location.address_1 = address.address_1,
location.address_2 = address.address_2,
location.city = address.city,
location.state_province = address.state_province,
location.postal_code = address.postal_code,
location.country = address.country;

ALTER TABLE service
ADD COLUMN address_1 VARCHAR(100),
ADD COLUMN address_2 VARCHAR(100),
ADD COLUMN city VARCHAR(100),
ADD COLUMN state_province VARCHAR(100),
ADD COLUMN postal_code VARCHAR(20),
ADD COLUMN country VARCHAR(100),
ADD COLUMN billing_email VARCHAR(100);

UPDATE service
JOIN address ON service.billing_address_id = address.address_id
SET service.address_1 = address.address_1,
service.address_2 = address.address_2,
service.city = address.city,
service.state_province = address.state_province,
service.postal_code = address.postal_code,
service.country = address.country,
service.billing_email = address.email;

ALTER TABLE ethernet_service
ADD COLUMN z_address_1 VARCHAR(100),
ADD COLUMN z_address_2 VARCHAR(100),
ADD COLUMN z_city VARCHAR(100),
ADD COLUMN z_state_province VARCHAR(100),
ADD COLUMN z_postal_code VARCHAR(20),
ADD COLUMN z_country VARCHAR(100);

UPDATE ethernet_service
JOIN address ON ethernet_service.z_address_id = address.address_id
SET ethernet_service.z_address_1 = address.address_1,
ethernet_service.z_address_2 = address.address_2,
ethernet_service.z_city = address.city,
ethernet_service.z_state_province = address.state_province,
ethernet_service.z_postal_code = address.postal_code,
ethernet_service.z_country = address.country;
