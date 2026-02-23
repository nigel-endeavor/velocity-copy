


DROP VIEW IF EXISTS pbi_iss_location_contacts CASCADE;
create or replace view pbi_iss_location_contacts as
SELECT l.location_id AS 'Location ID',
       c.contact_id AS 'Contact ID',
       first_name as 'First Name',
       last_name as 'Last Name',
       contact_active as Active,
       contact_type as 'Contact Type',
       role as Rold,
       phone as Phone,
       email as Email
FROM location l
  left join location_contact lc on l.location_id = lc.location_id
   left  JOIN contact c ON lc.contact_id = c.contact_id
where l.tenant_id = (select tenant_id from v_tenant where name = 'Endeavor');

DROP VIEW IF EXISTS pbi_iss_location_jeops CASCADE;
create or replace view pbi_iss_location_jeops as
select l.location_id as 'Location ID',
       lji.jeop_instance_id as 'Jeopardy Instance ID',
       ji.jeop_description as 'Jeopardy Description',
       ji.responsibility as 'Responsibility',
       ji.start_date as 'Start Date',
       ji.end_date as 'End Date',
       ji.note as 'Note',
       ji.originator as 'Originator',
       ji.assigned_to as 'Assigned To'
       from location l
  left join location_jeop_instance lji on l.location_id = lji.location_id
left join jeop_instance ji ON lji.jeop_instance_id = ji.jeop_instance_id
where l.tenant_id = (select tenant_id from v_tenant where name = 'Endeavor');

DROP VIEW IF EXISTS pbi_iss_location_notes CASCADE;
create or replace view pbi_iss_location_notes as
select l.location_id as 'Location ID',
       n.note as Note,
       n.created_by as 'Created By',
       n.created_date as 'Created Date',
       n.internal_only as 'Internal Only'
from location l
  left join location_note ln on l.location_id = ln.location_id
left join note n on ln.note_id = n.note_id
where l.tenant_id = (select tenant_id from v_tenant where name = 'Endeavor');









