package com.example.gachaapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val pokemonList = listOf("Pikachu", "Charmander", "Squirtle", "Bulbasaur")
    private var spinCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // FIXED: Use findViewById to get references to all UI elements
        val spinButton = findViewById<Button>(R.id.buttonSpin)
        val guessEditText = findViewById<EditText>(R.id.editTextGuess)
        val spinCounterTextView = findViewById<TextView>(R.id.textViewSpinCounter)
        val slot1 = findViewById<ImageView>(R.id.slot1)
        val slot2 = findViewById<ImageView>(R.id.slot2)
        val slot3 = findViewById<ImageView>(R.id.slot3)

        // --- Start Animations ---
        val pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse)
        slot1.startAnimation(pulseAnimation)
        slot2.startAnimation(pulseAnimation)
        slot3.startAnimation(pulseAnimation)
        spinButton.startAnimation(pulseAnimation)

        // FIXED: Use the string resource to set the initial text
        spinCounterTextView.text = getString(R.string.spin_count_label, spinCount)

        spinButton.setOnClickListener {
            spinCount++
            // FIXED: Use the string resource to update the text
            spinCounterTextView.text = getString(R.string.spin_count_label, spinCount)

            val playerGuess = guessEditText.text.toString()

            var isWinner: Boolean
            var winningPokemon = ""

            if (spinCount >= 10) {
                isWinner = true
            } else {
                val winChance = 5 + ((spinCount - 1) * 10)
                val randomPercent = (1..100).random()
                isWinner = randomPercent <= winChance
            }

            val result1: String
            val result2: String
            val result3: String

            if (isWinner) {
                // Check if the guess is valid (case-insensitive)
                val isValidGuess = pokemonList.any { it.equals(playerGuess, ignoreCase = true) }
                winningPokemon = if (playerGuess.isBlank() || !isValidGuess) {
                    pokemonList.random()
                } else {
                    // Find the correctly capitalized version of the guessed Pokemon
                    pokemonList.first { it.equals(playerGuess, ignoreCase = true) }
                }
                result1 = winningPokemon
                result2 = winningPokemon
                result3 = winningPokemon
            } else {
                result1 = pokemonList.random()
                result2 = pokemonList.random()
                result3 = pokemonList.random()
            }

            if (isWinner) {
                spinCount = 0
            }

            // FIXED: Correct Intent syntax with ::class.java
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("IS_WINNER", isWinner)
            intent.putExtra("RESULT_1", result1)
            intent.putExtra("RESULT_2", result2)
            intent.putExtra("RESULT_3", result3)
            if (isWinner) {
                intent.putExtra("WINNING_POKEMON", winningPokemon)
            }
            startActivity(intent)
        }
    }
}