const axios = require('axios');
const ROS_URL = "http://localhost:3002/optimizeRoute";

class ROSAdapter {
  async optimizeRoute(orderId, address, vehicles) {
    const res = await axios.post(ROS_URL, {
      orders: [{ orderId, address }],
      vehicles
    });
    return res.data; // standardized route response
  }
}

module.exports = new ROSAdapter();
