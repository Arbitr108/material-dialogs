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
  @ColorRes res: Int = 0,
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
  return ContextCompat.getColor(context, res)
}

@ColorInt internal fun MaterialDialog.getColor(
  @ColorRes res: Int = 0,
  @AttrRes attr: Int? = null
): Int = getColor(context, res, attr)

internal fun MaterialDialog.getString(@StringRes res: Int, @StringRes fallback: Int = 0): CharSequence? {
  return context.resources.getText(if (res == 0) fallback else res)
}

internal fun getDrawable(
  context: Context,
  @DrawableRes res: Int = 0,
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
  return ContextCompat.getDrawable(context, res) ?: fallback
}

internal fun MaterialDialog.getDrawable(
  @DrawableRes res: Int = 0,
  @AttrRes attr: Int? = null,
  fallback: Drawable? = null
): Drawable? {
  return getDrawable(context, res = res, attr = attr, fallback = fallback)
}

internal fun MaterialDialog.getStringArray(@ArrayRes res: Int): Array<CharSequence> {
  return context.resources.getTextArray(res)
}

internal fun MaterialDialog.hasActionButtons(): Boolean {
  return view.buttonsLayout.visibleButtons
      .isNotEmpty()
}

internal fun MaterialDialog.setIcon(
  imageView: ImageView,
  @DrawableRes iconRes: Int,
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
  @StringRes textRes: Int = 0,
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
