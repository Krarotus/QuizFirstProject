package com.bignerdranch.android.quizfirstproject

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean, var answered:Boolean)

