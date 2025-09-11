package com.swiftlogistics.ROS.repository;

import com.swiftlogistics.ROS.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Route entity operations.
 * Provides standard CRUD operations and custom query methods for route management.
 */
@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    
    /**
     * Find route by name
     */
    Optional<Route> findByRouteName(String routeName);
    
    /**
     * Find all routes by status
     */
    List<Route> findByRouteStatus(Route.RouteStatus routeStatus);
    
    /**
     * Find routes assigned to a specific vehicle
     */
    List<Route> findByVehicleId(Long vehicleId);
    
    /**
     * Find active routes (PLANNED, IN_PROGRESS)
     */
    @Query("SELECT r FROM Route r WHERE r.routeStatus IN ('PLANNED', 'IN_PROGRESS')")
    List<Route> findActiveRoutes();
    
    /**
     * Find completed routes
     */
    @Query("SELECT r FROM Route r WHERE r.routeStatus = 'COMPLETED'")
    List<Route> findCompletedRoutes();
    
    /**
     * Find routes by optimization type
     */
    List<Route> findByOptimizationType(String optimizationType);
    
    /**
     * Find routes created within a date range
     */
    @Query("SELECT r FROM Route r WHERE r.createdAt BETWEEN :startDate AND :endDate")
    List<Route> findRoutesByDateRange(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    /**
     * Find routes with total distance greater than specified value
     */
    @Query("SELECT r FROM Route r WHERE r.totalDistance >= :minDistance")
    List<Route> findRoutesWithMinimumDistance(@Param("minDistance") BigDecimal minDistance);
    
    /**
     * Find routes with estimated duration within range (in minutes)
     */
    @Query("SELECT r FROM Route r WHERE r.estimatedDuration BETWEEN :minDuration AND :maxDuration")
    List<Route> findRoutesByDurationRange(
        @Param("minDuration") Integer minDuration,
        @Param("maxDuration") Integer maxDuration
    );
    
    /**
     * Count routes by status
     */
    @Query("SELECT COUNT(r) FROM Route r WHERE r.routeStatus = :routeStatus")
    Long countByRouteStatus(@Param("routeStatus") Route.RouteStatus routeStatus);
    
    /**
     * Find routes by vehicle number
     */
    @Query("SELECT r FROM Route r WHERE r.vehicle.vehicleNumber = :vehicleNumber")
    List<Route> findRoutesByVehicleNumber(@Param("vehicleNumber") String vehicleNumber);
    
    /**
     * Get route efficiency statistics (distance per delivery)
     */
    @Query("SELECT r.id, r.routeName, r.totalDistance, " +
           "SIZE(r.deliveries) as deliveryCount, " +
           "(r.totalDistance / SIZE(r.deliveries)) as distancePerDelivery " +
           "FROM Route r WHERE r.routeStatus = 'COMPLETED' AND SIZE(r.deliveries) > 0")
    List<Object[]> getRouteEfficiencyStatistics();
    
    /**
     * Find most efficient routes (lowest distance per delivery)
     */
    @Query("SELECT r FROM Route r WHERE r.routeStatus = 'COMPLETED' AND SIZE(r.deliveries) > 0 " +
           "ORDER BY (r.totalDistance / SIZE(r.deliveries)) ASC")
    List<Route> findMostEfficientRoutes();
    
    /**
     * Get route summary statistics
     */
    @Query("SELECT " +
           "COUNT(r) as totalRoutes, " +
           "AVG(r.totalDistance) as avgDistance, " +
           "AVG(r.estimatedDuration) as avgDuration, " +
           "SUM(CASE WHEN r.routeStatus = 'COMPLETED' THEN 1 ELSE 0 END) as completedRoutes " +
           "FROM Route r")
    Object[] getRouteSummaryStatistics();
    
    /**
     * Check if route name already exists
     */
    boolean existsByRouteName(String routeName);
    
    /**
     * Find recent routes (created in last N days)
     */
    @Query("SELECT r FROM Route r WHERE r.createdAt >= :cutoffDate ORDER BY r.createdAt DESC")
    List<Route> findRecentRoutes(@Param("cutoffDate") LocalDateTime cutoffDate);
}
