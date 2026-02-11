set @displaySetId = (select milestone_display_set_id from milestone_display_set where display_group = 'BROADBAND_SERVICE_MILESTONE');

update milestone_display_set_include set milestone_sequence = 10 where milestone_id = (select milestone_id from milestone where milestone_code = 'CREATED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 20 where milestone_id = (select milestone_id from milestone where milestone_code = 'RECEIVED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 30 where milestone_id = (select milestone_id from milestone where milestone_code = 'CUSTOMER_REQUESTED_INSTALL') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1010 where milestone_id = (select milestone_id from milestone where milestone_code = 'SITE_SURVEY_SUBMIT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1020 where milestone_id = (select milestone_id from milestone where milestone_code = 'SITE_SURVEY_DUE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1030 where milestone_id = (select milestone_id from milestone where milestone_code = 'SITE_SURVEY_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1040 where milestone_id = (select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_START') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1050 where milestone_id = (select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1060 where milestone_id = (select milestone_id from milestone where milestone_code = 'CARRIER_ORDER_SUBMITTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1070 where milestone_id = (select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_FOC') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1080 where milestone_id = (select milestone_id from milestone where milestone_code = 'DATA_PROVISIONING_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 2010 where milestone_id = (select milestone_id from milestone where milestone_code = 'PRE_ACTIVATION_CALL_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 2020 where milestone_id = (select milestone_id from milestone where milestone_code = 'ACTIVATION_REQUESTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 2030 where milestone_id = (select milestone_id from milestone where milestone_code = 'ACTIVATION_SCHEDULED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 2040 where milestone_id = (select milestone_id from milestone where milestone_code = 'ACTIVATION_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 2050 where milestone_id = (select milestone_id from milestone where milestone_code = 'CUSTOMER_COMPLETION_NOTIFICATION_SENT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 3010 where milestone_id = (select milestone_id from milestone where milestone_code = 'ON_HOLD') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 3020 where milestone_id = (select milestone_id from milestone where milestone_code = 'COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 3030 where milestone_id = (select milestone_id from milestone where milestone_code = 'CANCELLED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 3040 where milestone_id = (select milestone_id from milestone where milestone_code = 'CHANGE_IN_ASSIGNMENT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 4010 where milestone_id = (select milestone_id from milestone where milestone_code = 'CUSTOMER_BILL_STOP') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 4020 where milestone_id = (select milestone_id from milestone where milestone_code = 'BILLING_REVIEW_COMPLETE') and milestone_display_set_id = @displaySetId;


set @displaySetId = (select milestone_display_set_id from milestone_display_set where display_group = 'DIA_SERVICE_MILESTONE');

update milestone_display_set_include set milestone_sequence = 10 where milestone_id = (select milestone_id from milestone where milestone_code = 'CREATED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 20 where milestone_id = (select milestone_id from milestone where milestone_code = 'RECEIVED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 30 where milestone_id = (select milestone_id from milestone where milestone_code = 'CUSTOMER_REQUESTED_INSTALL') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1010 where milestone_id = (select milestone_id from milestone where milestone_code = 'SITE_SURVEY_SUBMIT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1020 where milestone_id = (select milestone_id from milestone where milestone_code = 'SITE_SURVEY_DUE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1030 where milestone_id = (select milestone_id from milestone where milestone_code = 'SITE_SURVEY_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1040 where milestone_id = (select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_START') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1050 where milestone_id = (select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1060 where milestone_id = (select milestone_id from milestone where milestone_code = 'CARRIER_ORDER_SUBMITTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1070 where milestone_id = (select milestone_id from milestone where milestone_code = 'ACCESS_CIRCUIT_FOC') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1080 where milestone_id = (select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_FOC') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1090 where milestone_id = (select milestone_id from milestone where milestone_code = 'EQUIPMENT_ORDERED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1100 where milestone_id = (select milestone_id from milestone where milestone_code = 'EQUIPMENT_RECEIVED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1110 where milestone_id = (select milestone_id from milestone where milestone_code = 'EQUIPMENT_CONFIGURED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1120 where milestone_id = (select milestone_id from milestone where milestone_code = 'DATA_PROVISIONING_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 2010 where milestone_id = (select milestone_id from milestone where milestone_code = 'PRE_ACTIVATION_CALL_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 2020 where milestone_id = (select milestone_id from milestone where milestone_code = 'ACTIVATION_REQUESTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 2030 where milestone_id = (select milestone_id from milestone where milestone_code = 'ACTIVATION_SCHEDULED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 2040 where milestone_id = (select milestone_id from milestone where milestone_code = 'ACTIVATION_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 2050 where milestone_id = (select milestone_id from milestone where milestone_code = 'CUSTOMER_COMPLETION_NOTIFICATION_SENT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 3010 where milestone_id = (select milestone_id from milestone where milestone_code = 'ON_HOLD') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 3020 where milestone_id = (select milestone_id from milestone where milestone_code = 'COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 3030 where milestone_id = (select milestone_id from milestone where milestone_code = 'CANCELLED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 3040 where milestone_id = (select milestone_id from milestone where milestone_code = 'CHANGE_IN_ASSIGNMENT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 4010 where milestone_id = (select milestone_id from milestone where milestone_code = 'CUSTOMER_BILL_STOP') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 4020 where milestone_id = (select milestone_id from milestone where milestone_code = 'BILLING_REVIEW_COMPLETE') and milestone_display_set_id = @displaySetId;


set @displaySetId = (select milestone_display_set_id from milestone_display_set where display_group = 'UCAAS_SERVICE_MILESTONE');

update milestone_display_set_include set milestone_sequence = 10 where milestone_id = (select milestone_id from milestone where milestone_code = 'CREATED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 20 where milestone_id = (select milestone_id from milestone where milestone_code = 'RECEIVED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 30 where milestone_id = (select milestone_id from milestone where milestone_code = 'ENGINEER_ASSIGNED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1010 where milestone_id = (select milestone_id from milestone where milestone_code = 'EQUIPMENT_ORDERED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1020 where milestone_id = (select milestone_id from milestone where milestone_code = 'EQUIPMENT_RECEIVED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 1030 where milestone_id = (select milestone_id from milestone where milestone_code = 'EQUIPMENT_CONFIGURED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 2010 where milestone_id = (select milestone_id from milestone where milestone_code = 'ACTIVATION_REQUESTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 2020 where milestone_id = (select milestone_id from milestone where milestone_code = 'ACTIVATION_SCHEDULED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 2030 where milestone_id = (select milestone_id from milestone where milestone_code = 'ACTIVATION_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 3010 where milestone_id = (select milestone_id from milestone where milestone_code = 'ON_HOLD') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 3020 where milestone_id = (select milestone_id from milestone where milestone_code = 'COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 3030 where milestone_id = (select milestone_id from milestone where milestone_code = 'CANCELLED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set milestone_sequence = 3040 where milestone_id = (select milestone_id from milestone where milestone_code = 'CHANGE_IN_ASSIGNMENT') and milestone_display_set_id = @displaySetId;
