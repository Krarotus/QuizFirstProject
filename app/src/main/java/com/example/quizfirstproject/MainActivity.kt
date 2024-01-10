package com.example.quizfirstproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.android.quizfirstproject.CheatActivity
import com.bignerdranch.android.quizfirstproject.EXTRA_ANSWER_SHOWN
import com.bignerdranch.android.quizfirstproject.QuizViewModel


private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button
    private var colCorrect: Int = 0
    private var colCheat: Int = 0
    private val quizViewModel: QuizViewModel by
    lazy {
        ViewModelProviders.of(this)[QuizViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)
        cheatButton = findViewById(R.id.cheat_button)


        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val currentIndex = quizViewModel.currentIndex
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue, currentIndex)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }


        updateQuestion()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT)
        {
            quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            colCheat += 1
            checkCheat()
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

    override fun onSaveInstanceState(savedInstanceState: Bundle)
    {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
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
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        trueButton.visibility = View.VISIBLE
        falseButton.visibility = View.VISIBLE
        cheatButton.visibility = View.VISIBLE
        checkCheat()

        if (quizViewModel.currentQuestionAnswered) {
            trueButton.visibility = View.GONE
            falseButton.visibility = View.GONE
            cheatButton.visibility = View.GONE
        }

        if (quizViewModel.checkEnd(1))
            nextButton.visibility = View.GONE

        if (quizViewModel.checkEnd(2))
            nextButton.visibility = View.VISIBLE

        if (quizViewModel.currentIndex == 0)
            prevButton.visibility = View.GONE

        if (quizViewModel.currentIndex == 1)
            prevButton.visibility = View.VISIBLE
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater ->
                R.string.judgment_toast
            userAnswer == correctAnswer ->
                R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        if (userAnswer == correctAnswer)
            colCorrect++
        Toast.makeText(this, messageResId,
            Toast.LENGTH_SHORT)
            .show()
        quizViewModel.questionAnswered()
        trueButton.visibility = View.GONE
        falseButton.visibility = View.GONE
        cheatButton.visibility = View.GONE
        if (quizViewModel.checkEndQuestions() && !quizViewModel.isCheater) {
            Toast.makeText(this, "Правильно : $colCorrect",
                Toast.LENGTH_SHORT)
                .show()
        }
    }
    private fun checkCheat(){
        if (colCheat ==3)
            cheatButton.visibility = View.GONE
    }

}