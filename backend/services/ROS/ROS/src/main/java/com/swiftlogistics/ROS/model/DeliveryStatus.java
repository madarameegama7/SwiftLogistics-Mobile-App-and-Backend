package com.swiftlogistics.ROS.model;

/**
 * Enum representing the status of a delivery
 */
public enum DeliveryStatus {
    PENDING("Pending"),
    ASSIGNED("Assigned"),
    IN_TRANSIT("In Transit"),
    OUT_FOR_DELIVERY("Out for Delivery"),
    DELIVERED("Delivered"),
    FAILED("Failed"),
    RETURNED("Returned"),
    CANCELLED("Cancelled");

    private final String displayName;

    DeliveryStatus(String displayName) {
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
