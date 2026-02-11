CREATE TABLE mpls_service
(
    service_id INT PRIMARY KEY,
    last_mile_provider VARCHAR(100),
    access_circuit_id VARCHAR(100),
    port_circuit_id VARCHAR(100),
    mpls_type VARCHAR(100),
    port_speed VARCHAR(100),
    interface_connector VARCHAR(100),
    provider_activation_method VARCHAR(100),
    npa_nxx VARCHAR(100),
    routing_protocol VARCHAR(100),
    cer_ips VARCHAR(100),
    per_ips VARCHAR(100),
    vlan_tag_1 VARCHAR(100),
    vlan_tag_2 VARCHAR(100),
    vlan_tag_3 VARCHAR(100),
    vlan_tag_4 VARCHAR(100),
    other_technical_notes VARCHAR(500),
    CONSTRAINT FK_mpls_service_service
        FOREIGN KEY (service_id) REFERENCES service (service_id)
);