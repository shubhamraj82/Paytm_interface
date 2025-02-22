package com.android.paytmclone

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.paytmclone.databinding.ActivityPaymentResultBinding
import java.text.SimpleDateFormat

class PaymentResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentResultBinding
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        val name = intent.getStringExtra("name").toString()
        val upi = intent.getStringExtra("upi").toString()
        val amount = intent.getStringExtra("amount").toString()

        binding.textView26.text = name
        binding.textView27.text = "UPI ID: $upi"
        binding.textView271.text = "UPI ID: $upi"
        binding.textView261.text = name
        binding.textView28.text = "₹$amount"
        binding.textView281.text = "₹$amount"

        if (name.contains(" ")) binding.textView18.text = name.split(" ")[0][0].toString() + name.split(" ")[1][0]
        else binding.textView18.text = name[0].toString() + name[name.length-1]

        if (name.contains(" ")) binding.textView181.text = name.split(" ")[0][0].toString() + name.split(" ")[1][0]
        else binding.textView181.text = name[0].toString() + name[name.length-1]

//        01 Jan, 12:59 pm

        val reference = System.currentTimeMillis().toString()
        binding.time.text = SimpleDateFormat("dd MMM, hh:mm a").format(reference.toLong())
        binding.reference.text = "Ref no. XXXX ${reference.substring(reference.length - 4)}"

        binding.imageView15.setOnClickListener { finish() }
        binding.cardView31.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java).putExtra("name", name).putExtra("upi", upi))
            finish()
        }
    }
}