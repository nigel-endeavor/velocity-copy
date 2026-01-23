CREATE TABLE service_microsoft_licenses
(
    microsoft_licenses_service_id    int AUTO_INCREMENT
        PRIMARY KEY,
    service_id int NOT NULL,
    e3 int NOT NULL,
    e5 int NOT NULL,
    business_premium int NOT NULL,
    microsoft_entra_id_p1 int NOT NULL,
    microsoft_entra_id_p2 int NOT NULL,
    microsoft_entra_suite int NOT NULL,
    microsoft_intune_plan_1 int NOT NULL,
    microsoft_intune_plan_2 int NOT NULL,
    microsoft_defender_for_endpoint_p1 int NOT NULL,
    microsoft_defender_for_endpoint_p2 int NOT NULL,
    defender_for_office_365_plan_1 int NOT NULL,
    defender_for_office_365_plan_2 int NOT NULL,

    CONSTRAINT FK_microsoft_licenses_service
        FOREIGN KEY (service_id) REFERENCES service (service_id)
);



