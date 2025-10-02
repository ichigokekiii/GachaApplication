package com.example.gachaapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ThankYouActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you)

        val processingLayout = findViewById<LinearLayout>(R.id.processingLayout)
        val successLayout = findViewById<LinearLayout>(R.id.successLayout)
        val playAgainButton = findViewById<Button>(R.id.buttonPlayAgain)

        lifecycleScope.launch {
            processingLayout.visibility = View.VISIBLE
            successLayout.visibility = View.GONE

            delay(3000)

            val fadeIn = AnimationUtils.loadAnimation(this@ThankYouActivity, R.anim.fade_in)
            processingLayout.visibility = View.GONE
            successLayout.visibility = View.VISIBLE
            successLayout.startAnimation(fadeIn)
        }

        playAgainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)

            // ADDED: Apply custom fade transition
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

            finish()
        }
    }
}