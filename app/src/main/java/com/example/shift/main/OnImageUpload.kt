package com.example.shift.main

import android.net.Uri

interface OnImageUpload {
    fun onImageUpload(image: Uri): String
}