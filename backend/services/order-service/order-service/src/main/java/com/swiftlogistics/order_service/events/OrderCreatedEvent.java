package com.swiftlogistics.order_service.events;

import java.io.Serializable;

public class OrderCreatedEvent implements Serializable {
    private Long orderId;
    private String customerName;
    private String status;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(Long orderId, String customerName, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.status = status;
    }

    // Getters and Setters
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "OrderCreatedEvent{" +
                "orderId=" + orderId +
                ", customerName='" + customerName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
