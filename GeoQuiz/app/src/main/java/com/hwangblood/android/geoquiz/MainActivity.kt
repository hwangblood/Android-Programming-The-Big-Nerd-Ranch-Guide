package com.hwangblood.android.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.hwangblood.android.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.simpleName as String

    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    // question todos list, false is uncompleted, ture is completed
    private val questionTodos = MutableList(questionBank.size) { false }

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.questionTextView.setOnClickListener {
            Toast.makeText(
                this, R.string.question_clicked_toast, Toast.LENGTH_SHORT
            ).show()
        }
        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }
        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }
        binding.prevButton.setOnClickListener {
            currentIndex = (currentIndex - 1) % questionBank.size
            if (currentIndex < 0) {
                currentIndex = 0
                Toast.makeText(
                    this, R.string.you_already_at_the_first_question_toast, Toast.LENGTH_SHORT
                ).show()
            } else {
                updateQuestion()
            }
        }
        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)

        updateAnswerButtons()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        // Log a message at DEBUG log level
        Log.d(TAG, "Current question index: $currentIndex")
        try {
            val question = questionBank[-1]
        } catch (ex: ArrayIndexOutOfBoundsException) {
            // Log a message at ERROR log level along with an exception stack trace
            Log.e(TAG, "Index was out of bounds", ex)
        }

        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (correctAnswer == userAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        // mark current question as completed, and update the answer buttons' state
        questionTodos[currentIndex] = true
        updateAnswerButtons()

        Snackbar.make(
            binding.root, messageResId, Snackbar.LENGTH_LONG
        ).setAction(R.string.ok) {
            Toast.makeText(
                this, R.string.incorrect_toast, Toast.LENGTH_SHORT
            ).show()
        }.show()
    }

    private fun updateAnswerButtons() {
        if (questionTodos[currentIndex]) {
            // question is completed, disable the answer buttons
            binding.falseButton.isEnabled = false
            binding.trueButton.isEnabled = false
        } else {
            // question is not completed, enable the answer buttons
            binding.falseButton.isEnabled = true
            binding.trueButton.isEnabled = true
        }
    }
}