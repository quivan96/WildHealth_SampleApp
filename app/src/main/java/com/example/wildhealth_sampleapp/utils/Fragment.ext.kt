package com.example.wildhealth_sampleapp.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(msg: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), msg, duration).show()
}