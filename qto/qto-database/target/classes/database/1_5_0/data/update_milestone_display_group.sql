
update milestone_display_set set display_type = 'Order' where display_group = 'ORDER_MILESTONE';
update milestone_display_set set display_type = 'Location' where display_group = 'LOCATION_MILESTONE';
update milestone_display_set set display_type = 'Broadband' where display_group = 'BROADBAND_SERVICE_MILESTONE';
update milestone_display_set set display_type = 'DIA' where display_group = 'DIA_SERVICE_MILESTONE';
update milestone_display_set set display_type = 'UCaaS' where display_group = 'UCAAS_SERVICE_MILESTONE';
update milestone_display_set set display_type = '4G/5G' where display_group = '4G5G_SERVICE_MILESTONE';
update milestone_display_set set display_type = 'MAC' where display_group = 'EXISTING_MAC_MILESTONE';
update milestone_display_set set display_type = 'Disconnect' where display_group = 'DISCONNECT_MILESTONE';
update milestone_display_set set display_type = 'Cross Connect' where display_group = 'CROSS_CONNECT_SERVICE_MILESTONE';
