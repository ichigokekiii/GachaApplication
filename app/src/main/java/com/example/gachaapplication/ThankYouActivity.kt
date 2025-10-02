package com.example.gachaapplication // Make sure this matches your package name

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ThankYouActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you)

        val playAgainButton = findViewById<Button>(R.id.buttonPlayAgain)
        playAgainButton.setOnClickListener {
            // This special intent will clear all previous screens and start fresh at MainActivity
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish() // Close this final screen
        }
    }
}