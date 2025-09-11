package com.swiftlogistics.ROS.repository;

import com.swiftlogistics.ROS.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Address entity operations.
 * Provides standard CRUD operations and custom query methods for address management.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    
    /**
     * Find address by exact street address and city combination
     */
    Optional<Address> findByStreetAddressAndCity(String streetAddress, String city);
    
    /**
     * Find all addresses in a specific city
     */
    List<Address> findByCity(String city);
    
    /**
     * Find all addresses in a specific state
     */
    List<Address> findByState(String state);
    
    /**
     * Find all addresses by postal code
     */
    List<Address> findByPostalCode(String postalCode);
    
    /**
     * Find all addresses within a geographical bounding box
     * This is useful for route optimization within specific areas
     */
    @Query("SELECT a FROM Address a WHERE " +
           "a.latitude BETWEEN :minLat AND :maxLat AND " +
           "a.longitude BETWEEN :minLng AND :maxLng")
    List<Address> findAddressesInBoundingBox(
        @Param("minLat") BigDecimal minLatitude,
        @Param("maxLat") BigDecimal maxLatitude,
        @Param("minLng") BigDecimal minLongitude,
        @Param("maxLng") BigDecimal maxLongitude
    );
    
    /**
     * Find addresses near a specific coordinate point within a given radius
     * Uses the Haversine formula for distance calculation
     */
    @Query(value = "SELECT a.* FROM addresses a WHERE " +
           "(6371 * acos(cos(radians(:latitude)) * cos(radians(a.latitude)) * " +
           "cos(radians(a.longitude) - radians(:longitude)) + " +
           "sin(radians(:latitude)) * sin(radians(a.latitude)))) <= :radiusKm",
           nativeQuery = true)
    List<Address> findAddressesWithinRadius(
        @Param("latitude") BigDecimal latitude,
        @Param("longitude") BigDecimal longitude,
        @Param("radiusKm") Double radiusKm
    );
    
    /**
     * Search addresses by partial street address match (case-insensitive)
     */
    @Query("SELECT a FROM Address a WHERE LOWER(a.streetAddress) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Address> findByStreetAddressContaining(@Param("searchTerm") String searchTerm);
    
    /**
     * Find all unique cities in the database
     */
    @Query("SELECT DISTINCT a.city FROM Address a ORDER BY a.city")
    List<String> findAllUniqueCities();
    
    /**
     * Find all unique states in the database
     */
    @Query("SELECT DISTINCT a.state FROM Address a ORDER BY a.state")
    List<String> findAllUniqueStates();
    
    /**
     * Count addresses by city
     */
    @Query("SELECT COUNT(a) FROM Address a WHERE a.city = :city")
    Long countAddressesByCity(@Param("city") String city);
    
    /**
     * Check if coordinates already exist in the database
     * Useful to prevent duplicate location entries
     */
    boolean existsByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude);
    
    /**
     * Find the closest address to given coordinates
     * Returns the single nearest address
     */
    @Query(value = "SELECT a.* FROM addresses a " +
           "ORDER BY (6371 * acos(cos(radians(:latitude)) * cos(radians(a.latitude)) * " +
           "cos(radians(a.longitude) - radians(:longitude)) + " +
           "sin(radians(:latitude)) * sin(radians(a.latitude)))) " +
           "LIMIT 1",
           nativeQuery = true)
    Optional<Address> findClosestAddress(
        @Param("latitude") BigDecimal latitude,
        @Param("longitude") BigDecimal longitude
    );
}
