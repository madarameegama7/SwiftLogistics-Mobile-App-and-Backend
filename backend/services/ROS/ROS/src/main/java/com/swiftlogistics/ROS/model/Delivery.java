package com.swiftlogistics.ROS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
public class Delivery {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Package ID is required")
    @Column(name = "package_id", nullable = false, unique = true, length = 50)
    private String packageId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;
    
    @NotNull(message = "Delivery address is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_id", nullable = false)
    private Address deliveryAddress;
    
    @NotBlank(message = "Recipient name is required")
    @Column(name = "recipient_name", nullable = false, length = 100)
    private String recipientName;
    
    @Column(name = "recipient_contact", length = 20)
    private String recipientContact;
    
    @Column(name = "package_weight", precision = 10, scale = 2)
    private BigDecimal packageWeight;
    
    @Column(name = "package_volume", precision = 10, scale = 2)
    private BigDecimal packageVolume;
    
    @Column(name = "package_type", length = 50)
    private String packageType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "priority_level", length = 20)
    private PriorityLevel priorityLevel = PriorityLevel.NORMAL;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status", length = 20)
    private DeliveryStatus deliveryStatus = DeliveryStatus.PENDING;
    
    @Column(name = "scheduled_time")
    private LocalDateTime scheduledTime;
    
    @Column(name = "actual_delivery_time")
    private LocalDateTime actualDeliveryTime;
    
    @Column(name = "delivery_sequence")
    private Integer deliverySequence;
    
    @Column(name = "special_instructions", columnDefinition = "TEXT")
    private String specialInstructions;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Enums
    public enum PriorityLevel {
        LOW, NORMAL, HIGH, URGENT
    }
    
    public enum DeliveryStatus {
        PENDING, ASSIGNED, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED, FAILED, RETURNED
    }
    
    // Default constructor
    public Delivery() {}
    
    // Constructor
    public Delivery(String packageId, Address deliveryAddress, String recipientName) {
        this.packageId = packageId;
        this.deliveryAddress = deliveryAddress;
        this.recipientName = recipientName;
    }
    
    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPackageId() {
        return packageId;
    }
    
    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
    
    public Route getRoute() {
        return route;
    }
    
    public void setRoute(Route route) {
        this.route = route;
    }
    
    public Address getDeliveryAddress() {
        return deliveryAddress;
    }
    
    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    
    public String getRecipientName() {
        return recipientName;
    }
    
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
    
    public String getRecipientContact() {
        return recipientContact;
    }
    
    public void setRecipientContact(String recipientContact) {
        this.recipientContact = recipientContact;
    }
    
    public BigDecimal getPackageWeight() {
        return packageWeight;
    }
    
    public void setPackageWeight(BigDecimal packageWeight) {
        this.packageWeight = packageWeight;
    }
    
    public BigDecimal getPackageVolume() {
        return packageVolume;
    }
    
    public void setPackageVolume(BigDecimal packageVolume) {
        this.packageVolume = packageVolume;
    }
    
    public String getPackageType() {
        return packageType;
    }
    
    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }
    
    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }
    
    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
    
    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }
    
    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
    
    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }
    
    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
    
    public LocalDateTime getActualDeliveryTime() {
        return actualDeliveryTime;
    }
    
    public void setActualDeliveryTime(LocalDateTime actualDeliveryTime) {
        this.actualDeliveryTime = actualDeliveryTime;
    }
    
    public Integer getDeliverySequence() {
        return deliverySequence;
    }
    
    public void setDeliverySequence(Integer deliverySequence) {
        this.deliverySequence = deliverySequence;
    }
    
    public String getSpecialInstructions() {
        return specialInstructions;
    }
    
    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Utility methods
    public boolean isDelivered() {
        return deliveryStatus == DeliveryStatus.DELIVERED;
    }
    
    public boolean isPending() {
        return deliveryStatus == DeliveryStatus.PENDING;
    }
    
    public boolean isHighPriority() {
        return priorityLevel == PriorityLevel.HIGH || priorityLevel == PriorityLevel.URGENT;
    }
    
    public boolean canBeDelivered() {
        return deliveryStatus == DeliveryStatus.OUT_FOR_DELIVERY;
    }
    
    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", packageId='" + packageId + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", deliveryStatus=" + deliveryStatus +
                ", priorityLevel=" + priorityLevel +
                '}';
    }
}
