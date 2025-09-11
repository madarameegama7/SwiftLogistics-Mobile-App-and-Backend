package com.swiftlogistics.ROS.model;

/**
 * Enum representing the status of a waypoint in a route
 */
public enum WaypointStatus {
    PENDING("Pending"),
    IN_TRANSIT("In Transit"),
    ARRIVED("Arrived"),
    COMPLETED("Completed"),
    SKIPPED("Skipped"),
    FAILED("Failed");

    private final String displayName;

    WaypointStatus(String displayName) {
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
