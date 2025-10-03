package com.example.gachaapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.gachaapplication.ClaimActivity

class SecondActivity : AppCompatActivity() {

    private val pokemonList = listOf("Pikachu", "Charmander", "Squirtle", "Bulbasaur")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val resultTextView = findViewById<TextView>(R.id.textViewResult)
        val actionButton = findViewById<Button>(R.id.buttonPlayAgain)
        val resultSlot1 = findViewById<ImageView>(R.id.resultSlot1)
        val resultSlot2 = findViewById<ImageView>(R.id.resultSlot2)
        val resultSlot3 = findViewById<ImageView>(R.id.resultSlot3)

        resultTextView.visibility = View.INVISIBLE
        actionButton.visibility = View.INVISIBLE

        resultSlot1.setImageResource(R.drawable.poke_ball)
        resultSlot2.setImageResource(R.drawable.poke_ball)
        resultSlot3.setImageResource(R.drawable.poke_ball)

        val extras = intent.extras ?: return
        val isWinner = extras.getBoolean("IS_WINNER")
        val finalResult1 = extras.getString("RESULT_1")
        val finalResult2 = extras.getString("RESULT_2")
        val finalResult3 = extras.getString("RESULT_3")

        lifecycleScope.launch {
            delay(300)

            // Using 'i' is perfectly fine. It will give a warning, but not an error.
            for (i in 1..10) {
                resultSlot1.setImageResource(getRandomPokemonImage())
                delay(100)
            }
            resultSlot1.setImageResource(getDrawableIdForPokemon(finalResult1))
            delay(300)

            for (i in 1..10) {
                resultSlot2.setImageResource(getRandomPokemonImage())
                delay(100)
            }
            resultSlot2.setImageResource(getDrawableIdForPokemon(finalResult2))
            delay(300)

            for (i in 1..10) {
                resultSlot3.setImageResource(getRandomPokemonImage())
                delay(100)
            }
            resultSlot3.setImageResource(getDrawableIdForPokemon(finalResult3))
            delay(500)

            if (isWinner) {
                val winningPokemon = extras.getString("WINNING_POKEMON") ?: "a Pokémon"
                // Using a direct string template is fine. It will give a warning, but not an error.
                resultTextView.text = "You caught a ${winningPokemon}!"
            } else {
                resultTextView.text = "Almost! Try again."
            }

            val fadeIn = AnimationUtils.loadAnimation(this@SecondActivity, R.anim.fade_in)
            resultTextView.visibility = View.VISIBLE
            resultTextView.startAnimation(fadeIn)

            if (isWinner) {
                actionButton.text = "Claim it now"
                actionButton.setOnClickListener {
                    val claimIntent = Intent(this@SecondActivity, ClaimActivity::class.java)
                    startActivity(claimIntent)
                }
            } else {
                actionButton.text = "Play Again"
                actionButton.setOnClickListener {
                    finish()
                }
            }

            actionButton.visibility = View.VISIBLE
            actionButton.startAnimation(fadeIn)
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