const soap = require('soap');
const axios = require('axios');
const net = require('net');
const amqp = require('amqplib');

// ---- CONFIG ----
const CMS_WSDL = 'http://localhost:3001/cms?wsdl';
const ROS_URL = 'http://localhost:3002/optimizeRoute';
const WMS_HOST = 'localhost';
const WMS_PORT = 3003;
const ORDER_SERVICE_URL = 'http://localhost:8080/api/order/updateStatus';

// ---- RabbitMQ Config ----
const RABBITMQ_URL = 'amqp://localhost';
const EXCHANGE = 'order-exchange';
const QUEUE = 'order-queue';
const ROUTING_KEY = 'order-routing-key';

// ---- Main ----
async function listenForOrders() {
    try {
        const connection = await amqp.connect(RABBITMQ_URL);
        const channel = await connection.createChannel();

        await channel.assertExchange(EXCHANGE, 'direct', { durable: true });
        await channel.assertQueue(QUEUE, { durable: true });
        await channel.bindQueue(QUEUE, EXCHANGE, ROUTING_KEY);

        console.log("✅ Orchestrator listening for new orders...");

        channel.consume(QUEUE, async (msg) => {
            if (msg !== null) {
                const event = JSON.parse(msg.content.toString());
                console.log("📩 Received Order Event:", event);

                try {
                    await processOrder(event);
                } catch (err) {
                    console.error("❌ Error processing order:", err);
                }

                channel.ack(msg);
            }
        });
    } catch (err) {
        console.error("❌ RabbitMQ connection error:", err);
    }
}

// ---- Order Processing ----
async function processOrder(event) {
    const orderId = event.orderId;
    const address = "123 Main Street"; // TODO: extend event payload to carry real address

    // ---- 1. Call CMS ----
    try {
        const client = await soap.createClientAsync(CMS_WSDL);
        const cmsRes = await client.createOrderAsync({
            clientId: orderId,
            orderDetails: "Mock Details"
        });
        console.log("📦 CMS Response:", cmsRes[0]);
        await updateOrderStatus(orderId, "PROCESSED_BY_CMS");
    } catch (err) {
        console.error("❌ CMS Error:", err);
        return;
    }

    // ---- 2. Call ROS ----
    try {
        const rosRes = await axios.post(ROS_URL, {
            orders: [{ orderId, address }],
            vehicles: ["Van1"]
        });
        console.log("🗺️  ROS Response:", rosRes.data);
        await updateOrderStatus(orderId, "ROUTE_OPTIMIZED");
    } catch (err) {
        console.error("❌ ROS Error:", err);
        return;
    }

    // ---- 3. Call WMS ----
    try {
        await new Promise((resolve) => {
            const socket = new net.Socket();
            socket.connect(WMS_PORT, WMS_HOST, () => {
                const msg = `ORDER:${orderId}:RECEIVED`;
                console.log("🚚 Sending to WMS:", msg);
                socket.write(msg);
            });

            socket.on('data', async (data) => {
                console.log("🏭 WMS Response:", data.toString());
                await updateOrderStatus(orderId, "PACKAGE_RECEIVED_WMS");
                socket.destroy();
                resolve();
            });

            socket.on('error', (err) => {
                console.error("❌ WMS Connection Error:", err);
                resolve();
            });
        });
    } catch (err) {
        console.error("❌ WMS Error:", err);
    }
}

// ---- Update Order Service ----
async function updateOrderStatus(orderId, status) {
    try {
        await axios.put(`${ORDER_SERVICE_URL}/${orderId}`, { status });
        console.log(`✅ Order ${orderId} updated to ${status}`);
    } catch (err) {
        console.error("❌ Failed to update order status:", err.message);
    }
}

// ---- Start Listener ----
listenForOrders();
