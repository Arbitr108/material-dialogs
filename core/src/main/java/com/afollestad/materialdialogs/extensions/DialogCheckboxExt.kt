package com.afollestad.materialdialogs.extensions

import android.support.annotation.CheckResult
import android.support.annotation.StringRes
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.assertOneSet

@CheckResult
fun MaterialDialog.checkBoxPrompt(
  @StringRes textRes: Int = 0,
  text: CharSequence? = null,
  isCheckedDefault: Boolean = false,
  onToggle: ((Boolean) -> Unit)?
): MaterialDialog {
  assertOneSet(textRes, text)
  with(view.buttonsLayout.checkBoxPrompt) {
    this.visibility = View.VISIBLE
    this.text = text ?: getString(textRes)
    this.isChecked = isCheckedDefault
    this.setOnCheckedChangeListener { _, checked ->
      onToggle?.invoke(checked)
    }
  }
  return this
}