package com.swiftlogistics.ROS.model;

/**
 * Enum representing the status of a route
 */
public enum RouteStatus {
    PLANNED("Planned"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    PAUSED("Paused");

    private final String displayName;

    RouteStatus(String displayName) {
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
