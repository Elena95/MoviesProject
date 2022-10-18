package com.example.movies.viewmodel

import android.content.Intent
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModel

class CameraViewModel: ViewModel() {
    private val file = 1
    fun fileManager(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type ="*/*"
    }
}