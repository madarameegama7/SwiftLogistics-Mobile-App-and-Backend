import 'package:flutter/material.dart';
import 'package:flutter/gestures.dart';

class ProofOfDeliveryPage extends StatefulWidget {
  const ProofOfDeliveryPage({super.key});

  @override
  State<ProofOfDeliveryPage> createState() => _ProofOfDeliveryPageState();
}

class _ProofOfDeliveryPageState extends State<ProofOfDeliveryPage> {
  List<Offset> signaturePoints = [];
  bool isDrawing = false;
  final TextEditingController customerNameController = TextEditingController();

  @override
  void dispose() {
    customerNameController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final args = ModalRoute.of(context)?.settings.arguments as Map<String, String>?;
    final proofType = args?['type'] ?? 'signature';
    final packageId = args?['packageId'] ?? 'PKG-001';

    return Scaffold(
      appBar: AppBar(
        title: Text(proofType == 'signature' ? 'Digital Signature' : 'Photo Proof'),
        backgroundColor: Colors.blue.shade600,
        foregroundColor: Colors.white,
        actions: [
          TextButton(
            onPressed: _clearSignature,
            child: const Text(
              'Clear',
              style: TextStyle(color: Colors.white),
            ),
          ),
        ],
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Package Info
            Card(
              child: Padding(
                padding: const EdgeInsets.all(16),
                child: Row(
                  children: [
                    Icon(Icons.inventory, color: Colors.blue.shade600),
                    const SizedBox(width: 12),
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          'Package: $packageId',
                          style: const TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 16,
                          ),
                        ),
                        const Text(
                          'John Doe - 123 Main St, Colombo 03',
                          style: TextStyle(
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

            const SizedBox(height: 24),

            if (proofType == 'signature') ...[
              // Customer Name Input
              const Text(
                'Customer Name',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 8),
              TextField(
                controller: customerNameController,
                decoration: InputDecoration(
                  hintText: 'Enter customer name who received the package',
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

              // Signature Section
              const Text(
                'Digital Signature',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 8),
              const Text(
                'Please ask the customer to sign below:',
                style: TextStyle(
                  color: Colors.grey,
                  fontSize: 14,
                ),
              ),
              const SizedBox(height: 12),

              // Signature Canvas
              Container(
                width: double.infinity,
                height: 200,
                decoration: BoxDecoration(
                  border: Border.all(color: Colors.grey.shade300, width: 2),
                  borderRadius: BorderRadius.circular(12),
                  color: Colors.grey.shade50,
                ),
                child: ClipRRect(
                  borderRadius: BorderRadius.circular(10),
                  child: GestureDetector(
                    onPanStart: (details) {
                      setState(() {
                        isDrawing = true;
                        signaturePoints.add(details.localPosition);
                      });
                    },
                    onPanUpdate: (details) {
                      setState(() {
                        signaturePoints.add(details.localPosition);
                      });
                    },
                    onPanEnd: (details) {
                      setState(() {
                        isDrawing = false;
                        signaturePoints.add(Offset.infinite);
                      });
                    },
                    child: CustomPaint(
                      painter: SignaturePainter(signaturePoints),
                      size: Size.infinite,
                    ),
                  ),
                ),
              ),

              const SizedBox(height: 12),
              Center(
                child: Text(
                  'Tap and drag to create signature',
                  style: TextStyle(
                    color: Colors.grey.shade600,
                    fontSize: 12,
                    fontStyle: FontStyle.italic,
                  ),
                ),
              ),
            ] else ...[
              // Photo Capture Section
              const Text(
                'Photo Proof',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 8),
              const Text(
                'Take a photo as proof of delivery:',
                style: TextStyle(
                  color: Colors.grey,
                  fontSize: 14,
                ),
              ),
              const SizedBox(height: 16),

              // Photo Placeholder
              Container(
                width: double.infinity,
                height: 200,
                decoration: BoxDecoration(
                  border: Border.all(color: Colors.grey.shade300, width: 2),
                  borderRadius: BorderRadius.circular(12),
                  color: Colors.grey.shade50,
                ),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Icon(
                      Icons.camera_alt,
                      size: 64,
                      color: Colors.grey.shade400,
                    ),
                    const SizedBox(height: 8),
                    Text(
                      'No photo captured',
                      style: TextStyle(
                        color: Colors.grey.shade600,
                        fontSize: 16,
                      ),
                    ),
                  ],
                ),
              ),

              const SizedBox(height: 16),

              // Camera Buttons
              Row(
                children: [
                  Expanded(
                    child: ElevatedButton.icon(
                      onPressed: () => _takePhoto('camera'),
                      icon: const Icon(Icons.camera_alt),
                      label: const Text('Take Photo'),
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
                      onPressed: () => _takePhoto('gallery'),
                      icon: const Icon(Icons.photo_library),
                      label: const Text('From Gallery'),
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

            // Terms and Conditions
            Container(
              padding: const EdgeInsets.all(16),
              decoration: BoxDecoration(
                color: Colors.blue.shade50,
                borderRadius: BorderRadius.circular(12),
                border: Border.all(color: Colors.blue.shade200),
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    children: [
                      Icon(Icons.info_outline, color: Colors.blue.shade600, size: 20),
                      const SizedBox(width: 8),
                      const Text(
                        'Terms & Conditions',
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 14,
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 8),
                  const Text(
                    '• By signing/providing photo proof, you confirm receipt of the package\n'
                    '• Package condition was verified before handover\n'
                    '• This serves as proof of successful delivery\n'
                    '• Digital signature/photo has the same legal validity as handwritten signature',
                    style: TextStyle(
                      fontSize: 12,
                      color: Colors.grey,
                    ),
                  ),
                ],
              ),
            ),

            const SizedBox(height: 24),

            // Submit Button
            Container(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: _canSubmit() ? () => _submitProof() : null,
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.green.shade600,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 16),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                  disabledBackgroundColor: Colors.grey.shade300,
                ),
                child: Text(
                  'Submit ${proofType == 'signature' ? 'Signature' : 'Photo Proof'}',
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

  void _clearSignature() {
    setState(() {
      signaturePoints.clear();
    });
  }

  void _takePhoto(String source) {
    // In a real app, you would use image_picker package
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text('Photo captured from $source'),
        backgroundColor: Colors.green,
      ),
    );
  }

  bool _canSubmit() {
    final args = ModalRoute.of(context)?.settings.arguments as Map<String, String>?;
    final proofType = args?['type'] ?? 'signature';
    
    if (proofType == 'signature') {
      return customerNameController.text.trim().isNotEmpty && signaturePoints.isNotEmpty;
    } else {
      // For photo, we'll assume a photo is "captured" for demo purposes
      return true;
    }
  }

  void _submitProof() {
    final args = ModalRoute.of(context)?.settings.arguments as Map<String, String>?;
    final proofType = args?['type'] ?? 'signature';
    final packageId = args?['packageId'] ?? 'PKG-001';

    // Here you would normally upload the signature/photo to your backend
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Proof Submitted'),
          content: Text(
            '${proofType == 'signature' ? 'Digital signature' : 'Photo proof'} '
            'has been successfully submitted for package $packageId.'
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(); // Close dialog
                Navigator.of(context).pop(); // Go back to delivery page
              },
              child: const Text('OK'),
            ),
          ],
        );
      },
    );
  }
}

class SignaturePainter extends CustomPainter {
  final List<Offset> points;

  SignaturePainter(this.points);

  @override
  void paint(Canvas canvas, Size size) {
    final paint = Paint()
      ..color = Colors.black
      ..strokeCap = StrokeCap.round
      ..strokeWidth = 3.0;

    for (int i = 0; i < points.length - 1; i++) {
      if (points[i] != Offset.infinite && points[i + 1] != Offset.infinite) {
        canvas.drawLine(points[i], points[i + 1], paint);
      }
    }
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) {
    return true;
  }
}
