@file:Suppress("unused")

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
  onToggle: ((Boolean) -> Unit)?
): MaterialDialog {
  assertOneSet(textRes, text)
  view.buttonsLayout.checkBoxPrompt.visibility = View.VISIBLE
  setText(
      view.buttonsLayout.checkBoxPrompt,
      textRes = textRes,
      text = text,
      allowDismiss = false
  )
  view.buttonsLayout.checkBoxPrompt.setOnCheckedChangeListener { _, checked ->
    onToggle?.invoke(checked)
  }
  return this
}