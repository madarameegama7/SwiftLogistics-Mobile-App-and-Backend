import 'package:flutter/material.dart';
import 'package:flutter_iconly/flutter_iconly.dart';

class PackageDeliveryPage extends StatefulWidget {
  const PackageDeliveryPage({super.key});

  @override
  State<PackageDeliveryPage> createState() => _PackageDeliveryPageState();
}

class _PackageDeliveryPageState extends State<PackageDeliveryPage> {
  String selectedStatus = '';
  String failureReason = '';
  final TextEditingController notesController = TextEditingController();

  final List<String> failureReasons = [
    'Recipient not available',
    'Incorrect address',
    'Refused delivery',
    'Damaged package',
    'Security concerns',
    'Other',
  ];

  @override
  void dispose() {
    notesController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    // In a real app, you would get this from route arguments
    final packageInfo = _getPackageInfo();

    return Scaffold(
      appBar: AppBar(
        title: Text('Deliver ${packageInfo['id']}'),
        backgroundColor: Colors.blue.shade600,
        foregroundColor: Colors.white,
        actions: [
          IconButton(
            onPressed: () => _showPackageDetails(context, packageInfo),
            icon: const Icon(Icons.info_outline),
          ),
        ],
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Package Info Card
            Card(
              child: Padding(
                padding: const EdgeInsets.all(16),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text(
                          packageInfo['id'] ?? 'Unknown',
                          style: const TextStyle(
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        Container(
                          padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                          decoration: BoxDecoration(
                            color: Colors.orange.shade100,
                            borderRadius: BorderRadius.circular(20),
                            border: Border.all(color: Colors.orange.shade300),
                          ),
                          child: const Text(
                            'HIGH PRIORITY',
                            style: TextStyle(
                              color: Colors.orange,
                              fontSize: 10,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 12),
                    _buildInfoRow(Icons.person, 'Recipient', packageInfo['recipient'] ?? 'Unknown'),
                    _buildInfoRow(Icons.location_on, 'Address', packageInfo['address'] ?? 'Unknown'),
                    _buildInfoRow(Icons.phone, 'Phone', packageInfo['phone'] ?? 'N/A'),
                    _buildInfoRow(Icons.access_time, 'ETA', packageInfo['eta'] ?? 'TBD'),
                    _buildInfoRow(Icons.inventory, 'Type', packageInfo['type'] ?? 'Package'),
                  ],
                ),
              ),
            ),

            const SizedBox(height: 24),

            // Action Buttons
            const Text(
              'Delivery Status',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 16),

            // Delivered Button
            SizedBox(
              width: double.infinity,
              child: ElevatedButton.icon(
                onPressed: () => _selectStatus('delivered'),
                icon: Icon(
                  Icons.check_circle,
                  color: selectedStatus == 'delivered' ? Colors.white : Colors.green,
                ),
                label: const Text('Mark as Delivered'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: selectedStatus == 'delivered' 
                      ? Colors.green.shade600 
                      : Colors.green.shade50,
                  foregroundColor: selectedStatus == 'delivered' 
                      ? Colors.white 
                      : Colors.green.shade600,
                  padding: const EdgeInsets.symmetric(vertical: 16),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                    side: BorderSide(
                      color: Colors.green.shade600,
                      width: selectedStatus == 'delivered' ? 0 : 2,
                    ),
                  ),
                ),
              ),
            ),

            const SizedBox(height: 12),

            // Failed Button
            SizedBox(
              width: double.infinity,
              child: ElevatedButton.icon(
                onPressed: () => _selectStatus('failed'),
                icon: Icon(
                  Icons.error,
                  color: selectedStatus == 'failed' ? Colors.white : Colors.red,
                ),
                label: const Text('Mark as Failed'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: selectedStatus == 'failed' 
                      ? Colors.red.shade600 
                      : Colors.red.shade50,
                  foregroundColor: selectedStatus == 'failed' 
                      ? Colors.white 
                      : Colors.red.shade600,
                  padding: const EdgeInsets.symmetric(vertical: 16),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                    side: BorderSide(
                      color: Colors.red.shade600,
                      width: selectedStatus == 'failed' ? 0 : 2,
                    ),
                  ),
                ),
              ),
            ),

            // Failure Reason (shown only when failed is selected)
            if (selectedStatus == 'failed') ...[
              const SizedBox(height: 16),
              const Text(
                'Failure Reason',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 8),
              ...failureReasons.map((reason) => RadioListTile<String>(
                title: Text(reason),
                value: reason,
                groupValue: failureReason,
                onChanged: (value) {
                  setState(() {
                    failureReason = value!;
                  });
                },
                activeColor: Colors.red.shade600,
              )).toList(),
            ],

            // Notes Section
            const SizedBox(height: 24),
            const Text(
              'Notes (Optional)',
              style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 8),
            TextField(
              controller: notesController,
              maxLines: 3,
              decoration: InputDecoration(
                hintText: 'Add any additional notes about the delivery...',
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(12),
                ),
                focusedBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(12),
                  borderSide: BorderSide(color: Colors.blue.shade600, width: 2),
                ),
              ),
            ),

            const SizedBox(height: 24),

            // Proof of Delivery Section
            if (selectedStatus == 'delivered') ...[
              const Text(
                'Proof of Delivery',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 16),
              Row(
                children: [
                  Expanded(
                    child: ElevatedButton.icon(
                      onPressed: () => Navigator.pushNamed(context, '/proof-of-delivery', 
                          arguments: {'type': 'signature', 'packageId': packageInfo['id']}),
                      icon: const Icon(Icons.draw),
                      label: const Text('Capture Signature'),
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.blue.shade600,
                        foregroundColor: Colors.white,
                        padding: const EdgeInsets.symmetric(vertical: 12),
                      ),
                    ),
                  ),
                  const SizedBox(width: 12),
                  Expanded(
                    child: ElevatedButton.icon(
                      onPressed: () => Navigator.pushNamed(context, '/proof-of-delivery', 
                          arguments: {'type': 'photo', 'packageId': packageInfo['id']}),
                      icon: const Icon(Icons.camera_alt),
                      label: const Text('Take Photo'),
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.green.shade600,
                        foregroundColor: Colors.white,
                        padding: const EdgeInsets.symmetric(vertical: 12),
                      ),
                    ),
                  ),
                ],
              ),
            ],

            const SizedBox(height: 32),

            // Submit Button
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: selectedStatus.isNotEmpty ? () => _submitDelivery() : null,
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.blue.shade600,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 16),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                  disabledBackgroundColor: Colors.grey.shade300,
                ),
                child: Text(
                  'Submit ${selectedStatus == 'delivered' ? 'Delivery' : selectedStatus == 'failed' ? 'Failure' : 'Status'}',
                  style: const TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildInfoRow(IconData icon, String label, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Icon(icon, size: 16, color: Colors.grey.shade600),
          const SizedBox(width: 8),
          Text(
            '$label: ',
            style: const TextStyle(
              fontWeight: FontWeight.w500,
              color: Colors.grey,
            ),
          ),
          Expanded(
            child: Text(value),
          ),
        ],
      ),
    );
  }

  void _selectStatus(String status) {
    setState(() {
      selectedStatus = status;
      if (status != 'failed') {
        failureReason = '';
      }
    });
  }

  void _submitDelivery() {
    if (selectedStatus == 'failed' && failureReason.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Please select a failure reason'),
          backgroundColor: Colors.red,
        ),
      );
      return;
    }

    // Here you would normally send the data to your backend
    final message = selectedStatus == 'delivered' 
        ? 'Package marked as delivered successfully!'
        : 'Package marked as failed: $failureReason';

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Status Updated'),
          content: Text(message),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(); // Close dialog
                Navigator.of(context).pop(); // Go back to previous screen
              },
              child: const Text('OK'),
            ),
          ],
        );
      },
    );
  }

  void _showPackageDetails(BuildContext context, Map<String, String> packageInfo) {
    showModalBottomSheet(
      context: context,
      builder: (BuildContext context) {
        return Container(
          padding: const EdgeInsets.all(16),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text(
                'Package Details',
                style: TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 16),
              _buildDetailRow('Package ID', packageInfo['id']!),
              _buildDetailRow('Weight', '2.5 kg'),
              _buildDetailRow('Dimensions', '30x20x15 cm'),
              _buildDetailRow('Special Instructions', 'Handle with care - Fragile items'),
              _buildDetailRow('Delivery Window', '09:00 AM - 06:00 PM'),
              const SizedBox(height: 16),
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  onPressed: () => Navigator.pop(context),
                  child: const Text('Close'),
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  Widget _buildDetailRow(String label, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          SizedBox(
            width: 100,
            child: Text(
              '$label:',
              style: const TextStyle(
                fontWeight: FontWeight.w500,
                color: Colors.grey,
              ),
            ),
          ),
          Expanded(
            child: Text(value),
          ),
        ],
      ),
    );
  }

  Map<String, String> _getPackageInfo() {
    // In a real app, this would come from route arguments or API
    return {
      'id': 'PKG-001',
      'recipient': 'John Doe',
      'address': '123 Main St, Colombo 03',
      'phone': '+94 77 123 4567',
      'eta': '09:30 AM',
      'type': 'Electronics',
    };
  }
}
