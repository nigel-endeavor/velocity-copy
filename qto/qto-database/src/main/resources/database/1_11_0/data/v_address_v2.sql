CREATE OR REPLACE VIEW v_address AS
SELECT
    client_location_id,
    address_1,
    address_2,
    city,
    state_province,
    postal_code,
    country,
    type,
    company_id,
    master_customer_id,
    tenant_id,
    UUID() as id, -- dummy id to keep JPA happy
    0 as version  -- dummy version so we can extend StandardVersionedBaseEntity
FROM (
         SELECT DISTINCT
             l.client_location_id,
             l.address_1,
             l.address_2,
             l.city,
             l.state_province,
             l.postal_code,
             l.country,
             'Location' as type,
             o.company_id,
             l.master_customer_id,
             l.tenant_id
         FROM location l
                  JOIN orders o ON l.order_id = o.order_id
         WHERE l.address_1 IS NOT NULL

         UNION ALL

         SELECT DISTINCT
             null as client_location_id,
             s.address_1,
             s.address_2,
             s.city,
             s.state_province,
             s.postal_code,
             s.country,
             'Billing' as type,
             o.company_id,
             s.master_customer_id,
             s.tenant_id
         FROM service s
                  JOIN orders o ON s.order_id = o.order_id
         WHERE s.address_1 IS NOT NULL

         UNION ALL

         SELECT DISTINCT
             null as client_location_id,
             es.z_address_1,
             es.z_address_2,
             es.z_city,
             es.z_state_province,
             es.z_postal_code,
             es.z_country,
             'Z Location' as type,
             o.company_id,
             s.master_customer_id,
             s.tenant_id
         FROM ethernet_service es
                  JOIN service s ON es.service_id = s.service_id
                  JOIN orders o ON s.order_id = o.order_id
         WHERE es.z_address_1 IS NOT NULL
     ) AS distinct_addresses;
