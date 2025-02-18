package com.example.paytm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.paytm.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)

        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.white)

        val upi = intent.getStringExtra("upi").orEmpty()
        val name = intent.getStringExtra("name").orEmpty()

        binding.textView5.text = name
        binding.textView6.text = upi

        binding.textView18.text = if (name.contains(" ")) {
            name.split(" ")[0][0].toString() + name.split(" ")[1][0]
        } else {
            name[0].toString() + name[name.length - 1]
        }

        binding.editTextText.addTextChangedListener { text ->
            binding.words.visibility = View.VISIBLE

            if (text.isNullOrEmpty()) {
                binding.words.visibility = View.GONE
            } else {
                val amount = text.toString().toIntOrNull()
                if (amount == null || amount > 100000) {
                    binding.words.setTextColor(getColor(R.color.red))
                    binding.words.text = "You can only send up to Rs. 1 lakh at a time via UPI. Please enter a lower amount"
                } else {
                    binding.words.setTextColor(getColor(R.color.blachite))
                    val words = convertToWords(text.toString())
                    binding.words.text = if (words.isEmpty()) {
                        binding.words.visibility = View.GONE
                        ""
                    } else {
                        "Rupees ${words.replace("  ", " ").trim()} Only"
                    }
                }
            }
        }

        binding.cardView23.setOnClickListener {
            val view = currentFocus ?: View(this)
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            val amount = binding.editTextText.text.toString()

            binding.editTextText.clearFocus()
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            if (amount.isEmpty()) {
                binding.words.visibility = View.VISIBLE
                binding.words.text = "Please enter an amount to proceed"
                binding.words.setTextColor(getColor(R.color.red))
            } else {
                binding.paymentLayout.visibility = View.VISIBLE
                binding.text2.text = "Pay Securely ₹ $amount"
            }
        }

        binding.cardView24.setOnClickListener {
            startActivity(Intent(this, UpiActivity::class.java).apply {
                putExtra("name", name)
                putExtra("upi", upi)
                putExtra("amount", binding.editTextText.text.toString())
            })
            finish()
        }

        binding.paymentLayout.setOnClickListener {
            binding.paymentLayout.visibility = View.GONE
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    @Deprecated("This method has been deprecated in favor of using the OnBackPressedDispatcher via getOnBackPressedDispatcher(). The OnBackPressedDispatcher controls how back button events are dispatched to one or more OnBackPressedCallback objects.")
    override fun onBackPressed() {
        if (binding.paymentLayout.isVisible) {
            binding.paymentLayout.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }

    private fun convertToWords(input: String): String {
        if (input.startsWith("0")) {
            binding.words.text = input.substring(1)
            return ""
        }

        return when (input.length) {
            1 -> returnOnes(input[0])
            2 -> if (input[0] == '1') returnMiniTens(input[1]) else "${returnTens(input[0])} ${returnOnes(input[1])}"
            3 -> {
                val a = "${returnOnes(input[0])} Hundred"
                val b = if (input[1] != '1') returnTens(input[1]) else ""
                val c = if (input[1] != '1') returnOnes(input[2]) else returnMiniTens(input[2])
                "$a $b $c"
            }
            4 -> {
                val a = "${returnOnes(input[0])} Thousand"
                val b = "${returnOnes(input[1])} Hundred"
                val c = if (input[2] != '1') returnTens(input[2]) else ""
                val d = if (input[2] != '1') returnOnes(input[3]) else returnMiniTens(input[3])
                "$a $b $c $d"
            }
            5 -> {
                val a = if (input[0] != '1') returnTens(input[0]) else ""
                val b = if (input[0] != '1') "${returnOnes(input[1])} Thousand" else "${returnMiniTens(input[1])} Thousand"
                val c = "${returnOnes(input[2])} Hundred"
                val d = if (input[3] != '1') returnTens(input[3]) else ""
                val e = if (input[3] != '1') returnOnes(input[4]) else returnMiniTens(input[4])
                "$a $b $c $d $e"
            }
            6 -> "One Lakh"
            else -> ""
        }
    }

    private fun returnTens(c: Char): String {
        return when (c) {
            '2' -> "Twenty"
            '3' -> "Thirty"
            '4' -> "Forty"
            '5' -> "Fifty"
            '6' -> "Sixty"
            '7' -> "Seventy"
            '8' -> "Eighty"
            '9' -> "Ninety"
            else -> ""
        }
    }

    private fun returnMiniTens(c: Char): String {
        return when (c) {
            '1' -> "Eleven"
            '3' -> "Thirteen"
            '4' -> "Fourteen"
            '5' -> "Fifteen"
            '6' -> "Sixteen"
            '7' -> "Seventeen"
            '8' -> "Eighteen"
            '9' -> "Nineteen"
            else -> "Ten"
        }
    }

    private fun returnOnes(c: Char): String {
        return when (c) {
            '1' -> "One"
            '2' -> "Two"
            '3' -> "Three"
            '4' -> "Four"
            '5' -> "Five"
            '6' -> "Six"
            '7' -> "Seven"
            '8' -> "Eight"
            '9' -> "Nine"
            else -> ""
        }
    }
}