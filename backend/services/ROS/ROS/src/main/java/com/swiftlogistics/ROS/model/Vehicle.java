package com.swiftlogistics.ROS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Vehicle number is required")
    @Column(name = "vehicle_number", nullable = false, unique = true, length = 50)
    private String vehicleNumber;
    
    @NotBlank(message = "Vehicle type is required")
    @Column(name = "vehicle_type", nullable = false, length = 50)
    private String vehicleType;
    
    @NotNull(message = "Capacity weight is required")
    @Positive(message = "Capacity weight must be positive")
    @Column(name = "capacity_weight", nullable = false, precision = 10, scale = 2)
    private BigDecimal capacityWeight;
    
    @Column(name = "capacity_volume", precision = 10, scale = 2)
    private BigDecimal capacityVolume;
    
    @Column(name = "driver_name", length = 100)
    private String driverName;
    
    @Column(name = "driver_contact", length = 20)
    private String driverContact;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private VehicleStatus status = VehicleStatus.AVAILABLE;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_location_id")
    private Address currentLocation;
    
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Route> routes;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Enum for vehicle status
    public enum VehicleStatus {
        AVAILABLE, IN_TRANSIT, MAINTENANCE, OUT_OF_SERVICE
    }
    
    // Default constructor
    public Vehicle() {}
    
    // Constructor
    public Vehicle(String vehicleNumber, String vehicleType, BigDecimal capacityWeight) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.capacityWeight = capacityWeight;
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
    
    public String getVehicleNumber() {
        return vehicleNumber;
    }
    
    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
    
    public String getVehicleType() {
        return vehicleType;
    }
    
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    
    public BigDecimal getCapacityWeight() {
        return capacityWeight;
    }
    
    public void setCapacityWeight(BigDecimal capacityWeight) {
        this.capacityWeight = capacityWeight;
    }
    
    public BigDecimal getCapacityVolume() {
        return capacityVolume;
    }
    
    public void setCapacityVolume(BigDecimal capacityVolume) {
        this.capacityVolume = capacityVolume;
    }
    
    public String getDriverName() {
        return driverName;
    }
    
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    
    public String getDriverContact() {
        return driverContact;
    }
    
    public void setDriverContact(String driverContact) {
        this.driverContact = driverContact;
    }
    
    public VehicleStatus getStatus() {
        return status;
    }
    
    public void setStatus(VehicleStatus status) {
        this.status = status;
    }
    
    public Address getCurrentLocation() {
        return currentLocation;
    }
    
    public void setCurrentLocation(Address currentLocation) {
        this.currentLocation = currentLocation;
    }
    
    public List<Route> getRoutes() {
        return routes;
    }
    
    public void setRoutes(List<Route> routes) {
        this.routes = routes;
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
    public boolean isAvailable() {
        return status == VehicleStatus.AVAILABLE;
    }
    
    public boolean canCarryWeight(BigDecimal weight) {
        return capacityWeight.compareTo(weight) >= 0;
    }
    
    public boolean canCarryVolume(BigDecimal volume) {
        return capacityVolume != null && capacityVolume.compareTo(volume) >= 0;
    }
    
    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", capacityWeight=" + capacityWeight +
                ", status=" + status +
                '}';
    }
}
