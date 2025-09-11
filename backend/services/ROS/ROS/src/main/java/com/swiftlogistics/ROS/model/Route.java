package com.swiftlogistics.ROS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "routes")
public class Route {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "route_name", length = 100)
    private String routeName;
    
    @NotNull(message = "Vehicle is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
    
    @NotNull(message = "Start location is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_location_id", nullable = false)
    private Address startLocation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_location_id")
    private Address endLocation;
    
    @Column(name = "total_distance", precision = 10, scale = 2)
    private BigDecimal totalDistance;
    
    @Column(name = "estimated_duration")
    private Integer estimatedDuration; // in minutes
    
    @Column(name = "actual_duration")
    private Integer actualDuration; // in minutes
    
    @Enumerated(EnumType.STRING)
    @Column(name = "route_status", length = 20)
    private RouteStatus routeStatus = RouteStatus.PLANNED;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "optimization_type", length = 50)
    private OptimizationType optimizationType = OptimizationType.DISTANCE;
    
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Delivery> deliveries;
    
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RouteWaypoint> waypoints;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Enums
    public enum RouteStatus {
        PLANNED, IN_PROGRESS, COMPLETED, CANCELLED
    }
    
    public enum OptimizationType {
        DISTANCE, TIME, FUEL_EFFICIENCY, PRIORITY
    }
    
    // Default constructor
    public Route() {}
    
    // Constructor
    public Route(String routeName, Vehicle vehicle, Address startLocation) {
        this.routeName = routeName;
        this.vehicle = vehicle;
        this.startLocation = startLocation;
    }
    
    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRouteName() {
        return routeName;
    }
    
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
    
    public Vehicle getVehicle() {
        return vehicle;
    }
    
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    public Address getStartLocation() {
        return startLocation;
    }
    
    public void setStartLocation(Address startLocation) {
        this.startLocation = startLocation;
    }
    
    public Address getEndLocation() {
        return endLocation;
    }
    
    public void setEndLocation(Address endLocation) {
        this.endLocation = endLocation;
    }
    
    public BigDecimal getTotalDistance() {
        return totalDistance;
    }
    
    public void setTotalDistance(BigDecimal totalDistance) {
        this.totalDistance = totalDistance;
    }
    
    public Integer getEstimatedDuration() {
        return estimatedDuration;
    }
    
    public void setEstimatedDuration(Integer estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }
    
    public Integer getActualDuration() {
        return actualDuration;
    }
    
    public void setActualDuration(Integer actualDuration) {
        this.actualDuration = actualDuration;
    }
    
    public RouteStatus getRouteStatus() {
        return routeStatus;
    }
    
    public void setRouteStatus(RouteStatus routeStatus) {
        this.routeStatus = routeStatus;
    }
    
    public OptimizationType getOptimizationType() {
        return optimizationType;
    }
    
    public void setOptimizationType(OptimizationType optimizationType) {
        this.optimizationType = optimizationType;
    }
    
    public List<Delivery> getDeliveries() {
        return deliveries;
    }
    
    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }
    
    public List<RouteWaypoint> getWaypoints() {
        return waypoints;
    }
    
    public void setWaypoints(List<RouteWaypoint> waypoints) {
        this.waypoints = waypoints;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Utility methods
    public boolean isActive() {
        return routeStatus == RouteStatus.PLANNED || routeStatus == RouteStatus.IN_PROGRESS;
    }
    
    public boolean isCompleted() {
        return routeStatus == RouteStatus.COMPLETED;
    }
    
    public int getDeliveryCount() {
        return deliveries != null ? deliveries.size() : 0;
    }
    
    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", routeName='" + routeName + '\'' +
                ", vehicle=" + (vehicle != null ? vehicle.getVehicleNumber() : "null") +
                ", routeStatus=" + routeStatus +
                ", totalDistance=" + totalDistance +
                '}';
    }
}
