package com.example.gachaapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class ClaimActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_claim)

        val confirmButton = findViewById<Button>(R.id.buttonConfirm)
        val emailEditText = findViewById<TextInputEditText>(R.id.editTextEmail)
        val cardNumberEditText = findViewById<TextInputEditText>(R.id.editTextCardNumber)
        val expirationEditText = findViewById<TextInputEditText>(R.id.editTextExpiration)
        val cvvEditText = findViewById<TextInputEditText>(R.id.editTextCvv)

        confirmButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val cardNumber = cardNumberEditText.text.toString()
            val expiration = expirationEditText.text.toString()
            val cvv = cvvEditText.text.toString()

            if (email.isBlank() || cardNumber.isBlank() || expiration.isBlank() || cvv.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, ThankYouActivity::class.java)
                startActivity(intent)
            }
        }
    }
}