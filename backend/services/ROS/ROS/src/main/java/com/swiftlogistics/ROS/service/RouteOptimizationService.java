package com.swiftlogistics.ROS.service;

import com.swiftlogistics.ROS.model.OptimizationRequest;
import com.swiftlogistics.ROS.model.OptimizationResponse;
import com.swiftlogistics.ROS.model.Address;
import com.swiftlogistics.ROS.model.Delivery;
import com.swiftlogistics.ROS.model.Route;
import com.swiftlogistics.ROS.model.Vehicle;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for Route Optimization operations.
 * This is the core service that handles route optimization algorithms and logic.
 * Developer B will implement this interface with actual optimization algorithms.
 * 
 * PRIORITY: CRITICAL - This is the main business logic for the ROS service
 */
public interface RouteOptimizationService {
    
    /**
     * Main optimization method - optimizes routes for given deliveries and vehicles
     * This is the primary method that Developer B needs to implement.
     * 
     * @param request Optimization request containing deliveries, vehicles, and preferences
     * @return Optimized routes with waypoints and statistics
     */
    OptimizationResponse optimizeDeliveryRoutes(OptimizationRequest request);
    
    /**
     * Optimize a single route by distance (shortest path algorithm)
     * Implements nearest neighbor or similar distance-minimizing algorithm
     * 
     * @param deliveries List of deliveries to optimize
     * @param vehicle Vehicle to use for the route
     * @param startLocation Starting location (depot/warehouse)
     * @return Optimized route with minimal total distance
     */
    Route optimizeByDistance(List<Delivery> deliveries, Vehicle vehicle, Address startLocation);
    
    /**
     * Optimize a single route by time (fastest delivery)
     * Considers traffic patterns, delivery time windows, etc.
     * 
     * @param deliveries List of deliveries to optimize
     * @param vehicle Vehicle to use for the route
     * @param startLocation Starting location (depot/warehouse)
     * @return Optimized route with minimal total time
     */
    Route optimizeByTime(List<Delivery> deliveries, Vehicle vehicle, Address startLocation);
    
    /**
     * Optimize a single route by priority (high priority deliveries first)
     * Sorts deliveries by priority level and optimizes within priority groups
     * 
     * @param deliveries List of deliveries to optimize
     * @param vehicle Vehicle to use for the route
     * @param startLocation Starting location (depot/warehouse)
     * @return Optimized route prioritizing urgent deliveries
     */
    Route optimizeByPriority(List<Delivery> deliveries, Vehicle vehicle, Address startLocation);
    
    /**
     * Calculate the total distance for a route using Haversine formula
     * 
     * @param waypoints List of addresses in route order
     * @return Total distance in kilometers
     */
    BigDecimal calculateRouteDistance(List<Address> waypoints);
    
    /**
     * Calculate estimated delivery time for a route
     * Considers distance, traffic, delivery time per stop
     * 
     * @param route Route to calculate time for
     * @return Estimated time in minutes
     */
    Integer calculateEstimatedTime(Route route);
    
    /**
     * Check if deliveries fit within vehicle capacity constraints
     * Validates both weight and volume constraints
     * 
     * @param deliveries List of deliveries to check
     * @param vehicle Vehicle to check against
     * @return True if all deliveries fit, false otherwise
     */
    boolean validateVehicleCapacity(List<Delivery> deliveries, Vehicle vehicle);
    
    /**
     * Split deliveries across multiple vehicles if needed
     * Implements bin packing or similar algorithms for multi-vehicle optimization
     * 
     * @param deliveries List of all deliveries
     * @param vehicles List of available vehicles
     * @param depot Starting depot location
     * @return Optimized routes for multiple vehicles
     */
    OptimizationResponse optimizeMultiVehicleRoutes(List<Delivery> deliveries, List<Vehicle> vehicles, Address depot);
    
    /**
     * Re-optimize an existing route with new parameters or deliveries.
     * Useful when route conditions change or new deliveries are added.
     * 
     * @param routeId The ID of the existing route to re-optimize
     * @param additionalDeliveries Additional deliveries to include in the route
     * @return OptimizationResponse with the updated optimized route
     */
    OptimizationResponse reoptimizeRoute(Long routeId, List<Long> additionalDeliveries);
    
    /**
     * Calculate route efficiency score.
     * Higher score indicates better efficiency (distance per delivery, time utilization, etc.)
     * 
     * @param routeId The route ID to calculate efficiency for
     * @return Efficiency score as a decimal (0.0 to 1.0, where 1.0 is most efficient)
     */
    Double calculateRouteEfficiency(Long routeId);
    
    /**
     * Find the best vehicle for a given set of deliveries.
     * Considers capacity, fuel efficiency, and availability.
     * 
     * @param deliveries List of deliveries to transport
     * @return Vehicle ID of the most suitable vehicle, null if none suitable
     */
    Long findOptimalVehicle(List<Delivery> deliveries);
    
    /**
     * Get optimization suggestions for improving an existing route.
     * Provides recommendations for better efficiency.
     * 
     * @param routeId The route ID to analyze
     * @return List of optimization suggestions as strings
     */
    List<String> getOptimizationSuggestions(Long routeId);
}
