package com.endeavorms.velocity.qto.service;

import java.util.Arrays;
import java.util.List;

/**
 * Enumerates the different order types.
 */
public enum OrderType {

    NEW("New"),

    MOVE("Move"),

    ADD("Add"),

    CHANGE("Change"),

    DISCONNECT("Disconnect");

    /** Order Type. */
    private final String orderType;

    /**
     * Constructor for initializing the enum.
     * @param orderType the name of the service.
     */
    OrderType(final String orderType) {
        this.orderType = orderType;
    }

    /**
     * Returns the order type enum by the given name.
     * @param orderType the given name.
     * @return a matching order type enum, if it exists.
     * @throws IllegalArgumentException if it's unsupported order type string.
     */
    public static OrderType fromOrderType(String orderType)
            throws IllegalArgumentException {
        for (OrderType b : OrderType.values()) {
            if (b.orderType.equalsIgnoreCase(orderType)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unsupported service type '" + orderType + "'.");
    }

    public String getOrderType() {
        return orderType;
    }

    /**
     * Returns a list of all order types except disconnect.
     * @return a list of all order types except disconnect.
     */
    public static List<String> getMacTypes() {
        //return list of all order types except disconnect
        return Arrays.asList(Arrays.stream(OrderType.values())
                .filter(orderType -> !orderType.equals(OrderType.DISCONNECT))
                .map(OrderType::getOrderType).toArray(String[]::new));
    }
}
