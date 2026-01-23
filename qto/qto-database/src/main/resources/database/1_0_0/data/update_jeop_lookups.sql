# ORDER_JEOPARDY
SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY');

DELETE FROM lookup_value where lookup_type_id = @lookupTypeCode;

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Access Agreement', 'Access Agreement', 10, 1),
       (@lookupTypeCode, 'Address Validation', 'Address Validation', 20, 1),
       (@lookupTypeCode, 'Cancellation Pending', 'Cancellation Pending', 30, 1),
       (@lookupTypeCode, 'Carrier Other', 'Carrier Other', 40, 1),
       (@lookupTypeCode, 'Circuit Test Failure', 'Circuit Test Failure', 50, 1),
       (@lookupTypeCode, 'Customer Not Ready', 'Customer Not Ready', 60, 1),
       (@lookupTypeCode, 'Customer Other', 'Customer Other', 70, 1),
       (@lookupTypeCode, 'Electrical Power', 'Electrical Power', 80, 1),
       (@lookupTypeCode, 'Equipment Configuration', 'Equipment Configuration', 90, 1),
       (@lookupTypeCode, 'Equipment Defective', 'Equipment Defective', 100, 1),
       (@lookupTypeCode, 'Equipment Missing', 'Equipment Missing', 110, 1),
       (@lookupTypeCode, 'Equipment Other', 'Equipment Other', 120, 1),
       (@lookupTypeCode, 'Facilities Issue', 'Facilities Issue', 130, 1),
       (@lookupTypeCode, 'FOC Date Missed', 'FOC Date Missed', 140, 1),
       (@lookupTypeCode, 'Incomplete Order', 'Incomplete Order', 150, 1),
       (@lookupTypeCode, 'Inside Wiring', 'Inside Wiring', 160, 1),
       (@lookupTypeCode, 'No Access', 'No Access', 170, 1),
       (@lookupTypeCode, 'No Dual 4G', 'No Dual 4G', 180, 1),
       (@lookupTypeCode, 'No provider Available', 'No provider Available', 190, 1),
       (@lookupTypeCode, 'OSP Construction', 'OSP Construction', 200, 1),
       (@lookupTypeCode, 'Poor Wireless Signal', 'Poor Wireless Signal', 210, 1),
       (@lookupTypeCode, 'Service not Installed', 'Service not Installed', 220, 1),
       (@lookupTypeCode, 'Site Construction', 'Site Construction', 230, 1),
       (@lookupTypeCode, 'Site Survey', 'Site Survey', 240, 1),
       (@lookupTypeCode, 'Technician Abilities', 'Technician Abilities', 250, 1),
       (@lookupTypeCode, 'Technician Other', 'Technician Other', 260, 1),
       (@lookupTypeCode, 'Technician Tools', 'Technician Tools', 270, 1);


# LOCATION_JEOPARDY
SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'LOCATION_JEOPARDY');

DELETE FROM lookup_value where lookup_type_id = @lookupTypeCode;

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Access Agreement', 'Access Agreement', 10, 1),
       (@lookupTypeCode, 'Address Validation', 'Address Validation', 20, 1),
       (@lookupTypeCode, 'Cancellation Pending', 'Cancellation Pending', 30, 1),
       (@lookupTypeCode, 'Carrier Other', 'Carrier Other', 40, 1),
       (@lookupTypeCode, 'Circuit Test Failure', 'Circuit Test Failure', 50, 1),
       (@lookupTypeCode, 'Customer Not Ready', 'Customer Not Ready', 60, 1),
       (@lookupTypeCode, 'Customer Other', 'Customer Other', 70, 1),
       (@lookupTypeCode, 'Electrical Power', 'Electrical Power', 80, 1),
       (@lookupTypeCode, 'Equipment Configuration', 'Equipment Configuration', 90, 1),
       (@lookupTypeCode, 'Equipment Defective', 'Equipment Defective', 100, 1),
       (@lookupTypeCode, 'Equipment Missing', 'Equipment Missing', 110, 1),
       (@lookupTypeCode, 'Equipment Other', 'Equipment Other', 120, 1),
       (@lookupTypeCode, 'Facilities Issue', 'Facilities Issue', 130, 1),
       (@lookupTypeCode, 'FOC Date Missed', 'FOC Date Missed', 140, 1),
       (@lookupTypeCode, 'Incomplete Order', 'Incomplete Order', 150, 1),
       (@lookupTypeCode, 'Inside Wiring', 'Inside Wiring', 160, 1),
       (@lookupTypeCode, 'No Access', 'No Access', 170, 1),
       (@lookupTypeCode, 'No Dual 4G', 'No Dual 4G', 180, 1),
       (@lookupTypeCode, 'No provider Available', 'No provider Available', 190, 1),
       (@lookupTypeCode, 'OSP Construction', 'OSP Construction', 200, 1),
       (@lookupTypeCode, 'Poor Wireless Signal', 'Poor Wireless Signal', 210, 1),
       (@lookupTypeCode, 'Service not Installed', 'Service not Installed', 220, 1),
       (@lookupTypeCode, 'Site Construction', 'Site Construction', 230, 1),
       (@lookupTypeCode, 'Site Survey', 'Site Survey', 240, 1),
       (@lookupTypeCode, 'Technician Abilities', 'Technician Abilities', 250, 1),
       (@lookupTypeCode, 'Technician Other', 'Technician Other', 260, 1),
       (@lookupTypeCode, 'Technician Tools', 'Technician Tools', 270, 1);


