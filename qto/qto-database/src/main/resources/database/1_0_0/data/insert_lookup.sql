INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Order Jeopardy', 'ORDER_JEOPARDY', 1, 1, 1);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Customer Unresponsive', 'Customer Unresponsive', 0, 1);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Location Jeopardy', 'LOCATION_JEOPARDY', 1, 1, 0);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'LOCATION_JEOPARDY');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'No Site Access', 'No Site Access', 0, 1),
       (@lookupTypeCode, 'ISP Construction', 'ISP Construction', 0, 1),
       (@lookupTypeCode, 'Customer Not ready', 'Customer Not ready', 0, 1),
       (@lookupTypeCode, 'Customer requested delay', 'Customer requested delay', 0, 1),
       (@lookupTypeCode, 'Missing Equipment', 'Missing Equipment', 0, 1);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Service Jeopardy', 'SERVICE_JEOPARDY', 1, 1, 0);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SERVICE_JEOPARDY');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'No Site Access', 'No Site Access', 0, 1),
       (@lookupTypeCode, 'ISP Construction', 'ISP Construction', 0, 1),
       (@lookupTypeCode, 'Customer Not ready', 'Customer Not ready', 0, 1),
       (@lookupTypeCode, 'Customer requested delay', 'Customer requested delay', 0, 1),
       (@lookupTypeCode, 'Missing Equipment', 'Missing Equipment', 0, 1);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Contract Term', 'CONTRACT_TERM', 1, 1, 1);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CONTRACT_TERM');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'MTM', 'MTM', 10, 1),
       (@lookupTypeCode, '12 Month', '12 Month', 20, 1),
       (@lookupTypeCode, '24 Month', '24 Month', 30, 1),
       (@lookupTypeCode, '36 Month', '36 Month', 40, 1),
       (@lookupTypeCode, '72 Month', '72 Month', 50, 1);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Carrier', 'CARRIER', 1, 1, 0);

SET
@lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'CARRIER');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'AT&T', 'AT&T', 0, 1),
       (@lookupTypeCode, 'Verizon', 'Verizon', 0, 1),
       (@lookupTypeCode, 'Lumen', 'Lumen', 0, 1),
       (@lookupTypeCode, 'Brightspeed', 'Brightspeed', 0, 1),
       (@lookupTypeCode, 'Comcast', 'Comcast', 0, 1),
       (@lookupTypeCode, 'Spectrum', 'Spectrum', 0, 1);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('OSP Construction Interval Estimate', 'OSP_CONS_INTERVAL', 1, 1, 1);

SET
@lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'OSP_CONS_INTERVAL');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Less than 30 day', 'Less than 30 day', 10, 1),
       (@lookupTypeCode, '30-60 days', '30-60 days', 20, 1),
       (@lookupTypeCode, '0-90 days', '0-90 days', 30, 1),
       (@lookupTypeCode, '90+ Days', '90+ Days', 40, 1);



-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Speed', 'SPEED', 1, 1, 0);

SET
@lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, '5M', '5M', 0, 1),
       (@lookupTypeCode, '10M', '10M', 0, 1),
       (@lookupTypeCode, '20M', '20M', 0, 1),
       (@lookupTypeCode, '25M', '25M', 0, 1),
       (@lookupTypeCode, '50M', '50M', 0, 1),
       (@lookupTypeCode, '100M', '100M', 0, 1),
       (@lookupTypeCode, '1000M', '1000M', 0, 1);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Media Type', 'MEDIA_TYPE', 1, 1, 0);

SET
@lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'MEDIA_TYPE');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Fiber', 'Fiber', 0, 1),
       (@lookupTypeCode, 'Coax', 'Coax', 0, 1),
       (@lookupTypeCode, 'Copper', 'Copper', 0, 1),
       (@lookupTypeCode, 'HFC', 'HFC', 0, 1);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Net Status', 'NET_STATUS', 1, 1, 0);

SET
@lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'NET_STATUS');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Connected', 'Connected', 0, 1),
       (@lookupTypeCode, 'Not Connected', 'Not Connected', 0, 1);

-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Product Install Interval', 'PRODUCT_INSTALL_INTERVAL', 1, 1, 1);

SET
@lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'PRODUCT_INSTALL_INTERVAL');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, '30 Days', '30 Days', 10, 1),
       (@lookupTypeCode, '45 Days', '45 Days', 20, 1),
       (@lookupTypeCode, '60 days', '60 days', 30, 1),
       (@lookupTypeCode, '90 Days', '90 Days', 40, 1),
       (@lookupTypeCode, '120 days', '120 days', 50, 1);


-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Additional IP Block', 'ADDITIONAL_IP_BLOCK', 1, 1, 1);

SET
@lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ADDITIONAL_IP_BLOCK');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, '/30', '/30', 10, 1),
       (@lookupTypeCode, '/29', '/29', 20, 1),
       (@lookupTypeCode, '/28', '/28', 30, 1);




-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Location Status', 'LOCATION_STATUS', 1, 1, 1);

