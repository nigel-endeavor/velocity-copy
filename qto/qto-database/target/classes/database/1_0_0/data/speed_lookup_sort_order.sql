# get lookup type id for SPEED lookup type
SET @lookupTypeId = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'SPEED');

# set lookup_type.sort_strategy to 1
UPDATE lookup_type SET sort_strategy = 1 WHERE lookup_type_id = @lookupTypeId;

# delete old values from tenant_lookup_value and lookup_value
DELETE FROM tenant_lookup_value WHERE lookup_value_id in (SELECT lookup_value_id FROM lookup_value WHERE lookup_type_id = @lookupTypeId);
DELETE FROM lookup_value WHERE lookup_type_id = @lookupTypeId;

# insert new values into lookup_value
INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeId, '384k', '384k' , 10, 1),
       (@lookupTypeId, '500k', '500k' , 20, 1),
       (@lookupTypeId, '512k', '512k' , 30, 1),
       (@lookupTypeId, '768k', '768k' , 40, 1),
       (@lookupTypeId, '1M', '1M' , 50, 1),
       (@lookupTypeId, '1.5M', '1.5M' , 60, 1),
       (@lookupTypeId, '2M', '2M' , 70, 1),
       (@lookupTypeId, '3M', '3M' , 80, 1),
       (@lookupTypeId, '4M', '4M' , 90, 1),
       (@lookupTypeId, '5M', '5M' , 100, 1),
       (@lookupTypeId, '6M', '6M' , 110, 1),
       (@lookupTypeId, '7M', '7M' , 120, 1),
       (@lookupTypeId, '8M', '8M' , 130, 1),
       (@lookupTypeId, '9M', '9M' , 140, 1),
       (@lookupTypeId, '10M', '10M' , 150, 1),
       (@lookupTypeId, '11M', '11M' , 160, 1),
       (@lookupTypeId, '12M', '12M' , 170, 1),
       (@lookupTypeId, '13M', '13M' , 180, 1),
       (@lookupTypeId, '14M', '14M' , 190, 1),
       (@lookupTypeId, '15M', '15M' , 200, 1),
       (@lookupTypeId, '16M', '16M' , 210, 1),
       (@lookupTypeId, '17M', '17M' , 220, 1),
       (@lookupTypeId, '18M', '18M' , 230, 1),
       (@lookupTypeId, '19M', '19M' , 240, 1),
       (@lookupTypeId, '20M', '20M' , 250, 1),
       (@lookupTypeId, '25M', '25M' , 260, 1),
       (@lookupTypeId, '30M', '30M' , 270, 1),
       (@lookupTypeId, '35M', '35M' , 280, 1),
       (@lookupTypeId, '40M', '40M' , 290, 1),
       (@lookupTypeId, '45M', '45M' , 300, 1),
       (@lookupTypeId, '50M', '50M' , 310, 1),
       (@lookupTypeId, '60M', '60M' , 320, 1),
       (@lookupTypeId, '70M', '70M' , 330, 1),
       (@lookupTypeId, '80M', '80M' , 340, 1),
       (@lookupTypeId, '90M', '90M' , 350, 1),
       (@lookupTypeId, '100M', '100M' , 360, 1),
       (@lookupTypeId, '115M', '115M' , 370, 1),
       (@lookupTypeId, '120M', '120M' , 380, 1),
       (@lookupTypeId, '125M', '125M' , 390, 1),
       (@lookupTypeId, '150M', '150M' , 400, 1),
       (@lookupTypeId, '200M', '200M' , 410, 1),
       (@lookupTypeId, '250M', '250M' , 420, 1),
       (@lookupTypeId, '300M', '300M' , 430, 1),
       (@lookupTypeId, '350M', '350M' , 440, 1),
       (@lookupTypeId, '400M', '400M' , 450, 1),
       (@lookupTypeId, '450M', '450M' , 460, 1),
       (@lookupTypeId, '500M', '500M' , 470, 1),
       (@lookupTypeId, '600M', '600M' , 480, 1),
       (@lookupTypeId, '700M', '700M' , 490, 1),
       (@lookupTypeId, '800M', '800M' , 500, 1),
       (@lookupTypeId, '880M', '880M' , 510, 1),
       (@lookupTypeId, '900M', '900M' , 520, 1),
       (@lookupTypeId, '940M', '940M' , 530, 1),
       (@lookupTypeId, '1000M', '1000M' , 540, 1),
       (@lookupTypeId, '1G', '1G' , 550, 1),
       (@lookupTypeId, '2G', '2G' , 560, 1),
       (@lookupTypeId, '3G', '3G' , 570, 1),
       (@lookupTypeId, '4G', '4G' , 580, 1),
       (@lookupTypeId, '5G', '5G' , 590, 1),
       (@lookupTypeId, '6G', '6G' , 600, 1),
       (@lookupTypeId, '7G', '7G' , 610, 1),
       (@lookupTypeId, '8G', '8G' , 620, 1),
       (@lookupTypeId, '9G', '9G' , 630, 1),
       (@lookupTypeId, '10G', '10G' , 640, 1);

# insert new values into tenant_lookup_value
SET @tenantId = (SELECT tenant_id FROM v_tenant WHERE name ='QTO First Tenant');

INSERT INTO tenant_lookup_value
    SELECT lookup_value_id, @tenantId
    FROM lookup_value
    WHERE lookup_type_id = @lookupTypeId;

SET @tenantId = (SELECT tenant_id FROM v_tenant WHERE name ='Endeavor');

INSERT INTO tenant_lookup_value
    SELECT lookup_value_id, @tenantId
    FROM lookup_value
    WHERE lookup_type_id = @lookupTypeId;
