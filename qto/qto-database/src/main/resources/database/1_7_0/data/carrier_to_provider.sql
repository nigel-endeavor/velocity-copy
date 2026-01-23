# Populate new cols with data from old cols
UPDATE service SET provider = carrier;
UPDATE service SET provider_site_account_num = carrier_site_account_num;
UPDATE service SET provider_order_num = carrier_order_num;
UPDATE service SET provider_circuit_id = carrier_circuit_id;
UPDATE dia_service SET provider_activation_method = carrier_activation_method;
UPDATE dia_service SET last_mile_provider = last_mile_carrier;

UPDATE interval_instance SET provider_business_day_deduct_time = carrier_business_day_deduct_time;
UPDATE interval_instance SET provider_calendar_day_deduct_time = carrier_calendar_day_deduct_time;

UPDATE cost_history SET type_provider = type_carrier;

#  change milestone name
UPDATE milestone SET milestone_code = 'PROVIDER_ORDER_SUBMITTED', milestone_name = 'Provider Order Submitted'
WHERE milestone_code = 'CARRIER_ORDER_SUBMITTED';
UPDATE milestone SET milestone_code = 'PROVIDER_DISCONNECT_COMPLETE', milestone_name = 'Provider Disconnect Complete'
WHERE milestone_code = 'CARRIER_DISCONNECT_COMPLETE';

UPDATE milestone_display_set_include SET status = 'Provider Disconnect Order Complete', description = 'Confirmation by Provider that the service has been disconnected'
WHERE status = 'Carrier Disconnect Order Complete';

UPDATE interval_type SET interval_type_desc = 'Provider Install Interval', interval_type_code = 'PROVIDER_ORDER_SUBMITTED_TO_DATA_PROVISIONING_COMPLETE'
WHERE interval_type_code = 'CARRIER_ORDER_SUBMITTED_TO_DATA_PROVISIONING_COMPLETE';

# change lookup type name
UPDATE lookup_type SET lookup_type_code = 'PROVIDER', lookup_type_descr = 'Provider'
WHERE lookup_type_code = 'CARRIER';
UPDATE lookup_type SET lookup_type_code =  'PROVIDER_ACTIVATION_METHOD', lookup_type_descr = 'Provider Activation Method'
WHERE lookup_type_code = 'CARRIER_ACTIVATION_METHOD';

# change lookup values
# Carrier
UPDATE lookup_value SET lookup_display = 'Provider', lookup_value = 'Provider'
WHERE lookup_display = 'Carrier';
# Carrier Other
UPDATE lookup_value SET lookup_display = 'Provider Other', lookup_value = 'Provider Other'
WHERE lookup_display = 'Carrier Other';
# Upgraded speed - Service replaced with same carrier
UPDATE lookup_value SET lookup_display = 'Upgraded speed - Service replaced with same provider', lookup_value = 'Upgraded speed - Service replaced with same provider'
WHERE lookup_display = 'Upgraded speed - Service replaced with same carrier';
# Poor performance - Service replaced with same carrier
UPDATE lookup_value SET lookup_display = 'Poor performance - Service replaced with same provider', lookup_value = 'Poor performance - Service replaced with same provider'
WHERE lookup_display = 'Poor performance - Service replaced with same carrier';
# Poor performance - Service replaced with new carrier
UPDATE lookup_value SET lookup_display = 'Poor performance - Service replaced with new provider', lookup_value = 'Poor performance - Service replaced with new provider'
WHERE lookup_display = 'Poor performance - Service replaced with new carrier';
# Service groom - Service replaced with new carrier
UPDATE lookup_value SET lookup_display = 'Service groom - Service replaced with new provider', lookup_value = 'Service groom - Service replaced with new provider'
WHERE lookup_display = 'Service groom - Service replaced with new carrier';
# New Service - Change carrier
UPDATE lookup_value SET lookup_display = 'New Service - Change provider', lookup_value = 'New Service - Change provider'
WHERE lookup_display = 'New Service - Change carrier';
# --Upgraded Speed -Service replaced with same carrier
UPDATE lookup_value SET lookup_display = '--Upgraded Speed -Service replaced with same provider', lookup_value = '--Upgraded Speed -Service replaced with same provider'
WHERE lookup_display = '--Upgraded Speed -Service replaced with same carrier';
# --Poor Performance - Service replaced with same carrier
UPDATE lookup_value SET lookup_display = '--Poor Performance - Service replaced with same provider', lookup_value = '--Poor Performance - Service replaced with same provider'
WHERE lookup_display = '--Poor Performance - Service replaced with same carrier';
# --Poor Performance - Service replaced with new carrier
UPDATE lookup_value SET lookup_display = '--Poor Performance - Service replaced with new provider', lookup_value = '--Poor Performance - Service replaced with new provider'
WHERE lookup_display = '--Poor Performance - Service replaced with new carrier';
# --Service Groom - Service replaced with new carrier
UPDATE lookup_value SET lookup_display = '--Service Groom - Service replaced with new provider', lookup_value = '--Service Groom - Service replaced with new provider'
WHERE lookup_display = '--Service Groom - Service replaced with new carrier';
