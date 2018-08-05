package com.afollestad.materialdialogssample

import android.app.Activity
import android.widget.Toast

var toast: Toast? = null

internal fun Activity.toast(message: CharSequence) {
  toast?.cancel()
  toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
  toast!!.show()
}