import 'package:flutter/material.dart';
import 'dart:async';

class RouteNavigationPage extends StatefulWidget {
  const RouteNavigationPage({super.key});

  @override
  State<RouteNavigationPage> createState() => _RouteNavigationPageState();
}

class _RouteNavigationPageState extends State<RouteNavigationPage> {
  bool isNavigationActive = false;
  int currentStopIndex = 0;
  Timer? _timer;
  Duration elapsedTime = Duration.zero;

  final List<RouteStop> routeStops = [
    RouteStop(
      id: 'STOP-001',
      packageId: 'PKG-001',
      recipientName: 'John Doe',
      address: '123 Main St, Colombo 03',
      estimatedTime: '09:30 AM',
      distance: '2.3 km',
      isCompleted: false,
    ),
    RouteStop(
      id: 'STOP-002',
      packageId: 'PKG-002',
      recipientName: 'Jane Smith',
      address: '456 Galle Road, Colombo 04',
      estimatedTime: '10:15 AM',
      distance: '1.8 km',
      isCompleted: false,
    ),
    RouteStop(
      id: 'STOP-003',
      packageId: 'PKG-004',
      recipientName: 'Sarah Wilson',
      address: '321 Nugegoda Main Road',
      estimatedTime: '11:45 AM',
      distance: '3.2 km',
      isCompleted: false,
    ),
    RouteStop(
      id: 'STOP-004',
      packageId: 'PKG-005',
      recipientName: 'David Brown',
      address: '654 Dehiwala Junction',
      estimatedTime: '12:30 PM',
      distance: '2.7 km',
      isCompleted: false,
    ),
  ];

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    _timer?.cancel();
    super.dispose();
  }

  void _startNavigation() {
    setState(() {
      isNavigationActive = true;
      elapsedTime = Duration.zero;
    });

    _timer = Timer.periodic(const Duration(seconds: 1), (timer) {
      setState(() {
        elapsedTime = Duration(seconds: elapsedTime.inSeconds + 1);
      });
    });
  }

  void _stopNavigation() {
    setState(() {
      isNavigationActive = false;
    });
    _timer?.cancel();
  }

  void _completeCurrentStop() {
    if (currentStopIndex < routeStops.length) {
      setState(() {
        routeStops[currentStopIndex].isCompleted = true;
        if (currentStopIndex < routeStops.length - 1) {
          currentStopIndex++;
        }
      });

      Navigator.pushNamed(
        context,
        '/package-delivery',
        arguments: routeStops[currentStopIndex],
      );
    }
  }

  String _formatDuration(Duration duration) {
    String twoDigits(int n) => n.toString().padLeft(2, "0");
    String twoDigitMinutes = twoDigits(duration.inMinutes.remainder(60));
    String twoDigitSeconds = twoDigits(duration.inSeconds.remainder(60));
    return "${twoDigits(duration.inHours)}:$twoDigitMinutes:$twoDigitSeconds";
  }

  @override
  Widget build(BuildContext context) {
    final currentStop = currentStopIndex < routeStops.length 
        ? routeStops[currentStopIndex] 
        : null;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Route Navigation'),
        backgroundColor: Colors.blue.shade600,
        foregroundColor: Colors.white,
        actions: [
          IconButton(
            onPressed: () => Navigator.pushNamed(context, '/delivery-manifest'),
            icon: const Icon(Icons.list_alt),
          ),
        ],
      ),
      body: Column(
        children: [
          // Navigation Status Card
          Container(
            width: double.infinity,
            margin: const EdgeInsets.all(16),
            padding: const EdgeInsets.all(20),
            decoration: BoxDecoration(
              gradient: LinearGradient(
                colors: isNavigationActive
                    ? [Colors.green.shade600, Colors.green.shade800]
                    : [Colors.grey.shade600, Colors.grey.shade800],
                begin: Alignment.topLeft,
                end: Alignment.bottomRight,
              ),
              borderRadius: BorderRadius.circular(16),
            ),
            child: Column(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          isNavigationActive ? 'Navigation Active' : 'Navigation Inactive',
                          style: const TextStyle(
                            color: Colors.white,
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        Text(
                          'Elapsed Time: ${_formatDuration(elapsedTime)}',
                          style: const TextStyle(
                            color: Colors.white70,
                            fontSize: 14,
                          ),
                        ),
                      ],
                    ),
                    Container(
                      width: 12,
                      height: 12,
                      decoration: BoxDecoration(
                        color: isNavigationActive ? Colors.green : Colors.red,
                        shape: BoxShape.circle,
                      ),
                    ),
                  ],
                ),
                if (currentStop != null) ...[
                  const SizedBox(height: 16),
                  const Divider(color: Colors.white30),
                  const SizedBox(height: 16),
                  Row(
                    children: [
                      const Icon(Icons.location_on, color: Colors.white, size: 20),
                      const SizedBox(width: 8),
                      Expanded(
                        child: Text(
                          'Next: ${currentStop.address}',
                          style: const TextStyle(
                            color: Colors.white,
                            fontSize: 16,
                          ),
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 8),
                  Row(
                    children: [
                      const Icon(Icons.access_time, color: Colors.white70, size: 16),
                      const SizedBox(width: 8),
                      Text(
                        'ETA: ${currentStop.estimatedTime}',
                        style: const TextStyle(
                          color: Colors.white70,
                          fontSize: 14,
                        ),
                      ),
                      const Spacer(),
                      const Icon(Icons.straighten, color: Colors.white70, size: 16),
                      const SizedBox(width: 4),
                      Text(
                        currentStop.distance,
                        style: const TextStyle(
                          color: Colors.white70,
                          fontSize: 14,
                        ),
                      ),
                    ],
                  ),
                ],
              ],
            ),
          ),

          // Route Progress
          Container(
            margin: const EdgeInsets.symmetric(horizontal: 16),
            padding: const EdgeInsets.all(16),
            decoration: BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.circular(12),
              boxShadow: [
                BoxShadow(
                  color: Colors.grey.shade200,
                  blurRadius: 4,
                  offset: const Offset(0, 2),
                ),
              ],
            ),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    const Text(
                      'Route Progress',
                      style: TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    Text(
                      '${routeStops.where((s) => s.isCompleted).length}/${routeStops.length}',
                      style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.bold,
                        color: Colors.blue.shade600,
                      ),
                    ),
                  ],
                ),
                const SizedBox(height: 12),
                LinearProgressIndicator(
                  value: routeStops.where((s) => s.isCompleted).length / routeStops.length,
                  backgroundColor: Colors.grey.shade300,
                  valueColor: AlwaysStoppedAnimation<Color>(Colors.blue.shade600),
                  minHeight: 8,
                ),
              ],
            ),
          ),

          const SizedBox(height: 16),

          // Route Stops List
          Expanded(
            child: ListView.builder(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              itemCount: routeStops.length,
              itemBuilder: (context, index) {
                final stop = routeStops[index];
                final isCurrent = index == currentStopIndex;
                final isPast = index < currentStopIndex;

                return Container(
                  margin: const EdgeInsets.only(bottom: 12),
                  child: Row(
                    children: [
                      // Stop number and line
                      Column(
                        children: [
                          Container(
                            width: 40,
                            height: 40,
                            decoration: BoxDecoration(
                              color: stop.isCompleted
                                  ? Colors.green
                                  : isCurrent
                                      ? Colors.blue
                                      : Colors.grey.shade300,
                              shape: BoxShape.circle,
                            ),
                            child: Center(
                              child: stop.isCompleted
                                  ? const Icon(Icons.check, color: Colors.white, size: 20)
                                  : Text(
                                      '${index + 1}',
                                      style: TextStyle(
                                        color: isCurrent ? Colors.white : Colors.grey.shade600,
                                        fontWeight: FontWeight.bold,
                                      ),
                                    ),
                            ),
                          ),
                          if (index < routeStops.length - 1)
                            Container(
                              width: 2,
                              height: 30,
                              color: isPast ? Colors.green : Colors.grey.shade300,
                            ),
                        ],
                      ),
                      const SizedBox(width: 16),

                      // Stop details
                      Expanded(
                        child: Card(
                          elevation: isCurrent ? 4 : 1,
                          color: isCurrent ? Colors.blue.shade50 : null,
                          child: Padding(
                            padding: const EdgeInsets.all(12),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Row(
                                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                  children: [
                                    Text(
                                      stop.packageId,
                                      style: const TextStyle(
                                        fontWeight: FontWeight.bold,
                                        fontSize: 14,
                                      ),
                                    ),
                                    Text(
                                      stop.estimatedTime,
                                      style: TextStyle(
                                        color: Colors.blue.shade600,
                                        fontSize: 12,
                                        fontWeight: FontWeight.w500,
                                      ),
                                    ),
                                  ],
                                ),
                                const SizedBox(height: 4),
                                Text(
                                  stop.recipientName,
                                  style: const TextStyle(fontSize: 14),
                                ),
                                const SizedBox(height: 2),
                                Text(
                                  stop.address,
                                  style: const TextStyle(
                                    fontSize: 12,
                                    color: Colors.grey,
                                  ),
                                ),
                                if (isCurrent) ...[
                                  const SizedBox(height: 8),
                                  Row(
                                    mainAxisAlignment: MainAxisAlignment.end,
                                    children: [
                                      TextButton.icon(
                                        onPressed: () => Navigator.pushNamed(
                                          context,
                                          '/package-delivery',
                                          arguments: stop,
                                        ),
                                        icon: const Icon(Icons.local_shipping, size: 16),
                                        label: const Text('Deliver'),
                                        style: TextButton.styleFrom(
                                          foregroundColor: Colors.blue.shade600,
                                        ),
                                      ),
                                    ],
                                  ),
                                ],
                              ],
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                );
              },
            ),
          ),
        ],
      ),
      bottomNavigationBar: Container(
        padding: const EdgeInsets.all(16),
        child: Row(
          children: [
            if (!isNavigationActive)
              Expanded(
                child: ElevatedButton.icon(
                  onPressed: _startNavigation,
                  icon: const Icon(Icons.play_arrow),
                  label: const Text('Start Navigation'),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.green.shade600,
                    foregroundColor: Colors.white,
                    padding: const EdgeInsets.symmetric(vertical: 12),
                  ),
                ),
              )
            else ...[
              Expanded(
                child: ElevatedButton.icon(
                  onPressed: _stopNavigation,
                  icon: const Icon(Icons.stop),
                  label: const Text('Stop Navigation'),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.red.shade600,
                    foregroundColor: Colors.white,
                    padding: const EdgeInsets.symmetric(vertical: 12),
                  ),
                ),
              ),
              const SizedBox(width: 12),
              Expanded(
                child: ElevatedButton.icon(
                  onPressed: currentStop != null ? () {
                    Navigator.pushNamed(
                      context,
                      '/package-delivery',
                      arguments: currentStop,
                    );
                  } : null,
                  icon: const Icon(Icons.location_on),
                  label: const Text('Arrive'),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.blue.shade600,
                    foregroundColor: Colors.white,
                    padding: const EdgeInsets.symmetric(vertical: 12),
                  ),
                ),
              ),
            ],
          ],
        ),
      ),
    );
  }
}

class RouteStop {
  final String id;
  final String packageId;
  final String recipientName;
  final String address;
  final String estimatedTime;
  final String distance;
  bool isCompleted;

  RouteStop({
    required this.id,
    required this.packageId,
    required this.recipientName,
    required this.address,
    required this.estimatedTime,
    required this.distance,
    this.isCompleted = false,
  });
}
