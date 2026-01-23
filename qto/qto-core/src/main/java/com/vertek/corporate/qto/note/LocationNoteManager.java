package com.vertek.corporate.qto.note;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.subject.Subject;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Stateless
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
        if (org.apache.shiro.SecurityUtils.getSubject().isPermitted(Permissions.ADMIN)
                || org.apache.shiro.SecurityUtils.getSubject().isPermitted(Permissions.ORDER_WRITE_TERMINAL)
                || org.apache.shiro.SecurityUtils.getSubject().isPermitted(Permissions.INVENTORY_WRITE)) {
            if (org.apache.shiro.SecurityUtils.getSubject().isPermitted(Permissions.ADMIN)) {
                editable = true;
            } else if (org.apache.shiro.SecurityUtils.getSubject().isPermitted(Permissions.ORDER_WRITE_TERMINAL)
                    && (!locationManager.retrieve(note.getLocationId()).isCurrentInventory())) {
                editable = true;
            } else if (org.apache.shiro.SecurityUtils.getSubject().isPermitted(Permissions.INVENTORY_WRITE)
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
