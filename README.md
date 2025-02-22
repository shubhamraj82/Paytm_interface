The project is an Android application developed using Kotlin and Java, with Gradle as the build system. It includes a QR code scanner feature implemented in the ScannerActivity class.

Main Features:

QR Code Scanning:
Uses the CodeScanner library to scan QR codes.
Handles camera permissions and starts scanning when permissions are granted.
Processes scanned QR codes to extract UPI payment information.

QR Code Picker:
Allows users to pick a QR code image from the gallery.
Decodes the QR code from the selected image and extracts the data.

UI Components:
Custom UI elements for the scanner, including a torch toggle and QR picker.
Layouts are defined in XML files, such as activity_scanner.xml
