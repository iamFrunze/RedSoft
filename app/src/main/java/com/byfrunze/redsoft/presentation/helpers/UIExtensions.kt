package com.byfrunze.redsoft.presentation.helpers

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showMessage(@StringRes resId: Int, length: Int = Snackbar.LENGTH_SHORT) = view?.let {
    Snackbar.make(
        it,
        resId,
        length
    ).show()
}

fun Fragment.showMessage(message: String, length: Int = Snackbar.LENGTH_SHORT) = view?.let {
    Snackbar.make(
        it,
        message,
        length
    ).show()
}

fun Fragment.showToast(@StringRes resId: Int, length: Int = Toast.LENGTH_SHORT) = view?.let {
    Toast.makeText(
        context, resId, length
    ).show()
}

fun Fragment.showToast(message: String, length: Int = Toast.LENGTH_SHORT) = view?.let {
    Toast.makeText(
        context, message, length
    ).show()
}