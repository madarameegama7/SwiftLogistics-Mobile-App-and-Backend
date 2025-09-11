package com.swiftlogistics.ROS.service;

import com.swiftlogistics.ROS.model.Vehicle;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Vehicle management operations.
 * Handles CRUD operations and business logic for vehicles.
 * Developer B will implement this interface.
 */
public interface VehicleService {
    
    /**
     * Create a new vehicle
     */
    Vehicle createVehicle(Vehicle vehicle);
    
    /**
     * Get vehicle by ID
     */
    Optional<Vehicle> getVehicleById(Long vehicleId);
    
    /**
     * Get vehicle by vehicle number
     */
    Optional<Vehicle> getVehicleByNumber(String vehicleNumber);
    
    /**
     * Update an existing vehicle
     */
    Vehicle updateVehicle(Long vehicleId, Vehicle vehicle);
    
    /**
     * Delete a vehicle (soft delete - mark as out of service)
     */
    void deleteVehicle(Long vehicleId);
    
    /**
     * Get all vehicles with pagination support
     */
    List<Vehicle> getAllVehicles(int page, int size);
    
    /**
     * Get vehicles by status
     */
    List<Vehicle> getVehiclesByStatus(Vehicle.VehicleStatus status);
    
    /**
     * Get available vehicles
     */
    List<Vehicle> getAvailableVehicles();
    
    /**
     * Get vehicles in transit
     */
    List<Vehicle> getVehiclesInTransit();
    
    /**
     * Get vehicles under maintenance
     */
    List<Vehicle> getVehiclesUnderMaintenance();
    
    /**
     * Find vehicles with sufficient weight capacity
     */
    List<Vehicle> findVehiclesWithWeightCapacity(BigDecimal requiredWeight);
    
    /**
     * Find vehicles with sufficient volume capacity
     */
    List<Vehicle> findVehiclesWithVolumeCapacity(BigDecimal requiredVolume);
    
    /**
     * Find vehicles with sufficient capacity (both weight and volume)
     */
    List<Vehicle> findVehiclesWithCapacity(BigDecimal requiredWeight, BigDecimal requiredVolume);
    
    /**
     * Get vehicles by type
     */
    List<Vehicle> getVehiclesByType(String vehicleType);
    
    /**
     * Assign vehicle to a route (change status to IN_TRANSIT)
     */
    Vehicle assignVehicleToRoute(Long vehicleId, Long routeId);
    
    /**
     * Mark vehicle as available (change status to AVAILABLE)
     */
    Vehicle markVehicleAsAvailable(Long vehicleId);
    
    /**
     * Mark vehicle as under maintenance
     */
    Vehicle markVehicleUnderMaintenance(Long vehicleId, String maintenanceReason);
    
    /**
     * Mark vehicle as out of service
     */
    Vehicle markVehicleOutOfService(Long vehicleId, String reason);
    
    /**
     * Update vehicle location
     */
    Vehicle updateVehicleLocation(Long vehicleId, BigDecimal latitude, BigDecimal longitude);
    
    /**
     * Update vehicle driver information
     */
    Vehicle updateDriverInfo(Long vehicleId, String driverName, String driverContact);
    
    /**
     * Get vehicles by driver name
     */
    List<Vehicle> getVehiclesByDriver(String driverName);
    
    /**
     * Get most fuel-efficient vehicles
     */
    List<Vehicle> getMostFuelEfficientVehicles();
    
    /**
     * Get vehicles with highest capacity
     */
    List<Vehicle> getHighestCapacityVehicles();
    
    /**
     * Check vehicle availability for date range
     */
    boolean isVehicleAvailable(Long vehicleId, java.time.LocalDateTime startTime, java.time.LocalDateTime endTime);
    
    /**
     * Get fleet statistics
     */
    FleetStatistics getFleetStatistics();
    
    /**
     * Find optimal vehicle for delivery requirements
     */
    Optional<Vehicle> findOptimalVehicle(BigDecimal requiredWeight, BigDecimal requiredVolume, String preferredType);
    
