package com.vertek.corporate.qto.location.jms;

import com.vertek.corporate.qto.location.Location;

import java.io.Serializable;

public class LocationMessage implements Serializable {

    private Long locationId;

    private String messageType;

    private Location existingLocation;

    private Location newLocation;

    public LocationMessage(final Long locationId, final Location existingLocation,
                           final Location newLocation, final String messageType) {
        this.locationId = locationId;
        this.messageType = messageType;
        this.existingLocation = existingLocation;
        this.newLocation = newLocation;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(final String messageType) {
        this.messageType = messageType;
    }

    public Location getExistingLocation() {
        return existingLocation;
    }

    public void setExistingLocation(final Location existingLocation) {
        this.existingLocation = existingLocation;
    }

    public Location getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(final Location newLocation) {
        this.newLocation = newLocation;
    }

    @Override
    public String toString() {
        return "LocationMessage{"
                + "locationId=" + locationId
                + ", existingLocation=" + existingLocation + '\''
                + ", newLocation=" + newLocation + '\''
                + ", messageType='" + messageType + '\''
                + '}';
    }
}
