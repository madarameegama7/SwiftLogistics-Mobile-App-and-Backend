package com.swiftlogistics.ROS.model;

/**
 * Enum representing the status of a vehicle
 */
public enum VehicleStatus {
    AVAILABLE("Available"),
    IN_USE("In Use"),
    MAINTENANCE("Maintenance"),
    OUT_OF_SERVICE("Out of Service"),
    OFFLINE("Offline");

    private final String displayName;

    VehicleStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
