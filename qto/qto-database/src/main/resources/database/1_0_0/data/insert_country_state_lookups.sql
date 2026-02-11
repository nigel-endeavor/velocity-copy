INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('Country', 'COUNTRY', 1, 1, 1);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'COUNTRY');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'US', 'US', 10, 1),
       (@lookupTypeCode, 'Canada', 'Canada', 20, 1);

#

INSERT INTO lookup_type (lookup_type_descr, lookup_type_code, lookup_type_active, modifiable, sort_strategy)
VALUES ('State Province', 'STATE_PROVINCE', 1, 1, 0);

SET @lookupTypeCode = (SELECT lookup_type_id FROM lookup_type WHERE lookup_type_code = 'STATE_PROVINCE');

INSERT INTO lookup_value (lookup_type_id, lookup_display, lookup_value, sort_seq, lookup_value_active)
VALUES (@lookupTypeCode, 'Alabama', 'AL', 0, 1),
       (@lookupTypeCode, 'Alaska', 'AK', 0, 1),
       (@lookupTypeCode, 'Arizona', 'AZ', 0, 1),
       (@lookupTypeCode, 'Arkansas', 'AR', 0, 1),
       (@lookupTypeCode, 'American Samoa', 'AS', 0, 1),
       (@lookupTypeCode, 'California', 'CA', 0, 1),
       (@lookupTypeCode, 'Colorado', 'CO', 0, 1),
       (@lookupTypeCode, 'Connecticut', 'CT', 0, 1),
       (@lookupTypeCode, 'Delaware', 'DE', 0, 1),
       (@lookupTypeCode, 'District of Columbia', 'DC', 0, 1),
       (@lookupTypeCode, 'Florida', 'FL', 0, 1),
       (@lookupTypeCode, 'Georgia', 'GA', 0, 1),
       (@lookupTypeCode, 'Guam', 'GU', 0, 1),
       (@lookupTypeCode, 'Hawaii', 'HI', 0, 1),
       (@lookupTypeCode, 'Idaho', 'ID', 0, 1),
       (@lookupTypeCode, 'Illinois', 'IL', 0, 1),
       (@lookupTypeCode, 'Indiana', 'IN', 0, 1),
       (@lookupTypeCode, 'Iowa', 'IA', 0, 1),
       (@lookupTypeCode, 'Kansas', 'KS', 0, 1),
       (@lookupTypeCode, 'Kentucky', 'KY', 0, 1),
       (@lookupTypeCode, 'Louisiana', 'LA', 0, 1),
       (@lookupTypeCode, 'Maine', 'ME', 0, 1),
       (@lookupTypeCode, 'Maryland', 'MD', 0, 1),
       (@lookupTypeCode, 'Massachusetts', 'MA', 0, 1),
       (@lookupTypeCode, 'Michigan', 'MI', 0, 1),
       (@lookupTypeCode, 'Minnesota', 'MN', 0, 1),
       (@lookupTypeCode, 'Mississippi', 'MS', 0, 1),
       (@lookupTypeCode, 'Missouri', 'MO', 0, 1),
       (@lookupTypeCode, 'Montana', 'MT', 0, 1),
       (@lookupTypeCode, 'Nebraska', 'NE', 0, 1),
       (@lookupTypeCode, 'Nevada', 'NV', 0, 1),
       (@lookupTypeCode, 'New Hampshire', 'NH', 0, 1),
       (@lookupTypeCode, 'New Jersey', 'NJ', 0, 1),
       (@lookupTypeCode, 'New Mexico', 'NM', 0, 1),
       (@lookupTypeCode, 'New York', 'NY', 0, 1),
       (@lookupTypeCode, 'North Carolina', 'NC', 0, 1),
       (@lookupTypeCode, 'North Dakota', 'ND', 0, 1),
       (@lookupTypeCode, 'Northern Mariana Islands', 'MP', 0, 1),
       (@lookupTypeCode, 'Ohio', 'OH', 0, 1),
       (@lookupTypeCode, 'Oklahoma', 'OK', 0, 1),
       (@lookupTypeCode, 'Oregon', 'OR', 0, 1),
       (@lookupTypeCode, 'Pennsylvania', 'PA', 0, 1),
       (@lookupTypeCode, 'Puerto Rico', 'PR', 0, 1),
       (@lookupTypeCode, 'Rhode Island', 'RI', 0, 1),
       (@lookupTypeCode, 'South Carolina', 'SC', 0, 1),
       (@lookupTypeCode, 'South Dakota', 'SD', 0, 1),
       (@lookupTypeCode, 'Tennessee', 'TN', 0, 1),
       (@lookupTypeCode, 'Texas', 'TX', 0, 1),
       (@lookupTypeCode, 'Trust Territories', 'TT', 0, 1),
       (@lookupTypeCode, 'Utah', 'UT', 0, 1),
       (@lookupTypeCode, 'Vermont', 'VT', 0, 1),
       (@lookupTypeCode, 'Virginia', 'VA', 0, 1),
       (@lookupTypeCode, 'Virgin Islands', 'VI', 0, 1),
       (@lookupTypeCode, 'Washington', 'WA', 0, 1),
       (@lookupTypeCode, 'West Virginia', 'WV', 0, 1),
       (@lookupTypeCode, 'Wisconsin', 'WI', 0, 1),
       (@lookupTypeCode, 'Wyoming', 'WY', 0, 1),
       (@lookupTypeCode, 'Newfoundland and Labrador', 'NL', 0, 1),
       (@lookupTypeCode, 'Prince Edward Island', 'PE', 0, 1),
       (@lookupTypeCode, 'Nova Scotia', 'NS', 0, 1),
       (@lookupTypeCode, 'New Brunswick', 'NB', 0, 1),
       (@lookupTypeCode, 'Quebec', 'QC', 0, 1),
       (@lookupTypeCode, 'Ontario', 'ON', 0, 1),
       (@lookupTypeCode, 'Manitoba', 'MB', 0, 1),
       (@lookupTypeCode, 'Saskatchewan', 'SK', 0, 1),
       (@lookupTypeCode, 'Alberta', 'AB', 0, 1),
       (@lookupTypeCode, 'British Columbia', 'BC', 0, 1),
       (@lookupTypeCode, 'Yukon', 'YT', 0, 1),
       (@lookupTypeCode, 'Northwest Territories', 'NT', 0, 1),
       (@lookupTypeCode, 'Nunavut', 'NU', 0, 1);
