package com.example.paytm

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.paytm.databinding.ActivityPaymentResultBinding
import java.text.SimpleDateFormat

class PaymentResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentResultBinding

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment_result)


        binding = ActivityPaymentResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.white)
        binding.shimmer.startShimmer()

        Handler().postDelayed({
            binding.pendingCard.visibility = View.GONE
            binding.successCard.visibility = View.VISIBLE
            binding.imageView19.visibility = View.VISIBLE
            binding.help.visibility = View.VISIBLE
        }, 3000)

        Handler().postDelayed({
            if (!isDestroyed) MediaPlayer.create(this, R.raw.ring).start()
        }, 2500)

        val name = intent.getStringExtra("name").orEmpty()
        val upi = intent.getStringExtra("upi").orEmpty()
        val amount = intent.getStringExtra("amount").orEmpty()

        binding.textView26.text = name
        binding.textView27.text = "UPI ID: $upi"
        binding.textView271.text = "UPI ID: $upi"
        binding.textView261.text = name
        binding.textView28.text = "₹$amount"
        binding.textView281.text = "₹$amount"

        binding.textView18.text = if (name.contains(" ")) {
            name.split(" ")[0][0].toString() + name.split(" ")[1][0]
        } else {
            name[0].toString() + name[name.length - 1]
        }

        binding.textView181.text = if (name.contains(" ")) {
            name.split(" ")[0][0].toString() + name.split(" ")[1][0]
        } else {
            name[0].toString() + name[name.length - 1]
        }

        val reference = System.currentTimeMillis().toString()
        binding.time.text = SimpleDateFormat("dd MMM, hh:mm a").format(reference.toLong())
        binding.reference.text = "Ref no. XXXX ${reference.takeLast(4)}"

        binding.imageView15.setOnClickListener { finish() }
        binding.cardView31.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java).apply {
                putExtra("name", name)
                putExtra("upi", upi)
            })
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}