package com.swiftlogistics.order_service.enums;

public enum OrderStatus {
    CREATED,
    PROCESSED_BY_CMS,
    ROUTE_OPTIMIZED,
    PACKAGE_RECEIVED_WMS,
    DELIVERED,
    FAILED
}
