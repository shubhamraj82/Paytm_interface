The current project is an Android application developed using Kotlin and Java, with Gradle as the build system. The application includes a QR code scanner functionality, which is implemented in the ScannerActivity class. The main features of the project are:  
1- QR Code Scanning:  
   _Uses the CodeScanner library to scan QR codes.
   _Handles camera permissions and starts scanning when permissions are granted.
   _Processes scanned QR codes to extract UPI payment information.
2- QR Code Picker:  
   _Allows users to pick a QR code image from the gallery.
   _Decodes the QR code from the selected image and extracts the data.
3- UI Components:  
   _Custom UI elements for the scanner, including a torch toggle and QR picker.
   _Layouts defined in XML files, such as activity_scanner.xml.
