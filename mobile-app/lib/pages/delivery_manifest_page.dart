import 'package:flutter/material.dart';
import 'package:flutter_iconly/flutter_iconly.dart';

class DeliveryManifestPage extends StatefulWidget {
  const DeliveryManifestPage({super.key});

  @override
  State<DeliveryManifestPage> createState() => _DeliveryManifestPageState();
}

class _DeliveryManifestPageState extends State<DeliveryManifestPage> {
  String selectedFilter = 'All';
  final List<String> filters = ['All', 'Pending', 'In Transit', 'Delivered', 'Failed'];

  final List<DeliveryItem> deliveries = [
    DeliveryItem(
      id: 'PKG-001',
      recipientName: 'John Doe',
      address: '123 Main St, Colombo 03',
      phone: '+94 77 123 4567',
      priority: Priority.high,
      status: DeliveryStatus.pending,
      estimatedTime: '09:30 AM',
      packageType: 'Electronics',
    ),
    DeliveryItem(
      id: 'PKG-002',
      recipientName: 'Jane Smith',
      address: '456 Galle Road, Colombo 04',
      phone: '+94 77 234 5678',
      priority: Priority.normal,
      status: DeliveryStatus.inTransit,
      estimatedTime: '10:15 AM',
      packageType: 'Clothing',
    ),
    DeliveryItem(
      id: 'PKG-003',
      recipientName: 'Mike Johnson',
      address: '789 Kandy Road, Colombo 07',
      phone: '+94 77 345 6789',
      priority: Priority.low,
      status: DeliveryStatus.delivered,
      estimatedTime: '11:00 AM',
      packageType: 'Books',
    ),
    DeliveryItem(
      id: 'PKG-004',
      recipientName: 'Sarah Wilson',
      address: '321 Nugegoda Main Road',
      phone: '+94 77 456 7890',
      priority: Priority.high,
      status: DeliveryStatus.pending,
      estimatedTime: '11:45 AM',
      packageType: 'Medical Supplies',
    ),
    DeliveryItem(
      id: 'PKG-005',
      recipientName: 'David Brown',
      address: '654 Dehiwala Junction',
      phone: '+94 77 567 8901',
      priority: Priority.normal,
      status: DeliveryStatus.pending,
      estimatedTime: '12:30 PM',
      packageType: 'Food Items',
    ),
  ];

