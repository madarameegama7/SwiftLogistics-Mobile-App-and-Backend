package com.swiftlogistics.orchestrator_service.orchestrator.events;

import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import com.swiftlogistics.orchestrator_service.orchestrator.config.RabbitMQConfig;
import com.swiftlogistics.orchestrator_service.orchestrator.events.OrderCreatedEvent;

@Service
public class OrderEventListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("Orchestrator received: " + event);

        // Step 1: Call CMS Adapter (SOAP)
        callCMSAdapter(event);

        // Step 2: Call WMS Adapter (TCP)
        callWMSAdapter(event);

        // Step 3: Call ROS Adapter (REST)
        callROSAdapter(event);

        // Step 4: Publish OrderReadyEvent (optional)
        System.out.println("Order processed: " + event.getOrderId());
    }

    private void callCMSAdapter(OrderCreatedEvent event) {
        System.out.println("Calling CMS Adapter for order: " + event.getOrderId());
    }

    private void callWMSAdapter(OrderCreatedEvent event) {
        System.out.println("Calling WMS Adapter for order: " + event.getOrderId());
    }

    private void callROSAdapter(OrderCreatedEvent event) {
        System.out.println("Calling ROS Adapter for order: " + event.getOrderId());
    }
}
