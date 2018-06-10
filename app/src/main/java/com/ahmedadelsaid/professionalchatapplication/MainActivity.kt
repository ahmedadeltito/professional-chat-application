package com.ahmedadelsaid.professionalchatapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkWordsBTN.setOnClickListener {
            textThatUserWrites.text = resources.getString(R.string.text_that_user_writes) + "\n" +
                    textEditorET.text.toString()
            tokenizerString(textEditorET.text.toString())
        }
    }

    /**
     * tokenizerString function is a function that implementing Sentence Tokenization algorithm
     */
    @SuppressLint("SetTextI18n")
    private fun tokenizerString(userInput: String) {
        val stringTokenizer = StringTokenizer(userInput, " ")
        val stringTokenizerArray = ArrayList<String>()
        while (stringTokenizer.hasMoreTokens()) {
            stringTokenizerArray.add(stringTokenizer.nextToken().toLowerCase())
        }
        checkInappropriateWords(stringTokenizerArray)?.let { inappropriateWordsArray ->
            if (!inappropriateWordsArray.isEmpty()) {
                var inappropriateWords = ""
                var inappropriateWordsNumber = 0
                inappropriateWordsArray.forEach {
                    inappropriateWords += it + "\n"
                    inappropriateWordsNumber++
                }
                val inappropriateWordsAccuracy =
                    ((inappropriateWordsNumber.toDouble() / stringTokenizerArray.size.toDouble()) * 100).toInt()
                textAccuracyTV.text = resources.getString(R.string.text_accuracy) + "\n" +
                        inappropriateWords +
                        "Inappropriate Words Accuracy : $inappropriateWordsAccuracy %"
            }
        }
    }

    /**
     * checkInappropriateWords function is the function that making the checking processing.
     * Here we are making it by a traditional way but we can enhance it by adding some machine learning and
     * neural network to make the process something hard.
     */
    private fun checkInappropriateWords(stringTokenizerArray: ArrayList<String>): ArrayList<String>? {
        val inappropriateWordsArray = resources.getStringArray(R.array.inappropriatewords)
        val returnedInappropriateWordsArray = ArrayList<String>()
        stringTokenizerArray.forEach { stringTokenizer ->
            if (inappropriateWordsArray.contains(stringTokenizer.toLowerCase())) {
                returnedInappropriateWordsArray.add(stringTokenizer)
            }
        }
        return returnedInappropriateWordsArray
    }
}
