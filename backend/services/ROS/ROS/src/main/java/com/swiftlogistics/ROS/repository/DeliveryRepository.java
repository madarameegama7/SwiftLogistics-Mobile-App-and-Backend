package com.swiftlogistics.ROS.repository;

import com.swiftlogistics.ROS.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Delivery entity operations.
 * Provides standard CRUD operations and custom query methods for delivery management.
 */
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    
    /**
     * Find delivery by package ID
     */
    Optional<Delivery> findByPackageId(String packageId);
    
    /**
     * Find all deliveries by status
     */
    List<Delivery> findByDeliveryStatus(Delivery.DeliveryStatus deliveryStatus);
    
    /**
     * Find deliveries assigned to a specific route
     */
    List<Delivery> findByRouteId(Long routeId);
    
    /**
     * Find deliveries by recipient name (case-insensitive)
     */
    List<Delivery> findByRecipientNameContainingIgnoreCase(String recipientName);
    
    /**
     * Find deliveries by recipient contact
     */
    List<Delivery> findByRecipientContact(String recipientContact);
    
    /**
     * Find deliveries by priority level
     */
    List<Delivery> findByPriorityLevel(String priorityLevel);
    
    /**
     * Find high priority deliveries
     */
    @Query("SELECT d FROM Delivery d WHERE d.priorityLevel = 'HIGH'")
    List<Delivery> findHighPriorityDeliveries();
    
    /**
     * Find deliveries by package type
     */
    List<Delivery> findByPackageType(String packageType);
    
    /**
     * Find deliveries within weight range
     */
    @Query("SELECT d FROM Delivery d WHERE d.packageWeight BETWEEN :minWeight AND :maxWeight")
    List<Delivery> findDeliveriesByWeightRange(
        @Param("minWeight") BigDecimal minWeight,
        @Param("maxWeight") BigDecimal maxWeight
    );
    
    /**
     * Find deliveries within volume range
     */
    @Query("SELECT d FROM Delivery d WHERE d.packageVolume BETWEEN :minVolume AND :maxVolume")
    List<Delivery> findDeliveriesByVolumeRange(
        @Param("minVolume") BigDecimal minVolume,
        @Param("maxVolume") BigDecimal maxVolume
    );
    
    /**
     * Find pending deliveries (not yet assigned to a route)
     */
    @Query("SELECT d FROM Delivery d WHERE d.route IS NULL AND d.deliveryStatus = 'PENDING'")
    List<Delivery> findPendingUnassignedDeliveries();
    
    /**
     * Find deliveries by address ID
     */
    List<Delivery> findByDeliveryAddressId(Long addressId);
    
    /**
     * Count deliveries by status
     */
    @Query("SELECT COUNT(d) FROM Delivery d WHERE d.deliveryStatus = :deliveryStatus")
    Long countByDeliveryStatus(@Param("deliveryStatus") Delivery.DeliveryStatus deliveryStatus);
    
    /**
     * Find completed deliveries within date range
     */
    @Query("SELECT d FROM Delivery d WHERE d.deliveryStatus = 'DELIVERED' AND " +
           "d.actualDeliveryTime BETWEEN :startDate AND :endDate")
    List<Delivery> findCompletedDeliveriesInDateRange(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    /**
     * Get delivery performance statistics
     */
    @Query("SELECT " +
           "COUNT(d) as totalDeliveries, " +
           "SUM(CASE WHEN d.deliveryStatus = 'DELIVERED' THEN 1 ELSE 0 END) as completedDeliveries, " +
           "SUM(CASE WHEN d.deliveryStatus = 'FAILED' THEN 1 ELSE 0 END) as failedDeliveries, " +
           "AVG(d.packageWeight) as avgWeight, " +
           "AVG(d.packageVolume) as avgVolume " +
           "FROM Delivery d")
    Object[] getDeliveryStatistics();
    
    /**
     * Find deliveries that require special handling
     */
    @Query("SELECT d FROM Delivery d WHERE d.specialInstructions IS NOT NULL AND d.specialInstructions != ''")
    List<Delivery> findDeliveriesWithSpecialInstructions();
    
    /**
     * Find deliveries by route status
     */
    @Query("SELECT d FROM Delivery d WHERE d.route.routeStatus = :routeStatus")
    List<Delivery> findDeliveriesByRouteStatus(@Param("routeStatus") String routeStatus);
    
    /**
     * Check if package ID already exists
     */
    boolean existsByPackageId(String packageId);
    
    /**
     * Find fragile deliveries
     */
    @Query("SELECT d FROM Delivery d WHERE LOWER(d.packageType) LIKE '%fragile%' OR " +
           "LOWER(d.specialInstructions) LIKE '%fragile%'")
    List<Delivery> findFragileDeliveries();
    
    /**
     * Find deliveries by weight exceeding threshold
     */
    @Query("SELECT d FROM Delivery d WHERE d.packageWeight > :weightThreshold")
    List<Delivery> findHeavyDeliveries(@Param("weightThreshold") BigDecimal weightThreshold);
    
    /**
     * Get delivery summary for a specific route
     */
    @Query("SELECT " +
           "COUNT(d) as totalDeliveries, " +
           "SUM(d.packageWeight) as totalWeight, " +
           "SUM(d.packageVolume) as totalVolume, " +
           "SUM(CASE WHEN d.priorityLevel = 'HIGH' THEN 1 ELSE 0 END) as highPriorityCount " +
           "FROM Delivery d WHERE d.route.id = :routeId")
    Object[] getDeliverySummaryForRoute(@Param("routeId") Long routeId);
    
    /**
     * Find recent deliveries (created in last N days)
     */
    @Query("SELECT d FROM Delivery d WHERE d.createdAt >= :cutoffDate ORDER BY d.createdAt DESC")
    List<Delivery> findRecentDeliveries(@Param("cutoffDate") LocalDateTime cutoffDate);
    
    /**
     * Find deliveries that can fit in specific vehicle capacity
     */
    @Query("SELECT d FROM Delivery d WHERE d.packageWeight <= :maxWeight AND d.packageVolume <= :maxVolume AND " +
           "d.deliveryStatus = 'PENDING' AND d.route IS NULL")
    List<Delivery> findDeliveriesThatFitInVehicle(
        @Param("maxWeight") BigDecimal maxWeight,
        @Param("maxVolume") BigDecimal maxVolume
    );
}
