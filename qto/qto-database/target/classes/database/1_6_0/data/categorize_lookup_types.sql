UPDATE lookup_type SET modifiable = 0 WHERE lookup_type_code = 'LOCATION_STATUS';
UPDATE lookup_type SET modifiable = 0 WHERE lookup_type_code = 'BROADBAND_STATUS';
UPDATE lookup_type SET modifiable = 0 WHERE lookup_type_code = 'DIA_STATUS';
UPDATE lookup_type SET modifiable = 0 WHERE lookup_type_code = 'ACTIVATION_ATTEMPT_STATUS';
UPDATE lookup_type SET modifiable = 0 WHERE lookup_type_code = 'ORDER_TYPE';

-- Location/Service General
UPDATE lookup_type SET category = 'Location/Service General' WHERE lookup_type_code = 'CARRIER';
UPDATE lookup_type SET category = 'Location/Service General' WHERE lookup_type_code = 'CONTRACT_TERM';
UPDATE lookup_type SET category = 'Location/Service General' WHERE lookup_type_code = 'CLIENT_PROJECT_MANAGER';
UPDATE lookup_type SET category = 'Location/Service General' WHERE lookup_type_code = 'PROJECT_NAME';
UPDATE lookup_type SET category = 'Location/Service General' WHERE lookup_type_code = 'SERVICE_BILLED_TO';
UPDATE lookup_type SET category = 'Location/Service General' WHERE lookup_type_code = 'CONTACT_ROLE';

-- Service Technical - Common
UPDATE lookup_type SET category = 'Service Technical - Common' WHERE lookup_type_code = 'SPEED';
UPDATE lookup_type SET category = 'Service Technical - Common' WHERE lookup_type_code = 'OSP_CONS_INTERVAL';
UPDATE lookup_type SET category = 'Service Technical - Common' WHERE lookup_type_code = 'MEDIA_TYPE';
UPDATE lookup_type SET category = 'Service Technical - Common' WHERE lookup_type_code = 'NET_STATUS';
UPDATE lookup_type SET category = 'Service Technical - Common' WHERE lookup_type_code = 'PRODUCT_INSTALL_INTERVAL';
UPDATE lookup_type SET category = 'Service Technical - Common' WHERE lookup_type_code = 'ADDITIONAL_IP_BLOCK';
UPDATE lookup_type SET category = 'Service Technical - Common' WHERE lookup_type_code = 'BUILDING_STATUS';
UPDATE lookup_type SET category = 'Service Technical - Common' WHERE lookup_type_code = 'NETWORK_PROTOCOL';
UPDATE lookup_type SET category = 'Service Technical - Common' WHERE lookup_type_code = 'INTERFACE_CONNECTOR';

-- Service Technical - Product Specific
UPDATE lookup_type SET category = 'Service Technical - Product Specific' WHERE lookup_type_code = 'CARRIER_ACTIVATION_METHOD';
UPDATE lookup_type SET category = 'Service Technical - Product Specific' WHERE lookup_type_code = 'CROSS_CONNECT_TYPE';
UPDATE lookup_type SET category = 'Service Technical - Product Specific' WHERE lookup_type_code = 'ETHERNET_PRODUCT_TYPE';
UPDATE lookup_type SET category = 'Service Technical - Product Specific' WHERE lookup_type_code = 'MTU';
UPDATE lookup_type SET category = 'Service Technical - Product Specific' WHERE lookup_type_code = 'CABLE_CATEGORY';
UPDATE lookup_type SET category = 'Service Technical - Product Specific' WHERE lookup_type_code = 'MUX';
UPDATE lookup_type SET category = 'Service Technical - Product Specific' WHERE lookup_type_code = 'ACCESS_TYPE';
UPDATE lookup_type SET category = 'Service Technical - Product Specific' WHERE lookup_type_code = 'HANDOFF_FIBER_MODE';
UPDATE lookup_type SET category = 'Service Technical - Product Specific' WHERE lookup_type_code = 'CIRCUIT_PRIORITY';

-- Activations
UPDATE lookup_type SET category = 'Activations' WHERE lookup_type_code = 'DISPATCH_VENDOR';
UPDATE lookup_type SET category = 'Activations' WHERE lookup_type_code = 'ACTIVATION_REQUIREMENTS';
UPDATE lookup_type SET category = 'Activations' WHERE lookup_type_code = 'ACTIVATION_REPLACE_4G5G';
UPDATE lookup_type SET category = 'Activations' WHERE lookup_type_code = 'ACTIVATION_ISSUE_PRIMARY';
UPDATE lookup_type SET category = 'Activations' WHERE lookup_type_code = 'ACTIVATION_ISSUE_SECONDARY';
UPDATE lookup_type SET category = 'Activations' WHERE lookup_type_code = 'ACTIVATION_ISSUE_TERTIARY';

-- Jeopardies
UPDATE lookup_type SET category = 'Jeopardies' WHERE lookup_type_code = 'ORDER_JEOPARDY';
UPDATE lookup_type SET category = 'Jeopardies' WHERE lookup_type_code = 'LOCATION_JEOPARDY';
UPDATE lookup_type SET category = 'Jeopardies' WHERE lookup_type_code = 'SERVICE_JEOPARDY';
UPDATE lookup_type SET category = 'Jeopardies' WHERE lookup_type_code = 'JEOPARDY_RESPONSIBILITY';

-- MACD & Disputes
UPDATE lookup_type SET category = 'MACD & Disputes' WHERE lookup_type_code = 'DISCONNECT_REASON';
UPDATE lookup_type SET category = 'MACD & Disputes' WHERE lookup_type_code = 'COST_CHANGE_REASON';
UPDATE lookup_type SET category = 'MACD & Disputes' WHERE lookup_type_code = 'DISPUTE_TYPE';
