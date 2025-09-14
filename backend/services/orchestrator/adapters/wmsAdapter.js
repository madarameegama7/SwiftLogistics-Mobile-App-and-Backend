const net = require('net');
const WMS_HOST = "localhost";
const WMS_PORT = 3003;

class WMSAdapter {
  async sendOrder(orderId) {
    return new Promise((resolve, reject) => {
      const socket = new net.Socket();
      socket.connect(WMS_PORT, WMS_HOST, () => {
        const msg = `ORDER:${orderId}:RECEIVED`;
        socket.write(msg);
      });

      socket.on('data', (data) => {
        resolve(data.toString());
        socket.destroy();
      });

      socket.on('error', (err) => reject(err));
    });
  }
}

module.exports = new WMSAdapter();
