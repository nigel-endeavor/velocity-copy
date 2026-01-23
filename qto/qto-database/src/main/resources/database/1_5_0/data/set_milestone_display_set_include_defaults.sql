set @displaySetId = (Select milestone_display_set_id from milestone_display_set where display_group = '4G5G_SERVICE_MILESTONE');
update milestone_display_set_include set status = 'Pending Assignment', progress_percentage = 5 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CREATED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Equipment Ordered' where milestone_id = (Select milestone_id from milestone where milestone_code = 'EQUIPMENT_ORDERED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Equipment Received' where milestone_id = (Select milestone_id from milestone where milestone_code = 'EQUIPMENT_RECEIVED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Equipment Configured' where milestone_id = (Select milestone_id from milestone where milestone_code = 'EQUIPMENT_CONFIGURED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Activation Requested', progress_percentage = 70 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_REQUESTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Activation Scheduled', progress_percentage = 80 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_SCHEDULED') and  milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Complete', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Complete', progress_percentage = 100, inventory_flag = TRUE where milestone_id = (Select milestone_id from milestone where milestone_code = 'COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Change In Assignment', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CHANGE_IN_ASSIGNMENT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Cancelled', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CANCELLED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'On Hold' where milestone_id = (Select milestone_id from milestone where milestone_code = 'ON_HOLD') and milestone_display_set_id = @displaySetId;

