ALTER TABLE activation_schedule
ADD COLUMN master_customer_id INT NULL;

ALTER TABLE activation_schedule
ADD CONSTRAINT fk_activation_schedule_master_customer_id
FOREIGN KEY (master_customer_id) REFERENCES company(company_id);

UPDATE activation_schedule
JOIN service s ON activation_schedule.service_id = s.service_id
SET activation_schedule.master_customer_id = s.master_customer_id;


ALTER TABLE activation_attempt
ADD COLUMN master_customer_id INT NULL;

ALTER TABLE activation_attempt
ADD CONSTRAINT fk_activation_attempt_master_customer_id
FOREIGN KEY (master_customer_id) REFERENCES company(company_id);

UPDATE activation_attempt
JOIN service s ON activation_attempt.service_id = s.service_id
SET activation_attempt.master_customer_id = s.master_customer_id;


ALTER TABLE activation_issue
ADD COLUMN master_customer_id INT NULL;

ALTER TABLE activation_issue
ADD CONSTRAINT fk_activation_issue_master_customer_id
FOREIGN KEY (master_customer_id) REFERENCES company(company_id);

UPDATE activation_issue
JOIN activation_attempt a ON activation_issue.activation_attempt_id = a.activation_attempt_id
SET activation_issue.master_customer_id = a.master_customer_id;
