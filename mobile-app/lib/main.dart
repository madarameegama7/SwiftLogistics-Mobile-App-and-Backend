import 'package:swiftlogistics/pages/onboarding_page.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:swiftlogistics/pages/login_page.dart';
import 'package:swiftlogistics/pages/order.dart';
// Driver App Screens
import 'package:swiftlogistics/pages/main_navigation.dart';
import 'package:swiftlogistics/pages/driver_dashboard.dart';
import 'package:swiftlogistics/pages/delivery_manifest_page.dart';
import 'package:swiftlogistics/pages/route_navigation_page.dart';
import 'package:swiftlogistics/pages/package_delivery_page.dart';
import 'package:swiftlogistics/pages/proof_of_delivery_page.dart';
import 'package:swiftlogistics/pages/notifications_page.dart';
import 'package:swiftlogistics/pages/settings_page.dart';
import 'package:swiftlogistics/pages/settings_page.dart';

void main() {
  runApp(const MainApp());
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Swift Logistics',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.blue,
        colorScheme: ColorScheme.fromSeed(
          seedColor: Colors.blue.shade600,
          brightness: Brightness.light,
        ),
        textTheme: GoogleFonts.poppinsTextTheme(),
        useMaterial3: true,
        appBarTheme: AppBarTheme(
          backgroundColor: Colors.blue.shade600,
          foregroundColor: Colors.white,
          elevation: 0,
        ),
        elevatedButtonTheme: ElevatedButtonThemeData(
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.blue.shade600,
            foregroundColor: Colors.white,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(8),
            ),
            padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 12),
          ),
        ),
      ),
      home: LoginScreen(),
      routes: {
        '/orderDetails': (context) => OrderDetailsPage(),
        // Driver App Routes
        '/main-navigation': (context) => const MainNavigation(),
        '/driver-dashboard': (context) => const DriverDashboard(),
        '/delivery-manifest': (context) => const DeliveryManifestPage(),
        '/route-navigation': (context) => const RouteNavigationPage(),
        '/package-delivery': (context) => const PackageDeliveryPage(),
        '/proof-of-delivery': (context) => const ProofOfDeliveryPage(),
        '/notifications': (context) => const NotificationsPage(),
        '/settings': (context) => const SettingsPage(),
      },
    );
  }
}
