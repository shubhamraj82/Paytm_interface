package com.example.paytm

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.paytm.databinding.ActivityUpiBinding

class UpiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpiBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upi)

        binding= ActivityUpiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#EDEDED")
        window.navigationBarColor = Color.parseColor("#EDEDED")

        binding.c1.setOnClickListener { pinEntered() }
        binding.c2.setOnClickListener { pinEntered() }
        binding.c3.setOnClickListener { pinEntered() }
        binding.c4.setOnClickListener { pinEntered() }
        binding.c5.setOnClickListener { pinEntered() }
        binding.c6.setOnClickListener { pinEntered() }
        binding.c7.setOnClickListener { pinEntered() }
        binding.c8.setOnClickListener { pinEntered() }
        binding.c9.setOnClickListener { pinEntered() }
        binding.c0.setOnClickListener { pinEntered() }

        val name = intent.getStringExtra("name").orEmpty()
        val upi = intent.getStringExtra("upi").orEmpty()
        val amount = intent.getStringExtra("amount").orEmpty()

        binding.textView23.text = name
        binding.amount.text = "₹ $amount.00"
        binding.textView11.text = "You are transferring money from your account to $name"

        binding.erase.setOnClickListener {
            when {
                binding.dot4.isVisible -> {
                    binding.dot4.visibility = View.GONE
                    binding.view4.visibility = View.VISIBLE
                }
                binding.dot3.isVisible -> {
                    binding.dot3.visibility = View.GONE
                    binding.view3.visibility = View.VISIBLE
                    binding.view3.setBackgroundColor(getColor(R.color.blachite))
                    binding.view4.setBackgroundColor(getColor(R.color.fg_toggle_dark))
                }
                binding.dot2.isVisible -> {
                    binding.dot2.visibility = View.GONE
                    binding.view2.visibility = View.VISIBLE
                    binding.view2.setBackgroundColor(getColor(R.color.blachite))
                    binding.view3.setBackgroundColor(getColor(R.color.fg_toggle_dark))
                }
                binding.dot1.isVisible -> {
                    binding.dot1.visibility = View.GONE
                    binding.view1.visibility = View.VISIBLE
                    binding.view1.setBackgroundColor(getColor(R.color.blachite))
                    binding.view2.setBackgroundColor(getColor(R.color.fg_toggle_dark))
                }
            }
        }

        binding.done.setOnClickListener {
            if (binding.dot4.isVisible) {
                startActivity(Intent(this, PaymentResultActivity::class.java).apply {
                    putExtra("name", name)
                    putExtra("upi", upi)
                    putExtra("amount", amount)
                })
                finish()
            } else {
                binding.errorLayout.visibility = View.VISIBLE
                binding.dismiss.setOnClickListener {
                    binding.errorLayout.visibility = View.GONE
                }

                Handler().postDelayed({
                    binding.errorLayout.visibility = View.GONE
                }, 3000)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun pinEntered() {
        when {
            binding.dot1.isGone -> {
                binding.dot1.visibility = View.VISIBLE
                binding.view1.visibility = View.INVISIBLE
                binding.view2.setBackgroundColor(getColor(R.color.blachite))
            }
            binding.dot2.isGone -> {
                binding.dot2.visibility = View.VISIBLE
                binding.view2.visibility = View.INVISIBLE
                binding.view3.setBackgroundColor(getColor(R.color.blachite))
            }
            binding.dot3.isGone -> {
                binding.dot3.visibility = View.VISIBLE
                binding.view3.visibility = View.INVISIBLE
                binding.view4.setBackgroundColor(getColor(R.color.blachite))
            }
            binding.dot4.isGone -> {
                binding.dot4.visibility = View.VISIBLE
                binding.view4.visibility = View.INVISIBLE
            }
        }
    }
}