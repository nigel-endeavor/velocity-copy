INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Order Jeopardy', 'ORDER_JEOPARDY', true, true, true);

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer Unresponsive', 'Customer Unresponsive', 0, true);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Location Jeopardy', 'LOCATION_JEOPARDY', true, true, false);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'No Site Access', 'No Site Access', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'ISP Construction', 'ISP Construction', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer Not ready', 'Customer Not ready', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer requested delay', 'Customer requested delay', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Missing Equipment', 'Missing Equipment', 0, true);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Service Jeopardy', 'SERVICE_JEOPARDY', true, true, false);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'No Site Access', 'No Site Access', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'ISP Construction', 'ISP Construction', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer Not ready', 'Customer Not ready', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer requested delay', 'Customer requested delay', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Missing Equipment', 'Missing Equipment', 0, true);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Contract Term', 'CONTRACT_TERM', true, true, true);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'MTM', 'MTM', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '12 Month', '12 Month', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '24 Month', '24 Month', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '36 Month', '36 Month', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '72 Month', '72 Month', 50, true);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Carrier', 'CARRIER', true, true, false);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'AT&T', 'AT&T', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Verizon', 'Verizon', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Lumen', 'Lumen', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Brightspeed', 'Brightspeed', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Comcast', 'Comcast', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Spectrum', 'Spectrum', 0, true);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('OSP Construction Interval Estimate', 'OSP_CONS_INTERVAL', true, true, true);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Less than 30 day', 'Less than 30 day', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '30-60 days', '30-60 days', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '0-90 days', '0-90 days', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '90+ Days', '90+ Days', 40, true);



-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Speed', 'SPEED', true, true, false);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '5M', '5M', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '10M', '10M', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '20M', '20M', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '25M', '25M', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '50M', '50M', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '100M', '100M', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '1000M', '1000M', 0, true);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Media Type', 'MEDIA_TYPE', true, true, false);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Fiber', 'Fiber', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Coax', 'Coax', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Copper', 'Copper', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'HFC', 'HFC', 0, true);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Net Status', 'NET_STATUS', true, true, false);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Connected', 'Connected', 0, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Not Connected', 'Not Connected', 0, true);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Product Install Interval', 'PRODUCT_INSTALL_INTERVAL', true, true, true);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '30 Days', '30 Days', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '45 Days', '45 Days', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '60 days', '60 days', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '90 Days', '90 Days', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '120 days', '120 days', 50, true);


-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Additional IP Block', 'ADDITIONAL_IP_BLOCK', true, true, true);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '/30', '/30', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '/29', '/29', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), '/28', '/28', 30, true);




-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Location Status', 'LOCATION_STATUS', true, true, true);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Pending Assignment', 'Pending Assignment', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Change In Assignment', 'Change In Assignment', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'On Hold', 'On Hold', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer Requested Delay', 'Customer Requested Delay', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer Unresponsive', 'Customer Unresponsive', 50, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer Unresponsive', 'Customer Unresponsive', 60, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Pending Customer Provided Circuit Info', 'Pending Customer Provided Circuit Info', 70, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Pending Info From Sales', 'Pending Info From Sales', 80, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Pending Customer Contact', 'Pending Customer Contact', 90, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'PM or Sales Contacted', 'PM or Sales Contacted', 100, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer Contacted', 'Customer Contacted', 110, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'TDG Interview Scheduled', 'TDG Interview Scheduled', 120, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer Gathering Info', 'Customer Gathering Info', 130, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Further Research Required', 'Further Research Required', 140, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Pending Design Approval', 'Pending Design Approval', 150, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'TDG Complete', 'TDG Complete', 160, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Site Survey In Progress', 'Site Survey In Progress', 170, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Provisioning In Progress', 'Provisioning In Progress', 180, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Location Complete', 'Location Complete', 190, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Location Cancelled', 'Location Cancelled', 200, true);


-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Broadband Status', 'BROADBAND_STATUS', true, true, true);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Pending Assignment', 'Pending Assignment', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Change In Assignment', 'Change In Assignment', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'On Hold', 'On Hold', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Engineer Assigned', 'Engineer Assigned', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer Contact In Progress', 'Customer Contact In Progress', 50, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Site Survey In Progress', 'Site Survey In Progress', 60, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Provisioning', 'Provisioning', 70, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Circuit Order Placed', 'Circuit Order Placed', 80, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Circuit Complete', 'Circuit Complete', 90, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Activation Scheduled', 'Activation Scheduled', 100, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Installation Issue', 'Installation Issue', 110, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Activation Complete', 'Activation Complete', 120, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Pending Bill Review', 'Pending Bill Review', 130, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Bill Review Complete', 'Bill Review Complete', 140, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Service Complete', 'Service Complete', 150, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Service Cancelled', 'Service Cancelled', 160, true);


-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('DIA Status', 'DIA_STATUS', true, true, true);



INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Pending Assignment', 'Pending Assignment', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Change In Assignment', 'Change In Assignment', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'On Hold', 'On Hold', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Engineer Assigned', 'Engineer Assigned', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer Contact In Progress', 'Customer Contact In Progress', 50, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Site Survey In Progress', 'Site Survey In Progress', 60, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Provisioning', 'Provisioning', 70, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Circuit Complete', 'Circuit Complete', 80, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Activation Scheduled', 'Activation Scheduled', 90, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Installation Issue', 'Installation Issue', 100, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Activation Complete', 'Activation Complete', 110, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Pending Bill Review', 'Pending Bill Review', 120, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Bill Review Complete', 'Bill Review Complete', 130, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Service Complete', 'Service Complete', 140, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Service Cancelled', 'Service Cancelled', 150, true);














