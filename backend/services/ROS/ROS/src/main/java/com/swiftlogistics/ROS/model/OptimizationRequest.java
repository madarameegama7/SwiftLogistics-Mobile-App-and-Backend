package com.swiftlogistics.ROS.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class OptimizationRequest {
    
    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;
    
    @NotEmpty(message = "At least one delivery is required")
    private List<DeliveryRequest> deliveries;
    
    @NotNull(message = "Start location is required")
    private AddressRequest startLocation;
    
    private AddressRequest endLocation;
    
    private OptimizationConstraints constraints;
    
    // Default constructor
    public OptimizationRequest() {}
    
    // Getters and Setters
    public Long getVehicleId() {
        return vehicleId;
    }
    
    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public List<DeliveryRequest> getDeliveries() {
        return deliveries;
    }
    
    public void setDeliveries(List<DeliveryRequest> deliveries) {
        this.deliveries = deliveries;
    }
    
    public AddressRequest getStartLocation() {
        return startLocation;
    }
    
    public void setStartLocation(AddressRequest startLocation) {
        this.startLocation = startLocation;
    }
    
    public AddressRequest getEndLocation() {
        return endLocation;
    }
    
    public void setEndLocation(AddressRequest endLocation) {
        this.endLocation = endLocation;
    }
    
    public OptimizationConstraints getConstraints() {
        return constraints;
    }
    
    public void setConstraints(OptimizationConstraints constraints) {
        this.constraints = constraints;
    }
    
    // Inner class for delivery request
    public static class DeliveryRequest {
        @NotBlank(message = "Package ID is required")
        private String packageId;
        
        @NotNull(message = "Delivery address is required")
        private AddressRequest deliveryAddress;
        
        @NotBlank(message = "Recipient name is required")
        private String recipientName;
        
        private String recipientContact;
        private BigDecimal packageWeight;
        private BigDecimal packageVolume;
        private String packageType;
        private String priorityLevel;
        private String specialInstructions;
        
        // Getters and Setters
        public String getPackageId() { return packageId; }
        public void setPackageId(String packageId) { this.packageId = packageId; }
        
        public AddressRequest getDeliveryAddress() { return deliveryAddress; }
        public void setDeliveryAddress(AddressRequest deliveryAddress) { this.deliveryAddress = deliveryAddress; }
        
        public String getRecipientName() { return recipientName; }
        public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
        
        public String getRecipientContact() { return recipientContact; }
        public void setRecipientContact(String recipientContact) { this.recipientContact = recipientContact; }
        
        public BigDecimal getPackageWeight() { return packageWeight; }
        public void setPackageWeight(BigDecimal packageWeight) { this.packageWeight = packageWeight; }
        
        public BigDecimal getPackageVolume() { return packageVolume; }
        public void setPackageVolume(BigDecimal packageVolume) { this.packageVolume = packageVolume; }
        
        public String getPackageType() { return packageType; }
        public void setPackageType(String packageType) { this.packageType = packageType; }
        
        public String getPriorityLevel() { return priorityLevel; }
        public void setPriorityLevel(String priorityLevel) { this.priorityLevel = priorityLevel; }
        
        public String getSpecialInstructions() { return specialInstructions; }
        public void setSpecialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; }
    }
    
    // Inner class for address request
    public static class AddressRequest {
        @NotBlank(message = "Street address is required")
        private String streetAddress;
        
        @NotBlank(message = "City is required")
        private String city;
        
        private String state;
        private String postalCode;
        
        @NotBlank(message = "Country is required")
        private String country;
        
        private BigDecimal latitude;
        private BigDecimal longitude;
        
        // Getters and Setters
        public String getStreetAddress() { return streetAddress; }
        public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }
        
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        
        public String getPostalCode() { return postalCode; }
        public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
        
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        
        public BigDecimal getLatitude() { return latitude; }
        public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
        
        public BigDecimal getLongitude() { return longitude; }
        public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
    }
    
    // Inner class for optimization constraints
    public static class OptimizationConstraints {
        private String optimizationType = "DISTANCE"; // DISTANCE, TIME, FUEL_EFFICIENCY, PRIORITY
        private BigDecimal maxDistance;
        private Integer maxDuration; // in minutes
        private Boolean avoidTollRoads = false;
        private Boolean considerTraffic = false;
        
        // Getters and Setters
        public String getOptimizationType() { return optimizationType; }
        public void setOptimizationType(String optimizationType) { this.optimizationType = optimizationType; }
        
        public BigDecimal getMaxDistance() { return maxDistance; }
        public void setMaxDistance(BigDecimal maxDistance) { this.maxDistance = maxDistance; }
        
        public Integer getMaxDuration() { return maxDuration; }
        public void setMaxDuration(Integer maxDuration) { this.maxDuration = maxDuration; }
        
        public Boolean getAvoidTollRoads() { return avoidTollRoads; }
        public void setAvoidTollRoads(Boolean avoidTollRoads) { this.avoidTollRoads = avoidTollRoads; }
        
        public Boolean getConsiderTraffic() { return considerTraffic; }
        public void setConsiderTraffic(Boolean considerTraffic) { this.considerTraffic = considerTraffic; }
    }
}
