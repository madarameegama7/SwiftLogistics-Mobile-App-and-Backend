const soap = require('soap');
const CMS_WSDL = "http://localhost:3001/cms?wsdl";

class CMSAdapter {
  async createOrder(orderId, details) {
    const client = await soap.createClientAsync(CMS_WSDL);
    const res = await client.createOrderAsync({ clientId: orderId, orderDetails: details });
    return res[0];
  }

  async getClientInfo(clientId) {
    const client = await soap.createClientAsync(CMS_WSDL);
    const res = await client.getClientInfoAsync({ clientId });
    return res[0];
  }
}

module.exports = new CMSAdapter();
