package com.example.paytm

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.paytm.databinding.ActivityScannerBinding
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.NotFoundException
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.ResultPoint
import com.google.zxing.common.HybridBinarizer
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import java.io.FileNotFoundException

class ScannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScannerBinding
    private lateinit var barcodeView: CompoundBarcodeView
    private var isTorchOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_scanner)

        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.black)

        barcodeView = binding.scanner
        barcodeView.barcodeView.decoderFactory = DefaultDecoderFactory(listOf(BarcodeFormat.QR_CODE))
        barcodeView.initializeFromIntent(intent)

        // Reduce the scanning area
        barcodeView.setFramingRectSize(800, 800)

        binding.imageView6.setOnClickListener { finish() }
        binding.cardView18.setOnClickListener {
            if (isTorchOn) {
                barcodeView.setTorchOff()
                binding.cardView18.setCardBackgroundColor(Color.parseColor("#A87B7A7A"))
                binding.torch.setColorFilter(getColor(R.color.white))
            } else {
                barcodeView.setTorchOn()
                binding.cardView18.setCardBackgroundColor(getColor(R.color.white))
                binding.torch.setColorFilter(getColor(R.color.black))
            }
            isTorchOn = !isTorchOn
        }

        binding.cardView16.setOnClickListener { openQrPicker() }
        binding.cardView19.setOnClickListener { openQrPicker() }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startScanning()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun openQrPicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImage.launch(intent)
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageUri = result.data?.data
            if (imageUri != null) {
                try {
                    val inputStream = contentResolver.openInputStream(imageUri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)

                    if (bitmap == null) {
                        Toast.makeText(applicationContext, "Unsupported file format.", Toast.LENGTH_SHORT).show()
                        return@registerForActivityResult
                    }

                    val width = bitmap.width
                    val height = bitmap.height
                    val pixels = IntArray(width * height)

                    bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
                    bitmap.recycle()

                    val source = RGBLuminanceSource(width, height, pixels)
                    val bBitmap = BinaryBitmap(HybridBinarizer(source))
                    val reader = com.google.zxing.MultiFormatReader()
                    try {
                        val output = reader.decode(bBitmap)
                        getQrData(output.text.toString())
                    } catch (e: NotFoundException) {
                    }
                } catch (e: FileNotFoundException) {
                    Toast.makeText(applicationContext, "Something went wrong.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startScanning()
        }
    }

    private fun startScanning() {
        var lastResult = ""
        barcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                val resultText = result.text.trim()
                if (resultText.startsWith("upi://pay")) {
                    if (lastResult != resultText) {
                        getQrData(resultText)
                    }
                } else {
                    Toast.makeText(applicationContext, resultText, Toast.LENGTH_SHORT).show()
                }
                lastResult = resultText
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
        })
    }

    private fun getQrData(result: String) {
        var x = false
        var upi = ""
        var name = ""
        var b = ""

        for ((i, a) in result.toCharArray().withIndex()) {
            when (a) {
                '&' -> if (x) x = false
                '=' -> {
                    x = true
                    b = if (result.substring(i - 2).startsWith("pn")) "name"
                    else if (result.substring(i - 2).startsWith("pa")) "upi"
                    else "other"
                }
                else -> if (x) {
                    when (b) {
                        "name" -> name = "$name$a"
                        "upi" -> upi = "$upi$a"
                    }
                }
            }
        }

        if (upi.trim().isEmpty()) upi = "user@paytm"
        if (name.trim().isEmpty()) name = "Paytm User"

        startActivity(Intent(this, PaymentActivity::class.java).putExtra("name", name.replace("%20", " ")).putExtra("upi", upi))
        finish()
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }
}