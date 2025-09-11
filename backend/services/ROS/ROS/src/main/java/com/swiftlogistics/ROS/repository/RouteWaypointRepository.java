package com.swiftlogistics.ROS.repository;

import com.swiftlogistics.ROS.model.RouteWaypoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for RouteWaypoint entity operations.
 * Provides standard CRUD operations and custom query methods for waypoint management.
 */
@Repository
public interface RouteWaypointRepository extends JpaRepository<RouteWaypoint, Long> {
    
    /**
     * Find all waypoints for a specific route, ordered by waypoint order
     */
    List<RouteWaypoint> findByRouteIdOrderByWaypointOrderAsc(Long routeId);
    
    /**
     * Find waypoints for a specific delivery
     */
    List<RouteWaypoint> findByDeliveryId(Long deliveryId);
    
    /**
     * Find waypoint by route and waypoint order
     */
    Optional<RouteWaypoint> findByRouteIdAndWaypointOrder(Long routeId, Integer waypointOrder);
    
    /**
     * Find visited waypoints for a route (have actual arrival time)
     */
    @Query("SELECT rw FROM RouteWaypoint rw WHERE rw.route.id = :routeId AND rw.actualArrivalTime IS NOT NULL " +
           "ORDER BY rw.waypointOrder ASC")
    List<RouteWaypoint> findVisitedWaypointsForRoute(@Param("routeId") Long routeId);
    
    /**
     * Find pending waypoints for a route (not yet visited)
     */
    @Query("SELECT rw FROM RouteWaypoint rw WHERE rw.route.id = :routeId AND rw.actualArrivalTime IS NULL " +
           "ORDER BY rw.waypointOrder ASC")
    List<RouteWaypoint> findPendingWaypointsForRoute(@Param("routeId") Long routeId);
    
    /**
     * Find the next waypoint in sequence for a route
     */
    @Query("SELECT rw FROM RouteWaypoint rw WHERE rw.route.id = :routeId AND rw.actualArrivalTime IS NULL " +
           "ORDER BY rw.waypointOrder ASC")
    Optional<RouteWaypoint> findNextWaypointForRoute(@Param("routeId") Long routeId);
    
    /**
     * Find waypoints scheduled within a time range
     */
    @Query("SELECT rw FROM RouteWaypoint rw WHERE rw.estimatedArrivalTime BETWEEN :startTime AND :endTime " +
           "ORDER BY rw.estimatedArrivalTime ASC")
    List<RouteWaypoint> findWaypointsInTimeRange(
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );
    
    /**
     * Find overdue waypoints (estimated arrival time passed but not visited)
     */
    @Query("SELECT rw FROM RouteWaypoint rw WHERE rw.estimatedArrivalTime < CURRENT_TIMESTAMP AND " +
           "rw.actualArrivalTime IS NULL")
    List<RouteWaypoint> findOverdueWaypoints();
    
    /**
     * Count visited waypoints for a specific route
     */
    @Query("SELECT COUNT(rw) FROM RouteWaypoint rw WHERE rw.route.id = :routeId AND rw.actualArrivalTime IS NOT NULL")
    Long countVisitedWaypointsByRouteId(@Param("routeId") Long routeId);
    
    /**
     * Get route progress (percentage of visited waypoints)
     */
    @Query("SELECT " +
           "(COUNT(CASE WHEN rw.actualArrivalTime IS NOT NULL THEN 1 END) * 100.0 / COUNT(rw)) as progressPercentage " +
           "FROM RouteWaypoint rw WHERE rw.route.id = :routeId")
    Double getRouteProgressPercentage(@Param("routeId") Long routeId);
    
    /**
     * Find waypoints with delays (actual arrival later than estimated)
     */
    @Query("SELECT rw FROM RouteWaypoint rw WHERE rw.actualArrivalTime IS NOT NULL AND " +
           "rw.estimatedArrivalTime IS NOT NULL AND rw.actualArrivalTime > rw.estimatedArrivalTime")
    List<RouteWaypoint> findDelayedWaypoints();
    
    /**
     * Find last visited waypoint for a route
     */
    @Query("SELECT rw FROM RouteWaypoint rw WHERE rw.route.id = :routeId AND rw.actualArrivalTime IS NOT NULL " +
           "ORDER BY rw.waypointOrder DESC")
    Optional<RouteWaypoint> findLastVisitedWaypointForRoute(@Param("routeId") Long routeId);
    
    /**
     * Find all waypoints for multiple routes
     */
    @Query("SELECT rw FROM RouteWaypoint rw WHERE rw.route.id IN :routeIds ORDER BY rw.route.id, rw.waypointOrder")
    List<RouteWaypoint> findByRouteIdIn(@Param("routeIds") List<Long> routeIds);
    
    /**
     * Check if waypoint order exists for a route
     */
    boolean existsByRouteIdAndWaypointOrder(Long routeId, Integer waypointOrder);
    
    /**
     * Find waypoints that need attention (overdue)
     */
    @Query("SELECT rw FROM RouteWaypoint rw WHERE " +
           "rw.estimatedArrivalTime < CURRENT_TIMESTAMP AND rw.actualArrivalTime IS NULL")
    List<RouteWaypoint> findWaypointsNeedingAttention();
    
    /**
     * Get the maximum waypoint order for a route
     */
    @Query("SELECT MAX(rw.waypointOrder) FROM RouteWaypoint rw WHERE rw.route.id = :routeId")
    Optional<Integer> findMaxWaypointOrderForRoute(@Param("routeId") Long routeId);
    
    /**
     * Delete all waypoints for a specific route
     */
    void deleteByRouteId(Long routeId);
    
    /**
     * Count total waypoints for a route
     */
    long countByRouteId(Long routeId);
}
