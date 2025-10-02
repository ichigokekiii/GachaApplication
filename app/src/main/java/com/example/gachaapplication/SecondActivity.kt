package com.example.gachaapplication // Make sure this matches your package name

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SecondActivity : AppCompatActivity() {

    private val pokemonList = listOf("Pikachu", "Charmander", "Squirtle", "Bulbasaur")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val resultTextView = findViewById<TextView>(R.id.textViewResult)
        val playAgainButton = findViewById<Button>(R.id.buttonPlayAgain)
        val resultSlot1 = findViewById<ImageView>(R.id.resultSlot1)
        val resultSlot2 = findViewById<ImageView>(R.id.resultSlot2)
        val resultSlot3 = findViewById<ImageView>(R.id.resultSlot3)

        resultTextView.visibility = View.INVISIBLE

        val extras = intent.extras ?: return
        val isWinner = extras.getBoolean("IS_WINNER")
        val finalResult1 = extras.getString("RESULT_1")
        val finalResult2 = extras.getString("RESULT_2")
        val finalResult3 = extras.getString("RESULT_3")

        lifecycleScope.launch {
            for (i in 1..15) {
                resultSlot1.setImageResource(getRandomPokemonImage())
                resultSlot2.setImageResource(getRandomPokemonImage())
                resultSlot3.setImageResource(getRandomPokemonImage())
                delay(100)
            }

            resultSlot1.setImageResource(getDrawableIdForPokemon(finalResult1))
            resultSlot2.setImageResource(getDrawableIdForPokemon(finalResult2))
            resultSlot3.setImageResource(getDrawableIdForPokemon(finalResult3))

            if (isWinner) {
                resultTextView.text = "You Win!"
            } else {
                resultTextView.text = "Try Again"
            }

            val fadeIn = AnimationUtils.loadAnimation(this@SecondActivity, R.anim.fade_in)
            resultTextView.visibility = View.VISIBLE
            resultTextView.startAnimation(fadeIn)
        }

        playAgainButton.setOnClickListener {
            finish()
        }
    }

    private fun getRandomPokemonImage(): Int {
        return getDrawableIdForPokemon(pokemonList.random())
    }

    private fun getDrawableIdForPokemon(pokemonName: String?): Int {
        return when (pokemonName) {
            "Pikachu" -> R.drawable.pikachu
            "Charmander" -> R.drawable.charmander
            "Squirtle" -> R.drawable.squirtle
            "Bulbasaur" -> R.drawable.bulbasaur
            else -> R.drawable.poke_ball
        }
    }
}