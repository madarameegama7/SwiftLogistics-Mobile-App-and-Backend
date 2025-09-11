package com.swiftlogistics.ROS.service;

import com.swiftlogistics.ROS.model.Delivery;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Delivery management operations.
 * Handles CRUD operations and business logic for deliveries.
 * Developer B will implement this interface.
 */
public interface DeliveryService {
    
    /**
     * Create a new delivery
     */
    Delivery createDelivery(Delivery delivery);
    
    /**
     * Get delivery by ID
     */
    Optional<Delivery> getDeliveryById(Long deliveryId);
    
    /**
     * Get delivery by package ID
     */
    Optional<Delivery> getDeliveryByPackageId(String packageId);
    
    /**
     * Update an existing delivery
     */
    Delivery updateDelivery(Long deliveryId, Delivery delivery);
    
    /**
     * Delete a delivery (soft delete - mark as cancelled)
     */
    void deleteDelivery(Long deliveryId);
    
    /**
     * Get all deliveries with pagination support
     */
    List<Delivery> getAllDeliveries(int page, int size);
    
    /**
     * Get deliveries by status
     */
    List<Delivery> getDeliveriesByStatus(Delivery.DeliveryStatus status);
    
    /**
     * Get deliveries assigned to a specific route
     */
    List<Delivery> getDeliveriesByRoute(Long routeId);
    
    /**
     * Get unassigned deliveries (not yet assigned to a route)
     */
    List<Delivery> getUnassignedDeliveries();
    
    /**
     * Assign delivery to a route
     */
    Delivery assignDeliveryToRoute(Long deliveryId, Long routeId);
    
    /**
     * Unassign delivery from route
     */
    Delivery unassignDeliveryFromRoute(Long deliveryId);
    
    /**
     * Mark delivery as in transit
     */
    Delivery markDeliveryInTransit(Long deliveryId);
    
    /**
     * Mark delivery as delivered
     */
    Delivery markDeliveryAsDelivered(Long deliveryId, String deliveredBy, String recipientSignature);
    
    /**
     * Mark delivery as failed
     */
    Delivery markDeliveryAsFailed(Long deliveryId, String failureReason);
    
    /**
     * Get deliveries by priority level
     */
    List<Delivery> getDeliveriesByPriority(String priorityLevel);
    
    /**
     * Get high priority deliveries
     */
    List<Delivery> getHighPriorityDeliveries();
    
    /**
     * Get overdue deliveries
     */
    List<Delivery> getOverdueDeliveries();
    
    /**
     * Get deliveries scheduled for today
     */
    List<Delivery> getTodaysDeliveries();
    
    /**
     * Get deliveries within date range
     */
    List<Delivery> getDeliveriesInDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Search deliveries by recipient name
     */
    List<Delivery> searchDeliveriesByRecipient(String recipientName);
    
    /**
     * Get deliveries by package type
     */
    List<Delivery> getDeliveriesByPackageType(String packageType);
    
    /**
     * Get fragile deliveries
     */
    List<Delivery> getFragileDeliveries();
    
    /**
     * Get heavy deliveries (exceeding weight threshold)
     */
    List<Delivery> getHeavyDeliveries(BigDecimal weightThreshold);
    
    /**
     * Calculate total weight for deliveries
     */
    BigDecimal calculateTotalWeight(List<Long> deliveryIds);
    
    /**
     * Calculate total volume for deliveries
     */
    BigDecimal calculateTotalVolume(List<Long> deliveryIds);
    
    /**
     * Check if deliveries can fit in vehicle capacity
     */
    boolean canDeliveriesFitInVehicle(List<Long> deliveryIds, BigDecimal maxWeight, BigDecimal maxVolume);
    
    /**
     * Get delivery performance statistics
     */
    DeliveryStatistics getDeliveryStatistics();
    
    /**
     * Get delivery statistics for specific route
     */
    RouteDeliveryStatistics getDeliveryStatisticsForRoute(Long routeId);
    
    /**
     * Update delivery status
     */
    Delivery updateDeliveryStatus(Long deliveryId, Delivery.DeliveryStatus newStatus);
    
    /**
     * Add special instructions to delivery
     */
    Delivery addSpecialInstructions(Long deliveryId, String instructions);
    
    /**
     * Estimate delivery time based on route and traffic
     */
    LocalDateTime estimateDeliveryTime(Long deliveryId);
    
    /**
     * Inner class for delivery statistics
     */
    class DeliveryStatistics {
        private Long totalDeliveries;
        private Long pendingDeliveries;
        private Long inTransitDeliveries;
        private Long completedDeliveries;
        private Long failedDeliveries;
        private Double successRate;
        private BigDecimal averageWeight;
        private BigDecimal averageVolume;
        
        // Constructors
        public DeliveryStatistics() {}
        
        // Getters and Setters
        public Long getTotalDeliveries() { return totalDeliveries; }
        public void setTotalDeliveries(Long totalDeliveries) { this.totalDeliveries = totalDeliveries; }
        
        public Long getPendingDeliveries() { return pendingDeliveries; }
        public void setPendingDeliveries(Long pendingDeliveries) { this.pendingDeliveries = pendingDeliveries; }
        
        public Long getInTransitDeliveries() { return inTransitDeliveries; }
        public void setInTransitDeliveries(Long inTransitDeliveries) { this.inTransitDeliveries = inTransitDeliveries; }
        
        public Long getCompletedDeliveries() { return completedDeliveries; }
        public void setCompletedDeliveries(Long completedDeliveries) { this.completedDeliveries = completedDeliveries; }
        
        public Long getFailedDeliveries() { return failedDeliveries; }
        public void setFailedDeliveries(Long failedDeliveries) { this.failedDeliveries = failedDeliveries; }
        
        public Double getSuccessRate() { return successRate; }
        public void setSuccessRate(Double successRate) { this.successRate = successRate; }
        
        public BigDecimal getAverageWeight() { return averageWeight; }
        public void setAverageWeight(BigDecimal averageWeight) { this.averageWeight = averageWeight; }
        
        public BigDecimal getAverageVolume() { return averageVolume; }
        public void setAverageVolume(BigDecimal averageVolume) { this.averageVolume = averageVolume; }
    }
    
    /**
     * Inner class for route-specific delivery statistics
     */
    class RouteDeliveryStatistics {
        private Long routeId;
        private Long totalDeliveries;
        private BigDecimal totalWeight;
        private BigDecimal totalVolume;
        private Long highPriorityCount;
        private Long completedCount;
        
        // Constructors
        public RouteDeliveryStatistics() {}
        
        // Getters and Setters
        public Long getRouteId() { return routeId; }
        public void setRouteId(Long routeId) { this.routeId = routeId; }
        
        public Long getTotalDeliveries() { return totalDeliveries; }
        public void setTotalDeliveries(Long totalDeliveries) { this.totalDeliveries = totalDeliveries; }
        
        public BigDecimal getTotalWeight() { return totalWeight; }
        public void setTotalWeight(BigDecimal totalWeight) { this.totalWeight = totalWeight; }
        
        public BigDecimal getTotalVolume() { return totalVolume; }
        public void setTotalVolume(BigDecimal totalVolume) { this.totalVolume = totalVolume; }
        
        public Long getHighPriorityCount() { return highPriorityCount; }
        public void setHighPriorityCount(Long highPriorityCount) { this.highPriorityCount = highPriorityCount; }
        
        public Long getCompletedCount() { return completedCount; }
        public void setCompletedCount(Long completedCount) { this.completedCount = completedCount; }
    }
}
