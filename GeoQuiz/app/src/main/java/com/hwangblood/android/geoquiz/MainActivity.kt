package com.hwangblood.android.geoquiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)

        trueButton.setOnClickListener { view: View ->

            val mySnackbar = Snackbar.make(findViewById(R.id.root),
                R.string.correct_toast, Snackbar.LENGTH_LONG)
            mySnackbar.setAction(R.string.ok) {
                Toast.makeText(
                    this, R.string.correct_toast, Toast.LENGTH_SHORT
                ).show()
            }
            mySnackbar.show()
        }
        falseButton.setOnClickListener { view: View ->
            val mySnackbar = Snackbar.make(findViewById(R.id.root),
                R.string.incorrect_toast, Snackbar.LENGTH_LONG)
            mySnackbar.setAction(R.string.ok) {
                Toast.makeText(
                    this, R.string.incorrect_toast, Toast.LENGTH_SHORT
                ).show()
            }
            mySnackbar.show()
        }
    }
}