package com.bignerdranch.android.quizfirstproject

import androidx.lifecycle.ViewModel
import com.example.quizfirstproject.R

class QuizViewModel : ViewModel() {
    var currentIndex = 0
    var isCheater = false

    private val questionBank = listOf(
        Question(textResId = R.string.question_australia, answer = true, answered = false),
        Question(textResId = R.string.question_oceans, answer = true, answered = false),
        Question(textResId = R.string.question_mideast, answer = false, answered = false),
        Question(textResId = R.string.question_africa, answer = false, answered = false),
        Question(textResId = R.string.question_americas, answer = true, answered = false),
        Question(textResId = R.string.question_asia, answer = true, answered = false)
    )
    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    val currentQuestionAnswered: Boolean
        get() = questionBank[currentIndex].answered
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1) % questionBank.size
    }

    fun checkEnd(col:Int): Boolean {
        return (currentIndex + col) == questionBank.size
    }

    fun questionAnswered(){
        questionBank[currentIndex].answered = true
    }

    fun checkEndQuestions(): Boolean {
        for (i in 0..<questionBank.size - 1)
            if (!questionBank[i].answered)
                return false
        return true
    }

}