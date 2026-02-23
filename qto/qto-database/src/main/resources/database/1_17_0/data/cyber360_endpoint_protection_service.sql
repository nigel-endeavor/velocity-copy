CREATE TABLE service_cyber360_endpoint_protection
(
    cyber360_endpoint_protection_service_id    SERIAL
        PRIMARY KEY,
    service_id              int NOT NULL,


    CONSTRAINT FK_service_cyber360_endpoint_protection_service
        FOREIGN KEY (service_id) REFERENCES service (service_id)
);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, tenant_id)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ALL_SERVICE_TYPES'), 'cyber360-Endpoint Protection', 'cyber360-Endpoint Protection', 0, 1, 0);
