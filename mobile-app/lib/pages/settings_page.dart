import 'package:flutter/material.dart';

class SettingsPage extends StatefulWidget {
  const SettingsPage({super.key});

  @override
  State<SettingsPage> createState() => _SettingsPageState();
}

class _SettingsPageState extends State<SettingsPage> {
  bool _notificationsEnabled = true;
  bool _locationEnabled = true;
  bool _darkModeEnabled = false;
  String _selectedLanguage = 'English';
  
  final List<String> _languages = ['English', 'Sinhala', 'Tamil'];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Settings'),
        backgroundColor: Colors.blue.shade600,
        foregroundColor: Colors.white,
      ),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          // Profile Section
          _buildSectionHeader('Profile'),
          _buildProfileCard(),
          const SizedBox(height: 24),

          // App Settings
          _buildSectionHeader('App Settings'),
          _buildSettingsCard([
            _buildSwitchTile(
              'Push Notifications',
              'Receive delivery updates and alerts',
              Icons.notifications,
              _notificationsEnabled,
              (value) {
                setState(() {
                  _notificationsEnabled = value;
                });
              },
            ),
            const Divider(height: 1),
            _buildSwitchTile(
              'Location Services',
              'Enable GPS for route navigation',
              Icons.location_on,
              _locationEnabled,
              (value) {
                setState(() {
                  _locationEnabled = value;
                });
              },
            ),
            const Divider(height: 1),
            _buildSwitchTile(
              'Dark Mode',
              'Use dark theme for the app',
              Icons.dark_mode,
              _darkModeEnabled,
              (value) {
                setState(() {
                  _darkModeEnabled = value;
                });
              },
            ),
            const Divider(height: 1),
            _buildDropdownTile(
              'Language',
              'Choose your preferred language',
              Icons.language,
              _selectedLanguage,
              _languages,
              (value) {
                setState(() {
                  _selectedLanguage = value!;
                });
              },
            ),
          ]),
          const SizedBox(height: 24),

          // Account Settings
          _buildSectionHeader('Account'),
          _buildSettingsCard([
            _buildActionTile(
              'Change Password',
              'Update your account password',
              Icons.lock,
              () => _showChangePasswordDialog(),
            ),
            const Divider(height: 1),
            _buildActionTile(
              'Privacy Policy',
              'Read our privacy policy',
              Icons.privacy_tip,
              () => _showPrivacyPolicy(),
            ),
            const Divider(height: 1),
            _buildActionTile(
              'Terms of Service',
              'View terms and conditions',
              Icons.description,
              () => _showTermsOfService(),
            ),
          ]),
          const SizedBox(height: 24),

          // Support
          _buildSectionHeader('Support'),
          _buildSettingsCard([
            _buildActionTile(
              'Help Center',
              'Get help and support',
              Icons.help,
              () => _showHelpCenter(),
            ),
            const Divider(height: 1),
            _buildActionTile(
              'Contact Support',
              'Get in touch with our team',
              Icons.support_agent,
              () => _contactSupport(),
            ),
            const Divider(height: 1),
            _buildActionTile(
              'Report a Bug',
              'Report issues with the app',
              Icons.bug_report,
              () => _reportBug(),
            ),
          ]),
          const SizedBox(height: 24),

          // About
          _buildSectionHeader('About'),
          _buildSettingsCard([
            _buildInfoTile('Version', '1.0.0', Icons.info),
            const Divider(height: 1),
            _buildInfoTile('Build', '100', Icons.code),
          ]),
          const SizedBox(height: 24),

          // Logout
          Container(
            width: double.infinity,
            child: ElevatedButton(
              onPressed: _showLogoutDialog,
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.red.shade600,
                foregroundColor: Colors.white,
                padding: const EdgeInsets.symmetric(vertical: 16),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(8),
                ),
              ),
              child: const Text(
                'Logout',
                style: TextStyle(fontSize: 16, fontWeight: FontWeight.w600),
              ),
            ),
          ),
          const SizedBox(height: 32),
        ],
      ),
    );
  }

  Widget _buildSectionHeader(String title) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 8),
      child: Text(
        title,
        style: TextStyle(
          fontSize: 18,
          fontWeight: FontWeight.bold,
          color: Colors.blue.shade600,
        ),
      ),
    );
  }

  Widget _buildProfileCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Row(
          children: [
            CircleAvatar(
              radius: 30,
              backgroundColor: Colors.blue.shade100,
              child: Icon(
                Icons.person,
                size: 30,
                color: Colors.blue.shade600,
              ),
            ),
            const SizedBox(width: 16),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Text(
                    'John Driver',
                    style: TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  Text(
                    'Driver ID: DR001',
                    style: TextStyle(
                      color: Colors.grey.shade600,
                      fontSize: 14,
                    ),
                  ),
                  Text(
                    'john.driver@swiftlogistics.com',
                    style: TextStyle(
                      color: Colors.grey.shade600,
                      fontSize: 14,
                    ),
                  ),
                ],
              ),
            ),
            IconButton(
              onPressed: () => _editProfile(),
              icon: const Icon(Icons.edit),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildSettingsCard(List<Widget> children) {
    return Card(
      child: Column(children: children),
    );
  }

  Widget _buildSwitchTile(
    String title,
    String subtitle,
    IconData icon,
    bool value,
    Function(bool) onChanged,
  ) {
    return ListTile(
      leading: Icon(icon, color: Colors.blue.shade600),
      title: Text(title),
      subtitle: Text(subtitle),
      trailing: Switch(
        value: value,
        onChanged: onChanged,
        activeColor: Colors.blue.shade600,
      ),
    );
  }

  Widget _buildDropdownTile(
    String title,
    String subtitle,
    IconData icon,
    String value,
    List<String> options,
    Function(String?) onChanged,
  ) {
    return ListTile(
      leading: Icon(icon, color: Colors.blue.shade600),
      title: Text(title),
      subtitle: Text(subtitle),
      trailing: DropdownButton<String>(
        value: value,
        onChanged: onChanged,
        items: options.map((String option) {
          return DropdownMenuItem<String>(
            value: option,
            child: Text(option),
          );
        }).toList(),
      ),
    );
  }

  Widget _buildActionTile(
    String title,
    String subtitle,
    IconData icon,
    VoidCallback onTap,
  ) {
    return ListTile(
      leading: Icon(icon, color: Colors.blue.shade600),
      title: Text(title),
      subtitle: Text(subtitle),
      trailing: const Icon(Icons.chevron_right),
      onTap: onTap,
    );
  }

  Widget _buildInfoTile(String title, String value, IconData icon) {
    return ListTile(
      leading: Icon(icon, color: Colors.blue.shade600),
      title: Text(title),
      trailing: Text(
        value,
        style: TextStyle(
          color: Colors.grey.shade600,
          fontWeight: FontWeight.w500,
        ),
      ),
    );
  }

  void _editProfile() {
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text('Profile editing coming soon!')),
    );
  }

  void _showChangePasswordDialog() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Change Password'),
          content: const Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                obscureText: true,
                decoration: InputDecoration(
                  labelText: 'Current Password',
                  border: OutlineInputBorder(),
                ),
              ),
              SizedBox(height: 16),
              TextField(
                obscureText: true,
                decoration: InputDecoration(
                  labelText: 'New Password',
                  border: OutlineInputBorder(),
                ),
              ),
              SizedBox(height: 16),
              TextField(
                obscureText: true,
                decoration: InputDecoration(
                  labelText: 'Confirm New Password',
                  border: OutlineInputBorder(),
                ),
              ),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text('Cancel'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.of(context).pop();
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Password updated successfully!')),
                );
              },
              child: const Text('Update'),
            ),
          ],
        );
      },
    );
  }

  void _showPrivacyPolicy() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Privacy Policy'),
          content: const SingleChildScrollView(
            child: Text(
              'SwiftLogistics Privacy Policy\n\n'
              'We respect your privacy and are committed to protecting your personal data. '
              'This privacy policy explains how we collect, use, and protect your information.\n\n'
              '1. Information We Collect\n'
              '- Personal identification information\n'
              '- Location data for navigation\n'
              '- Delivery performance metrics\n\n'
              '2. How We Use Your Information\n'
              '- To provide delivery services\n'
              '- To improve our app functionality\n'
              '- To communicate important updates\n\n'
              '3. Data Protection\n'
              'We implement security measures to protect your data...',
            ),
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text('Close'),
            ),
          ],
        );
      },
    );
  }

  void _showTermsOfService() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Terms of Service'),
          content: const SingleChildScrollView(
            child: Text(
              'SwiftLogistics Terms of Service\n\n'
              'By using this application, you agree to the following terms:\n\n'
              '1. Service Usage\n'
              '- Use the app only for authorized deliveries\n'
              '- Follow all traffic laws and safety regulations\n'
              '- Report any issues immediately\n\n'
              '2. Driver Responsibilities\n'
              '- Maintain professional conduct\n'
              '- Handle packages with care\n'
              '- Update delivery status accurately\n\n'
              '3. Liability\n'
              'SwiftLogistics is not liable for...',
            ),
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text('Close'),
            ),
          ],
        );
      },
    );
  }

  void _showHelpCenter() {
    Navigator.of(context).push(
      MaterialPageRoute(
        builder: (context) => Scaffold(
          appBar: AppBar(
            title: const Text('Help Center'),
            backgroundColor: Colors.blue.shade600,
            foregroundColor: Colors.white,
          ),
          body: ListView(
            padding: const EdgeInsets.all(16),
            children: [
              _buildHelpItem('How to start a delivery?', 
                'Navigate to the delivery manifest and select a package to begin.'),
              _buildHelpItem('How to mark a delivery as complete?', 
                'Use the delivery status page to mark completion and capture proof.'),
              _buildHelpItem('What if customer is not available?', 
                'Mark as failed delivery and select the appropriate reason.'),
              _buildHelpItem('How to contact support?', 
                'Use the contact support option in settings or call emergency number.'),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildHelpItem(String question, String answer) {
    return Card(
      margin: const EdgeInsets.only(bottom: 16),
      child: ExpansionTile(
        title: Text(question),
        children: [
          Padding(
            padding: const EdgeInsets.all(16),
            child: Text(answer),
          ),
        ],
      ),
    );
  }

  void _contactSupport() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Contact Support'),
          content: const Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text('Emergency: +94 77 123 4567'),
              SizedBox(height: 8),
              Text('Support Email: support@swiftlogistics.com'),
              SizedBox(height: 8),
              Text('Business Hours: 8:00 AM - 6:00 PM'),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text('Close'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.of(context).pop();
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Calling support...')),
                );
              },
              child: const Text('Call Now'),
            ),
          ],
        );
      },
    );
  }

  void _reportBug() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Report a Bug'),
          content: const Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                decoration: InputDecoration(
                  labelText: 'Bug Title',
                  border: OutlineInputBorder(),
                ),
              ),
              SizedBox(height: 16),
              TextField(
                maxLines: 4,
                decoration: InputDecoration(
                  labelText: 'Describe the issue',
                  border: OutlineInputBorder(),
                ),
              ),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text('Cancel'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.of(context).pop();
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Bug report submitted!')),
                );
              },
              child: const Text('Submit'),
            ),
          ],
        );
      },
    );
  }

  void _showLogoutDialog() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Logout'),
          content: const Text('Are you sure you want to logout?'),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text('Cancel'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.of(context).pop();
                Navigator.of(context).pushNamedAndRemoveUntil(
                  '/',
                  (route) => false,
                );
              },
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.red.shade600,
              ),
              child: const Text('Logout'),
            ),
          ],
        );
      },
    );
  }
}
