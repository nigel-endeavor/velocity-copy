CREATE TABLE service_cyber360_information_protection
(
    cyber360_information_protection_service_id    int AUTO_INCREMENT
        PRIMARY KEY,
    service_id              int NOT NULL,


    CONSTRAINT FK_service_cyber360_information_protection_service
        FOREIGN KEY (service_id) REFERENCES service (service_id)
);


SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ALL_SERVICE_TYPES');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active, tenant_id)
VALUES (@lookupTypeCode, 'cyber360-Information Protection', 'cyber360-Information Protection', 0, 1, 0);