SET
@lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'LOCATION_STATUS');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Pending Assignment', 'Pending Assignment', 10, 1),
       (@lookupTypeCode, 'Change In Assignment', 'Change In Assignment', 20, 1),
       (@lookupTypeCode, 'On Hold', 'On Hold', 30, 1),
       (@lookupTypeCode, 'Customer Requested Delay', 'Customer Requested Delay', 40, 1),
       (@lookupTypeCode, 'Customer Unresponsive', 'Customer Unresponsive', 50, 1),
       (@lookupTypeCode, 'Customer Unresponsive', 'Customer Unresponsive', 60, 1),
       (@lookupTypeCode, 'Pending Customer Provided Circuit Info', 'Pending Customer Provided Circuit Info', 70, 1),
       (@lookupTypeCode, 'Pending Info From Sales', 'Pending Info From Sales', 80, 1),
       (@lookupTypeCode, 'Pending Customer Contact', 'Pending Customer Contact', 90, 1),
       (@lookupTypeCode, 'PM or Sales Contacted', 'PM or Sales Contacted', 100, 1),
       (@lookupTypeCode, 'Customer Contacted', 'Customer Contacted', 110, 1),
       (@lookupTypeCode, 'TDG Interview Scheduled', 'TDG Interview Scheduled', 120, 1),
       (@lookupTypeCode, 'Customer Gathering Info', 'Customer Gathering Info', 130, 1),
       (@lookupTypeCode, 'Further Research Required', 'Further Research Required', 140, 1),
       (@lookupTypeCode, 'Pending Design Approval', 'Pending Design Approval', 150, 1),
       (@lookupTypeCode, 'TDG Complete', 'TDG Complete', 160, 1),
       (@lookupTypeCode, 'Site Survey In Progress', 'Site Survey In Progress', 170, 1),
       (@lookupTypeCode, 'Provisioning In Progress', 'Provisioning In Progress', 180, 1),
       (@lookupTypeCode, 'Location Complete', 'Location Complete', 190, 1),
       (@lookupTypeCode, 'Location Cancelled', 'Location Cancelled', 200, 1);


-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Broadband Status', 'BROADBAND_STATUS', 1, 1, 1);

SET
@lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'BROADBAND_STATUS');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Pending Assignment', 'Pending Assignment', 10, 1),
       (@lookupTypeCode, 'Change In Assignment', 'Change In Assignment', 20, 1),
       (@lookupTypeCode, 'On Hold', 'On Hold', 30, 1),
       (@lookupTypeCode, 'Engineer Assigned', 'Engineer Assigned', 40, 1),
       (@lookupTypeCode, 'Customer Contact In Progress', 'Customer Contact In Progress', 50, 1),
       (@lookupTypeCode, 'Site Survey In Progress', 'Site Survey In Progress', 60, 1),
       (@lookupTypeCode, 'Provisioning', 'Provisioning', 70, 1),
       (@lookupTypeCode, 'Circuit Order Placed', 'Circuit Order Placed', 80, 1),
       (@lookupTypeCode, 'Circuit Complete', 'Circuit Complete', 90, 1),
       (@lookupTypeCode, 'Activation Scheduled', 'Activation Scheduled', 100, 1),
       (@lookupTypeCode, 'Installation Issue', 'Installation Issue', 110, 1),
       (@lookupTypeCode, 'Activation Complete', 'Activation Complete', 120, 1),
       (@lookupTypeCode, 'Pending Bill Review', 'Pending Bill Review', 130, 1),
       (@lookupTypeCode, 'Bill Review Complete', 'Bill Review Complete', 140, 1),
       (@lookupTypeCode, 'Service Complete', 'Service Complete', 150, 1),
       (@lookupTypeCode, 'Service Cancelled', 'Service Cancelled', 160, 1);


-- ##############################
INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('DIA Status', 'DIA_STATUS', 1, 1, 1);

SET
@lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'DIA_STATUS');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Pending Assignment', 'Pending Assignment', 10, 1),
       (@lookupTypeCode, 'Change In Assignment', 'Change In Assignment', 20, 1),
       (@lookupTypeCode, 'On Hold', 'On Hold', 30, 1),
       (@lookupTypeCode, 'Engineer Assigned', 'Engineer Assigned', 40, 1),
       (@lookupTypeCode, 'Customer Contact In Progress', 'Customer Contact In Progress', 50, 1),
       (@lookupTypeCode, 'Site Survey In Progress', 'Site Survey In Progress', 60, 1),
       (@lookupTypeCode, 'Provisioning', 'Provisioning', 70, 1),
       (@lookupTypeCode, 'Circuit Complete', 'Circuit Complete', 80, 1),
       (@lookupTypeCode, 'Activation Scheduled', 'Activation Scheduled', 90, 1),
       (@lookupTypeCode, 'Installation Issue', 'Installation Issue', 100, 1),
       (@lookupTypeCode, 'Activation Complete', 'Activation Complete', 110, 1),
       (@lookupTypeCode, 'Pending Bill Review', 'Pending Bill Review', 120, 1),
       (@lookupTypeCode, 'Bill Review Complete', 'Bill Review Complete', 130, 1),
       (@lookupTypeCode, 'Service Complete', 'Service Complete', 140, 1),
       (@lookupTypeCode, 'Service Cancelled', 'Service Cancelled', 150, 1);