# SERVICE_JEOPARDY
SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SERVICE_JEOPARDY');

DELETE FROM lookup_value where lookup_type_id = @lookupTypeCode;

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Access Agreement', 'Access Agreement', 10, 1),
       (@lookupTypeCode, 'Address Validation', 'Address Validation', 20, 1),
       (@lookupTypeCode, 'Cancellation Pending', 'Cancellation Pending', 30, 1),
       (@lookupTypeCode, 'Carrier Other', 'Carrier Other', 40, 1),
       (@lookupTypeCode, 'Circuit Test Failure', 'Circuit Test Failure', 50, 1),
       (@lookupTypeCode, 'Customer Not Ready', 'Customer Not Ready', 60, 1),
       (@lookupTypeCode, 'Customer Other', 'Customer Other', 70, 1),
       (@lookupTypeCode, 'Electrical Power', 'Electrical Power', 80, 1),
       (@lookupTypeCode, 'Equipment Configuration', 'Equipment Configuration', 90, 1),
       (@lookupTypeCode, 'Equipment Defective', 'Equipment Defective', 100, 1),
       (@lookupTypeCode, 'Equipment Missing', 'Equipment Missing', 110, 1),
       (@lookupTypeCode, 'Equipment Other', 'Equipment Other', 120, 1),
       (@lookupTypeCode, 'Facilities Issue', 'Facilities Issue', 130, 1),
       (@lookupTypeCode, 'FOC Date Missed', 'FOC Date Missed', 140, 1),
       (@lookupTypeCode, 'Incomplete Order', 'Incomplete Order', 150, 1),
       (@lookupTypeCode, 'Inside Wiring', 'Inside Wiring', 160, 1),
       (@lookupTypeCode, 'No Access', 'No Access', 170, 1),
       (@lookupTypeCode, 'No Dual 4G', 'No Dual 4G', 180, 1),
       (@lookupTypeCode, 'No provider Available', 'No provider Available', 190, 1),
       (@lookupTypeCode, 'OSP Construction', 'OSP Construction', 200, 1),
       (@lookupTypeCode, 'Poor Wireless Signal', 'Poor Wireless Signal', 210, 1),
       (@lookupTypeCode, 'Service not Installed', 'Service not Installed', 220, 1),
       (@lookupTypeCode, 'Site Construction', 'Site Construction', 230, 1),
       (@lookupTypeCode, 'Site Survey', 'Site Survey', 240, 1),
       (@lookupTypeCode, 'Technician Abilities', 'Technician Abilities', 250, 1),
       (@lookupTypeCode, 'Technician Other', 'Technician Other', 260, 1),
       (@lookupTypeCode, 'Technician Tools', 'Technician Tools', 270, 1);

# JEOPARDY_RESPONSIBILITY
SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'JEOPARDY_RESPONSIBILITY');

DELETE FROM lookup_value where lookup_type_id = @lookupTypeCode;

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Carrier', 'Carrier', 10, 1),
       (@lookupTypeCode, 'Client', 'Client', 20, 1),
       (@lookupTypeCode, 'End Customer', 'End Customer', 30, 1),
       (@lookupTypeCode, 'Project Manager', 'Project Manager', 40, 1),
       (@lookupTypeCode, 'Provisioner', 'Provisioner', 50, 1);
