const soap = require('soap');
const express = require('express');
const fs = require('fs');
const app = express();
const PORT = 3001;

const service = {
  CMSService: {
    CMSPort: {
      createOrder: function(args) {
        console.log("Order received:", args);
        return { status: "OrderCreated", orderId: Math.floor(Math.random() * 1000) };
      },
      getClientInfo: function(args) {
        console.log("Client info requested:", args);
        return { clientName: "Client", clientId: args.clientId };
      }
    }
  }
};

const wsdlXml = fs.readFileSync('./cms.wsdl', 'utf8');

app.listen(PORT, () => {
  soap.listen(app, '/cms', service, wsdlXml, () => {
    console.log(`CMS SOAP mock running on http://localhost:${PORT}/cms?wsdl`);
  });
});
