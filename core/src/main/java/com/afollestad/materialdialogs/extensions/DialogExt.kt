/*
 * Licensed under Apache-2.0
 *
 * Designed an developed by Aidan Follestad (afollestad)
 */

package com.afollestad.materialdialogs.extensions

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.ArrayRes
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog

@ColorInt internal fun getColor(
  context: Context,
  @ColorRes res: Int? = null,
  @AttrRes attr: Int? = null
): Int {
  if (attr != null) {
    val a = context.theme.obtainStyledAttributes(intArrayOf(attr))
    try {
      return a.getColor(0, Color.BLACK)
    } finally {
      a.recycle()
    }
  }
  return ContextCompat.getColor(context, res ?: 0)
}

@ColorInt internal fun MaterialDialog.getColor(
  @ColorRes res: Int? = null,
  @AttrRes attr: Int? = null
): Int = getColor(context, res, attr)

internal fun MaterialDialog.getString(
  @StringRes res: Int? = null,
  @StringRes fallback: Int? = null
): CharSequence? {
  val resourceId = res ?: (fallback ?: 0)
  if (resourceId == 0) return null
  return context.resources.getText(resourceId)
}

internal fun getDrawable(
  context: Context,
  @DrawableRes res: Int? = null,
  @AttrRes attr: Int? = null,
  fallback: Drawable? = null
): Drawable? {
  if (attr != null) {
    val a = context.theme.obtainStyledAttributes(intArrayOf(attr))
    try {
      var d = a.getDrawable(0)
      if (d == null && fallback != null) {
        d = fallback
      }
      return d
    } finally {
      a.recycle()
    }
  }
  if (res == null) return fallback
  return ContextCompat.getDrawable(context, res)
}

internal fun MaterialDialog.getDrawable(
  ctxt: Context = context,
  @DrawableRes res: Int? = null,
  @AttrRes attr: Int? = null,
  fallback: Drawable? = null
): Drawable? {
  return getDrawable(ctxt, res = res, attr = attr, fallback = fallback)
}

internal fun MaterialDialog.getStringArray(@ArrayRes res: Int?): Array<CharSequence> {
  if (res == null) return emptyArray()
  return context.resources.getTextArray(res)
}

internal fun MaterialDialog.hasActionButtons(): Boolean {
  return view.buttonsLayout.visibleButtons
      .isNotEmpty()
}

internal fun MaterialDialog.setIcon(
  imageView: ImageView,
  @DrawableRes iconRes: Int?,
  icon: Drawable?
) {
  val drawable = getDrawable(res = iconRes, fallback = icon)
  if (drawable != null) {
    (imageView.parent as View).visibility = View.VISIBLE
    imageView.visibility = View.VISIBLE
    imageView.setImageDrawable(drawable)
  } else {
    imageView.visibility = View.GONE
  }
}

internal fun MaterialDialog.setText(
  textView: TextView,
  @StringRes textRes: Int? = null,
  text: CharSequence? = null,
  @StringRes fallback: Int = 0,
  click: ((MaterialDialog) -> (Unit))? = null,
  allowDismiss: Boolean = true
) {
  val value = text ?: getString(textRes, fallback)
  if (value != null) {
    (textView.parent as View).visibility = View.VISIBLE
    textView.visibility = View.VISIBLE
    textView.text = value
  } else {
    textView.visibility = View.GONE
  }
  if (value != null && (allowDismiss || click != null)) {
    textView.setOnClickListener {
      if (autoDismiss) {
        dismiss()
      }
      if (click != null) {
        click(this@setText)
      }
    }
  }
}
