package com.swiftlogistics.ROS.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OptimizationResponse {
    
    private Long routeId;
    private String routeName;
    private Long vehicleId;
    private String vehicleNumber;
    private List<OptimizedWaypoint> optimizedPath;
    private BigDecimal totalDistance;
    private Integer estimatedDuration; // in minutes
    private String optimizationType;
    private String routeStatus;
    private OptimizationSummary summary;
    private LocalDateTime createdAt;
    
    // Default constructor
    public OptimizationResponse() {}
    
    // Constructor
    public OptimizationResponse(Long routeId, String routeName) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getRouteId() {
        return routeId;
    }
    
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
    
    public String getRouteName() {
        return routeName;
    }
    
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
    
    public Long getVehicleId() {
        return vehicleId;
    }
    
    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public String getVehicleNumber() {
        return vehicleNumber;
    }
    
    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
    
    public List<OptimizedWaypoint> getOptimizedPath() {
        return optimizedPath;
    }
    
    public void setOptimizedPath(List<OptimizedWaypoint> optimizedPath) {
        this.optimizedPath = optimizedPath;
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
    
    public String getOptimizationType() {
        return optimizationType;
    }
    
    public void setOptimizationType(String optimizationType) {
        this.optimizationType = optimizationType;
    }
    
    public String getRouteStatus() {
        return routeStatus;
    }
    
    public void setRouteStatus(String routeStatus) {
        this.routeStatus = routeStatus;
    }
    
    public OptimizationSummary getSummary() {
        return summary;
    }
    
    public void setSummary(OptimizationSummary summary) {
        this.summary = summary;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    // Inner class for optimized waypoint
    public static class OptimizedWaypoint {
        private Integer sequence;
        private String packageId;
        private String recipientName;
        private String deliveryAddress;
        private BigDecimal latitude;
        private BigDecimal longitude;
        private LocalDateTime estimatedArrivalTime;
        private BigDecimal distanceFromPrevious;
        private String priorityLevel;
        private String packageType;
        
        // Default constructor
        public OptimizedWaypoint() {}
        
        // Constructor
        public OptimizedWaypoint(Integer sequence, String packageId, String recipientName) {
            this.sequence = sequence;
            this.packageId = packageId;
            this.recipientName = recipientName;
        }
        
        // Getters and Setters
        public Integer getSequence() { return sequence; }
        public void setSequence(Integer sequence) { this.sequence = sequence; }
        
        public String getPackageId() { return packageId; }
        public void setPackageId(String packageId) { this.packageId = packageId; }
        
        public String getRecipientName() { return recipientName; }
        public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
        
        public String getDeliveryAddress() { return deliveryAddress; }
        public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
        
        public BigDecimal getLatitude() { return latitude; }
        public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
        
        public BigDecimal getLongitude() { return longitude; }
        public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
        
        public LocalDateTime getEstimatedArrivalTime() { return estimatedArrivalTime; }
        public void setEstimatedArrivalTime(LocalDateTime estimatedArrivalTime) { this.estimatedArrivalTime = estimatedArrivalTime; }
        
        public BigDecimal getDistanceFromPrevious() { return distanceFromPrevious; }
        public void setDistanceFromPrevious(BigDecimal distanceFromPrevious) { this.distanceFromPrevious = distanceFromPrevious; }
        
        public String getPriorityLevel() { return priorityLevel; }
        public void setPriorityLevel(String priorityLevel) { this.priorityLevel = priorityLevel; }
        
        public String getPackageType() { return packageType; }
        public void setPackageType(String packageType) { this.packageType = packageType; }
    }
    
    // Inner class for optimization summary
    public static class OptimizationSummary {
        private Integer totalDeliveries;
        private BigDecimal totalWeight;
        private BigDecimal totalVolume;
        private Integer highPriorityCount;
        private BigDecimal fuelEfficiencyScore;
        private String optimizationAlgorithm;
        private Long processingTimeMs;
        
        // Getters and Setters
        public Integer getTotalDeliveries() { return totalDeliveries; }
        public void setTotalDeliveries(Integer totalDeliveries) { this.totalDeliveries = totalDeliveries; }
        
        public BigDecimal getTotalWeight() { return totalWeight; }
        public void setTotalWeight(BigDecimal totalWeight) { this.totalWeight = totalWeight; }
        
        public BigDecimal getTotalVolume() { return totalVolume; }
        public void setTotalVolume(BigDecimal totalVolume) { this.totalVolume = totalVolume; }
        
        public Integer getHighPriorityCount() { return highPriorityCount; }
        public void setHighPriorityCount(Integer highPriorityCount) { this.highPriorityCount = highPriorityCount; }
        
        public BigDecimal getFuelEfficiencyScore() { return fuelEfficiencyScore; }
        public void setFuelEfficiencyScore(BigDecimal fuelEfficiencyScore) { this.fuelEfficiencyScore = fuelEfficiencyScore; }
        
        public String getOptimizationAlgorithm() { return optimizationAlgorithm; }
        public void setOptimizationAlgorithm(String optimizationAlgorithm) { this.optimizationAlgorithm = optimizationAlgorithm; }
        
        public Long getProcessingTimeMs() { return processingTimeMs; }
        public void setProcessingTimeMs(Long processingTimeMs) { this.processingTimeMs = processingTimeMs; }
    }
}
