const amqp = require('amqplib');
const axios = require('axios');

// Adapters
const cmsAdapter = require('./adapters/cmsAdapter');
const rosAdapter = require('./adapters/rosAdapter');
const wmsAdapter = require('./adapters/wmsAdapter');

//config
const ORDER_SERVICE_URL = "http://localhost:8083/api/order/updateStatus";

//RabbitMQ config
const RABBITMQ_URL = "amqp://localhost";
const EXCHANGE = "order-exchange";
const QUEUE = "order-queue";
const ROUTING_KEY = "order-routing-key";

// main functions
async function listenForOrders() {
  try {
    const connection = await amqp.connect(RABBITMQ_URL);
    console.log("✅ Connected to RabbitMQ successfully");

    const channel = await connection.createChannel();
    await channel.assertExchange(EXCHANGE, "direct", { durable: true });
    await channel.assertQueue(QUEUE, { durable: true });
    await channel.bindQueue(QUEUE, EXCHANGE, ROUTING_KEY);

    console.log("✅ Orchestrator listening for new orders...");

    channel.consume(QUEUE, async (msg) => {
      if (!msg) return;

      let event;
      try {
        event = JSON.parse(msg.content.toString());
      } catch (err) {
        console.error("❌ Received invalid JSON message:", msg.content.toString());
        channel.nack(msg, false, false);
        return;
      }

      console.log("📩 Received Order Event:", event);

      try {
        await processOrder(event);
      } catch (err) {
        console.error("❌ Error processing order:", err);
      }

      channel.ack(msg);
    });

  } catch (err) {
    console.error("❌ RabbitMQ connection error:", err);
  }
}

//Process Order 
async function processOrder(event) {
  const orderId = event.orderId;
  const address = "123 Main Street";

  // first CMS
  try {
    const cmsRes = await cmsAdapter.createOrder(orderId, "Mock Details");
    console.log("📦 CMS Response:", cmsRes);
    await updateOrderStatus(orderId, "PROCESSED_BY_CMS");
  } catch (err) {
    console.error("❌ CMS Error:", err);
    return;
  }

  // then ROS
  try {
    const rosRes = await rosAdapter.optimizeRoute(orderId, address, ["Van1"]);
    console.log("🗺️ ROS Response:", rosRes);
    await updateOrderStatus(orderId, "ROUTE_OPTIMIZED");
  } catch (err) {
    console.error("❌ ROS Error:", err);
    return;
  }

  // last WMS
  try {
    const wmsRes = await wmsAdapter.sendOrder(orderId);
    console.log("🏭 WMS Response:", wmsRes);
    await updateOrderStatus(orderId, "PACKAGE_RECEIVED_WMS");
  } catch (err) {
    console.error("❌ WMS Error:", err);
  }
}

// Update Order Status
async function updateOrderStatus(orderId, status) {
  try {
    await axios.put(`${ORDER_SERVICE_URL}/${orderId}`, { status });
    console.log(`✅ Order ${orderId} updated to ${status}`);
  } catch (err) {
    console.error("❌ Failed to update order status:", err.message);
  }
}
//start listening
listenForOrders();
