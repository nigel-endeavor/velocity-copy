package com.endeavorms.velocity.qto.note;

import com.endeavorms.velocity.qto.authentication.Permissions;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerOwnedEntity;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

@Component
public abstract class AbstractNoteManager<T extends Note> extends StandardManager<T> {
    /** DAO for accessing NoteUnionViews. */
    @Inject
    private NoteUnionViewJpaDao noteUnionViewJpaDao;

    @Inject
    protected SubjectManager subjectManager;

    @Inject
    private LocationManager locationManager;

    @Inject
    private ServiceManager serviceManager;

    @Override
    public T create(final T entity) {
        AbstractMasterCustomerOwnedEntity owner = getParentManager().retrieve(getOwnerEntityId(entity));
        entity.setTenantId(owner.getTenantId());
        entity.setMasterCustomerId(owner.getMasterCustomerId());
        entity.setUpdateClient(true);
        T created = super.create(entity);
        String loggedInUser = SecurityUtils.getLoggedInUser();
        if (!SecurityUtils.SCHEDULER.equals(loggedInUser)) {
            created.setEditable(determineEditability(created));
        }
        return created;
    }

    @Override
    public T edit(final T entity) {
        Note existing  = retrieve(entity.getId());
        entity.setCreatedById(existing.getCreatedById());
        AbstractMasterCustomerOwnedEntity owner = getParentManager().retrieve(getOwnerEntityId(entity));
        entity.setTenantId(owner.getTenantId());
        entity.setMasterCustomerId(owner.getMasterCustomerId());
        entity.setUpdateClient(true);
        T edited = super.edit(entity);
        String loggedInUser = SecurityUtils.getLoggedInUser();
        if (!SecurityUtils.SCHEDULER.equals(loggedInUser)) {
            edited.setEditable(determineEditability(edited));
        }
        return edited;
    }

    /**
     * Common method for finding notes by search criteria.
     * @param criteria the criteria to filter by.
     * @param auditNotes whether to include audit notes.
     * @return matching notes.
     */
    public PaginatedResult<NoteUnionView> findBySearchCriteria(final NoteUnionViewSearchCriteria criteria, final boolean auditNotes) {
        PaginatedResult<NoteUnionView> notes = noteUnionViewJpaDao.findBySearchCriteria(criteria, auditNotes);
        //mark each note whether it is editable by the current user based on permissions or ownership
        if (SecurityUtils.hasAuthority(Permissions.ADMIN)
                || SecurityUtils.hasAuthority(Permissions.ORDER_WRITE_TERMINAL)
                || SecurityUtils.hasAuthority(Permissions.INVENTORY_WRITE)) {
            for (NoteUnionView note : notes.getCollection()) {
                if (SecurityUtils.hasAuthority(Permissions.ADMIN)) {
                    note.setEditable(true);
                } else if (SecurityUtils.hasAuthority(Permissions.ORDER_WRITE_TERMINAL)
                    && ((note.getLocationId() != null && note.getServiceId() == null && (!locationManager.retrieve(note.getLocationId()).isCurrentInventory()))
                        || (note.getServiceId() != null && (!serviceManager.retrieve(note.getServiceId()).isCurrentInventory())))) {
                    note.setEditable(true);
                } else if (SecurityUtils.hasAuthority(Permissions.INVENTORY_WRITE)
                        && ((note.getLocationId() != null && note.getServiceId() == null && (locationManager.retrieve(note.getLocationId()).isCurrentInventory()))
                        || (note.getServiceId() != null && (serviceManager.retrieve(note.getServiceId()).isCurrentInventory())))) {
                    note.setEditable(true);
                }
            }
        }
        if (!notes.getCollection().stream().allMatch(NoteUnionView::isEditable)) {
            //can use the logged-in username to find the subject
            String username = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(username);
            //need to use the subject id because the subject's name could be changed
            notes.getCollection().stream().filter(note -> !note.isEditable()).forEach(note -> {
                if (note.getCreatedById() != null && note.getCreatedById().equals(subject.getId())) {
                    note.setEditable(true);
                }
            });
        }

        return notes;
    }

    protected abstract StandardManager<AbstractMasterCustomerOwnedEntity> getParentManager();
    
    protected abstract Long getOwnerEntityId(final T note);

    public abstract boolean determineEditability(final T note);
}
