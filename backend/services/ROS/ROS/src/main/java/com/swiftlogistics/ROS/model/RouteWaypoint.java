package com.swiftlogistics.ROS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "route_waypoints")
public class RouteWaypoint {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Route is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;
    
    @NotNull(message = "Delivery is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;
    
    @NotNull(message = "Waypoint order is required")
    @Column(name = "waypoint_order", nullable = false)
    private Integer waypointOrder;
    
    @Column(name = "estimated_arrival_time")
    private LocalDateTime estimatedArrivalTime;
    
    @Column(name = "actual_arrival_time")
    private LocalDateTime actualArrivalTime;
    
    @Column(name = "distance_from_previous", precision = 10, scale = 2)
    private BigDecimal distanceFromPrevious;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    // Default constructor
    public RouteWaypoint() {}
    
    // Constructor
    public RouteWaypoint(Route route, Delivery delivery, Integer waypointOrder) {
        this.route = route;
        this.delivery = delivery;
        this.waypointOrder = waypointOrder;
    }
    
    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Route getRoute() {
        return route;
    }
    
    public void setRoute(Route route) {
        this.route = route;
    }
    
    public Delivery getDelivery() {
        return delivery;
    }
    
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
    
    public Integer getWaypointOrder() {
        return waypointOrder;
    }
    
    public void setWaypointOrder(Integer waypointOrder) {
        this.waypointOrder = waypointOrder;
    }
    
    public LocalDateTime getEstimatedArrivalTime() {
        return estimatedArrivalTime;
    }
    
    public void setEstimatedArrivalTime(LocalDateTime estimatedArrivalTime) {
        this.estimatedArrivalTime = estimatedArrivalTime;
    }
    
    public LocalDateTime getActualArrivalTime() {
        return actualArrivalTime;
    }
    
    public void setActualArrivalTime(LocalDateTime actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime;
    }
    
    public BigDecimal getDistanceFromPrevious() {
        return distanceFromPrevious;
    }
    
    public void setDistanceFromPrevious(BigDecimal distanceFromPrevious) {
        this.distanceFromPrevious = distanceFromPrevious;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    // Utility methods
    public boolean isVisited() {
        return actualArrivalTime != null;
    }
    
    public boolean isDelayed() {
        if (estimatedArrivalTime == null || actualArrivalTime == null) {
            return false;
        }
        return actualArrivalTime.isAfter(estimatedArrivalTime);
    }
    
    public long getDelayInMinutes() {
        if (!isDelayed()) {
            return 0;
        }
        return java.time.Duration.between(estimatedArrivalTime, actualArrivalTime).toMinutes();
    }
    
    @Override
    public String toString() {
        return "RouteWaypoint{" +
                "id=" + id +
                ", waypointOrder=" + waypointOrder +
                ", delivery=" + (delivery != null ? delivery.getPackageId() : "null") +
                ", estimatedArrivalTime=" + estimatedArrivalTime +
                '}';
    }
}
