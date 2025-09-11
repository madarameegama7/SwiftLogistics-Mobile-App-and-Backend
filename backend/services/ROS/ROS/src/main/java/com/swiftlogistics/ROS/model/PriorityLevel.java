package com.swiftlogistics.ROS.model;

/**
 * Enum representing the priority level of a delivery
 */
public enum PriorityLevel {
    LOW("Low"),
    NORMAL("Normal"),
    HIGH("High"),
    URGENT("Urgent"),
    CRITICAL("Critical");

    private final String displayName;

    PriorityLevel(String displayName) {
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
