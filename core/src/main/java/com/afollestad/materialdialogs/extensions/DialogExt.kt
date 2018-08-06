package com.afollestad.materialdialogs.extensions

import android.graphics.drawable.Drawable
import android.support.annotation.ArrayRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog

internal fun MaterialDialog.getString(@StringRes res: Int, @StringRes fallback: Int = 0): CharSequence? {
  return context.resources.getText(if (res == 0) fallback else res)
}

internal fun MaterialDialog.getDrawable(@DrawableRes res: Int, fallback: Drawable? = null): Drawable? {
  return ContextCompat.getDrawable(context, res) ?: fallback
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
  val drawable = getDrawable(iconRes, icon)
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
  click: ((MaterialDialog) -> (Unit))? = null
) {
  val value = text ?: getString(textRes, fallback)
  if (value != null) {
    (textView.parent as View).visibility = View.VISIBLE
    textView.visibility = View.VISIBLE
    textView.text = value
  } else {
    textView.visibility = View.GONE
  }
  if (value != null) {
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