set @displaySetId = (Select milestone_display_set_id from milestone_display_set where display_group = 'BROADBAND_SERVICE_MILESTONE');
update milestone_display_set_include set status = 'Pending Assignment', progress_percentage = 5 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CREATED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Site Survey In Progress', progress_percentage = 10 where milestone_id = (Select milestone_id from milestone where milestone_code = 'SITE_SURVEY_SUBMIT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Site Survey Complete', progress_percentage = 15 where milestone_id = (Select milestone_id from milestone where milestone_code = 'SITE_SURVEY_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Network Provider Construction', progress_percentage = 5 where milestone_id = (Select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_START') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Network Provider Construction Complete', progress_percentage = 20 where milestone_id = (Select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Provisioning', progress_percentage = 25 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CARRIER_ORDER_SUBMITTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Provisioning', progress_percentage = 40 where milestone_id = (Select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_FOC') and milestone_display_set_id = @displaySetId ;
update milestone_display_set_include set status = 'Circuit Complete', progress_percentage = 60, inventory_flag = true where milestone_id = (Select milestone_id from milestone where milestone_code = 'DATA_PROVISIONING_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Activation Requested', progress_percentage = 70 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_REQUESTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Activation Scheduled', progress_percentage = 80 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_SCHEDULED') and  milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Complete', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Complete', progress_percentage = 100, inventory_flag = TRUE where milestone_id = (Select milestone_id from milestone where milestone_code = 'COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Change In Assignment', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CHANGE_IN_ASSIGNMENT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Cancelled', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CANCELLED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'On Hold' where milestone_id = (Select milestone_id from milestone where milestone_code = 'ON_HOLD') and milestone_display_set_id = @displaySetId;

set @displaySetId = (Select milestone_display_set_id from milestone_display_set where display_group = 'CROSS_CONNECT_SERVICE_MILESTONE');
update milestone_display_set_include set status = 'Pending Assignment', progress_percentage = 5 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CREATED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Provisioning', progress_percentage = 25 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CARRIER_ORDER_SUBMITTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Provisioning', progress_percentage = 40 where milestone_id = (Select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_FOC') and milestone_display_set_id = @displaySetId ;
update milestone_display_set_include set status = 'Circuit Complete', progress_percentage = 60, inventory_flag = true where milestone_id = (Select milestone_id from milestone where milestone_code = 'DATA_PROVISIONING_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Complete', progress_percentage = 100, inventory_flag = TRUE where milestone_id = (Select milestone_id from milestone where milestone_code = 'COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Change In Assignment', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CHANGE_IN_ASSIGNMENT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Cancelled', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CANCELLED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'On Hold' where milestone_id = (Select milestone_id from milestone where milestone_code = 'ON_HOLD') and milestone_display_set_id = @displaySetId;



set @displaySetId = (Select milestone_display_set_id from milestone_display_set where display_group = 'DIA_SERVICE_MILESTONE');
update milestone_display_set_include set status = 'Pending Assignment', progress_percentage = 5 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CREATED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Site Survey In Progress', progress_percentage = 10 where milestone_id = (Select milestone_id from milestone where milestone_code = 'SITE_SURVEY_SUBMIT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Site Survey Complete', progress_percentage = 15 where milestone_id = (Select milestone_id from milestone where milestone_code = 'SITE_SURVEY_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Network Provider Construction', progress_percentage = 5 where milestone_id = (Select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_START') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Network Provider Construction Complete', progress_percentage = 20 where milestone_id = (Select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_CONSTRUCTION_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Provisioning', progress_percentage = 25 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CARRIER_ORDER_SUBMITTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set progress_percentage = 35 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACCESS_CIRCUIT_FOC') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Provisioning', progress_percentage = 40 where milestone_id = (Select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_FOC') and milestone_display_set_id = @displaySetId ;
update milestone_display_set_include set status = 'Equipment Ordered' where milestone_id = (Select milestone_id from milestone where milestone_code = 'EQUIPMENT_ORDERED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Equipment Received' where milestone_id = (Select milestone_id from milestone where milestone_code = 'EQUIPMENT_RECEIVED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Equipment Configured'where milestone_id = (Select milestone_id from milestone where milestone_code = 'EQUIPMENT_CONFIGURED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Circuit Complete', progress_percentage = 60, inventory_flag = true where milestone_id = (Select milestone_id from milestone where milestone_code = 'DATA_PROVISIONING_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Activation Requested', progress_percentage = 70 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_REQUESTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Activation Scheduled', progress_percentage = 80 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_SCHEDULED') and  milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Complete', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Complete', progress_percentage = 100, inventory_flag = TRUE where milestone_id = (Select milestone_id from milestone where milestone_code = 'COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Change In Assignment', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CHANGE_IN_ASSIGNMENT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Cancelled', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CANCELLED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'On Hold' where milestone_id = (Select milestone_id from milestone where milestone_code = 'ON_HOLD') and milestone_display_set_id = @displaySetId;


set @displaySetId = (Select milestone_display_set_id from milestone_display_set where display_group = 'DISCONNECT_MILESTONE');
update milestone_display_set_include set status = 'Pending Assignment', progress_percentage = 5 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CREATED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Disconnect In Progress', progress_percentage = 25 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CARRIER_ORDER_SUBMITTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Disconnect In Progress', progress_percentage = 40 where milestone_id = (Select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_FOC') and milestone_display_set_id = @displaySetId ;
update milestone_display_set_include set status = 'Carrier Disconnect Order Complete', progress_percentage = 60, inventory_flag = true where milestone_id = (Select milestone_id from milestone where milestone_code = 'CARRIER_DISCONNECT_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Billing Review Complete', progress_percentage = 80 where milestone_id = (Select milestone_id from milestone where milestone_code = 'BILLING_REVIEW_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Disconnect Complete', progress_percentage = 100, inventory_flag = TRUE where milestone_id = (Select milestone_id from milestone where milestone_code = 'COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Change In Assignment', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CHANGE_IN_ASSIGNMENT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Disconnect Cancelled', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CANCELLED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'On Hold' where milestone_id = (Select milestone_id from milestone where milestone_code = 'ON_HOLD') and milestone_display_set_id = @displaySetId;



set @displaySetId = (Select milestone_display_set_id from milestone_display_set where display_group = 'EXISTING_MAC_MILESTONE');
update milestone_display_set_include set status = 'Pending Assignment', progress_percentage = 5 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CREATED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Provisioning', progress_percentage = 25 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CARRIER_ORDER_SUBMITTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Provisioning', progress_percentage = 40 where milestone_id = (Select milestone_id from milestone where milestone_code = 'NETWORK_PROVIDER_FOC') and milestone_display_set_id = @displaySetId ;
update milestone_display_set_include set status = 'Circuit Complete', progress_percentage = 60, inventory_flag = true where milestone_id = (Select milestone_id from milestone where milestone_code = 'DATA_PROVISIONING_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Activation Requested', progress_percentage = 70 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_REQUESTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Activation Scheduled', progress_percentage = 80 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_SCHEDULED') and  milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Complete', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Complete', progress_percentage = 100, inventory_flag = TRUE where milestone_id = (Select milestone_id from milestone where milestone_code = 'COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Change In Assignment', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CHANGE_IN_ASSIGNMENT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Cancelled', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CANCELLED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'On Hold' where milestone_id = (Select milestone_id from milestone where milestone_code = 'ON_HOLD') and milestone_display_set_id = @displaySetId;


set @displaySetId = (Select milestone_display_set_id from milestone_display_set where display_group = 'UCAAS_SERVICE_MILESTONE');
update milestone_display_set_include set status = 'Pending Assignment', progress_percentage = 5 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CREATED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Engineer Assigned', progress_percentage = 5 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ENGINEER_ASSIGNED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Equipment Received' where milestone_id = (Select milestone_id from milestone where milestone_code = 'EQUIPMENT_RECEIVED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Equipment Configured', progress_percentage = 5 where milestone_id = (Select milestone_id from milestone where milestone_code = 'EQUIPMENT_CONFIGURED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Activation Requested', progress_percentage = 70 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_REQUESTED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Activation Scheduled', progress_percentage = 80 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_SCHEDULED') and  milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Complete', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'ACTIVATION_COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Complete', progress_percentage = 100, inventory_flag = TRUE where milestone_id = (Select milestone_id from milestone where milestone_code = 'COMPLETE') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Change In Assignment', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CHANGE_IN_ASSIGNMENT') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'Service Cancelled', progress_percentage = 100 where milestone_id = (Select milestone_id from milestone where milestone_code = 'CANCELLED') and milestone_display_set_id = @displaySetId;
update milestone_display_set_include set status = 'On Hold' where milestone_id = (Select milestone_id from milestone where milestone_code = 'ON_HOLD') and milestone_display_set_id = @displaySetId;


select mdsi.milestone_display_set_include_id, mds.display_group, m.milestone_code, milestone_sequence, status, inventory_flag, progress_percentage from milestone_display_set_include mdsi
     join milestone m ON mdsi.milestone_id = m.milestone_id
     join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
order by mds.display_group, mdsi.milestone_sequence
