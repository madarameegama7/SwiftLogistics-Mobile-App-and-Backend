package com.swiftlogistics.ROS.algorithm;

import com.swiftlogistics.ROS.model.Address;
import com.swiftlogistics.ROS.model.Delivery;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Distance-based route optimization using Nearest Neighbor Algorithm.
 * This is a TEMPLATE implementation for Developer B to complete.
 * 
 * The Nearest Neighbor algorithm is a simple greedy algorithm that:
 * 1. Starts at the depot
 * 2. Always visits the nearest unvisited delivery location
 * 3. Repeats until all deliveries are visited
 * 
 * Time Complexity: O(n²) where n is number of deliveries
 * Result Quality: Good for most practical cases, though not optimal
 */
@Component
public class DistanceOptimizer {
    
    /**
     * Optimize delivery sequence using nearest neighbor algorithm
     * 
     * @param deliveries List of deliveries to optimize
     * @param startLocation Starting depot location
     * @return Optimized list of deliveries in visit order
     */
    public List<Delivery> optimizeByDistance(List<Delivery> deliveries, Address startLocation) {
        
        if (deliveries == null || deliveries.isEmpty()) {
            return new ArrayList<>();
        }
        
        // TODO for Developer B: Implement nearest neighbor algorithm
        
        List<Delivery> unvisited = new ArrayList<>(deliveries);
        List<Delivery> optimizedRoute = new ArrayList<>();
        Address currentLocation = startLocation;
        
        while (!unvisited.isEmpty()) {
            // Find nearest delivery to current location
            Delivery nearestDelivery = findNearestDelivery(currentLocation, unvisited);
            
            if (nearestDelivery != null) {
                // Add to route and remove from unvisited
                optimizedRoute.add(nearestDelivery);
                unvisited.remove(nearestDelivery);
                
                // Update current location
                currentLocation = nearestDelivery.getDeliveryAddress();
            } else {
                // Fallback: add first remaining delivery
                Delivery firstDelivery = unvisited.get(0);
                optimizedRoute.add(firstDelivery);
                unvisited.remove(firstDelivery);
                currentLocation = firstDelivery.getDeliveryAddress();
            }
        }
        
        return optimizedRoute;
    }
    
    /**
     * Find the nearest delivery to the current location
     * 
     * @param currentLocation Current position
     * @param availableDeliveries List of unvisited deliveries
     * @return Nearest delivery, or null if list is empty
     */
    private Delivery findNearestDelivery(Address currentLocation, List<Delivery> availableDeliveries) {
        
        if (availableDeliveries.isEmpty()) {
            return null;
        }
        
        Delivery nearestDelivery = null;
        BigDecimal shortestDistance = BigDecimal.valueOf(Double.MAX_VALUE);
        
        for (Delivery delivery : availableDeliveries) {
            BigDecimal distance = calculateDistance(currentLocation, delivery.getDeliveryAddress());
            
            if (distance.compareTo(shortestDistance) < 0) {
                shortestDistance = distance;
                nearestDelivery = delivery;
            }
        }
        
        return nearestDelivery;
    }
    
    /**
     * Calculate distance between two addresses using Haversine formula
     * TODO for Developer B: Implement proper Haversine formula
     * 
     * @param from Starting address
     * @param to Destination address
     * @return Distance in kilometers
     */
    private BigDecimal calculateDistance(Address from, Address to) {
        
        if (from == null || to == null || 
            from.getLatitude() == null || from.getLongitude() == null ||
            to.getLatitude() == null || to.getLongitude() == null) {
            return BigDecimal.valueOf(1000); // Large penalty for invalid addresses
        }
        
        // TODO: Developer B should implement Haversine formula here
        // This is a placeholder implementation
        
        double lat1 = from.getLatitude().doubleValue();
        double lon1 = from.getLongitude().doubleValue();
        double lat2 = to.getLatitude().doubleValue();
        double lon2 = to.getLongitude().doubleValue();
        
        // Simple Euclidean distance as placeholder (NOT accurate for GPS coordinates)
        // Developer B should replace with proper Haversine formula
        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;
        double distance = Math.sqrt(deltaLat * deltaLat + deltaLon * deltaLon) * 111; // ~111 km per degree
        
        return BigDecimal.valueOf(distance);
    }
    
    /**
     * Calculate total distance for a route
     * 
     * @param route List of deliveries in visit order
     * @param startLocation Starting depot location
     * @return Total distance in kilometers
     */
    public BigDecimal calculateTotalDistance(List<Delivery> route, Address startLocation) {
        
        if (route == null || route.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal totalDistance = BigDecimal.ZERO;
        Address currentLocation = startLocation;
        
        for (Delivery delivery : route) {
            BigDecimal segmentDistance = calculateDistance(currentLocation, delivery.getDeliveryAddress());
            totalDistance = totalDistance.add(segmentDistance);
            currentLocation = delivery.getDeliveryAddress();
        }
        
        // Optional: Add return distance to depot
        // totalDistance = totalDistance.add(calculateDistance(currentLocation, startLocation));
        
        return totalDistance;
    }
}

/* 
 * IMPLEMENTATION NOTES for Developer B:
 * 
 * 1. HAVERSINE FORMULA for accurate GPS distance calculation:
 *    a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
 *    c = 2 ⋅ atan2( √a, √(1−a) )  
 *    d = R ⋅ c
 *    where φ is latitude, λ is longitude, R is earth's radius (6371 km)
 * 
 * 2. OPTIMIZATION IMPROVEMENTS:
 *    - 2-opt algorithm: Improve routes by removing two edges and reconnecting
 *    - 3-opt algorithm: More complex but better results
 *    - Simulated Annealing: Accept worse solutions probabilistically to escape local optima
 *    - Genetic Algorithm: Population-based optimization
 * 
 * 3. PERFORMANCE OPTIMIZATIONS:
 *    - Cache distance calculations
 *    - Use distance matrix for large datasets
 *    - Implement geographic clustering for large delivery sets
 * 
 * 4. REAL-WORLD CONSIDERATIONS:
 *    - Traffic patterns (time-dependent distances)
 *    - Road network constraints (not straight-line distances)
 *    - Vehicle-specific routing (truck restrictions, fuel efficiency)
 *    - Time windows for deliveries
 */
