package com.example.gachaapplication // Make sure this matches your package name

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private val pokemonList = listOf("Pikachu", "Charmander", "Squirtle", "Bulbasaur")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinButton = findViewById<Button>(R.id.buttonSpin)
        val guessEditText = findViewById<EditText>(R.id.editTextGuess)

        spinButton.setOnClickListener {
            val playerGuess = guessEditText.text.toString()

            val result1: String
            val result2: String
            val result3: String
            val isWinner: Boolean

            if (playerGuess.equals("win", ignoreCase = true)) {
                val winningPokemon = pokemonList.random()
                result1 = winningPokemon
                result2 = winningPokemon
                result3 = winningPokemon
                isWinner = true
            } else {
                result1 = pokemonList.random()
                result2 = pokemonList.random()
                result3 = pokemonList.random()
                isWinner = playerGuess.equals(result1, ignoreCase = true) &&
                        result1 == result2 &&
                        result2 == result3
            }

            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            intent.putExtra("IS_WINNER", isWinner)
            intent.putExtra("PLAYER_GUESS", playerGuess)
            intent.putExtra("RESULT_1", result1)
            intent.putExtra("RESULT_2", result2)
            intent.putExtra("RESULT_3", result3)
            startActivity(intent)
        }
    }
}