package com.asustug.themoviedb.utils.extension

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController


fun Fragment.findNavControllerSafely(id: Int): NavController? {
    return if (findNavController().currentDestination?.id == id) {
        findNavController()
    } else {
        Toast.makeText(requireContext(), "null", Toast.LENGTH_SHORT).show()
        null
    }
}