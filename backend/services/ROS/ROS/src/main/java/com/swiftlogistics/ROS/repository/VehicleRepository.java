package com.swiftlogistics.ROS.repository;

import com.swiftlogistics.ROS.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Vehicle entity operations.
 * Provides standard CRUD operations and custom query methods for vehicle management.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    
    /**
     * Find vehicle by vehicle number (license plate)
     */
    Optional<Vehicle> findByVehicleNumber(String vehicleNumber);
    
    /**
     * Find all vehicles by status
     */
    List<Vehicle> findByStatus(Vehicle.VehicleStatus status);
    
    /**
     * Find all available vehicles for assignment
     */
    @Query("SELECT v FROM Vehicle v WHERE v.status = 'AVAILABLE'")
    List<Vehicle> findAvailableVehicles();
    
    /**
     * Find vehicles by driver name
     */
    List<Vehicle> findByDriverNameContainingIgnoreCase(String driverName);
    
    /**
     * Find vehicles with capacity greater than or equal to specified weight
     */
    @Query("SELECT v FROM Vehicle v WHERE v.capacityWeight >= :requiredWeight AND v.status = 'AVAILABLE'")
    List<Vehicle> findVehiclesWithSufficientWeightCapacity(@Param("requiredWeight") BigDecimal requiredWeight);
    
    /**
     * Find vehicles with volume capacity greater than or equal to specified volume
     */
    @Query("SELECT v FROM Vehicle v WHERE v.capacityVolume >= :requiredVolume AND v.status = 'AVAILABLE'")
    List<Vehicle> findVehiclesWithSufficientVolumeCapacity(@Param("requiredVolume") BigDecimal requiredVolume);
    
    /**
     * Find vehicles that can handle both weight and volume requirements
     */
    @Query("SELECT v FROM Vehicle v WHERE v.capacityWeight >= :requiredWeight AND " +
           "v.capacityVolume >= :requiredVolume AND v.status = 'AVAILABLE'")
    List<Vehicle> findVehiclesWithSufficientCapacity(
        @Param("requiredWeight") BigDecimal requiredWeight,
        @Param("requiredVolume") BigDecimal requiredVolume
    );
    
    /**
     * Find vehicles by type
     */
    List<Vehicle> findByVehicleType(String vehicleType);
    
    /**
     * Count vehicles by status
     */
    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.status = :status")
    Long countByStatus(@Param("status") Vehicle.VehicleStatus status);
    
    /**
     * Find vehicles currently on delivery (status = IN_TRANSIT)
     */
    @Query("SELECT v FROM Vehicle v WHERE v.status = 'IN_TRANSIT'")
    List<Vehicle> findVehiclesInTransit();
    
    /**
     * Find vehicles under maintenance
     */
    @Query("SELECT v FROM Vehicle v WHERE v.status = 'MAINTENANCE'")
    List<Vehicle> findVehiclesUnderMaintenance();
    
    /**
     * Check if vehicle number already exists
     */
    boolean existsByVehicleNumber(String vehicleNumber);
    
    /**
     * Find vehicles with highest capacity (for optimization purposes)
     */
    @Query("SELECT v FROM Vehicle v WHERE v.status = 'AVAILABLE' ORDER BY v.capacityWeight DESC, v.capacityVolume DESC")
    List<Vehicle> findAvailableVehiclesOrderedByCapacity();
    
    /**
     * Get total fleet capacity statistics
     */
    @Query("SELECT SUM(v.capacityWeight) as totalWeight, SUM(v.capacityVolume) as totalVolume, COUNT(v) as totalVehicles " +
           "FROM Vehicle v WHERE v.status != 'OUT_OF_SERVICE'")
    Object[] getFleetCapacityStatistics();
    
    /**
     * Find vehicles by driver contact information
     */
    Optional<Vehicle> findByDriverContact(String driverContact);
    
    /**
     * Get vehicle utilization summary
     */
    @Query("SELECT v.status, COUNT(v) FROM Vehicle v GROUP BY v.status")
    List<Object[]> getVehicleStatusSummary();
}
