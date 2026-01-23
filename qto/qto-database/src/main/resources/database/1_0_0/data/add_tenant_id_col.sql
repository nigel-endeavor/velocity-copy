# jeop_instance
alter table jeop_instance add tenant_id int not null;

update jeop_instance ji
    left join location_jeop_instance lji on ji.jeop_instance_id = lji.jeop_instance_id
    left join location l on lji.location_id = l.location_id
    left join service_jeop_instance sji on ji.jeop_instance_id = sji.jeop_instance_id
    left join service s on sji.service_id = s.service_id
    left join order_jeop_instance oji on ji.jeop_instance_id = oji.jeop_instance_id
    left join orders o on oji.order_id = o.order_id
set ji.tenant_id = COALESCE(l.tenant_id, s.tenant_id, o.tenant_id);

# note
alter table note add tenant_id int not null;

update note n
    left join location_note ln on n.note_id = ln.note_id
    left join location l on ln.location_id = l.location_id
    left join service_note sn on n.note_id = sn.note_id
    left join service s on sn.service_id = s.service_id
    left join order_note o on n.note_id = o.note_id
    left join orders o2 on o.order_id = o2.order_id
set n.tenant_id = COALESCE(l.tenant_id, s.tenant_id, o2.tenant_id);

# file_attachment
alter table file_attachment add tenant_id int not null;

update file_attachment fa
    left join location_file_attachment lfa on fa.file_attachment_id = lfa.file_attachment_id
    left join location l on lfa.location_id = l.location_id
    left join service_file_attachment sfa on fa.file_attachment_id = sfa.file_attachment_id
    left join service s on sfa.service_id = s.service_id
    left join order_file_attachment ofa on fa.file_attachment_id = ofa.file_attachment_id
    left join orders o on ofa.order_id = o.order_id
set fa.tenant_id = COALESCE(l.tenant_id, s.tenant_id, o.tenant_id);

# address
alter table address add tenant_id int not null;

update address a
    join company c on a.address_id = c.address_id
set a.tenant_id = c.tenant_id;

# milestone_instance
alter table milestone_instance add tenant_id int not null;

update milestone_instance mi
    left join location_milestone_instance lmi on mi.milestone_instance_id = lmi.milestone_instance_id
    left join location l on lmi.location_id = l.location_id
    left join service_milestone_instance smi on mi.milestone_instance_id = smi.milestone_instance_id
    left join service s on smi.service_id = s.service_id
    left join order_milestone_instance omi on mi.milestone_instance_id = omi.milestone_instance_id
    left join orders o on omi.order_id = o.order_id
set mi.tenant_id = COALESCE(l.tenant_id, s.tenant_id, o.tenant_id);

# activation_attempt
alter table activation_attempt add tenant_id int not null;

update activation_attempt aa
    join service s on aa.service_id = s.service_id
set aa.tenant_id = s.tenant_id;

# activation_schedule
alter table activation_schedule add tenant_id int not null;

update activation_schedule a
    join service s on a.service_id = s.service_id
set a.tenant_id = s.tenant_id;

# activation_issue
alter table activation_issue add tenant_id int not null;

update activation_issue ai
    join activation_attempt aa on aa.activation_attempt_id = ai.activation_attempt_id
set ai.tenant_id = aa.tenant_id;

# requirement_template
alter table requirement_template add tenant_id int not null;

update requirement_template rt
    join company c on c.company_id = rt.company_id
set rt.tenant_id = c.tenant_id;

