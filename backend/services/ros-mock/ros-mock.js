const express = require('express');
const app = express();
app.use(express.json());
const PORT = 3002;

app.post('/optimizeRoute', (req, res) => {
    console.log("Received request at ROS:", req.body);
    const { orders } = req.body;
    const dummyRoute = orders.map((order, index) => ({
        orderId: order.orderId,
        address: order.address,
        estimatedTime: `${10 + index*5} mins`
    }));
    res.json({ routeId: Math.floor(Math.random()*1000), route: dummyRoute });
});


app.listen(PORT, () => console.log(`ROS REST mock running on port ${PORT}`));
