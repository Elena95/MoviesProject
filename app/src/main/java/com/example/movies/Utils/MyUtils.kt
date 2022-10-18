package com.example.movies.Utils

import android.util.Log

class MyUtils {
    companion object {
        fun log(txt: String) {
            Log.d("LOG: ",txt)
        }

        fun randomColor(): Int {
            val color =(0x000000..0xFFFFFF).random()
            return color
        }
    }
}