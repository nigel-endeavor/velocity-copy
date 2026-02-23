CREATE TABLE custom_field (
    custom_field_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    label VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    required BOOLEAN NOT NULL DEFAULT FALSE,
    version INT NOT NULL DEFAULT 0,
    tenant_id INT NOT NULL
);

CREATE TABLE custom_field_value (
    custom_field_value_id SERIAL PRIMARY KEY,
    custom_field_id INT NOT NULL,
    value VARCHAR(200) NOT NULL,
    version INT NOT NULL DEFAULT 0,
    tenant_id INT NOT NULL,
    CONSTRAINT fk_custom_field_value_custom_field FOREIGN KEY (custom_field_id) REFERENCES custom_field(custom_field_id)
);

CREATE TABLE custom_field_tab (
    custom_field_tab_id SERIAL PRIMARY KEY,
    custom_field_id INT NOT NULL,
    tab VARCHAR(50) NOT NULL,
    CONSTRAINT fk_custom_field_tab_custom_field FOREIGN KEY (custom_field_id) REFERENCES custom_field(custom_field_id)
);

CREATE TABLE service_custom_field_value (
    service_id INT NOT NULL,
    custom_field_value_id INT NOT NULL,
    CONSTRAINT fk_service_custom_field_value_service FOREIGN KEY (service_id) REFERENCES service(service_id),
    CONSTRAINT fk_service_custom_field_value_custom_field_value FOREIGN KEY (custom_field_value_id) REFERENCES custom_field_value(custom_field_value_id)
);

CREATE TABLE location_custom_field_value (
    location_id INT NOT NULL,
    custom_field_value_id INT NOT NULL,
    CONSTRAINT fk_location_custom_field_value_location FOREIGN KEY (location_id) REFERENCES location(location_id),
    CONSTRAINT fk_location_custom_field_value_custom_field_value FOREIGN KEY (custom_field_value_id) REFERENCES custom_field_value(custom_field_value_id)
);