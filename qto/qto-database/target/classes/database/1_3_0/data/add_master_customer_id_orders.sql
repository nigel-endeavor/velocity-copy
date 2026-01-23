ALTER TABLE orders
ADD COLUMN master_customer_id INT NULL;

ALTER TABLE orders
ADD CONSTRAINT fk_orders_master_customer_id
FOREIGN KEY (master_customer_id) REFERENCES company(company_id);

UPDATE orders
JOIN company c on orders.company_id = c.company_id
SET orders.master_customer_id = c.master_customer_id;
