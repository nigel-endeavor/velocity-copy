update milestone_display_set set display_type = 'Ethernet' where display_group = 'ETHERNET_SERVICE_MILESTONE';

set @displaySetId = (Select milestone_display_set_id from milestone_display_set where display_group = 'ETHERNET_SERVICE_MILESTONE');
update milestone_display_set_include set status = 'Pending Assignment', progress_percentage = 5 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CREATED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Site Survey In Progress', progress_percentage = 10 where milestone_id = (Select milestone_id from milestone where milestone_code = 'SITE_SURVEY_SUBMIT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Site Survey Complete', progress_percentage = 15 where milestone_id = (Select milestone_id from milestone where milestone_code = 'SITE_SURVEY_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Network Provider Construction', progress_percentage = 15 where milestone_id = (Select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_START') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Network Provider Construction Complete', progress_percentage = 20 where milestone_id = (Select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Provisioning', progress_percentage = 25 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CARRIER_ORDER_SUBMITTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set progress_percentage = 35 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACCESS_CIRCUIT_FOC') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Provisioning', progress_percentage = 40 where milestone_id = (Select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_FOC') and milestone_display_set_id = @displaySetId ;
update milestone_display_set_include set status = 'Circuit Complete', progress_percentage = 60, inventory_flag = true where milestone_id = (Select milestone_id from milestone where milestone_code = 'DATA_PROVISIONING_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Circuit Complete', progress_percentage = 65, inventory_flag = false where milestone_id = (Select milestone_id from milestone where milestone_code = 'CROSS_CONNECT_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Activation Requested', progress_percentage = 70 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_REQUESTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Activation Scheduled', progress_percentage = 80 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_SCHEDULED') and  milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Complete', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Complete', progress_percentage = 100, inventory_flag = TRUE where milestone_id = (Select milestone_id from milestone where milestone_code = 'COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Change In Assignment', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CHANGE_IN_ASSIGNMENT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Cancelled', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CANCELLED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'On Hold' where milestone_id = (Select milestone_id from milestone where milestone_code = 'ON_HOLD') and milestone_display_set_id = @displaySetId;


set @displaySetId = (Select milestone_display_set_id from milestone_display_set where display_group = 'DIA_SERVICE_MILESTONE');
update milestone_display_set_include set status = null where milestone_id = (Select milestone_id from milestone where milestone_code = 'EQUIPMENT_ORDERED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = null where milestone_id = (Select milestone_id from milestone where milestone_code = 'EQUIPMENT_RECEIVED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = null where milestone_id = (Select milestone_id from milestone where milestone_code = 'EQUIPMENT_CONFIGURED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Network Provider Construction', progress_percentage = 15 where milestone_id = (Select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_START') and milestone_display_set_id = @displaySetId;


set @displaySetId = (Select milestone_display_set_id from milestone_display_set where display_group = 'BROADBAND_SERVICE_MILESTONE');
update milestone_display_set_include set status = 'Network Provider Construction', progress_percentage = 15 where milestone_id = (Select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_START') and milestone_display_set_id = @displaySetId;
