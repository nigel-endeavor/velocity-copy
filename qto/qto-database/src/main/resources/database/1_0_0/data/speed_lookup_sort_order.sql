# get lookup type id for SPEED lookup type

# set lookup_type.sort_strategy to 1
UPDATE lookup_type SET sort_strategy = true WHERE lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED');

# delete old values from tenant_lookup_value and lookup_value
DELETE FROM tenant_lookup_value WHERE lookup_value_id in (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'));
DELETE FROM lookup_value WHERE lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED');

# insert new values into lookup_value
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '384k', '384k' , 10, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '500k', '500k' , 20, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '512k', '512k' , 30, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '768k', '768k' , 40, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '1M', '1M' , 50, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '1.5M', '1.5M' , 60, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '2M', '2M' , 70, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '3M', '3M' , 80, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '4M', '4M' , 90, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '5M', '5M' , 100, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '6M', '6M' , 110, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '7M', '7M' , 120, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '8M', '8M' , 130, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '9M', '9M' , 140, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '10M', '10M' , 150, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '11M', '11M' , 160, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '12M', '12M' , 170, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '13M', '13M' , 180, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '14M', '14M' , 190, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '15M', '15M' , 200, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '16M', '16M' , 210, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '17M', '17M' , 220, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '18M', '18M' , 230, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '19M', '19M' , 240, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '20M', '20M' , 250, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '25M', '25M' , 260, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '30M', '30M' , 270, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '35M', '35M' , 280, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '40M', '40M' , 290, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '45M', '45M' , 300, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '50M', '50M' , 310, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '60M', '60M' , 320, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '70M', '70M' , 330, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '80M', '80M' , 340, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '90M', '90M' , 350, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '100M', '100M' , 360, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '115M', '115M' , 370, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '120M', '120M' , 380, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '125M', '125M' , 390, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '150M', '150M' , 400, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '200M', '200M' , 410, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '250M', '250M' , 420, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '300M', '300M' , 430, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '350M', '350M' , 440, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '400M', '400M' , 450, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '450M', '450M' , 460, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '500M', '500M' , 470, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '600M', '600M' , 480, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '700M', '700M' , 490, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '800M', '800M' , 500, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '880M', '880M' , 510, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '900M', '900M' , 520, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '940M', '940M' , 530, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '1000M', '1000M' , 540, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '1G', '1G' , 550, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '2G', '2G' , 560, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '3G', '3G' , 570, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '4G', '4G' , 580, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '5G', '5G' , 590, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '6G', '6G' , 600, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '7G', '7G' , 610, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '8G', '8G' , 620, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '9G', '9G' , 630, true),
       ((SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED'), '10G', '10G' , 640, true);

# insert new values into tenant_lookup_value

INSERT INTO tenant_lookup_value
    SELECT lookup_value_id, (SELECT tenant_id FROM v_tenant WHERE name ='QTO First Tenant')
    FROM lookup_value
    WHERE lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED');



INSERT INTO tenant_lookup_value
    SELECT lookup_value_id, (SELECT tenant_id FROM v_tenant WHERE name ='QTO First Tenant')
    FROM lookup_value
    WHERE lookup_type_id = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED');
