@file:Suppress("DEPRECATION")

package com.android.paytmclone

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.android.paytmclone.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.white)

        val upi = intent.getStringExtra("upi").toString()
        val name = intent.getStringExtra("name").toString()

        binding.textView5.text = name
        binding.textView6.text = upi

        if (name.contains(" ")) binding.textView18.text = name.split(" ")[0][0].toString() + name.split(" ")[1][0]
        else binding.textView18.text = name[0].toString() + name[name.length-1]

        binding.editTextText.addTextChangedListener { text->
            binding.words.visibility = View.VISIBLE

            if (text.toString().isEmpty()) binding.words.visibility = View.GONE
            else if (text.toString().toInt() > 100000) {
                binding.words.setTextColor(getColor(R.color.red))
                binding.words.text = "You can only send upto Rs. 1 lakh at a time via UPI. Please enter a lower amount"
            }
            else {
                binding.words.setTextColor(getColor(R.color.blachite))
                val words = convertToWords(text.toString())

                if (words.isEmpty()) binding.words.visibility = View.GONE
                else binding.words.text = "Rupees ${words.replace("  ", " ").trim()} Only"
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
            }
            else {
                binding.paymentLayout.visibility = View.VISIBLE
                binding.text2.text = "Pay Securely ₹ $amount"
            }
        }

        binding.cardView24.setOnClickListener {
            startActivity(Intent(this, UpiActivity::class.java).putExtra("name", name).putExtra("upi", upi).putExtra("amount", binding.editTextText.text.toString()))
            finish()
        }

        binding.paymentLayout.setOnClickListener {
            binding.paymentLayout.visibility = View.GONE
        }
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        if (binding.paymentLayout.isVisible) binding.paymentLayout.visibility = View.GONE
        else super.onBackPressed()
    }

    private fun convertToWords(input: String): String {
        if (input.startsWith("0")) {
            binding.words.text = input.substring(1, input.length)
            return ""
        }

        if (input.isEmpty()) return ""
        else if (input.length == 1) return returnOnes(input[0])
        else if (input.length == 2) {
            return if (input[0] == '1') returnMiniTens(input[1])
            else {
                val a = returnTens(input[0])
                val b = returnOnes(input[1])

                "$a $b"
            }
        }
        else if (input.length == 3) {
            var b = ""
            val c: String
            val a = returnOnes(input[0]) + " Hundred"

            if (input[1] != '1') {
                b = returnTens(input[1])
                c = returnOnes(input[2])
            }
            else c = returnMiniTens(input[2])
            return "$a $b $c"
        }
        else if (input.length == 4) {
            var c = ""
            val d: String

            val a = returnOnes(input[0]) + " Thousand"
            val b = returnOnes(input[1]) + " Hundred"

            if (input[2] != '1') {
                c = returnTens(input[2])
                d = returnOnes(input[3])
            }
            else d = returnMiniTens(input[3])
            return "$a $b $c $d"
        }
        else if (input.length == 5) {
            var a = ""
            var d = ""
            val b: String
            val e: String

            if (input[0] != '1') {
                a = returnTens(input[0])
                b = returnOnes(input[1]) + " Thousand"
            }
            else b = returnMiniTens(input[1]) + " Thousand"

            val c = returnOnes(input[2]) + " Hundred"
            if (input[3] != '1') {
                d = returnTens(input[3])
                e = returnOnes(input[4])
            }
            else e = returnMiniTens(input[4])

            return "$a $b $c $d $e"
        }
        else if (input == "100000") return "One Lakh"
        else return ""
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
            '2' -> "Twelve"
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
        return when(c) {
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