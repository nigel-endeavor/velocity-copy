# ORDER_JEOPARDY

DELETE FROM lookup_value where lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Access Agreement', 'Access Agreement', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Address Validation', 'Address Validation', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Cancellation Pending', 'Cancellation Pending', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Carrier Other', 'Carrier Other', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Circuit Test Failure', 'Circuit Test Failure', 50, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer Not Ready', 'Customer Not Ready', 60, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer Other', 'Customer Other', 70, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Electrical Power', 'Electrical Power', 80, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Equipment Configuration', 'Equipment Configuration', 90, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Equipment Defective', 'Equipment Defective', 100, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Equipment Missing', 'Equipment Missing', 110, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Equipment Other', 'Equipment Other', 120, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Facilities Issue', 'Facilities Issue', 130, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'FOC Date Missed', 'FOC Date Missed', 140, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Incomplete Order', 'Incomplete Order', 150, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Inside Wiring', 'Inside Wiring', 160, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'No Access', 'No Access', 170, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'No Dual 4G', 'No Dual 4G', 180, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'No provider Available', 'No provider Available', 190, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'OSP Construction', 'OSP Construction', 200, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Poor Wireless Signal', 'Poor Wireless Signal', 210, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Service not Installed', 'Service not Installed', 220, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Site Construction', 'Site Construction', 230, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Site Survey', 'Site Survey', 240, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Technician Abilities', 'Technician Abilities', 250, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Technician Other', 'Technician Other', 260, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Technician Tools', 'Technician Tools', 270, true);


# LOCATION_JEOPARDY


DELETE FROM lookup_value where lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Access Agreement', 'Access Agreement', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Address Validation', 'Address Validation', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Cancellation Pending', 'Cancellation Pending', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Carrier Other', 'Carrier Other', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Circuit Test Failure', 'Circuit Test Failure', 50, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer Not Ready', 'Customer Not Ready', 60, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer Other', 'Customer Other', 70, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Electrical Power', 'Electrical Power', 80, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Equipment Configuration', 'Equipment Configuration', 90, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Equipment Defective', 'Equipment Defective', 100, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Equipment Missing', 'Equipment Missing', 110, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Equipment Other', 'Equipment Other', 120, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Facilities Issue', 'Facilities Issue', 130, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'FOC Date Missed', 'FOC Date Missed', 140, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Incomplete Order', 'Incomplete Order', 150, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Inside Wiring', 'Inside Wiring', 160, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'No Access', 'No Access', 170, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'No Dual 4G', 'No Dual 4G', 180, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'No provider Available', 'No provider Available', 190, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'OSP Construction', 'OSP Construction', 200, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Poor Wireless Signal', 'Poor Wireless Signal', 210, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Service not Installed', 'Service not Installed', 220, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Site Construction', 'Site Construction', 230, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Site Survey', 'Site Survey', 240, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Technician Abilities', 'Technician Abilities', 250, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Technician Other', 'Technician Other', 260, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Technician Tools', 'Technician Tools', 270, true);


# SERVICE_JEOPARDY


DELETE FROM lookup_value where lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Access Agreement', 'Access Agreement', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Address Validation', 'Address Validation', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Cancellation Pending', 'Cancellation Pending', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Carrier Other', 'Carrier Other', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Circuit Test Failure', 'Circuit Test Failure', 50, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer Not Ready', 'Customer Not Ready', 60, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Customer Other', 'Customer Other', 70, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Electrical Power', 'Electrical Power', 80, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Equipment Configuration', 'Equipment Configuration', 90, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Equipment Defective', 'Equipment Defective', 100, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Equipment Missing', 'Equipment Missing', 110, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Equipment Other', 'Equipment Other', 120, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Facilities Issue', 'Facilities Issue', 130, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'FOC Date Missed', 'FOC Date Missed', 140, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Incomplete Order', 'Incomplete Order', 150, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Inside Wiring', 'Inside Wiring', 160, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'No Access', 'No Access', 170, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'No Dual 4G', 'No Dual 4G', 180, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'No provider Available', 'No provider Available', 190, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'OSP Construction', 'OSP Construction', 200, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Poor Wireless Signal', 'Poor Wireless Signal', 210, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Service not Installed', 'Service not Installed', 220, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Site Construction', 'Site Construction', 230, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Site Survey', 'Site Survey', 240, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Technician Abilities', 'Technician Abilities', 250, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Technician Other', 'Technician Other', 260, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Technician Tools', 'Technician Tools', 270, true);

# JEOPARDY_RESPONSIBILITY


DELETE FROM lookup_value where lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Carrier', 'Carrier', 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Client', 'Client', 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'End Customer', 'End Customer', 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Project Manager', 'Project Manager', 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'ORDER_JEOPARDY'), 'Provisioner', 'Provisioner', 50, true);
