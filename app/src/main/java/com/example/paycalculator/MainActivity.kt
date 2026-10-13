
package com.example.paycalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Input fields
        val etHours = findViewById<EditText>(R.id.etHours)
        val etRate = findViewById<EditText>(R.id.etRate)
        val etTaxRate = findViewById<EditText>(R.id.etTaxRate)

        // Buttons
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val btnAbout = findViewById<Button>(R.id.btnAbout)

        // Output fields
        val tvPay = findViewById<TextView>(R.id.tvPay)
        val tvOvertime = findViewById<TextView>(R.id.tvOvertime)
        val tvTotalPay = findViewById<TextView>(R.id.tvTotalPay)
        val tvTax = findViewById<TextView>(R.id.tvTax)

        // Calculate Pay button
        btnCalculate.setOnClickListener {

            val hours = etHours.text.toString().toDoubleOrNull()
            val rate = etRate.text.toString().toDoubleOrNull()
            val taxRate = etTaxRate.text.toString().toDoubleOrNull()

            // Validate input
            if (hours == null || rate == null || taxRate == null ||
                !hours.isFinite() || !rate.isFinite() ||
                !taxRate.isFinite() ||
                hours < 0 || rate < 0 ||
                taxRate < 0 || taxRate > 1) {

                Toast.makeText(
                    this,
                    "Enter valid values (tax rate between 0 and 1)",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Calculate regular pay and overtime pay
            val pay: Double
            val overtimePay: Double

            if (hours <= 40) {
                pay = hours * rate
                overtimePay = 0.0
            } else {
                pay = 40 * rate
                overtimePay = (hours - 40) * rate * 1.5
            }

            // Calculate total pay and tax
            val totalPay = pay + overtimePay
            val tax = pay * taxRate

            // Display results
            tvPay.text = String.format(
                Locale.US,
                "Regular Pay: $%.2f",
                pay
            )

            tvOvertime.text = String.format(
                Locale.US,
                "Overtime Pay: $%.2f",
                overtimePay
            )

            tvTotalPay.text = String.format(
                Locale.US,
                "Total Pay: $%.2f",
                totalPay
            )

            tvTax.text = String.format(
                Locale.US,
                "Tax: $%.2f",
                tax
            )
        }

        // About button opens AboutActivity
        btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }
}
