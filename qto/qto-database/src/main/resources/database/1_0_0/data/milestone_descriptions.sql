# Location
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Date the location was created in the order'
where m.milestone_name = 'Created' and mds.display_set_label = 'Location Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Order received and working'
where m.milestone_name = 'Received' and mds.display_set_label = 'Location Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Date the Design Engineer has been assigned to the Location'
where m.milestone_name = 'Design Engineer Assigned' and mds.display_set_label = 'Location Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'First reach out to LCON for Data Gathering effort'
where m.milestone_name = 'Initial Contact with Customer' and mds.display_set_label = 'Location Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Successful contact made with the customer to engage'
where m.milestone_name = 'Customer Contact Complete' and mds.display_set_label = 'Location Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Call scheduled with the customer for verbal data gathering discussion '
where m.milestone_name = 'TDG Interview Scheduled' and mds.display_set_label = 'Location Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Technical data is being provided and design is fully engaged with the LCON'
where m.milestone_name = 'TDG In Progress' and mds.display_set_label = 'Location Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Design completed and sent to the LCON for approval'
where m.milestone_name = 'Design Sent To Customer' and mds.display_set_label = 'Location Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Customer approved design date'
where m.milestone_name = 'Design Approved By Customer' and mds.display_set_label = 'Location Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'All data gathered and design is completed'
where m.milestone_name = 'TDG Complete' and mds.display_set_label = 'Location Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Confirmation of provisioning order start date'
where m.milestone_name = 'Provisioning Order Submitted' and mds.display_set_label = 'Location Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Location order is on hold, Jeop has been noted and opened'
where m.milestone_name = 'On Hold' and mds.display_set_label = 'Location Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'All aspects of the location order have been successfully completed'
where m.milestone_name = 'Complete' and mds.display_set_label = 'Location Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Order is on hold, Jeop has been noted and opened with a complete date and order closed out'
where m.milestone_name = 'Cancelled' and mds.display_set_label = 'Location Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Date order has been reassigned to another user worklist'
where m.milestone_name = 'Change In Assignment' and mds.display_set_label = 'Location Milestones';

# DIA
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Order created in the platform'
where m.milestone_name = 'Created' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Order received and working'
where m.milestone_name = 'Received' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Customer requested install date'
where m.milestone_name = 'Customer Requested Install' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'The date the site survey was requested to the carrier'
where m.milestone_name = 'Site Survey Submit' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Site Survey due date'
where m.milestone_name = 'Site Survey Due' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Date Site Survey results received from carrier'
where m.milestone_name = 'Site Survey Complete' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Network Provider Construction Start date provided'
where m.milestone_name = 'Network Provider Construction Start' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Network Provider Construction Complete date provided'
where m.milestone_name = 'Network Provider Construction Complete' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Date order submitted to the Carrier'
where m.milestone_name = 'Carrier Order Submitted' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'The access providers Firm Order Commitment date'
where m.milestone_name = 'Access Circuit FOC' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Network Firm Order Commitment date provided by the carrier'
where m.milestone_name = 'Network Provider FOC' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Date Equipment Order was submitted to the equipment vendor'
where m.milestone_name = 'Equipment Ordered' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Date the Equipment order was delivered to it''s destination'
where m.milestone_name = 'Equipment Received' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Confirmed date Equipment Configuration has completed'
where m.milestone_name = 'Equipment Configured' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Confirmation by Carrier that the Provisioning is completed'
where m.milestone_name = 'Data Provisioning Complete' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Pre activation tasks successfully completed with tech and/or LCON'
where m.milestone_name = 'Pre-Activation Call Complete' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Date the Activation was requested to be scheduled'
where m.milestone_name = 'Activation Requested' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Confirmated Activation scheduled on requested date'
where m.milestone_name = 'Activation Scheduled' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Date all activation activites were successfully completed'
where m.milestone_name = 'Activation Complete' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Emailed completion notification sent to LCON'
where m.milestone_name = 'Customer Completion Notification Sent' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Old service stop bill date confirmed'
where m.milestone_name = 'Customer Bill Stop' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Bill review successfully validated and completed'
where m.milestone_name = 'Billing Review Complete' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Order is on hold, Jeop has been noted and opened'
where m.milestone_name = 'On Hold' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'All aspects of the order have been successfully completed'
where m.milestone_name = 'Complete' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Order is on hold, Jeop has been noted and opened with a complete date and order closed out'
where m.milestone_name = 'Cancelled' and mds.display_set_label = 'DIA Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Date order has been reassigned to another user worklist'
where m.milestone_name = 'Change In Assignment' and mds.display_set_label = 'DIA Milestones';

# Broadband
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Order created in the platform'
where m.milestone_name = 'Created' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Order received and working'
where m.milestone_name = 'Received' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Customer requested install date'
where m.milestone_name = 'Customer Requested Install' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'The date the site survey was requested to the carrier'
where m.milestone_name = 'Site Survey Submit' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Site Survey due date'
where m.milestone_name = 'Site Survey Due' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Date Site Survey results received from carrier'
where m.milestone_name = 'Site Survey Complete' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Network Provider Construction Start date provided'
where m.milestone_name = 'Network Provider Construction Start' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Network Provider Construction Complete date provided'
where m.milestone_name = 'Network Provider Construction Complete' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Date order submitted to the Carrier'
where m.milestone_name = 'Carrier Order Submitted' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Network Firm Order Commitment date provided by the carrier'
where m.milestone_name = 'Network Provider FOC' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Confirmation by Carrier that the Provisioning is completed'
where m.milestone_name = 'Data Provisioning Complete' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Pre activation tasks successfully completed with tech and/or LCON'
where m.milestone_name = 'Pre-Activation Call Complete' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Date the Activation was requested to be scheduled'
where m.milestone_name = 'Activation Requested' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Confirmated Activation scheduled on requested date'
where m.milestone_name = 'Activation Scheduled' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Date all activation activites were successfully completed'
where m.milestone_name = 'Activation Complete' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Emailed completion notification sent to LCON'
where m.milestone_name = 'Customer Completion Notification Sent' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Old service stop bill date confirmed'
where m.milestone_name = 'Customer Bill Stop' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Bill review successfully validated and completed'
where m.milestone_name = 'Billing Review Complete' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Order is on hold, Jeop has been noted and opened'
where m.milestone_name = 'On Hold' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'All aspects of the order have been successfully completed'
where m.milestone_name = 'Complete' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Order is on hold, Jeop has been noted and opened with a complete date and order closed out'
where m.milestone_name = 'Cancelled' and mds.display_set_label = 'Broadband Milestones';
update milestone_display_set_include mdsi
inner join milestone_display_set mds on mdsi.milestone_display_set_id = mds.milestone_display_set_id
inner join milestone m on mdsi.milestone_id = m.milestone_id
set mdsi.description = 'Date order has been reassigned to another user worklist '
where m.milestone_name = 'Change In Assignment' and mds.display_set_label = 'Broadband Milestones';