package com.endeavorms.velocity.qto.note;

import com.endeavorms.velocity.qto.authentication.Permissions;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.subject.Subject;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Component
public class LocationNoteManager extends AbstractNoteManager<LocationNote> {

    /**
     * Persistence tier for LocationNote.
     */
    @Inject
    private LocationNoteJpaDao dao;

    @Inject
    private LocationManager locationManager;

    @Override
    protected LocationNoteJpaDao getDao() {
        return dao;
    }

    @Override
    public boolean determineEditability(final LocationNote note) {
        boolean editable = false;
        if (SecurityUtils.hasAuthority(Permissions.ADMIN)
                || SecurityUtils.hasAuthority(Permissions.ORDER_WRITE_TERMINAL)
                || SecurityUtils.hasAuthority(Permissions.INVENTORY_WRITE)) {
            if (SecurityUtils.hasAuthority(Permissions.ADMIN)) {
                editable = true;
            } else if (SecurityUtils.hasAuthority(Permissions.ORDER_WRITE_TERMINAL)
                    && (!locationManager.retrieve(note.getLocationId()).isCurrentInventory())) {
                editable = true;
            } else if (SecurityUtils.hasAuthority(Permissions.INVENTORY_WRITE)
                    && (locationManager.retrieve(note.getLocationId()).isCurrentInventory())) {
                editable = true;
            }
        }
        if (!editable) {
            //can use the logged-in username to find the subject
            String username = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(username);
            //need to use the subject id because the subject's name could be changed
            if (note.getCreatedById() != null && note.getCreatedById().equals(subject.getId())) {
                editable = true;
            }
        }
        return editable;
    }

    public LocationNote create(final Long locationId, final String noteBody, final String category) {
        LocationNote note = new LocationNote();
        note.setLocationId(locationId);
        note.setNote(noteBody);
        note.setCategory(category);
        String username = SecurityUtils.getLoggedInUser();
        Subject subject = subjectManager.findByUsername(username);
        note.setCreatedBy(subject == null ? username : subject.getDisplayName());
        if (subject != null) {
            note.setCreatedById(subject.getId());
        }
        note.setCreatedDate(new Date());
        Location location = locationManager.retrieve(locationId);
        note.setTenantId(location.getTenantId());
        note.setMasterCustomerId(location.getMasterCustomerId());
        return super.create(note);
    }

        /**
     * Find all notes for a location.
     *
     * @param locationId the location id
     * @return the list of notes
     */
    public List<LocationNote> findByLocationId(final Long locationId) {
        return dao.findByLocationId(locationId);
    }

    @Override
    protected StandardManager getParentManager() {
        return locationManager;
    }

    @Override
    protected Long getOwnerEntityId(final LocationNote note) {
        return note.getLocationId();
    }
}
