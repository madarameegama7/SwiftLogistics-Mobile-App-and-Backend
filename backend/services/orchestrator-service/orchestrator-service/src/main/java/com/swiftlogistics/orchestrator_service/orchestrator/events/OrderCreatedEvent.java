package com.swiftlogistics.orchestrator_service.orchestrator.events;

import java.io.Serializable;

public class OrderCreatedEvent implements Serializable {
    private Long orderId;
    private String user;
    private String status;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(Long orderId, String user, String status) {
        this.orderId = orderId;
        this.user = user;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
