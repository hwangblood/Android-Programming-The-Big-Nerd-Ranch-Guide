package com.hwangblood.android.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hwangblood.android.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @Suppress("PrivatePropertyName")
    private val TAG = MainActivity::class.simpleName as String

    private lateinit var binding: ActivityMainBinding

    // not initialized until you access it
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // create view model at the time we use it
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.includedLayoutQuiz.questionTextView.setOnClickListener {
            Toast.makeText(
                this, R.string.question_clicked_toast, Toast.LENGTH_SHORT
            ).show()
        }
        binding.includedLayoutQuiz.trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }
        binding.includedLayoutQuiz.falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }
        binding.prevButton.setOnClickListener {
            val result = quizViewModel.moveToPrev()
            if (result) {
                updateQuestion()
            } else {
                Toast.makeText(
                    this, R.string.can_not_move_to_prev_question_toast, Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.nextButton.setOnClickListener {
            val result = quizViewModel.moveToNext()
            if (result) {
                updateQuestion()
            } else {
                Toast.makeText(
                    this, R.string.can_not_move_to_next_question_toast, Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.includedLayoutGrade.resetQuizButton.setOnClickListener {
            resetQuiz()
        }

        updateQuestion()
        updateQuizProcess()

        if (quizViewModel.isQuizFinished) {
            finishQuiz()
        }
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

        binding.prevButton.isEnabled = !quizViewModel.isQuestionFirst
        binding.nextButton.isEnabled = !quizViewModel.isQuestionLast

        val questionTextMsg = getString(
            R.string.question_text_message,
            quizViewModel.currentQuestionNumber,
            getString(quizViewModel.currentQuestionTextResId)
        )
        binding.includedLayoutQuiz.questionTextView.text = questionTextMsg

        updateAnswerButtons()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val result = quizViewModel.checkAnswer(userAnswer)

        val messageResId = if (result) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        updateAnswerButtons()
        updateQuizProcess()

        Toast.makeText(
            this, messageResId, Toast.LENGTH_SHORT
        ).show()

        if (quizViewModel.isQuizFinished) {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
        val gradeMessage = getString(
            R.string.quiz_grade_message,
            quizViewModel.currentCorrectQuestionsCount,
            quizViewModel.totalQuestionsCount
        )

        binding.layoutQuiz.visibility = View.GONE
        binding.navButtonGroup.visibility = View.GONE

        binding.layoutGrade.visibility = View.VISIBLE
        binding.includedLayoutGrade.gradeText.text = gradeMessage
    }

    private fun resetQuiz() {
        quizViewModel.resetQuiz()

        updateQuestion()
        updateQuizProcess()

        binding.layoutQuiz.visibility = View.VISIBLE
        binding.navButtonGroup.visibility = View.VISIBLE

        binding.layoutGrade.visibility = View.GONE
    }

    private fun updateQuizProcess() {
        val quizProcessMessage = getString(
            R.string.quiz_process_message,
            quizViewModel.currentCompletedQuestionsCount,
            quizViewModel.totalQuestionsCount
        )
        binding.includedLayoutQuiz.quizProcess.text = quizProcessMessage
    }

    private fun updateAnswerButtons() {
        if (quizViewModel.currentQuestionIsCompleted) {
            // question is completed, disable the answer buttons
            binding.includedLayoutQuiz.falseButton.isEnabled = false
            binding.includedLayoutQuiz.trueButton.isEnabled = false
        } else {
            // question is not completed, enable the answer buttons
            binding.includedLayoutQuiz.falseButton.isEnabled = true
            binding.includedLayoutQuiz.trueButton.isEnabled = true
        }
    }
}