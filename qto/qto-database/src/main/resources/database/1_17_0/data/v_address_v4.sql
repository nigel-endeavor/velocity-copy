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
             case when l.address_2 = '' then null else l.address_2 end as address_2,
             case when l.city = '' then null else l.city end as city,
             case when l.state_province = '' then null else l.state_province end as state_province,
             case when l.postal_code  = '' then null else l.postal_code end as postal_code,
             case when l.country = '' then null else l.country end as country,
             'Location' as type,
             o.company_id,
             l.master_customer_id,
             l.tenant_id
         FROM location l
                  JOIN orders o ON l.order_id = o.order_id
         WHERE l.address_1 IS NOT NULL and (l.current_inventory = true or
              (l.current_inventory = false and l.location_status not in ('Location Complete', 'Location Cancelled', 'Change In Assignment')))

         UNION ALL

         SELECT DISTINCT
             null as client_location_id,
             s.address_1,
             case when s.address_2 = '' then null else s.address_2 end as address_2,
             case when s.city = '' then null else s.city end as city,
             case when s.state_province = '' then null else s.state_province end as state_province,
             case when s.postal_code = '' then null else s.postal_code end as postal_code,
             case when s.country = '' then null else s.country end as country,
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
             case when es.z_address_2 = '' then null else es.z_address_2 end as z_address_2,
             case when es.z_city = '' then null else es.z_city end as z_city,
             case when es.z_state_province = '' then null else es.z_state_province end as z_state_province,
             case when es.z_postal_code = '' then null else es.z_postal_code end as z_postal_code,
             case when es.z_country = '' then null else es.z_country end as z_country,
             'Z Location' as type,
             o.company_id,
             s.master_customer_id,
             s.tenant_id
         FROM ethernet_service es
                  JOIN service s ON es.service_id = s.service_id
                  JOIN orders o ON s.order_id = o.order_id
         WHERE es.z_address_1 IS NOT NULL
     ) AS distinct_addresses;
