package com.android.paytmclone

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
import com.android.paytmclone.databinding.ActivityScannerBinding
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.io.FileNotFoundException

class ScannerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScannerBinding
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.black)

        codeScanner = CodeScanner(this, binding.scanner)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.CONTINUOUS
        codeScanner.scanMode = ScanMode.CONTINUOUS
        codeScanner.isAutoFocusEnabled = true

        binding.imageView6.setOnClickListener { finish() }
        binding.cardView18.setOnClickListener {
            if (codeScanner.isFlashEnabled) {
                codeScanner.isFlashEnabled = false
                binding.cardView18.setCardBackgroundColor(Color.parseColor("#A87B7A7A"))
                binding.torch.setColorFilter(getColor(R.color.white))
            }
            else {
                codeScanner.isFlashEnabled = true
                binding.cardView18.setCardBackgroundColor(getColor(R.color.white))
                binding.torch.setColorFilter(getColor(R.color.black))
            }
        }

        binding.cardView16.setOnClickListener { openQrPicker() }
        binding.cardView19.setOnClickListener { openQrPicker() }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startScanning()
        }
        else ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
    }

    private fun openQrPicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImage.launch(intent)
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result->
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
                    val reader = MultiFormatReader()

                    try {
                        val output = reader.decode(bBitmap)
                        getQrData(output.text.toString())
                    }
                    catch (e: NotFoundException) {
                        Toast.makeText(applicationContext, "Format not supported.", Toast.LENGTH_SHORT).show()
                    }
                }
                catch (e: FileNotFoundException) {
                    Toast.makeText(applicationContext, "Something went wrong.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startScanning()
        }
    }

    private fun startScanning() {
        var lastResult = ""
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                val result = it.text.toString().trim()
                if (result.startsWith("upi://pay")) {
                    if (lastResult != result) {
                        getQrData(result)
                    }
                }
                else Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                lastResult = result
            }
        }
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
                    b = if (result.substring(i-2).startsWith("pn")) "name"
                    else if (result.substring(i-2).startsWith("pa")) "upi"
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
        codeScanner.startPreview()
    }

    override fun onPause() {
        super.onPause()
        codeScanner.stopPreview()
    }
}