  List<DeliveryItem> get filteredDeliveries {
    if (selectedFilter == 'All') return deliveries;
    return deliveries.where((delivery) {
      switch (selectedFilter) {
        case 'Pending':
          return delivery.status == DeliveryStatus.pending;
        case 'In Transit':
          return delivery.status == DeliveryStatus.inTransit;
        case 'Delivered':
          return delivery.status == DeliveryStatus.delivered;
        case 'Failed':
          return delivery.status == DeliveryStatus.failed;
        default:
          return true;
      }
    }).toList();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Delivery Manifest'),
        backgroundColor: Colors.blue.shade600,
        foregroundColor: Colors.white,
        actions: [
          IconButton(
            onPressed: () => Navigator.pushNamed(context, '/route-navigation'),
            icon: const Icon(Icons.navigation),
          ),
          IconButton(
            onPressed: () {
              // Refresh manifest
              setState(() {});
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Manifest refreshed')),
              );
            },
            icon: const Icon(Icons.refresh),
          ),
        ],
      ),
      body: Column(
        children: [
          // Summary Card
          Container(
            margin: const EdgeInsets.all(16),
            padding: const EdgeInsets.all(16),
            decoration: BoxDecoration(
              gradient: LinearGradient(
                colors: [Colors.blue.shade50, Colors.blue.shade100],
                begin: Alignment.topLeft,
                end: Alignment.bottomRight,
              ),
              borderRadius: BorderRadius.circular(12),
            ),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                _buildSummaryItem('Total', '${deliveries.length}', Colors.blue),
                _buildSummaryItem('Pending', '${deliveries.where((d) => d.status == DeliveryStatus.pending).length}', Colors.orange),
                _buildSummaryItem('Delivered', '${deliveries.where((d) => d.status == DeliveryStatus.delivered).length}', Colors.green),
              ],
            ),
          ),

          // Filter Chips
          Container(
            height: 50,
            padding: const EdgeInsets.symmetric(horizontal: 16),
            child: ListView.builder(
              scrollDirection: Axis.horizontal,
              itemCount: filters.length,
              itemBuilder: (context, index) {
                final filter = filters[index];
                final isSelected = selectedFilter == filter;
                return Padding(
                  padding: const EdgeInsets.only(right: 8),
                  child: FilterChip(
                    label: Text(filter),
                    selected: isSelected,
                    onSelected: (selected) {
                      setState(() {
                        selectedFilter = filter;
                      });
                    },
                    selectedColor: Colors.blue.shade100,
                    checkmarkColor: Colors.blue.shade600,
                  ),
                );
              },
            ),
          ),

          // Delivery List
          Expanded(
            child: ListView.builder(
              padding: const EdgeInsets.all(16),
              itemCount: filteredDeliveries.length,
              itemBuilder: (context, index) {
                final delivery = filteredDeliveries[index];
                return _buildDeliveryCard(delivery);
              },
            ),
          ),
        ],
      ),
      floatingActionButton: FloatingActionButton.extended(
        onPressed: () => Navigator.pushNamed(context, '/route-navigation'),
        icon: const Icon(Icons.navigation),
        label: const Text('Start Route'),
        backgroundColor: Colors.green.shade600,
      ),
    );
  }

  Widget _buildSummaryItem(String title, String value, Color color) {
    return Column(
      children: [
        Text(
          value,
          style: TextStyle(
            fontSize: 24,
            fontWeight: FontWeight.bold,
            color: color,
          ),
        ),
        Text(
          title,
          style: const TextStyle(
            fontSize: 12,
            color: Colors.grey,
          ),
        ),
      ],
    );
  }

  Widget _buildDeliveryCard(DeliveryItem delivery) {
    return Card(
      margin: const EdgeInsets.only(bottom: 12),
      child: InkWell(
        onTap: () => Navigator.pushNamed(
          context, 
          '/package-delivery',
          arguments: delivery,
        ),
        borderRadius: BorderRadius.circular(8),
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    delivery.id,
                    style: const TextStyle(
                      fontWeight: FontWeight.bold,
                      fontSize: 16,
                    ),
                  ),
                  Row(
                    children: [
                      _buildPriorityChip(delivery.priority),
                      const SizedBox(width: 8),
                      _buildStatusChip(delivery.status),
                    ],
                  ),
                ],
              ),
              const SizedBox(height: 12),
              Row(
                children: [
                  const Icon(Icons.person, size: 16, color: Colors.grey),
                  const SizedBox(width: 8),
                  Text(delivery.recipientName),
                ],
              ),
              const SizedBox(height: 4),
              Row(
                children: [
                  const Icon(Icons.location_on, size: 16, color: Colors.grey),
                  const SizedBox(width: 8),
                  Expanded(child: Text(delivery.address)),
                ],
              ),
              const SizedBox(height: 4),
              Row(
                children: [
                  const Icon(Icons.phone, size: 16, color: Colors.grey),
                  const SizedBox(width: 8),
                  Text(delivery.phone),
                ],
              ),
              const SizedBox(height: 8),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    'ETA: ${delivery.estimatedTime}',
                    style: TextStyle(
                      color: Colors.blue.shade600,
                      fontWeight: FontWeight.w500,
                    ),
                  ),
                  Text(
                    delivery.packageType,
                    style: const TextStyle(
                      color: Colors.grey,
                      fontSize: 12,
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildPriorityChip(Priority priority) {
    Color color;
    String text;
    switch (priority) {
      case Priority.high:
        color = Colors.red;
        text = 'HIGH';
        break;
      case Priority.normal:
        color = Colors.orange;
        text = 'NORMAL';
        break;
      case Priority.low:
        color = Colors.grey;
        text = 'LOW';
        break;
    }

    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 2),
      decoration: BoxDecoration(
        color: color.withOpacity(0.1),
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: color.withOpacity(0.3)),
      ),
      child: Text(
        text,
        style: TextStyle(
          color: color,
          fontSize: 10,
          fontWeight: FontWeight.bold,
        ),
      ),
    );
  }

  Widget _buildStatusChip(DeliveryStatus status) {
    Color color;
    String text;
    switch (status) {
      case DeliveryStatus.pending:
        color = Colors.orange;
        text = 'PENDING';
        break;
      case DeliveryStatus.inTransit:
        color = Colors.blue;
        text = 'IN TRANSIT';
        break;
      case DeliveryStatus.delivered:
        color = Colors.green;
        text = 'DELIVERED';
        break;
      case DeliveryStatus.failed:
        color = Colors.red;
        text = 'FAILED';
        break;
    }

    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 2),
      decoration: BoxDecoration(
        color: color.withOpacity(0.1),
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: color.withOpacity(0.3)),
      ),
      child: Text(
        text,
        style: TextStyle(
          color: color,
          fontSize: 10,
          fontWeight: FontWeight.bold,
        ),
      ),
    );
  }
}

class DeliveryItem {
  final String id;
  final String recipientName;
  final String address;
  final String phone;
  final Priority priority;
  final DeliveryStatus status;
  final String estimatedTime;
  final String packageType;

  DeliveryItem({
    required this.id,
    required this.recipientName,
    required this.address,
    required this.phone,
    required this.priority,
    required this.status,
    required this.estimatedTime,
    required this.packageType,
  });
}

enum Priority { high, normal, low }
enum DeliveryStatus { pending, inTransit, delivered, failed }
