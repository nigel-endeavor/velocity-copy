ALTER TABLE address
ADD COLUMN master_customer_id INT NULL;

ALTER TABLE address
ADD CONSTRAINT fk_address_master_customer_id
FOREIGN KEY (master_customer_id) REFERENCES company(company_id);

UPDATE address
JOIN company c ON address.company_id = c.company_id
SET address.master_customer_id = c.master_customer_id;