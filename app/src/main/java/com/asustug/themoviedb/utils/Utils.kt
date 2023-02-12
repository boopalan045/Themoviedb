package com.asustug.themoviedb.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.regex.Matcher
import java.util.regex.Pattern

class Utils(@ApplicationContext private val context: Context) {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
        const val apikey = "42d8887d8d91e293a3eb7c98518d1635"
    }

    fun processError(msg: String?) {
        showToast(msg.toString())
    }

    fun processError() {
        showToast("Something went wrong!!!")
    }

    fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * validate your email address format. Ex-akhi@mani.com
     */
    fun emailValidator(email: String?): Boolean {
        val pattern: Pattern
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher: Matcher = pattern.matcher(email!!)
        return matcher.matches()
    }

    fun hideKeyboard(view: View) {
        try {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            processError(e.message)
        }
    }

    fun showSnackBar(msg: String, view: View) {
        val snackbar = Snackbar
            .make(view, msg, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }

}