package com.example.gachaapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val pokemonList = listOf("Pikachu", "Charmander", "Squirtle", "Bulbasaur")
    // NEW: Counter is now a countdown from 10
    private var spinsLeft = 10
    private lateinit var spinCounterTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinButton = findViewById<Button>(R.id.buttonSpin)
        val guessEditText = findViewById<EditText>(R.id.editTextGuess)
        spinCounterTextView = findViewById(R.id.textViewSpinCounter)
        val slot1 = findViewById<ImageView>(R.id.slot1)
        val slot2 = findViewById<ImageView>(R.id.slot2)
        val slot3 = findViewById<ImageView>(R.id.slot3)

        val pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse)
        slot1.startAnimation(pulseAnimation)
        slot2.startAnimation(pulseAnimation)
        slot3.startAnimation(pulseAnimation)

        spinCounterTextView.text = getString(R.string.spins_left_label, spinsLeft)

        spinButton.setOnClickListener {
            // Check if there are spins left
            if (spinsLeft <= 0) {
                Toast.makeText(this, "Out of spins! Win to play again.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Stop the function here
            }

            spinsLeft--
            // Update the text immediately for better feedback
            spinCounterTextView.text = getString(R.string.spins_left_label, spinsLeft)

            val playerGuess = guessEditText.text.toString()
            var isWinner: Boolean
            var winningPokemon = ""

            // NEW: Win chance logic based on a countdown
            val tryNumber = 10 - spinsLeft // Calculate current try number (1 through 10)

            if (spinsLeft == 0) { // Guaranteed win on the last spin
                isWinner = true
            } else {
                val winChance = 5 + ((tryNumber - 1) * 10)
                val randomPercent = (1..100).random()
                isWinner = randomPercent <= winChance
            }

            // ... (Rest of the logic is the same)
            val result1: String; val result2: String; val result3: String

            if (isWinner) {
                val isValidGuess = pokemonList.any { it.equals(playerGuess, ignoreCase = true) }
                winningPokemon = if (playerGuess.isBlank() || !isValidGuess) {
                    pokemonList.random()
                } else {
                    pokemonList.first { it.equals(playerGuess, ignoreCase = true) }
                }
                result1 = winningPokemon; result2 = winningPokemon; result3 = winningPokemon
            } else {
                result1 = pokemonList.random(); result2 = pokemonList.random(); result3 = pokemonList.random()
            }

            // On a win, reset spins to 10
            if (isWinner) {
                spinsLeft = 10
            }

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("IS_WINNER", isWinner)
            intent.putExtra("RESULT_1", result1)
            intent.putExtra("RESULT_2", result2)
            intent.putExtra("RESULT_3", result3)
            if (isWinner) { intent.putExtra("WINNING_POKEMON", winningPokemon) }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Update the counter every time we return to the screen
        spinCounterTextView.text = getString(R.string.spins_left_label, spinsLeft)
    }
}