    /**
     * Update vehicle fuel efficiency
     */
    Vehicle updateFuelEfficiency(Long vehicleId, BigDecimal newFuelEfficiency);
    
    /**
     * Get vehicle utilization report
     */
    List<VehicleUtilization> getVehicleUtilizationReport();
    
    /**
     * Inner class for fleet statistics
     */
    class FleetStatistics {
        private Long totalVehicles;
        private Long availableVehicles;
        private Long inTransitVehicles;
        private Long maintenanceVehicles;
        private Long outOfServiceVehicles;
        private BigDecimal totalWeightCapacity;
        private BigDecimal totalVolumeCapacity;
        private Double averageFuelEfficiency;
        
        // Constructors
        public FleetStatistics() {}
        
        // Getters and Setters
        public Long getTotalVehicles() { return totalVehicles; }
        public void setTotalVehicles(Long totalVehicles) { this.totalVehicles = totalVehicles; }
        
        public Long getAvailableVehicles() { return availableVehicles; }
        public void setAvailableVehicles(Long availableVehicles) { this.availableVehicles = availableVehicles; }
        
        public Long getInTransitVehicles() { return inTransitVehicles; }
        public void setInTransitVehicles(Long inTransitVehicles) { this.inTransitVehicles = inTransitVehicles; }
        
        public Long getMaintenanceVehicles() { return maintenanceVehicles; }
        public void setMaintenanceVehicles(Long maintenanceVehicles) { this.maintenanceVehicles = maintenanceVehicles; }
        
        public Long getOutOfServiceVehicles() { return outOfServiceVehicles; }
        public void setOutOfServiceVehicles(Long outOfServiceVehicles) { this.outOfServiceVehicles = outOfServiceVehicles; }
        
        public BigDecimal getTotalWeightCapacity() { return totalWeightCapacity; }
        public void setTotalWeightCapacity(BigDecimal totalWeightCapacity) { this.totalWeightCapacity = totalWeightCapacity; }
        
        public BigDecimal getTotalVolumeCapacity() { return totalVolumeCapacity; }
        public void setTotalVolumeCapacity(BigDecimal totalVolumeCapacity) { this.totalVolumeCapacity = totalVolumeCapacity; }
        
        public Double getAverageFuelEfficiency() { return averageFuelEfficiency; }
        public void setAverageFuelEfficiency(Double averageFuelEfficiency) { this.averageFuelEfficiency = averageFuelEfficiency; }
    }
    
    /**
     * Inner class for vehicle utilization
     */
    class VehicleUtilization {
        private Long vehicleId;
        private String vehicleNumber;
        private Double utilizationPercentage;
        private Long totalRoutesAssigned;
        private BigDecimal totalDistanceCovered;
        private java.time.LocalDateTime lastUsed;
        
        // Constructors
        public VehicleUtilization() {}
        
        // Getters and Setters
        public Long getVehicleId() { return vehicleId; }
        public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }
        
        public String getVehicleNumber() { return vehicleNumber; }
        public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
        
        public Double getUtilizationPercentage() { return utilizationPercentage; }
        public void setUtilizationPercentage(Double utilizationPercentage) { this.utilizationPercentage = utilizationPercentage; }
        
        public Long getTotalRoutesAssigned() { return totalRoutesAssigned; }
        public void setTotalRoutesAssigned(Long totalRoutesAssigned) { this.totalRoutesAssigned = totalRoutesAssigned; }
        
        public BigDecimal getTotalDistanceCovered() { return totalDistanceCovered; }
        public void setTotalDistanceCovered(BigDecimal totalDistanceCovered) { this.totalDistanceCovered = totalDistanceCovered; }
        
        public java.time.LocalDateTime getLastUsed() { return lastUsed; }
        public void setLastUsed(java.time.LocalDateTime lastUsed) { this.lastUsed = lastUsed; }
    }
}
