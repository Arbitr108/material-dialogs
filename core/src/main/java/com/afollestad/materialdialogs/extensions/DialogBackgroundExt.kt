@file:Suppress("unused")

package com.afollestad.materialdialogs.extensions

import android.graphics.drawable.GradientDrawable
import android.support.annotation.CheckResult
import android.support.annotation.ColorInt
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.R.dimen

@CheckResult
fun MaterialDialog.backgroundColor(@ColorInt color: Int): MaterialDialog {
  val drawable = GradientDrawable()
  drawable.cornerRadius = context.resources
      .getDimension(dimen.md_dialog_bg_corner_radius)
  drawable.setColor(color)
  window.setBackgroundDrawable(drawable)
  return this
}