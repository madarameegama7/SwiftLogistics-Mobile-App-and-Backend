const net = require('net');
const PORT = 3003;

const server = net.createServer(socket => {
    console.log('WMS client connected');

    socket.on('data', data => {
        const msg = data.toString();
        console.log('Received:', msg);
        // Send an acknowledgment
        socket.write(`ACK:${msg}`);
    });

    socket.on('close', () => console.log('Client disconnected'));
});

server.listen(PORT, () => console.log(`WMS TCP mock running on port ${PORT}`));
