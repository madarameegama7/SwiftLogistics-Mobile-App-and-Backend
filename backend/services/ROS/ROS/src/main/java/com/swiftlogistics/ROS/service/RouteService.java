package com.swiftlogistics.ROS.service;

import com.swiftlogistics.ROS.model.Route;
import com.swiftlogistics.ROS.model.RouteWaypoint;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Route management operations.
 * Handles CRUD operations and business logic for routes.
 * Developer B will implement this interface.
 */
public interface RouteService {
    
    /**
     * Create a new route
     */
    Route createRoute(Route route);
    
    /**
     * Get route by ID
     */
    Optional<Route> getRouteById(Long routeId);
    
    /**
     * Get route by name
     */
    Optional<Route> getRouteByName(String routeName);
    
    /**
     * Update an existing route
     */
    Route updateRoute(Long routeId, Route route);
    
    /**
     * Delete a route (soft delete - mark as cancelled)
     */
    void deleteRoute(Long routeId);
    
    /**
     * Get all routes with pagination support
     */
    List<Route> getAllRoutes(int page, int size);
    
    /**
     * Get routes by status
     */
    List<Route> getRoutesByStatus(Route.RouteStatus status);
    
    /**
     * Get routes assigned to a specific vehicle
     */
    List<Route> getRoutesByVehicle(Long vehicleId);
    
    /**
     * Start a route (change status to IN_PROGRESS)
     */
    Route startRoute(Long routeId);
    
    /**
     * Complete a route (change status to COMPLETED)
     */
    Route completeRoute(Long routeId);
    
    /**
     * Cancel a route
     */
    Route cancelRoute(Long routeId, String reason);
    
    /**
     * Add waypoints to a route
     */
    Route addWaypointsToRoute(Long routeId, List<RouteWaypoint> waypoints);
    
    /**
     * Remove waypoint from route
     */
    Route removeWaypointFromRoute(Long routeId, Long waypointId);
    
    /**
     * Update waypoint sequence in route
     */
    Route updateWaypointSequence(Long routeId, Long waypointId, Integer newSequence);
    
    /**
     * Get route progress (percentage completed)
     */
    Double getRouteProgress(Long routeId);
    
    /**
     * Get estimated completion time for route
     */
    LocalDateTime getEstimatedCompletionTime(Long routeId);
    
    /**
     * Check if route is overdue
     */
    boolean isRouteOverdue(Long routeId);
    
    /**
     * Get routes scheduled for today
     */
    List<Route> getTodaysRoutes();
    
    /**
     * Get active routes (in progress)
     */
    List<Route> getActiveRoutes();
    
    /**
     * Get route summary statistics
     */
    RouteStatistics getRouteStatistics();
    
    /**
     * Clone a route (create copy with new name)
     */
    Route cloneRoute(Long routeId, String newRouteName);
    
    /**
     * Inner class for route statistics
     */
    class RouteStatistics {
        private Long totalRoutes;
        private Long activeRoutes;
        private Long completedRoutes;
        private Long cancelledRoutes;
        private Double averageDistance;
        private Double averageDuration;
        
        // Constructors
        public RouteStatistics() {}
        
        public RouteStatistics(Long totalRoutes, Long activeRoutes, Long completedRoutes, 
                             Long cancelledRoutes, Double averageDistance, Double averageDuration) {
            this.totalRoutes = totalRoutes;
            this.activeRoutes = activeRoutes;
            this.completedRoutes = completedRoutes;
            this.cancelledRoutes = cancelledRoutes;
            this.averageDistance = averageDistance;
            this.averageDuration = averageDuration;
        }
        
        // Getters and Setters
        public Long getTotalRoutes() { return totalRoutes; }
        public void setTotalRoutes(Long totalRoutes) { this.totalRoutes = totalRoutes; }
        
        public Long getActiveRoutes() { return activeRoutes; }
        public void setActiveRoutes(Long activeRoutes) { this.activeRoutes = activeRoutes; }
        
        public Long getCompletedRoutes() { return completedRoutes; }
        public void setCompletedRoutes(Long completedRoutes) { this.completedRoutes = completedRoutes; }
        
        public Long getCancelledRoutes() { return cancelledRoutes; }
        public void setCancelledRoutes(Long cancelledRoutes) { this.cancelledRoutes = cancelledRoutes; }
        
        public Double getAverageDistance() { return averageDistance; }
        public void setAverageDistance(Double averageDistance) { this.averageDistance = averageDistance; }
        
        public Double getAverageDuration() { return averageDuration; }
        public void setAverageDuration(Double averageDuration) { this.averageDuration = averageDuration; }
    }
}
