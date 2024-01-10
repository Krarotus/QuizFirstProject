package com.bignerdranch.android.quizfirstproject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quizfirstproject.R
import java.util.Locale


const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"

class CheatActivity : AppCompatActivity() {
    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private lateinit var versionAndroid: TextView
    private var answerIsTrue = false
    private var currentIndex = 1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        currentIndex = intent.getIntExtra(EXTRA_CURRENT_INDEX, 0)
        currentIndex = currentIndex + 1
        answerTextView = findViewById(R.id.answer_text_view)
        answerTextView.text = currentIndex.toString()
        showAnswerButton = findViewById(R.id.show_answer_button)
        versionAndroid = findViewById(R.id.version_android)
        versionAndroid.setText("Android API "+ Build.VERSION.SDK_INT.toString())
        showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }
    }

    companion object {
        private const val EXTRA_CURRENT_INDEX = "com.bignerdranch.android.geoquiz.current_index"

        fun newIntent(packageContext: Context, answerIsTrue: Boolean, currentIndex: Int): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                putExtra(EXTRA_CURRENT_INDEX, currentIndex)
            }
        }
    }


    private fun
            setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

}