package com.hwangblood.android.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * Store the index of current question into [SavedStateHandle], it saves data across process death
 */
class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        const val KEY_CURRENT_QUESTION_INDEX = "KEY_CURRENT_QUESTION_INDEX"
    }

    private val TAG = QuizViewModel::class.simpleName as String

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentQuestionIndex: Int
        get() = savedStateHandle[KEY_CURRENT_QUESTION_INDEX] ?: 0
        set(value) = savedStateHandle.set(KEY_CURRENT_QUESTION_INDEX, value)

    val currentQuestionNumber: Int
        get() = currentQuestionIndex + 1

    // question todos list, false is uncompleted, ture is completed
    private val questionTodos: MutableList<Boolean> = MutableList(questionBank.size) { false }

    val currentQuestionIsCompleted: Boolean
        get() = questionTodos[currentQuestionIndex]

    private var correctQuestionsCount: Int = 0
    val currentCorrectQuestionsCount: Int
        get() = correctQuestionsCount

    private var completedQuestionsCount: Int = 0
    val currentCompletedQuestionsCount: Int
        get() = completedQuestionsCount

    val totalQuestionsCount = questionBank.size

    val isQuizFinished: Boolean
        get() = completedQuestionsCount == totalQuestionsCount

    private val currentQuestionAnswer: Boolean
        get() = questionBank[currentQuestionIndex].answer

    val currentQuestionTextResId: Int
        get() = questionBank[currentQuestionIndex].textResId

    val isQuestionFirst: Boolean
        get() = currentQuestionIndex == 0

    val isQuestionLast: Boolean
        get() = currentQuestionIndex == (totalQuestionsCount - 1)

    fun moveToPrev(): Boolean {
        if (isQuestionFirst) {
            return false
        }

        currentQuestionIndex = (currentQuestionIndex - 1) % questionBank.size

        return true
    }

    fun moveToNext(): Boolean {
        if (isQuestionLast) {
            return false
        }

        currentQuestionIndex = (currentQuestionIndex + 1) % totalQuestionsCount
        Log.d(
            TAG,
            "CURRENT INDEX ${currentQuestionIndex >= 0 && currentQuestionIndex < questionBank.size}"
        )
        return currentQuestionIndex >= 0 && currentQuestionIndex < questionBank.size
    }

    fun checkAnswer(answer: Boolean): Boolean {
        // Log a message at DEBUG log level
        Log.d(TAG, "Current question index: $currentQuestionIndex.")
        try {
            // when currentIndex equals to 0, ArrayIndexOutOfBoundsException will happen
            val question = questionBank[currentQuestionIndex - 1]
            Log.d(TAG, "Current question $question")
        } catch (ex: ArrayIndexOutOfBoundsException) {
            // Log a message at ERROR log level along with an exception stack trace
            Log.e(TAG, "Index was out of bounds", ex)
        }

        // mark current question as completed
        questionTodos[currentQuestionIndex] = true
        // update quiz process
        completedQuestionsCount = questionTodos.count { it }

        if (isQuizFinished) {
            currentQuestionIndex = 0
        }

        if (currentQuestionAnswer == answer) {
            correctQuestionsCount = correctQuestionsCount.plus(1)
            return true
        }
        return false
    }

    fun resetQuiz() {
        currentQuestionIndex = 0
        completedQuestionsCount = 0
        correctQuestionsCount = 0
        questionTodos.fill(false)
    }

    init {
        Log.d(TAG, "ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }

}