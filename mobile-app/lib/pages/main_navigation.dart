import 'package:flutter/material.dart';
import 'package:flutter_iconly/flutter_iconly.dart';
import 'package:badges/badges.dart' as badges;
import 'package:swiftlogistics/pages/driver_dashboard.dart';
import 'package:swiftlogistics/pages/delivery_manifest_page.dart';
import 'package:swiftlogistics/pages/route_navigation_page.dart';
import 'package:swiftlogistics/pages/notifications_page.dart';
import 'package:swiftlogistics/pages/settings_page.dart';

class MainNavigation extends StatefulWidget {
  const MainNavigation({super.key});

  @override
  State<MainNavigation> createState() => _MainNavigationState();
}

class _MainNavigationState extends State<MainNavigation> {
  int _currentIndex = 0;
  
  // Simulated notification count - in real app this would come from a state management solution
  int _notificationCount = 3;

  final List<Widget> _pages = [
    const DriverDashboard(),
    const DeliveryManifestPage(),
    const RouteNavigationPage(),
    const NotificationsPage(),
    const SettingsPage(),
  ];

  final List<NavigationItem> _navigationItems = [
    NavigationItem(
      icon: IconlyBold.home,
      label: 'Dashboard',
      description: 'Overview & Status',
    ),
    NavigationItem(
      icon: IconlyBold.document,
      label: 'Deliveries',
      description: 'Package List',
    ),
    NavigationItem(
      icon: IconlyBold.location,
      label: 'Route',
      description: 'Navigation',
    ),
    NavigationItem(
      icon: IconlyBold.notification,
      label: 'Alerts',
      description: 'Notifications',
      showBadge: true,
    ),
    NavigationItem(
      icon: IconlyBold.setting,
      label: 'Settings',
      description: 'Preferences',
    ),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: IndexedStack(
        index: _currentIndex,
        children: _pages,
      ),
      bottomNavigationBar: Container(
        decoration: BoxDecoration(
          color: Colors.white,
          boxShadow: [
            BoxShadow(
              color: Colors.black.withOpacity(0.1),
              blurRadius: 10,
              offset: const Offset(0, -2),
            ),
          ],
        ),
        child: SafeArea(
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 8),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: _navigationItems.asMap().entries.map((entry) {
                final index = entry.key;
                final item = entry.value;
                final isSelected = _currentIndex == index;
                
                return Expanded(
                  child: GestureDetector(
                    onTap: () => _onItemTapped(index),
                    child: AnimatedContainer(
                      duration: const Duration(milliseconds: 200),
                      padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 4),
                      margin: const EdgeInsets.symmetric(horizontal: 2),
                      decoration: BoxDecoration(
                        color: isSelected 
                            ? Colors.blue.shade600.withOpacity(0.1)
                            : Colors.transparent,
                        borderRadius: BorderRadius.circular(12),
                      ),
                      child: Column(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          Stack(
                            children: [
                              AnimatedContainer(
                                duration: const Duration(milliseconds: 200),
                                padding: const EdgeInsets.all(8),
                                decoration: BoxDecoration(
                                  color: isSelected 
                                      ? Colors.blue.shade600
                                      : Colors.grey.shade200,
                                  borderRadius: BorderRadius.circular(8),
                                ),
                                child: Icon(
                                  item.icon,
                                  color: isSelected 
                                      ? Colors.white 
                                      : Colors.grey.shade600,
                                  size: 20,
                                ),
                              ),
                              if (item.showBadge && _notificationCount > 0)
                                Positioned(
                                  right: 0,
                                  top: 0,
                                  child: badges.Badge(
                                    badgeContent: Text(
                                      _notificationCount > 99 ? '99+' : _notificationCount.toString(),
                                      style: const TextStyle(
                                        color: Colors.white,
                                        fontSize: 10,
                                        fontWeight: FontWeight.bold,
                                      ),
                                    ),
                                    badgeStyle: badges.BadgeStyle(
                                      badgeColor: Colors.red.shade600,
                                      padding: const EdgeInsets.all(4),
                                    ),
                                  ),
                                ),
                            ],
                          ),
                          const SizedBox(height: 4),
                          AnimatedDefaultTextStyle(
                            duration: const Duration(milliseconds: 200),
                            style: TextStyle(
                              fontSize: isSelected ? 11 : 10,
                              fontWeight: isSelected ? FontWeight.w600 : FontWeight.w500,
                              color: isSelected 
                                  ? Colors.blue.shade600 
                                  : Colors.grey.shade600,
                            ),
                            child: Text(
                              item.label,
                              textAlign: TextAlign.center,
                              maxLines: 1,
                              overflow: TextOverflow.ellipsis,
                            ),
                          ),
                          if (isSelected) ...[
                            const SizedBox(height: 2),
                            Text(
                              item.description,
                              style: TextStyle(
                                fontSize: 8,
                                color: Colors.blue.shade400,
                              ),
                              textAlign: TextAlign.center,
                              maxLines: 1,
                              overflow: TextOverflow.ellipsis,
                            ),
                          ],
                        ],
                      ),
                    ),
                  ),
                );
              }).toList(),
            ),
          ),
        ),
      ),
      floatingActionButton: _currentIndex == 1 || _currentIndex == 2
          ? FloatingActionButton.extended(
              onPressed: _showQuickActions,
              backgroundColor: Colors.blue.shade600,
              foregroundColor: Colors.white,
              icon: const Icon(Icons.bolt),
              label: Text(_currentIndex == 1 ? 'Quick Scan' : 'Emergency'),
              elevation: 4,
            )
          : null,
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
    );
  }

  void _onItemTapped(int index) {
    setState(() {
      _currentIndex = index;
    });

    // Update notification count when notifications tab is opened
    if (index == 3 && _notificationCount > 0) {
      // Simulate marking notifications as read after a delay
      Future.delayed(const Duration(seconds: 2), () {
        if (mounted) {
          setState(() {
            _notificationCount = 0;
          });
        }
      });
    }

    // Provide haptic feedback
    _hapticFeedback();
  }

  void _hapticFeedback() {
    // In a real app, you would use HapticFeedback.lightImpact()
    // For web, we'll skip this
  }

  void _showQuickActions() {
    showModalBottomSheet(
      context: context,
      backgroundColor: Colors.transparent,
      builder: (context) => Container(
        margin: const EdgeInsets.all(16),
        decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.circular(16),
        ),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Container(
              width: 40,
              height: 4,
              margin: const EdgeInsets.only(top: 12),
              decoration: BoxDecoration(
                color: Colors.grey.shade300,
                borderRadius: BorderRadius.circular(2),
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(16),
              child: Column(
                children: [
                  Text(
                    _currentIndex == 1 ? 'Quick Actions' : 'Emergency Actions',
                    style: const TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 16),
                  if (_currentIndex == 1) ...[
                    _buildQuickAction(
                      icon: Icons.qr_code_scanner,
                      title: 'Scan Package',
                      subtitle: 'Quick barcode scan',
                      onTap: () {
                        Navigator.pop(context);
                        _showSnackBar('Package scanner opened');
                      },
                    ),
                    _buildQuickAction(
                      icon: Icons.add_circle,
                      title: 'Add Delivery',
                      subtitle: 'Manual package entry',
                      onTap: () {
                        Navigator.pop(context);
                        _showSnackBar('Add delivery form opened');
                      },
                    ),
                    _buildQuickAction(
                      icon: Icons.refresh,
                      title: 'Sync Data',
                      subtitle: 'Update delivery list',
                      onTap: () {
                        Navigator.pop(context);
                        _showSnackBar('Data synchronized');
                      },
                    ),
                  ] else ...[
                    _buildQuickAction(
                      icon: Icons.warning,
                      title: 'Report Emergency',
                      subtitle: 'Contact support immediately',
                      onTap: () {
                        Navigator.pop(context);
                        _showEmergencyDialog();
                      },
                      isEmergency: true,
                    ),
                    _buildQuickAction(
                      icon: Icons.local_hospital,
                      title: 'Medical Emergency',
                      subtitle: 'Call emergency services',
                      onTap: () {
                        Navigator.pop(context);
                        _showSnackBar('Emergency services contacted');
                      },
                      isEmergency: true,
                    ),
                    _buildQuickAction(
                      icon: Icons.phone,
                      title: 'Call Support',
                      subtitle: 'Direct line to dispatch',
                      onTap: () {
                        Navigator.pop(context);
                        _showSnackBar('Calling support...');
                      },
                    ),
                  ],
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildQuickAction({
    required IconData icon,
    required String title,
    required String subtitle,
    required VoidCallback onTap,
    bool isEmergency = false,
  }) {
    return ListTile(
      leading: Container(
        padding: const EdgeInsets.all(8),
        decoration: BoxDecoration(
          color: isEmergency 
              ? Colors.red.shade100
              : Colors.blue.shade100,
          borderRadius: BorderRadius.circular(8),
        ),
        child: Icon(
          icon,
          color: isEmergency 
              ? Colors.red.shade600
              : Colors.blue.shade600,
        ),
      ),
      title: Text(title),
      subtitle: Text(subtitle),
      onTap: onTap,
      trailing: const Icon(Icons.arrow_forward_ios, size: 16),
    );
  }

  void _showEmergencyDialog() {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Row(
          children: [
            Icon(Icons.warning, color: Colors.red),
            SizedBox(width: 8),
            Text('Emergency Report'),
          ],
        ),
        content: const Text(
          'This will immediately notify dispatch and emergency services. '
          'Are you sure you want to proceed?',
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Cancel'),
          ),
          ElevatedButton(
            onPressed: () {
              Navigator.pop(context);
              _showSnackBar('Emergency report sent to dispatch');
            },
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.red,
              foregroundColor: Colors.white,
            ),
            child: const Text('Send Emergency Report'),
          ),
        ],
      ),
    );
  }

  void _showSnackBar(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(message),
        backgroundColor: Colors.blue.shade600,
        behavior: SnackBarBehavior.floating,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(8),
        ),
      ),
    );
  }
}

class NavigationItem {
  final IconData icon;
  final String label;
  final String description;
  final bool showBadge;

  NavigationItem({
    required this.icon,
    required this.label,
    required this.description,
    this.showBadge = false,
  });
}
