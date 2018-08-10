/*
 * Licensed under Apache-2.0
 *
 * Designed an developed by Aidan Follestad (afollestad)
 */

package com.afollestad.materialdialogs.extensions

import android.graphics.drawable.GradientDrawable
import android.support.annotation.CheckResult
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.assertOneSet

@CheckResult
internal fun MaterialDialog.colorBackground(
  @ColorInt color: Int? = null,
  @ColorRes colorRes: Int = 0
): MaterialDialog {
  assertOneSet(colorRes, color)
  val colorValue = color ?: getColor(res = colorRes)
  val drawable = GradientDrawable()
  drawable.cornerRadius = context.resources
      .getDimension(R.dimen.md_dialog_bg_corner_radius)
  drawable.setColor(colorValue)
  window!!.setBackgroundDrawable(drawable)
  return this
}

//@CheckResult
//fun MaterialDialog.colorActionButtons(
//  @ColorInt color: Int? = null,
//  @ColorRes colorRes: Int = 0
//): MaterialDialog {
//  if (this.textViewMessage == null)
//    throw IllegalStateException("A message has not been set yet.")
//  assertOneSet(colorRes, color)
//  val colorValue = color ?: getColor(res = colorRes)
//  this.view.buttonsLayout.setActionButtonColor(colorValue)
//  return this
//}
